package com.online.abaca.service;

import com.online.abaca.dto.UserAccountRequestDTO;
import com.online.abaca.dto.UserAccountResponseDTO;

import java.util.List;

public interface UserAccountService {
    UserAccountResponseDTO createUserAccount(UserAccountRequestDTO requestDTO);
    UserAccountResponseDTO getUserAccountById(Long idUser);
    List<UserAccountResponseDTO> getAllUserAccounts();
    UserAccountResponseDTO updateUserAccount(Long idUser, UserAccountRequestDTO requestDTO);
    void deleteUserAccount(Long idUser);
    UserAccountResponseDTO getUserByEmail(String email);
}
