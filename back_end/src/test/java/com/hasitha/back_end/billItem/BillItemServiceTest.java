package com.hasitha.back_end.billItem;

import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BillItemServiceTest {

    private BillItemDAO mockBillItemDao;
    private BillItemService billItemService;

    @BeforeEach
    public void setUp() {
        mockBillItemDao = mock(BillItemDAO.class);
        billItemService = new BillItemService(mockBillItemDao);
    }

    // --- Tests for saveBillItems() ---
    @Test
    public void testSaveBillItems_shouldSucceed() {
        int billId = 1;
        List<BillItem> items = List.of(
                new BillItem(1, 2, 25.0),
                new BillItem(2, 1, 50.0)
        );

        doNothing().when(mockBillItemDao).saveItems(billId, items);

        assertDoesNotThrow(() -> billItemService.saveBillItems(billId, items));
        verify(mockBillItemDao, times(1)).saveItems(billId, items);
    }

    @Test
    public void testSaveBillItems_shouldThrowValidationExceptionForInvalidBillId() {
        int billId = 0;
        List<BillItem> items = List.of(new BillItem(1, 2, 25.0));

        ValidationException exception = assertThrows(ValidationException.class,
                () -> billItemService.saveBillItems(billId, items));

        assertEquals("Invalid bill ID: must be a positive integer.", exception.getMessage());
        verify(mockBillItemDao, never()).saveItems(anyInt(), anyList());
    }

    @Test
    public void testSaveBillItems_shouldThrowValidationExceptionForEmptyItems() {
        int billId = 1;
        List<BillItem> items = new ArrayList<>();

        ValidationException exception = assertThrows(ValidationException.class,
                () -> billItemService.saveBillItems(billId, items));

        assertEquals("A bill must contain at least one item.", exception.getMessage());
        verify(mockBillItemDao, never()).saveItems(anyInt(), anyList());
    }

    @Test
    public void testSaveBillItems_shouldThrowValidationExceptionForInvalidItemId() {
        int billId = 1;
        List<BillItem> items = List.of(new BillItem(0, 2, 25.0));

        ValidationException exception = assertThrows(ValidationException.class,
                () -> billItemService.saveBillItems(billId, items));

        assertEquals("Item ID must be a positive integer.", exception.getMessage());
    }

    @Test
    public void testSaveBillItems_shouldThrowValidationExceptionForInvalidQuantity() {
        int billId = 1;
        List<BillItem> items = List.of(new BillItem(1, 0, 25.0));

        ValidationException exception = assertThrows(ValidationException.class,
                () -> billItemService.saveBillItems(billId, items));

        assertEquals("Quantity must be greater than 0 for item ID: 1", exception.getMessage());
    }

    // --- Tests for getBillItemsByBillId() ---
    @Test
    public void testGetBillItemsByBillId_shouldReturnItems() {
        int billId = 1;
        List<BillItem> expectedItems = List.of(
                new BillItem(1, 2, 25.0),
                new BillItem(2, 1, 50.0)
        );
        when(mockBillItemDao.findByBillId(billId)).thenReturn(expectedItems);

        List<BillItem> result = billItemService.getBillItemsByBillId(billId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedItems, result);
    }

    @Test
    public void testGetBillItemsByBillId_shouldThrowNotFoundExceptionWhenNoItemsFound() {
        int billId = 1;
        when(mockBillItemDao.findByBillId(billId)).thenReturn(new ArrayList<>());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> billItemService.getBillItemsByBillId(billId));

        assertEquals("No items found for bill ID: 1", exception.getMessage());
    }

    @Test
    public void testGetBillItemsByBillId_shouldThrowValidationExceptionForInvalidBillId() {
        int billId = 0;

        ValidationException exception = assertThrows(ValidationException.class,
                () -> billItemService.getBillItemsByBillId(billId));

        assertEquals("Invalid bill ID: must be a positive integer.", exception.getMessage());
        verify(mockBillItemDao, never()).findByBillId(anyInt());
    }
}
