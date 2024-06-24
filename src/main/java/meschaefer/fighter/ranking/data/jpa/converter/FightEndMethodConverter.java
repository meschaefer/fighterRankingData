package meschaefer.fighter.ranking.data.jpa.converter;

import jakarta.persistence.AttributeConverter;
import meschaefer.fighter.ranking.data.jpa.model.FightEndMethod;

public class FightEndMethodConverter implements AttributeConverter<FightEndMethod, String> {

	@Override
	public String convertToDatabaseColumn(FightEndMethod method) {
		return method == null ? null : method.name();
	}

	@Override
	public FightEndMethod convertToEntityAttribute(String method) {
		return FightEndMethod.valueOf(method);
	}

}