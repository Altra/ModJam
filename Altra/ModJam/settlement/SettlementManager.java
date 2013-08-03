package Altra.ModJam.settlement;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class SettlementManager extends WorldSavedData{

	private World worldObj;

	public SettlementManager(String mapName){
		super(mapName);
	}

	public SettlementManager(World world){
		super("Settlement");
		this.worldObj = world;
		this.markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}

}
