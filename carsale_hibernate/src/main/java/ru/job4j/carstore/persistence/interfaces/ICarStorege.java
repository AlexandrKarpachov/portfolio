package ru.job4j.carstore.persistence.interfaces;

import java.util.List;

import ru.job4j.carstore.models.Car;
import ru.job4j.carstore.models.CarFilter;

public interface ICarStorege {
	public void save(Car car);
	public void update(Car car);
	public void delete(Car car);
	public Car getById(long id);
	public List<Car> getByFilter(CarFilter filter);
	public Long getPagesCount(CarFilter filter);
}
