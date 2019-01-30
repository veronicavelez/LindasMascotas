/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.dtos;

import co.com.lindasmascotas.entities.Especies;
import co.com.lindasmascotas.entities.Mascotas;
import co.com.lindasmascotas.entities.Razas;
import co.com.lindasmascotas.entities.Sexos;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Veronica
 */
public class MascotasDTO {
    private Integer idMascota;
    private String nombreMascota;
    private Date fechaNacimiento;
    private BigDecimal peso;
    private boolean estado;
    private boolean vive;
    private Especies idEspecie;
    private Razas idRaza;
    private Sexos idSexo;

    private MascotasDTO(Integer idMascota, String nombreMascota, Date fechaNacimiento, BigDecimal peso, boolean estado, boolean vive, Especies idEspecie, Razas idRaza, Sexos idSexo) {
        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.estado = estado;
        this.vive = vive;
        this.idEspecie = idEspecie;
        this.idRaza = idRaza;
        this.idSexo = idSexo;
    }

    public static MascotasDTO setData(Mascotas m){
        return new MascotasDTO(m.getIdMascota(), m.getNombreMascota(), m.getFechaNacimiento(), m.getPeso(), m.getEstado(), m.getVive(), m.getIdEspecie(), m.getIdRaza(), m.getIdSexo());
    }
    
    public Integer getIdMascota() {
        return idMascota;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public boolean isEstado() {
        return estado;
    }

    public boolean isVive() {
        return vive;
    }

    public Especies getIdEspecie() {
        return idEspecie;
    }

    public Razas getIdRaza() {
        return idRaza;
    }

    public Sexos getIdSexo() {
        return idSexo;
    }
    
    
}
