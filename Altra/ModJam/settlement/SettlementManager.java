package Altra.ModJam.settlement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

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
		this.addNewDoorsToSettlementOrCreateSettlement();

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

	private void addNewDoorsToSettlementOrCreateSettlement(){
		int i = 0;
		while (i < this.newDoors.size()){
			SettlementDoorInfo doorinfo = (SettlementDoorInfo)this.newDoors.get(i);
			boolean flag = false;
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
					flag = true;
				}
				if (!flag){
					Settlement s1 = new Settlement(this.worldObj);
					s1.addSettlementDoorInfo(doorinfo);
					this.settlementList.add(s1);
					this.markDirty();
				}
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

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub

	}

}
