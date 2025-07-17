package com.aasencios.taskapi.controller;

import com.aasencios.taskapi.dto.UserAdminDTO;
import com.aasencios.taskapi.model.Role;
import com.aasencios.taskapi.model.User;
import com.aasencios.taskapi.repository.UserRepository;
import com.aasencios.taskapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final UserService userService;

    public AdminController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // ✅ 1. Promueve a un usuario a ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/promote/{email}")
    public ResponseEntity<?> promoteToAdmin(@PathVariable String email) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            user.setRole(Role.ADMIN); // Asegúrate que sea enum y no null
            userRepository.save(user);

            return ResponseEntity.ok("Usuario promovido a ADMIN correctamente");

        } catch (Exception e) {
            e.printStackTrace(); // 👈 Imprime en consola
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Error interno del servidor", "message", e.getMessage())
            );
        }
    }

    // ✅ 2. Endpoint de prueba solo accesible por ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public ResponseEntity<String> testAdminAccess() {
        return ResponseEntity.ok("Acceso permitido solo a ADMIN");
    }

    // ✅ 3. Endpoint de listado de usuarios
    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserAdminDTO>> listarUsuarios() {
        List<User> usuarios = userRepository.findAll();
        List<UserAdminDTO> respuesta = usuarios.stream().map(user -> {
            UserAdminDTO dto = new UserAdminDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole().name());
            dto.setActive(user.isEnabled());
            return dto;
        }).toList();

        return ResponseEntity.ok(respuesta);
    }


    //  ✅ 4. Endpoint de desactivación de usuario

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deactivate/{email}")
    public ResponseEntity<String> deactivateUser(@PathVariable String email) {
        userService.deactivateUserByEmail(email);
        return ResponseEntity.ok("Usuario desactivado correctamente");
    }

    // ✅ 5. Endpoint de listado de usuarios inactivos

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios/inactivos")
    public ResponseEntity<List<UserAdminDTO>> listarUsuariosInactivos() {
        List<User> inactivos = userRepository.findByEnabledFalse();
        List<UserAdminDTO> respuesta = inactivos.stream().map(user -> {
            UserAdminDTO dto = new UserAdminDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole().name());
            dto.setActive(user.isEnabled());
            return dto;
        }).toList();

        return ResponseEntity.ok(respuesta);
    }

    // ✅ 6. Endpoint de reactivación de usuario

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/reactivate/{email}")
    public ResponseEntity<?> reactivarUsuario(@PathVariable String email) {
        return userRepository.findByEmailAndEnabledFalse(email)
                .map(user -> {
                    user.setEnabled(true);
                    userRepository.save(user);
                    return ResponseEntity.ok("Usuario reactivado correctamente");
                })
                .orElseGet(() -> ResponseEntity.badRequest()
                        .body("Usuario no encontrado o ya se encuentra activo"));
    }
}
