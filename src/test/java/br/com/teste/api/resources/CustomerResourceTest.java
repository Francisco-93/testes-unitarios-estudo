package br.com.teste.api.resources;

import br.com.teste.api.domain.Customer;
import br.com.teste.api.domain.dto.CustomerDTO;
import br.com.teste.api.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

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
class CustomerResourceTest {

    public static final int INDEX       = 0;
    public static final Integer ID      = 1;
    public static final String NAME     = "Francisco";
    public static final String EMAIL    = "francisco@ibm.com";
    public static final String PASSWORD = "123";

    private Customer customer;
    private CustomerDTO customerDTO;

    @InjectMocks
    private CustomerResource resource;

    @Mock
    private ModelMapper mapper;

    @Mock
    private CustomerService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCustomer();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(service.findById(anyInt())).thenReturn(customer);
        when(mapper.map(any(), any())).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(CustomerDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void whenFindAllThenReturnAListOfUserDTO() {
        when(service.findAll()).thenReturn(List.of(customer));
        when(mapper.map(any(), any())).thenReturn(customerDTO);

        ResponseEntity<List<CustomerDTO>> response = resource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(CustomerDTO.class, response.getBody().get(0).getClass());

        assertEquals(ID, response.getBody().get(0).getId());
        assertEquals(NAME, response.getBody().get(0).getName());
        assertEquals(EMAIL, response.getBody().get(0).getEmail());
        assertEquals(PASSWORD, response.getBody().get(0).getPassword());
    }

    @Test
    void whenCreateThenReturncreated() {
        when(service.create(any())).thenReturn(customer);

        ResponseEntity<CustomerDTO> response = resource.create(customerDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(service.update(any())).thenReturn(customer);
        when(mapper.map(any(), any())).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = resource.update(ID, customerDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(CustomerDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
    }

    @Test
    void whenDeleteWithSuccess() {
        doNothing().when(service).delete(anyInt());

        ResponseEntity<CustomerDTO> response = resource.delete(ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        verify(service, times(1)).delete(anyInt());
    }

    private void startCustomer(){
        customer = new Customer(ID, NAME, EMAIL, PASSWORD);
        customerDTO = new CustomerDTO(ID, NAME, EMAIL, PASSWORD);
    }
}