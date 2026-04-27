package com.example.vsms.service;

import com.example.vsms.dto.CustomerRequest;
import com.example.vsms.dto.CustomerResponse;
import com.example.vsms.entity.Customer;
import com.example.vsms.exception.ResourceNotFoundException;
import com.example.vsms.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll(String search) {
        List<Customer> customers = search == null || search.isBlank()
                ? customerRepository.findAll()
                : customerRepository.findByNameContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrEmailContainingIgnoreCase(
                search, search, search
        );
        return customers.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Customer findEntity(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        return toResponse(findEntity(id));
    }

    public CustomerResponse create(CustomerRequest request) {
        Customer customer = Customer.builder()
                .name(request.name())
                .phone(request.phone())
                .email(request.email())
                .address(request.address())
                .build();
        return toResponse(customerRepository.save(customer));
    }

    public CustomerResponse update(Long id, CustomerRequest request) {
        Customer customer = findEntity(id);
        customer.setName(request.name());
        customer.setPhone(request.phone());
        customer.setEmail(request.email());
        customer.setAddress(request.address());
        return toResponse(customer);
    }

    public void delete(Long id) {
        Customer customer = findEntity(id);
        customerRepository.delete(customer);
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getVehicles().size()
        );
    }
}
