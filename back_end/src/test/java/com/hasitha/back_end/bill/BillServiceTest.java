package com.hasitha.back_end.bill;

import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BillServiceTest {

    private BillDAO mockBillDao;
    private BillService billService;

    @BeforeEach
    public void setUp() {
        mockBillDao = mock(BillDAO.class);
        billService = new BillService(mockBillDao);
    }

    // --- Tests for create() ---
    @Test
    public void testCreate_shouldSucceed() {
        // Corrected constructor call to include a new Date() object
        Bill billToCreate = new Bill(1, 1, new Date(), 100.0);
        Bill createdBill = new Bill(1, 1, 1, new Date(), 100.0);
        when(mockBillDao.create(any(Bill.class))).thenReturn(createdBill);

        Bill result = billService.create(billToCreate);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(mockBillDao, times(1)).create(billToCreate);
    }

    @Test
    public void testCreate_shouldThrowValidationException_whenBillIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            billService.create(null);
        });

        assertEquals("Bill cannot be null.", exception.getMessage());
        verify(mockBillDao, never()).create(any(Bill.class));
    }

    @Test
    public void testCreate_shouldThrowValidationException_whenCustomerIdIsInvalid() {
        // Corrected constructor call to include a new Date() object
        Bill invalidBill = new Bill(0, 1, new Date(), 100.0);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            billService.create(invalidBill);
        });

        assertEquals("Customer ID must be a positive integer.", exception.getMessage());
        verify(mockBillDao, never()).create(any(Bill.class));
    }

    @Test
    public void testCreate_shouldThrowValidationException_whenUserIdIsInvalid() {
        // Corrected constructor call to include a new Date() object
        Bill invalidBill = new Bill(1, 0, new Date(), 100.0);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            billService.create(invalidBill);
        });

        assertEquals("User ID must be a positive integer.", exception.getMessage());
        verify(mockBillDao, never()).create(any(Bill.class));
    }

    @Test
    public void testCreate_shouldThrowValidationException_whenTotalIsZero() {
        // Corrected constructor call to include a new Date() object
        Bill invalidBill = new Bill(1, 1, new Date(), 0.0);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            billService.create(invalidBill);
        });

        assertEquals("Total price must be greater than zero.", exception.getMessage());
        verify(mockBillDao, never()).create(any(Bill.class));
    }

    @Test
    public void testCreate_shouldThrowValidationException_whenTotalIsNegative() {
        // Corrected constructor call to include a new Date() object
        Bill invalidBill = new Bill(1, 1, new Date(), -10.0);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            billService.create(invalidBill);
        });

        assertEquals("Total price cannot be negative.", exception.getMessage());
        verify(mockBillDao, never()).create(any(Bill.class));
    }

    // --- Tests for findAll() ---
    @Test
    public void testFindAll_shouldReturnListOfBills() {
        // Corrected constructor calls to include a new Date() object
        List<Bill> expectedList = List.of(
                new Bill(1, 1, 1, new Date(), 100.0),
                new Bill(2, 2, 1, new Date(), 150.0)
        );
        when(mockBillDao.findAll()).thenReturn(expectedList);

        List<Bill> result = billService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedList, result);
    }

    @Test
    public void testFindAll_shouldThrowNotFoundException_whenListIsEmpty() {
        when(mockBillDao.findAll()).thenReturn(new ArrayList<>());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            billService.findAll();
        });

        assertEquals("No bills found.", exception.getMessage());
    }

    // --- Tests for findById() ---
    @Test
    public void testFindById_shouldReturnBill() {
        int billId = 1;
        // Corrected constructor call to include a new Date() object
        Bill expectedBill = new Bill(billId, 1, 1, new Date(), 100.0);
        when(mockBillDao.findById(billId)).thenReturn(expectedBill);

        Bill result = billService.findById(billId);

        assertNotNull(result);
        assertEquals(billId, result.getId());
        assertEquals(expectedBill, result);
    }

    @Test
    public void testFindById_shouldThrowNotFoundException_whenBillIsNotFound() {
        int billId = 99;
        when(mockBillDao.findById(billId)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            billService.findById(billId);
        });

        assertEquals("Bill with ID " + billId + " not found.", exception.getMessage());
    }
}
