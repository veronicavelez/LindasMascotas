
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.Generos;
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


@Path("generos")
public interface GenerosSvc {
     @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response listarGeneros();
       
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response crear(Generos g);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response editar(Generos g);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response eliminar(@QueryParam("id") Integer id);
}
