package org.io.GreenGame;

import jakarta.persistence.EntityManager;
import org.io.GreenGame.inventory.model.Inventory;
import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.inventory.repository.InventoryRepository;
import org.io.GreenGame.inventory.repository.ItemRepository;
import org.io.GreenGame.inventory.service.InventoryServiceImplementation;
import org.io.GreenGame.user.model.GreenGameUser;
import org.io.GreenGame.user.model.UserRegisterForm;
import org.io.GreenGame.user.repository.UserRepository;
import org.io.GreenGame.user.service.AuthService;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class GreenGameApplicationTests {

	@Test
	void contextLoads() {
	}
	@Mock
	private UserRepository userRepository;

	@Mock
	private InventoryRepository inventoryRepository;

	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	private InventoryServiceImplementation inventoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAssignUserToInventory() {
//		System.out.println("testAssignUserToInventory");
//		Long userID = 1L;
//		Long id = 1L;
//		Inventory inventory = new Inventory(userID, Arrays.asList(new Item[10]), 0.0);
////		when(inventoryRepository.checkInventoryIDInDatabase(id)).thenReturn(0L);
////		when(userRepository.checkIfIdIsInDatabase(userID)).thenReturn(1L);
////		when(inventoryRepository.save(inventory)).thenReturn(inventory);
//
////		assertTrue(inventoryRepository.checkInventoryIDInDatabase(id) == 0);
//		AuthServiceImplementation authService = new AuthServiceImplementation();
//		authService.registerUser(new UserRegisterForm("test", "test", "test", "test"));
//
//		assertTrue(userRepository.checkIfIdIsInDatabase(userID) == 1);
//
////		Boolean result = inventoryService.assignUserToInventory(userID);
////		assertTrue(result);
////		System.out.println("invenotry: "+ inventoryService.getUserInventory(userID).toString());
	}

	@Test
	public void testAddItemToInventory() {
		System.out.println("testAddItemToInventory");
		Long userID = 1L;
		Long itemID = 1L;
		Long id = 1L;
//		Inventory inventory = new Inventory(userID, Arrays.asList(new Item[10]), 10.0);
//		Item item = new Item("testowy_item", "test", 2.0);
//		when(inventoryRepository.checkInventoryIDInDatabase(id)).thenReturn(1L);
//		System.out.println(inventoryRepository.checkInventoryIDInDatabase(id));
//		when(userRepository.checkIfIdIsInDatabase(userID)).thenReturn(1L);
//		when(itemRepository.checkItemIDInDatabase(itemID)).thenReturn(1L);
////		when(inventoryRepository.findInventoryByUserID(userID)).thenReturn(inventory);
//		assertTrue(inventoryService.assignUserToInventory(userID));

		System.out.println("inventory: " + inventoryRepository.findInventoryByUserID(userID).toString());


//		when(itemRepository.findItemByID(itemID)).thenReturn(item);
//		System.out.println("item: "+itemRepository.findItemByID(itemID).toString());


//		when(inventoryRepository.save(inventory)).thenReturn(inventory);
//		Boolean result = inventoryService.addItemToInventory(userID, item);
//		assertTrue(result);
	}
}
