package meschaefer.fighter.ranking.data.jpa.converter;

import jakarta.persistence.AttributeConverter;
import meschaefer.fighter.ranking.data.jpa.model.WeightClass;

public class WeightClassConverter implements AttributeConverter<WeightClass, String> {

	@Override
	public String convertToDatabaseColumn(WeightClass method) {
		return method == null ? null : method.name();
	}

	@Override
	public WeightClass convertToEntityAttribute(String method) {
		return WeightClass.valueOf(method);
	}

}