package com.online.abaca.service;

import com.online.abaca.dto.OrderHeadRequestDTO;
import com.online.abaca.dto.OrderHeadResponseDTO;
import com.online.abaca.mapper.OrderHeadMapper;
import com.online.abaca.model.Address;
import com.online.abaca.model.Buyer;
import com.online.abaca.model.CartHead;
import com.online.abaca.model.OrderHead;
import com.online.abaca.repository.AddressRepository;
import com.online.abaca.repository.BuyerRepository;
import com.online.abaca.repository.CartHeadRepository;
import com.online.abaca.repository.OrderHeadRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderHeadServiceImpl implements OrderHeadService {

    private final OrderHeadRepository orderHeadRepository;
    private final BuyerRepository buyerRepository;
    private final CartHeadRepository cartHeadRepository;
    private final AddressRepository addressRepository;
    private final OrderHeadMapper orderHeadMapper;

    @Override
    @Transactional
    public OrderHeadResponseDTO createOrderHead(OrderHeadRequestDTO requestDTO) {
        Buyer buyer = buyerRepository.findById(requestDTO.getIdBuyer())
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found with id: " + requestDTO.getIdBuyer()));
        Address address = addressRepository.findById(requestDTO.getIdShippingAddress())
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + requestDTO.getIdShippingAddress()));
        OrderHead orderHead = orderHeadMapper.toEntity(requestDTO);
        orderHead.setBuyer(buyer);
        orderHead.setShippingAddress(address);
        if (requestDTO.getIdCart() != null) {
            CartHead cartHead = cartHeadRepository.findById(requestDTO.getIdCart())
                    .orElseThrow(() -> new EntityNotFoundException("CartHead not found with id: " + requestDTO.getIdCart()));
            orderHead.setCartHead(cartHead);
        }
        orderHead.setOrderDate(LocalDateTime.now());
        OrderHead savedOrderHead = orderHeadRepository.save(orderHead);
        return orderHeadMapper.toResponseDTO(savedOrderHead);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderHeadResponseDTO getOrderHeadById(Long idOrder) {
        OrderHead orderHead = orderHeadRepository.findById(idOrder)
                .orElseThrow(() -> new EntityNotFoundException("OrderHead not found with id: " + idOrder));
        return orderHeadMapper.toResponseDTO(orderHead);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderHeadResponseDTO> getAllOrderHeads() {
        return orderHeadRepository.findAll().stream()
                .map(orderHeadMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderHeadResponseDTO updateOrderHead(Long idOrder, OrderHeadRequestDTO requestDTO) {
        OrderHead existingOrderHead = orderHeadRepository.findById(idOrder)
                .orElseThrow(() -> new EntityNotFoundException("OrderHead not found with id: " + idOrder));
        if (!existingOrderHead.getBuyer().getIdBuyer().equals(requestDTO.getIdBuyer())) {
            Buyer buyer = buyerRepository.findById(requestDTO.getIdBuyer())
                    .orElseThrow(() -> new EntityNotFoundException("Buyer not found with id: " + requestDTO.getIdBuyer()));
            existingOrderHead.setBuyer(buyer);
        }
        if (!existingOrderHead.getShippingAddress().getIdAddress().equals(requestDTO.getIdShippingAddress())) {
            Address address = addressRepository.findById(requestDTO.getIdShippingAddress())
                    .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + requestDTO.getIdShippingAddress()));
            existingOrderHead.setShippingAddress(address);
        }
        if (requestDTO.getIdCart() != null && (existingOrderHead.getCartHead() == null || !existingOrderHead.getCartHead().getIdCart().equals(requestDTO.getIdCart()))) {
            CartHead cartHead = cartHeadRepository.findById(requestDTO.getIdCart())
                    .orElseThrow(() -> new EntityNotFoundException("CartHead not found with id: " + requestDTO.getIdCart()));
            existingOrderHead.setCartHead(cartHead);
        } else if (requestDTO.getIdCart() == null) {
            existingOrderHead.setCartHead(null);
        }
        orderHeadMapper.updateEntityFromDTO(requestDTO, existingOrderHead);
        OrderHead updatedOrderHead = orderHeadRepository.save(existingOrderHead);
        return orderHeadMapper.toResponseDTO(updatedOrderHead);
    }

    @Override
    @Transactional
    public void deleteOrderHead(Long idOrder) {
        if (!orderHeadRepository.existsById(idOrder)) {
            throw new EntityNotFoundException("OrderHead not found with id: " + idOrder);
        }
        orderHeadRepository.deleteById(idOrder);
    }
}