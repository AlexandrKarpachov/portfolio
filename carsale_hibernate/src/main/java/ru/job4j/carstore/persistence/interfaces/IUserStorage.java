package ru.job4j.carstore.persistence.interfaces;

import ru.job4j.carstore.models.User;

public interface IUserStorage {
	void addUser(User user);
	void deleteUser(User user);
	void updateUser(User user);
	User getUserById(long id);
	User getUserByLogin(String name);
}
