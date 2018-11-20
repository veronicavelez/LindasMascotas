/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Isa
 */
@Entity
@Table(name = "procedimientos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Procedimientos.findAll", query = "SELECT p FROM Procedimientos p")
    , @NamedQuery(name = "Procedimientos.findByIdProcedimiento", query = "SELECT p FROM Procedimientos p WHERE p.idProcedimiento = :idProcedimiento")
    , @NamedQuery(name = "Procedimientos.findByNombreProcedimiento", query = "SELECT p FROM Procedimientos p WHERE p.nombreProcedimiento = :nombreProcedimiento")
    , @NamedQuery(name = "Procedimientos.findByDescripcion", query = "SELECT p FROM Procedimientos p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Procedimientos.findByPeso", query = "SELECT p FROM Procedimientos p WHERE p.peso = :peso")
    , @NamedQuery(name = "Procedimientos.findByFecha", query = "SELECT p FROM Procedimientos p WHERE p.fecha = :fecha")})
public class Procedimientos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_procedimiento")
    private Integer idProcedimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_procedimiento")
    private String nombreProcedimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso")
    private BigDecimal peso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false)
    private Empleados idEmpleado;
    @JoinColumn(name = "id_mascota", referencedColumnName = "id_mascota")
    @ManyToOne(optional = false)
    private Mascotas idMascota;
    @JoinColumn(name = "id_tipo_servicio", referencedColumnName = "id_servicio")
    @ManyToOne(optional = false)
    private Servicios idTipoServicio;
    @JoinColumn(name = "id_sexo", referencedColumnName = "id_sexo")
    @ManyToOne(optional = false)
    private Sexos idSexo;
    @JoinColumn(name = "id_vacuna", referencedColumnName = "id_vacuna")
    @ManyToOne
    private Vacunas idVacuna;

    public Procedimientos() {
    }

    public Procedimientos(Integer idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }

    public Procedimientos(Integer idProcedimiento, String nombreProcedimiento, String descripcion, BigDecimal peso, Date fecha) {
        this.idProcedimiento = idProcedimiento;
        this.nombreProcedimiento = nombreProcedimiento;
        this.descripcion = descripcion;
        this.peso = peso;
        this.fecha = fecha;
    }

    public Integer getIdProcedimiento() {
        return idProcedimiento;
    }

    public void setIdProcedimiento(Integer idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }

    public String getNombreProcedimiento() {
        return nombreProcedimiento;
    }

    public void setNombreProcedimiento(String nombreProcedimiento) {
        this.nombreProcedimiento = nombreProcedimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Empleados getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleados idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Mascotas getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Mascotas idMascota) {
        this.idMascota = idMascota;
    }

    public Servicios getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(Servicios idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public Sexos getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Sexos idSexo) {
        this.idSexo = idSexo;
    }

    public Vacunas getIdVacuna() {
        return idVacuna;
    }

    public void setIdVacuna(Vacunas idVacuna) {
        this.idVacuna = idVacuna;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProcedimiento != null ? idProcedimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Procedimientos)) {
            return false;
        }
        Procedimientos other = (Procedimientos) object;
        if ((this.idProcedimiento == null && other.idProcedimiento != null) || (this.idProcedimiento != null && !this.idProcedimiento.equals(other.idProcedimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.lindasmascotas.entities.Procedimientos[ idProcedimiento=" + idProcedimiento + " ]";
    }
    
}
