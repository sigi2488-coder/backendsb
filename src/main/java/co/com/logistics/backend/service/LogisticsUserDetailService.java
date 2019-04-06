/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.logistics.backend.service;

import co.com.logistics.backend.Repository.UsuarioRepository;
import co.com.logistics.backend.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author sigifredo
 */
@Service
public class LogisticsUserDetailService implements UserDetailsService {
 
    @Autowired
    private UsuarioRepository userRepository;
 
    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new LogisticsUserDetail(user);
    }
}