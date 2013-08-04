package Altra.ModJam.settlement.dwarven;

import java.util.ArrayList;
import java.util.List;

import Altra.ModJam.settlement.IBuilding;

public class CenterBuilding implements IBuilding{
	
	private final int[] L1Z1 = {57, 57};
	private final int[] L1Z2 = {57, 57};
	private final int[][] level1 = {L1Z1, L1Z2};
	
	@Override
	public int noLevels() {
		return 1;
	}

	@Override
	public int blockIdFor(int x, int z, int level) {
		int[] Z = level1[z];
		return Z[x];
	}

	@Override
	public String buildingName() {
		return "Test";
	}

	@Override
	public int lengthX() {
		return level1.length;
	}

	@Override
	public int lengthZ() {
		return L1Z1.length;
	}

}
