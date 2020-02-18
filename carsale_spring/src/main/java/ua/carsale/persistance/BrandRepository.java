package ua.carsale.persistance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import ua.carsale.domain.Brand;


@Repository
public interface BrandRepository extends CrudRepository<Brand, Long> {

	@Query("SELECT b FROM Brand b JOIN FETCH b.models WHERE b.id = (:id)")
    public Brand findByIdAndFetchModelsEagerly(@Param("id") Long id);
	
}
