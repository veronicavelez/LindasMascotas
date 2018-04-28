
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.Ciudades;
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

@Path("ciudades")
public interface CiudadesSvc {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Ciudades> listarCiudades();
    
    @Path("/ciudadespordpto")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Ciudades> ciudadesPorDpto(@QueryParam("id")String id);
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Ciudades> crear(Ciudades c);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Ciudades> editar(Ciudades c);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Ciudades> eliminar(@QueryParam("id") String id);  
    
}
