package Altra.ModJam.settlement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import Altra.ModJam.MJMod;
import Altra.ModJam.blocks.BlockMineDoor;

public class SettlementManager extends WorldSavedData{
	private World worldObj;
	private final List newDoors = new ArrayList();
	private final List settlementList = new ArrayList();
	private int tickCounter;

	public SettlementManager(String mapName){
		super(mapName);
	}

	public SettlementManager(World world){
		super("Settlement");
		this.worldObj = world;
		this.markDirty();
	}

	public void tick(){
		++this.tickCounter;
		Iterator iterator = this.settlementList.iterator();

		while (iterator.hasNext()){
			Settlement s = (Settlement)iterator.next();
			s.tick(this.tickCounter);
		}

		this.removeAnnihilatedSettlements();
		this.addNewDoorsToSettlement();

		if(this.tickCounter % 400 == 0){
			this.markDirty();
		}
	}

	private void removeAnnihilatedSettlements(){
		Iterator iterator = this.settlementList.iterator();
		while (iterator.hasNext()){
			Settlement s = (Settlement)iterator.next();

			if (s.isAnnihilated()){
				iterator.remove();
				this.markDirty();
			}
		}
	}

	public List getSettlementList()
	{
		return this.settlementList;
	}

	public Settlement findNearestSettlement(int x, int y, int z, int par4){
		Settlement settlement = null;
		float minD = Float.MAX_VALUE;
		Iterator iterator = this.settlementList.iterator();
		while (iterator.hasNext()){
			Settlement s = (Settlement)iterator.next();
			float distance = s.getCenter().getDistanceSquared(x, y, z);
			if (distance < minD){
				float r = (float)(par4 + s.getSettlementRadius());
				if (distance <= r * r)
				{
					settlement = s;
					minD = distance;
				}
			}
		}
		return settlement;
	}

	private void addNewDoorsToSettlement(){
		int i = 0;
		while (i < this.newDoors.size()){
			SettlementDoorInfo doorinfo = (SettlementDoorInfo)this.newDoors.get(i);
			Iterator iterator = this.settlementList.iterator();

			while (true){
				if (iterator.hasNext()){
					Settlement settlement = (Settlement)iterator.next();
					int dist = (int)settlement.getCenter().getDistanceSquared(doorinfo.posX, doorinfo.posY, doorinfo.posZ);
					float k = 32f + settlement.getSettlementRadius();

					if (dist > k * k){
						continue;
					}
					settlement.addSettlementDoorInfo(doorinfo);
				}
				/**
				if (!flag){
					Settlement s1 = new Settlement(this.worldObj);
					s1.addSettlementDoorInfo(doorinfo);
					this.settlementList.add(s1);
					this.markDirty();
				}
				 **/
				++i;
				break;
			}
		}
		this.newDoors.clear();
	}

	private void addUnassignedMineDoorsToNewDoorsList(ChunkCoordinates coords){
		byte b0 = 16;
		byte b1 = 4;
		byte b2 = 16;
		for (int x = coords.posX - b0; x < coords.posX + b0; ++x){
			for (int y = coords.posY - b1; y < coords.posY + b1; ++y){
				for (int z = coords.posZ - b2; z < coords.posZ + b2; ++z){
					if (this.isMineDoorAt(x, y, z)){
						SettlementDoorInfo doorinfo = this.getSettlementDoorAt(x, y, z);

						if(doorinfo == null){
							this.addDoorToNewListIfAppropriate(x, y, z);
						} else {
							doorinfo.lastActivityTimestamp = this.tickCounter;
						}
					}
				}
			}
		}
	}

	private SettlementDoorInfo getSettlementDoorAt(int x, int y, int z){
		Iterator iterator = this.newDoors.iterator();
		SettlementDoorInfo doorinfo;
		do{
			if (!iterator.hasNext()){
				iterator = this.settlementList.iterator();
				SettlementDoorInfo workingDoorinfo;
				do{
					if (!iterator.hasNext()){
						return null;
					}
					Settlement settlement = (Settlement)iterator.next();
					workingDoorinfo = settlement.getSettlementDoorAt(x, y, z);
				}
				while (workingDoorinfo == null);
				return workingDoorinfo;
			}

			doorinfo = (SettlementDoorInfo)iterator.next();
		}
		while(doorinfo.posX != x || doorinfo.posZ != z || Math.abs(doorinfo.posY - y) > 1);
		return doorinfo;
	}

	private void addDoorToNewListIfAppropriate(int x, int y, int z){
		int or = ((BlockMineDoor)MJMod.mineDoor).getDoorOrientation(this.worldObj, x, y, z);
		int i1;
		int spacecount;
		if (or != 0 && or != 2){
			i1 = 0;
			for (spacecount = -5; spacecount < 0; ++spacecount){
				if (this.worldObj.canBlockSeeTheSky(x, y, z + spacecount)){
					--i1;
				}
			}
			for (spacecount = 1; spacecount <= 5; ++spacecount) {
				if (this.worldObj.canBlockSeeTheSky(x, y, z + spacecount))
				{
					++i1;
				}
			}
			if (i1 != 0){
				this.newDoors.add(new SettlementDoorInfo(x, y, z, 0, i1 > 0 ? -2 : 2, this.tickCounter));
			}
		} else {
			i1 = 0;
			for (spacecount = -5; spacecount < 0; ++spacecount){
				if (this.worldObj.canBlockSeeTheSky(x + spacecount, y, z))
				{
					--i1;
				}
			}
			for (spacecount = 1; spacecount <= 5; ++spacecount)
			{
				if (this.worldObj.canBlockSeeTheSky(x + spacecount, y, z))
				{
					++i1;
				}
			}

			if (i1 != 0)
			{
				this.newDoors.add(new SettlementDoorInfo(x, y, z, i1 > 0 ? -2 : 2, 0, this.tickCounter));
			}
		}
	}

	private boolean isMineDoorAt(int x, int y, int z){
		int id = this.worldObj.getBlockId(x, y, z);
		return id == MJMod.mineDoor.blockID;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		this.tickCounter = nbttagcompound.getInteger("Tick");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Settlement");

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			Settlement settlement = new Settlement();
			settlement.readSettlementDataFromNBT(nbttagcompound1);
			this.settlementList.add(settlement);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("Tick", this.tickCounter);
		NBTTagList nbttaglist = new NBTTagList("Settlements");
		Iterator iterator = this.settlementList.iterator();

		while (iterator.hasNext())
		{
			Settlement settlement = (Settlement)iterator.next();
			NBTTagCompound nbttagcompound1 = new NBTTagCompound("Settlement");
			settlement.writeSettlementDataToNBT(nbttagcompound1);
			nbttaglist.appendTag(nbttagcompound1);
		}

		nbttagcompound.setTag("Settlements", nbttaglist);
	}

}
