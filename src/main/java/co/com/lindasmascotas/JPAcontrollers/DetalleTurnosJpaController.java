/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.JPAcontrollers;

import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.PreexistingEntityException;
import co.com.lindasmascotas.entities.DetalleTurnos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.lindasmascotas.entities.Turnos;
import co.com.lindasmascotas.entities.TurnosPorEmpleados;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ISABEL MEDINA
 */
public class DetalleTurnosJpaController implements Serializable {

    public DetalleTurnosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleTurnos detalleTurnos) throws PreexistingEntityException, Exception {
        if (detalleTurnos.getTurnosPorEmpleadosList() == null) {
            detalleTurnos.setTurnosPorEmpleadosList(new ArrayList<TurnosPorEmpleados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turnos idTurno = detalleTurnos.getIdTurno();
            if (idTurno != null) {
                idTurno = em.getReference(idTurno.getClass(), idTurno.getIdTurno());
                detalleTurnos.setIdTurno(idTurno);
            }
            List<TurnosPorEmpleados> attachedTurnosPorEmpleadosList = new ArrayList<TurnosPorEmpleados>();
            for (TurnosPorEmpleados turnosPorEmpleadosListTurnosPorEmpleadosToAttach : detalleTurnos.getTurnosPorEmpleadosList()) {
                turnosPorEmpleadosListTurnosPorEmpleadosToAttach = em.getReference(turnosPorEmpleadosListTurnosPorEmpleadosToAttach.getClass(), turnosPorEmpleadosListTurnosPorEmpleadosToAttach.getIdTurnosPorEmpl());
                attachedTurnosPorEmpleadosList.add(turnosPorEmpleadosListTurnosPorEmpleadosToAttach);
            }
            detalleTurnos.setTurnosPorEmpleadosList(attachedTurnosPorEmpleadosList);
            em.persist(detalleTurnos);
            if (idTurno != null) {
                idTurno.getDetalleTurnosList().add(detalleTurnos);
                idTurno = em.merge(idTurno);
            }
            for (TurnosPorEmpleados turnosPorEmpleadosListTurnosPorEmpleados : detalleTurnos.getTurnosPorEmpleadosList()) {
                DetalleTurnos oldIdDetalleTurnosOfTurnosPorEmpleadosListTurnosPorEmpleados = turnosPorEmpleadosListTurnosPorEmpleados.getIdDetalleTurnos();
                turnosPorEmpleadosListTurnosPorEmpleados.setIdDetalleTurnos(detalleTurnos);
                turnosPorEmpleadosListTurnosPorEmpleados = em.merge(turnosPorEmpleadosListTurnosPorEmpleados);
                if (oldIdDetalleTurnosOfTurnosPorEmpleadosListTurnosPorEmpleados != null) {
                    oldIdDetalleTurnosOfTurnosPorEmpleadosListTurnosPorEmpleados.getTurnosPorEmpleadosList().remove(turnosPorEmpleadosListTurnosPorEmpleados);
                    oldIdDetalleTurnosOfTurnosPorEmpleadosListTurnosPorEmpleados = em.merge(oldIdDetalleTurnosOfTurnosPorEmpleadosListTurnosPorEmpleados);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetalleTurnos(detalleTurnos.getIdDetalleTurno()) != null) {
                throw new PreexistingEntityException("DetalleTurnos " + detalleTurnos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleTurnos detalleTurnos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleTurnos persistentDetalleTurnos = em.find(DetalleTurnos.class, detalleTurnos.getIdDetalleTurno());
            Turnos idTurnoOld = persistentDetalleTurnos.getIdTurno();
            Turnos idTurnoNew = detalleTurnos.getIdTurno();
            List<TurnosPorEmpleados> turnosPorEmpleadosListOld = persistentDetalleTurnos.getTurnosPorEmpleadosList();
            List<TurnosPorEmpleados> turnosPorEmpleadosListNew = detalleTurnos.getTurnosPorEmpleadosList();
            List<String> illegalOrphanMessages = null;
            for (TurnosPorEmpleados turnosPorEmpleadosListOldTurnosPorEmpleados : turnosPorEmpleadosListOld) {
                if (!turnosPorEmpleadosListNew.contains(turnosPorEmpleadosListOldTurnosPorEmpleados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TurnosPorEmpleados " + turnosPorEmpleadosListOldTurnosPorEmpleados + " since its idDetalleTurnos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTurnoNew != null) {
                idTurnoNew = em.getReference(idTurnoNew.getClass(), idTurnoNew.getIdTurno());
                detalleTurnos.setIdTurno(idTurnoNew);
            }
            List<TurnosPorEmpleados> attachedTurnosPorEmpleadosListNew = new ArrayList<TurnosPorEmpleados>();
            for (TurnosPorEmpleados turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach : turnosPorEmpleadosListNew) {
                turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach = em.getReference(turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach.getClass(), turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach.getIdTurnosPorEmpl());
                attachedTurnosPorEmpleadosListNew.add(turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach);
            }
            turnosPorEmpleadosListNew = attachedTurnosPorEmpleadosListNew;
            detalleTurnos.setTurnosPorEmpleadosList(turnosPorEmpleadosListNew);
            detalleTurnos = em.merge(detalleTurnos);
            if (idTurnoOld != null && !idTurnoOld.equals(idTurnoNew)) {
                idTurnoOld.getDetalleTurnosList().remove(detalleTurnos);
                idTurnoOld = em.merge(idTurnoOld);
            }
            if (idTurnoNew != null && !idTurnoNew.equals(idTurnoOld)) {
                idTurnoNew.getDetalleTurnosList().add(detalleTurnos);
                idTurnoNew = em.merge(idTurnoNew);
            }
            for (TurnosPorEmpleados turnosPorEmpleadosListNewTurnosPorEmpleados : turnosPorEmpleadosListNew) {
                if (!turnosPorEmpleadosListOld.contains(turnosPorEmpleadosListNewTurnosPorEmpleados)) {
                    DetalleTurnos oldIdDetalleTurnosOfTurnosPorEmpleadosListNewTurnosPorEmpleados = turnosPorEmpleadosListNewTurnosPorEmpleados.getIdDetalleTurnos();
                    turnosPorEmpleadosListNewTurnosPorEmpleados.setIdDetalleTurnos(detalleTurnos);
                    turnosPorEmpleadosListNewTurnosPorEmpleados = em.merge(turnosPorEmpleadosListNewTurnosPorEmpleados);
                    if (oldIdDetalleTurnosOfTurnosPorEmpleadosListNewTurnosPorEmpleados != null && !oldIdDetalleTurnosOfTurnosPorEmpleadosListNewTurnosPorEmpleados.equals(detalleTurnos)) {
                        oldIdDetalleTurnosOfTurnosPorEmpleadosListNewTurnosPorEmpleados.getTurnosPorEmpleadosList().remove(turnosPorEmpleadosListNewTurnosPorEmpleados);
                        oldIdDetalleTurnosOfTurnosPorEmpleadosListNewTurnosPorEmpleados = em.merge(oldIdDetalleTurnosOfTurnosPorEmpleadosListNewTurnosPorEmpleados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleTurnos.getIdDetalleTurno();
                if (findDetalleTurnos(id) == null) {
                    throw new NonexistentEntityException("The detalleTurnos with id " + id + " no longer exists.");
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
            DetalleTurnos detalleTurnos;
            try {
                detalleTurnos = em.getReference(DetalleTurnos.class, id);
                detalleTurnos.getIdDetalleTurno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleTurnos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TurnosPorEmpleados> turnosPorEmpleadosListOrphanCheck = detalleTurnos.getTurnosPorEmpleadosList();
            for (TurnosPorEmpleados turnosPorEmpleadosListOrphanCheckTurnosPorEmpleados : turnosPorEmpleadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DetalleTurnos (" + detalleTurnos + ") cannot be destroyed since the TurnosPorEmpleados " + turnosPorEmpleadosListOrphanCheckTurnosPorEmpleados + " in its turnosPorEmpleadosList field has a non-nullable idDetalleTurnos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Turnos idTurno = detalleTurnos.getIdTurno();
            if (idTurno != null) {
                idTurno.getDetalleTurnosList().remove(detalleTurnos);
                idTurno = em.merge(idTurno);
            }
            em.remove(detalleTurnos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleTurnos> findDetalleTurnosEntities() {
        return findDetalleTurnosEntities(true, -1, -1);
    }

    public List<DetalleTurnos> findDetalleTurnosEntities(int maxResults, int firstResult) {
        return findDetalleTurnosEntities(false, maxResults, firstResult);
    }

    private List<DetalleTurnos> findDetalleTurnosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleTurnos.class));
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

    public DetalleTurnos findDetalleTurnos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleTurnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleTurnosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleTurnos> rt = cq.from(DetalleTurnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
