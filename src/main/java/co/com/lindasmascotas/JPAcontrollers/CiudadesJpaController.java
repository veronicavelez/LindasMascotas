/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.JPAcontrollers;

import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.PreexistingEntityException;
import co.com.lindasmascotas.entities.Ciudades;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.lindasmascotas.entities.Departamentos;
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
public class CiudadesJpaController implements Serializable {

    public CiudadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudades ciudades) throws PreexistingEntityException, Exception {
        if (ciudades.getPropietariosList() == null) {
            ciudades.setPropietariosList(new ArrayList<Propietarios>());
        }
        if (ciudades.getEmpleadosList() == null) {
            ciudades.setEmpleadosList(new ArrayList<Empleados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamentos idDpto = ciudades.getIdDpto();
            if (idDpto != null) {
                idDpto = em.getReference(idDpto.getClass(), idDpto.getIdDepartamento());
                ciudades.setIdDpto(idDpto);
            }
            List<Propietarios> attachedPropietariosList = new ArrayList<Propietarios>();
            for (Propietarios propietariosListPropietariosToAttach : ciudades.getPropietariosList()) {
                propietariosListPropietariosToAttach = em.getReference(propietariosListPropietariosToAttach.getClass(), propietariosListPropietariosToAttach.getIdPropietario());
                attachedPropietariosList.add(propietariosListPropietariosToAttach);
            }
            ciudades.setPropietariosList(attachedPropietariosList);
            List<Empleados> attachedEmpleadosList = new ArrayList<Empleados>();
            for (Empleados empleadosListEmpleadosToAttach : ciudades.getEmpleadosList()) {
                empleadosListEmpleadosToAttach = em.getReference(empleadosListEmpleadosToAttach.getClass(), empleadosListEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosList.add(empleadosListEmpleadosToAttach);
            }
            ciudades.setEmpleadosList(attachedEmpleadosList);
            em.persist(ciudades);
            if (idDpto != null) {
                idDpto.getCiudadesList().add(ciudades);
                idDpto = em.merge(idDpto);
            }
            for (Propietarios propietariosListPropietarios : ciudades.getPropietariosList()) {
                Ciudades oldIdCiudadOfPropietariosListPropietarios = propietariosListPropietarios.getIdCiudad();
                propietariosListPropietarios.setIdCiudad(ciudades);
                propietariosListPropietarios = em.merge(propietariosListPropietarios);
                if (oldIdCiudadOfPropietariosListPropietarios != null) {
                    oldIdCiudadOfPropietariosListPropietarios.getPropietariosList().remove(propietariosListPropietarios);
                    oldIdCiudadOfPropietariosListPropietarios = em.merge(oldIdCiudadOfPropietariosListPropietarios);
                }
            }
            for (Empleados empleadosListEmpleados : ciudades.getEmpleadosList()) {
                Ciudades oldIdCiudadOfEmpleadosListEmpleados = empleadosListEmpleados.getIdCiudad();
                empleadosListEmpleados.setIdCiudad(ciudades);
                empleadosListEmpleados = em.merge(empleadosListEmpleados);
                if (oldIdCiudadOfEmpleadosListEmpleados != null) {
                    oldIdCiudadOfEmpleadosListEmpleados.getEmpleadosList().remove(empleadosListEmpleados);
                    oldIdCiudadOfEmpleadosListEmpleados = em.merge(oldIdCiudadOfEmpleadosListEmpleados);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCiudades(ciudades.getIdCiudad()) != null) {
                throw new PreexistingEntityException("Ciudades " + ciudades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ciudades ciudades) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudades persistentCiudades = em.find(Ciudades.class, ciudades.getIdCiudad());
            Departamentos idDptoOld = persistentCiudades.getIdDpto();
            Departamentos idDptoNew = ciudades.getIdDpto();
            List<Propietarios> propietariosListOld = persistentCiudades.getPropietariosList();
            List<Propietarios> propietariosListNew = ciudades.getPropietariosList();
            List<Empleados> empleadosListOld = persistentCiudades.getEmpleadosList();
            List<Empleados> empleadosListNew = ciudades.getEmpleadosList();
            List<String> illegalOrphanMessages = null;
            for (Propietarios propietariosListOldPropietarios : propietariosListOld) {
                if (!propietariosListNew.contains(propietariosListOldPropietarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propietarios " + propietariosListOldPropietarios + " since its idCiudad field is not nullable.");
                }
            }
            for (Empleados empleadosListOldEmpleados : empleadosListOld) {
                if (!empleadosListNew.contains(empleadosListOldEmpleados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleados " + empleadosListOldEmpleados + " since its idCiudad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idDptoNew != null) {
                idDptoNew = em.getReference(idDptoNew.getClass(), idDptoNew.getIdDepartamento());
                ciudades.setIdDpto(idDptoNew);
            }
            List<Propietarios> attachedPropietariosListNew = new ArrayList<Propietarios>();
            for (Propietarios propietariosListNewPropietariosToAttach : propietariosListNew) {
                propietariosListNewPropietariosToAttach = em.getReference(propietariosListNewPropietariosToAttach.getClass(), propietariosListNewPropietariosToAttach.getIdPropietario());
                attachedPropietariosListNew.add(propietariosListNewPropietariosToAttach);
            }
            propietariosListNew = attachedPropietariosListNew;
            ciudades.setPropietariosList(propietariosListNew);
            List<Empleados> attachedEmpleadosListNew = new ArrayList<Empleados>();
            for (Empleados empleadosListNewEmpleadosToAttach : empleadosListNew) {
                empleadosListNewEmpleadosToAttach = em.getReference(empleadosListNewEmpleadosToAttach.getClass(), empleadosListNewEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosListNew.add(empleadosListNewEmpleadosToAttach);
            }
            empleadosListNew = attachedEmpleadosListNew;
            ciudades.setEmpleadosList(empleadosListNew);
            ciudades = em.merge(ciudades);
            if (idDptoOld != null && !idDptoOld.equals(idDptoNew)) {
                idDptoOld.getCiudadesList().remove(ciudades);
                idDptoOld = em.merge(idDptoOld);
            }
            if (idDptoNew != null && !idDptoNew.equals(idDptoOld)) {
                idDptoNew.getCiudadesList().add(ciudades);
                idDptoNew = em.merge(idDptoNew);
            }
            for (Propietarios propietariosListNewPropietarios : propietariosListNew) {
                if (!propietariosListOld.contains(propietariosListNewPropietarios)) {
                    Ciudades oldIdCiudadOfPropietariosListNewPropietarios = propietariosListNewPropietarios.getIdCiudad();
                    propietariosListNewPropietarios.setIdCiudad(ciudades);
                    propietariosListNewPropietarios = em.merge(propietariosListNewPropietarios);
                    if (oldIdCiudadOfPropietariosListNewPropietarios != null && !oldIdCiudadOfPropietariosListNewPropietarios.equals(ciudades)) {
                        oldIdCiudadOfPropietariosListNewPropietarios.getPropietariosList().remove(propietariosListNewPropietarios);
                        oldIdCiudadOfPropietariosListNewPropietarios = em.merge(oldIdCiudadOfPropietariosListNewPropietarios);
                    }
                }
            }
            for (Empleados empleadosListNewEmpleados : empleadosListNew) {
                if (!empleadosListOld.contains(empleadosListNewEmpleados)) {
                    Ciudades oldIdCiudadOfEmpleadosListNewEmpleados = empleadosListNewEmpleados.getIdCiudad();
                    empleadosListNewEmpleados.setIdCiudad(ciudades);
                    empleadosListNewEmpleados = em.merge(empleadosListNewEmpleados);
                    if (oldIdCiudadOfEmpleadosListNewEmpleados != null && !oldIdCiudadOfEmpleadosListNewEmpleados.equals(ciudades)) {
                        oldIdCiudadOfEmpleadosListNewEmpleados.getEmpleadosList().remove(empleadosListNewEmpleados);
                        oldIdCiudadOfEmpleadosListNewEmpleados = em.merge(oldIdCiudadOfEmpleadosListNewEmpleados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ciudades.getIdCiudad();
                if (findCiudades(id) == null) {
                    throw new NonexistentEntityException("The ciudades with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudades ciudades;
            try {
                ciudades = em.getReference(Ciudades.class, id);
                ciudades.getIdCiudad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudades with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Propietarios> propietariosListOrphanCheck = ciudades.getPropietariosList();
            for (Propietarios propietariosListOrphanCheckPropietarios : propietariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudades (" + ciudades + ") cannot be destroyed since the Propietarios " + propietariosListOrphanCheckPropietarios + " in its propietariosList field has a non-nullable idCiudad field.");
            }
            List<Empleados> empleadosListOrphanCheck = ciudades.getEmpleadosList();
            for (Empleados empleadosListOrphanCheckEmpleados : empleadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudades (" + ciudades + ") cannot be destroyed since the Empleados " + empleadosListOrphanCheckEmpleados + " in its empleadosList field has a non-nullable idCiudad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamentos idDpto = ciudades.getIdDpto();
            if (idDpto != null) {
                idDpto.getCiudadesList().remove(ciudades);
                idDpto = em.merge(idDpto);
            }
            em.remove(ciudades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ciudades> findCiudadesEntities() {
        return findCiudadesEntities(true, -1, -1);
    }

    public List<Ciudades> findCiudadesEntities(int maxResults, int firstResult) {
        return findCiudadesEntities(false, maxResults, firstResult);
    }

    private List<Ciudades> findCiudadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudades.class));
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

    public Ciudades findCiudades(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudades.class, id);
        } finally {
            em.close();
        }
    }

    public int getCiudadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudades> rt = cq.from(Ciudades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Ciudades> findCiudadByDepartamento(Departamentos departamento){
        EntityManager em = getEntityManager();
        
        try {
            Query q = em.createNamedQuery("Ciudades.findCiudadByDepartamento");
            q.setParameter("idDepartamento", departamento);
            
            return q.getResultList();
            
        } catch (Exception e) {
        }finally{
            em.close();
}
        
        return null;
    }
    
}
