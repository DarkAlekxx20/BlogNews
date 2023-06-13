package org.blognews.rest;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import org.blognews.core.ControllerComentario;
import org.blognews.model.Comentario;
/**
 * @author Alex SP
 */
@Path("comentario")
public class ComentarioREST{
    @Path("agregar")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response agregar(@FormParam("datos") @DefaultValue("") String datos){
        String out="";
        Gson gson=new Gson();
        Comentario c=new Comentario();
        ControllerComentario cc=new ControllerComentario();
        try{
            c=gson.fromJson(datos, Comentario.class);
            cc.guardarComentario(c);
            out=gson.toJson(c);
        } catch(JsonParseException jpe){
            jpe.printStackTrace();
            out="""
                {"exception":"Formato JSON de Datos Incorrectos"}
                """;
        } catch(Exception e){
            e.printStackTrace();
            out="""
                {"exception":"%s"}
                """;
            out=String.format(out, e.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAll(){
        String out="";
        Gson gson=new Gson();
        List<Comentario> comentarios=new ArrayList<>();
        ControllerComentario cc=new ControllerComentario();
        
        try{
            comentarios=cc.getAll();
            out=gson.toJson(comentarios);
        } catch(Exception e){
            e.printStackTrace();
            out="{\"exception\":\"Error interno del servidor.\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
}