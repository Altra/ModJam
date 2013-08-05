package Altra.ModJam.settlement.building;

import java.util.Random;

import net.minecraft.block.Block;


public class DwarfCenterBuilding extends Building{
	
	private Random rand = new Random();

	public DwarfCenterBuilding(int id) {
		super(id);
		this.lengthX = 9;
		this.lengthZ = 9;
		this.noLevels = 5;
	}
	
	private int s = Block.stoneBrick.blockID;
	private int g = Block.glass.blockID;
	private int t = Block.torchWood.blockID;

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
	
	private final int[] L2Z1 = {s, g, s, 0, 0, 0, s, g, s};
	private final int[] L2Z2 = {s, 0, 0, 0, 0, 0, 0, 0, s};
	private final int[] L2Z3 = {s, 0, 0, 0, 0, 0, 0, 0, s};
	private final int[] L2Z4 = {s, 0, 0, 0, 0, 0, 0, 0, s};
	private final int[] L2Z5 = {s, 0, 0, 0, 0, 0, 0, 0, s};
	private final int[] L2Z6 = {s, 0, 0, s, 0, s, 0, 0, s};
	private final int[] L2Z7 = {s, 0, 0, 0, s, 0, 0, 0, s};
	private final int[] L2Z8 = {s, 0, 0, 0, 0, 0, 0, 0, s};
	private final int[] L2Z9 = {s, s, s, s, s, s, s, s, s};
	private final int[][] level2 = {L2Z1, L2Z2, L2Z3, L2Z4, L2Z5, L2Z6, L2Z7, L2Z8, L2Z9};
	
	private final int[] L3Z1 = {s, g, s, 0, 0, 0, s, g, s};
	private final int[] L3Z2 = {s, 0, 0, 0, 0, 0, 0, 0, s};
	private final int[] L3Z3 = {g, 0, 0, 0, 0, 0, 0, 0, g};
	private final int[] L3Z4 = {g, 0, 0, 0, 0, 0, 0, 0, g};
	private final int[] L3Z5 = {g, 0, 0, 0, 0, 0, 0, 0, g};
	private final int[] L3Z6 = {g, 0, 0, 0, 0, 0, 0, 0, g};
	private final int[] L3Z7 = {g, 0, 0, 0, 0, 0, 0, 0, g};
	private final int[] L3Z8 = {s, 0, 0, 0, 0, 0, 0, 0, s};
	private final int[] L3Z9 = {s, s, s, s, s, s, s, s, s};
	private final int[][] level3 = {L3Z1, L3Z2, L3Z3, L3Z4, L3Z5, L3Z6, L3Z7, L3Z8, L3Z9};
	
	private final int[] L4Z1 = {s, g, s, 0, 0, 0, s, g, s};
	private final int[] L4Z2 = {s, 0, t, 0, 0, 0, t, 0, s};
	private final int[] L4Z3 = {s, 0, 0, 0, 0, 0, 0, 0, s};
	private final int[] L4Z4 = {s, 0, 0, 0, 0, 0, 0, 0, s};
	private final int[] L4Z5 = {s, t, 0, 0, 0, 0, 0, t, s};
	private final int[] L4Z6 = {s, 0, 0, 0, 0, 0, 0, 0, s};
	private final int[] L4Z7 = {s, 0, 0, 0, 0, 0, 0, 0, s};
	private final int[] L4Z8 = {s, 0, t, 0, 0, 0, t, 0, s};
	private final int[] L4Z9 = {s, s, s, s, s, s, s, s, s};
	private final int[][] level4 = {L4Z1, L4Z2, L4Z3, L4Z4, L4Z5, L4Z6, L4Z7, L4Z8, L4Z9};

	private final int[] L5Z1 = {s, s, s, s, s, s, s, s, s};
	private final int[] L5Z2 = {s, s, s, s, s, s, s, s, s};
	private final int[] L5Z3 = {s, s, s, s, s, s, s, s, s};
	private final int[] L5Z4 = {s, s, s, s, s, s, s, s, s};
	private final int[] L5Z5 = {s, s, s, s, s, s, s, s, s};
	private final int[] L5Z6 = {s, s, s, s, s, s, s, s, s};
	private final int[] L5Z7 = {s, s, s, s, s, s, s, s, s};
	private final int[] L5Z8 = {s, s, s, s, s, s, s, s, s};
	private final int[] L5Z9 = {s, s, s, s, s, s, s, s, s};
	private final int[][] level5 = {L5Z1, L5Z2, L5Z3, L5Z4, L5Z5, L5Z6, L5Z7, L5Z8, L5Z9};
	
	@Override
	public int getBlockIdFor(int x, int level, int z) {
		if(level == 1){
		int[] Z = level1[z];
		return Z[x];
		} else if(level == 2){
			int[] Z = level2[z];
			return Z[x];
		} else if(level == 3){
			int[] Z = level3[z];
			return Z[x];
		}else if(level == 4){
			int[] Z = level4[z];
			return Z[x];
		} else if(level == 5){
			int[] Z = level5[z];
			return Z[x];
		}
		
		return 0;
	}
	
}
