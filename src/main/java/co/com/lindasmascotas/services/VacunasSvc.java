
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.Vacunas;
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


@Path("vacunas")
public interface VacunasSvc {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Vacunas> listarVacunas();
       
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Vacunas> crear(Vacunas v);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Vacunas> editar(Vacunas v);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Vacunas> eliminar(Vacunas v);  
    
}
