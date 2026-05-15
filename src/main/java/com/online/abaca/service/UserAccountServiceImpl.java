package com.online.abaca.service;

import com.online.abaca.dto.UserAccountRequestDTO;
import com.online.abaca.dto.UserAccountResponseDTO;
import com.online.abaca.mapper.UserAccountMapper;
import com.online.abaca.model.UserAccount;
import com.online.abaca.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;

    @Override
    @Transactional
    public UserAccountResponseDTO createUserAccount(UserAccountRequestDTO requestDTO) {
        UserAccount userAccount = userAccountMapper.toEntity(requestDTO);
        userAccount.setCreatedAt(LocalDateTime.now());
        UserAccount savedAccount = userAccountRepository.save(userAccount);
        return userAccountMapper.toResponseDTO(savedAccount);
    }
    @Override
    @Transactional(readOnly = true)
    public UserAccountResponseDTO getUserByEmail(String email) {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("UserAccount not found with email: " + email));

        return userAccountMapper.toResponseDTO(userAccount);
    }
    @Override
    @Transactional(readOnly = true)
    public UserAccountResponseDTO getUserAccountById(Long idUser) {
        UserAccount userAccount = userAccountRepository.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("UserAccount not found with id: " + idUser));
        return userAccountMapper.toResponseDTO(userAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAccountResponseDTO> getAllUserAccounts() {
        return userAccountRepository.findAll().stream()
                .map(userAccountMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserAccountResponseDTO updateUserAccount(Long idUser, UserAccountRequestDTO requestDTO) {
        UserAccount existingAccount = userAccountRepository.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("UserAccount not found with id: " + idUser));
        userAccountMapper.updateEntityFromDTO(requestDTO, existingAccount);
        UserAccount updatedAccount = userAccountRepository.save(existingAccount);
        return userAccountMapper.toResponseDTO(updatedAccount);
    }

    @Override
    @Transactional
    public void deleteUserAccount(Long idUser) {
        if (!userAccountRepository.existsById(idUser)) {
            throw new EntityNotFoundException("UserAccount not found with id: " + idUser);
        }
        userAccountRepository.deleteById(idUser);
    }
}
