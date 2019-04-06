/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.logistics.backend.entity;

import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



/**
 *
 * @author sigifredo
 */
@Entity
@Table (name = "usuario") 
@Data 
@Builder 
@AllArgsConstructor 
@NoArgsConstructor 
public class Usuario{
    
    @Id
    private Long  numeroempleado;
    @Column
    private String nombres;	
    @Column
    private String apellido1;	
    @Column
    private String apellido2;	
    @Column
    private String cedula;	
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechanacimiento;	
    @Column
    private String genero;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaingreso;	
    @Column
    private String cargo;
    //@Column
    //private int jefe;
    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL )
    @JoinColumn(name="jefe")
    @Fetch(FetchMode.SELECT)
    private Usuario jefe;
    @Column
    private String zona;
    @Column
    private String municipio;
    @Column
    private String departamento;
    @Column
    private double ventas2019;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String imagen;
    @Column
    private String celular;
    
//    @OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "jefe" )
//    @Fetch(FetchMode.SELECT)
//    private List<Usuario> subalternos =  new ArrayList<Usuario>();

}
