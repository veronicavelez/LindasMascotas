
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.Sexos;
import co.com.lindasmascotas.util.Response;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("sexos")
public interface SexosSvc {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response listarSexos();
       
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response crear(Sexos s);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response editar(Sexos s);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response eliminar(@QueryParam("id") Integer id);  
    
}
