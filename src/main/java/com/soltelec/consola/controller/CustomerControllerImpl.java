package com.soltelec.consola.controller;

import com.soltelec.consola.handler.ResourceNotFoundException;
import com.soltelec.consola.model.Customer;
import com.soltelec.consola.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Jay Ehsaniara, Dec 30 2019
 */
@RestController
public class CustomerControllerImpl {

    @PersistenceContext(unitName = "persistence.postgres")
    private EntityManager postgresDataSource;

    private final CustomerService customerService;

    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(path = "/customer/{id}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable("id") Long id) {
        postgresDataSource.find(Customer.class, 1L);
        return customerService.getCustomer(id).orElseThrow(() -> new ResourceNotFoundException("Invalid Customer"));
    }

    @RequestMapping(path = "/customer", method = RequestMethod.POST)
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @RequestMapping(path = "/customer", method = RequestMethod.PUT)
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }
}


