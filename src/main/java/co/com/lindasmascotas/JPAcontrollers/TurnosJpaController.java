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
import co.com.lindasmascotas.entities.DetalleTurnos;
import co.com.lindasmascotas.entities.Turnos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ISABEL MEDINA
 */
public class TurnosJpaController implements Serializable {

    public TurnosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Turnos turnos) {
        if (turnos.getDetalleTurnosList() == null) {
            turnos.setDetalleTurnosList(new ArrayList<DetalleTurnos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DetalleTurnos> attachedDetalleTurnosList = new ArrayList<DetalleTurnos>();
            for (DetalleTurnos detalleTurnosListDetalleTurnosToAttach : turnos.getDetalleTurnosList()) {
                detalleTurnosListDetalleTurnosToAttach = em.getReference(detalleTurnosListDetalleTurnosToAttach.getClass(), detalleTurnosListDetalleTurnosToAttach.getIdDetalleTurno());
                attachedDetalleTurnosList.add(detalleTurnosListDetalleTurnosToAttach);
            }
            turnos.setDetalleTurnosList(attachedDetalleTurnosList);
            em.persist(turnos);
            for (DetalleTurnos detalleTurnosListDetalleTurnos : turnos.getDetalleTurnosList()) {
                Turnos oldIdTurnoOfDetalleTurnosListDetalleTurnos = detalleTurnosListDetalleTurnos.getIdTurno();
                detalleTurnosListDetalleTurnos.setIdTurno(turnos);
                detalleTurnosListDetalleTurnos = em.merge(detalleTurnosListDetalleTurnos);
                if (oldIdTurnoOfDetalleTurnosListDetalleTurnos != null) {
                    oldIdTurnoOfDetalleTurnosListDetalleTurnos.getDetalleTurnosList().remove(detalleTurnosListDetalleTurnos);
                    oldIdTurnoOfDetalleTurnosListDetalleTurnos = em.merge(oldIdTurnoOfDetalleTurnosListDetalleTurnos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Turnos turnos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turnos persistentTurnos = em.find(Turnos.class, turnos.getIdTurno());
            List<DetalleTurnos> detalleTurnosListOld = persistentTurnos.getDetalleTurnosList();
            List<DetalleTurnos> detalleTurnosListNew = turnos.getDetalleTurnosList();
            List<String> illegalOrphanMessages = null;
            for (DetalleTurnos detalleTurnosListOldDetalleTurnos : detalleTurnosListOld) {
                if (!detalleTurnosListNew.contains(detalleTurnosListOldDetalleTurnos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleTurnos " + detalleTurnosListOldDetalleTurnos + " since its idTurno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<DetalleTurnos> attachedDetalleTurnosListNew = new ArrayList<DetalleTurnos>();
            for (DetalleTurnos detalleTurnosListNewDetalleTurnosToAttach : detalleTurnosListNew) {
                detalleTurnosListNewDetalleTurnosToAttach = em.getReference(detalleTurnosListNewDetalleTurnosToAttach.getClass(), detalleTurnosListNewDetalleTurnosToAttach.getIdDetalleTurno());
                attachedDetalleTurnosListNew.add(detalleTurnosListNewDetalleTurnosToAttach);
            }
            detalleTurnosListNew = attachedDetalleTurnosListNew;
            turnos.setDetalleTurnosList(detalleTurnosListNew);
            turnos = em.merge(turnos);
            for (DetalleTurnos detalleTurnosListNewDetalleTurnos : detalleTurnosListNew) {
                if (!detalleTurnosListOld.contains(detalleTurnosListNewDetalleTurnos)) {
                    Turnos oldIdTurnoOfDetalleTurnosListNewDetalleTurnos = detalleTurnosListNewDetalleTurnos.getIdTurno();
                    detalleTurnosListNewDetalleTurnos.setIdTurno(turnos);
                    detalleTurnosListNewDetalleTurnos = em.merge(detalleTurnosListNewDetalleTurnos);
                    if (oldIdTurnoOfDetalleTurnosListNewDetalleTurnos != null && !oldIdTurnoOfDetalleTurnosListNewDetalleTurnos.equals(turnos)) {
                        oldIdTurnoOfDetalleTurnosListNewDetalleTurnos.getDetalleTurnosList().remove(detalleTurnosListNewDetalleTurnos);
                        oldIdTurnoOfDetalleTurnosListNewDetalleTurnos = em.merge(oldIdTurnoOfDetalleTurnosListNewDetalleTurnos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = turnos.getIdTurno();
                if (findTurnos(id) == null) {
                    throw new NonexistentEntityException("The turnos with id " + id + " no longer exists.");
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
            Turnos turnos;
            try {
                turnos = em.getReference(Turnos.class, id);
                turnos.getIdTurno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The turnos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleTurnos> detalleTurnosListOrphanCheck = turnos.getDetalleTurnosList();
            for (DetalleTurnos detalleTurnosListOrphanCheckDetalleTurnos : detalleTurnosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Turnos (" + turnos + ") cannot be destroyed since the DetalleTurnos " + detalleTurnosListOrphanCheckDetalleTurnos + " in its detalleTurnosList field has a non-nullable idTurno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(turnos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Turnos> findTurnosEntities() {
        return findTurnosEntities(true, -1, -1);
    }

    public List<Turnos> findTurnosEntities(int maxResults, int firstResult) {
        return findTurnosEntities(false, maxResults, firstResult);
    }

    private List<Turnos> findTurnosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Turnos.class));
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

    public Turnos findTurnos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Turnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTurnosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Turnos> rt = cq.from(Turnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
