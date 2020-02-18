package ua.carsale.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
 
@Entity
@Table(name = "brands")
public class Brand implements Serializable {
 
    private static final long serialVersionUID = 0;
    private Long id;
    private String name;
    private Set<Model> models;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

    @Column(name = "name", unique = true, nullable = false, length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "brand_id")
	public Set<Model> getModels() {
		return models;
	}

	public void setModels(Set<Model> models) {
		this.models = models;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Brand other = (Brand) obj;
		return Objects.equals(id, other.id) && Objects.equals(models, other.models) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return String.format("Brand [id=%s, name=%s, models=%s]", id, name, models);
	}
    
	
}