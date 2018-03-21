
package co.com.lindasmascotas.services;
import co.com.lindasmascotas.entities.Empleados;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("empleados")
public interface EmpleadosSvc {
    
 @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Empleados> listarEmpleados();
   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Empleados> crear(Empleados e);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Empleados> editar(Empleados e);
    
    @Path("/estado")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Empleados> estado(Empleados e);    
}
   
    

