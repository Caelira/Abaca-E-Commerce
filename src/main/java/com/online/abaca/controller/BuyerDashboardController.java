package com.online.abaca.controller;


import com.online.abaca.dto.OrderHeadResponseDTO;
import com.online.abaca.model.Buyer;
import com.online.abaca.model.OrderHead;
import com.online.abaca.model.OrderItem;
import com.online.abaca.repository.BuyerRepository;
import com.online.abaca.repository.OrderHeadRepository;
import com.online.abaca.repository.OrderItemRepository;
import com.online.abaca.repository.PaymentRepository;
import com.online.abaca.service.OrderHeadService;
import com.online.abaca.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class BuyerDashboardController {

    private final OrderHeadService orderHeadService;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final BuyerRepository buyerRepository;
    private final OrderHeadRepository orderHeadRepository;

    @GetMapping("/orders/{id}")
    public String viewOrderDetails(@PathVariable("id") Long idOrder, Model model) {
        OrderHeadResponseDTO order = orderHeadService.getOrderHeadById(idOrder);
        model.addAttribute("order", order);

        List<OrderItem> items = orderItemRepository.findAllByOrderHead_IdOrder(idOrder);
        model.addAttribute("items", items);

        paymentRepository.findByOrderHead_IdOrder(idOrder).ifPresent(payment -> {
            model.addAttribute("payment", payment);
        });

        return "order-detail";
    }

    @GetMapping("/purchases")
    public String viewMyPurchases(@AuthenticationPrincipal CustomUserDetails user, Model model) {

        Optional<Buyer> buyerOpt = buyerRepository.findByUserAccount_IdUser(user.getIdUser());

        if (buyerOpt.isEmpty()) {
            model.addAttribute("orders", List.of());
            model.addAttribute("orderItemsMap", new HashMap<>());
            return "buyer-purchases";
        }

        Buyer buyer = buyerOpt.get();

        List<OrderHead> orders = orderHeadRepository.findByBuyer_IdBuyerOrderByOrderDateDesc(buyer.getIdBuyer());
        Map<Long, List<OrderItem>> orderItemsMap = new HashMap<>();
        for (OrderHead order : orders) {
            orderItemsMap.put(order.getIdOrder(), orderItemRepository.findAllByOrderHead_IdOrder(order.getIdOrder()));
        }

        model.addAttribute("orders", orders);
        model.addAttribute("orderItemsMap", orderItemsMap);

        return "buyer-purchases";
    }

    @GetMapping("/profile")
    public String viewProfile(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        Buyer buyer = buyerRepository.findByUserAccount_IdUser(user.getIdUser()).orElseThrow();
        model.addAttribute("buyer", buyer);
        return "buyer-profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("contactNumber") String contactNumber, @AuthenticationPrincipal CustomUserDetails user) {
        Buyer buyer = buyerRepository.findByUserAccount_IdUser(user.getIdUser()).orElseThrow();
        buyer.setContactNumber(contactNumber);
        buyerRepository.save(buyer);
        return "redirect:/buyer/profile?success=true";
    }
}
