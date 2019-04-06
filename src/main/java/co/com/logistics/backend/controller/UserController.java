/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.logistics.backend.controller;

import co.com.logistics.backend.Repository.UsuarioRepository;
import co.com.logistics.backend.entity.Usuario;
import java.util.List;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sigifredo
 */
@RestController
//@RequestMapping("/users")
public class UserController {
    
    private UsuarioRepository usuarios;
    
    public UserController(UsuarioRepository usuarios){
        this.usuarios = usuarios;
    }
    
    @GetMapping("/users")
    public ResponseEntity all() {
        return ok(this.usuarios.findAll());
    }
    
    
  /*  @GetMapping("/user/{numero}")
    public ResponseEntity get(@PathVariable("numero") Long numero) {
        return ok(this.usuarios.findById(numero).orElse(null));
    }*/
    
    @GetMapping("/users/{email}")
    public ResponseEntity getEmail(@PathVariable("email") String email) {
        return ok(this.usuarios.getUsuarioAuth(email));
    }
    
    @GetMapping("/subalternos/{jefe}")
    public List<Usuario> getSubalternos(@PathVariable("jefe") Long jefe) {
        return this.usuarios.subalternos(jefe);
    }
}
