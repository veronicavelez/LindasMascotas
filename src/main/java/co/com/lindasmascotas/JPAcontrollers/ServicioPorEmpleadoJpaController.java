/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.JPAcontrollers;

import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.lindasmascotas.entities.Empleados;
import co.com.lindasmascotas.entities.ServicioPorEmpleado;
import co.com.lindasmascotas.entities.Servicios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ISABEL MEDINA
 */
public class ServicioPorEmpleadoJpaController implements Serializable {

    public ServicioPorEmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ServicioPorEmpleado servicioPorEmpleado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados idEmpleado = servicioPorEmpleado.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                servicioPorEmpleado.setIdEmpleado(idEmpleado);
            }
            Servicios idServicio = servicioPorEmpleado.getIdServicio();
            if (idServicio != null) {
                idServicio = em.getReference(idServicio.getClass(), idServicio.getIdServicio());
                servicioPorEmpleado.setIdServicio(idServicio);
            }
            em.persist(servicioPorEmpleado);
            if (idEmpleado != null) {
                idEmpleado.getServicioPorEmpleadoList().add(servicioPorEmpleado);
                idEmpleado = em.merge(idEmpleado);
            }
            if (idServicio != null) {
                idServicio.getServicioPorEmpleadoList().add(servicioPorEmpleado);
                idServicio = em.merge(idServicio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ServicioPorEmpleado servicioPorEmpleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ServicioPorEmpleado persistentServicioPorEmpleado = em.find(ServicioPorEmpleado.class, servicioPorEmpleado.getIdServEmpl());
            Empleados idEmpleadoOld = persistentServicioPorEmpleado.getIdEmpleado();
            Empleados idEmpleadoNew = servicioPorEmpleado.getIdEmpleado();
            Servicios idServicioOld = persistentServicioPorEmpleado.getIdServicio();
            Servicios idServicioNew = servicioPorEmpleado.getIdServicio();
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                servicioPorEmpleado.setIdEmpleado(idEmpleadoNew);
            }
            if (idServicioNew != null) {
                idServicioNew = em.getReference(idServicioNew.getClass(), idServicioNew.getIdServicio());
                servicioPorEmpleado.setIdServicio(idServicioNew);
            }
            servicioPorEmpleado = em.merge(servicioPorEmpleado);
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getServicioPorEmpleadoList().remove(servicioPorEmpleado);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getServicioPorEmpleadoList().add(servicioPorEmpleado);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            if (idServicioOld != null && !idServicioOld.equals(idServicioNew)) {
                idServicioOld.getServicioPorEmpleadoList().remove(servicioPorEmpleado);
                idServicioOld = em.merge(idServicioOld);
            }
            if (idServicioNew != null && !idServicioNew.equals(idServicioOld)) {
                idServicioNew.getServicioPorEmpleadoList().add(servicioPorEmpleado);
                idServicioNew = em.merge(idServicioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = servicioPorEmpleado.getIdServEmpl();
                if (findServicioPorEmpleado(id) == null) {
                    throw new NonexistentEntityException("The servicioPorEmpleado with id " + id + " no longer exists.");
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
            ServicioPorEmpleado servicioPorEmpleado;
            try {
                servicioPorEmpleado = em.getReference(ServicioPorEmpleado.class, id);
                servicioPorEmpleado.getIdServEmpl();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicioPorEmpleado with id " + id + " no longer exists.", enfe);
            }
            Empleados idEmpleado = servicioPorEmpleado.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getServicioPorEmpleadoList().remove(servicioPorEmpleado);
                idEmpleado = em.merge(idEmpleado);
            }
            Servicios idServicio = servicioPorEmpleado.getIdServicio();
            if (idServicio != null) {
                idServicio.getServicioPorEmpleadoList().remove(servicioPorEmpleado);
                idServicio = em.merge(idServicio);
            }
            em.remove(servicioPorEmpleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ServicioPorEmpleado> findServicioPorEmpleadoEntities() {
        return findServicioPorEmpleadoEntities(true, -1, -1);
    }

    public List<ServicioPorEmpleado> findServicioPorEmpleadoEntities(int maxResults, int firstResult) {
        return findServicioPorEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<ServicioPorEmpleado> findServicioPorEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ServicioPorEmpleado.class));
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

    public ServicioPorEmpleado findServicioPorEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ServicioPorEmpleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicioPorEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ServicioPorEmpleado> rt = cq.from(ServicioPorEmpleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
