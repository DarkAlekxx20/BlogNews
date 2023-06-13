package org.blognews.core;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.blognews.db.ConexionMySQL;
import org.blognews.model.Usuario;
/** 
 * @author Alex SP
 */
public class UsuarioController{
    public Usuario login(String nombreUsuario,String contrasenia) throws Exception{
        String sql = "SELECT * FROM usuario WHERE nombreUsuario=? AND contrasenia=?;";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = null;
        pstmt.setString(1,nombreUsuario);
        pstmt.setString(2,contrasenia);
        rs = pstmt.executeQuery();
        Usuario u = null;
        if(rs.next()){
            u = (fill(rs));
        }
        rs.close();
        pstmt.close();
        connMySQL.close();
        return u;
    }
    
    public void insert(Usuario u) throws Exception{
        String sql = "CALL sp_insertUsuario(?,?,?);";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        CallableStatement cstmt = conn.prepareCall(sql);
        cstmt.setString(1,u.getNombreUsuario());
        cstmt.setString(2,u.getContrasenia());
        cstmt.setString(3,u.getCorreo());
        cstmt.executeUpdate();
        cstmt.close();
        connMySQL.close();
    }
    
    public List<Usuario> getAll(String filtro) throws Exception{
        String sql = "SELECT * FROM usuario;";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareCall(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Usuario> usuarios = new ArrayList<>();
        while(rs.next()){
            usuarios.add(fill(rs));
        }
        rs.close();
        pstmt.close();
        connMySQL.close();
        return usuarios;
    }
    
    private Usuario fill(ResultSet rs) throws Exception{
    Usuario u = new Usuario();
    u.setIdUsuario(rs.getInt("idUsuario"));
    u.setNombreUsuario(rs.getString("nombreUsuario"));
    u.setContrasenia(rs.getString("contrasenia"));
    u.setCorreo(rs.getString("correo"));
    u.setRol(rs.getString("rol"));
    return u;
    }
}