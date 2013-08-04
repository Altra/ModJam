package Altra.ModJam.settlement.building;


public class DwarfCenterBuilding extends Building{
	
	public DwarfCenterBuilding(int id) {
		super(id);
        this.lengthX = 2;
        this.lengthZ = 2;
        this.noLevels = 1;
	}

	private final int[] L1Z1 = {57, 57};
	private final int[] L1Z2 = {57, 57};
	private final int[][] level1 = {L1Z1, L1Z2};

	@Override
	public int blockIdFor(int x, int z, int level) {
		int[] Z = level1[z];
		return Z[x];
	}
}
