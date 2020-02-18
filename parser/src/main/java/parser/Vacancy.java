package parser;


import java.util.Date;
import java.util.Objects;

public class Vacancy {
	private int id;
	private String name;
	private String description;
	private Date date;
	private String reference;

	public Vacancy() {
	}

	public Vacancy(String name, String description, String reference, Date date) {
		this.name = name;
		this.description = description;
		this.date = date;
		this.reference = reference;
	}

	public Vacancy(int id, String name, String description, String reference, Date date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.date = date;
		this.reference = reference;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Vacancy)) {
			return false;
		}
		Vacancy vacancy = (Vacancy) o;
		return id == vacancy.id
				&& Objects.equals(name, vacancy.name)
				&& Objects.equals(description, vacancy.description)
				&& Objects.equals(date, vacancy.date)
				&& Objects.equals(reference, vacancy.reference);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, date, reference);
	}
}
