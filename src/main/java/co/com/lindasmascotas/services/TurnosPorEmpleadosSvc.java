/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.services;

import co.com.lindasmascotas.entities.TurnosPorEmpleados;
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
@Path("turnosporempl")
public interface TurnosPorEmpleadosSvc {
   
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   Response listarTurnosPorEmpleados();
    
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   Response crear(TurnosPorEmpleados templ);
   
   @PUT
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   Response editar(TurnosPorEmpleados templ);
   
   @DELETE
   @Produces(MediaType.APPLICATION_JSON)
   Response eliminar (@QueryParam("id") Integer Id);
    
}
