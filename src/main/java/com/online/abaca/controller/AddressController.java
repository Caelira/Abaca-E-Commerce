package com.online.abaca.controller;

import com.online.abaca.dto.AddressRequestDTO;
import com.online.abaca.dto.AddressResponseDTO;
import com.online.abaca.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@Valid @RequestBody AddressRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable("id") Long idAddress) {
        return ResponseEntity.ok(addressService.getAddressById(idAddress));
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @PathVariable("id") Long idAddress,
            @Valid @RequestBody AddressRequestDTO requestDTO) {
        return ResponseEntity.ok(addressService.updateAddress(idAddress, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("id") Long idAddress) {
        addressService.deleteAddress(idAddress);
        return ResponseEntity.noContent().build();
    }
}