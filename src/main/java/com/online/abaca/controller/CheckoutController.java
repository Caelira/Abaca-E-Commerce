package com.online.abaca.controller;

import com.online.abaca.dto.AddressRequestDTO;
import com.online.abaca.dto.AddressResponseDTO;
import com.online.abaca.dto.OrderHeadResponseDTO;
import com.online.abaca.model.Buyer;
import com.online.abaca.model.CartHead;
import com.online.abaca.model.CartItem;
import com.online.abaca.repository.BuyerRepository;
import com.online.abaca.repository.CartHeadRepository;
import com.online.abaca.repository.CartItemRepository;
import com.online.abaca.service.AddressService;
import com.online.abaca.service.OrderHeadService;
import com.online.abaca.userdetails.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final OrderHeadService orderHeadService;
    private final AddressService addressService;
    private final BuyerRepository buyerRepository;
    private final CartHeadRepository cartHeadRepository;
    private final CartItemRepository cartItemRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public String viewCheckout(
            @RequestParam(value = "addressId", required = false) Long addressId,
            @AuthenticationPrincipal CustomUserDetails user, Model model) {

        Buyer buyer = buyerRepository.findByUserAccount_IdUser(user.getIdUser())
                .orElseThrow(() -> new RuntimeException("Buyer profile not found"));

        //get cart
        Optional<CartHead> activeCartOpt = cartHeadRepository.findByBuyer_IdBuyerAndStatus(buyer.getIdBuyer(), "ACTIVE");
        if (activeCartOpt.isEmpty()) {
            return "redirect:/cart";
        }

        List<CartItem> cartItems = cartItemRepository.findAllByCartHead_IdCart(activeCartOpt.get().getIdCart());
        model.addAttribute("cartItems", cartItems);

        BigDecimal cartTotal = cartItems.stream()
                .map(item -> item.getProduct().getProductPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("cartTotal", cartTotal);

        //get address
        List<AddressResponseDTO> addresses = addressService.getAddressesByUserId(user.getIdUser());
        model.addAttribute("addresses", addresses);

        BigDecimal shippingFee = new BigDecimal("0.00");
        if (!addresses.isEmpty()) {
            AddressResponseDTO selectedAddress = null;

             if (addressId != null) {
                selectedAddress = addresses.stream()
                        .filter(a -> a.getIdAddress().equals(addressId))
                        .findFirst()
                        .orElse(addresses.get(0));
            } else {
                selectedAddress = addresses.get(0);
            }

            shippingFee = calculateMockShippingFee(selectedAddress.getMunicipality());
            model.addAttribute("selectedAddress", selectedAddress);
        }

        model.addAttribute("shippingFee", shippingFee);
        model.addAttribute("orderTotal", cartTotal.add(shippingFee));

        return "checkout";
    }

    // add and edit address
    @PostMapping("/save-address")
    public String saveAddress(
            @ModelAttribute AddressRequestDTO requestDTO,
            @RequestParam(value = "idAddress", required = false) Long idAddress,
            @AuthenticationPrincipal CustomUserDetails user) {

        requestDTO.setIdUser(user.getIdUser());

        if (idAddress != null) {
            addressService.updateAddress(idAddress, requestDTO);
        } else {
            addressService.createAddressForUser(user.getIdUser(), requestDTO);
        }
        return "redirect:/checkout";
    }

    @PostMapping("/delete-address")
    public String deleteAddress(@RequestParam("addressId") Long addressId) {
        addressService.deleteAddress(addressId);
        return "redirect:/checkout";
    }

    @PostMapping("/process")
    public String processCheckout(
            @RequestParam("addressId") Long addressId,
            @RequestParam("paymentMethod") String paymentMethod,
            @AuthenticationPrincipal CustomUserDetails user) {
        try {
            Buyer buyer = buyerRepository.findByUserAccount_IdUser(user.getIdUser())
                    .orElseThrow(() -> new RuntimeException("Buyer profile not found"));

            OrderHeadResponseDTO order = orderHeadService.processCheckout(buyer.getIdBuyer(), addressId, paymentMethod);

            return "redirect:/buyer/orders/" + order.getIdOrder() + "?success=true";

        } catch (Exception e) {
            return "redirect:/cart?error=" + e.getMessage();
        }
    }

    private BigDecimal calculateMockShippingFee(String municipality) {
        if (municipality == null) return new BigDecimal("150.00");
        return switch (municipality.trim().toUpperCase()) {
            case "DARAGA" -> new BigDecimal("50.00");
            case "LEGAZPI CITY", "CAMALIG" -> new BigDecimal("80.00");
            default -> new BigDecimal("100.00");
        };
    }
}