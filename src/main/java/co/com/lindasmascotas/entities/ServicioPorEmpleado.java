/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ISABEL MEDINA
 */
@Entity
@Table(name = "servicio_por_empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServicioPorEmpleado.findAll", query = "SELECT s FROM ServicioPorEmpleado s")
    , @NamedQuery(name = "ServicioPorEmpleado.findByIdServEmpl", query = "SELECT s FROM ServicioPorEmpleado s WHERE s.idServEmpl = :idServEmpl")})
public class ServicioPorEmpleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_serv_empl")
    private Integer idServEmpl;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false)
    private Empleados idEmpleado;
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio")
    @ManyToOne(optional = false)
    private Servicios idServicio;

    public ServicioPorEmpleado() {
    }

    public ServicioPorEmpleado(Integer idServEmpl) {
        this.idServEmpl = idServEmpl;
    }

    public Integer getIdServEmpl() {
        return idServEmpl;
    }

    public void setIdServEmpl(Integer idServEmpl) {
        this.idServEmpl = idServEmpl;
    }

    public Empleados getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleados idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Servicios getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Servicios idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServEmpl != null ? idServEmpl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicioPorEmpleado)) {
            return false;
        }
        ServicioPorEmpleado other = (ServicioPorEmpleado) object;
        if ((this.idServEmpl == null && other.idServEmpl != null) || (this.idServEmpl != null && !this.idServEmpl.equals(other.idServEmpl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.lindasmascotas.entities.ServicioPorEmpleado[ idServEmpl=" + idServEmpl + " ]";
    }
    
}
