/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.services;
import co.com.lindasmascotas.entities.Detalleturnos;
import co.com.lindasmascotas.entities.Turnos;
import co.com.lindasmascotas.util.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ISABEL MEDINA
 */

@Path ("detalleturnos")
public interface DetalleTurnosSvc {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response listarDetalleTurnos();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response crear(Detalleturnos d);
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response editar(Detalleturnos d);
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    Response eliminar(@QueryParam("id") Integer id); 
    
}
