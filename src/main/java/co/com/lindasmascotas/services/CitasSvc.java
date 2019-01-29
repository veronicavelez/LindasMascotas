
package co.com.lindasmascotas.services;
import co.com.lindasmascotas.dtos.CitasDTO;
import co.com.lindasmascotas.entities.Citas;
import co.com.lindasmascotas.util.Response;
import java.util.Date;
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
    Response listarCitas();
     
    @GET
    @Path("/horarioemple")
    @Produces(MediaType.APPLICATION_JSON)
    Response horarioEmple(@QueryParam("idEmpleado")Integer idEmpleado, @QueryParam("fechaCita")Date fechaCita);
       
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response crear(CitasDTO c);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response editar(CitasDTO c);
    
    @Path("/cancelar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response Cancelar(CitasDTO c);  
    
    @GET
    @Path("/emplporserv") //empleados por servicio
    @Produces(MediaType.APPLICATION_JSON)
    Response empleadosPorServicio(@QueryParam("idServicio") Integer idServicio);
    
    @GET
    @Path("/propietario")
    @Produces(MediaType.APPLICATION_JSON)
    Response propietario(@QueryParam("idPropietario")Integer idPropietario);
    
}
