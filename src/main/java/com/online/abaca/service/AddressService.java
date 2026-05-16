package com.online.abaca.service;
import com.online.abaca.dto.AddressRequestDTO;
import com.online.abaca.dto.AddressResponseDTO;

import java.util.List;

public interface AddressService {
    AddressResponseDTO createAddress(AddressRequestDTO requestDTO);
    AddressResponseDTO getAddressById(Long idAddress);
    List<AddressResponseDTO> getAllAddresses();
    AddressResponseDTO updateAddress(Long idAddress, AddressRequestDTO requestDTO);
    void deleteAddress(Long idAddress);
    List<AddressResponseDTO> getAddressesByUserId(Long idUser);
    AddressResponseDTO createAddressForUser(Long idUser, AddressRequestDTO requestDTO);
    AddressResponseDTO updateAddressForUser(Long idAddress, Long idUser, AddressRequestDTO requestDTO);
    void deleteAddressForUser(Long idAddress, Long idUser);
}