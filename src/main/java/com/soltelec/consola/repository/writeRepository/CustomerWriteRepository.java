package com.soltelec.consola.repository.writeRepository;

import com.soltelec.consola.model.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Jay Ehsaniara, Dec 30 2019
 */
public interface CustomerWriteRepository extends CrudRepository<Customer, Long> {
}
