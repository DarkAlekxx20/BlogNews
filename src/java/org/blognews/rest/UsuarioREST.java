package org.blognews.rest;
import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.blognews.core.UsuarioController;
import org.blognews.model.Usuario;

/**
 * @author Alex SP
 */
@Path("usuario")
public class UsuarioREST {
    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("datosUsuario") String datosUsuario){
        String out = null;
        Gson gson = new Gson();
        Usuario u = null;
        UsuarioController uc = new UsuarioController();
        try {
            uc.insert(u);
            u = gson.fromJson(datosUsuario, Usuario.class);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
            {"error":"Error en base de datos :("}
            """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("datos") @DefaultValue("") String datos) throws Exception{
        String out = "";
        Gson gson = new Gson();
        Usuario u = new Usuario();
        UsuarioController uc = new UsuarioController();
        u = gson.fromJson(datos,Usuario.class);
        u = uc.login(u.getNombreUsuario(),u.getContrasenia());
        try {
        if(u != null){
            out = gson.toJson(u);
        }else{
            out="""
            {"error":"Datos de Credencial incorrectos"}
            """;
        }
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"exception":"?"}
                  """;
            out = String.format(out, e.toString());
        }
                return Response.status(Response.Status.OK).entity(out).build();
    }
}