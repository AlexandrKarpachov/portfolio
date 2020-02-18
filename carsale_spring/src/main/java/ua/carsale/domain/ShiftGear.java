package ua.carsale.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ua.carsale.serializers.ShiftGearSerializer;


@JsonSerialize(using = ShiftGearSerializer.class)
public enum ShiftGear {
	AUTO("Автомат"),
	MANUAL("Ручная"),
	ROBOTIC("Робот"),
	VARIATOR("Вариатор");
	
	String type;
	
	private ShiftGear(String type) {
		this.type = type;
	}
	
	public String getValue() {
		return type;
	}
}
