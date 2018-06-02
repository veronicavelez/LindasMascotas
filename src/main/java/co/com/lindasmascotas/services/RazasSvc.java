
package co.com.lindasmascotas.services;

    import co.com.lindasmascotas.entities.Razas;
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


@Path("razas")
public interface RazasSvc {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Razas> listarRazas();
       
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Razas> crear(Razas r);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Razas> editar(Razas r);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Razas> eliminar(@QueryParam("id") Integer id);  
    
    @Path("/razasporespecie")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Razas> razasPorEspecie(@QueryParam("id") Integer id); 
    
}
