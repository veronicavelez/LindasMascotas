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
import co.com.lindasmascotas.entities.Perfiles;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Isa
 */
public class PerfilesJpaController implements Serializable {

    public PerfilesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Perfiles perfiles) {
        if (perfiles.getEmpleadosList() == null) {
            perfiles.setEmpleadosList(new ArrayList<Empleados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Empleados> attachedEmpleadosList = new ArrayList<Empleados>();
            for (Empleados empleadosListEmpleadosToAttach : perfiles.getEmpleadosList()) {
                empleadosListEmpleadosToAttach = em.getReference(empleadosListEmpleadosToAttach.getClass(), empleadosListEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosList.add(empleadosListEmpleadosToAttach);
            }
            perfiles.setEmpleadosList(attachedEmpleadosList);
            em.persist(perfiles);
            for (Empleados empleadosListEmpleados : perfiles.getEmpleadosList()) {
                Perfiles oldIdPerfilOfEmpleadosListEmpleados = empleadosListEmpleados.getIdPerfil();
                empleadosListEmpleados.setIdPerfil(perfiles);
                empleadosListEmpleados = em.merge(empleadosListEmpleados);
                if (oldIdPerfilOfEmpleadosListEmpleados != null) {
                    oldIdPerfilOfEmpleadosListEmpleados.getEmpleadosList().remove(empleadosListEmpleados);
                    oldIdPerfilOfEmpleadosListEmpleados = em.merge(oldIdPerfilOfEmpleadosListEmpleados);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Perfiles perfiles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perfiles persistentPerfiles = em.find(Perfiles.class, perfiles.getIdPerfil());
            List<Empleados> empleadosListOld = persistentPerfiles.getEmpleadosList();
            List<Empleados> empleadosListNew = perfiles.getEmpleadosList();
            List<String> illegalOrphanMessages = null;
            for (Empleados empleadosListOldEmpleados : empleadosListOld) {
                if (!empleadosListNew.contains(empleadosListOldEmpleados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleados " + empleadosListOldEmpleados + " since its idPerfil field is not nullable.");
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
            perfiles.setEmpleadosList(empleadosListNew);
            perfiles = em.merge(perfiles);
            for (Empleados empleadosListNewEmpleados : empleadosListNew) {
                if (!empleadosListOld.contains(empleadosListNewEmpleados)) {
                    Perfiles oldIdPerfilOfEmpleadosListNewEmpleados = empleadosListNewEmpleados.getIdPerfil();
                    empleadosListNewEmpleados.setIdPerfil(perfiles);
                    empleadosListNewEmpleados = em.merge(empleadosListNewEmpleados);
                    if (oldIdPerfilOfEmpleadosListNewEmpleados != null && !oldIdPerfilOfEmpleadosListNewEmpleados.equals(perfiles)) {
                        oldIdPerfilOfEmpleadosListNewEmpleados.getEmpleadosList().remove(empleadosListNewEmpleados);
                        oldIdPerfilOfEmpleadosListNewEmpleados = em.merge(oldIdPerfilOfEmpleadosListNewEmpleados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = perfiles.getIdPerfil();
                if (findPerfiles(id) == null) {
                    throw new NonexistentEntityException("The perfiles with id " + id + " no longer exists.");
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
            Perfiles perfiles;
            try {
                perfiles = em.getReference(Perfiles.class, id);
                perfiles.getIdPerfil();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perfiles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Empleados> empleadosListOrphanCheck = perfiles.getEmpleadosList();
            for (Empleados empleadosListOrphanCheckEmpleados : empleadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Perfiles (" + perfiles + ") cannot be destroyed since the Empleados " + empleadosListOrphanCheckEmpleados + " in its empleadosList field has a non-nullable idPerfil field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(perfiles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Perfiles> findPerfilesEntities() {
        return findPerfilesEntities(true, -1, -1);
    }

    public List<Perfiles> findPerfilesEntities(int maxResults, int firstResult) {
        return findPerfilesEntities(false, maxResults, firstResult);
    }

    private List<Perfiles> findPerfilesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Perfiles.class));
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

    public Perfiles findPerfiles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Perfiles.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerfilesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Perfiles> rt = cq.from(Perfiles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
