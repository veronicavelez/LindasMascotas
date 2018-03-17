
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.TiposDocumento;
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

@Path("tiposdocumento")
public interface TiposDocumentoSvc {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<TiposDocumento> listarTiposDocumento();
       
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<TiposDocumento> crear(TiposDocumento td);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<TiposDocumento> editar(TiposDocumento td);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<TiposDocumento> eliminar(TiposDocumento td);
}
