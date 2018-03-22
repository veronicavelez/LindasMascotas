package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.MascotasJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Mascotas;
import co.com.lindasmascotas.services.MascotasSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MascotasImpl implements MascotasSvc {

    @Override
    public List<Mascotas> listarMascotas() {
        MascotasJpaController ctrl = new MascotasJpaController(UPfactory.getFACTORY());

        return ctrl.findMascotasEntities();
    }

    @Override
    public List<Mascotas> crear(Mascotas m) {
        MascotasJpaController ctrl = new MascotasJpaController(UPfactory.getFACTORY());

        try {
            if (m.getIdPropietario() != null) {
                
               ctrl.create(m);   
            }
            
        } catch (Exception ex) {
            Logger.getLogger(EspeciesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarMascotas();
    }

    @Override
    public List<Mascotas> editar(Mascotas m) {
        MascotasJpaController ctrl = new MascotasJpaController(UPfactory.getFACTORY());
        Mascotas mascotaActual = ctrl.findMascotas(m.getIdMascota());

        mascotaActual.setNombreMascota(m.getNombreMascota());

        try {
            ctrl.edit(mascotaActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MascotasImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MascotasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarMascotas();

    }

    @Override
    public List<Mascotas> estado(Mascotas m) {
        MascotasJpaController ctrl = new MascotasJpaController(UPfactory.getFACTORY());
        Mascotas mascotaActual = ctrl.findMascotas(m.getIdMascota());

        mascotaActual.setEstado(m.getEstado());

        try {
            ctrl.edit(mascotaActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MascotasImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MascotasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarMascotas();

    }

}
