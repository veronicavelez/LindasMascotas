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
import co.com.lindasmascotas.entities.Citas;
import co.com.lindasmascotas.entities.Empleados;
import java.util.ArrayList;
import java.util.List;
import co.com.lindasmascotas.entities.ServicioPorEmpleado;
import co.com.lindasmascotas.entities.Procedimientos;
import co.com.lindasmascotas.entities.ServicioPorEmpleado_;
import co.com.lindasmascotas.entities.Servicios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Isa
 */
public class ServiciosJpaController implements Serializable {

    public ServiciosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servicios servicios) throws PreexistingEntityException, Exception {
        if (servicios.getCitasList() == null) {
            servicios.setCitasList(new ArrayList<Citas>());
        }
        if (servicios.getServicioPorEmpleadoList() == null) {
            servicios.setServicioPorEmpleadoList(new ArrayList<ServicioPorEmpleado>());
        }
        if (servicios.getProcedimientosList() == null) {
            servicios.setProcedimientosList(new ArrayList<Procedimientos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Citas> attachedCitasList = new ArrayList<Citas>();
            for (Citas citasListCitasToAttach : servicios.getCitasList()) {
                citasListCitasToAttach = em.getReference(citasListCitasToAttach.getClass(), citasListCitasToAttach.getIdCita());
                attachedCitasList.add(citasListCitasToAttach);
            }
            servicios.setCitasList(attachedCitasList);
            List<ServicioPorEmpleado> attachedServicioPorEmpleadoList = new ArrayList<ServicioPorEmpleado>();
            for (ServicioPorEmpleado servicioPorEmpleadoListServicioPorEmpleadoToAttach : servicios.getServicioPorEmpleadoList()) {
                servicioPorEmpleadoListServicioPorEmpleadoToAttach = em.getReference(servicioPorEmpleadoListServicioPorEmpleadoToAttach.getClass(), servicioPorEmpleadoListServicioPorEmpleadoToAttach.getIdServEmpl());
                attachedServicioPorEmpleadoList.add(servicioPorEmpleadoListServicioPorEmpleadoToAttach);
            }
            servicios.setServicioPorEmpleadoList(attachedServicioPorEmpleadoList);
            List<Procedimientos> attachedProcedimientosList = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListProcedimientosToAttach : servicios.getProcedimientosList()) {
                procedimientosListProcedimientosToAttach = em.getReference(procedimientosListProcedimientosToAttach.getClass(), procedimientosListProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosList.add(procedimientosListProcedimientosToAttach);
            }
            servicios.setProcedimientosList(attachedProcedimientosList);
            em.persist(servicios);
            for (Citas citasListCitas : servicios.getCitasList()) {
                Servicios oldIdTipoServicioOfCitasListCitas = citasListCitas.getIdTipoServicio();
                citasListCitas.setIdTipoServicio(servicios);
                citasListCitas = em.merge(citasListCitas);
                if (oldIdTipoServicioOfCitasListCitas != null) {
                    oldIdTipoServicioOfCitasListCitas.getCitasList().remove(citasListCitas);
                    oldIdTipoServicioOfCitasListCitas = em.merge(oldIdTipoServicioOfCitasListCitas);
                }
            }
            for (ServicioPorEmpleado servicioPorEmpleadoListServicioPorEmpleado : servicios.getServicioPorEmpleadoList()) {
                Servicios oldIdServicioOfServicioPorEmpleadoListServicioPorEmpleado = servicioPorEmpleadoListServicioPorEmpleado.getIdServicio();
                servicioPorEmpleadoListServicioPorEmpleado.setIdServicio(servicios);
                servicioPorEmpleadoListServicioPorEmpleado = em.merge(servicioPorEmpleadoListServicioPorEmpleado);
                if (oldIdServicioOfServicioPorEmpleadoListServicioPorEmpleado != null) {
                    oldIdServicioOfServicioPorEmpleadoListServicioPorEmpleado.getServicioPorEmpleadoList().remove(servicioPorEmpleadoListServicioPorEmpleado);
                    oldIdServicioOfServicioPorEmpleadoListServicioPorEmpleado = em.merge(oldIdServicioOfServicioPorEmpleadoListServicioPorEmpleado);
                }
            }
            for (Procedimientos procedimientosListProcedimientos : servicios.getProcedimientosList()) {
                Servicios oldIdTipoServicioOfProcedimientosListProcedimientos = procedimientosListProcedimientos.getIdTipoServicio();
                procedimientosListProcedimientos.setIdTipoServicio(servicios);
                procedimientosListProcedimientos = em.merge(procedimientosListProcedimientos);
                if (oldIdTipoServicioOfProcedimientosListProcedimientos != null) {
                    oldIdTipoServicioOfProcedimientosListProcedimientos.getProcedimientosList().remove(procedimientosListProcedimientos);
                    oldIdTipoServicioOfProcedimientosListProcedimientos = em.merge(oldIdTipoServicioOfProcedimientosListProcedimientos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findServicios(servicios.getIdServicio()) != null) {
                throw new PreexistingEntityException("Servicios " + servicios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public boolean transactionCreate(Servicios servicio) throws PreexistingEntityException, Exception{
        EntityManager em = null;
        
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            Servicios s = new Servicios();
            s.setNombreServicio(servicio.getNombreServicio());
            s.setPrecioServicio(servicio.getPrecioServicio());
            s.setDescripcionServicio(servicio.getDescripcionServicio());
            
            em.persist(s);
            //em.getTransaction().commit();
            
            for(ServicioPorEmpleado spe: servicio.getServicioPorEmpleadoList()){
                ServicioPorEmpleado se = new ServicioPorEmpleado();
                se.setIdEmpleado(spe.getIdEmpleado());
                se.setIdServicio(s);
                em.persist(se);
            }
            em.getTransaction().commit();
            
            return true;
    
        } catch (Exception e) {
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            
            throw new Exception("No fue posible guardar el registro.");
        }finally{
            if(em != null){
                em.clear();
                em.close();
            }
        }
        
    }

    public void edit(Servicios servicios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicios persistentServicios = em.find(Servicios.class, servicios.getIdServicio());
            List<Citas> citasListOld = persistentServicios.getCitasList();
            List<Citas> citasListNew = servicios.getCitasList();
            List<ServicioPorEmpleado> servicioPorEmpleadoListOld = persistentServicios.getServicioPorEmpleadoList();
            List<ServicioPorEmpleado> servicioPorEmpleadoListNew = servicios.getServicioPorEmpleadoList();
            List<Procedimientos> procedimientosListOld = persistentServicios.getProcedimientosList();
            List<Procedimientos> procedimientosListNew = servicios.getProcedimientosList();
            List<String> illegalOrphanMessages = null;
            for (Citas citasListOldCitas : citasListOld) {
                if (!citasListNew.contains(citasListOldCitas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Citas " + citasListOldCitas + " since its idTipoServicio field is not nullable.");
                }
            }
            for (ServicioPorEmpleado servicioPorEmpleadoListOldServicioPorEmpleado : servicioPorEmpleadoListOld) {
                if (!servicioPorEmpleadoListNew.contains(servicioPorEmpleadoListOldServicioPorEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ServicioPorEmpleado " + servicioPorEmpleadoListOldServicioPorEmpleado + " since its idServicio field is not nullable.");
                }
            }
            for (Procedimientos procedimientosListOldProcedimientos : procedimientosListOld) {
                if (!procedimientosListNew.contains(procedimientosListOldProcedimientos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Procedimientos " + procedimientosListOldProcedimientos + " since its idTipoServicio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Citas> attachedCitasListNew = new ArrayList<Citas>();
            for (Citas citasListNewCitasToAttach : citasListNew) {
                citasListNewCitasToAttach = em.getReference(citasListNewCitasToAttach.getClass(), citasListNewCitasToAttach.getIdCita());
                attachedCitasListNew.add(citasListNewCitasToAttach);
            }
            citasListNew = attachedCitasListNew;
            servicios.setCitasList(citasListNew);
            List<ServicioPorEmpleado> attachedServicioPorEmpleadoListNew = new ArrayList<ServicioPorEmpleado>();
            for (ServicioPorEmpleado servicioPorEmpleadoListNewServicioPorEmpleadoToAttach : servicioPorEmpleadoListNew) {
                servicioPorEmpleadoListNewServicioPorEmpleadoToAttach = em.getReference(servicioPorEmpleadoListNewServicioPorEmpleadoToAttach.getClass(), servicioPorEmpleadoListNewServicioPorEmpleadoToAttach.getIdServEmpl());
                attachedServicioPorEmpleadoListNew.add(servicioPorEmpleadoListNewServicioPorEmpleadoToAttach);
            }
            servicioPorEmpleadoListNew = attachedServicioPorEmpleadoListNew;
            servicios.setServicioPorEmpleadoList(servicioPorEmpleadoListNew);
            List<Procedimientos> attachedProcedimientosListNew = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListNewProcedimientosToAttach : procedimientosListNew) {
                procedimientosListNewProcedimientosToAttach = em.getReference(procedimientosListNewProcedimientosToAttach.getClass(), procedimientosListNewProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosListNew.add(procedimientosListNewProcedimientosToAttach);
            }
            procedimientosListNew = attachedProcedimientosListNew;
            servicios.setProcedimientosList(procedimientosListNew);
            servicios = em.merge(servicios);
            for (Citas citasListNewCitas : citasListNew) {
                if (!citasListOld.contains(citasListNewCitas)) {
                    Servicios oldIdTipoServicioOfCitasListNewCitas = citasListNewCitas.getIdTipoServicio();
                    citasListNewCitas.setIdTipoServicio(servicios);
                    citasListNewCitas = em.merge(citasListNewCitas);
                    if (oldIdTipoServicioOfCitasListNewCitas != null && !oldIdTipoServicioOfCitasListNewCitas.equals(servicios)) {
                        oldIdTipoServicioOfCitasListNewCitas.getCitasList().remove(citasListNewCitas);
                        oldIdTipoServicioOfCitasListNewCitas = em.merge(oldIdTipoServicioOfCitasListNewCitas);
                    }
                }
            }
            for (ServicioPorEmpleado servicioPorEmpleadoListNewServicioPorEmpleado : servicioPorEmpleadoListNew) {
                if (!servicioPorEmpleadoListOld.contains(servicioPorEmpleadoListNewServicioPorEmpleado)) {
                    Servicios oldIdServicioOfServicioPorEmpleadoListNewServicioPorEmpleado = servicioPorEmpleadoListNewServicioPorEmpleado.getIdServicio();
                    servicioPorEmpleadoListNewServicioPorEmpleado.setIdServicio(servicios);
                    servicioPorEmpleadoListNewServicioPorEmpleado = em.merge(servicioPorEmpleadoListNewServicioPorEmpleado);
                    if (oldIdServicioOfServicioPorEmpleadoListNewServicioPorEmpleado != null && !oldIdServicioOfServicioPorEmpleadoListNewServicioPorEmpleado.equals(servicios)) {
                        oldIdServicioOfServicioPorEmpleadoListNewServicioPorEmpleado.getServicioPorEmpleadoList().remove(servicioPorEmpleadoListNewServicioPorEmpleado);
                        oldIdServicioOfServicioPorEmpleadoListNewServicioPorEmpleado = em.merge(oldIdServicioOfServicioPorEmpleadoListNewServicioPorEmpleado);
                    }
                }
            }
            for (Procedimientos procedimientosListNewProcedimientos : procedimientosListNew) {
                if (!procedimientosListOld.contains(procedimientosListNewProcedimientos)) {
                    Servicios oldIdTipoServicioOfProcedimientosListNewProcedimientos = procedimientosListNewProcedimientos.getIdTipoServicio();
                    procedimientosListNewProcedimientos.setIdTipoServicio(servicios);
                    procedimientosListNewProcedimientos = em.merge(procedimientosListNewProcedimientos);
                    if (oldIdTipoServicioOfProcedimientosListNewProcedimientos != null && !oldIdTipoServicioOfProcedimientosListNewProcedimientos.equals(servicios)) {
                        oldIdTipoServicioOfProcedimientosListNewProcedimientos.getProcedimientosList().remove(procedimientosListNewProcedimientos);
                        oldIdTipoServicioOfProcedimientosListNewProcedimientos = em.merge(oldIdTipoServicioOfProcedimientosListNewProcedimientos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = servicios.getIdServicio();
                if (findServicios(id) == null) {
                    throw new NonexistentEntityException("The servicios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
     
    }
    
    public boolean transactionEdit(Servicios servicio) throws IllegalOrphanException, NonexistentEntityException, Exception{
        EntityManager em = null;
        
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            
            Servicios s = new Servicios();
            s.setIdServicio(servicio.getIdServicio());
            s.setNombreServicio(servicio.getNombreServicio());
            s.setDescripcionServicio(servicio.getDescripcionServicio());
            s.setPrecioServicio(servicio.getPrecioServicio());
            
            for(ServicioPorEmpleado spe: servicio.getServicioPorEmpleadoList()){
                spe.setIdServicio(new Servicios(servicio.getIdServicio()));
                spe.setIdEmpleado(new Empleados(spe.getIdEmpleado().getIdEmpleado()));
                
                if(spe.getIdServEmpl() == null){
                    ServicioPorEmpleado serv = new ServicioPorEmpleado();
                    serv.setIdEmpleado(spe.getIdEmpleado());
                    serv.setIdServicio(spe.getIdServicio());
                    
                    em.persist(serv);
                }else{
                    em.merge(spe);
                }
            }
            
            em.merge(s);
            em.getTransaction().commit();
            
            return true;
            
        }catch (Exception ex) { 
            em.getTransaction().rollback();
            
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = servicio.getIdServicio();
                if (findServicios(id) == null) {
                    throw new NonexistentEntityException("The servicios with id " + id + " no longer exists.");
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
            Servicios servicios;
            try {
                servicios = em.getReference(Servicios.class, id);
                servicios.getIdServicio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Citas> citasListOrphanCheck = servicios.getCitasList();
            for (Citas citasListOrphanCheckCitas : citasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servicios (" + servicios + ") cannot be destroyed since the Citas " + citasListOrphanCheckCitas + " in its citasList field has a non-nullable idTipoServicio field.");
            }
            List<ServicioPorEmpleado> servicioPorEmpleadoListOrphanCheck = servicios.getServicioPorEmpleadoList();
            for (ServicioPorEmpleado servicioPorEmpleadoListOrphanCheckServicioPorEmpleado : servicioPorEmpleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servicios (" + servicios + ") cannot be destroyed since the ServicioPorEmpleado " + servicioPorEmpleadoListOrphanCheckServicioPorEmpleado + " in its servicioPorEmpleadoList field has a non-nullable idServicio field.");
            }
            List<Procedimientos> procedimientosListOrphanCheck = servicios.getProcedimientosList();
            for (Procedimientos procedimientosListOrphanCheckProcedimientos : procedimientosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servicios (" + servicios + ") cannot be destroyed since the Procedimientos " + procedimientosListOrphanCheckProcedimientos + " in its procedimientosList field has a non-nullable idTipoServicio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(servicios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public boolean transactionDestroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, Exception{
        EntityManager em = null;
        
        try {
            em = getEntityManager();
            em.getTransaction().begin();
           
            Servicios s = em.find(Servicios.class, id);
            
            for(ServicioPorEmpleado spe: s.getServicioPorEmpleadoList()){
                em.remove(spe.getIdServEmpl());
            }
             em.remove(id);
             em.getTransaction().commit();
           
             return true;
             
        }catch (Exception ex){
            em.getTransaction().rollback();
            
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                if (findServicios(id) == null) {
                    throw new NonexistentEntityException("The servicios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null){
                em.close();
            }
        } 
    
    }
        
    public List<Servicios> findServiciosEntities() {
        return findServiciosEntities(true, -1, -1);
    }

    public List<Servicios> findServiciosEntities(int maxResults, int firstResult) {
        return findServiciosEntities(false, maxResults, firstResult);
    }

    private List<Servicios> findServiciosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servicios.class));
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

    public Servicios findServicios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servicios.class, id);
        } finally {
            em.close();
        }
    }

    public int getServiciosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servicios> rt = cq.from(Servicios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
