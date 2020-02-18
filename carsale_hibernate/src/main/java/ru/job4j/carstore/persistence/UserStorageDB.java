package ru.job4j.carstore.persistence;

import java.util.function.Function;


import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import ru.job4j.carstore.models.User;
import ru.job4j.carstore.persistence.interfaces.IUserStorage;

public class UserStorageDB implements IUserStorage {
	private static final UserStorageDB INSTANCE = new UserStorageDB();
	private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
	private final static Logger LOG = LoggerFactory.getLogger(UserStorageDB.class);

	private UserStorageDB() {
	}
	
	public static UserStorageDB getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void addUser(User user) {
		tx(session -> session.save(user));
		
	}

	@Override
	public void deleteUser(User user) {
		tx(session -> {
			session.delete(user);
			return true;
		});
		
	}

	@Override
	public void updateUser(User user) {
		tx(session -> {
			session.update(user);
			return true;
		});
		
	}

	@Override
	public User getUserById(long id) {
		return tx(session -> session.get(User.class, id));			
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserByLogin(String login) {
		return (User) tx(session -> session.createQuery("from User where login = :userLogin")
									.setParameter("userLogin", login)
									.getResultList()
									.stream().findFirst().orElse(null));
	}
	
	private <T> T tx(final Function<Session, T> command) {
	    final Session session = factory.openSession();
	    final Transaction tx = session.beginTransaction();
	    try {
	        T rsl = command.apply(session);
	        tx.commit();
	        return rsl;
	    } catch (final Exception e) {
	        session.getTransaction().rollback();
			LOG.error(e.getMessage(), e);
	        throw e;
	    } finally {
	        session.close();
	    }
	}
	
}
