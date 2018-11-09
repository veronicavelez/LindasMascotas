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
import co.com.lindasmascotas.entities.Barrios;
import co.com.lindasmascotas.entities.Ciudades;
import co.com.lindasmascotas.entities.Generos;
import co.com.lindasmascotas.entities.TiposDocumento;
import co.com.lindasmascotas.entities.Citas;
import java.util.ArrayList;
import java.util.List;
import co.com.lindasmascotas.entities.Mascotas;
import co.com.lindasmascotas.entities.Propietarios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Isa
 */
public class PropietariosJpaController implements Serializable {

    public PropietariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Propietarios propietarios) throws PreexistingEntityException, Exception {
        if (propietarios.getCitasList() == null) {
            propietarios.setCitasList(new ArrayList<Citas>());
        }
        if (propietarios.getMascotasList() == null) {
            propietarios.setMascotasList(new ArrayList<Mascotas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barrios idBarrio = propietarios.getIdBarrio();
            if (idBarrio != null) {
                idBarrio = em.getReference(idBarrio.getClass(), idBarrio.getIdBarrio());
                propietarios.setIdBarrio(idBarrio);
            }
            Ciudades idCiudad = propietarios.getIdCiudad();
            if (idCiudad != null) {
                idCiudad = em.getReference(idCiudad.getClass(), idCiudad.getIdCiudad());
                propietarios.setIdCiudad(idCiudad);
            }
            Generos idGenero = propietarios.getIdGenero();
            if (idGenero != null) {
                idGenero = em.getReference(idGenero.getClass(), idGenero.getIdGenero());
                propietarios.setIdGenero(idGenero);
            }
            TiposDocumento idTipoDocumento = propietarios.getIdTipoDocumento();
            if (idTipoDocumento != null) {
                idTipoDocumento = em.getReference(idTipoDocumento.getClass(), idTipoDocumento.getIdTipoDoc());
                propietarios.setIdTipoDocumento(idTipoDocumento);
            }
            List<Citas> attachedCitasList = new ArrayList<Citas>();
            for (Citas citasListCitasToAttach : propietarios.getCitasList()) {
                citasListCitasToAttach = em.getReference(citasListCitasToAttach.getClass(), citasListCitasToAttach.getIdCita());
                attachedCitasList.add(citasListCitasToAttach);
            }
            propietarios.setCitasList(attachedCitasList);
            List<Mascotas> attachedMascotasList = new ArrayList<Mascotas>();
            for (Mascotas mascotasListMascotasToAttach : propietarios.getMascotasList()) {
                mascotasListMascotasToAttach = em.getReference(mascotasListMascotasToAttach.getClass(), mascotasListMascotasToAttach.getIdMascota());
                attachedMascotasList.add(mascotasListMascotasToAttach);
            }
            propietarios.setMascotasList(attachedMascotasList);
            em.persist(propietarios);
            if (idBarrio != null) {
                idBarrio.getPropietariosList().add(propietarios);
                idBarrio = em.merge(idBarrio);
            }
            if (idCiudad != null) {
                idCiudad.getPropietariosList().add(propietarios);
                idCiudad = em.merge(idCiudad);
            }
            if (idGenero != null) {
                idGenero.getPropietariosList().add(propietarios);
                idGenero = em.merge(idGenero);
            }
            if (idTipoDocumento != null) {
                idTipoDocumento.getPropietariosList().add(propietarios);
                idTipoDocumento = em.merge(idTipoDocumento);
            }
            for (Citas citasListCitas : propietarios.getCitasList()) {
                Propietarios oldIdPropietarioOfCitasListCitas = citasListCitas.getIdPropietario();
                citasListCitas.setIdPropietario(propietarios);
                citasListCitas = em.merge(citasListCitas);
                if (oldIdPropietarioOfCitasListCitas != null) {
                    oldIdPropietarioOfCitasListCitas.getCitasList().remove(citasListCitas);
                    oldIdPropietarioOfCitasListCitas = em.merge(oldIdPropietarioOfCitasListCitas);
                }
            }
            for (Mascotas mascotasListMascotas : propietarios.getMascotasList()) {
                Propietarios oldIdPropietarioOfMascotasListMascotas = mascotasListMascotas.getIdPropietario();
                mascotasListMascotas.setIdPropietario(propietarios);
                mascotasListMascotas = em.merge(mascotasListMascotas);
                if (oldIdPropietarioOfMascotasListMascotas != null) {
                    oldIdPropietarioOfMascotasListMascotas.getMascotasList().remove(mascotasListMascotas);
                    oldIdPropietarioOfMascotasListMascotas = em.merge(oldIdPropietarioOfMascotasListMascotas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPropietarios(propietarios.getIdPropietario()) != null) {
                throw new PreexistingEntityException("Propietarios " + propietarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Propietarios propietarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propietarios persistentPropietarios = em.find(Propietarios.class, propietarios.getIdPropietario());
            Barrios idBarrioOld = persistentPropietarios.getIdBarrio();
            Barrios idBarrioNew = propietarios.getIdBarrio();
            Ciudades idCiudadOld = persistentPropietarios.getIdCiudad();
            Ciudades idCiudadNew = propietarios.getIdCiudad();
            Generos idGeneroOld = persistentPropietarios.getIdGenero();
            Generos idGeneroNew = propietarios.getIdGenero();
            TiposDocumento idTipoDocumentoOld = persistentPropietarios.getIdTipoDocumento();
            TiposDocumento idTipoDocumentoNew = propietarios.getIdTipoDocumento();
            List<Citas> citasListOld = persistentPropietarios.getCitasList();
            List<Citas> citasListNew = propietarios.getCitasList();
            List<Mascotas> mascotasListOld = persistentPropietarios.getMascotasList();
            List<Mascotas> mascotasListNew = propietarios.getMascotasList();
            List<String> illegalOrphanMessages = null;
            for (Citas citasListOldCitas : citasListOld) {
                if (!citasListNew.contains(citasListOldCitas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Citas " + citasListOldCitas + " since its idPropietario field is not nullable.");
                }
            }
            for (Mascotas mascotasListOldMascotas : mascotasListOld) {
                if (!mascotasListNew.contains(mascotasListOldMascotas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mascotas " + mascotasListOldMascotas + " since its idPropietario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idBarrioNew != null) {
                idBarrioNew = em.getReference(idBarrioNew.getClass(), idBarrioNew.getIdBarrio());
                propietarios.setIdBarrio(idBarrioNew);
            }
            if (idCiudadNew != null) {
                idCiudadNew = em.getReference(idCiudadNew.getClass(), idCiudadNew.getIdCiudad());
                propietarios.setIdCiudad(idCiudadNew);
            }
            if (idGeneroNew != null) {
                idGeneroNew = em.getReference(idGeneroNew.getClass(), idGeneroNew.getIdGenero());
                propietarios.setIdGenero(idGeneroNew);
            }
            if (idTipoDocumentoNew != null) {
                idTipoDocumentoNew = em.getReference(idTipoDocumentoNew.getClass(), idTipoDocumentoNew.getIdTipoDoc());
                propietarios.setIdTipoDocumento(idTipoDocumentoNew);
            }
            List<Citas> attachedCitasListNew = new ArrayList<Citas>();
            for (Citas citasListNewCitasToAttach : citasListNew) {
                citasListNewCitasToAttach = em.getReference(citasListNewCitasToAttach.getClass(), citasListNewCitasToAttach.getIdCita());
                attachedCitasListNew.add(citasListNewCitasToAttach);
            }
            citasListNew = attachedCitasListNew;
            propietarios.setCitasList(citasListNew);
            List<Mascotas> attachedMascotasListNew = new ArrayList<Mascotas>();
            for (Mascotas mascotasListNewMascotasToAttach : mascotasListNew) {
                mascotasListNewMascotasToAttach = em.getReference(mascotasListNewMascotasToAttach.getClass(), mascotasListNewMascotasToAttach.getIdMascota());
                attachedMascotasListNew.add(mascotasListNewMascotasToAttach);
            }
            mascotasListNew = attachedMascotasListNew;
            propietarios.setMascotasList(mascotasListNew);
            propietarios = em.merge(propietarios);
            if (idBarrioOld != null && !idBarrioOld.equals(idBarrioNew)) {
                idBarrioOld.getPropietariosList().remove(propietarios);
                idBarrioOld = em.merge(idBarrioOld);
            }
            if (idBarrioNew != null && !idBarrioNew.equals(idBarrioOld)) {
                idBarrioNew.getPropietariosList().add(propietarios);
                idBarrioNew = em.merge(idBarrioNew);
            }
            if (idCiudadOld != null && !idCiudadOld.equals(idCiudadNew)) {
                idCiudadOld.getPropietariosList().remove(propietarios);
                idCiudadOld = em.merge(idCiudadOld);
            }
            if (idCiudadNew != null && !idCiudadNew.equals(idCiudadOld)) {
                idCiudadNew.getPropietariosList().add(propietarios);
                idCiudadNew = em.merge(idCiudadNew);
            }
            if (idGeneroOld != null && !idGeneroOld.equals(idGeneroNew)) {
                idGeneroOld.getPropietariosList().remove(propietarios);
                idGeneroOld = em.merge(idGeneroOld);
            }
            if (idGeneroNew != null && !idGeneroNew.equals(idGeneroOld)) {
                idGeneroNew.getPropietariosList().add(propietarios);
                idGeneroNew = em.merge(idGeneroNew);
            }
            if (idTipoDocumentoOld != null && !idTipoDocumentoOld.equals(idTipoDocumentoNew)) {
                idTipoDocumentoOld.getPropietariosList().remove(propietarios);
                idTipoDocumentoOld = em.merge(idTipoDocumentoOld);
            }
            if (idTipoDocumentoNew != null && !idTipoDocumentoNew.equals(idTipoDocumentoOld)) {
                idTipoDocumentoNew.getPropietariosList().add(propietarios);
                idTipoDocumentoNew = em.merge(idTipoDocumentoNew);
            }
            for (Citas citasListNewCitas : citasListNew) {
                if (!citasListOld.contains(citasListNewCitas)) {
                    Propietarios oldIdPropietarioOfCitasListNewCitas = citasListNewCitas.getIdPropietario();
                    citasListNewCitas.setIdPropietario(propietarios);
                    citasListNewCitas = em.merge(citasListNewCitas);
                    if (oldIdPropietarioOfCitasListNewCitas != null && !oldIdPropietarioOfCitasListNewCitas.equals(propietarios)) {
                        oldIdPropietarioOfCitasListNewCitas.getCitasList().remove(citasListNewCitas);
                        oldIdPropietarioOfCitasListNewCitas = em.merge(oldIdPropietarioOfCitasListNewCitas);
                    }
                }
            }
            for (Mascotas mascotasListNewMascotas : mascotasListNew) {
                if (!mascotasListOld.contains(mascotasListNewMascotas)) {
                    Propietarios oldIdPropietarioOfMascotasListNewMascotas = mascotasListNewMascotas.getIdPropietario();
                    mascotasListNewMascotas.setIdPropietario(propietarios);
                    mascotasListNewMascotas = em.merge(mascotasListNewMascotas);
                    if (oldIdPropietarioOfMascotasListNewMascotas != null && !oldIdPropietarioOfMascotasListNewMascotas.equals(propietarios)) {
                        oldIdPropietarioOfMascotasListNewMascotas.getMascotasList().remove(mascotasListNewMascotas);
                        oldIdPropietarioOfMascotasListNewMascotas = em.merge(oldIdPropietarioOfMascotasListNewMascotas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = propietarios.getIdPropietario();
                if (findPropietarios(id) == null) {
                    throw new NonexistentEntityException("The propietarios with id " + id + " no longer exists.");
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
            Propietarios propietarios;
            try {
                propietarios = em.getReference(Propietarios.class, id);
                propietarios.getIdPropietario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The propietarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Citas> citasListOrphanCheck = propietarios.getCitasList();
            for (Citas citasListOrphanCheckCitas : citasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propietarios (" + propietarios + ") cannot be destroyed since the Citas " + citasListOrphanCheckCitas + " in its citasList field has a non-nullable idPropietario field.");
            }
            List<Mascotas> mascotasListOrphanCheck = propietarios.getMascotasList();
            for (Mascotas mascotasListOrphanCheckMascotas : mascotasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propietarios (" + propietarios + ") cannot be destroyed since the Mascotas " + mascotasListOrphanCheckMascotas + " in its mascotasList field has a non-nullable idPropietario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Barrios idBarrio = propietarios.getIdBarrio();
            if (idBarrio != null) {
                idBarrio.getPropietariosList().remove(propietarios);
                idBarrio = em.merge(idBarrio);
            }
            Ciudades idCiudad = propietarios.getIdCiudad();
            if (idCiudad != null) {
                idCiudad.getPropietariosList().remove(propietarios);
                idCiudad = em.merge(idCiudad);
            }
            Generos idGenero = propietarios.getIdGenero();
            if (idGenero != null) {
                idGenero.getPropietariosList().remove(propietarios);
                idGenero = em.merge(idGenero);
            }
            TiposDocumento idTipoDocumento = propietarios.getIdTipoDocumento();
            if (idTipoDocumento != null) {
                idTipoDocumento.getPropietariosList().remove(propietarios);
                idTipoDocumento = em.merge(idTipoDocumento);
            }
            em.remove(propietarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Propietarios> findPropietariosEntities() {
        return findPropietariosEntities(true, -1, -1);
    }

    public List<Propietarios> findPropietariosEntities(int maxResults, int firstResult) {
        return findPropietariosEntities(false, maxResults, firstResult);
    }

    private List<Propietarios> findPropietariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Propietarios.class));
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

    public Propietarios findPropietarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Propietarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getPropietariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Propietarios> rt = cq.from(Propietarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
