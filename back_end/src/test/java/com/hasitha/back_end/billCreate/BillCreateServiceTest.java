package com.hasitha.back_end.billCreate;

import com.hasitha.back_end.bill.Bill;
import com.hasitha.back_end.bill.BillDTO;
import com.hasitha.back_end.bill.BillService;
import com.hasitha.back_end.billCreate.CreateBillRequest.BillItemRequest;
import com.hasitha.back_end.billItem.BillItem;
import com.hasitha.back_end.billItem.BillItemDTO;
import com.hasitha.back_end.billItem.BillItemService;
import com.hasitha.back_end.customer.Customer;
import com.hasitha.back_end.customer.CustomerService;
import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import com.hasitha.back_end.item.Item;
import com.hasitha.back_end.item.ItemService;
import com.hasitha.back_end.user.User;
import com.hasitha.back_end.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BillCreateServiceTest {

    private ItemService mockItemService;
    private CustomerService mockCustomerService;
    private UserService mockUserService;
    private BillService mockBillService;
    private BillItemService mockBillItemService;

    private BillCreateService billCreateService;

    @BeforeEach
    public void setUp() {
        mockItemService = mock(ItemService.class);
        mockCustomerService = mock(CustomerService.class);
        mockUserService = mock(UserService.class);
        mockBillService = mock(BillService.class);
        mockBillItemService = mock(BillItemService.class);

        billCreateService = new BillCreateService(
                mockItemService, mockCustomerService, mockUserService, mockBillService, mockBillItemService
        );
    }

    // --- Test of createBill method ---
    @Test
    public void testCreateBill_shouldSucceed() {
        // Arrange
        CreateBillRequest req = new CreateBillRequest();
        req.setUserId(1);
        req.setCustomerId(1);

        CreateBillRequest.BillItemRequest itemReq1 = new CreateBillRequest.BillItemRequest();
        itemReq1.setItemId(1);
        itemReq1.setQuantity(2);

        CreateBillRequest.BillItemRequest itemReq2 = new CreateBillRequest.BillItemRequest();
        itemReq2.setItemId(2);
        itemReq2.setQuantity(3);

        req.setItems(List.of(itemReq1, itemReq2));

        Item item1 = new Item(1, "Item One", 10.0, 100);
        Item item2 = new Item(2, "Item Two", 20.0, 50);

        when(mockItemService.getPriceById(1)).thenReturn(10.0);
        when(mockItemService.getPriceById(2)).thenReturn(20.0);

        doNothing().when(mockUserService).ensureUserExists(1);
        doNothing().when(mockCustomerService).ensureCustomerExists(1);
        doNothing().when(mockItemService).ensureItemExists(1);
        doNothing().when(mockItemService).ensureItemExists(2);

        when(mockBillService.create(any(Bill.class))).thenReturn(new Bill(1, 1, 1, new Date(), 80.0));

        List<BillItem> createdBillItems = List.of(
                new BillItem(1, 1, 2, 20.0),
                new BillItem(2, 2, 3, 60.0)
        );
        when(mockBillItemService.getBillItemsByBillId(1)).thenReturn(createdBillItems);

        when(mockItemService.findById(1)).thenReturn(item1);
        when(mockItemService.findById(2)).thenReturn(item2);

        when(mockCustomerService.findById(1)).thenReturn(new Customer(1, "John", "Doe", "123 Main St", "+94712345678"));
        when(mockUserService.findById(1)).thenReturn(new User(1, "John", "Doe", "johndoe", "cashier", "cashier"));

        // Act
        BillDTO result = billCreateService.createBill(req);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(80.0, result.getTotal());
        assertEquals(2, result.getBillItems().size());

        verify(mockUserService, times(1)).ensureUserExists(1);
        verify(mockCustomerService, times(1)).ensureCustomerExists(1);
        verify(mockBillService, times(1)).create(any(Bill.class));
        verify(mockBillItemService, times(1)).saveBillItems(eq(1), anyList());
        verify(mockBillItemService, times(1)).getBillItemsByBillId(1);
    }

    @Test
    public void testCreateBill_shouldThrowValidationException_forEmptyItems() {
        // Arrange
        CreateBillRequest req = new CreateBillRequest();
        req.setUserId(1);
        req.setCustomerId(1);
        req.setItems(Collections.emptyList());

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> billCreateService.createBill(req));
        assertEquals("Bill must contain at least one item.", exception.getMessage());
        verify(mockBillService, never()).create(any());
    }

    @Test
    public void testCreateBill_shouldThrowValidationException_forInvalidQuantity() {
        // Arrange
        CreateBillRequest req = new CreateBillRequest();
        req.setUserId(1);
        req.setCustomerId(1);

        CreateBillRequest.BillItemRequest itemReq = new CreateBillRequest.BillItemRequest();
        itemReq.setItemId(1);
        itemReq.setQuantity(0);
        req.setItems(List.of(itemReq));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> billCreateService.createBill(req));
        assertEquals("Quantity must be > 0 for item 1", exception.getMessage());
        verify(mockBillService, never()).create(any());
    }

    // --- Test of getAllBills method ---
    @Test
    public void testGetAllBills_shouldReturnListOfBillDTOs() {
        // Arrange
        List<Bill> mockBills = List.of(
                new Bill(1, 1, 1, new Date(), 100.0),
                new Bill(2, 2, 1, new Date(), 150.0)
        );
        when(mockBillService.findAll()).thenReturn(mockBills);

        when(mockCustomerService.findById(1)).thenReturn(new Customer(1, "John", "Doe", "123 Main St", "+94712345678"));
        when(mockCustomerService.findById(2)).thenReturn(new Customer(2, "Jane", "Doe", "456 Main St", "+94718765432"));
        when(mockUserService.findById(1)).thenReturn(new User(1, "John", "Doe", "johndoe", "cashier", "cashier"));

        // Act
        List<BillDTO> result = billCreateService.getAllBills();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(100.0, result.get(0).getTotal());
        assertEquals("John", result.get(0).getCustomer().getFirstName());
        assertEquals("johndoe", result.get(0).getUser().getUserName());
    }

    @Test
    public void testGetAllBills_shouldThrowNotFoundException() {
        // Arrange
        when(mockBillService.findAll()).thenThrow(new NotFoundException("No bills found."));

        // Act & Assert
        assertThrows(NotFoundException.class, () -> billCreateService.getAllBills());
    }

    // --- Test of getBillItemsByBillId method ---
    @Test
    public void testGetBillItemsByBillId_shouldReturnItems() {
        // Arrange
        int billId = 1;
        List<BillItem> mockBillItems = List.of(
                new BillItem(1, 1, 2, 20.0),
                new BillItem(2, 2, 3, 60.0)
        );
        when(mockBillItemService.getBillItemsByBillId(billId)).thenReturn(mockBillItems);

        when(mockItemService.findById(1)).thenReturn(new Item(1, "Item One", 10.0, 100));
        when(mockItemService.findById(2)).thenReturn(new Item(2, "Item Two", 20.0, 50));

        // Act
        List<BillItemDTO> result = billCreateService.getBillItemsByBillId(billId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(20.0, result.get(0).getSubTotal());
        assertEquals("Item Two", result.get(1).getItemName());
    }

    @Test
    public void testGetBillItemsByBillId_shouldThrowNotFoundException() {
        // Arrange
        int billId = 99;
        when(mockBillItemService.getBillItemsByBillId(billId)).thenThrow(new NotFoundException("No items found for bill ID: " + billId));

        // Act & Assert
        assertThrows(NotFoundException.class, () -> billCreateService.getBillItemsByBillId(billId));
    }

    // --- Test of getBill method ---
    @Test
    public void testGetBill_shouldReturnBillDTO() {
        // Arrange
        int billId = 1;
        Bill mockBill = new Bill(billId, 1, 1, new Date(), 80.0);
        List<BillItem> mockBillItems = List.of(
                new BillItem(1, 1, 2, 20.0),
                new BillItem(2, 2, 3, 60.0)
        );

        when(mockBillService.findById(billId)).thenReturn(mockBill);
        when(mockBillItemService.getBillItemsByBillId(billId)).thenReturn(mockBillItems);

        when(mockItemService.findById(1)).thenReturn(new Item(1, "Item One", 10.0, 100));
        when(mockItemService.findById(2)).thenReturn(new Item(2, "Item Two", 20.0, 50));

        when(mockCustomerService.findById(1)).thenReturn(new Customer(1, "John", "Doe", "123 Main St", "+94712345678"));
        when(mockUserService.findById(1)).thenReturn(new User(1, "John", "Doe", "johndoe", "password", "cashier"));

        // Act
        BillDTO result = billCreateService.getBill(billId);

        // Assert
        assertNotNull(result);
        assertEquals(billId, result.getId());
        assertEquals(2, result.getBillItems().size());
        assertEquals("John", result.getCustomer().getFirstName());
        assertEquals("johndoe", result.getUser().getUserName());
    }

    @Test
    public void testGetBill_shouldThrowNotFoundException() {
        // Arrange
        int billId = 99;
        when(mockBillService.findById(billId)).thenThrow(new NotFoundException("Bill with ID " + billId + " not found."));

        // Act & Assert
        assertThrows(NotFoundException.class, () -> billCreateService.getBill(billId));
    }
}
