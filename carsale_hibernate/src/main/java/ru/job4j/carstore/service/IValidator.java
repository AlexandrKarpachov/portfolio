package ru.job4j.carstore.service;

import java.util.List;

import ru.job4j.carstore.models.Brand;
import ru.job4j.carstore.models.Car;
import ru.job4j.carstore.models.CarFilter;
import ru.job4j.carstore.models.User;

public interface IValidator {
	//brand
	public void addBrand(Brand brand);
	public void updateBrand(Brand brand);
	public void deleteBrand(Brand brand);
	public List<Brand> getAllBrands();
	public Brand getBrandById(long id);
	public Brand getBrandByName(String name);
	//car
	public void addCar(Car car);
	public void updateCar(Car car);
	public void deleteCar(Car car);
	public Car getCarById(long id);
	public List<Car> getCarByFilter(CarFilter filter);
	public Long getPagesCount(CarFilter filter);
	//user
	void addUser(User user);
	void deleteUser(User user);
	void updateUser(User user);
	User getUserById(long id);
	User getUserByLogin(String login);
}
