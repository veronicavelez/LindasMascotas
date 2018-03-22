
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.ProductosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Productos;
import co.com.lindasmascotas.services.ProductosSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProductosImpl implements ProductosSvc {

    @Override
    public List<Productos> listarProductos() {
        ProductosJpaController ctrl = new ProductosJpaController(UPfactory.getFACTORY());

            return ctrl.findProductosEntities();
    }

    @Override
    public List<Productos> crear(Productos p) {
        ProductosJpaController ctrl = new ProductosJpaController(UPfactory.getFACTORY());
                
        try {
            ctrl.create(p);
        } catch (Exception ex) {
            Logger.getLogger(ProductosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarProductos();
    }

    @Override
    public List<Productos> editar(Productos p) {
        ProductosJpaController ctrl = new ProductosJpaController(UPfactory.getFACTORY());
        
        Productos productoActual = ctrl.findProductos(p.getIdProducto());
        
        productoActual.setNombreProducto(p.getNombreProducto());
        productoActual.setMarca(p.getMarca());
        productoActual.setPrecio(p.getPrecio());
        productoActual.setDescripcionProducto(p.getDescripcionProducto());
        
        try {
            ctrl.edit(productoActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProductosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarProductos();

    }

    @Override
    public List<Productos> eliminar(Productos p) {
        ProductosJpaController ctrl = new ProductosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(p.getIdProducto());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProductosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarProductos();
    }
     
    
}
