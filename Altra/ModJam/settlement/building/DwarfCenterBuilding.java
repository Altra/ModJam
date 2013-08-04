package Altra.ModJam.settlement.building;

import java.util.Random;

import net.minecraft.block.Block;


public class DwarfCenterBuilding extends Building{
	
	private Random rand = new Random();

	public DwarfCenterBuilding(int id) {
		super(id);
		this.lengthX = 9;
		this.lengthZ = 9;
		this.noLevels = 1;
	}
	
	private int s = Block.stoneBrick.blockID;

	private final int[] L1Z1 = {s, s, s, s, s, s, s, s, s};
	private final int[] L1Z2 = {s, s, s, s, s, s, s, s, s};
	private final int[] L1Z3 = {s, s, s, s, s, s, s, s, s};
	private final int[] L1Z4 = {s, s, s, s, s, s, s, s, s};
	private final int[] L1Z5 = {s, s, s, s, s, s, s, s, s};
	private final int[] L1Z6 = {s, s, s, s, s, s, s, s, s};
	private final int[] L1Z7 = {s, s, s, s, s, s, s, s, s};
	private final int[] L1Z8 = {s, s, s, s, s, s, s, s, s};
	private final int[] L1Z9 = {s, s, s, s, s, s, s, s, s};
	private final int[][] level1 = {L1Z1, L1Z2, L1Z3, L1Z4, L1Z5, L1Z6, L1Z7, L1Z8, L1Z9};
	
	private final int[] L2Z1 = {s, s, s, s, s, s, s, s, s};
	private final int[] L2Z2 = {s, s, s, s, s, s, s, s, s};
	private final int[] L2Z3 = {s, s, s, s, s, s, s, s, s};
	private final int[] L2Z4 = {s, s, s, s, s, s, s, s, s};
	private final int[] L2Z5 = {s, s, s, s, s, s, s, s, s};
	private final int[] L2Z6 = {s, s, s, s, s, s, s, s, s};
	private final int[] L2Z7 = {s, s, s, s, s, s, s, s, s};
	private final int[] L2Z8 = {s, s, s, s, s, s, s, s, s};
	private final int[] L2Z9 = {s, s, s, s, s, s, s, s, s};
	private final int[][] level2 = {L2Z1, L2Z2, L2Z3, L2Z4, L2Z5, L2Z6, L2Z7, L2Z8, L2Z9};

	@Override
	public int getBlockIdFor(int x, int level, int z) {
		int[] Z = level1[z];
		return Z[x];
	}
	
}
