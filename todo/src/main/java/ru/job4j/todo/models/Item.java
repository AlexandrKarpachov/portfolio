package ru.job4j.todo.models;


import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class Item {
	private long id;
	private String desc;
	private Timestamp date;
	private boolean done;
	
	public Item() {	
	}
	
	
	public Item(long id, boolean done) {
		this.id = id;
		this.done = done;
	}


	public Item(String desc, Timestamp date) {
		this.desc = desc;
		this.date = date;
	}

	public Item(long id, String desc, Timestamp date, boolean done) {
		this.id = id;
		this.desc = desc;
		this.date = date;
		this.done = done;
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
	 * @return the description
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the description to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}
	/**
	 * @return the done
	 */
	public boolean getDone() {
		return done;
	}
	/**
	 * @param done the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id, date, desc, done);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Item)) {
			return false;
		}
		Item other = (Item) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (desc == null) {
			if (other.desc != null) {
				return false;
			}
		} else if (!desc.equals(other.desc)) {
			return false;
		}
		if (done != other.done) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("Item [id=%s, desc=%s, date=%s, done=%s]", id, desc, date, done);
	}
	
	
	
	
	
}
