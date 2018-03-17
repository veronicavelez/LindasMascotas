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
import co.com.lindasmascotas.entities.Departamentos;
import co.com.lindasmascotas.entities.Paises;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Veronica
 */
public class PaisesJpaController implements Serializable {

    public PaisesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paises paises) throws PreexistingEntityException, Exception {
        if (paises.getDepartamentosList() == null) {
            paises.setDepartamentosList(new ArrayList<Departamentos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Departamentos> attachedDepartamentosList = new ArrayList<Departamentos>();
            for (Departamentos departamentosListDepartamentosToAttach : paises.getDepartamentosList()) {
                departamentosListDepartamentosToAttach = em.getReference(departamentosListDepartamentosToAttach.getClass(), departamentosListDepartamentosToAttach.getIdDepartamento());
                attachedDepartamentosList.add(departamentosListDepartamentosToAttach);
            }
            paises.setDepartamentosList(attachedDepartamentosList);
            em.persist(paises);
            for (Departamentos departamentosListDepartamentos : paises.getDepartamentosList()) {
                Paises oldIdPaisOfDepartamentosListDepartamentos = departamentosListDepartamentos.getIdPais();
                departamentosListDepartamentos.setIdPais(paises);
                departamentosListDepartamentos = em.merge(departamentosListDepartamentos);
                if (oldIdPaisOfDepartamentosListDepartamentos != null) {
                    oldIdPaisOfDepartamentosListDepartamentos.getDepartamentosList().remove(departamentosListDepartamentos);
                    oldIdPaisOfDepartamentosListDepartamentos = em.merge(oldIdPaisOfDepartamentosListDepartamentos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPaises(paises.getIdPais()) != null) {
                throw new PreexistingEntityException("Paises " + paises + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paises paises) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paises persistentPaises = em.find(Paises.class, paises.getIdPais());
            List<Departamentos> departamentosListOld = persistentPaises.getDepartamentosList();
            List<Departamentos> departamentosListNew = paises.getDepartamentosList();
            List<String> illegalOrphanMessages = null;
            for (Departamentos departamentosListOldDepartamentos : departamentosListOld) {
                if (!departamentosListNew.contains(departamentosListOldDepartamentos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Departamentos " + departamentosListOldDepartamentos + " since its idPais field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Departamentos> attachedDepartamentosListNew = new ArrayList<Departamentos>();
            for (Departamentos departamentosListNewDepartamentosToAttach : departamentosListNew) {
                departamentosListNewDepartamentosToAttach = em.getReference(departamentosListNewDepartamentosToAttach.getClass(), departamentosListNewDepartamentosToAttach.getIdDepartamento());
                attachedDepartamentosListNew.add(departamentosListNewDepartamentosToAttach);
            }
            departamentosListNew = attachedDepartamentosListNew;
            paises.setDepartamentosList(departamentosListNew);
            paises = em.merge(paises);
            for (Departamentos departamentosListNewDepartamentos : departamentosListNew) {
                if (!departamentosListOld.contains(departamentosListNewDepartamentos)) {
                    Paises oldIdPaisOfDepartamentosListNewDepartamentos = departamentosListNewDepartamentos.getIdPais();
                    departamentosListNewDepartamentos.setIdPais(paises);
                    departamentosListNewDepartamentos = em.merge(departamentosListNewDepartamentos);
                    if (oldIdPaisOfDepartamentosListNewDepartamentos != null && !oldIdPaisOfDepartamentosListNewDepartamentos.equals(paises)) {
                        oldIdPaisOfDepartamentosListNewDepartamentos.getDepartamentosList().remove(departamentosListNewDepartamentos);
                        oldIdPaisOfDepartamentosListNewDepartamentos = em.merge(oldIdPaisOfDepartamentosListNewDepartamentos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = paises.getIdPais();
                if (findPaises(id) == null) {
                    throw new NonexistentEntityException("The paises with id " + id + " no longer exists.");
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
            Paises paises;
            try {
                paises = em.getReference(Paises.class, id);
                paises.getIdPais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paises with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Departamentos> departamentosListOrphanCheck = paises.getDepartamentosList();
            for (Departamentos departamentosListOrphanCheckDepartamentos : departamentosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paises (" + paises + ") cannot be destroyed since the Departamentos " + departamentosListOrphanCheckDepartamentos + " in its departamentosList field has a non-nullable idPais field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(paises);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paises> findPaisesEntities() {
        return findPaisesEntities(true, -1, -1);
    }

    public List<Paises> findPaisesEntities(int maxResults, int firstResult) {
        return findPaisesEntities(false, maxResults, firstResult);
    }

    private List<Paises> findPaisesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paises.class));
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

    public Paises findPaises(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paises.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paises> rt = cq.from(Paises.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
