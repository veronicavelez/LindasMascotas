/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Isa
 */
@Entity
@Table(name = "detalleturnos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detalleturnos.findAll", query = "SELECT d FROM Detalleturnos d")
    , @NamedQuery(name = "Detalleturnos.findByIdDetalleTurno", query = "SELECT d FROM Detalleturnos d WHERE d.idDetalleTurno = :idDetalleTurno")
    , @NamedQuery(name = "Detalleturnos.findByDias", query = "SELECT d FROM Detalleturnos d WHERE d.dias = :dias")
    , @NamedQuery(name = "Detalleturnos.findByHoraInicial", query = "SELECT d FROM Detalleturnos d WHERE d.horaInicial = :horaInicial")
    , @NamedQuery(name = "Detalleturnos.findByHoraFinal", query = "SELECT d FROM Detalleturnos d WHERE d.horaFinal = :horaFinal")})
public class Detalleturnos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_detalle_turno")
    private Integer idDetalleTurno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dias")
    private int dias;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_inicial")
    @Temporal(TemporalType.TIME)
    private Date horaInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_final")
    @Temporal(TemporalType.TIME)
    private Date horaFinal;
    @JoinColumn(name = "id_turno", referencedColumnName = "id_turno")
    @ManyToOne(optional = false)
    private Turnos idTurno;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDetalleTurnos")
    private List<TurnosPorEmpleados> turnosPorEmpleadosList;

    public Detalleturnos() {
    }

    public Detalleturnos(Integer idDetalleTurno) {
        this.idDetalleTurno = idDetalleTurno;
    }

    public Detalleturnos(Integer idDetalleTurno, int dias, Date horaInicial, Date horaFinal) {
        this.idDetalleTurno = idDetalleTurno;
        this.dias = dias;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
    }

    public Integer getIdDetalleTurno() {
        return idDetalleTurno;
    }

    public void setIdDetalleTurno(Integer idDetalleTurno) {
        this.idDetalleTurno = idDetalleTurno;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public Date getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Date horaInicial) {
        this.horaInicial = horaInicial;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Turnos getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Turnos idTurno) {
        this.idTurno = idTurno;
    }

    @XmlTransient
    @JsonIgnore
    public List<TurnosPorEmpleados> getTurnosPorEmpleadosList() {
        return turnosPorEmpleadosList;
    }

    public void setTurnosPorEmpleadosList(List<TurnosPorEmpleados> turnosPorEmpleadosList) {
        this.turnosPorEmpleadosList = turnosPorEmpleadosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleTurno != null ? idDetalleTurno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detalleturnos)) {
            return false;
        }
        Detalleturnos other = (Detalleturnos) object;
        if ((this.idDetalleTurno == null && other.idDetalleTurno != null) || (this.idDetalleTurno != null && !this.idDetalleTurno.equals(other.idDetalleTurno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.lindasmascotas.entities.Detalleturnos[ idDetalleTurno=" + idDetalleTurno + " ]";
    }
    
}
