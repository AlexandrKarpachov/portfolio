package ru.job4j.carstore.persistence;

import java.util.List;
import java.util.function.Function;

import javax.persistence.Query;


import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import ru.job4j.carstore.models.Brand;
import ru.job4j.carstore.persistence.interfaces.IBrandStorage;

public class BrandStorage implements IBrandStorage {
	private final static BrandStorage INSTANCE = new BrandStorage();
	private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
	private final static Logger LOG = LoggerFactory.getLogger(CarStoreageDB.class);
	private BrandStorage() {
	}

	public static BrandStorage getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void addBrand(Brand brand) {
		tx(session -> session.save(brand), "Can't add brand = " + brand.toString());
	}

	@Override
	public void update(Brand brand) {
		tx(session -> {
			session.update(brand);
			return true;
		}, "Can't update brand =" + brand.toString());
	}

	@Override
	public void delete(Brand brand) {
		tx(session -> {
			session.delete(brand);
			return true;
		}, "Can't delete brand =" + brand.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Brand> getAll() {
		return tx(session -> session.createQuery("from Brand b order by b.name").list(),
				"Can't get brands");
	}

	@Override
	public Brand getById(long id) {
		final Session session = factory.openSession();
	    final Transaction tx = session.beginTransaction();
	    Brand result = null;
	    try {
	        result = session.get(Brand.class, id);
	        tx.commit();
	        Hibernate.initialize(result.getModels()); 
	    } catch (final Exception e) {
	    	 LOG.error("Can't get Brand by id= " + id, e);
	    } finally {
	        session.close();
	    }
	    return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Brand getByName(String name) {
		Brand result = null;
		final Session session = factory.openSession();
	    final Transaction tx = session.beginTransaction();
	    try {
	    	Query query = session.createQuery("from Brand where name = :brandName");
			query.setParameter("brandName", name);
			result = (Brand) query.getResultList()
					.stream().findFirst().orElse(null);
			tx.commit();
			Hibernate.initialize(result.getModels()); 
	    } catch (final Exception e) {
	    	 LOG.error("Can't get Brand by name= " + name, e);
	    } finally {
	        session.close();
	    }
	    return result;
	}

	private <T> T tx(final Function<Session, T> command, String msg) {
	    final Session session = factory.openSession();
	    final Transaction tx = session.beginTransaction();
	    try {
	        T rsl = command.apply(session);
	        tx.commit();
	        return rsl;
	    } catch (final Exception e) {
	        session.getTransaction().rollback();
	        LOG.error(msg, e);
	    } finally {
	        session.close();
	    }
	    return null;
	}
}
