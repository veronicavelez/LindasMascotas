/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.dtos;

import co.com.lindasmascotas.entities.Barrios;
import co.com.lindasmascotas.entities.Ciudades;
import co.com.lindasmascotas.entities.Generos;
import co.com.lindasmascotas.entities.Propietarios;
import co.com.lindasmascotas.entities.TiposDocumento;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class PropietariosDTO {
    
    private Integer idPropietario;
    private String nombrePropietario;
    private String apellidosPropietario;
    private Date fechaNacimiento;
    private String correoElectronico;
    private String direccion;
    private Integer telefonoFijo;
    private long telefonoMovil;
    private boolean estado;
    private Barrios idBarrio;
    private Ciudades idCiudad;
    private Generos idGenero;
    private TiposDocumento idTipoDocumento;
    //private List<CitasDTO> citasList;
    //private List<Mascotas> mascotasList;

    private PropietariosDTO(Integer idPropietario, String nombrePropietario, String apellidosPropietario, Date fechaNacimiento, String correoElectronico, String direccion, Integer telefonoFijo, long telefonoMovil, boolean estado, Barrios idBarrio, Ciudades idCiudad, Generos idGenero, TiposDocumento idTipoDocumento) {
        this.idPropietario = idPropietario;
        this.nombrePropietario = nombrePropietario;
        this.apellidosPropietario = apellidosPropietario;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.telefonoFijo = telefonoFijo;
        this.telefonoMovil = telefonoMovil;
        this.estado = estado;
        this.idBarrio = idBarrio;
        this.idCiudad = idCiudad;
        this.idGenero = idGenero;
        this.idTipoDocumento = idTipoDocumento;
    }
    
    public static PropietariosDTO setData(Propietarios p){
        return new PropietariosDTO(p.getIdPropietario(), p.getNombrePropietario(),p.getApellidosPropietario(),
                p.getFechaNacimiento(), p.getCorreoElectronico(), p.getDireccion(), p.getTelefonoFijo(),
                p.getTelefonoMovil(), p.getEstado(), p.getIdBarrio(), p.getIdCiudad(), p.getIdGenero(),
                p.getIdTipoDocumento());
    }

    public Integer getIdPropietario() {
        return idPropietario;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public String getApellidosPropietario() {
        return apellidosPropietario;
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

    public boolean isEstado() {
        return estado;
    }

    public Barrios getIdBarrio() {
        return idBarrio;
    }

    public Ciudades getIdCiudad() {
        return idCiudad;
    }

    public Generos getIdGenero() {
        return idGenero;
    }

    public TiposDocumento getIdTipoDocumento() {
        return idTipoDocumento;
    }
   
       
}
