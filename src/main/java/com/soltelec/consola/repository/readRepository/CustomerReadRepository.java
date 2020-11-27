package com.soltelec.consola.repository.readRepository;

import com.soltelec.consola.model.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Jay Ehsaniara, Dec 30 2019
 */
public interface CustomerReadRepository extends CrudRepository<Customer, Long> {
}
