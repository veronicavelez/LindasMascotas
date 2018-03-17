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
import co.com.lindasmascotas.entities.Propietarios;
import java.util.ArrayList;
import java.util.List;
import co.com.lindasmascotas.entities.Empleados;
import co.com.lindasmascotas.entities.TiposDocumento;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Veronica
 */
public class TiposDocumentoJpaController implements Serializable {

    public TiposDocumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TiposDocumento tiposDocumento) {
        if (tiposDocumento.getPropietariosList() == null) {
            tiposDocumento.setPropietariosList(new ArrayList<Propietarios>());
        }
        if (tiposDocumento.getEmpleadosList() == null) {
            tiposDocumento.setEmpleadosList(new ArrayList<Empleados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Propietarios> attachedPropietariosList = new ArrayList<Propietarios>();
            for (Propietarios propietariosListPropietariosToAttach : tiposDocumento.getPropietariosList()) {
                propietariosListPropietariosToAttach = em.getReference(propietariosListPropietariosToAttach.getClass(), propietariosListPropietariosToAttach.getIdPropietario());
                attachedPropietariosList.add(propietariosListPropietariosToAttach);
            }
            tiposDocumento.setPropietariosList(attachedPropietariosList);
            List<Empleados> attachedEmpleadosList = new ArrayList<Empleados>();
            for (Empleados empleadosListEmpleadosToAttach : tiposDocumento.getEmpleadosList()) {
                empleadosListEmpleadosToAttach = em.getReference(empleadosListEmpleadosToAttach.getClass(), empleadosListEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosList.add(empleadosListEmpleadosToAttach);
            }
            tiposDocumento.setEmpleadosList(attachedEmpleadosList);
            em.persist(tiposDocumento);
            for (Propietarios propietariosListPropietarios : tiposDocumento.getPropietariosList()) {
                TiposDocumento oldIdTipoDocumentoOfPropietariosListPropietarios = propietariosListPropietarios.getIdTipoDocumento();
                propietariosListPropietarios.setIdTipoDocumento(tiposDocumento);
                propietariosListPropietarios = em.merge(propietariosListPropietarios);
                if (oldIdTipoDocumentoOfPropietariosListPropietarios != null) {
                    oldIdTipoDocumentoOfPropietariosListPropietarios.getPropietariosList().remove(propietariosListPropietarios);
                    oldIdTipoDocumentoOfPropietariosListPropietarios = em.merge(oldIdTipoDocumentoOfPropietariosListPropietarios);
                }
            }
            for (Empleados empleadosListEmpleados : tiposDocumento.getEmpleadosList()) {
                TiposDocumento oldIdTipoDocumentoOfEmpleadosListEmpleados = empleadosListEmpleados.getIdTipoDocumento();
                empleadosListEmpleados.setIdTipoDocumento(tiposDocumento);
                empleadosListEmpleados = em.merge(empleadosListEmpleados);
                if (oldIdTipoDocumentoOfEmpleadosListEmpleados != null) {
                    oldIdTipoDocumentoOfEmpleadosListEmpleados.getEmpleadosList().remove(empleadosListEmpleados);
                    oldIdTipoDocumentoOfEmpleadosListEmpleados = em.merge(oldIdTipoDocumentoOfEmpleadosListEmpleados);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TiposDocumento tiposDocumento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposDocumento persistentTiposDocumento = em.find(TiposDocumento.class, tiposDocumento.getIdTipoDoc());
            List<Propietarios> propietariosListOld = persistentTiposDocumento.getPropietariosList();
            List<Propietarios> propietariosListNew = tiposDocumento.getPropietariosList();
            List<Empleados> empleadosListOld = persistentTiposDocumento.getEmpleadosList();
            List<Empleados> empleadosListNew = tiposDocumento.getEmpleadosList();
            List<String> illegalOrphanMessages = null;
            for (Propietarios propietariosListOldPropietarios : propietariosListOld) {
                if (!propietariosListNew.contains(propietariosListOldPropietarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propietarios " + propietariosListOldPropietarios + " since its idTipoDocumento field is not nullable.");
                }
            }
            for (Empleados empleadosListOldEmpleados : empleadosListOld) {
                if (!empleadosListNew.contains(empleadosListOldEmpleados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleados " + empleadosListOldEmpleados + " since its idTipoDocumento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Propietarios> attachedPropietariosListNew = new ArrayList<Propietarios>();
            for (Propietarios propietariosListNewPropietariosToAttach : propietariosListNew) {
                propietariosListNewPropietariosToAttach = em.getReference(propietariosListNewPropietariosToAttach.getClass(), propietariosListNewPropietariosToAttach.getIdPropietario());
                attachedPropietariosListNew.add(propietariosListNewPropietariosToAttach);
            }
            propietariosListNew = attachedPropietariosListNew;
            tiposDocumento.setPropietariosList(propietariosListNew);
            List<Empleados> attachedEmpleadosListNew = new ArrayList<Empleados>();
            for (Empleados empleadosListNewEmpleadosToAttach : empleadosListNew) {
                empleadosListNewEmpleadosToAttach = em.getReference(empleadosListNewEmpleadosToAttach.getClass(), empleadosListNewEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosListNew.add(empleadosListNewEmpleadosToAttach);
            }
            empleadosListNew = attachedEmpleadosListNew;
            tiposDocumento.setEmpleadosList(empleadosListNew);
            tiposDocumento = em.merge(tiposDocumento);
            for (Propietarios propietariosListNewPropietarios : propietariosListNew) {
                if (!propietariosListOld.contains(propietariosListNewPropietarios)) {
                    TiposDocumento oldIdTipoDocumentoOfPropietariosListNewPropietarios = propietariosListNewPropietarios.getIdTipoDocumento();
                    propietariosListNewPropietarios.setIdTipoDocumento(tiposDocumento);
                    propietariosListNewPropietarios = em.merge(propietariosListNewPropietarios);
                    if (oldIdTipoDocumentoOfPropietariosListNewPropietarios != null && !oldIdTipoDocumentoOfPropietariosListNewPropietarios.equals(tiposDocumento)) {
                        oldIdTipoDocumentoOfPropietariosListNewPropietarios.getPropietariosList().remove(propietariosListNewPropietarios);
                        oldIdTipoDocumentoOfPropietariosListNewPropietarios = em.merge(oldIdTipoDocumentoOfPropietariosListNewPropietarios);
                    }
                }
            }
            for (Empleados empleadosListNewEmpleados : empleadosListNew) {
                if (!empleadosListOld.contains(empleadosListNewEmpleados)) {
                    TiposDocumento oldIdTipoDocumentoOfEmpleadosListNewEmpleados = empleadosListNewEmpleados.getIdTipoDocumento();
                    empleadosListNewEmpleados.setIdTipoDocumento(tiposDocumento);
                    empleadosListNewEmpleados = em.merge(empleadosListNewEmpleados);
                    if (oldIdTipoDocumentoOfEmpleadosListNewEmpleados != null && !oldIdTipoDocumentoOfEmpleadosListNewEmpleados.equals(tiposDocumento)) {
                        oldIdTipoDocumentoOfEmpleadosListNewEmpleados.getEmpleadosList().remove(empleadosListNewEmpleados);
                        oldIdTipoDocumentoOfEmpleadosListNewEmpleados = em.merge(oldIdTipoDocumentoOfEmpleadosListNewEmpleados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tiposDocumento.getIdTipoDoc();
                if (findTiposDocumento(id) == null) {
                    throw new NonexistentEntityException("The tiposDocumento with id " + id + " no longer exists.");
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
            TiposDocumento tiposDocumento;
            try {
                tiposDocumento = em.getReference(TiposDocumento.class, id);
                tiposDocumento.getIdTipoDoc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiposDocumento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Propietarios> propietariosListOrphanCheck = tiposDocumento.getPropietariosList();
            for (Propietarios propietariosListOrphanCheckPropietarios : propietariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TiposDocumento (" + tiposDocumento + ") cannot be destroyed since the Propietarios " + propietariosListOrphanCheckPropietarios + " in its propietariosList field has a non-nullable idTipoDocumento field.");
            }
            List<Empleados> empleadosListOrphanCheck = tiposDocumento.getEmpleadosList();
            for (Empleados empleadosListOrphanCheckEmpleados : empleadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TiposDocumento (" + tiposDocumento + ") cannot be destroyed since the Empleados " + empleadosListOrphanCheckEmpleados + " in its empleadosList field has a non-nullable idTipoDocumento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tiposDocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TiposDocumento> findTiposDocumentoEntities() {
        return findTiposDocumentoEntities(true, -1, -1);
    }

    public List<TiposDocumento> findTiposDocumentoEntities(int maxResults, int firstResult) {
        return findTiposDocumentoEntities(false, maxResults, firstResult);
    }

    private List<TiposDocumento> findTiposDocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiposDocumento.class));
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

    public TiposDocumento findTiposDocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TiposDocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiposDocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TiposDocumento> rt = cq.from(TiposDocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
