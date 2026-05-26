package com.online.abaca.service;

import com.online.abaca.dto.OrderHeadRequestDTO;
import com.online.abaca.dto.OrderHeadResponseDTO;
import com.online.abaca.mapper.OrderHeadMapper;
import com.online.abaca.model.*;
import com.online.abaca.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private final CartItemRepository cartItemRepository;
    private  final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public OrderHeadResponseDTO createOrderHead(OrderHeadRequestDTO requestDTO) {
        Buyer buyer = buyerRepository.findById(requestDTO.getIdBuyer())
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
        Address address = addressRepository.findById(requestDTO.getIdShippingAddress())
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        OrderHead orderHead = orderHeadMapper.toEntity(requestDTO);
        orderHead.setBuyer(buyer);
        orderHead.setShippingAddress(address);

        BigDecimal shippingFee = calculateShippingFee(address.getMunicipality());

        if (requestDTO.getIdCart() != null) {
            CartHead cartHead = cartHeadRepository.findById(requestDTO.getIdCart())
                    .orElseThrow(() -> new EntityNotFoundException("CartHead not found"));
            orderHead.setCartHead(cartHead);
        }
        orderHead.setOrderDate(LocalDateTime.now());
        OrderHead savedOrderHead = orderHeadRepository.save(orderHead);
        return orderHeadMapper.toResponseDTO(savedOrderHead);
    }
    private BigDecimal calculateShippingFee(String destinationMunicipality) {
        if (destinationMunicipality == null) {
            return new BigDecimal("150.00"); // default fare
            }
        //LEGACY
        return switch (destinationMunicipality.trim().toUpperCase()) {
            case "DARAGA" -> new BigDecimal("50.00"); // Base rate
            case "LEGAZPI CITY", "LEGAZPI", "CAMALIG" -> new BigDecimal("80.00"); // pag tabi lang
            case "TABACO", "LIGAO", "GUINOBATAN", "POLANGUI", "OAS", "BACACAY", "MALILIPOT", "STO DOMINGO" -> new BigDecimal("100.00"); // Extended Albay
            default -> new BigDecimal("150.00"); // Outside Albay
        };
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
    @Transactional
    public OrderHeadResponseDTO processCheckout(Long buyerId, Long addressId, String paymentMethod) {
        CartHead cart = cartHeadRepository.findByBuyer_IdBuyerAndStatus(buyerId, "ACTIVE")
                .orElseThrow(() -> new IllegalStateException("No active cart found"));

        OrderHeadRequestDTO orderReq = new OrderHeadRequestDTO();
        orderReq.setIdBuyer(buyerId);
        orderReq.setIdShippingAddress(addressId);
        orderReq.setIdCart(cart.getIdCart());
        orderReq.setOrderStatus("PENDING");
        OrderHeadResponseDTO newOrder = createOrderHead(orderReq);

        OrderHead orderEntity = orderHeadRepository.findById(newOrder.getIdOrder()).orElseThrow();

         List<CartItem> cartItems = cartItemRepository.findAllByCartHead_IdCart(cart.getIdCart());
        BigDecimal totalOrderAmount = calculateShippingFee(orderEntity.getShippingAddress().getMunicipality());

        for (CartItem cItem : cartItems) {
            Product product = cItem.getProduct();

            if (product.getStockQuantity() < cItem.getQuantity()) {
                throw new IllegalStateException("Not enough stock for " + product.getProductName());
            }
            product.setStockQuantity(product.getStockQuantity() - cItem.getQuantity());
            product.setTotalSold(product.getTotalSold() + cItem.getQuantity());
            productRepository.save(product);

            OrderItem oItem = new OrderItem();
            oItem.setOrderHead(orderEntity);
            oItem.setProduct(product);
            oItem.setOrderQuantity(cItem.getQuantity());
            oItem.setFinalUnitPrice(product.getProductPrice()); // Lock in the price
            orderItemRepository.save(oItem);

            BigDecimal itemTotal = product.getProductPrice().multiply(new BigDecimal(cItem.getQuantity()));
            totalOrderAmount = totalOrderAmount.add(itemTotal);
        }
        Payment payment = new Payment();
        payment.setOrderHead(orderEntity);
        payment.setPaymentAmount(totalOrderAmount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);
        cart.setStatus("CHECKED_OUT");
        cartHeadRepository.save(cart);

        return newOrder;
    }
}