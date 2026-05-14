package com.online.abaca.service;

import com.online.abaca.dto.SellerRequestDTO;
import com.online.abaca.dto.SellerResponseDTO;

import java.util.List;

public interface SellerService {
    SellerResponseDTO createSeller(SellerRequestDTO requestDTO);
    SellerResponseDTO getSellerById(Long idSeller);
    List<SellerResponseDTO> getAllSellers();
    SellerResponseDTO updateSeller(Long idSeller, SellerRequestDTO requestDTO);
    void deleteSeller(Long idSeller);
}