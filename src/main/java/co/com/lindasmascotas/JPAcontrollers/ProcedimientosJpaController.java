/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.JPAcontrollers;

import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.lindasmascotas.entities.Empleados;
import co.com.lindasmascotas.entities.Mascotas;
import co.com.lindasmascotas.entities.Procedimientos;
import co.com.lindasmascotas.entities.Servicios;
import co.com.lindasmascotas.entities.Sexos;
import co.com.lindasmascotas.entities.Vacunas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ISABEL MEDINA
 */
public class ProcedimientosJpaController implements Serializable {

    public ProcedimientosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Procedimientos procedimientos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados idEmpleado = procedimientos.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                procedimientos.setIdEmpleado(idEmpleado);
            }
            Mascotas idMascota = procedimientos.getIdMascota();
            if (idMascota != null) {
                idMascota = em.getReference(idMascota.getClass(), idMascota.getIdMascota());
                procedimientos.setIdMascota(idMascota);
            }
            Servicios idTipoServicio = procedimientos.getIdTipoServicio();
            if (idTipoServicio != null) {
                idTipoServicio = em.getReference(idTipoServicio.getClass(), idTipoServicio.getIdServicio());
                procedimientos.setIdTipoServicio(idTipoServicio);
            }
            Sexos idSexo = procedimientos.getIdSexo();
            if (idSexo != null) {
                idSexo = em.getReference(idSexo.getClass(), idSexo.getIdSexo());
                procedimientos.setIdSexo(idSexo);
            }
            Vacunas idVacuna = procedimientos.getIdVacuna();
            if (idVacuna != null) {
                idVacuna = em.getReference(idVacuna.getClass(), idVacuna.getIdVacuna());
                procedimientos.setIdVacuna(idVacuna);
            }
            em.persist(procedimientos);
            if (idEmpleado != null) {
                idEmpleado.getProcedimientosList().add(procedimientos);
                idEmpleado = em.merge(idEmpleado);
            }
            if (idMascota != null) {
                idMascota.getProcedimientosList().add(procedimientos);
                idMascota = em.merge(idMascota);
            }
            if (idTipoServicio != null) {
                idTipoServicio.getProcedimientosList().add(procedimientos);
                idTipoServicio = em.merge(idTipoServicio);
            }
            if (idSexo != null) {
                idSexo.getProcedimientosList().add(procedimientos);
                idSexo = em.merge(idSexo);
            }
            if (idVacuna != null) {
                idVacuna.getProcedimientosList().add(procedimientos);
                idVacuna = em.merge(idVacuna);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProcedimientos(procedimientos.getIdProcedimiento()) != null) {
                throw new PreexistingEntityException("Procedimientos " + procedimientos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Procedimientos procedimientos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Procedimientos persistentProcedimientos = em.find(Procedimientos.class, procedimientos.getIdProcedimiento());
            Empleados idEmpleadoOld = persistentProcedimientos.getIdEmpleado();
            Empleados idEmpleadoNew = procedimientos.getIdEmpleado();
            Mascotas idMascotaOld = persistentProcedimientos.getIdMascota();
            Mascotas idMascotaNew = procedimientos.getIdMascota();
            Servicios idTipoServicioOld = persistentProcedimientos.getIdTipoServicio();
            Servicios idTipoServicioNew = procedimientos.getIdTipoServicio();
            Sexos idSexoOld = persistentProcedimientos.getIdSexo();
            Sexos idSexoNew = procedimientos.getIdSexo();
            Vacunas idVacunaOld = persistentProcedimientos.getIdVacuna();
            Vacunas idVacunaNew = procedimientos.getIdVacuna();
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                procedimientos.setIdEmpleado(idEmpleadoNew);
            }
            if (idMascotaNew != null) {
                idMascotaNew = em.getReference(idMascotaNew.getClass(), idMascotaNew.getIdMascota());
                procedimientos.setIdMascota(idMascotaNew);
            }
            if (idTipoServicioNew != null) {
                idTipoServicioNew = em.getReference(idTipoServicioNew.getClass(), idTipoServicioNew.getIdServicio());
                procedimientos.setIdTipoServicio(idTipoServicioNew);
            }
            if (idSexoNew != null) {
                idSexoNew = em.getReference(idSexoNew.getClass(), idSexoNew.getIdSexo());
                procedimientos.setIdSexo(idSexoNew);
            }
            if (idVacunaNew != null) {
                idVacunaNew = em.getReference(idVacunaNew.getClass(), idVacunaNew.getIdVacuna());
                procedimientos.setIdVacuna(idVacunaNew);
            }
            procedimientos = em.merge(procedimientos);
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getProcedimientosList().remove(procedimientos);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getProcedimientosList().add(procedimientos);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            if (idMascotaOld != null && !idMascotaOld.equals(idMascotaNew)) {
                idMascotaOld.getProcedimientosList().remove(procedimientos);
                idMascotaOld = em.merge(idMascotaOld);
            }
            if (idMascotaNew != null && !idMascotaNew.equals(idMascotaOld)) {
                idMascotaNew.getProcedimientosList().add(procedimientos);
                idMascotaNew = em.merge(idMascotaNew);
            }
            if (idTipoServicioOld != null && !idTipoServicioOld.equals(idTipoServicioNew)) {
                idTipoServicioOld.getProcedimientosList().remove(procedimientos);
                idTipoServicioOld = em.merge(idTipoServicioOld);
            }
            if (idTipoServicioNew != null && !idTipoServicioNew.equals(idTipoServicioOld)) {
                idTipoServicioNew.getProcedimientosList().add(procedimientos);
                idTipoServicioNew = em.merge(idTipoServicioNew);
            }
            if (idSexoOld != null && !idSexoOld.equals(idSexoNew)) {
                idSexoOld.getProcedimientosList().remove(procedimientos);
                idSexoOld = em.merge(idSexoOld);
            }
            if (idSexoNew != null && !idSexoNew.equals(idSexoOld)) {
                idSexoNew.getProcedimientosList().add(procedimientos);
                idSexoNew = em.merge(idSexoNew);
            }
            if (idVacunaOld != null && !idVacunaOld.equals(idVacunaNew)) {
                idVacunaOld.getProcedimientosList().remove(procedimientos);
                idVacunaOld = em.merge(idVacunaOld);
            }
            if (idVacunaNew != null && !idVacunaNew.equals(idVacunaOld)) {
                idVacunaNew.getProcedimientosList().add(procedimientos);
                idVacunaNew = em.merge(idVacunaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = procedimientos.getIdProcedimiento();
                if (findProcedimientos(id) == null) {
                    throw new NonexistentEntityException("The procedimientos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Procedimientos procedimientos;
            try {
                procedimientos = em.getReference(Procedimientos.class, id);
                procedimientos.getIdProcedimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The procedimientos with id " + id + " no longer exists.", enfe);
            }
            Empleados idEmpleado = procedimientos.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getProcedimientosList().remove(procedimientos);
                idEmpleado = em.merge(idEmpleado);
            }
            Mascotas idMascota = procedimientos.getIdMascota();
            if (idMascota != null) {
                idMascota.getProcedimientosList().remove(procedimientos);
                idMascota = em.merge(idMascota);
            }
            Servicios idTipoServicio = procedimientos.getIdTipoServicio();
            if (idTipoServicio != null) {
                idTipoServicio.getProcedimientosList().remove(procedimientos);
                idTipoServicio = em.merge(idTipoServicio);
            }
            Sexos idSexo = procedimientos.getIdSexo();
            if (idSexo != null) {
                idSexo.getProcedimientosList().remove(procedimientos);
                idSexo = em.merge(idSexo);
            }
            Vacunas idVacuna = procedimientos.getIdVacuna();
            if (idVacuna != null) {
                idVacuna.getProcedimientosList().remove(procedimientos);
                idVacuna = em.merge(idVacuna);
            }
            em.remove(procedimientos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Procedimientos> findProcedimientosEntities() {
        return findProcedimientosEntities(true, -1, -1);
    }

    public List<Procedimientos> findProcedimientosEntities(int maxResults, int firstResult) {
        return findProcedimientosEntities(false, maxResults, firstResult);
    }

    private List<Procedimientos> findProcedimientosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Procedimientos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Procedimientos findProcedimientos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Procedimientos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcedimientosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Procedimientos> rt = cq.from(Procedimientos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
