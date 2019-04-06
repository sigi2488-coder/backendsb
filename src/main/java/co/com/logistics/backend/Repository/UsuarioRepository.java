/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.logistics.backend.Repository;

import co.com.logistics.backend.entity.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author sigifredo
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Usuario findByEmail(String email);
    
    @Query(value = "select numeroempleado, nombres, apellido1, apellido2, cedula, fechanacimiento, genero, fechaingreso, cargo, jefe, zona, municipio, departamento, ventas2019, email, '' as password, imagen, celular from usuario  where jefe = :jefe ", nativeQuery = true)
    List<Usuario> subalternos(@Param("jefe") Long jefe);
    
    @Query(value = "select numeroempleado, nombres, apellido1, apellido2, cedula, fechanacimiento, genero, fechaingreso, cargo, jefe, zona, municipio, departamento, ventas2019, email, '' as password, imagen, celular from usuario  where email = :email ", nativeQuery = true)
     Usuario getUsuarioAuth(@Param("email") String email);
    //Optional<Usuario> findByEmailOp(String email);
}
