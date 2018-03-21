
package co.com.lindasmascotas.services;
import co.com.lindasmascotas.entities.Mascotas;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("mascotas")
public interface MascotasSvc {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Mascotas> listarMascotas();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Mascotas> crear(Mascotas m);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Mascotas> editar(Mascotas m);
    
    @Path("/estado")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Mascotas> estado(Mascotas m);    
    
}
