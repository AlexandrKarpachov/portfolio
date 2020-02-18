package ru.job4j.carstore.models;

import com.sun.istack.NotNull;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "engines")
public class Engine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Enumerated(EnumType.STRING) 
	private FuelType type;
	
	private double volume;
	private int power;
	
	public Engine() {
	}
	
	
	public Engine(FuelType type, double volume, int power) {
		this.type = type;
		this.volume = volume;
		this.power = power;
	}


	
	
	/**
	 * @return the power
	 */
	public int getPower() {
		return power;
	}
	/**
	 * @param power the power to set
	 */
	public void setPower(int power) {
		this.power = power;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the type
	 */
	public FuelType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(FuelType type) {
		this.type = type;
	}
	/**
	 * @return the volume
	 */
	public double getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(double volume) {
		this.volume = volume;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, power, type, volume);
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
		Engine other = (Engine) obj;
		return id == other.id && power == other.power && type == other.type
				&& Double.doubleToLongBits(volume) == Double.doubleToLongBits(other.volume);
	}

	@Override
	public String toString() {
		return String.format("Engine [id=%s, type=%s, volume=%s, power=%s]", id, type.name(), volume, power);
	}
	
	
}
