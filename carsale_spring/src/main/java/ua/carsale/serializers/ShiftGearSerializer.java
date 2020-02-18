package ua.carsale.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ua.carsale.domain.ShiftGear;


public class ShiftGearSerializer extends StdSerializer<ShiftGear> {

	private static final long serialVersionUID = 1L;
	
	public ShiftGearSerializer() {
		super(ShiftGear.class);
	}

	protected ShiftGearSerializer(Class<ShiftGear> t) {
		super(t);
	}

	@Override
	public void serialize(ShiftGear value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
		gen.writeFieldName("name");
		gen.writeString(value.name());
		gen.writeFieldName("value");
		gen.writeString(value.getValue());
		gen.writeEndObject();
	}
}