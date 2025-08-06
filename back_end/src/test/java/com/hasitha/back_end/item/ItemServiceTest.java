package com.hasitha.back_end.item;

import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemServiceTest {

    private ItemDAO mockItemDao;
    private ItemService itemService;

    @BeforeEach
    public void setUp() {
        mockItemDao = mock(ItemDAO.class);
        itemService = new ItemService(mockItemDao);
    }

    // --- Tests for findAll() ---
    @Test
    public void testFindAll_shouldReturnItemList() {
        Item item1 = new Item(1, "Laptop", 1200.00, 50);
        Item item2 = new Item(2, "Mouse", 25.00, 200);
        when(mockItemDao.findAll()).thenReturn(List.of(item1, item2));

        List<Item> items = itemService.findAll();
        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals("Laptop", items.get(0).getName());
    }

    @Test
    public void testFindAll_shouldThrowNotFoundIfEmpty() {
        when(mockItemDao.findAll()).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> itemService.findAll());
    }

    // --- Tests for findById() ---
    @Test
    public void testFindById_shouldReturnItem() {
        Item item = new Item(1, "Laptop", 1200.00, 50);
        when(mockItemDao.findById(1)).thenReturn(item);

        Item result = itemService.findById(1);
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
    }

    @Test
    public void testFindById_shouldThrowNotFoundIfNull() {
        when(mockItemDao.findById(99)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> itemService.findById(99));
    }

    // --- Tests for create() ---
    @Test
    public void testCreate_shouldSucceed() {
        Item newItem = new Item("Keyboard", 75.00, 100);
        when(mockItemDao.create(newItem)).thenReturn(newItem);

        Item created = itemService.create(newItem);
        assertNotNull(created);
        assertEquals("Keyboard", created.getName());
    }

    @Test
    public void testCreate_shouldThrowValidationIfNameIsBlank() {
        Item invalidItem = new Item("", 10.00, 5);

        assertThrows(ValidationException.class, () -> itemService.create(invalidItem));
    }

    @Test
    public void testCreate_shouldThrowValidationIfPriceIsZero() {
        Item invalidItem = new Item("Pencil", 0.00, 10);

        assertThrows(ValidationException.class, () -> itemService.create(invalidItem));
    }

    @Test
    public void testCreate_shouldThrowValidationIfStockIsNegative() {
        Item invalidItem = new Item("Pen", 1.50, -5);

        assertThrows(ValidationException.class, () -> itemService.create(invalidItem));
    }

    // --- Tests for update() ---
    @Test
    public void testUpdate_shouldSucceed() {
        Item existingItem = new Item(1, "Old Name", 10.00, 5);
        Item updatedItem = new Item(1, "New Name", 15.00, 10);

        when(mockItemDao.findById(1)).thenReturn(existingItem);
        when(mockItemDao.update(eq(1), any(Item.class))).thenReturn(updatedItem);

        Item result = itemService.update(1, updatedItem);
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals(15.00, result.getPrice(), 0.001);
    }

    @Test
    public void testUpdate_shouldThrowNotFoundIfNotExisting() {
        Item updatedItem = new Item(99, "Nonexistent", 20.00, 1);
        when(mockItemDao.findById(99)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> itemService.update(99, updatedItem));
    }

    @Test
    public void testUpdate_shouldThrowValidationIfNameIsBlank() {
        Item existingItem = new Item(1, "Old Name", 10.00, 5);
        Item invalidUpdate = new Item(1, "", 10.00, 5);

        when(mockItemDao.findById(1)).thenReturn(existingItem);

        assertThrows(ValidationException.class, () -> itemService.update(1, invalidUpdate));
    }

    // --- Tests for delete() ---
    @Test
    public void testDelete_shouldSucceed() {
        when(mockItemDao.findById(1)).thenReturn(new Item());
        doNothing().when(mockItemDao).delete(1);

        assertDoesNotThrow(() -> itemService.delete(1));
        verify(mockItemDao, times(1)).delete(1);
    }

    @Test
    public void testDelete_shouldThrowNotFoundIfNotExisting() {
        when(mockItemDao.findById(99)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> itemService.delete(99));
    }

    // --- Tests for getPriceById() ---
    @Test
    public void testGetPriceById_shouldReturnPrice() {
        Item item = new Item(1, "Laptop", 1200.00, 50);
        when(mockItemDao.findById(1)).thenReturn(item);

        double price = itemService.getPriceById(1);
        assertEquals(1200.00, price, 0.001);
    }

    @Test
    public void testGetPriceById_shouldThrowNotFoundIfNotExisting() {
        when(mockItemDao.findById(99)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> itemService.getPriceById(99));
    }
}
