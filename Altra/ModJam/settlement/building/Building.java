package Altra.ModJam.settlement.building;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;

public class Building {

	public static final Building[] buildingList = new Building[4096];

	public static final Building dwarfCenterBuilding = (new DwarfCenterBuilding(1)).setBuildingName("Test");


	public final int buildingID;

	public int lengthX;
	public int lengthZ;

	/**
	 * Y-axis length
	 */
	public int noLevels;

	public String name;

	public Building(int id){

		if (buildingList[id] != null)
		{
			throw new IllegalArgumentException("ID " + id + " is in use by " + buildingList[id] + ", failed to add " + this);
		}else{
			buildingList[id] = this;
			this.buildingID = id;
			this.lengthX = 1;
			this.lengthZ = 1;
			this.noLevels = 1;
		}
	}

	/**
	 * @return the number of Y levels the building has
	 */
	int noLevels() {
		return 1;
	}

	/**
	 * Used for the construction of the building
	 * @return the blockID for the give coords
	 */
	int getBlockIdFor(int x, int z, int level) {
		return 0;
	}

	/**
	 * @return The Buildings name
	 */
	String getBuildingName() {
		return name;
	}

	Building setBuildingName(String string){
		name = string;
		return this; 
	}

	/**
	 * The distance in blocks from one side to the other along the X-axis
	 * @return The integer of the no. of blocks
	 */
	int getLengthX() {
		return lengthX;
	}

	/**
	 * The distance in blocks from one side to the other along the Z-axis
	 * @return The integer of the no. of blocks
	 */
	int getLengthZ() {
		return lengthZ;
	}

}
