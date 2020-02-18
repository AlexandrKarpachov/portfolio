package ru.job4j.carstore.persistence;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import ru.job4j.carstore.models.Car;
import ru.job4j.carstore.models.CarFilter;
import ru.job4j.carstore.persistence.interfaces.ICarStorege;


public class CarStoreageDB implements ICarStorege {
	private static final CarStoreageDB INSTANCE = new CarStoreageDB();
	private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
	private final static Logger LOG = LoggerFactory.getLogger(CarStoreageDB.class);
	private CarStoreageDB() {
	}
	
	public static CarStoreageDB getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void save(Car car) {
		this.tx(session -> session.save(car));
		
	}

	@Override
	public void update(Car car) {
		this.tx(session -> {
			session.update(car);
			return true;
			}
		);
	}

	@Override
	public void delete(Car car) {
		this.tx(session -> {
			session.delete(car);
			return true;
			}
		);
		
	}

	@Override
	public Car getById(long id) {
		return this.tx(session -> session.get(Car.class, id));
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
	        throw e;
	    } finally {
	        session.close();
	    }
	}

	@Override
	public Long getPagesCount(CarFilter filter) {
		long result = 0L;
		try (Session session = factory.openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Long> query = builder.createQuery(Long.class);
			Root<Car> root = query.from(Car.class);
			CriteriaQuery<Long> select = query.select(builder.count(root));
			List<Predicate> predicates = filter.getFilterList(builder, root);
			select.where(predicates.toArray(new Predicate[] {}));
			TypedQuery<Long> typedQuery = session.createQuery(select);
			Long rows = typedQuery.getSingleResult();
			int rowInPage = filter.getMaxResultSize();
			result = rows % rowInPage > 0 ? (rows / rowInPage) + 1 : rows / rowInPage;
		} catch (Exception e) {
			LOG.error("can't compute page count", e);
		}
		return result;
	}
	
	@Override
	public List<Car> getByFilter(CarFilter filter) {
		List<Car> rst = new ArrayList<>();
		try (Session session = factory.openSession()) {                 
		CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Car> query = builder.createQuery(Car.class);
        Root<Car> root = query.from(Car.class);
        List<Predicate> predicates = filter.getFilterList(builder, root);
        query.select(root).where(predicates.toArray(new Predicate[]{}));
		query.orderBy(builder.asc(root.get("created")));
		rst = session.createQuery(query)
        		.setFirstResult(filter.getPage() * filter.getMaxResultSize())
        		.setMaxResults(filter.getMaxResultSize())
        		.getResultList();
		} catch (Exception e) {
			LOG.error("can't fetch car list", e);
		}
		return rst;
	}
}
