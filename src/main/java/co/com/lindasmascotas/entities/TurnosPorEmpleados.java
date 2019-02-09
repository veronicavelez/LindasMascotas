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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Isa
 */
@Entity
@Table(name = "turnos_por_empleados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TurnosPorEmpleados.findAll", query = "SELECT t FROM TurnosPorEmpleados t")
    , @NamedQuery(name = "TurnosPorEmpleados.findByIdTurnosPorEmpl", query = "SELECT t FROM TurnosPorEmpleados t WHERE t.idTurnosPorEmpl = :idTurnosPorEmpl")
    , @NamedQuery(name = "TurnosPorEmpleados.findIdTurnosPorEmplByEmpleado", query = "SELECT t FROM TurnosPorEmpleados t WHERE t.idEmpleado.idEmpleado = :idEmpleado ORDER BY t.idDetalleTurnos.dias")})
public class TurnosPorEmpleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_turnos_por_empl")
    private Integer idTurnosPorEmpl;
    @JoinColumn(name = "id_detalle_turnos", referencedColumnName = "id_detalle_turno")
    @ManyToOne(optional = false)
    private Detalleturnos idDetalleTurnos;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false)
    private Empleados idEmpleado;

    public TurnosPorEmpleados() {
    }

    public TurnosPorEmpleados(Integer idTurnosPorEmpl) {
        this.idTurnosPorEmpl = idTurnosPorEmpl;
    }

    public Integer getIdTurnosPorEmpl() {
        return idTurnosPorEmpl;
    }

    public void setIdTurnosPorEmpl(Integer idTurnosPorEmpl) {
        this.idTurnosPorEmpl = idTurnosPorEmpl;
    }

    public Detalleturnos getIdDetalleTurnos() {
        return idDetalleTurnos;
    }

    public void setIdDetalleTurnos(Detalleturnos idDetalleTurnos) {
        this.idDetalleTurnos = idDetalleTurnos;
    }

    public Empleados getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleados idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTurnosPorEmpl != null ? idTurnosPorEmpl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TurnosPorEmpleados)) {
            return false;
        }
        TurnosPorEmpleados other = (TurnosPorEmpleados) object;
        if ((this.idTurnosPorEmpl == null && other.idTurnosPorEmpl != null) || (this.idTurnosPorEmpl != null && !this.idTurnosPorEmpl.equals(other.idTurnosPorEmpl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.lindasmascotas.entities.TurnosPorEmpleados[ idTurnosPorEmpl=" + idTurnosPorEmpl + " ]";
    }
    
}
