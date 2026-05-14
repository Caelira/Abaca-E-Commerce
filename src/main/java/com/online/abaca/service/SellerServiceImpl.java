package com.online.abaca.service;

import com.online.abaca.dto.SellerRequestDTO;
import com.online.abaca.dto.SellerResponseDTO;
import com.online.abaca.mapper.SellerMapper;
import com.online.abaca.model.Seller;
import com.online.abaca.model.UserAccount;
import com.online.abaca.repository.SellerRepository;
import com.online.abaca.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final UserAccountRepository userAccountRepository;
    private final SellerMapper sellerMapper;

    @Override
    @Transactional
    public SellerResponseDTO createSeller(SellerRequestDTO requestDTO) {
        UserAccount userAccount = userAccountRepository.findById(requestDTO.getIdUser())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + requestDTO.getIdUser()));
        Seller seller = sellerMapper.toEntity(requestDTO);
        seller.setUserAccount(userAccount);
        Seller savedSeller = sellerRepository.save(seller);
        return sellerMapper.toResponseDTO(savedSeller);
    }

    @Override
    @Transactional(readOnly = true)
    public SellerResponseDTO getSellerById(Long idSeller) {
        Seller seller = sellerRepository.findById(idSeller)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with id: " + idSeller));
        return sellerMapper.toResponseDTO(seller);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SellerResponseDTO> getAllSellers() {
        return sellerRepository.findAll().stream()
                .map(sellerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SellerResponseDTO updateSeller(Long idSeller, SellerRequestDTO requestDTO) {
        Seller existingSeller = sellerRepository.findById(idSeller)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with id: " + idSeller));
        if (!existingSeller.getUserAccount().getIdUser().equals(requestDTO.getIdUser())) {
            UserAccount userAccount = userAccountRepository.findById(requestDTO.getIdUser())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + requestDTO.getIdUser()));
            existingSeller.setUserAccount(userAccount);
        }
        sellerMapper.updateEntityFromDTO(requestDTO, existingSeller);
        Seller updatedSeller = sellerRepository.save(existingSeller);
        return sellerMapper.toResponseDTO(updatedSeller);
    }

    @Override
    @Transactional
    public void deleteSeller(Long idSeller) {
        if (!sellerRepository.existsById(idSeller)) {
            throw new EntityNotFoundException("Seller not found with id: " + idSeller);
        }
        sellerRepository.deleteById(idSeller);
    }
}