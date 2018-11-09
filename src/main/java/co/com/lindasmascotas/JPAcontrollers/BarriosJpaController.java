/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.JPAcontrollers;

import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Barrios;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.lindasmascotas.entities.Propietarios;
import java.util.ArrayList;
import java.util.List;
import co.com.lindasmascotas.entities.Empleados;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Isa
 */
public class BarriosJpaController implements Serializable {

    public BarriosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Barrios barrios) {
        if (barrios.getPropietariosList() == null) {
            barrios.setPropietariosList(new ArrayList<Propietarios>());
        }
        if (barrios.getEmpleadosList() == null) {
            barrios.setEmpleadosList(new ArrayList<Empleados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Propietarios> attachedPropietariosList = new ArrayList<Propietarios>();
            for (Propietarios propietariosListPropietariosToAttach : barrios.getPropietariosList()) {
                propietariosListPropietariosToAttach = em.getReference(propietariosListPropietariosToAttach.getClass(), propietariosListPropietariosToAttach.getIdPropietario());
                attachedPropietariosList.add(propietariosListPropietariosToAttach);
            }
            barrios.setPropietariosList(attachedPropietariosList);
            List<Empleados> attachedEmpleadosList = new ArrayList<Empleados>();
            for (Empleados empleadosListEmpleadosToAttach : barrios.getEmpleadosList()) {
                empleadosListEmpleadosToAttach = em.getReference(empleadosListEmpleadosToAttach.getClass(), empleadosListEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosList.add(empleadosListEmpleadosToAttach);
            }
            barrios.setEmpleadosList(attachedEmpleadosList);
            em.persist(barrios);
            for (Propietarios propietariosListPropietarios : barrios.getPropietariosList()) {
                Barrios oldIdBarrioOfPropietariosListPropietarios = propietariosListPropietarios.getIdBarrio();
                propietariosListPropietarios.setIdBarrio(barrios);
                propietariosListPropietarios = em.merge(propietariosListPropietarios);
                if (oldIdBarrioOfPropietariosListPropietarios != null) {
                    oldIdBarrioOfPropietariosListPropietarios.getPropietariosList().remove(propietariosListPropietarios);
                    oldIdBarrioOfPropietariosListPropietarios = em.merge(oldIdBarrioOfPropietariosListPropietarios);
                }
            }
            for (Empleados empleadosListEmpleados : barrios.getEmpleadosList()) {
                Barrios oldIdBarrioOfEmpleadosListEmpleados = empleadosListEmpleados.getIdBarrio();
                empleadosListEmpleados.setIdBarrio(barrios);
                empleadosListEmpleados = em.merge(empleadosListEmpleados);
                if (oldIdBarrioOfEmpleadosListEmpleados != null) {
                    oldIdBarrioOfEmpleadosListEmpleados.getEmpleadosList().remove(empleadosListEmpleados);
                    oldIdBarrioOfEmpleadosListEmpleados = em.merge(oldIdBarrioOfEmpleadosListEmpleados);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Barrios barrios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barrios persistentBarrios = em.find(Barrios.class, barrios.getIdBarrio());
            List<Propietarios> propietariosListOld = persistentBarrios.getPropietariosList();
            List<Propietarios> propietariosListNew = barrios.getPropietariosList();
            List<Empleados> empleadosListOld = persistentBarrios.getEmpleadosList();
            List<Empleados> empleadosListNew = barrios.getEmpleadosList();
            List<String> illegalOrphanMessages = null;
            for (Propietarios propietariosListOldPropietarios : propietariosListOld) {
                if (!propietariosListNew.contains(propietariosListOldPropietarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propietarios " + propietariosListOldPropietarios + " since its idBarrio field is not nullable.");
                }
            }
            for (Empleados empleadosListOldEmpleados : empleadosListOld) {
                if (!empleadosListNew.contains(empleadosListOldEmpleados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleados " + empleadosListOldEmpleados + " since its idBarrio field is not nullable.");
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
            barrios.setPropietariosList(propietariosListNew);
            List<Empleados> attachedEmpleadosListNew = new ArrayList<Empleados>();
            for (Empleados empleadosListNewEmpleadosToAttach : empleadosListNew) {
                empleadosListNewEmpleadosToAttach = em.getReference(empleadosListNewEmpleadosToAttach.getClass(), empleadosListNewEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosListNew.add(empleadosListNewEmpleadosToAttach);
            }
            empleadosListNew = attachedEmpleadosListNew;
            barrios.setEmpleadosList(empleadosListNew);
            barrios = em.merge(barrios);
            for (Propietarios propietariosListNewPropietarios : propietariosListNew) {
                if (!propietariosListOld.contains(propietariosListNewPropietarios)) {
                    Barrios oldIdBarrioOfPropietariosListNewPropietarios = propietariosListNewPropietarios.getIdBarrio();
                    propietariosListNewPropietarios.setIdBarrio(barrios);
                    propietariosListNewPropietarios = em.merge(propietariosListNewPropietarios);
                    if (oldIdBarrioOfPropietariosListNewPropietarios != null && !oldIdBarrioOfPropietariosListNewPropietarios.equals(barrios)) {
                        oldIdBarrioOfPropietariosListNewPropietarios.getPropietariosList().remove(propietariosListNewPropietarios);
                        oldIdBarrioOfPropietariosListNewPropietarios = em.merge(oldIdBarrioOfPropietariosListNewPropietarios);
                    }
                }
            }
            for (Empleados empleadosListNewEmpleados : empleadosListNew) {
                if (!empleadosListOld.contains(empleadosListNewEmpleados)) {
                    Barrios oldIdBarrioOfEmpleadosListNewEmpleados = empleadosListNewEmpleados.getIdBarrio();
                    empleadosListNewEmpleados.setIdBarrio(barrios);
                    empleadosListNewEmpleados = em.merge(empleadosListNewEmpleados);
                    if (oldIdBarrioOfEmpleadosListNewEmpleados != null && !oldIdBarrioOfEmpleadosListNewEmpleados.equals(barrios)) {
                        oldIdBarrioOfEmpleadosListNewEmpleados.getEmpleadosList().remove(empleadosListNewEmpleados);
                        oldIdBarrioOfEmpleadosListNewEmpleados = em.merge(oldIdBarrioOfEmpleadosListNewEmpleados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = barrios.getIdBarrio();
                if (findBarrios(id) == null) {
                    throw new NonexistentEntityException("The barrios with id " + id + " no longer exists.");
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
            Barrios barrios;
            try {
                barrios = em.getReference(Barrios.class, id);
                barrios.getIdBarrio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The barrios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Propietarios> propietariosListOrphanCheck = barrios.getPropietariosList();
            for (Propietarios propietariosListOrphanCheckPropietarios : propietariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barrios (" + barrios + ") cannot be destroyed since the Propietarios " + propietariosListOrphanCheckPropietarios + " in its propietariosList field has a non-nullable idBarrio field.");
            }
            List<Empleados> empleadosListOrphanCheck = barrios.getEmpleadosList();
            for (Empleados empleadosListOrphanCheckEmpleados : empleadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barrios (" + barrios + ") cannot be destroyed since the Empleados " + empleadosListOrphanCheckEmpleados + " in its empleadosList field has a non-nullable idBarrio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(barrios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Barrios> findBarriosEntities() {
        return findBarriosEntities(true, -1, -1);
    }

    public List<Barrios> findBarriosEntities(int maxResults, int firstResult) {
        return findBarriosEntities(false, maxResults, firstResult);
    }

    private List<Barrios> findBarriosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Barrios.class));
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

    public Barrios findBarrios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Barrios.class, id);
        } finally {
            em.close();
        }
    }

    public int getBarriosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Barrios> rt = cq.from(Barrios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
