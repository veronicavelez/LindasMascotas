/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.JPAcontrollers;

import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.lindasmascotas.entities.Empleados;
import co.com.lindasmascotas.entities.TiposSangre;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ISABEL MEDINA
 */
public class TiposSangreJpaController implements Serializable {

    public TiposSangreJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TiposSangre tiposSangre) {
        if (tiposSangre.getEmpleadosList() == null) {
            tiposSangre.setEmpleadosList(new ArrayList<Empleados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Empleados> attachedEmpleadosList = new ArrayList<Empleados>();
            for (Empleados empleadosListEmpleadosToAttach : tiposSangre.getEmpleadosList()) {
                empleadosListEmpleadosToAttach = em.getReference(empleadosListEmpleadosToAttach.getClass(), empleadosListEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosList.add(empleadosListEmpleadosToAttach);
            }
            tiposSangre.setEmpleadosList(attachedEmpleadosList);
            em.persist(tiposSangre);
            for (Empleados empleadosListEmpleados : tiposSangre.getEmpleadosList()) {
                TiposSangre oldIdTipoSangreOfEmpleadosListEmpleados = empleadosListEmpleados.getIdTipoSangre();
                empleadosListEmpleados.setIdTipoSangre(tiposSangre);
                empleadosListEmpleados = em.merge(empleadosListEmpleados);
                if (oldIdTipoSangreOfEmpleadosListEmpleados != null) {
                    oldIdTipoSangreOfEmpleadosListEmpleados.getEmpleadosList().remove(empleadosListEmpleados);
                    oldIdTipoSangreOfEmpleadosListEmpleados = em.merge(oldIdTipoSangreOfEmpleadosListEmpleados);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TiposSangre tiposSangre) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposSangre persistentTiposSangre = em.find(TiposSangre.class, tiposSangre.getIdTipoSangre());
            List<Empleados> empleadosListOld = persistentTiposSangre.getEmpleadosList();
            List<Empleados> empleadosListNew = tiposSangre.getEmpleadosList();
            List<String> illegalOrphanMessages = null;
            for (Empleados empleadosListOldEmpleados : empleadosListOld) {
                if (!empleadosListNew.contains(empleadosListOldEmpleados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleados " + empleadosListOldEmpleados + " since its idTipoSangre field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Empleados> attachedEmpleadosListNew = new ArrayList<Empleados>();
            for (Empleados empleadosListNewEmpleadosToAttach : empleadosListNew) {
                empleadosListNewEmpleadosToAttach = em.getReference(empleadosListNewEmpleadosToAttach.getClass(), empleadosListNewEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosListNew.add(empleadosListNewEmpleadosToAttach);
            }
            empleadosListNew = attachedEmpleadosListNew;
            tiposSangre.setEmpleadosList(empleadosListNew);
            tiposSangre = em.merge(tiposSangre);
            for (Empleados empleadosListNewEmpleados : empleadosListNew) {
                if (!empleadosListOld.contains(empleadosListNewEmpleados)) {
                    TiposSangre oldIdTipoSangreOfEmpleadosListNewEmpleados = empleadosListNewEmpleados.getIdTipoSangre();
                    empleadosListNewEmpleados.setIdTipoSangre(tiposSangre);
                    empleadosListNewEmpleados = em.merge(empleadosListNewEmpleados);
                    if (oldIdTipoSangreOfEmpleadosListNewEmpleados != null && !oldIdTipoSangreOfEmpleadosListNewEmpleados.equals(tiposSangre)) {
                        oldIdTipoSangreOfEmpleadosListNewEmpleados.getEmpleadosList().remove(empleadosListNewEmpleados);
                        oldIdTipoSangreOfEmpleadosListNewEmpleados = em.merge(oldIdTipoSangreOfEmpleadosListNewEmpleados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tiposSangre.getIdTipoSangre();
                if (findTiposSangre(id) == null) {
                    throw new NonexistentEntityException("The tiposSangre with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposSangre tiposSangre;
            try {
                tiposSangre = em.getReference(TiposSangre.class, id);
                tiposSangre.getIdTipoSangre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiposSangre with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Empleados> empleadosListOrphanCheck = tiposSangre.getEmpleadosList();
            for (Empleados empleadosListOrphanCheckEmpleados : empleadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TiposSangre (" + tiposSangre + ") cannot be destroyed since the Empleados " + empleadosListOrphanCheckEmpleados + " in its empleadosList field has a non-nullable idTipoSangre field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tiposSangre);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TiposSangre> findTiposSangreEntities() {
        return findTiposSangreEntities(true, -1, -1);
    }

    public List<TiposSangre> findTiposSangreEntities(int maxResults, int firstResult) {
        return findTiposSangreEntities(false, maxResults, firstResult);
    }

    private List<TiposSangre> findTiposSangreEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiposSangre.class));
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

    public TiposSangre findTiposSangre(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TiposSangre.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiposSangreCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TiposSangre> rt = cq.from(TiposSangre.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
