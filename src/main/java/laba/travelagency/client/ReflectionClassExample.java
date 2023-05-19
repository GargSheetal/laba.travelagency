package laba.travelagency.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import laba.travelagency.server.*;

public class ReflectionClassExample {
	
	
	public static void getClassDetails(String className) throws ClassNotFoundException {
		
		System.out.println(" *** Reflection Example *** ");
		Class<?> myClass = Class.forName(className);
		System.out.println("FIELDS:");
		Field[] fields = myClass.getDeclaredFields();
		for(Field field: fields)
		{
			System.out.println("  " + Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() + " " + field.getName());
		}

		System.out.println("\nCONSTRUCTORS:");
		Constructor<?>[] constructors = myClass.getDeclaredConstructors();
		for(Constructor<?> constructor: constructors)
		{
			System.out.println("  " + Modifier.toString(constructor.getModifiers()) + " " + constructor.getName() + " (numParameters: " + 
					constructor.getParameterCount() + ")");
			System.out.println("    Parameters:");
			Parameter[] parameters = constructor.getParameters();
			for(Parameter parameter: parameters)
			{
				System.out.println("     -- " + parameter.getType().getSimpleName() + " " + parameter.getName());
			}
		}
		
		System.out.println("\nMETHODS:");
		Method[] methods = myClass.getDeclaredMethods();
		for(Method method: methods)
		{
			System.out.println("  " + Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getSimpleName() + " " + method.getName()
			+ " (numParameters: " + method.getParameterCount() + ")");
			Parameter[] parameters = method.getParameters();
			for(Parameter parameter: parameters)
			{
				System.out.println("     -- " + parameter.getType().getSimpleName() + " " + parameter.getName());
			}
		}

	}
	
	
}
