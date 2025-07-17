package com.aasencios.taskapi.service;

import com.aasencios.taskapi.dto.*;
import com.aasencios.taskapi.model.User;

public interface UserService {

    UserResponseDTO register(RegisterRequestDTO request);

    LoginResponseDTO login(LoginRequestDTO request);

    User getUserByEmail(String email);

    UserBasicDTO getUserBasicInfo(String email);

    public void deactivateUserByEmail(String email);

    public UserBasicDTO updateUser(String email, UpdateUserDTO dto);

}
