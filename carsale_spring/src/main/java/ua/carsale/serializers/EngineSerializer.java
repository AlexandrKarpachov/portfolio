package ua.carsale.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ua.carsale.domain.FuelType;

public class EngineSerializer extends StdSerializer<FuelType> {
	
	private static final long serialVersionUID = 1L;

	public EngineSerializer() {
		super(FuelType.class);
	}

	
	protected EngineSerializer(Class<FuelType> t) {
		super(t);
	}

	@Override
	public void serialize(FuelType value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
		gen.writeFieldName("name");
		gen.writeString(value.name());
		gen.writeFieldName("value");
		gen.writeString(value.getValue());
		gen.writeEndObject();
	}
}