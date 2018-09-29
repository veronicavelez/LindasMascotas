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
import co.com.lindasmascotas.entities.Especies;
import co.com.lindasmascotas.entities.Mascotas;
import co.com.lindasmascotas.entities.Propietarios;
import co.com.lindasmascotas.entities.Razas;
import co.com.lindasmascotas.entities.Sexos;
import co.com.lindasmascotas.entities.Procedimientos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ISABEL MEDINA
 */
public class MascotasJpaController implements Serializable {

    public MascotasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mascotas mascotas) {
        if (mascotas.getProcedimientosList() == null) {
            mascotas.setProcedimientosList(new ArrayList<Procedimientos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especies idEspecie = mascotas.getIdEspecie();
            if (idEspecie != null) {
                idEspecie = em.getReference(idEspecie.getClass(), idEspecie.getIdEspecie());
                mascotas.setIdEspecie(idEspecie);
            }
            Propietarios idPropietario = mascotas.getIdPropietario();
            if (idPropietario != null) {
                idPropietario = em.getReference(idPropietario.getClass(), idPropietario.getIdPropietario());
                mascotas.setIdPropietario(idPropietario);
            }
            Razas idRaza = mascotas.getIdRaza();
            if (idRaza != null) {
                idRaza = em.getReference(idRaza.getClass(), idRaza.getIdRaza());
                mascotas.setIdRaza(idRaza);
            }
            Sexos idSexo = mascotas.getIdSexo();
            if (idSexo != null) {
                idSexo = em.getReference(idSexo.getClass(), idSexo.getIdSexo());
                mascotas.setIdSexo(idSexo);
            }
            List<Procedimientos> attachedProcedimientosList = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListProcedimientosToAttach : mascotas.getProcedimientosList()) {
                procedimientosListProcedimientosToAttach = em.getReference(procedimientosListProcedimientosToAttach.getClass(), procedimientosListProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosList.add(procedimientosListProcedimientosToAttach);
            }
            mascotas.setProcedimientosList(attachedProcedimientosList);
            em.persist(mascotas);
            if (idEspecie != null) {
                idEspecie.getMascotasList().add(mascotas);
                idEspecie = em.merge(idEspecie);
            }
            if (idPropietario != null) {
                idPropietario.getMascotasList().add(mascotas);
                idPropietario = em.merge(idPropietario);
            }
            if (idRaza != null) {
                idRaza.getMascotasList().add(mascotas);
                idRaza = em.merge(idRaza);
            }
            if (idSexo != null) {
                idSexo.getMascotasList().add(mascotas);
                idSexo = em.merge(idSexo);
            }
            for (Procedimientos procedimientosListProcedimientos : mascotas.getProcedimientosList()) {
                Mascotas oldIdMascotaOfProcedimientosListProcedimientos = procedimientosListProcedimientos.getIdMascota();
                procedimientosListProcedimientos.setIdMascota(mascotas);
                procedimientosListProcedimientos = em.merge(procedimientosListProcedimientos);
                if (oldIdMascotaOfProcedimientosListProcedimientos != null) {
                    oldIdMascotaOfProcedimientosListProcedimientos.getProcedimientosList().remove(procedimientosListProcedimientos);
                    oldIdMascotaOfProcedimientosListProcedimientos = em.merge(oldIdMascotaOfProcedimientosListProcedimientos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mascotas mascotas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mascotas persistentMascotas = em.find(Mascotas.class, mascotas.getIdMascota());
            Especies idEspecieOld = persistentMascotas.getIdEspecie();
            Especies idEspecieNew = mascotas.getIdEspecie();
            Propietarios idPropietarioOld = persistentMascotas.getIdPropietario();
            Propietarios idPropietarioNew = mascotas.getIdPropietario();
            Razas idRazaOld = persistentMascotas.getIdRaza();
            Razas idRazaNew = mascotas.getIdRaza();
            Sexos idSexoOld = persistentMascotas.getIdSexo();
            Sexos idSexoNew = mascotas.getIdSexo();
            List<Procedimientos> procedimientosListOld = persistentMascotas.getProcedimientosList();
            List<Procedimientos> procedimientosListNew = mascotas.getProcedimientosList();
            List<String> illegalOrphanMessages = null;
            for (Procedimientos procedimientosListOldProcedimientos : procedimientosListOld) {
                if (!procedimientosListNew.contains(procedimientosListOldProcedimientos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Procedimientos " + procedimientosListOldProcedimientos + " since its idMascota field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEspecieNew != null) {
                idEspecieNew = em.getReference(idEspecieNew.getClass(), idEspecieNew.getIdEspecie());
                mascotas.setIdEspecie(idEspecieNew);
            }
            if (idPropietarioNew != null) {
                idPropietarioNew = em.getReference(idPropietarioNew.getClass(), idPropietarioNew.getIdPropietario());
                mascotas.setIdPropietario(idPropietarioNew);
            }
            if (idRazaNew != null) {
                idRazaNew = em.getReference(idRazaNew.getClass(), idRazaNew.getIdRaza());
                mascotas.setIdRaza(idRazaNew);
            }
            if (idSexoNew != null) {
                idSexoNew = em.getReference(idSexoNew.getClass(), idSexoNew.getIdSexo());
                mascotas.setIdSexo(idSexoNew);
            }
            List<Procedimientos> attachedProcedimientosListNew = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListNewProcedimientosToAttach : procedimientosListNew) {
                procedimientosListNewProcedimientosToAttach = em.getReference(procedimientosListNewProcedimientosToAttach.getClass(), procedimientosListNewProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosListNew.add(procedimientosListNewProcedimientosToAttach);
            }
            procedimientosListNew = attachedProcedimientosListNew;
            mascotas.setProcedimientosList(procedimientosListNew);
            mascotas = em.merge(mascotas);
            if (idEspecieOld != null && !idEspecieOld.equals(idEspecieNew)) {
                idEspecieOld.getMascotasList().remove(mascotas);
                idEspecieOld = em.merge(idEspecieOld);
            }
            if (idEspecieNew != null && !idEspecieNew.equals(idEspecieOld)) {
                idEspecieNew.getMascotasList().add(mascotas);
                idEspecieNew = em.merge(idEspecieNew);
            }
            if (idPropietarioOld != null && !idPropietarioOld.equals(idPropietarioNew)) {
                idPropietarioOld.getMascotasList().remove(mascotas);
                idPropietarioOld = em.merge(idPropietarioOld);
            }
            if (idPropietarioNew != null && !idPropietarioNew.equals(idPropietarioOld)) {
                idPropietarioNew.getMascotasList().add(mascotas);
                idPropietarioNew = em.merge(idPropietarioNew);
            }
            if (idRazaOld != null && !idRazaOld.equals(idRazaNew)) {
                idRazaOld.getMascotasList().remove(mascotas);
                idRazaOld = em.merge(idRazaOld);
            }
            if (idRazaNew != null && !idRazaNew.equals(idRazaOld)) {
                idRazaNew.getMascotasList().add(mascotas);
                idRazaNew = em.merge(idRazaNew);
            }
            if (idSexoOld != null && !idSexoOld.equals(idSexoNew)) {
                idSexoOld.getMascotasList().remove(mascotas);
                idSexoOld = em.merge(idSexoOld);
            }
            if (idSexoNew != null && !idSexoNew.equals(idSexoOld)) {
                idSexoNew.getMascotasList().add(mascotas);
                idSexoNew = em.merge(idSexoNew);
            }
            for (Procedimientos procedimientosListNewProcedimientos : procedimientosListNew) {
                if (!procedimientosListOld.contains(procedimientosListNewProcedimientos)) {
                    Mascotas oldIdMascotaOfProcedimientosListNewProcedimientos = procedimientosListNewProcedimientos.getIdMascota();
                    procedimientosListNewProcedimientos.setIdMascota(mascotas);
                    procedimientosListNewProcedimientos = em.merge(procedimientosListNewProcedimientos);
                    if (oldIdMascotaOfProcedimientosListNewProcedimientos != null && !oldIdMascotaOfProcedimientosListNewProcedimientos.equals(mascotas)) {
                        oldIdMascotaOfProcedimientosListNewProcedimientos.getProcedimientosList().remove(procedimientosListNewProcedimientos);
                        oldIdMascotaOfProcedimientosListNewProcedimientos = em.merge(oldIdMascotaOfProcedimientosListNewProcedimientos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mascotas.getIdMascota();
                if (findMascotas(id) == null) {
                    throw new NonexistentEntityException("The mascotas with id " + id + " no longer exists.");
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
            Mascotas mascotas;
            try {
                mascotas = em.getReference(Mascotas.class, id);
                mascotas.getIdMascota();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mascotas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Procedimientos> procedimientosListOrphanCheck = mascotas.getProcedimientosList();
            for (Procedimientos procedimientosListOrphanCheckProcedimientos : procedimientosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mascotas (" + mascotas + ") cannot be destroyed since the Procedimientos " + procedimientosListOrphanCheckProcedimientos + " in its procedimientosList field has a non-nullable idMascota field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Especies idEspecie = mascotas.getIdEspecie();
            if (idEspecie != null) {
                idEspecie.getMascotasList().remove(mascotas);
                idEspecie = em.merge(idEspecie);
            }
            Propietarios idPropietario = mascotas.getIdPropietario();
            if (idPropietario != null) {
                idPropietario.getMascotasList().remove(mascotas);
                idPropietario = em.merge(idPropietario);
            }
            Razas idRaza = mascotas.getIdRaza();
            if (idRaza != null) {
                idRaza.getMascotasList().remove(mascotas);
                idRaza = em.merge(idRaza);
            }
            Sexos idSexo = mascotas.getIdSexo();
            if (idSexo != null) {
                idSexo.getMascotasList().remove(mascotas);
                idSexo = em.merge(idSexo);
            }
            em.remove(mascotas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mascotas> findMascotasEntities() {
        return findMascotasEntities(true, -1, -1);
    }

    public List<Mascotas> findMascotasEntities(int maxResults, int firstResult) {
        return findMascotasEntities(false, maxResults, firstResult);
    }

    private List<Mascotas> findMascotasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mascotas.class));
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

    public Mascotas findMascotas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mascotas.class, id);
        } finally {
            em.close();
        }
    }

    public int getMascotasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mascotas> rt = cq.from(Mascotas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
