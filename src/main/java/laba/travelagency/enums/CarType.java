/**
 * 
 */
package laba.travelagency.enums;

import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author sheetal
 *
 */
public enum CarType {

	SEDAN("Sedan", 4),
	SUV("SUV", 5),
	MINIVAN("Minivan", 7),
	PICKUP_TRUCK("Pickup Truck", 5),
	CONVERTIBLE("Convertible", 4);
	
	private final String displayName;
	private final int numPassengers;
	
	private CarType(String displayName, int numPassengers) {
		this.displayName = displayName;
		this.numPassengers = numPassengers;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getNumPassengers() {
		return numPassengers;
	}
	
	public static CarType displayCarType(String displayName) {

			for(CarType cartype: CarType.values())
			{
				if(cartype.displayName.equals(displayName))
				{
					return cartype;
				}
			}
			throw new IllegalArgumentException("Invalid Car Type : " + displayName);
	}
	
}
