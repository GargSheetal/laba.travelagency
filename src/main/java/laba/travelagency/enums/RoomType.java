/**
 * 
 */
package laba.travelagency.enums;

/**
 * @author sheetal
 *
 */
public enum RoomType {

	SINGLE("Single", 1),
	DOUBLE("Double", 2),
	QUEEN("Queen", 2),
	KING("King", 4),
	SUITE("Suite", 4);
	
	private final String displayName;
	private final int capacity;
	
	private RoomType(String displayName, int capacity) {
		this.displayName = displayName;
		this.capacity = capacity;
	}

	public String getRoomName() {
		return displayName;
	}

	public int getCapacity() {
		return capacity;
	}
	
	public static RoomType getRoomTypeByDisplayName(String displayName) {
		for(RoomType roomType: RoomType.values()) 
		{
			if(roomType.displayName.equals(displayName))
			{
				return roomType;
			}
		}
		throw new IllegalArgumentException("Invalid Room Type : " + displayName);
	}
	
}
