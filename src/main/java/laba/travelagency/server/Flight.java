package laba.travelagency.server;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import laba.travelagency.exceptions.InvalidStateException;

/**
 * @author sheetal
 *
 */

public final class Flight {
	
	private final String originAirport;
	private final String destinationAirport;
	private final String departureTimestamp;
	private final String arrivalTimestamp;
	private final String flightNumber;
	private final int noOfStops;
	private final double price;
	
	public Map<Seat, String> seatOccupancyMap = new HashMap<>();
	public LinkedList<String> queueForUpgradeToBusinessClass = new LinkedList<>();
	
	public Flight() {
		this.originAirport = "";
		this.destinationAirport = "";
		this.departureTimestamp  = LocalDate.now().toString();
		this.arrivalTimestamp = LocalDate.now().toString();
		this.flightNumber = "";
		this.noOfStops = 0;
		this.price = 0;
	}
	
	public Flight( String flightNumber, String originAirport, String destinationAirport, String departureTimestamp, 
			String arrivalTimestamp, int noOfStops, double price) {
		this.originAirport = originAirport;
		this.destinationAirport = destinationAirport;
		this.departureTimestamp = departureTimestamp;
		this.arrivalTimestamp = arrivalTimestamp;
		this.flightNumber = flightNumber;
		this.noOfStops = noOfStops;
		this.price = price;
		this.initSeatOccupancyMap();
		this.initQueueForUpgradeToBusinessClass();
	}
	
	private void initSeatOccupancyMap() {
		try {
			this.seatOccupancyMap = FileUtils.readLines(new File("./src/main/resources/laba/travelagency/testdata/seatAvailabilityData.csv"), "UTF-8")
					.stream()
					.map(line -> line.split(","))
					.filter(tokens -> tokens.length == 2)
					.collect(Collectors.toMap(
                            tokens -> new Seat(tokens[0].trim()),
                            tokens -> tokens[1].trim()
                    ));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initQueueForUpgradeToBusinessClass() {
		try {
			this.queueForUpgradeToBusinessClass = FileUtils.readLines(new File("./src/main/resources/laba/travelagency/testdata/queueForBusinessClassUpgradeData.csv"), "UTF-8")
					.stream().collect(Collectors.toCollection(LinkedList::new));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getOriginAirport() {
		return originAirport;
	}

	public String getDestinationAirport() {
		return destinationAirport;
	}

	public String getDepartureTimestamp() {
		return departureTimestamp;
	}

	public String getArrivalTimestamp() {
		return arrivalTimestamp;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public double getPrice() {
		return price;
	}

	public int getNoOfStops() {
		return noOfStops;
	}

	public static Set<String> getAirportCodes() {
		return Utils.createHashSetFromFileContents("./src/main/resources/laba/travelagency/testdata/airportCodes.txt");
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}
		if(!(o instanceof Flight))
		{
			return false;
		}
		
		Flight f = (Flight)o;
		return Objects.equals(originAirport, f.originAirport) &&
				Objects.equals(destinationAirport, f.destinationAirport) &&
				Objects.equals(departureTimestamp, f.departureTimestamp) &&
				Objects.equals(arrivalTimestamp, f.arrivalTimestamp) &&
				Objects.equals(flightNumber, f.flightNumber) &&
				Objects.equals(price, f.price) &&
				Objects.equals(noOfStops, f.noOfStops);
		
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(originAirport, destinationAirport, departureTimestamp, arrivalTimestamp, flightNumber, price, noOfStops);
    }
	
	// method to return the stringify version of the flight object
	@Override
	public final String toString() {
		return (
				getFlightNumber() + " from " +
				getOriginAirport() + " (" +
				getDepartureTimestamp() + ") to " +
				getDestinationAirport() + " (" +
				getArrivalTimestamp() + ") with " +
				getNoOfStops() + " stops, $ " +
				getPrice()
		);	
	}
	
	public void addSeatOccupancy(Seat seat, String customerEmail) throws InvalidStateException {
		if (seatOccupancyMap.containsKey(seat)) {
            throw new InvalidStateException(seat.toString() + " is occupied!");
        } else {
            seatOccupancyMap.put(seat, customerEmail);
        }
    }
	
//	public void removeSeatOccupancy(String customerEmail) {
//		
//		for(Map.Entry<Seat, String> entry: seatOccupancyMap.entrySet())
//		{
//			if(entry.getValue() == customerEmail)
//			{
//				Seat seat = entry.getKey();
//				seatOccupancyMap.remove(seat);
//				System.out.println("\n   | Customer(" + customerEmail + ") is unassigned from " + seat.toString() + " of flight " + this.flightNumber);
//			}
//		}
//	}
	
// above method has been restructured into the following code to demonstrate Stream API on Collections
	public void removeSeatOccupancy(String customerEmail) {
		seatOccupancyMap.entrySet().stream()
		.filter(entry -> entry.getValue().equals(customerEmail))
		.forEach(entry -> {
			Seat seat = entry.getKey();
			seatOccupancyMap.remove(seat);
			System.out.println("\n   | Customer(" + customerEmail + ") is unassigned from " + seat.toString() + " of flight " + this.flightNumber);
		});
	}
	
	public void addToQueueForUpgradeToBusinessClass(String customerEmail) {
		System.out.println("\n   | Customer(" + customerEmail + ") added to queue for upgrade to Business Class for flight " + this.flightNumber);
		queueForUpgradeToBusinessClass.add(customerEmail);
	}
	
	public void removeFromQueueForUpgradeToBusinessClass(String customerEmail) {
		System.out.println("\n   | Customer(" + customerEmail + ") removed from queue for upgrade to Business Class for flight " + this.flightNumber);
		queueForUpgradeToBusinessClass.remove(customerEmail);
	}
	
	public String getNextInQueueForUpgradeToBusinessClass() {
		return queueForUpgradeToBusinessClass.element();
	}

	
//	public static List<Flight> readFlightsFromCsv(File file) {
//	    List<Flight> flights = new ArrayList<>();
//
//	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//	        String line;
//
//	        while ((line = reader.readLine()) != null) {
//	            String[] values = line.split(",");
//
//	            String flightNumber = values[0];
//	            String origin = values[1];
//	            String destination = values[2];
//	            String departureTime = values[3];
//	            String arrivalTime =values[4];
//	            int noOfStops = Integer.parseInt(values[5]);
//	            double price = Double.parseDouble(values[6]);
//
//	            Flight flight = new Flight(flightNumber, origin, destination, departureTime, arrivalTime, noOfStops, price);
//	            flights.add(flight);
//	        }
//
//	    } catch (IOException e) {
//	    	System.out.println("Error while fetching Flight List: " + e.getMessage());
//	        logger.fatal("Error while fetching Flight List: {}", e.getMessage());
//	        System.exit(1);
//	    }
//
//	    return flights;
//	}

}
