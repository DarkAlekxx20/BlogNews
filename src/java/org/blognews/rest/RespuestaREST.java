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
import org.blognews.core.ControllerRespuesta;
import org.blognews.model.Respuesta;

/**
 * @author Alex SP
 */
@Path("respuesta")
public class RespuestaREST {
    @Path("agregar")
    @Produces
    @POST
    public Response agregar(@FormParam("datos") @DefaultValue("") String datos){
        String out="";
        Gson gson=new Gson();
        Respuesta r=new Respuesta();
        ControllerRespuesta cr=new ControllerRespuesta();
        try{
            r=gson.fromJson(datos, Respuesta.class);
            cr.guardarRespuesta(r);
            out=gson.toJson(r);
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
        List<Respuesta> respuestas=new ArrayList<>();
        ControllerRespuesta cr=new ControllerRespuesta();
        try{
            respuestas=cr.getAll();
            out=gson.toJson(respuestas);
        } catch(Exception e){
            e.printStackTrace();
            out="{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}