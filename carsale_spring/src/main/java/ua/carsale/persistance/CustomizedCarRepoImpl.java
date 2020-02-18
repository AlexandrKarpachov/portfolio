package ua.carsale.persistance;

import ua.carsale.domain.Car;
import ua.carsale.domain.CarFilter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;



public class CustomizedCarRepoImpl implements CustomizedCarRepo {

	@PersistenceContext
    private EntityManager em;

	@Override
	public List findByCarFilter(CarFilter filter) {
		List<Car> rst = new ArrayList<>();
	              
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Car> query = builder.createQuery(Car.class);
        Root<Car> root = query.from(Car.class);
        List<Predicate> predicates = filter.getFilterList(builder, root);
        query.select(root).where(predicates.toArray(new Predicate[]{}));
		query.orderBy(builder.asc(root.get("created")));
		rst = em.createQuery(query)
        		.setFirstResult(filter.getPage() * filter.getMaxResultSize())
        		.setMaxResults(filter.getMaxResultSize())
        		.getResultList();
		return rst;
	}

	@Override
	public Long getPagesCount(CarFilter filter) {
		Long result = 0L;
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Car> root = query.from(Car.class);
		CriteriaQuery<Long> select = query.select(builder.count(root));
		List<Predicate> predicates = filter.getFilterList(builder, root);
		select.where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Long> typedQuery = em.createQuery(select);
		Long rows = typedQuery.getSingleResult();
		int rowInPage = filter.getMaxResultSize();
		result = rows % rowInPage > 0 ? (rows / rowInPage) + 1 : rows / rowInPage;
		return result;
	}
	
}
