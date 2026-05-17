package com.online.abaca.controller;


import com.online.abaca.dto.OrderHeadResponseDTO;
import com.online.abaca.model.Buyer;
import com.online.abaca.model.OrderHead;
import com.online.abaca.model.OrderItem;
import com.online.abaca.model.UserAccount;
import com.online.abaca.repository.*;
import com.online.abaca.service.OrderHeadService;
import com.online.abaca.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class BuyerDashboardController {

    private final OrderHeadService orderHeadService;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final BuyerRepository buyerRepository;
    private final OrderHeadRepository orderHeadRepository;
    private final UserAccountRepository userAccountRepository;

    private Buyer getOrCreateBuyer(CustomUserDetails user) {
        return buyerRepository.findByUserAccount_IdUser(user.getIdUser())
                .orElseGet(() -> {
                    UserAccount userAcc = userAccountRepository.findById(user.getIdUser()).orElseThrow();
                    Buyer newBuyer = new Buyer();
                    newBuyer.setUserAccount(userAcc);
                    newBuyer.setContactNumber("Update your profile");
                    return buyerRepository.save(newBuyer);
                });
    }
    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    @GetMapping("/purchases")
    public String viewMyPurchases(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        Buyer buyer = getOrCreateBuyer(user);

        List<OrderHead> orders = orderHeadRepository.findByBuyer_IdBuyerOrderByOrderDateDesc(buyer.getIdBuyer());

        Map<Long, List<OrderItem>> orderItemsMap = new HashMap<>();
        for (OrderHead order : orders) {
           orderItemsMap.put(order.getIdOrder(), orderItemRepository.findAllByOrderHead_IdOrder(order.getIdOrder()));
        }

        model.addAttribute("orders", orders);
        model.addAttribute("orderItemsMap", orderItemsMap);

        return "buyer-purchases";
    }
}