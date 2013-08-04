package Altra.ModJam.settlement;

public interface IBuilding {
	
	/**
	 * @return the number of Y levels the building has
	 */
	int noLevels();
	
	/**
	 * Used for the construction of the building
	 * @return the blockID for the give coords
	 */
	int blockIdFor(int x, int z, int level);
	
	/**
	 * @return Its name;
	 */
	String buildingName();
	
	/**
	 * The distance in blocks from one side to the other along the X-axis
	 * @return The integer of the no. of blocks
	 */
	int lengthX();
	
	/**
	 * The distance in blocks from one side to the other along the Z-axis
	 * @return The integer of the no. of blocks
	 */
	int lengthZ();

}
