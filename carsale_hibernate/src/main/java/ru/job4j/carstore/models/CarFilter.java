package ru.job4j.carstore.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CarFilter {
	private final static String EMPTY_FIELD_STR = "none";
	private final static int EMPTY_FIELD_NUM = 0;
	private final static int DEFAULT_PAGE_SIZE = 10;
	
	private final List<BiFunction<CriteriaBuilder, Root<Car>, Predicate>> filters 	= new CopyOnWriteArrayList<>(
							 Arrays.asList(
									 this::markFilter,
									 this::modelFilter,
									 this::fuelFilter,
									 this::shiftGearFilter,
									 this::volumeFilter,
									 this::priceFilter,
									 this::yearFilter,
									 this::activeFilter
	));
	
	private int page;
	private int maxResultSize = DEFAULT_PAGE_SIZE;
	private String mark = EMPTY_FIELD_STR;
	private String model = EMPTY_FIELD_STR;
	private String fuelType = EMPTY_FIELD_STR;
	private double minVolume = EMPTY_FIELD_NUM;
	private double maxVolume = EMPTY_FIELD_NUM;
	private String shiftGear = EMPTY_FIELD_STR;
	private int minPrice = EMPTY_FIELD_NUM;
	private int maxPrice = EMPTY_FIELD_NUM;
	private int minYear = EMPTY_FIELD_NUM;
	private int maxYear = EMPTY_FIELD_NUM;
	private boolean active;
		

	public List<Predicate> getFilterList(CriteriaBuilder builder, Root<Car> root) {
		List<Predicate> result = new ArrayList<>();
		for (BiFunction<CriteriaBuilder, Root<Car>, Predicate> predicate : filters) {
			Predicate filter = predicate.apply(builder, root);
			if (filter != null) {
				result.add(filter);
			}
		}
		return result;
	}

	private Predicate markFilter(CriteriaBuilder builder, Root<Car> root) {
		Predicate rst = null;
		if (getMark() != null && !getMark().equals(EMPTY_FIELD_STR)) {
			rst = builder.equal(root.get("brand"), getMark());
		}
		return rst;
	}
	
	private Predicate activeFilter(CriteriaBuilder builder, Root<Car> root) {
		Predicate rst = null;
		if (active) {
			rst = builder.equal(root.get("isActive"), true);
		}
		return rst;
	}
	
	private Predicate modelFilter(CriteriaBuilder builder, Root<Car> root) {
		Predicate rst = null;
		if (getModel() != null && !getModel().equals(EMPTY_FIELD_STR)) {
			rst = builder.equal(root.get("model"), getModel());
        }
		return rst;
	}
	
	private Predicate fuelFilter(CriteriaBuilder builder, Root<Car> root) {
		Predicate rst = null;
		if (getFuelType() != null && !getFuelType().equals(EMPTY_FIELD_STR)) {
			Join<Car, Engine> engine = root.join("engine");
			rst = builder.equal(engine.get("type"), FuelType.valueOf(getFuelType()));
		}
		return rst;
	}
	
	private Predicate shiftGearFilter(CriteriaBuilder builder, Root<Car> root) {
		Predicate rst = null;
		if (getShiftGear() != null && !getShiftGear().equals(EMPTY_FIELD_STR)) {
			rst = builder.equal(root.get("shiftGear"), ShiftGear.valueOf(getShiftGear()));
		}
		return rst;
	}
	
	private Predicate volumeFilter(CriteriaBuilder builder, Root<Car> root) {
		Predicate rst = null;
		if (getMinVolume() != EMPTY_FIELD_NUM && getMaxVolume() != EMPTY_FIELD_NUM) {
        	Join<Car, Engine> engine = root.join("engine");
        	rst = builder.between(engine.get("volume"), getMinVolume(), getMaxVolume());
        } else if (getMinVolume() != EMPTY_FIELD_NUM) {
        	Join<Car, Engine> engine = root.join("engine");
        	rst = builder.ge(engine.get("volume"), getMinVolume());
        } else if (getMaxVolume() != EMPTY_FIELD_NUM) {
        	Join<Car, Engine> engine = root.join("engine");
        	rst = builder.le(engine.get("volume"), getMaxVolume());
        }
		return rst;
	}
	
	private Predicate priceFilter(CriteriaBuilder builder, Root<Car> root) {
		Predicate rst = null;
		if (getMinPrice() != EMPTY_FIELD_NUM && getMaxPrice() != EMPTY_FIELD_NUM) {
			rst = builder.between(root.get("price"), getMinPrice(), getMaxPrice());
        } else if (getMinPrice() != EMPTY_FIELD_NUM) {
        	rst = builder.ge(root.get("price"), getMinPrice());
        } else if (getMaxPrice() != EMPTY_FIELD_NUM) {
        	rst = builder.le(root.get("price"), getMaxPrice());
        }
		return rst;
	}
	
	private Predicate yearFilter(CriteriaBuilder builder, Root<Car> root) {
		Predicate rst = null;
		if (getMinYear() != EMPTY_FIELD_NUM && getMaxYear() != EMPTY_FIELD_NUM) {
			rst = builder.between(root.get("year"), getMinYear(), getMaxYear());
        } else if (getMinYear() != EMPTY_FIELD_NUM) {
        	rst = builder.ge(root.get("year"), getMinYear());
        } else if (getMaxYear() != EMPTY_FIELD_NUM) {
        	rst = builder.le(root.get("year"), getMaxYear());
        }
		return rst;
	}
	
	
	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public int getMaxResultSize() {
		return maxResultSize;
	}


	public void setMaxResultSize(int maxResultSize) {
		this.maxResultSize = maxResultSize;
	}


	public String getMark() {
		return mark;
	}


	public void setMark(String mark) {
		this.mark = mark;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getFuelType() {
		return fuelType;
	}


	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}


	public double getMinVolume() {
		return minVolume;
	}


	public void setMinVolume(double minVolume) {
		this.minVolume = minVolume;
	}


	public double getMaxVolume() {
		return maxVolume;
	}


	public void setMaxVolume(double maxVolume) {
		this.maxVolume = maxVolume;
	}


	public String getShiftGear() {
		return shiftGear;
	}


	public void setShiftGear(String shiftGear) {
		this.shiftGear = shiftGear;
	}


	public int getMinPrice() {
		return minPrice;
	}


	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}


	public int getMaxPrice() {
		return maxPrice;
	}


	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}


	public int getMinYear() {
		return minYear;
	}


	public void setMinYear(int minYear) {
		this.minYear = minYear;
	}


	public int getMaxYear() {
		return maxYear;
	}


	public void setMaxYear(int maxYear) {
		this.maxYear = maxYear;
	}


	public static String getEmptyFieldStr() {
		return EMPTY_FIELD_STR;
	}


	public static int getEmptyFieldNum() {
		return EMPTY_FIELD_NUM;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, filters, fuelType, mark, maxPrice, maxResultSize, maxVolume, maxYear, minPrice,
				minVolume, minYear, model, page, shiftGear);
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
		CarFilter other = (CarFilter) obj;
		return active == other.active && Objects.equals(filters, other.filters)
				&& Objects.equals(fuelType, other.fuelType) && Objects.equals(mark, other.mark)
				&& maxPrice == other.maxPrice && maxResultSize == other.maxResultSize
				&& Double.doubleToLongBits(maxVolume) == Double.doubleToLongBits(other.maxVolume)
				&& maxYear == other.maxYear && minPrice == other.minPrice
				&& Double.doubleToLongBits(minVolume) == Double.doubleToLongBits(other.minVolume)
				&& minYear == other.minYear && Objects.equals(model, other.model) && page == other.page
				&& Objects.equals(shiftGear, other.shiftGear);
	}

	@Override
	public String toString() {
		return String.format(
				"CarFilter [filters=%s, page=%s, maxResultSize=%s, mark=%s, model=%s, fuelType=%s, minVolume=%s, maxVolume=%s, shiftGear=%s, minPrice=%s, maxPrice=%s, minYear=%s, maxYear=%s, active=%s]",
				filters, page, maxResultSize, mark, model, fuelType, minVolume, maxVolume, shiftGear, minPrice,
				maxPrice, minYear, maxYear, active);
	}
	
	
	
}
