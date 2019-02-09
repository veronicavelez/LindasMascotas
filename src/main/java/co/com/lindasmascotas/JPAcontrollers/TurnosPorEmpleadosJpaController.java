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
import co.com.lindasmascotas.entities.Detalleturnos;
import co.com.lindasmascotas.entities.Empleados;
import co.com.lindasmascotas.entities.TurnosPorEmpleados;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Isa
 */
public class TurnosPorEmpleadosJpaController implements Serializable {

    public TurnosPorEmpleadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TurnosPorEmpleados turnosPorEmpleados) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detalleturnos idDetalleTurnos = turnosPorEmpleados.getIdDetalleTurnos();
            if (idDetalleTurnos != null) {
                idDetalleTurnos = em.getReference(idDetalleTurnos.getClass(), idDetalleTurnos.getIdDetalleTurno());
                turnosPorEmpleados.setIdDetalleTurnos(idDetalleTurnos);
            }
            Empleados idEmpleado = turnosPorEmpleados.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                turnosPorEmpleados.setIdEmpleado(idEmpleado);
            }
            em.persist(turnosPorEmpleados);
            if (idDetalleTurnos != null) {
                idDetalleTurnos.getTurnosPorEmpleadosList().add(turnosPorEmpleados);
                idDetalleTurnos = em.merge(idDetalleTurnos);
            }
            if (idEmpleado != null) {
                idEmpleado.getTurnosPorEmpleadosList().add(turnosPorEmpleados);
                idEmpleado = em.merge(idEmpleado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTurnosPorEmpleados(turnosPorEmpleados.getIdTurnosPorEmpl()) != null) {
                throw new PreexistingEntityException("TurnosPorEmpleados " + turnosPorEmpleados + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TurnosPorEmpleados turnosPorEmpleados) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TurnosPorEmpleados persistentTurnosPorEmpleados = em.find(TurnosPorEmpleados.class, turnosPorEmpleados.getIdTurnosPorEmpl());
            Detalleturnos idDetalleTurnosOld = persistentTurnosPorEmpleados.getIdDetalleTurnos();
            Detalleturnos idDetalleTurnosNew = turnosPorEmpleados.getIdDetalleTurnos();
            Empleados idEmpleadoOld = persistentTurnosPorEmpleados.getIdEmpleado();
            Empleados idEmpleadoNew = turnosPorEmpleados.getIdEmpleado();
            if (idDetalleTurnosNew != null) {
                idDetalleTurnosNew = em.getReference(idDetalleTurnosNew.getClass(), idDetalleTurnosNew.getIdDetalleTurno());
                turnosPorEmpleados.setIdDetalleTurnos(idDetalleTurnosNew);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                turnosPorEmpleados.setIdEmpleado(idEmpleadoNew);
            }
            turnosPorEmpleados = em.merge(turnosPorEmpleados);
            if (idDetalleTurnosOld != null && !idDetalleTurnosOld.equals(idDetalleTurnosNew)) {
                idDetalleTurnosOld.getTurnosPorEmpleadosList().remove(turnosPorEmpleados);
                idDetalleTurnosOld = em.merge(idDetalleTurnosOld);
            }
            if (idDetalleTurnosNew != null && !idDetalleTurnosNew.equals(idDetalleTurnosOld)) {
                idDetalleTurnosNew.getTurnosPorEmpleadosList().add(turnosPorEmpleados);
                idDetalleTurnosNew = em.merge(idDetalleTurnosNew);
            }
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getTurnosPorEmpleadosList().remove(turnosPorEmpleados);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getTurnosPorEmpleadosList().add(turnosPorEmpleados);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = turnosPorEmpleados.getIdTurnosPorEmpl();
                if (findTurnosPorEmpleados(id) == null) {
                    throw new NonexistentEntityException("The turnosPorEmpleados with id " + id + " no longer exists.");
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
            TurnosPorEmpleados turnosPorEmpleados;
            try {
                turnosPorEmpleados = em.getReference(TurnosPorEmpleados.class, id);
                turnosPorEmpleados.getIdTurnosPorEmpl();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The turnosPorEmpleados with id " + id + " no longer exists.", enfe);
            }
            Detalleturnos idDetalleTurnos = turnosPorEmpleados.getIdDetalleTurnos();
            if (idDetalleTurnos != null) {
                idDetalleTurnos.getTurnosPorEmpleadosList().remove(turnosPorEmpleados);
                idDetalleTurnos = em.merge(idDetalleTurnos);
            }
            Empleados idEmpleado = turnosPorEmpleados.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getTurnosPorEmpleadosList().remove(turnosPorEmpleados);
                idEmpleado = em.merge(idEmpleado);
            }
            em.remove(turnosPorEmpleados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TurnosPorEmpleados> findTurnosPorEmpleadosEntities() {
        return findTurnosPorEmpleadosEntities(true, -1, -1);
    }

    public List<TurnosPorEmpleados> findTurnosPorEmpleadosEntities(int maxResults, int firstResult) {
        return findTurnosPorEmpleadosEntities(false, maxResults, firstResult);
    }

    private List<TurnosPorEmpleados> findTurnosPorEmpleadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TurnosPorEmpleados.class));
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

    public TurnosPorEmpleados findTurnosPorEmpleados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TurnosPorEmpleados.class, id);
        } finally {
            em.close();
        }
    }

    public int getTurnosPorEmpleadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TurnosPorEmpleados> rt = cq.from(TurnosPorEmpleados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<TurnosPorEmpleados> findIdTurnosPorEmplByEmpleado(Integer empleado){
        EntityManager em = getEntityManager();
        
        try{
            Query q = em.createNamedQuery("TurnosPorEmpleados.findIdTurnosPorEmplByEmpleado");
            q.setParameter("idEmpleado", empleado);
            
            return q.getResultList();
            
        } catch(Exception e){
            
        }finally{
            em.close();
        }
        return null;
    }
    
}
