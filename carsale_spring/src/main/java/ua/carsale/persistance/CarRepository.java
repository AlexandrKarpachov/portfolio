package ua.carsale.persistance;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import ua.carsale.domain.Car;


@Repository
public interface CarRepository extends CrudRepository<Car, Long>, CustomizedCarRepo<Car>{

}
