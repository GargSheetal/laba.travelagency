package laba.travelagency.server;


import java.util.Set;

import laba.travelagency.enums.CarType;


/**
 * @author sheetal
 *
 */

public final class Car {

	private String location;
	private String pickupDate;
	private String dropOffDate;
	private double price;
	private CarType carType;

	
	public Car(String location, String pickupDate, String dropOffDate, double price, CarType carType) {
		this.location = location;
		this.pickupDate = pickupDate;
		this.dropOffDate = dropOffDate;
		this.price = price;
		this.carType = carType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}

	public String getDropOffDate() {
		return dropOffDate;
	}

	public void setDropOffDate(String dropOffDate) {
		this.dropOffDate = dropOffDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}
	
	public static Set<String> getLocations() {
		return Utils.createHashSetFromFileContents("./src/main/resources/laba/travelagency/testdata/locations.csv");
	}
	
	@Override
	public int hashCode() {
		
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + price);
        result = prime * result + ((carType == null) ? 0 : carType.hashCode());
        return result;
    }
	
	@Override
	public final String toString() {
		String s = (
				getCarType() + " (capacity " +
				getCarType().getNumPassengers() + " passengers), at " +
				getLocation() + " (" +
				getPickupDate() + " - " +
				getDropOffDate()  + ") for $" +
				getPrice() + " per day"
		);	
		return s;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof Hotel))
		{
			return false;
		}
		
		Car c = (Car) o;
		return this.carType.equals(c.carType) && this.price == c.price;
		
	}
	
//	public static List<Car> search(String location, String pickupDate, String dropOffDate) {
//		
//		List<Car> matchingCars = new ArrayList<>();
//		
//		for(Car car: readCarsFromCsv(new File("./src/main/resources/laba/travelagency/testdata/carsData.csv")))
//		{
//			if(
//					car.getLocation().equalsIgnoreCase(location) 
//					&& car.getPickupDate().equals(pickupDate) 
//					&& car.getDropOffDate().equals(dropOffDate)
//				)
//			{
//				matchingCars.add(car);
//			}	
//		}
//		
//		return matchingCars;
//			
//	}
	
//	public static List<Car> filter(List<Car> cars, double maxPrice, String carType) {
//		
//		List<Car> matchingCars = new ArrayList<>();
//		
//		for(Car car: cars)
//		{
//			if(
//				car.getPrice() <= maxPrice 
//				&& car.getCarType().equalsIgnoreCase(carType)
//				)
//			{
//				matchingCars.add(car);
//			}
//		
//		}
//		return matchingCars;
//	}
	
//	public static List<Car> readCarsFromCsv(File file) {
//	    List<Car> cars = new ArrayList<>();
//
//	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//	        String line;
//
//	        while ((line = reader.readLine()) != null) {
//	            String[] values = line.split(",");
//
//	            String location = values[0];
//	            String pickupDate = values[1];
//	            String dropOffDate = values[2];
//	            double price = Double.parseDouble(values[3]);
//	            String carType =values[4];
//
//	            Car car = new Car(location, pickupDate, dropOffDate, price, carType);
//	            cars.add(car);
//	        }
//
//	    } catch (IOException e) {
//	    	System.out.println("Error while fetching Car List: " + e.getMessage());
//	        logger.fatal("Error while fetching Car List: {}", e.getMessage());
//	        System.exit(1);;
//	    }
//
//	    return cars;
//	}
	
}
