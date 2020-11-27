package com.soltelec.consola.service;

import com.soltelec.consola.model.Customer;

import java.util.Optional;

/**
 * @author Jay Ehsaniara, Dec 30 2019
 */
public interface CustomerService {

    Optional<Customer> getCustomer(Long id);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);
}
