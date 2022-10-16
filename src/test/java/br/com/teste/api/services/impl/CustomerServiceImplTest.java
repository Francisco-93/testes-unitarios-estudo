package br.com.teste.api.services.impl;

import br.com.teste.api.domain.Customer;
import br.com.teste.api.domain.dto.CustomerDTO;
import br.com.teste.api.exceptions.DataIntegrityViolationException;
import br.com.teste.api.exceptions.ObjectNotFoundException;
import br.com.teste.api.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceImplTest {

    public static final int INDEX       = 0;
    public static final Integer ID      = 1;
    public static final String NAME     = "Francisco";
    public static final String EMAIL    = "francisco@ibm.com";
    public static final String PASSWORD = "123";

    private Customer customer;
    private CustomerDTO customerDTO;
    private Optional<Customer> optionalCustomer;

    @InjectMocks //Injeta o objeto real aqui
    private CustomerServiceImpl service;

    @Mock
    private CustomerRepository repository;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        //Comando para criar os Mocks
        MockitoAnnotations.openMocks(this);
        //Chamada para criar objetos
        startCustomer();
    }

    @Test
    void whenFindByIdThenReturnAnCustomerInstance() {
        when(repository.findById(anyInt())).thenReturn(optionalCustomer);
        Customer response = service.findById(ID);

        assertNotNull(response);
        assertEquals(Customer.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        when(repository.findById(anyInt())).thenThrow(ObjectNotFoundException.class);
        try{
            service.findById(ID);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(customer));

        List<Customer> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Customer.class, response.get(INDEX).getClass());
        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(customer);

        Customer response = service.create(customerDTO);

        assertNotNull(response);
        assertEquals(Customer.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnADataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalCustomer);
        optionalCustomer.get().setId(2);
        try{
            service.create(customerDTO);
        }catch (Exception ex){
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("E-mail já cadastrado no sistema", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(customer);

        Customer response = service.update(customerDTO);

        assertNotNull(response);
        assertEquals(Customer.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnADataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalCustomer);
        optionalCustomer.get().setId(2);
        try{
            service.update(customerDTO);
        }catch (Exception ex){
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("E-mail já cadastrado no sistema", ex.getMessage());
        }
    }

    @Test
    void deleteWithSucess() {
        when(repository.findById(anyInt())).thenReturn(optionalCustomer);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteWithObjectNotFoundException() {
        when(repository.findById(any())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

        try{
            service.delete(ID);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado", ex.getMessage());
        }
    }

    private void startCustomer(){
        customer = new Customer(ID, NAME, EMAIL, PASSWORD);
        customerDTO = new CustomerDTO(ID, NAME, EMAIL, PASSWORD);
        optionalCustomer = Optional.of(new Customer(ID, NAME, EMAIL, PASSWORD));
    }
}