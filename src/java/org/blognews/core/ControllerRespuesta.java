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
import org.blognews.model.Respuesta;
import org.blognews.model.Usuario;

/**
 * @author Alex SP
 */
public class ControllerRespuesta {
 public boolean guardarRespuesta(Respuesta re) throws Exception{
        ConexionMySQL connMySQL=new ConexionMySQL();
        Connection conn=connMySQL.open();
        Statement stmt=null;
        ResultSet rs=null;
        boolean r=false;
        try{
            conn.setAutoCommit(false);
            String query1="INSERT INTO respuesta(texto, fechayHora, idComentario, idUsuario) "
                                        +"VALUES('"+re.getTexto()+"', STR_TO_DATE('"+re.getFechayHora()+"', '%d/%m/%Y'), '"
                                                  +re.getComentario().getIdComentario()+"', '"
                                                  +re.getUsuario().getIdUsuario()+"');";
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
            Logger.getLogger(ControllerRespuesta.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                rs.close();
                stmt.close();
                conn.close();
                connMySQL.close();
                r=false;
            } catch (SQLException ex1) {
                Logger.getLogger(ControllerRespuesta.class.getName()).log(Level.SEVERE, null, ex1);
                r=false;
            }
        }
        return r;
    }
    
    public List<Respuesta> getAll() throws Exception{
        String sql="SELECT * FROM caja_comentarios;"; 
        ConexionMySQL connMySQL=new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Respuesta> respuesta=new ArrayList<>();
        while (rs.next())
            respuesta.add(fill(rs));
        rs.close();
        pstmt.close();
        connMySQL.close();
        return respuesta;
    }
    
    private Respuesta fill(ResultSet rs) throws Exception{
        Respuesta re=new Respuesta();
        Comentario c=new Comentario();
        Usuario u=new Usuario();        
        u.setIdUsuario(rs.getInt("idUsuario"));        
        c.setIdComentario(rs.getInt("idComentario"));
        re.setIdRespuesta(rs.getInt("idRespuesta"));
        re.setTexto(rs.getString("texto"));
        re.setFechayHora(rs.getString("fechayHora"));
        re.setComentario(c);
        re.setUsuario(u);
        return re;
    }   
}