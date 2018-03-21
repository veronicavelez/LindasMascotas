
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.Propietarios;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("propietarios")
public interface PropietariosSvc {
  
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Propietarios> listarPropietarios();
    
   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Propietarios> crear(Propietarios p);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Propietarios> editar(Propietarios p);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Propietarios> eliminar(Propietarios p);
    
    
}
