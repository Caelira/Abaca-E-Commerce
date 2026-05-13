package com.online.abaca.repository;

import com.online.abaca.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> getAddressesByIdAddress(Long idAddress);

    List<Address> findAddressByIdAddress(Long idAddress);
}
