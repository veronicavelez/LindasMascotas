/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.JPAcontrollers;

import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Especies;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.lindasmascotas.entities.Mascotas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ISABEL MEDINA
 */
public class EspeciesJpaController implements Serializable {

    public EspeciesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Especies especies) {
        if (especies.getMascotasList() == null) {
            especies.setMascotasList(new ArrayList<Mascotas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Mascotas> attachedMascotasList = new ArrayList<Mascotas>();
            for (Mascotas mascotasListMascotasToAttach : especies.getMascotasList()) {
                mascotasListMascotasToAttach = em.getReference(mascotasListMascotasToAttach.getClass(), mascotasListMascotasToAttach.getIdMascota());
                attachedMascotasList.add(mascotasListMascotasToAttach);
            }
            especies.setMascotasList(attachedMascotasList);
            em.persist(especies);
            for (Mascotas mascotasListMascotas : especies.getMascotasList()) {
                Especies oldIdEspecieOfMascotasListMascotas = mascotasListMascotas.getIdEspecie();
                mascotasListMascotas.setIdEspecie(especies);
                mascotasListMascotas = em.merge(mascotasListMascotas);
                if (oldIdEspecieOfMascotasListMascotas != null) {
                    oldIdEspecieOfMascotasListMascotas.getMascotasList().remove(mascotasListMascotas);
                    oldIdEspecieOfMascotasListMascotas = em.merge(oldIdEspecieOfMascotasListMascotas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Especies especies) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especies persistentEspecies = em.find(Especies.class, especies.getIdEspecie());
            List<Mascotas> mascotasListOld = persistentEspecies.getMascotasList();
            List<Mascotas> mascotasListNew = especies.getMascotasList();
            List<String> illegalOrphanMessages = null;
            for (Mascotas mascotasListOldMascotas : mascotasListOld) {
                if (!mascotasListNew.contains(mascotasListOldMascotas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mascotas " + mascotasListOldMascotas + " since its idEspecie field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Mascotas> attachedMascotasListNew = new ArrayList<Mascotas>();
            for (Mascotas mascotasListNewMascotasToAttach : mascotasListNew) {
                mascotasListNewMascotasToAttach = em.getReference(mascotasListNewMascotasToAttach.getClass(), mascotasListNewMascotasToAttach.getIdMascota());
                attachedMascotasListNew.add(mascotasListNewMascotasToAttach);
            }
            mascotasListNew = attachedMascotasListNew;
            especies.setMascotasList(mascotasListNew);
            especies = em.merge(especies);
            for (Mascotas mascotasListNewMascotas : mascotasListNew) {
                if (!mascotasListOld.contains(mascotasListNewMascotas)) {
                    Especies oldIdEspecieOfMascotasListNewMascotas = mascotasListNewMascotas.getIdEspecie();
                    mascotasListNewMascotas.setIdEspecie(especies);
                    mascotasListNewMascotas = em.merge(mascotasListNewMascotas);
                    if (oldIdEspecieOfMascotasListNewMascotas != null && !oldIdEspecieOfMascotasListNewMascotas.equals(especies)) {
                        oldIdEspecieOfMascotasListNewMascotas.getMascotasList().remove(mascotasListNewMascotas);
                        oldIdEspecieOfMascotasListNewMascotas = em.merge(oldIdEspecieOfMascotasListNewMascotas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = especies.getIdEspecie();
                if (findEspecies(id) == null) {
                    throw new NonexistentEntityException("The especies with id " + id + " no longer exists.");
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
            Especies especies;
            try {
                especies = em.getReference(Especies.class, id);
                especies.getIdEspecie();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The especies with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Mascotas> mascotasListOrphanCheck = especies.getMascotasList();
            for (Mascotas mascotasListOrphanCheckMascotas : mascotasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Especies (" + especies + ") cannot be destroyed since the Mascotas " + mascotasListOrphanCheckMascotas + " in its mascotasList field has a non-nullable idEspecie field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(especies);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Especies> findEspeciesEntities() {
        return findEspeciesEntities(true, -1, -1);
    }

    public List<Especies> findEspeciesEntities(int maxResults, int firstResult) {
        return findEspeciesEntities(false, maxResults, firstResult);
    }

    private List<Especies> findEspeciesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Especies.class));
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

    public Especies findEspecies(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Especies.class, id);
        } finally {
            em.close();
        }
    }

    public int getEspeciesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Especies> rt = cq.from(Especies.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
