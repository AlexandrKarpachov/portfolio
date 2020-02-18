package ua.carsale.persistance;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.carsale.domain.User;

@Repository
public interface  UserRepository extends CrudRepository<User, Long> {
	User findByLogin(String login);
}
