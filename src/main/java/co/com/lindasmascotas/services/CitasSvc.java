
package co.com.lindasmascotas.services;
import co.com.lindasmascotas.entities.Citas;
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

@Path("citas")
public interface CitasSvc {

     @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Citas> listarCitas();
       
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Citas> crear(Citas c);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Citas> editar(Citas c);
    
    @Path("/cancelar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Citas> Cancelar(Citas c);  
    
}
