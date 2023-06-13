package org.blognews.core;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.blognews.db.ConexionMySQL;
import org.blognews.model.Comentario;
import org.blognews.model.Noticia;
import org.blognews.model.Usuario;
/**
 * @author Alex SP
 */
public class ControllerComentario {
    public boolean guardarComentario(Comentario c) throws Exception{
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        Statement stmt=null;
        ResultSet rs=null;
        boolean r=false;
        
        try{
            conn.setAutoCommit(false);
            String query1="INSERT INTO comentario(texto, fechayHora, idNoticia, idUsuario) "
                                         +"VALUES('"+c.getTexto()+"', STR_TO_DATE('"+c.getFechayHora()+"', '%d/%m/%Y'), '"
                                                   +c.getNoticia().getIdNoticia()+"', '"
                                                   +c.getUsuario().getIdUsuario()+"');";
            stmt=conn.createStatement();
            stmt.execute(query1);
            
            String query2 = "SELECT LAST_INSERT_ID()";
            rs=stmt.executeQuery(query2);            
            
            conn.commit();
            conn.setAutoCommit(true);
            rs.close();
            stmt.close();
            conn.close();
            connMySQL.close();
            r=true;
            
        } catch (SQLException ex) {
            Logger.getLogger(ControllerComentario.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                rs.close();
                stmt.close();
                conn.close();
                connMySQL.close();
                r=false;
            } catch (SQLException ex1) {
                Logger.getLogger(ControllerComentario.class.getName()).log(Level.SEVERE, null, ex1);
                r=false;
            }
        }
        return r;
    }
    
    public List<Comentario> getAll() throws Exception{
        String sql="SELECT * FROM comentario c INNER JOIN noticia n ON c.idNoticia=n.idNoticia INNER JOIN usuario u ON c.idUsuario=u.idUsuario;"; 
        ConexionMySQL connMySQL=new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Comentario> comentario=new ArrayList<>();
        while (rs.next())
            comentario.add(fill(rs));
        rs.close();
        pstmt.close();
        connMySQL.close();
        return comentario;
    }
    
    private Comentario fill(ResultSet rs) throws Exception{
        Comentario c=new Comentario();
        Noticia n=new Noticia();
        Usuario u=new Usuario();
        
        u.setIdUsuario(rs.getInt("idUsuario"));
        u.setNombreUsuario(rs.getString("nombreUsuario"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setCorreo(rs.getString("correo"));
        u.setRol(rs.getString("rol"));
        n.setIdNoticia(rs.getInt("idNoticia"));
        n.setTitulo(rs.getString("titulo"));
        n.setContenido(rs.getString("contenido"));
        n.setFechaPublicacion(rs.getString("fechaPublicacion"));
        c.setIdComentario(rs.getInt("idComentario"));
        c.setTexto(rs.getString("texto"));
        c.setFechayHora(rs.getString("fechayHora"));
        c.setNoticia(n);
        c.setUsuario(u);
        return c;
    }
}