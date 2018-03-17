
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.Perfiles;
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

@Path("perfiles")
public interface PerfilesSvc {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Perfiles> listarPerfiles();
       
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Perfiles> crear(Perfiles p);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Perfiles> editar(Perfiles p);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Perfiles> eliminar(Perfiles p);  
}
