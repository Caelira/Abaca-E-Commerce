package com.online.abaca.controller;

import com.online.abaca.dto.UserAccountRequestDTO;
import com.online.abaca.dto.UserAccountResponseDTO;
import com.online.abaca.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping
    public ResponseEntity<UserAccountResponseDTO> createUserAccount(@Valid @RequestBody UserAccountRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userAccountService.createUserAccount(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccountResponseDTO> getUserAccountById(@PathVariable("id") Long idUser) {
        return ResponseEntity.ok(userAccountService.getUserAccountById(idUser));
    }

    @GetMapping
    public ResponseEntity<List<UserAccountResponseDTO>> getAllUserAccounts() {
        return ResponseEntity.ok(userAccountService.getAllUserAccounts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAccountResponseDTO> updateUserAccount(
            @PathVariable("id") Long idUser,
            @Valid @RequestBody UserAccountRequestDTO requestDTO) {
        return ResponseEntity.ok(userAccountService.updateUserAccount(idUser, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAccount(@PathVariable("id") Long idUser) {
        userAccountService.deleteUserAccount(idUser);
        return ResponseEntity.noContent().build();
    }
}