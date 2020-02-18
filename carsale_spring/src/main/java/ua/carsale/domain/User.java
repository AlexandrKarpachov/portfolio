package ua.carsale.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, name = "name")
	private String name;
	
	@Column(nullable = false, name = "surname")
	private String surname;
	
	@JsonIgnore
	@Column(nullable = false, name = "login", unique=true)
	private String login;
	
	@JsonIgnore
	@Column(nullable = false, name = "password")
	private String password;
	
	@Transient
	private String confirmPassword;
	
	@Column(length = 20, columnDefinition = "varchar(20) default 'ROLE_USER'")
	@Enumerated(EnumType.STRING) 
	private UserRole role = UserRole.USER;
	
	@Column(columnDefinition = "boolean default true")
	private boolean enabled = true;
	
	@Column(nullable = false, name = "phone", length = 15)
	private String phone;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Car> cars = new ArrayList<Car>();

	
	public User() {
	}

	public void addCar(Car car) {
		cars.add(car);
		car.setUser(this);
	}
	
	public boolean checkPass(String password) {
		return this.password.contentEquals(password);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cars, id, login, name, password, phone, surname);
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
		User other = (User) obj;
		return Objects.equals(cars, other.cars) && Objects.equals(id, other.id) && Objects.equals(login, other.login)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password)
				&& Objects.equals(phone, other.phone) && Objects.equals(surname, other.surname);
	}

	

	@Override
	public String toString() {
		return String.format("User [id=%s, name=%s, surname=%s, login=%s, enabled=%s, phone=%s]", id, name, surname,
				login, enabled, phone);
	}

}
