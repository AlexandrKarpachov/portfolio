package ru.job4j.carstore.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ru.job4j.carstore.utils.EngineSerializer;

@JsonSerialize(using = EngineSerializer.class)
public enum FuelType {
	DIESEL("Дизель"), 
	GAS("Газ"), 
	PETROL("Бензин"), 
	GAS_PETROL("Газ/Бензин"), 
	ELECTRIC("Электро"), 
	OTHER("Другое");

	private String type;

	private FuelType(String type) {
		this.type = type;
	}

	public String getValue() {
		return type;
	}
	
	public static FuelType fromString(String text) {
        for (FuelType b : FuelType.values()) {
            if (b.type.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
