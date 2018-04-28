
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.Departamentos;
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

@Path("departamentos")
public interface DepartamentosSvc {
   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Departamentos> listarDepartamentos();
    
    @Path("/dptoporpais")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Departamentos> dptoPorPais(@QueryParam("id")String id);
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Departamentos> crear(Departamentos d);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Departamentos> editar(Departamentos d);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Departamentos> eliminar(@QueryParam("id") String id);    
}
