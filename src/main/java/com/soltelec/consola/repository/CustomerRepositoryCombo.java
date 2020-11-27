package com.soltelec.consola.repository;

import com.soltelec.consola.repository.readRepository.CustomerReadRepository;
import com.soltelec.consola.repository.writeRepository.CustomerWriteRepository;

public interface CustomerRepositoryCombo extends CustomerReadRepository, CustomerWriteRepository {

}
