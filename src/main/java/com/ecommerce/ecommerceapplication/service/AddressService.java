package com.ecommerce.ecommerceapplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerceapplication.entity.Address;
import com.ecommerce.ecommerceapplication.entity.User;
import com.ecommerce.ecommerceapplication.repository.AddressRepository;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address addAddress(User user, String street, String city, String state, String postalCode, String country) {
        Address address = new Address();
        address.setUser(user);
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setPostalCode(postalCode);
        address.setCountry(country);
        return addressRepository.save(address);
    }

    public List<Address> getAddressesByUser(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found with id: " + id));
    }

}
