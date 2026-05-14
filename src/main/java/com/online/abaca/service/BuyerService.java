package com.online.abaca.service;

import com.online.abaca.dto.BuyerRequestDTO;
import com.online.abaca.dto.BuyerResponseDTO;

import java.util.List;

public interface BuyerService {
    BuyerResponseDTO createBuyer(BuyerRequestDTO requestDTO);
    BuyerResponseDTO getBuyerById(Long idBuyer);
    List<BuyerResponseDTO> getAllBuyers();
    BuyerResponseDTO updateBuyer(Long idBuyer, BuyerRequestDTO requestDTO);
    void deleteBuyer(Long idBuyer);
}