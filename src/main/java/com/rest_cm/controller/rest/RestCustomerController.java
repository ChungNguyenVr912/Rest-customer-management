package com.rest_cm.controller.rest;

import com.rest_cm.entity.Customer;
import com.rest_cm.service.CustomerService;
import com.rest_cm.service.ImageService;
import dto.CustomerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class RestCustomerController {
    private final CustomerService customerService;
    private final ImageService imageService;

    public RestCustomerController(CustomerService customerService, ImageService imageService) {
        this.customerService = customerService;
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAllCustomer() {
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@ModelAttribute CustomerDTO form) {
        MultipartFile file = form.getAvatar();
        String fileName;
        String imageUrl;
        Customer customer = new Customer();
        try {
            fileName = imageService.save(file);
            imageUrl = imageService.getImageUrl(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        customer.setName(form.getName());
        customer.setEmail(form.getEmail());
        customer.setAddress(form.getAddress());
        customer.setAvatar(imageUrl);
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Customer oldCustomer = customerService.findById(id);
        if (oldCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customer.setId(oldCustomer.getId());
        customer.setAvatar(oldCustomer.getAvatar());
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.delete(id);
        return new ResponseEntity<>(customer, HttpStatus.NO_CONTENT);
    }
}
