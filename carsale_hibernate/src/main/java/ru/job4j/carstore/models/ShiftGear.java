package ru.job4j.carstore.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ru.job4j.carstore.utils.ShiftGearSerializer;

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
