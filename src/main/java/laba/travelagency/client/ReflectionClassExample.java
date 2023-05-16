package laba.travelagency.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;


public class ReflectionClassExample {
	
	
	public static <T> void getClassDetails(T t) {
		
		Class<?> bankClass = t.getClass();
		
		System.out.println("FIELDS:");
		Field[] fields = bankClass.getDeclaredFields();
		for(Field field: fields)
		{
			System.out.println(Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() + " " + field.getName());
		}

		System.out.println("\nCONSTRUCTORS:");
		Constructor<?>[] constructors = bankClass.getDeclaredConstructors();
		for(Constructor<?> constructor: constructors)
		{
			System.out.println(Modifier.toString(constructor.getModifiers()) + " " + constructor.getName() + " (numParameters: " + 
					constructor.getParameterCount() + ")");
			System.out.println("Parameters:");
			Parameter[] parameters = constructor.getParameters();
			for(Parameter parameter: parameters)
			{
				System.out.println(" -- " + parameter.getType().getSimpleName() + " " + parameter.getName());
			}
		}
		
		System.out.println("\nMETHODS:");
		Method[] methods = bankClass.getDeclaredMethods();
		for(Method method: methods)
		{
			System.out.println(Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getSimpleName() + " " + method.getName()
			+ " (numParameters: " + method.getParameterCount() + ")");
			Parameter[] parameters = method.getParameters();
			for(Parameter parameter: parameters)
			{
				System.out.println(" -- " + parameter.getType().getSimpleName() + " " + parameter.getName());
			}
		}

	}
	
	
}
