
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.TiposContrato;
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


@Path("tiposcontrato")
public interface TiposContratoSvc {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<TiposContrato> listarTiposContrato();
       
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<TiposContrato> crear(TiposContrato tc);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<TiposContrato> editar(TiposContrato tc);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<TiposContrato> eliminar(TiposContrato tc);  
    
    
}
