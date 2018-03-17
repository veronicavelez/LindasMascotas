
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.Especies;
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

@Path("especies")
public interface EspeciesSvc {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Especies> listarEspecies();
       
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Especies> crear(Especies e);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Especies> editar(Especies e);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Especies> eliminar(Especies e);  
    
}
