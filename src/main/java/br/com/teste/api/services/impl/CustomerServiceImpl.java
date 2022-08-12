package br.com.teste.api.services.impl;

import br.com.teste.api.domain.Customer;
import br.com.teste.api.domain.dto.CustomerDTO;
import br.com.teste.api.exceptions.DataIntegrityViolationException;
import br.com.teste.api.repositories.CustomerRepository;
import br.com.teste.api.services.CustomerService;
import br.com.teste.api.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    ModelMapper mapper;

    @Autowired
    private CustomerRepository repository;

    @Override
    public Customer findById(Integer id) {
        Optional<Customer> obj = repository.findById(id);
        return obj.orElseThrow( () -> new ObjectNotFoundException("Customer not found"));
    }

    @Override
    public List<Customer> findAll(){
        return repository.findAll();
    }

    @Override
    public Customer create(CustomerDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, Customer.class));
    }

    @Override
    public Customer update(CustomerDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, Customer.class));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void findByEmail(CustomerDTO obj){
        Optional<Customer> customer = repository.findByEmail(obj.getEmail());
        if(customer.isPresent() && !customer.get().getId().equals(obj.getId())){
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema");
        }
    }

}
