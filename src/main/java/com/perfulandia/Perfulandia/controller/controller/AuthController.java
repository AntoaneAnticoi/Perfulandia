package com.perfulandia.Perfulandia.controller.controller;

import com.perfulandia.Perfulandia.model.Usuario;
import com.perfulandia.Perfulandia.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<EntityModel<Usuario>> registrar(@Valid @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);

        EntityModel<Usuario> recurso = EntityModel.of(nuevoUsuario,
                linkTo(methodOn(AuthController.class).getUsuarioPorId(nuevoUsuario.getId())).withSelfRel(),
                linkTo(methodOn(AuthController.class).login(null)).withRel("login")
        );

        return ResponseEntity.created(
                linkTo(methodOn(AuthController.class).getUsuarioPorId(nuevoUsuario.getId())).toUri()
        ).body(recurso);
    }

    @PostMapping("/login")
    public ResponseEntity<EntityModel<Usuario>> login(@RequestBody Usuario usuario) {
        Optional<Usuario> existente = usuarioService.buscarPorEmail(usuario.getEmail());

        if (existente.isPresent() && existente.get().getPassword().equals(usuario.getPassword())) {
            Usuario user = existente.get();

            EntityModel<Usuario> usuarioModel = EntityModel.of(user,
                    linkTo(methodOn(AuthController.class).login(usuario)).withSelfRel(),
                    linkTo(methodOn(AuthController.class).registrar(new Usuario())).withRel("registrar")
            );

            return ResponseEntity.ok(usuarioModel);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioPorId(@PathVariable Long id) {
        return usuarioService.getUsuarioPorId(id)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}


