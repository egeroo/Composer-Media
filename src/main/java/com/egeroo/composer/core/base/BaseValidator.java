package com.egeroo.composer.core.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseValidator {
	public Set<String> getSupportedParameters(Class<?> objectClass) {
		List<String> supportedParameters = new ArrayList<>();
		Field[] fields = objectClass.getDeclaredFields();
		
		for(Field field : fields) {
			supportedParameters.add(field.getName());
		}
		
		return new HashSet<>(supportedParameters);
	}
	
	public Set<String> getSupportedParameters(List<String> supportedParameters) {
		return new HashSet<>(supportedParameters);
	}
}
