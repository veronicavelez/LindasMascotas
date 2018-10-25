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
import co.com.lindasmascotas.entities.Cargos;
import co.com.lindasmascotas.entities.Ciudades;
import co.com.lindasmascotas.entities.Empleados;
import co.com.lindasmascotas.entities.Generos;
import co.com.lindasmascotas.entities.Perfiles;
import co.com.lindasmascotas.entities.TiposContrato;
import co.com.lindasmascotas.entities.TiposDocumento;
import co.com.lindasmascotas.entities.TiposSangre;
import co.com.lindasmascotas.entities.TurnosPorEmpleados;
import java.util.ArrayList;
import java.util.List;
import co.com.lindasmascotas.entities.Citas;
import co.com.lindasmascotas.entities.Empleados;
import co.com.lindasmascotas.entities.TurnosPorEmpleados;
import co.com.lindasmascotas.entities.Procedimientos;
import co.com.lindasmascotas.entities.Servicios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ISABEL MEDINA
 */
public class EmpleadosJpaController implements Serializable {

    public EmpleadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleados empleados) throws PreexistingEntityException, Exception {
        if (empleados.getServiciosList() == null) {
            empleados.setServiciosList(new ArrayList<Servicios>());
        }
        if (empleados.getCitasList() == null) {
            empleados.setCitasList(new ArrayList<Citas>());
        }
        if (empleados.getTurnosPorEmpleadosList() == null) {
            empleados.setTurnosPorEmpleadosList(new ArrayList<TurnosPorEmpleados>());
        }
        if (empleados.getProcedimientosList() == null) {
            empleados.setProcedimientosList(new ArrayList<Procedimientos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barrios idBarrio = empleados.getIdBarrio();
            if (idBarrio != null) {
                idBarrio = em.getReference(idBarrio.getClass(), idBarrio.getIdBarrio());
                empleados.setIdBarrio(idBarrio);
            }
            Cargos idCargo = empleados.getIdCargo();
            if (idCargo != null) {
                idCargo = em.getReference(idCargo.getClass(), idCargo.getIdCargo());
                empleados.setIdCargo(idCargo);
            }
            Ciudades idCiudad = empleados.getIdCiudad();
            if (idCiudad != null) {
                idCiudad = em.getReference(idCiudad.getClass(), idCiudad.getIdCiudad());
                empleados.setIdCiudad(idCiudad);
            }
            Generos idGenero = empleados.getIdGenero();
            if (idGenero != null) {
                idGenero = em.getReference(idGenero.getClass(), idGenero.getIdGenero());
                empleados.setIdGenero(idGenero);
            }
            Perfiles idPerfil = empleados.getIdPerfil();
            if (idPerfil != null) {
                idPerfil = em.getReference(idPerfil.getClass(), idPerfil.getIdPerfil());
                empleados.setIdPerfil(idPerfil);
            }
            TiposContrato idTipoContrato = empleados.getIdTipoContrato();
            if (idTipoContrato != null) {
                idTipoContrato = em.getReference(idTipoContrato.getClass(), idTipoContrato.getIdTipoContrato());
                empleados.setIdTipoContrato(idTipoContrato);
            }
            TiposDocumento idTipoDocumento = empleados.getIdTipoDocumento();
            if (idTipoDocumento != null) {
                idTipoDocumento = em.getReference(idTipoDocumento.getClass(), idTipoDocumento.getIdTipoDoc());
                empleados.setIdTipoDocumento(idTipoDocumento);
            }
            TiposSangre idTipoSangre = empleados.getIdTipoSangre();
            if (idTipoSangre != null) {
                idTipoSangre = em.getReference(idTipoSangre.getClass(), idTipoSangre.getIdTipoSangre());
                empleados.setIdTipoSangre(idTipoSangre);
            }
            List<Servicios> attachedServiciosList = new ArrayList<Servicios>();
            for (Servicios serviciosListServiciosToAttach : empleados.getServiciosList()) {
                serviciosListServiciosToAttach = em.getReference(serviciosListServiciosToAttach.getClass(), serviciosListServiciosToAttach.getIdServicio());
                attachedServiciosList.add(serviciosListServiciosToAttach);
            }
            empleados.setServiciosList(attachedServiciosList);
            List<Citas> attachedCitasList = new ArrayList<Citas>();
            for (Citas citasListCitasToAttach : empleados.getCitasList()) {
                citasListCitasToAttach = em.getReference(citasListCitasToAttach.getClass(), citasListCitasToAttach.getIdCita());
                attachedCitasList.add(citasListCitasToAttach);
            }
            empleados.setCitasList(attachedCitasList);
            List<TurnosPorEmpleados> attachedTurnosPorEmpleadosList = new ArrayList<TurnosPorEmpleados>();
            for (TurnosPorEmpleados turnosPorEmpleadosListTurnosPorEmpleadosToAttach : empleados.getTurnosPorEmpleadosList()) {
                turnosPorEmpleadosListTurnosPorEmpleadosToAttach = em.getReference(turnosPorEmpleadosListTurnosPorEmpleadosToAttach.getClass(), turnosPorEmpleadosListTurnosPorEmpleadosToAttach.getIdTurnosPorEmpl());
                attachedTurnosPorEmpleadosList.add(turnosPorEmpleadosListTurnosPorEmpleadosToAttach);
            }
            empleados.setTurnosPorEmpleadosList(attachedTurnosPorEmpleadosList);
            List<Procedimientos> attachedProcedimientosList = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListProcedimientosToAttach : empleados.getProcedimientosList()) {
                procedimientosListProcedimientosToAttach = em.getReference(procedimientosListProcedimientosToAttach.getClass(), procedimientosListProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosList.add(procedimientosListProcedimientosToAttach);
            }
            empleados.setProcedimientosList(attachedProcedimientosList);
            em.persist(empleados);
            if (idBarrio != null) {
                idBarrio.getEmpleadosList().add(empleados);
                idBarrio = em.merge(idBarrio);
            }
            if (idCargo != null) {
                idCargo.getEmpleadosList().add(empleados);
                idCargo = em.merge(idCargo);
            }
            if (idCiudad != null) {
                idCiudad.getEmpleadosList().add(empleados);
                idCiudad = em.merge(idCiudad);
            }
            if (idGenero != null) {
                idGenero.getEmpleadosList().add(empleados);
                idGenero = em.merge(idGenero);
            }
            if (idPerfil != null) {
                idPerfil.getEmpleadosList().add(empleados);
                idPerfil = em.merge(idPerfil);
            }
            if (idTipoContrato != null) {
                idTipoContrato.getEmpleadosList().add(empleados);
                idTipoContrato = em.merge(idTipoContrato);
            }
            if (idTipoDocumento != null) {
                idTipoDocumento.getEmpleadosList().add(empleados);
                idTipoDocumento = em.merge(idTipoDocumento);
            }
            if (idTipoSangre != null) {
                idTipoSangre.getEmpleadosList().add(empleados);
                idTipoSangre = em.merge(idTipoSangre);
            }
            for (Servicios serviciosListServicios : empleados.getServiciosList()) {
                serviciosListServicios.getEmpleadosList().add(empleados);
                serviciosListServicios = em.merge(serviciosListServicios);
            }
            for (Citas citasListCitas : empleados.getCitasList()) {
                Empleados oldIdEmpleadoOfCitasListCitas = citasListCitas.getIdEmpleado();
                citasListCitas.setIdEmpleado(empleados);
                citasListCitas = em.merge(citasListCitas);
                if (oldIdEmpleadoOfCitasListCitas != null) {
                    oldIdEmpleadoOfCitasListCitas.getCitasList().remove(citasListCitas);
                    oldIdEmpleadoOfCitasListCitas = em.merge(oldIdEmpleadoOfCitasListCitas);
                }
            }
            for (TurnosPorEmpleados turnosPorEmpleadosListTurnosPorEmpleados : empleados.getTurnosPorEmpleadosList()) {
                Empleados oldIdEmpleadoOfTurnosPorEmpleadosListTurnosPorEmpleados = turnosPorEmpleadosListTurnosPorEmpleados.getIdEmpleado();
                turnosPorEmpleadosListTurnosPorEmpleados.setIdEmpleado(empleados);
                turnosPorEmpleadosListTurnosPorEmpleados = em.merge(turnosPorEmpleadosListTurnosPorEmpleados);
                if (oldIdEmpleadoOfTurnosPorEmpleadosListTurnosPorEmpleados != null) {
                    oldIdEmpleadoOfTurnosPorEmpleadosListTurnosPorEmpleados.getTurnosPorEmpleadosList().remove(turnosPorEmpleadosListTurnosPorEmpleados);
                    oldIdEmpleadoOfTurnosPorEmpleadosListTurnosPorEmpleados = em.merge(oldIdEmpleadoOfTurnosPorEmpleadosListTurnosPorEmpleados);
                }
            }
            for (Procedimientos procedimientosListProcedimientos : empleados.getProcedimientosList()) {
                Empleados oldIdEmpleadoOfProcedimientosListProcedimientos = procedimientosListProcedimientos.getIdEmpleado();
                procedimientosListProcedimientos.setIdEmpleado(empleados);
                procedimientosListProcedimientos = em.merge(procedimientosListProcedimientos);
                if (oldIdEmpleadoOfProcedimientosListProcedimientos != null) {
                    oldIdEmpleadoOfProcedimientosListProcedimientos.getProcedimientosList().remove(procedimientosListProcedimientos);
                    oldIdEmpleadoOfProcedimientosListProcedimientos = em.merge(oldIdEmpleadoOfProcedimientosListProcedimientos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleados(empleados.getIdEmpleado()) != null) {
                throw new PreexistingEntityException("Empleados " + empleados + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleados empleados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados persistentEmpleados = em.find(Empleados.class, empleados.getIdEmpleado());
            Barrios idBarrioOld = persistentEmpleados.getIdBarrio();
            Barrios idBarrioNew = empleados.getIdBarrio();
            Cargos idCargoOld = persistentEmpleados.getIdCargo();
            Cargos idCargoNew = empleados.getIdCargo();
            Ciudades idCiudadOld = persistentEmpleados.getIdCiudad();
            Ciudades idCiudadNew = empleados.getIdCiudad();
            Generos idGeneroOld = persistentEmpleados.getIdGenero();
            Generos idGeneroNew = empleados.getIdGenero();
            Perfiles idPerfilOld = persistentEmpleados.getIdPerfil();
            Perfiles idPerfilNew = empleados.getIdPerfil();
            TiposContrato idTipoContratoOld = persistentEmpleados.getIdTipoContrato();
            TiposContrato idTipoContratoNew = empleados.getIdTipoContrato();
            TiposDocumento idTipoDocumentoOld = persistentEmpleados.getIdTipoDocumento();
            TiposDocumento idTipoDocumentoNew = empleados.getIdTipoDocumento();
            TiposSangre idTipoSangreOld = persistentEmpleados.getIdTipoSangre();
            TiposSangre idTipoSangreNew = empleados.getIdTipoSangre();
            List<Servicios> serviciosListOld = persistentEmpleados.getServiciosList();
            List<Servicios> serviciosListNew = empleados.getServiciosList();
            List<Citas> citasListOld = persistentEmpleados.getCitasList();
            List<Citas> citasListNew = empleados.getCitasList();
            List<TurnosPorEmpleados> turnosPorEmpleadosListOld = persistentEmpleados.getTurnosPorEmpleadosList();
            List<TurnosPorEmpleados> turnosPorEmpleadosListNew = empleados.getTurnosPorEmpleadosList();
            List<Procedimientos> procedimientosListOld = persistentEmpleados.getProcedimientosList();
            List<Procedimientos> procedimientosListNew = empleados.getProcedimientosList();
            List<String> illegalOrphanMessages = null;
            for (Citas citasListOldCitas : citasListOld) {
                if (!citasListNew.contains(citasListOldCitas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Citas " + citasListOldCitas + " since its idEmpleado field is not nullable.");
                }
            }
            for (TurnosPorEmpleados turnosPorEmpleadosListOldTurnosPorEmpleados : turnosPorEmpleadosListOld) {
                if (!turnosPorEmpleadosListNew.contains(turnosPorEmpleadosListOldTurnosPorEmpleados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TurnosPorEmpleados " + turnosPorEmpleadosListOldTurnosPorEmpleados + " since its idEmpleado field is not nullable.");
                }
            }
            for (Procedimientos procedimientosListOldProcedimientos : procedimientosListOld) {
                if (!procedimientosListNew.contains(procedimientosListOldProcedimientos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Procedimientos " + procedimientosListOldProcedimientos + " since its idEmpleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idBarrioNew != null) {
                idBarrioNew = em.getReference(idBarrioNew.getClass(), idBarrioNew.getIdBarrio());
                empleados.setIdBarrio(idBarrioNew);
            }
            if (idCargoNew != null) {
                idCargoNew = em.getReference(idCargoNew.getClass(), idCargoNew.getIdCargo());
                empleados.setIdCargo(idCargoNew);
            }
            if (idCiudadNew != null) {
                idCiudadNew = em.getReference(idCiudadNew.getClass(), idCiudadNew.getIdCiudad());
                empleados.setIdCiudad(idCiudadNew);
            }
            if (idGeneroNew != null) {
                idGeneroNew = em.getReference(idGeneroNew.getClass(), idGeneroNew.getIdGenero());
                empleados.setIdGenero(idGeneroNew);
            }
            if (idPerfilNew != null) {
                idPerfilNew = em.getReference(idPerfilNew.getClass(), idPerfilNew.getIdPerfil());
                empleados.setIdPerfil(idPerfilNew);
            }
            if (idTipoContratoNew != null) {
                idTipoContratoNew = em.getReference(idTipoContratoNew.getClass(), idTipoContratoNew.getIdTipoContrato());
                empleados.setIdTipoContrato(idTipoContratoNew);
            }
            if (idTipoDocumentoNew != null) {
                idTipoDocumentoNew = em.getReference(idTipoDocumentoNew.getClass(), idTipoDocumentoNew.getIdTipoDoc());
                empleados.setIdTipoDocumento(idTipoDocumentoNew);
            }
            if (idTipoSangreNew != null) {
                idTipoSangreNew = em.getReference(idTipoSangreNew.getClass(), idTipoSangreNew.getIdTipoSangre());
                empleados.setIdTipoSangre(idTipoSangreNew);
            }
            List<Servicios> attachedServiciosListNew = new ArrayList<Servicios>();
            for (Servicios serviciosListNewServiciosToAttach : serviciosListNew) {
                serviciosListNewServiciosToAttach = em.getReference(serviciosListNewServiciosToAttach.getClass(), serviciosListNewServiciosToAttach.getIdServicio());
                attachedServiciosListNew.add(serviciosListNewServiciosToAttach);
            }
            serviciosListNew = attachedServiciosListNew;
            empleados.setServiciosList(serviciosListNew);
            List<Citas> attachedCitasListNew = new ArrayList<Citas>();
            for (Citas citasListNewCitasToAttach : citasListNew) {
                citasListNewCitasToAttach = em.getReference(citasListNewCitasToAttach.getClass(), citasListNewCitasToAttach.getIdCita());
                attachedCitasListNew.add(citasListNewCitasToAttach);
            }
            citasListNew = attachedCitasListNew;
            empleados.setCitasList(citasListNew);
            List<TurnosPorEmpleados> attachedTurnosPorEmpleadosListNew = new ArrayList<TurnosPorEmpleados>();
            for (TurnosPorEmpleados turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach : turnosPorEmpleadosListNew) {
                turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach = em.getReference(turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach.getClass(), turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach.getIdTurnosPorEmpl());
                attachedTurnosPorEmpleadosListNew.add(turnosPorEmpleadosListNewTurnosPorEmpleadosToAttach);
            }
            turnosPorEmpleadosListNew = attachedTurnosPorEmpleadosListNew;
            empleados.setTurnosPorEmpleadosList(turnosPorEmpleadosListNew);
            List<Procedimientos> attachedProcedimientosListNew = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListNewProcedimientosToAttach : procedimientosListNew) {
                procedimientosListNewProcedimientosToAttach = em.getReference(procedimientosListNewProcedimientosToAttach.getClass(), procedimientosListNewProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosListNew.add(procedimientosListNewProcedimientosToAttach);
            }
            procedimientosListNew = attachedProcedimientosListNew;
            empleados.setProcedimientosList(procedimientosListNew);
            empleados = em.merge(empleados);
            if (idBarrioOld != null && !idBarrioOld.equals(idBarrioNew)) {
                idBarrioOld.getEmpleadosList().remove(empleados);
                idBarrioOld = em.merge(idBarrioOld);
            }
            if (idBarrioNew != null && !idBarrioNew.equals(idBarrioOld)) {
                idBarrioNew.getEmpleadosList().add(empleados);
                idBarrioNew = em.merge(idBarrioNew);
            }
            if (idCargoOld != null && !idCargoOld.equals(idCargoNew)) {
                idCargoOld.getEmpleadosList().remove(empleados);
                idCargoOld = em.merge(idCargoOld);
            }
            if (idCargoNew != null && !idCargoNew.equals(idCargoOld)) {
                idCargoNew.getEmpleadosList().add(empleados);
                idCargoNew = em.merge(idCargoNew);
            }
            if (idCiudadOld != null && !idCiudadOld.equals(idCiudadNew)) {
                idCiudadOld.getEmpleadosList().remove(empleados);
                idCiudadOld = em.merge(idCiudadOld);
            }
            if (idCiudadNew != null && !idCiudadNew.equals(idCiudadOld)) {
                idCiudadNew.getEmpleadosList().add(empleados);
                idCiudadNew = em.merge(idCiudadNew);
            }
            if (idGeneroOld != null && !idGeneroOld.equals(idGeneroNew)) {
                idGeneroOld.getEmpleadosList().remove(empleados);
                idGeneroOld = em.merge(idGeneroOld);
            }
            if (idGeneroNew != null && !idGeneroNew.equals(idGeneroOld)) {
                idGeneroNew.getEmpleadosList().add(empleados);
                idGeneroNew = em.merge(idGeneroNew);
            }
            if (idPerfilOld != null && !idPerfilOld.equals(idPerfilNew)) {
                idPerfilOld.getEmpleadosList().remove(empleados);
                idPerfilOld = em.merge(idPerfilOld);
            }
            if (idPerfilNew != null && !idPerfilNew.equals(idPerfilOld)) {
                idPerfilNew.getEmpleadosList().add(empleados);
                idPerfilNew = em.merge(idPerfilNew);
            }
            if (idTipoContratoOld != null && !idTipoContratoOld.equals(idTipoContratoNew)) {
                idTipoContratoOld.getEmpleadosList().remove(empleados);
                idTipoContratoOld = em.merge(idTipoContratoOld);
            }
            if (idTipoContratoNew != null && !idTipoContratoNew.equals(idTipoContratoOld)) {
                idTipoContratoNew.getEmpleadosList().add(empleados);
                idTipoContratoNew = em.merge(idTipoContratoNew);
            }
            if (idTipoDocumentoOld != null && !idTipoDocumentoOld.equals(idTipoDocumentoNew)) {
                idTipoDocumentoOld.getEmpleadosList().remove(empleados);
                idTipoDocumentoOld = em.merge(idTipoDocumentoOld);
            }
            if (idTipoDocumentoNew != null && !idTipoDocumentoNew.equals(idTipoDocumentoOld)) {
                idTipoDocumentoNew.getEmpleadosList().add(empleados);
                idTipoDocumentoNew = em.merge(idTipoDocumentoNew);
            }
            if (idTipoSangreOld != null && !idTipoSangreOld.equals(idTipoSangreNew)) {
                idTipoSangreOld.getEmpleadosList().remove(empleados);
                idTipoSangreOld = em.merge(idTipoSangreOld);
            }
            if (idTipoSangreNew != null && !idTipoSangreNew.equals(idTipoSangreOld)) {
                idTipoSangreNew.getEmpleadosList().add(empleados);
                idTipoSangreNew = em.merge(idTipoSangreNew);
            }
            for (Servicios serviciosListOldServicios : serviciosListOld) {
                if (!serviciosListNew.contains(serviciosListOldServicios)) {
                    serviciosListOldServicios.getEmpleadosList().remove(empleados);
                    serviciosListOldServicios = em.merge(serviciosListOldServicios);
                }
            }
            for (Servicios serviciosListNewServicios : serviciosListNew) {
                if (!serviciosListOld.contains(serviciosListNewServicios)) {
                    serviciosListNewServicios.getEmpleadosList().add(empleados);
                    serviciosListNewServicios = em.merge(serviciosListNewServicios);
                }
            }
            for (Citas citasListNewCitas : citasListNew) {
                if (!citasListOld.contains(citasListNewCitas)) {
                    Empleados oldIdEmpleadoOfCitasListNewCitas = citasListNewCitas.getIdEmpleado();
                    citasListNewCitas.setIdEmpleado(empleados);
                    citasListNewCitas = em.merge(citasListNewCitas);
                    if (oldIdEmpleadoOfCitasListNewCitas != null && !oldIdEmpleadoOfCitasListNewCitas.equals(empleados)) {
                        oldIdEmpleadoOfCitasListNewCitas.getCitasList().remove(citasListNewCitas);
                        oldIdEmpleadoOfCitasListNewCitas = em.merge(oldIdEmpleadoOfCitasListNewCitas);
                    }
                }
            }
            for (TurnosPorEmpleados turnosPorEmpleadosListNewTurnosPorEmpleados : turnosPorEmpleadosListNew) {
                if (!turnosPorEmpleadosListOld.contains(turnosPorEmpleadosListNewTurnosPorEmpleados)) {
                    Empleados oldIdEmpleadoOfTurnosPorEmpleadosListNewTurnosPorEmpleados = turnosPorEmpleadosListNewTurnosPorEmpleados.getIdEmpleado();
                    turnosPorEmpleadosListNewTurnosPorEmpleados.setIdEmpleado(empleados);
                    turnosPorEmpleadosListNewTurnosPorEmpleados = em.merge(turnosPorEmpleadosListNewTurnosPorEmpleados);
                    if (oldIdEmpleadoOfTurnosPorEmpleadosListNewTurnosPorEmpleados != null && !oldIdEmpleadoOfTurnosPorEmpleadosListNewTurnosPorEmpleados.equals(empleados)) {
                        oldIdEmpleadoOfTurnosPorEmpleadosListNewTurnosPorEmpleados.getTurnosPorEmpleadosList().remove(turnosPorEmpleadosListNewTurnosPorEmpleados);
                        oldIdEmpleadoOfTurnosPorEmpleadosListNewTurnosPorEmpleados = em.merge(oldIdEmpleadoOfTurnosPorEmpleadosListNewTurnosPorEmpleados);
                    }
                }
            }
            for (Procedimientos procedimientosListNewProcedimientos : procedimientosListNew) {
                if (!procedimientosListOld.contains(procedimientosListNewProcedimientos)) {
                    Empleados oldIdEmpleadoOfProcedimientosListNewProcedimientos = procedimientosListNewProcedimientos.getIdEmpleado();
                    procedimientosListNewProcedimientos.setIdEmpleado(empleados);
                    procedimientosListNewProcedimientos = em.merge(procedimientosListNewProcedimientos);
                    if (oldIdEmpleadoOfProcedimientosListNewProcedimientos != null && !oldIdEmpleadoOfProcedimientosListNewProcedimientos.equals(empleados)) {
                        oldIdEmpleadoOfProcedimientosListNewProcedimientos.getProcedimientosList().remove(procedimientosListNewProcedimientos);
                        oldIdEmpleadoOfProcedimientosListNewProcedimientos = em.merge(oldIdEmpleadoOfProcedimientosListNewProcedimientos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleados.getIdEmpleado();
                if (findEmpleados(id) == null) {
                    throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.");
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
            Empleados empleados;
            try {
                empleados = em.getReference(Empleados.class, id);
                empleados.getIdEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Citas> citasListOrphanCheck = empleados.getCitasList();
            for (Citas citasListOrphanCheckCitas : citasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the Citas " + citasListOrphanCheckCitas + " in its citasList field has a non-nullable idEmpleado field.");
            }
            List<TurnosPorEmpleados> turnosPorEmpleadosListOrphanCheck = empleados.getTurnosPorEmpleadosList();
            for (TurnosPorEmpleados turnosPorEmpleadosListOrphanCheckTurnosPorEmpleados : turnosPorEmpleadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the TurnosPorEmpleados " + turnosPorEmpleadosListOrphanCheckTurnosPorEmpleados + " in its turnosPorEmpleadosList field has a non-nullable idEmpleado field.");
            }
            List<Procedimientos> procedimientosListOrphanCheck = empleados.getProcedimientosList();
            for (Procedimientos procedimientosListOrphanCheckProcedimientos : procedimientosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the Procedimientos " + procedimientosListOrphanCheckProcedimientos + " in its procedimientosList field has a non-nullable idEmpleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Barrios idBarrio = empleados.getIdBarrio();
            if (idBarrio != null) {
                idBarrio.getEmpleadosList().remove(empleados);
                idBarrio = em.merge(idBarrio);
            }
            Cargos idCargo = empleados.getIdCargo();
            if (idCargo != null) {
                idCargo.getEmpleadosList().remove(empleados);
                idCargo = em.merge(idCargo);
            }
            Ciudades idCiudad = empleados.getIdCiudad();
            if (idCiudad != null) {
                idCiudad.getEmpleadosList().remove(empleados);
                idCiudad = em.merge(idCiudad);
            }
            Generos idGenero = empleados.getIdGenero();
            if (idGenero != null) {
                idGenero.getEmpleadosList().remove(empleados);
                idGenero = em.merge(idGenero);
            }
            Perfiles idPerfil = empleados.getIdPerfil();
            if (idPerfil != null) {
                idPerfil.getEmpleadosList().remove(empleados);
                idPerfil = em.merge(idPerfil);
            }
            TiposContrato idTipoContrato = empleados.getIdTipoContrato();
            if (idTipoContrato != null) {
                idTipoContrato.getEmpleadosList().remove(empleados);
                idTipoContrato = em.merge(idTipoContrato);
            }
            TiposDocumento idTipoDocumento = empleados.getIdTipoDocumento();
            if (idTipoDocumento != null) {
                idTipoDocumento.getEmpleadosList().remove(empleados);
                idTipoDocumento = em.merge(idTipoDocumento);
            }
            TiposSangre idTipoSangre = empleados.getIdTipoSangre();
            if (idTipoSangre != null) {
                idTipoSangre.getEmpleadosList().remove(empleados);
                idTipoSangre = em.merge(idTipoSangre);
            }
            List<Servicios> serviciosList = empleados.getServiciosList();
            for (Servicios serviciosListServicios : serviciosList) {
                serviciosListServicios.getEmpleadosList().remove(empleados);
                serviciosListServicios = em.merge(serviciosListServicios);
            }
            em.remove(empleados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleados> findEmpleadosEntities() {
        return findEmpleadosEntities(true, -1, -1);
    }

    public List<Empleados> findEmpleadosEntities(int maxResults, int firstResult) {
        return findEmpleadosEntities(false, maxResults, firstResult);
    }

    private List<Empleados> findEmpleadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleados.class));
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

    public Empleados findEmpleados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleados.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleados> rt = cq.from(Empleados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
