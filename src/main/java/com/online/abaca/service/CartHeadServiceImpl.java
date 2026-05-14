package com.online.abaca.service;

import com.online.abaca.dto.CartHeadRequestDTO;
import com.online.abaca.dto.CartHeadResponseDTO;
import com.online.abaca.mapper.CartHeadMapper;
import com.online.abaca.model.Buyer;
import com.online.abaca.model.CartHead;
import com.online.abaca.repository.BuyerRepository;
import com.online.abaca.repository.CartHeadRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartHeadServiceImpl implements CartHeadService {

    private final CartHeadRepository cartHeadRepository;
    private final BuyerRepository buyerRepository;
    private final CartHeadMapper cartHeadMapper;

    @Override
    @Transactional
    public CartHeadResponseDTO createCartHead(CartHeadRequestDTO requestDTO) {
        Buyer buyer = buyerRepository.findById(requestDTO.getIdBuyer())
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found with id: " + requestDTO.getIdBuyer()));
        CartHead cartHead = cartHeadMapper.toEntity(requestDTO);
        cartHead.setBuyer(buyer);
        CartHead savedCartHead = cartHeadRepository.save(cartHead);
        return cartHeadMapper.toResponseDTO(savedCartHead);
    }

    @Override
    @Transactional(readOnly = true)
    public CartHeadResponseDTO getCartHeadById(Long idCart) {
        CartHead cartHead = cartHeadRepository.findById(idCart)
                .orElseThrow(() -> new EntityNotFoundException("CartHead not found with id: " + idCart));
        return cartHeadMapper.toResponseDTO(cartHead);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartHeadResponseDTO> getAllCartHeads() {
        return cartHeadRepository.findAll().stream()
                .map(cartHeadMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CartHeadResponseDTO updateCartHead(Long idCart, CartHeadRequestDTO requestDTO) {
        CartHead existingCartHead = cartHeadRepository.findById(idCart)
                .orElseThrow(() -> new EntityNotFoundException("CartHead not found with id: " + idCart));
        if (!existingCartHead.getBuyer().getIdBuyer().equals(requestDTO.getIdBuyer())) {
            Buyer buyer = buyerRepository.findById(requestDTO.getIdBuyer())
                    .orElseThrow(() -> new EntityNotFoundException("Buyer not found with id: " + requestDTO.getIdBuyer()));
            existingCartHead.setBuyer(buyer);
        }
        cartHeadMapper.updateEntityFromDTO(requestDTO, existingCartHead);
        CartHead updatedCartHead = cartHeadRepository.save(existingCartHead);
        return cartHeadMapper.toResponseDTO(updatedCartHead);
    }

    @Override
    @Transactional
    public void deleteCartHead(Long idCart) {
        if (!cartHeadRepository.existsById(idCart)) {
            throw new EntityNotFoundException("CartHead not found with id: " + idCart);
        }
        cartHeadRepository.deleteById(idCart);
    }
}