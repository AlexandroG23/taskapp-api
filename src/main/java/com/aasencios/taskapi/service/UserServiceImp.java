package com.aasencios.taskapi.service;

import com.aasencios.taskapi.dto.*;
import com.aasencios.taskapi.exception.EmailAlreadyExistsException;
import com.aasencios.taskapi.model.Role;
import com.aasencios.taskapi.model.User;
import com.aasencios.taskapi.repository.UserRepository;
import com.aasencios.taskapi.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{

    public UserServiceImp(com.aasencios.taskapi.repository.UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, com.aasencios.taskapi.repository.UserRepository userRepository1) {
        UserRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository1;
    }

    private final UserRepository UserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public UserResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("El email ya está registrado");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER); // Asignar rol por defecto

        User saved = userRepository.save(user);

        return mapToResponseDTO(saved);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmailAndEnabledTrue(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new LoginResponseDTO(token);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndEnabledTrue(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public UserBasicDTO getUserBasicInfo(String email) {
        User user = userRepository.findByEmailAndEnabledTrue(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UserBasicDTO dto = new UserBasicDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());

        return dto;
    }

    @Override
    public void deactivateUserByEmail(String email) {
        User user = userRepository.findByEmailAndEnabledTrue(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado o ya inactivo"));
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public UserBasicDTO updateUser(String email, UpdateUserDTO dto) {
        User user = userRepository.findByEmailAndEnabledTrue(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (dto.getName() != null && !dto.getName().isBlank()) user.setName(dto.getName());
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(user);

        UserBasicDTO response = new UserBasicDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());

        return response;
    }

    // Mapear objetos a DTO

    private UserResponseDTO mapToResponseDTO(User user){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
