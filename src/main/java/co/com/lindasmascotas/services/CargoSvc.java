
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.Cargos;
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


@Path("cargos")
public interface CargoSvc {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Cargos> listarCargos();
       
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Cargos> crear(Cargos c);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Cargos> editar(Cargos c);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Cargos> eliminar(Cargos c);  
    
}
