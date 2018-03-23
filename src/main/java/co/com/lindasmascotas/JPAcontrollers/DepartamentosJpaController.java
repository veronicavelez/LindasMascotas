/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.JPAcontrollers;

import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.lindasmascotas.entities.Paises;
import co.com.lindasmascotas.entities.Ciudades;
import co.com.lindasmascotas.entities.Departamentos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Veronica
 */
public class DepartamentosJpaController implements Serializable {

    public DepartamentosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamentos departamentos) throws PreexistingEntityException, Exception {
        if (departamentos.getCiudadesList() == null) {
            departamentos.setCiudadesList(new ArrayList<Ciudades>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paises idPais = departamentos.getIdPais();
            if (idPais != null) {
                idPais = em.getReference(idPais.getClass(), idPais.getIdPais());
                departamentos.setIdPais(idPais);
            }
            List<Ciudades> attachedCiudadesList = new ArrayList<Ciudades>();
            for (Ciudades ciudadesListCiudadesToAttach : departamentos.getCiudadesList()) {
                ciudadesListCiudadesToAttach = em.getReference(ciudadesListCiudadesToAttach.getClass(), ciudadesListCiudadesToAttach.getIdCiudad());
                attachedCiudadesList.add(ciudadesListCiudadesToAttach);
            }
            departamentos.setCiudadesList(attachedCiudadesList);
            em.persist(departamentos);
            if (idPais != null) {
                idPais.getDepartamentosList().add(departamentos);
                idPais = em.merge(idPais);
            }
            for (Ciudades ciudadesListCiudades : departamentos.getCiudadesList()) {
                Departamentos oldIdDptoOfCiudadesListCiudades = ciudadesListCiudades.getIdDpto();
                ciudadesListCiudades.setIdDpto(departamentos);
                ciudadesListCiudades = em.merge(ciudadesListCiudades);
                if (oldIdDptoOfCiudadesListCiudades != null) {
                    oldIdDptoOfCiudadesListCiudades.getCiudadesList().remove(ciudadesListCiudades);
                    oldIdDptoOfCiudadesListCiudades = em.merge(oldIdDptoOfCiudadesListCiudades);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDepartamentos(departamentos.getIdDepartamento()) != null) {
                throw new PreexistingEntityException("Departamentos " + departamentos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamentos departamentos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamentos persistentDepartamentos = em.find(Departamentos.class, departamentos.getIdDepartamento());
            Paises idPaisOld = persistentDepartamentos.getIdPais();
            Paises idPaisNew = departamentos.getIdPais();
            List<Ciudades> ciudadesListOld = persistentDepartamentos.getCiudadesList();
            List<Ciudades> ciudadesListNew = departamentos.getCiudadesList();
            List<String> illegalOrphanMessages = null;
            for (Ciudades ciudadesListOldCiudades : ciudadesListOld) {
                if (!ciudadesListNew.contains(ciudadesListOldCiudades)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ciudades " + ciudadesListOldCiudades + " since its idDpto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPaisNew != null) {
                idPaisNew = em.getReference(idPaisNew.getClass(), idPaisNew.getIdPais());
                departamentos.setIdPais(idPaisNew);
            }
            List<Ciudades> attachedCiudadesListNew = new ArrayList<Ciudades>();
            for (Ciudades ciudadesListNewCiudadesToAttach : ciudadesListNew) {
                ciudadesListNewCiudadesToAttach = em.getReference(ciudadesListNewCiudadesToAttach.getClass(), ciudadesListNewCiudadesToAttach.getIdCiudad());
                attachedCiudadesListNew.add(ciudadesListNewCiudadesToAttach);
            }
            ciudadesListNew = attachedCiudadesListNew;
            departamentos.setCiudadesList(ciudadesListNew);
            departamentos = em.merge(departamentos);
            if (idPaisOld != null && !idPaisOld.equals(idPaisNew)) {
                idPaisOld.getDepartamentosList().remove(departamentos);
                idPaisOld = em.merge(idPaisOld);
            }
            if (idPaisNew != null && !idPaisNew.equals(idPaisOld)) {
                idPaisNew.getDepartamentosList().add(departamentos);
                idPaisNew = em.merge(idPaisNew);
            }
            for (Ciudades ciudadesListNewCiudades : ciudadesListNew) {
                if (!ciudadesListOld.contains(ciudadesListNewCiudades)) {
                    Departamentos oldIdDptoOfCiudadesListNewCiudades = ciudadesListNewCiudades.getIdDpto();
                    ciudadesListNewCiudades.setIdDpto(departamentos);
                    ciudadesListNewCiudades = em.merge(ciudadesListNewCiudades);
                    if (oldIdDptoOfCiudadesListNewCiudades != null && !oldIdDptoOfCiudadesListNewCiudades.equals(departamentos)) {
                        oldIdDptoOfCiudadesListNewCiudades.getCiudadesList().remove(ciudadesListNewCiudades);
                        oldIdDptoOfCiudadesListNewCiudades = em.merge(oldIdDptoOfCiudadesListNewCiudades);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = departamentos.getIdDepartamento();
                if (findDepartamentos(id) == null) {
                    throw new NonexistentEntityException("The departamentos with id " + id + " no longer exists.");
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
            Departamentos departamentos;
            try {
                departamentos = em.getReference(Departamentos.class, id);
                departamentos.getIdDepartamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamentos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ciudades> ciudadesListOrphanCheck = departamentos.getCiudadesList();
            for (Ciudades ciudadesListOrphanCheckCiudades : ciudadesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamentos (" + departamentos + ") cannot be destroyed since the Ciudades " + ciudadesListOrphanCheckCiudades + " in its ciudadesList field has a non-nullable idDpto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Paises idPais = departamentos.getIdPais();
            if (idPais != null) {
                idPais.getDepartamentosList().remove(departamentos);
                idPais = em.merge(idPais);
            }
            em.remove(departamentos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamentos> findDepartamentosEntities() {
        return findDepartamentosEntities(true, -1, -1);
    }

    public List<Departamentos> findDepartamentosEntities(int maxResults, int firstResult) {
        return findDepartamentosEntities(false, maxResults, firstResult);
    }

    private List<Departamentos> findDepartamentosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamentos.class));
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

    public Departamentos findDepartamentos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamentos.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamentos> rt = cq.from(Departamentos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
public List<Departamentos> findDepartamentoByPais(Paises pais){
        EntityManager em = getEntityManager();
        
        try {
            Query q = em.createNamedQuery("Departamentos.findDepartamentoByPais");
            q.setParameter("idPais", pais);
            
            return q.getResultList();
            
        } catch (Exception e) {
        }finally{
            em.close();
        }
        
        return null;
    }
    
}
