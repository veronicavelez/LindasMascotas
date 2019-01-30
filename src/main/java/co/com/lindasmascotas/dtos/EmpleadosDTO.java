
package co.com.lindasmascotas.dtos;

import java.util.Date;
import java.util.List;
import co.com.lindasmascotas.entities.Empleados;

public class EmpleadosDTO {
    
    private Integer idEmpleado;
    private String nombreEmpleado;
    private String apellidosEmpleado;
    private Date fechaNacimiento;
    private String correoElectronico;
    private String direccion;
    private Integer telefonoFijo;
    private long telefonoMovil;
    private boolean estadoEmpleado;
    private Date fechaContratoInicial;
    private Date fechaContratoFinal;
    private String tipoRh;
    private List<CitasDTO> citasList;
    private List<ServicioPorEmpleadoDTO> servicioPorEmpleadoList;
   // private List<TurnosPorEmpleados> turnosPorEmpleadosList;
//    private Barrios idBarrio;
//    private Cargos idCargo;
//    private Ciudades idCiudad;
//    private Generos idGenero;
//    private Perfiles idPerfil;
//    private TiposContrato idTipoContrato;
//    private TiposDocumento idTipoDocumento;
//    private TiposSangre idTipoSangre;
    private List<ProcedimientosDTO> procedimientosList;

    private EmpleadosDTO(Integer idEmpleado, String nombreEmpleado, String apellidosEmpleado, Date fechaNacimiento, String correoElectronico, String direccion, Integer telefonoFijo, long telefonoMovil, boolean estadoEmpleado, Date fechaContratoInicial, Date fechaContratoFinal, String tipoRh) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidosEmpleado = apellidosEmpleado;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.telefonoFijo = telefonoFijo;
        this.telefonoMovil = telefonoMovil;
        this.estadoEmpleado = estadoEmpleado;
        this.fechaContratoInicial = fechaContratoInicial;
        this.fechaContratoFinal = fechaContratoFinal;
        this.tipoRh = tipoRh;
    }

   public static EmpleadosDTO setData(Empleados e){
       return new EmpleadosDTO(e.getIdEmpleado(), e.getNombreEmpleado(), e.getApellidosEmpleado(),
               e.getFechaNacimiento(), e.getCorreoElectronico(), e.getDireccion(),
               e.getTelefonoFijo(), e.getTelefonoMovil(), e.getEstadoEmpleado(), e.getFechaContratoInicial(), 
               e.getFechaContratoFinal(), e.getTipoRh());
   }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public String getApellidosEmpleado() {
        return apellidosEmpleado;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public Integer getTelefonoFijo() {
        return telefonoFijo;
    }

    public long getTelefonoMovil() {
        return telefonoMovil;
    }

    public boolean isEstadoEmpleado() {
        return estadoEmpleado;
    }

    public Date getFechaContratoInicial() {
        return fechaContratoInicial;
    }

    public Date getFechaContratoFinal() {
        return fechaContratoFinal;
    }

    public String getTipoRh() {
        return tipoRh;
    }

    public List<CitasDTO> getCitasList() {
        return citasList;
    }

    public List<ServicioPorEmpleadoDTO> getServicioPorEmpleadoList() {
        return servicioPorEmpleadoList;
    }

    public List<ProcedimientosDTO> getProcedimientosList() {
        return procedimientosList;
    }
   
   
}