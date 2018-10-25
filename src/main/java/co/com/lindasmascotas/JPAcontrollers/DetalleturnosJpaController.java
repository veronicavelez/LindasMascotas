/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.JPAcontrollers;

import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.PreexistingEntityException;
import co.com.lindasmascotas.entities.Detalleturnos;
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
public class DetalleturnosJpaController implements Serializable {

    public DetalleturnosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Detalleturnos detalleturnos) throws PreexistingEntityException, Exception {
        if (detalleturnos.getTurnosPorEmpleadosList() == null) {
            detalleturnos.setTurnosPorEmpleadosList(new ArrayList<TurnosPorEmpleados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turnos idTurno = detalleturnos.getIdTurno();
            if (idTurno != null) {
                idTurno = em.getReference(idTurno.getClass(), idTurno.getIdTurno());
                detalleturnos.setIdTurno(idTurno);
            }
            List<TurnosPorEmpleados> attachedTurnosPorEmpleadosList = new ArrayList<TurnosPorEmpleados>();
            for (TurnosPorEmpleados turnosPorEmpleadosListTurnosPorEmpleadosToAttach : detalleturnos.getTurnosPorEmpleadosList()) {
                turnosPorEmpleadosListTurnosPorEmpleadosToAttach = em.getReference(turnosPorEmpleadosListTurnosPorEmpleadosToAttach.getClass(), turnosPorEmpleadosListTurnosPorEmpleadosToAttach.getIdTurnosPorEmpl());
                attachedTurnosPorEmpleadosList.add(turnosPorEmpleadosListTurnosPorEmpleadosToAttach);
            }
            detalleturnos.setTurnosPorEmpleadosList(attachedTurnosPorEmpleadosList);
            em.persist(detalleturnos);
            if (idTurno != null) {
                idTurno.getDetalleturnosList().add(detalleturnos);
                idTurno = em.merge(idTurno);
            }
            for (TurnosPorEmpleados turnosPorEmpleadosListTurnosPorEmpleados : detalleturnos.getTurnosPorEmpleadosList()) {
                Detalleturnos oldIdDetalleTurnosOfTurnosPorEmpleadosListTurnosPorEmpleados = turnosPorEmpleadosListTurnosPorEmpleados.getIdDetalleTurnos();
                turnosPorEmpleadosListTurnosPorEmpleados.setIdDetalleTurnos(detalleturnos);
                turnosPorEmpleadosListTurnosPorEmpleados = em.merge(turnosPorEmpleadosListTurnosPorEmpleados);
                if (oldIdDetalleTurnosOfTurnosPorEmpleadosListTurnosPorEmpleados != null) {
                    oldIdDetalleTurnosOfTurnosPorEmpleadosListTurnosPorEmpleados.getTurnosPorEmpleadosList().remove(turnosPorEmpleadosListTurnosPorEmpleados);
                    oldIdDetalleTurnosOfTurnosPorEmpleadosListTurnosPorEmpleados = em.merge(oldIdDetalleTurnosOfTurnosPorEmpleadosListTurnosPorEmpleados);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetalleturnos(detalleturnos.getIdDetalleTurno()) != null) {
                throw new PreexistingEntityException("Detalleturnos " + detalleturnos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detalleturnos detalleturnos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detalleturnos persistentDetalleturnos = em.find(Detalleturnos.class, detalleturnos.getIdDetalleTurno());
            Turnos idTurnoOld = persistentDetalleturnos.getIdTurno();
            Turnos idTurnoNew = detalleturnos.getIdTurno();
            List<TurnosPorEmpleados> turnosPorEmpleadosListOld = persistentDetalleturnos.getTurnosPorEmpleadosList();
            List<TurnosPorEmpleados> turnosPorEmpleadosListNew = detalleturnos.getTurnosPorEmpleadosList();
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
                detalleturnos.setIdTurno(idTurnoNew);
            }
            List<TurnosPorEmpleados> attachedTurnosPorEmpleadosListNew = new ArrayList<TurnosPorEmpleados>();
            for (TurnosPorEmpleados turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach : turnosPorEmpleadosListNew) {
                turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach = em.getReference(turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach.getClass(), turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach.getIdTurnosPorEmpl());
                attachedTurnosPorEmpleadosListNew.add(turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach);
            }
            turnosPorEmpleadosListNew = attachedTurnosPorEmpleadosListNew;
            detalleturnos.setTurnosPorEmpleadosList(turnosPorEmpleadosListNew);
            detalleturnos = em.merge(detalleturnos);
            if (idTurnoOld != null && !idTurnoOld.equals(idTurnoNew)) {
                idTurnoOld.getDetalleturnosList().remove(detalleturnos);
                idTurnoOld = em.merge(idTurnoOld);
            }
            if (idTurnoNew != null && !idTurnoNew.equals(idTurnoOld)) {
                idTurnoNew.getDetalleturnosList().add(detalleturnos);
                idTurnoNew = em.merge(idTurnoNew);
            }
            for (TurnosPorEmpleados turnosPorEmpleadosListNewTurnosPorEmpleados : turnosPorEmpleadosListNew) {
                if (!turnosPorEmpleadosListOld.contains(turnosPorEmpleadosListNewTurnosPorEmpleados)) {
                    Detalleturnos oldIdDetalleTurnosOfTurnosPorEmpleadosListNewTurnosPorEmpleados = turnosPorEmpleadosListNewTurnosPorEmpleados.getIdDetalleTurnos();
                    turnosPorEmpleadosListNewTurnosPorEmpleados.setIdDetalleTurnos(detalleturnos);
                    turnosPorEmpleadosListNewTurnosPorEmpleados = em.merge(turnosPorEmpleadosListNewTurnosPorEmpleados);
                    if (oldIdDetalleTurnosOfTurnosPorEmpleadosListNewTurnosPorEmpleados != null && !oldIdDetalleTurnosOfTurnosPorEmpleadosListNewTurnosPorEmpleados.equals(detalleturnos)) {
                        oldIdDetalleTurnosOfTurnosPorEmpleadosListNewTurnosPorEmpleados.getTurnosPorEmpleadosList().remove(turnosPorEmpleadosListNewTurnosPorEmpleados);
                        oldIdDetalleTurnosOfTurnosPorEmpleadosListNewTurnosPorEmpleados = em.merge(oldIdDetalleTurnosOfTurnosPorEmpleadosListNewTurnosPorEmpleados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleturnos.getIdDetalleTurno();
                if (findDetalleturnos(id) == null) {
                    throw new NonexistentEntityException("The detalleturnos with id " + id + " no longer exists.");
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
            Detalleturnos detalleturnos;
            try {
                detalleturnos = em.getReference(Detalleturnos.class, id);
                detalleturnos.getIdDetalleTurno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleturnos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TurnosPorEmpleados> turnosPorEmpleadosListOrphanCheck = detalleturnos.getTurnosPorEmpleadosList();
            for (TurnosPorEmpleados turnosPorEmpleadosListOrphanCheckTurnosPorEmpleados : turnosPorEmpleadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Detalleturnos (" + detalleturnos + ") cannot be destroyed since the TurnosPorEmpleados " + turnosPorEmpleadosListOrphanCheckTurnosPorEmpleados + " in its turnosPorEmpleadosList field has a non-nullable idDetalleTurnos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Turnos idTurno = detalleturnos.getIdTurno();
            if (idTurno != null) {
                idTurno.getDetalleturnosList().remove(detalleturnos);
                idTurno = em.merge(idTurno);
            }
            em.remove(detalleturnos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detalleturnos> findDetalleturnosEntities() {
        return findDetalleturnosEntities(true, -1, -1);
    }

    public List<Detalleturnos> findDetalleturnosEntities(int maxResults, int firstResult) {
        return findDetalleturnosEntities(false, maxResults, firstResult);
    }

    private List<Detalleturnos> findDetalleturnosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detalleturnos.class));
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

    public Detalleturnos findDetalleturnos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detalleturnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleturnosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detalleturnos> rt = cq.from(Detalleturnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
