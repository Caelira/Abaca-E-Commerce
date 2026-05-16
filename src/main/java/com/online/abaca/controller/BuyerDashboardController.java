package com.online.abaca.controller;


import com.online.abaca.dto.OrderHeadResponseDTO;
import com.online.abaca.model.OrderItem;
import com.online.abaca.repository.OrderItemRepository;
import com.online.abaca.repository.PaymentRepository;
import com.online.abaca.service.OrderHeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class BuyerDashboardController {

    private final OrderHeadService orderHeadService;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;

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
}
