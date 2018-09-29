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
import co.com.lindasmascotas.entities.TiposContrato;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ISABEL MEDINA
 */
public class TiposContratoJpaController implements Serializable {

    public TiposContratoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TiposContrato tiposContrato) {
        if (tiposContrato.getEmpleadosList() == null) {
            tiposContrato.setEmpleadosList(new ArrayList<Empleados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Empleados> attachedEmpleadosList = new ArrayList<Empleados>();
            for (Empleados empleadosListEmpleadosToAttach : tiposContrato.getEmpleadosList()) {
                empleadosListEmpleadosToAttach = em.getReference(empleadosListEmpleadosToAttach.getClass(), empleadosListEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosList.add(empleadosListEmpleadosToAttach);
            }
            tiposContrato.setEmpleadosList(attachedEmpleadosList);
            em.persist(tiposContrato);
            for (Empleados empleadosListEmpleados : tiposContrato.getEmpleadosList()) {
                TiposContrato oldIdTipoContratoOfEmpleadosListEmpleados = empleadosListEmpleados.getIdTipoContrato();
                empleadosListEmpleados.setIdTipoContrato(tiposContrato);
                empleadosListEmpleados = em.merge(empleadosListEmpleados);
                if (oldIdTipoContratoOfEmpleadosListEmpleados != null) {
                    oldIdTipoContratoOfEmpleadosListEmpleados.getEmpleadosList().remove(empleadosListEmpleados);
                    oldIdTipoContratoOfEmpleadosListEmpleados = em.merge(oldIdTipoContratoOfEmpleadosListEmpleados);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TiposContrato tiposContrato) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposContrato persistentTiposContrato = em.find(TiposContrato.class, tiposContrato.getIdTipoContrato());
            List<Empleados> empleadosListOld = persistentTiposContrato.getEmpleadosList();
            List<Empleados> empleadosListNew = tiposContrato.getEmpleadosList();
            List<String> illegalOrphanMessages = null;
            for (Empleados empleadosListOldEmpleados : empleadosListOld) {
                if (!empleadosListNew.contains(empleadosListOldEmpleados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleados " + empleadosListOldEmpleados + " since its idTipoContrato field is not nullable.");
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
            tiposContrato.setEmpleadosList(empleadosListNew);
            tiposContrato = em.merge(tiposContrato);
            for (Empleados empleadosListNewEmpleados : empleadosListNew) {
                if (!empleadosListOld.contains(empleadosListNewEmpleados)) {
                    TiposContrato oldIdTipoContratoOfEmpleadosListNewEmpleados = empleadosListNewEmpleados.getIdTipoContrato();
                    empleadosListNewEmpleados.setIdTipoContrato(tiposContrato);
                    empleadosListNewEmpleados = em.merge(empleadosListNewEmpleados);
                    if (oldIdTipoContratoOfEmpleadosListNewEmpleados != null && !oldIdTipoContratoOfEmpleadosListNewEmpleados.equals(tiposContrato)) {
                        oldIdTipoContratoOfEmpleadosListNewEmpleados.getEmpleadosList().remove(empleadosListNewEmpleados);
                        oldIdTipoContratoOfEmpleadosListNewEmpleados = em.merge(oldIdTipoContratoOfEmpleadosListNewEmpleados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tiposContrato.getIdTipoContrato();
                if (findTiposContrato(id) == null) {
                    throw new NonexistentEntityException("The tiposContrato with id " + id + " no longer exists.");
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
            TiposContrato tiposContrato;
            try {
                tiposContrato = em.getReference(TiposContrato.class, id);
                tiposContrato.getIdTipoContrato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiposContrato with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Empleados> empleadosListOrphanCheck = tiposContrato.getEmpleadosList();
            for (Empleados empleadosListOrphanCheckEmpleados : empleadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TiposContrato (" + tiposContrato + ") cannot be destroyed since the Empleados " + empleadosListOrphanCheckEmpleados + " in its empleadosList field has a non-nullable idTipoContrato field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tiposContrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TiposContrato> findTiposContratoEntities() {
        return findTiposContratoEntities(true, -1, -1);
    }

    public List<TiposContrato> findTiposContratoEntities(int maxResults, int firstResult) {
        return findTiposContratoEntities(false, maxResults, firstResult);
    }

    private List<TiposContrato> findTiposContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiposContrato.class));
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

    public TiposContrato findTiposContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TiposContrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiposContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TiposContrato> rt = cq.from(TiposContrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
