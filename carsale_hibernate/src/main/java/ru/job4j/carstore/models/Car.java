package ru.job4j.carstore.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;


@Entity
@Table(name = "cars")
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "body_id")
	private Body body;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "engine_id")
	private Engine engine;
	
	@NotNull
	@Enumerated(EnumType.STRING) 
	private ShiftGear shiftGear;
	
	@Column(nullable = false, name = "brand")
	private String brand;
	
	@Column(nullable = false, name = "model")
	private String model;
	
	@Column(updatable = false)
	@org.hibernate.annotations.CreationTimestamp
	private Timestamp created;
	
	@Column(nullable = false)
	private Integer year;
	
	@Column(name = "price", nullable = false)
	private Integer price;
	
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "images", joinColumns = @JoinColumn(name = "car_id"))
	private List<String> images = new ArrayList<>();
	
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@Column(name = "description", length = 1000)
	private String description;
	
	public Car() {
	}
	
	public Car(Body body, Engine engine) {
		this.body = body;
		this.engine = engine;
	}

	
	public Car(String brand, String model, ShiftGear shifGear, Integer year, Integer price) {
		this.brand = brand;
		this.model = model;
		this.shiftGear = shifGear;
		this.year = year;
		this.price = price;
	}

	public void addImage(String img) {
		images.add(img);
	}
	
	
	public List<String> getImages() {
		return images;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public ShiftGear getShiftGear() {
		return shiftGear;
	}

	public void setShiftGear(ShiftGear shifGear) {
		this.shiftGear = shifGear;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(body, brand, created, engine, id, isActive, model, price, shiftGear, user, year);
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
		Car other = (Car) obj;
		return Objects.equals(body, other.body) && Objects.equals(brand, other.brand)
				&& Objects.equals(created, other.created) && Objects.equals(engine, other.engine)
				&& Objects.equals(id, other.id) && isActive == other.isActive && Objects.equals(model, other.model)
				&& Objects.equals(price, other.price) && shiftGear == other.shiftGear && Objects.equals(user, other.user)
				&& Objects.equals(year, other.year);
	}

	@Override
	public String toString() {
		return String.format(
				"Car [id=%s, body=%s, engine=%s, shiftGear=%s, brand=%s, model=%s, created=%s, year=%s, price=%s, user=%s, images=%s, isActive=%s, description=%s]",
				id, body, engine, shiftGear, brand, model, created, year, price, user, images, isActive, description);
	}
	
	
}
