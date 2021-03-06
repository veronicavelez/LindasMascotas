/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.dtos;

import co.com.lindasmascotas.entities.Especies;
import co.com.lindasmascotas.entities.Mascotas;
import co.com.lindasmascotas.entities.Procedimientos;
import co.com.lindasmascotas.entities.Razas;
import co.com.lindasmascotas.entities.Sexos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Isabel Medina
 */
public class MascotasDTO {
    
    private Integer idMascota;
    private String nombreMascota;
    private Date fechaNacimiento;
    private BigDecimal peso;
    private boolean estado;
    private boolean vive;
    private Especies idEspecie;
    private PropietariosDTO idPropietario;
    private Razas idRaza;
    private Sexos idSexo;
//    private List<ProcedimientosDTO> procedimientosList;

    public MascotasDTO() {
    }

    public MascotasDTO(Integer idMascota, String nombreMascota, Date fechaNacimiento, BigDecimal peso, boolean estado, boolean vive, Especies idEspecie, PropietariosDTO idPropietario, Razas idRaza, Sexos idSexo/*, List<ProcedimientosDTO> procedimientosList*/) {
        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.estado = estado;
        this.vive = vive;
        this.idEspecie = idEspecie;
        this.idPropietario = idPropietario;
        this.idRaza = idRaza;
        this.idSexo = idSexo;
//        this.procedimientosList = procedimientosList;
    }
    
    public static MascotasDTO setData(Mascotas m){
//        List<ProcedimientosDTO> procedimientosList = new ArrayList<ProcedimientosDTO>();
//        
//        for(Procedimientos p: m.getProcedimientosList()){
//            procedimientosList.add(ProcedimientosDTO.setData(p));
//        }
        
        return new MascotasDTO(m.getIdMascota(), m.getNombreMascota(), m.getFechaNacimiento(),
                m.getPeso(), m.getEstado(), m.getVive(), m.getIdEspecie(), PropietariosDTO.setData(m.getIdPropietario()), m.getIdRaza(), m.getIdSexo()/*, procedimientosList*/);
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

    public boolean getEstado() {
        return estado;
    }

    public boolean getVive() {
        return vive;
    }

    public Especies getIdEspecie() {
        return idEspecie;
    }

    public PropietariosDTO getIdPropietario() {
        return idPropietario;
    }

    public Razas getIdRaza() {
        return idRaza;
    }

    public Sexos getIdSexo() {
        return idSexo;
    }

//    public List<ProcedimientosDTO> getProcedimientosList() {
//        return procedimientosList;
//    }
   
}
