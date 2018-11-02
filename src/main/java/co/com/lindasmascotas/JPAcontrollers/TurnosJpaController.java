/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.JPAcontrollers;

import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.lindasmascotas.entities.Detalleturnos;
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
        if (turnos.getDetalleturnosList() == null) {
            turnos.setDetalleturnosList(new ArrayList<Detalleturnos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Detalleturnos> attachedDetalleturnosList = new ArrayList<Detalleturnos>();
            for (Detalleturnos detalleturnosListDetalleturnosToAttach : turnos.getDetalleturnosList()) {
                detalleturnosListDetalleturnosToAttach = em.getReference(detalleturnosListDetalleturnosToAttach.getClass(), detalleturnosListDetalleturnosToAttach.getIdDetalleTurno());
                attachedDetalleturnosList.add(detalleturnosListDetalleturnosToAttach);
            }
            turnos.setDetalleturnosList(attachedDetalleturnosList);
            em.persist(turnos);
            for (Detalleturnos detalleturnosListDetalleturnos : turnos.getDetalleturnosList()) {
                Turnos oldIdTurnoOfDetalleturnosListDetalleturnos = detalleturnosListDetalleturnos.getIdTurno();
                detalleturnosListDetalleturnos.setIdTurno(turnos);
                detalleturnosListDetalleturnos = em.merge(detalleturnosListDetalleturnos);
                if (oldIdTurnoOfDetalleturnosListDetalleturnos != null) {
                    oldIdTurnoOfDetalleturnosListDetalleturnos.getDetalleturnosList().remove(detalleturnosListDetalleturnos);
                    oldIdTurnoOfDetalleturnosListDetalleturnos = em.merge(oldIdTurnoOfDetalleturnosListDetalleturnos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Turnos turnos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turnos persistentTurnos = em.find(Turnos.class, turnos.getIdTurno());
            List<Detalleturnos> detalleturnosListOld = persistentTurnos.getDetalleturnosList();
            List<Detalleturnos> detalleturnosListNew = turnos.getDetalleturnosList();
            List<Detalleturnos> attachedDetalleturnosListNew = new ArrayList<Detalleturnos>();
            for (Detalleturnos detalleturnosListNewDetalleturnosToAttach : detalleturnosListNew) {
                detalleturnosListNewDetalleturnosToAttach = em.getReference(detalleturnosListNewDetalleturnosToAttach.getClass(), detalleturnosListNewDetalleturnosToAttach.getIdDetalleTurno());
                attachedDetalleturnosListNew.add(detalleturnosListNewDetalleturnosToAttach);
            }
            detalleturnosListNew = attachedDetalleturnosListNew;
            turnos.setDetalleturnosList(detalleturnosListNew);
            turnos = em.merge(turnos);
            for (Detalleturnos detalleturnosListOldDetalleturnos : detalleturnosListOld) {
                if (!detalleturnosListNew.contains(detalleturnosListOldDetalleturnos)) {
                    detalleturnosListOldDetalleturnos.setIdTurno(null);
                    detalleturnosListOldDetalleturnos = em.merge(detalleturnosListOldDetalleturnos);
                }
            }
            for (Detalleturnos detalleturnosListNewDetalleturnos : detalleturnosListNew) {
                if (!detalleturnosListOld.contains(detalleturnosListNewDetalleturnos)) {
                    Turnos oldIdTurnoOfDetalleturnosListNewDetalleturnos = detalleturnosListNewDetalleturnos.getIdTurno();
                    detalleturnosListNewDetalleturnos.setIdTurno(turnos);
                    detalleturnosListNewDetalleturnos = em.merge(detalleturnosListNewDetalleturnos);
                    if (oldIdTurnoOfDetalleturnosListNewDetalleturnos != null && !oldIdTurnoOfDetalleturnosListNewDetalleturnos.equals(turnos)) {
                        oldIdTurnoOfDetalleturnosListNewDetalleturnos.getDetalleturnosList().remove(detalleturnosListNewDetalleturnos);
                        oldIdTurnoOfDetalleturnosListNewDetalleturnos = em.merge(oldIdTurnoOfDetalleturnosListNewDetalleturnos);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            List<Detalleturnos> detalleturnosList = turnos.getDetalleturnosList();
            for (Detalleturnos detalleturnosListDetalleturnos : detalleturnosList) {
                detalleturnosListDetalleturnos.setIdTurno(null);
                detalleturnosListDetalleturnos = em.merge(detalleturnosListDetalleturnos);
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
