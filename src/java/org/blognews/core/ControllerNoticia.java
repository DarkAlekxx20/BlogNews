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
import org.blognews.model.Noticia;
import org.blognews.model.Personal;
import org.blognews.model.Usuario;

/**
 * @author Alex SP
 */
public class ControllerNoticia {
    public static boolean rol(Usuario u){
        if(u == null || u.getNombreUsuario() == null){
            return false;
        }else{
            return u.getRol().trim().toLowerCase().equals("Interno");
        }
    }
    
    public boolean guardarNoticia(Noticia n){
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        Statement stmt = null;
        ResultSet rs = null;
        boolean r = false;
        try {
            conn.setAutoCommit(false);
            String query1= "INSERT INTO noticia(titulo, contenido, fechaPublicacion, idPersonal)"
                                       +"VALUES('"+n.getTitulo()+"','"
                                                 +n.getContenido()+"',STR_TO_DATE('"+n.getFechaPublicacion()+"','%d/%m/%Y'),"
                                                 +n.getPersonal().getIdPersonal()+");";
            stmt = conn.createStatement();
            stmt.execute(query1);
            String query2 = "SELECT LAST_INSERT_ID();";
            rs = stmt.executeQuery(query2);
            conn.commit();
            conn.setAutoCommit(true);
            rs.close();
            stmt.close();
            conn.close();
            connMySQL.close();
            r=true;
        } catch (SQLException ex) {
            Logger.getLogger(ControllerNoticia.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                rs.close();
                stmt.close();
                conn.close();
                connMySQL.close();
                r=false;
            } catch (SQLException ex1) {
                Logger.getLogger(ControllerNoticia.class.getName()).log(Level.SEVERE, null, ex1);
                r=false;
            }
        }
        return r;
    }
    
        public List<Noticia> getAll() throws Exception{
        String sql="SELECT * FROM noticia n INNER JOIN personal p ON n.idPersonal=p.idPersonal;"; 
        ConexionMySQL connMySQL=new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Noticia> noticias=new ArrayList<>();
        while (rs.next())
            noticias.add(fill(rs));
        rs.close();
        pstmt.close();
        connMySQL.close();
        return noticias;
    }
        
        private Noticia fill(ResultSet rs) throws Exception{
        Personal p=new Personal();
        Noticia n=new Noticia();
        
        p.setIdPersonal(rs.getInt("idPersonal"));
        p.setApellidoPaterno(rs.getString("apePaterno"));
        p.setApellidoMaterno(rs.getString("apeMaterno"));
        p.setNombre(rs.getString("nombre"));
        p.setDireccion(rs.getString("direccion"));
        p.setFechaIngreso(rs.getString("fechaIngreso"));
        
        n.setIdNoticia(rs.getInt("idNoticia"));
        n.setTitulo(rs.getString("titulo"));
        n.setContenido(rs.getString("contenido"));
        n.setFechaPublicacion(rs.getString("fechaPublicacion"));
        n.setPersonal(p);       
        return n;
    }
}