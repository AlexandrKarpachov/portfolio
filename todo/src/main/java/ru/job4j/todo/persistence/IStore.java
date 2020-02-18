package ru.job4j.todo.persistence;

import java.util.List;

import ru.job4j.todo.models.Item;

public interface IStore {
	void addItem(Item item);
    void updateItem(Item item);
    List<Item> getAll();
    Item getByID(Long id);
}
