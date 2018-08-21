
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.Procedimientos;
import co.com.lindasmascotas.util.Response;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("procedimientos")
public interface ProcedimientosSvc {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response listarProcedimientos();
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response crear(Procedimientos pr);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response editar(Procedimientos pr);
    
    
    
    
}
