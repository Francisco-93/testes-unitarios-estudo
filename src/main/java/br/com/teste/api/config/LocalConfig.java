package br.com.teste.api.config;

import br.com.teste.api.domain.Customer;
import br.com.teste.api.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("test")
public class LocalConfig {

    @Autowired
    private CustomerRepository repository;

    @Bean
    public void startDB(){
        Customer c1 = new Customer(null, "Francisco", "fran@ibm.com", "123");
        Customer c2 = new Customer(null, "Miriã Linda", "mimi@brb.com.br", "123");
        Customer c3 = new Customer(null, "Samara", "samara@caixa.com.br", "321");

        repository.saveAll(List.of(c1, c2, c3));
    }
}
