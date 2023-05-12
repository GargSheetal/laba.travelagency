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
	
	private final String roomName;
	private final int capacity;
	
	private RoomType(String roomName, int capacity) {
		this.roomName = roomName;
		this.capacity = capacity;
	}

	public String getRoomName() {
		return roomName;
	}

	public int getCapacity() {
		return capacity;
	}
	
	public static RoomType displayRoomName(String roomName) {
		for(RoomType roomType: RoomType.values()) 
		{
			if(roomType.roomName.equals(roomName))
			{
				return roomType;
			}
		}
		throw new IllegalArgumentException("Invalid Room Type : " + roomName);
	}
	
}
