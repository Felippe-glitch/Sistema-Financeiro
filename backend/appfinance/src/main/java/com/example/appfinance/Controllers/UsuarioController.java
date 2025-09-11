package com.example.appfinance.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.appfinance.Models.Usuario;
import com.example.appfinance.Models.Usuario.CreateUsuario;
import com.example.appfinance.Models.Usuario.UpdateUsuario;
import com.example.appfinance.Services.UsuarioService;

import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/usuario")
@Validated
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Validated(CreateUsuario.class)
    public ResponseEntity<Void> postUsuario(@RequestBody Usuario user) {
        this.usuarioService.createUsuario(user);
        URI URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getIdUsuario()).toUri();

        return ResponseEntity.created(URI).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id){
        Usuario usuario = this.usuarioService.getUsuario(id);
        return ResponseEntity.ok().body(usuario);
    }

    @PutMapping("/{id}")
    @Validated(UpdateUsuario.class)
    public ResponseEntity<Void> putUsuario(@Valid @RequestBody Usuario newUsuario, @PathVariable Long id){
        newUsuario.setIdUsuario(id);
        usuarioService.updateUsuario(newUsuario);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){
        this.usuarioService.deleteUsuario(id);
         
        return ResponseEntity.noContent().build();
    }
    
}
