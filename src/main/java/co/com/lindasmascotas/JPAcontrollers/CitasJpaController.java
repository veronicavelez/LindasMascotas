/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.JPAcontrollers;

import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Citas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.lindasmascotas.entities.Propietarios;
import co.com.lindasmascotas.entities.Servicios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ISABEL MEDINA
 */
public class CitasJpaController implements Serializable {

    public CitasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Citas citas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propietarios idPropietario = citas.getIdPropietario();
            if (idPropietario != null) {
                idPropietario = em.getReference(idPropietario.getClass(), idPropietario.getIdPropietario());
                citas.setIdPropietario(idPropietario);
            }
            Servicios idTipoServicio = citas.getIdTipoServicio();
            if (idTipoServicio != null) {
                idTipoServicio = em.getReference(idTipoServicio.getClass(), idTipoServicio.getIdServicio());
                citas.setIdTipoServicio(idTipoServicio);
            }
            em.persist(citas);
            if (idPropietario != null) {
                idPropietario.getCitasList().add(citas);
                idPropietario = em.merge(idPropietario);
            }
            if (idTipoServicio != null) {
                idTipoServicio.getCitasList().add(citas);
                idTipoServicio = em.merge(idTipoServicio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Citas citas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Citas persistentCitas = em.find(Citas.class, citas.getIdCita());
            Propietarios idPropietarioOld = persistentCitas.getIdPropietario();
            Propietarios idPropietarioNew = citas.getIdPropietario();
            Servicios idTipoServicioOld = persistentCitas.getIdTipoServicio();
            Servicios idTipoServicioNew = citas.getIdTipoServicio();
            if (idPropietarioNew != null) {
                idPropietarioNew = em.getReference(idPropietarioNew.getClass(), idPropietarioNew.getIdPropietario());
                citas.setIdPropietario(idPropietarioNew);
            }
            if (idTipoServicioNew != null) {
                idTipoServicioNew = em.getReference(idTipoServicioNew.getClass(), idTipoServicioNew.getIdServicio());
                citas.setIdTipoServicio(idTipoServicioNew);
            }
            citas = em.merge(citas);
            if (idPropietarioOld != null && !idPropietarioOld.equals(idPropietarioNew)) {
                idPropietarioOld.getCitasList().remove(citas);
                idPropietarioOld = em.merge(idPropietarioOld);
            }
            if (idPropietarioNew != null && !idPropietarioNew.equals(idPropietarioOld)) {
                idPropietarioNew.getCitasList().add(citas);
                idPropietarioNew = em.merge(idPropietarioNew);
            }
            if (idTipoServicioOld != null && !idTipoServicioOld.equals(idTipoServicioNew)) {
                idTipoServicioOld.getCitasList().remove(citas);
                idTipoServicioOld = em.merge(idTipoServicioOld);
            }
            if (idTipoServicioNew != null && !idTipoServicioNew.equals(idTipoServicioOld)) {
                idTipoServicioNew.getCitasList().add(citas);
                idTipoServicioNew = em.merge(idTipoServicioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = citas.getIdCita();
                if (findCitas(id) == null) {
                    throw new NonexistentEntityException("The citas with id " + id + " no longer exists.");
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
            Citas citas;
            try {
                citas = em.getReference(Citas.class, id);
                citas.getIdCita();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The citas with id " + id + " no longer exists.", enfe);
            }
            Propietarios idPropietario = citas.getIdPropietario();
            if (idPropietario != null) {
                idPropietario.getCitasList().remove(citas);
                idPropietario = em.merge(idPropietario);
            }
            Servicios idTipoServicio = citas.getIdTipoServicio();
            if (idTipoServicio != null) {
                idTipoServicio.getCitasList().remove(citas);
                idTipoServicio = em.merge(idTipoServicio);
            }
            em.remove(citas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Citas> findCitasEntities() {
        return findCitasEntities(true, -1, -1);
    }

    public List<Citas> findCitasEntities(int maxResults, int firstResult) {
        return findCitasEntities(false, maxResults, firstResult);
    }

    private List<Citas> findCitasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Citas.class));
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

    public Citas findCitas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Citas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Citas> rt = cq.from(Citas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
