
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.ProductosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Productos;
import co.com.lindasmascotas.services.ProductosSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProductosImpl implements ProductosSvc {

    @Override
    public Response listarProductos() {
        Response res = new Response();
        ProductosJpaController ctrl = new ProductosJpaController(UPfactory.getFACTORY());
        
        try {
            
            List<Productos> list = ctrl.findProductosEntities();
            
            res.setStatus(true);
            res.setData(list);
        } catch (Exception e){
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }

            return res;
    }

    @Override
    public Response crear(Productos p) {
        Response res = new Response();
        ProductosJpaController ctrl = new ProductosJpaController(UPfactory.getFACTORY());
                
        try {
            
            p.setNombreProducto(p.getNombreProducto().toUpperCase());
            p.setDescripcionProducto(p.getDescripcionProducto().toUpperCase());
            p.setMarca(p.getMarca().toUpperCase());
            
            ctrl.create(p);
            
            res = listarProductos();
        } catch (Exception ex) {
            Logger.getLogger(ProductosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findProductosEntities());
        }
        return res;
    }

    @Override
    public Response editar(Productos p) {
        Response res = new Response();
        ProductosJpaController ctrl = new ProductosJpaController(UPfactory.getFACTORY());
        
        Productos productoActual = ctrl.findProductos(p.getIdProducto());
        
        productoActual.setNombreProducto(p.getNombreProducto());
        productoActual.setMarca(p.getMarca());
        productoActual.setPrecio(p.getPrecio());
        productoActual.setDescripcionProducto(p.getDescripcionProducto());
        
        try {
            p.setNombreProducto(p.getNombreProducto().toUpperCase());
            p.setDescripcionProducto(p.getDescripcionProducto().toUpperCase());
            p.setMarca(p.getMarca().toUpperCase());
            ctrl.edit(productoActual);
            
            res = listarProductos();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProductosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findProductosEntities());
        } catch (Exception ex) {
            Logger.getLogger(ProductosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findProductosEntities());
        }
        return res;

    }

    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
        ProductosJpaController ctrl = new ProductosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
            
            res = listarProductos();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProductosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findProductosEntities());
        }

        return res;
    }
     
    
}
