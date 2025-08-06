package com.hasitha.back_end.customer;

import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private CustomerDAO mockCustomerDao;
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        mockCustomerDao = mock(CustomerDAO.class);
        customerService = new CustomerService(mockCustomerDao);
    }

    // --- Tests for findAll() ---
    @Test
    public void testFindAll_shouldReturnCustomerList() {
        // Corrected constructor order: address, then phone number
        Customer customer1 = new Customer(1, "John", "Doe", "123 Main St", "+94712345678");
        Customer customer2 = new Customer(2, "Jane", "Smith", "456 Side Ave", "+94778765432");
        when(mockCustomerDao.findAll()).thenReturn(List.of(customer1, customer2));

        List<Customer> customers = customerService.findAll();
        assertNotNull(customers);
        assertEquals(2, customers.size());
        assertEquals("John", customers.get(0).getFirstName());
    }

    @Test
    public void testFindAll_shouldThrowNotFoundIfEmpty() {
        when(mockCustomerDao.findAll()).thenReturn(List.of());
        assertThrows(NotFoundException.class, () -> customerService.findAll());
    }

    // --- Tests for findById() ---
    @Test
    public void testFindById_shouldReturnCustomer() {
        // Corrected constructor order: address, then phone number
        Customer customer = new Customer(1, "John", "Doe", "123 Main St", "+94712345678");
        when(mockCustomerDao.findById(1)).thenReturn(customer);

        Customer result = customerService.findById(1);
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    public void testFindById_shouldThrowNotFoundIfNull() {
        when(mockCustomerDao.findById(99)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> customerService.findById(99));
    }

    // --- Tests for create() ---
    @Test
    public void testCreate_shouldSucceed() {
        // Corrected constructor order: address, then phone number
        Customer newCustomer = new Customer("Alice", "Brown", "789 Elm St", "+94761112233");
        when(mockCustomerDao.create(newCustomer)).thenReturn(newCustomer);

        Customer created = customerService.create(newCustomer);
        assertNotNull(created);
        assertEquals("Alice", created.getFirstName());
    }

    @Test
    public void testCreate_shouldThrowValidationIfFirstNameIsBlank() {
        // Corrected constructor order: address, then phone number
        Customer invalidCustomer = new Customer("", "Brown", "789 Elm St", "+94761112233");
        assertThrows(ValidationException.class, () -> customerService.create(invalidCustomer));
    }

    @Test
    public void testCreate_shouldThrowValidationIfPhoneNumberIsInvalid() {
        // Corrected constructor order: address, then phone number
        Customer invalidCustomer = new Customer("Alice", "Brown", "789 Elm St", "0761112233");
        assertThrows(ValidationException.class, () -> customerService.create(invalidCustomer));
    }

    // --- Tests for update() ---
    @Test
    public void testUpdate_shouldSucceed() {
        // Corrected constructor order for both
        Customer existingCustomer = new Customer(1, "Old", "User", "Old Address", "+94711234567");
        Customer updatedCustomer = new Customer(1, "Updated", "User", "New Address", "+94779876543");

        when(mockCustomerDao.findById(1)).thenReturn(existingCustomer);
        when(mockCustomerDao.update(eq(1), any(Customer.class))).thenReturn(updatedCustomer);

        Customer result = customerService.update(1, updatedCustomer);
        assertNotNull(result);
        assertEquals("Updated", result.getFirstName());
        assertEquals("New Address", result.getAddress());
    }

    @Test
    public void testUpdate_shouldThrowNotFoundIfNotExisting() {
        // Corrected constructor order
        Customer updatedCustomer = new Customer(99, "Nonexistent", "User", "Address", "+94711234567");
        when(mockCustomerDao.findById(99)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> customerService.update(99, updatedCustomer));
    }

    @Test
    public void testUpdate_shouldThrowValidationIfFirstNameIsBlank() {
        // Corrected constructor order
        Customer existingCustomer = new Customer(1, "Old", "User", "Old Address", "+94711234567");
        Customer invalidUpdate = new Customer(1, "", "User", "Old Address", "+94711234567");

        when(mockCustomerDao.findById(1)).thenReturn(existingCustomer);

        assertThrows(ValidationException.class, () -> customerService.update(1, invalidUpdate));
    }

    // --- Tests for delete() ---
    @Test
    public void testDelete_shouldSucceed() {
        when(mockCustomerDao.findById(1)).thenReturn(new Customer());
        doNothing().when(mockCustomerDao).delete(1);

        assertDoesNotThrow(() -> customerService.delete(1));
        verify(mockCustomerDao, times(1)).delete(1);
    }

    @Test
    public void testDelete_shouldThrowNotFoundIfNotExisting() {
        when(mockCustomerDao.findById(99)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> customerService.delete(99));
    }

    // --- Tests for ensureCustomerExists() ---
    @Test
    public void testEnsureCustomerExists_shouldSucceed() {
        when(mockCustomerDao.findById(1)).thenReturn(new Customer());
        assertDoesNotThrow(() -> customerService.ensureCustomerExists(1));
    }

    @Test
    public void testEnsureCustomerExists_shouldThrowNotFoundIfNotExisting() {
        when(mockCustomerDao.findById(99)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> customerService.ensureCustomerExists(99));
    }

    // --- Tests for exists() ---
    @Test
    public void testExists_shouldReturnTrue() {
        when(mockCustomerDao.findById(1)).thenReturn(new Customer());
        assertTrue(customerService.exists(1));
    }

    @Test
    public void testExists_shouldReturnFalse() {
        when(mockCustomerDao.findById(99)).thenReturn(null);
        assertFalse(customerService.exists(99));
    }
}
