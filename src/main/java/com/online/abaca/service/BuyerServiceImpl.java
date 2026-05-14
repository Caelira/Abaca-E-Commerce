package com.online.abaca.service;

import com.online.abaca.dto.BuyerRequestDTO;
import com.online.abaca.dto.BuyerResponseDTO;
import com.online.abaca.mapper.BuyerMapper;
import com.online.abaca.model.Buyer;
import com.online.abaca.model.UserAccount;
import com.online.abaca.repository.BuyerRepository;
import com.online.abaca.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuyerServiceImpl implements BuyerService {

    private final BuyerRepository buyerRepository;
    private final UserAccountRepository userAccountRepository;
    private final BuyerMapper buyerMapper;

    @Override
    @Transactional
    public BuyerResponseDTO createBuyer(BuyerRequestDTO requestDTO) {
        UserAccount userAccount = userAccountRepository.findById(requestDTO.getIdUser())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + requestDTO.getIdUser()));
        Buyer buyer = buyerMapper.toEntity(requestDTO);
        buyer.setUserAccount(userAccount);
        Buyer savedBuyer = buyerRepository.save(buyer);
        return buyerMapper.toResponseDTO(savedBuyer);
    }

    @Override
    @Transactional(readOnly = true)
    public BuyerResponseDTO getBuyerById(Long idBuyer) {
        Buyer buyer = buyerRepository.findById(idBuyer)
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found with id: " + idBuyer));
        return buyerMapper.toResponseDTO(buyer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BuyerResponseDTO> getAllBuyers() {
        return buyerRepository.findAll().stream()
                .map(buyerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BuyerResponseDTO updateBuyer(Long idBuyer, BuyerRequestDTO requestDTO) {
        Buyer existingBuyer = buyerRepository.findById(idBuyer)
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found with id: " + idBuyer));
        if (!existingBuyer.getUserAccount().getIdUser().equals(requestDTO.getIdUser())) {
            UserAccount userAccount = userAccountRepository.findById(requestDTO.getIdUser())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + requestDTO.getIdUser()));
            existingBuyer.setUserAccount(userAccount);
        }
        buyerMapper.updateEntityFromDTO(requestDTO, existingBuyer);
        Buyer updatedBuyer = buyerRepository.save(existingBuyer);
        return buyerMapper.toResponseDTO(updatedBuyer);
    }

    @Override
    @Transactional
    public void deleteBuyer(Long idBuyer) {
        if (!buyerRepository.existsById(idBuyer)) {
            throw new EntityNotFoundException("Buyer not found with id: " + idBuyer);
        }
        buyerRepository.deleteById(idBuyer);
    }
}