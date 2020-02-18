package ru.job4j.carstore.persistence.interfaces;

import java.util.List;

import ru.job4j.carstore.models.Brand;


public interface IBrandStorage {
	public void addBrand(Brand brand);
	public void update(Brand brand);
	public void delete(Brand brand);
	public List<Brand> getAll();
	public Brand getById(long id);
	public Brand getByName(String name);
	
}
