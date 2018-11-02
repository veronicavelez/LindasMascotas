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
import co.com.lindasmascotas.entities.Generos;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ISABEL MEDINA
 */
public class GenerosJpaController implements Serializable {

    public GenerosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Generos generos) {
        if (generos.getPropietariosList() == null) {
            generos.setPropietariosList(new ArrayList<Propietarios>());
        }
        if (generos.getEmpleadosList() == null) {
            generos.setEmpleadosList(new ArrayList<Empleados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Propietarios> attachedPropietariosList = new ArrayList<Propietarios>();
            for (Propietarios propietariosListPropietariosToAttach : generos.getPropietariosList()) {
                propietariosListPropietariosToAttach = em.getReference(propietariosListPropietariosToAttach.getClass(), propietariosListPropietariosToAttach.getIdPropietario());
                attachedPropietariosList.add(propietariosListPropietariosToAttach);
            }
            generos.setPropietariosList(attachedPropietariosList);
            List<Empleados> attachedEmpleadosList = new ArrayList<Empleados>();
            for (Empleados empleadosListEmpleadosToAttach : generos.getEmpleadosList()) {
                empleadosListEmpleadosToAttach = em.getReference(empleadosListEmpleadosToAttach.getClass(), empleadosListEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosList.add(empleadosListEmpleadosToAttach);
            }
            generos.setEmpleadosList(attachedEmpleadosList);
            em.persist(generos);
            for (Propietarios propietariosListPropietarios : generos.getPropietariosList()) {
                Generos oldIdGeneroOfPropietariosListPropietarios = propietariosListPropietarios.getIdGenero();
                propietariosListPropietarios.setIdGenero(generos);
                propietariosListPropietarios = em.merge(propietariosListPropietarios);
                if (oldIdGeneroOfPropietariosListPropietarios != null) {
                    oldIdGeneroOfPropietariosListPropietarios.getPropietariosList().remove(propietariosListPropietarios);
                    oldIdGeneroOfPropietariosListPropietarios = em.merge(oldIdGeneroOfPropietariosListPropietarios);
                }
            }
            for (Empleados empleadosListEmpleados : generos.getEmpleadosList()) {
                Generos oldIdGeneroOfEmpleadosListEmpleados = empleadosListEmpleados.getIdGenero();
                empleadosListEmpleados.setIdGenero(generos);
                empleadosListEmpleados = em.merge(empleadosListEmpleados);
                if (oldIdGeneroOfEmpleadosListEmpleados != null) {
                    oldIdGeneroOfEmpleadosListEmpleados.getEmpleadosList().remove(empleadosListEmpleados);
                    oldIdGeneroOfEmpleadosListEmpleados = em.merge(oldIdGeneroOfEmpleadosListEmpleados);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Generos generos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Generos persistentGeneros = em.find(Generos.class, generos.getIdGenero());
            List<Propietarios> propietariosListOld = persistentGeneros.getPropietariosList();
            List<Propietarios> propietariosListNew = generos.getPropietariosList();
            List<Empleados> empleadosListOld = persistentGeneros.getEmpleadosList();
            List<Empleados> empleadosListNew = generos.getEmpleadosList();
            List<String> illegalOrphanMessages = null;
            for (Propietarios propietariosListOldPropietarios : propietariosListOld) {
                if (!propietariosListNew.contains(propietariosListOldPropietarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propietarios " + propietariosListOldPropietarios + " since its idGenero field is not nullable.");
                }
            }
            for (Empleados empleadosListOldEmpleados : empleadosListOld) {
                if (!empleadosListNew.contains(empleadosListOldEmpleados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleados " + empleadosListOldEmpleados + " since its idGenero field is not nullable.");
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
            generos.setPropietariosList(propietariosListNew);
            List<Empleados> attachedEmpleadosListNew = new ArrayList<Empleados>();
            for (Empleados empleadosListNewEmpleadosToAttach : empleadosListNew) {
                empleadosListNewEmpleadosToAttach = em.getReference(empleadosListNewEmpleadosToAttach.getClass(), empleadosListNewEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosListNew.add(empleadosListNewEmpleadosToAttach);
            }
            empleadosListNew = attachedEmpleadosListNew;
            generos.setEmpleadosList(empleadosListNew);
            generos = em.merge(generos);
            for (Propietarios propietariosListNewPropietarios : propietariosListNew) {
                if (!propietariosListOld.contains(propietariosListNewPropietarios)) {
                    Generos oldIdGeneroOfPropietariosListNewPropietarios = propietariosListNewPropietarios.getIdGenero();
                    propietariosListNewPropietarios.setIdGenero(generos);
                    propietariosListNewPropietarios = em.merge(propietariosListNewPropietarios);
                    if (oldIdGeneroOfPropietariosListNewPropietarios != null && !oldIdGeneroOfPropietariosListNewPropietarios.equals(generos)) {
                        oldIdGeneroOfPropietariosListNewPropietarios.getPropietariosList().remove(propietariosListNewPropietarios);
                        oldIdGeneroOfPropietariosListNewPropietarios = em.merge(oldIdGeneroOfPropietariosListNewPropietarios);
                    }
                }
            }
            for (Empleados empleadosListNewEmpleados : empleadosListNew) {
                if (!empleadosListOld.contains(empleadosListNewEmpleados)) {
                    Generos oldIdGeneroOfEmpleadosListNewEmpleados = empleadosListNewEmpleados.getIdGenero();
                    empleadosListNewEmpleados.setIdGenero(generos);
                    empleadosListNewEmpleados = em.merge(empleadosListNewEmpleados);
                    if (oldIdGeneroOfEmpleadosListNewEmpleados != null && !oldIdGeneroOfEmpleadosListNewEmpleados.equals(generos)) {
                        oldIdGeneroOfEmpleadosListNewEmpleados.getEmpleadosList().remove(empleadosListNewEmpleados);
                        oldIdGeneroOfEmpleadosListNewEmpleados = em.merge(oldIdGeneroOfEmpleadosListNewEmpleados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = generos.getIdGenero();
                if (findGeneros(id) == null) {
                    throw new NonexistentEntityException("The generos with id " + id + " no longer exists.");
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
            Generos generos;
            try {
                generos = em.getReference(Generos.class, id);
                generos.getIdGenero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The generos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Propietarios> propietariosListOrphanCheck = generos.getPropietariosList();
            for (Propietarios propietariosListOrphanCheckPropietarios : propietariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Generos (" + generos + ") cannot be destroyed since the Propietarios " + propietariosListOrphanCheckPropietarios + " in its propietariosList field has a non-nullable idGenero field.");
            }
            List<Empleados> empleadosListOrphanCheck = generos.getEmpleadosList();
            for (Empleados empleadosListOrphanCheckEmpleados : empleadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Generos (" + generos + ") cannot be destroyed since the Empleados " + empleadosListOrphanCheckEmpleados + " in its empleadosList field has a non-nullable idGenero field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(generos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Generos> findGenerosEntities() {
        return findGenerosEntities(true, -1, -1);
    }

    public List<Generos> findGenerosEntities(int maxResults, int firstResult) {
        return findGenerosEntities(false, maxResults, firstResult);
    }

    private List<Generos> findGenerosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Generos.class));
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

    public Generos findGeneros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Generos.class, id);
        } finally {
            em.close();
        }
    }

    public int getGenerosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Generos> rt = cq.from(Generos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
