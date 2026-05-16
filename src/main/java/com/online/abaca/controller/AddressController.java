package com.online.abaca.controller;

import com.online.abaca.model.Address;
import com.online.abaca.model.UserAccount;
import com.online.abaca.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/profile/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressRepository addressRepository;

    @GetMapping
    public String listAddresses(@ModelAttribute("currentUser") UserAccount currentUser, Model model) {
        if (currentUser == null) return "redirect:/login";

        List<Address> addresses = addressRepository.findByUserAccount_IdUser(currentUser.getIdUser());
        model.addAttribute("addresses", addresses);
        return "addresses";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("address", new Address());
        return "address-form";
    }

     @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, @ModelAttribute("currentUser") UserAccount currentUser, Model model) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid address Id:" + id));

        if (!address.getUserAccount().getIdUser().equals(currentUser.getIdUser())) {
            return "redirect:/profile/addresses?error=unauthorized";
        }

        model.addAttribute("address", address);
        return "address-form";
    }

    @PostMapping("/save")
    public String saveAddress(@ModelAttribute("address") Address address, @ModelAttribute("currentUser") UserAccount currentUser) {
        if (currentUser == null) return "redirect:/login";

        address.setUserAccount(currentUser);
        addressRepository.save(address);

        return "redirect:/profile/addresses";
    }

    // delete add
    @PostMapping("/delete/{id}")
    public String deleteAddress(@PathVariable("id") Long id, @ModelAttribute("currentUser") UserAccount currentUser) {
        Address address = addressRepository.findById(id).orElse(null);

        if (address != null && address.getUserAccount().getIdUser().equals(currentUser.getIdUser())) {
            addressRepository.delete(address);
        }
        return "redirect:/profile/addresses";
    }
}