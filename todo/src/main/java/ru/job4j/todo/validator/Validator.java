package ru.job4j.todo.validator;

import java.util.List;

import ru.job4j.todo.models.Item;
import ru.job4j.todo.persistence.DBStorage;
import ru.job4j.todo.persistence.IStore;

public class Validator implements IValidator {
	private final static Validator INSTANCE = new Validator();
	private final IStore storage = DBStorage.getInstance();
	
	private Validator() {
		
	}

	public static Validator getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void addItem(Item item) {
		storage.addItem(item);
		
	}

	@Override
	public void updateItem(Item item) {
		storage.updateItem(item);
	}

	@Override
	public List<Item> getAll() {
		return storage.getAll();
	}

	@Override
	public Item getByID(Long id) {
		return storage.getByID(id);
	}
	
}
