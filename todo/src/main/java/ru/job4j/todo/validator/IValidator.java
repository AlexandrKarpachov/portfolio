package ru.job4j.todo.validator;

import java.util.List;

import ru.job4j.todo.models.Item;

public interface IValidator {
	void addItem(Item item);
    
	void updateItem(Item item);
    
	List<Item> getAll();
    
	Item getByID(Long id);
}
