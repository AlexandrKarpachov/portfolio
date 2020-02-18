package ru.job4j.carstore.service;

import java.util.List;

import ru.job4j.carstore.models.Brand;
import ru.job4j.carstore.models.Car;
import ru.job4j.carstore.models.CarFilter;
import ru.job4j.carstore.models.User;
import ru.job4j.carstore.persistence.BrandStorage;
import ru.job4j.carstore.persistence.CarStoreageDB;
import ru.job4j.carstore.persistence.UserStorageDB;

public class Validator implements IValidator {
	private final BrandStorage brandStorage = BrandStorage.getInstance();
	private final CarStoreageDB carStorage = CarStoreageDB.getInstance();	
	private final UserStorageDB userStorage = UserStorageDB.getInstance();
	private static final Validator INSTANCE = new Validator();
	
	
	private Validator() {
	}
	
	public static Validator getInstance() {
		return INSTANCE;
	}
	

	// -------------- BrandDAO --------------
	@Override
	public void addBrand(Brand brand) {
		brandStorage.addBrand(brand);
	}

	@Override
	public void updateBrand(Brand brand) {
		brandStorage.update(brand);
	}

	@Override
	public void deleteBrand(Brand brand) {
		brandStorage.delete(brand);
	}

	@Override
	public List<Brand> getAllBrands() {
		return brandStorage.getAll();
	}

	@Override
	public Brand getBrandById(long id) {
		return brandStorage.getById(id);
	}

	@Override
	public Brand getBrandByName(String name) {
		return brandStorage.getByName(name);
	}

	
	//--------- Car DAO-----------
	
	@Override
	public List<Car> getCarByFilter(CarFilter filter) {
		return carStorage.getByFilter(filter);
	}

	@Override
	public void addCar(Car car) {
		carStorage.save(car);
		
	}

	@Override
	public void updateCar(Car car) {
		carStorage.update(car);
		
	}

	@Override
	public void deleteCar(Car car) {
		carStorage.delete(car);
		
	}

	@Override
	public Car getCarById(long id) {
		return carStorage.getById(id);
	}

	@Override
	public Long getPagesCount(CarFilter filter) {
		return carStorage.getPagesCount(filter);
	}
	
	//------------User DAO----------
	
	@Override
	public void addUser(User user) {
		userStorage.addUser(user);
	}

	@Override
	public void deleteUser(User user) {
		userStorage.addUser(user);
	}

	@Override
	public void updateUser(User user) {
		userStorage.updateUser(user);
	}

	@Override
	public User getUserById(long id) {
		return userStorage.getUserById(id);
	}

	@Override
	public User getUserByLogin(String login) {
		return userStorage.getUserByLogin(login);
	}

	

}
