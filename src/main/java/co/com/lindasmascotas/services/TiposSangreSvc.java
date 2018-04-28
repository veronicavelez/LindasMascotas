
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.TiposSangre;
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




@Path("tipossangre")
public interface TiposSangreSvc {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<TiposSangre> listarTiposSangre();
       
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<TiposSangre> crear(TiposSangre ts);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<TiposSangre> editar(TiposSangre ts);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<TiposSangre> eliminar(@QueryParam("id") Integer id);  
    
    
}
