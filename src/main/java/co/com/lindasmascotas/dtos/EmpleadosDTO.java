
package co.com.lindasmascotas.dtos;

import java.util.Date;
import java.util.List;

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

    public EmpleadosDTO() {
    }

    public EmpleadosDTO(Integer idEmpleado, String nombreEmpleado, String apellidosEmpleado, Date fechaNacimiento, String correoElectronico, String direccion, Integer telefonoFijo, long telefonoMovil, boolean estadoEmpleado, Date fechaContratoInicial, Date fechaContratoFinal, String tipoRh) {
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

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidosEmpleado() {
        return apellidosEmpleado;
    }

    public void setApellidosEmpleado(String apellidosEmpleado) {
        this.apellidosEmpleado = apellidosEmpleado;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(Integer telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public long getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(long telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public boolean isEstadoEmpleado() {
        return estadoEmpleado;
    }

    public void setEstadoEmpleado(boolean estadoEmpleado) {
        this.estadoEmpleado = estadoEmpleado;
    }

    public Date getFechaContratoInicial() {
        return fechaContratoInicial;
    }

    public void setFechaContratoInicial(Date fechaContratoInicial) {
        this.fechaContratoInicial = fechaContratoInicial;
    }

    public Date getFechaContratoFinal() {
        return fechaContratoFinal;
    }

    public void setFechaContratoFinal(Date fechaContratoFinal) {
        this.fechaContratoFinal = fechaContratoFinal;
    }

    public String getTipoRh() {
        return tipoRh;
    }

    public void setTipoRh(String tipoRh) {
        this.tipoRh = tipoRh;
    }

    public List<CitasDTO> getCitasList() {
        return citasList;
    }

    public void setCitasList(List<CitasDTO> citasList) {
        this.citasList = citasList;
    }

    public List<ServicioPorEmpleadoDTO> getServicioPorEmpleadoList() {
        return servicioPorEmpleadoList;
    }

    public void setServicioPorEmpleadoList(List<ServicioPorEmpleadoDTO> servicioPorEmpleadoList) {
        this.servicioPorEmpleadoList = servicioPorEmpleadoList;
    }

    public List<ProcedimientosDTO> getProcedimientosList() {
        return procedimientosList;
    }

    public void setProcedimientosList(List<ProcedimientosDTO> procedimientosList) {
        this.procedimientosList = procedimientosList;
    }
    
}