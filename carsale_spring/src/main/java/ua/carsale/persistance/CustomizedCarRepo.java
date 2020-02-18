package ua.carsale.persistance;

import ua.carsale.domain.CarFilter;

import java.util.List;



public interface CustomizedCarRepo<T> {
	List<T> findByCarFilter(CarFilter filter);
	
	Long getPagesCount(CarFilter filter);
}
