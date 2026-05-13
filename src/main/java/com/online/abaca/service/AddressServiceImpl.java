package com.online.abaca.service;

import com.online.abaca.dto.AddressRequestDTO;
import com.online.abaca.dto.AddressResponseDTO;
import com.online.abaca.mapper.AddressMapper;
import com.online.abaca.model.Address;
import com.online.abaca.model.UserAccount;
import com.online.abaca.repository.AddressRepository;
import com.online.abaca.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserAccountRepository userAccountRepository;
    private final AddressMapper addressMapper;

    @Override
    @Transactional
    public AddressResponseDTO createAddress(AddressRequestDTO requestDTO) {
        UserAccount userAccount = userAccountRepository.findById(requestDTO.getIdUser())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + requestDTO.getIdUser()));

        Address address = addressMapper.toEntity(requestDTO);
        address.setUserAccount(userAccount);

        Address savedAddress = addressRepository.save(address);
        return addressMapper.toResponseDTO(savedAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponseDTO getAddressById(Long idAddress) {
        Address address = addressRepository.findById(idAddress)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + idAddress));
        return addressMapper.toResponseDTO(address);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDTO> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(addressMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AddressResponseDTO updateAddress(Long idAddress, AddressRequestDTO requestDTO) {
        Address existingAddress = addressRepository.findById(idAddress)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + idAddress));

        if (!existingAddress.getUserAccount().getIdUser().equals(requestDTO.getIdUser())) {
            UserAccount userAccount = userAccountRepository.findById(requestDTO.getIdUser())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + requestDTO.getIdUser()));
            existingAddress.setUserAccount(userAccount);
        }

        addressMapper.updateEntityFromDTO(requestDTO, existingAddress);

        Address updatedAddress = addressRepository.save(existingAddress);
        return addressMapper.toResponseDTO(updatedAddress);
    }

    @Override
    @Transactional
    public void deleteAddress(Long idAddress) {
        if (!addressRepository.existsById(idAddress)) {
            throw new EntityNotFoundException("Address not found with id: " + idAddress);
        }
        addressRepository.deleteById(idAddress);
    }
}
