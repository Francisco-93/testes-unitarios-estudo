package br.com.teste.api.services;

import br.com.teste.api.domain.Customer;
import br.com.teste.api.domain.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    Customer findById(Integer id);
    List<Customer> findAll();
    Customer create (CustomerDTO obj);
    Customer update(CustomerDTO obj);
    void delete(Integer id);

}
