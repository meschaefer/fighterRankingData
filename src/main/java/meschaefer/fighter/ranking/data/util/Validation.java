package meschaefer.fighter.ranking.data.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class Validation {

	private Validation() {
		throw new IllegalStateException("Don't instantiate me!");
	}

	public static String combineErrs(String[] errs) {
		String combined = "";
		if (!ObjectUtils.isEmpty(errs)) {
			combined = Stream.of(errs)
					.filter(StringUtils::hasText)
					.collect(Collectors.joining(", "));
		}
		return combined;
	}

	public static String makeErrMsg(Object field, String fieldName) {
		String errMsg = "";
		if (field instanceof String stringField && !StringUtils.hasText(stringField)) {
			errMsg = fieldName + " cannot be blank";
		} else if (field == null) {
			errMsg = fieldName + " cannot be null";
		}
		return errMsg;
	}

}
