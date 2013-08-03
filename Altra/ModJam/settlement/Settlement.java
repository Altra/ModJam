package Altra.ModJam.settlement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.World;
import Altra.ModJam.MJMod;
import Altra.ModJam.entity.EntityDwarf;

public class Settlement {
	private World worldObj;

	/** list of SettlementDoorInfo objects */
	private final List settlementDoorInfoList = new ArrayList();
	/** Settlement Centre */
	private final ChunkCoordinates center = new ChunkCoordinates(0, 0, 0);
	private int settlementRadius;
	private String settlementType;
	private String populationEntity;
	private int lastAddDoorTimestamp;
	private int tickCounter;
	private int population;

	private int noBreedTicks;

	/** List of player reputations with this settlement*/
	private TreeMap playerReputation = new TreeMap();
	private List settlementAgressors = new ArrayList();

	public Settlement(World world){
		this.worldObj = world;
	}

	public void tick(int tick){
		this.tickCounter = tick;
		//this.removeDeadAndOutOfRangeDoors();
		this.removeDeadAndOldAgressors();

		if (tick % 20 == 0){
			this.updatePopulation();
		}

	}

	private void updatePopulation(){
		List list = null;
		if(this.settlementType=="dwarven"){
			list = this.worldObj.getEntitiesWithinAABB(EntityDwarf.class, AxisAlignedBB.getAABBPool().getAABB((double)(this.center.posX - this.settlementRadius), (double)(this.center.posY - 30), (double)(this.center.posZ - this.settlementRadius), (double)(this.center.posX + this.settlementRadius), (double)(this.center.posY + 30), (double)(this.center.posZ + this.settlementRadius)));
		}

		if(list!=null)this.population = list.size();

		if (this.population == 0){
			this.playerReputation.clear();
		}
	}

	public ChunkCoordinates getCenter(){
		return this.center;
	}

	public int getSettlementRadius(){
		return this.settlementRadius;
	}

	public String getSettlementType(){
		return this.settlementType;
	}

	public String getPopulationName(){
		return this.populationEntity;
	}

	public int getNumSettlementDoors(){
		return this.settlementDoorInfoList.size();
	}

	public int getTicksSinceLastDoorAdding(){
		return this.tickCounter - this.lastAddDoorTimestamp;
	}

	public int getPopulation(){
		return this.population;
	}

	public boolean isInRange(int par1, int par2, int par3){
		return this.center.getDistanceSquared(par1, par2, par3) < (float)(this.settlementRadius * this.settlementRadius);
	}

	public List getSettlementDoorInfoList(){
		return this.settlementDoorInfoList;
	}

	public SettlementDoorInfo findNearestDoor(int par1, int par2, int par3)
	{
		SettlementDoorInfo settlementdoorinfo = null;
		int l = Integer.MAX_VALUE;
		Iterator iterator = this.settlementDoorInfoList.iterator();

		while (iterator.hasNext())
		{
			SettlementDoorInfo settlementdoorinfo1 = (SettlementDoorInfo)iterator.next();
			int i1 = settlementdoorinfo1.getDistanceSquared(par1, par2, par3);

			if (i1 < l)
			{
				settlementdoorinfo = settlementdoorinfo1;
				l = i1;
			}
		}
		return settlementdoorinfo;
	}

	/**
	 * Find a door suitable for shelter. If there are more doors in a distance of 16 blocks, then the least restricted
	 * one (i.e. the one protecting the lowest number of settlers) of them is chosen, else the nearest one regardless
	 * of restriction.
	 */
	public SettlementDoorInfo findNearestDoorUnrestricted(int par1, int par2, int par3){
		SettlementDoorInfo settlementdoorinfo = null;
		int l = Integer.MAX_VALUE;
		Iterator iterator = this.settlementDoorInfoList.iterator();

		while (iterator.hasNext()){
			SettlementDoorInfo settlementdoorinfo1 = (SettlementDoorInfo)iterator.next();
			int i1 = settlementdoorinfo1.getDistanceSquared(par1, par2, par3);

			if (i1 > 256)
			{
				i1 *= 1000;
			}
			else
			{
				i1 = settlementdoorinfo1.getDoorOpeningRestrictionCounter();
			}

			if (i1 < l)
			{
				settlementdoorinfo = settlementdoorinfo1;
				l = i1;
			}
		}
		return settlementdoorinfo;
	}

	public SettlementDoorInfo getSettlementDoorAt(int par1, int par2, int par3)
	{
		if (this.center.getDistanceSquared(par1, par2, par3) > (float)(this.settlementRadius * this.settlementRadius)){
			return null;
		}
		else
		{
			Iterator iterator = this.settlementDoorInfoList.iterator();
			SettlementDoorInfo settlementdoorinfo;
			do
			{
				if (!iterator.hasNext())
				{
					return null;
				}

				settlementdoorinfo = (SettlementDoorInfo)iterator.next();
			}
			while (settlementdoorinfo.posX != par1 || settlementdoorinfo.posZ != par3 || Math.abs(settlementdoorinfo.posY - par2) > 1);

			return settlementdoorinfo;
		}
	}

	public void addSettlementDoorInfo(SettlementDoorInfo doorInfo){
		this.settlementDoorInfoList.add(doorInfo);
		this.updateRadius();
		this.lastAddDoorTimestamp = doorInfo.lastActivityTimestamp;
	}

	public boolean isAnnihilated(){
		return this.settlementDoorInfoList.isEmpty();
	}

	public void addOrRenewAgressor(EntityLivingBase entity){
		Iterator iterator = this.settlementAgressors.iterator();
		SettlementAgressor agressor;
		do{
			if (!iterator.hasNext()){
				this.settlementAgressors.add(new SettlementAgressor(this, entity, this.tickCounter));
				return;
			}
			agressor = (SettlementAgressor)iterator.next();
		}
		while (agressor.agressor != entity);
		agressor.agressionTime = this.tickCounter;
	}

	private void removeDeadAndOldAgressors(){
		Iterator iterator = this.settlementAgressors.iterator();
		while (iterator.hasNext()){
			SettlementAgressor agressor = (SettlementAgressor)iterator.next();
			if (!agressor.agressor.isEntityAlive() || Math.abs(this.tickCounter - agressor.agressionTime) > 400){
				iterator.remove();
			}
		}
	}

	private void removeDeadAndOutOfRangeDoors(){
		boolean shouldReset = this.worldObj.rand.nextInt(50) == 0;
		Iterator iterator = this.settlementDoorInfoList.iterator();
		while (iterator.hasNext()){
			SettlementDoorInfo doorinfo = (SettlementDoorInfo)iterator.next();
			if (shouldReset){
				doorinfo.resetDoorOpeningRestrictionCounter();
			}
			if(!this.isBlockDoor(doorinfo.posX, doorinfo.posY, doorinfo.posZ) || Math.abs(this.tickCounter - doorinfo.lastActivityTimestamp) > 1200){
				doorinfo.isDetachedFromVillageFlag = true;
				iterator.remove();
				this.updateRadius();
			}
		}
	}

	private boolean isBlockDoor(int x, int y, int z){
		int id = this.worldObj.getBlockId(x, y, z);
		if(id == MJMod.mineDoor.blockID){
			return true;
		}else return false;
	}

	private void updateRadius(){
		if (this.settlementDoorInfoList.size() == 0){
			this.settlementRadius = 0;
		} else {
			int j = 0;
			SettlementDoorInfo doorinfo;
			for (Iterator iterator = this.settlementDoorInfoList.iterator(); iterator.hasNext(); j = Math.max(doorinfo.getDistanceSquared(this.center.posX, this.center.posY, this.center.posZ), j)){
				doorinfo = (SettlementDoorInfo)iterator.next();
			}
			this.settlementRadius = Math.max(32, (int)Math.sqrt((double)j) + 1);
		}
	}

	public int getPlayerRep(String name){
		Integer rep = (Integer)this.playerReputation.get(name);
		return rep != null ? rep.intValue() : 0;
	}

	public int setReputationForPlayer(String name, int addRep){
		int current = this.getPlayerRep(name);
		int newRep = MathHelper.clamp_int(current + addRep, -50, 100);
		this.playerReputation.put(name, Integer.valueOf(newRep));
		return newRep;
	}

	public boolean isPlayerRepCritical(String name){
		return this.getPlayerRep(name) <= -30;
	}

	public void readSettlementDataFromNBT(NBTTagCompound par1NBTTagCompound){
		this.population = par1NBTTagCompound.getInteger("Pop");
		this.settlementRadius = par1NBTTagCompound.getInteger("Radius");
		this.lastAddDoorTimestamp = par1NBTTagCompound.getInteger("DoorTime");
		this.tickCounter = par1NBTTagCompound.getInteger("Tick");
		this.noBreedTicks = par1NBTTagCompound.getInteger("BTick");
		this.center.posX = par1NBTTagCompound.getInteger("X");
		this.center.posY = par1NBTTagCompound.getInteger("Y");
		this.center.posZ = par1NBTTagCompound.getInteger("Z");
		this.settlementType = par1NBTTagCompound.getString("Type");
		this.populationEntity = par1NBTTagCompound.getString("Entity");
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Doors");

		for (int i = 0; i < nbttaglist.tagCount(); ++i){
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			SettlementDoorInfo doorinfo = new SettlementDoorInfo(nbttagcompound1.getInteger("X"), nbttagcompound1.getInteger("Y"), nbttagcompound1.getInteger("Z"), nbttagcompound1.getInteger("IDX"), nbttagcompound1.getInteger("IDZ"), nbttagcompound1.getInteger("TS"));
			this.settlementDoorInfoList.add(doorinfo);
		}

		NBTTagList nbtTagList = par1NBTTagCompound.getTagList("Players");
		for (int z = 0; z < nbtTagList.tagCount(); ++z){
			NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbtTagList.tagAt(z);
			this.playerReputation.put(nbttagcompound2.getString("Name"), Integer.valueOf(nbttagcompound2.getInteger("Rep")));
		}
	}

	public void writeSettlementDataToNBT(NBTTagCompound NBTTagCompound){
		NBTTagCompound.setInteger("Pop", this.population);
		NBTTagCompound.setInteger("Radius", this.settlementRadius);
		NBTTagCompound.setInteger("DoorTime", this.lastAddDoorTimestamp);
		NBTTagCompound.setInteger("Tick", this.tickCounter);
		NBTTagCompound.setInteger("BTick", this.noBreedTicks);
		NBTTagCompound.setInteger("X", this.center.posX);
		NBTTagCompound.setInteger("Y", this.center.posY);
		NBTTagCompound.setInteger("Z", this.center.posZ);
		NBTTagCompound.setString("Type", this.settlementType);
		NBTTagCompound.setString("Entity", this.populationEntity);
		NBTTagList nbttaglist = new NBTTagList("Doors");
		Iterator iterator = this.settlementDoorInfoList.iterator();
		while (iterator.hasNext()){
			SettlementDoorInfo doorinfo = (SettlementDoorInfo)iterator.next();
			NBTTagCompound nbttagcompound1 = new NBTTagCompound("Door");
			nbttagcompound1.setInteger("X", doorinfo.posX);
			nbttagcompound1.setInteger("Y", doorinfo.posY);
			nbttagcompound1.setInteger("Z", doorinfo.posZ);
			nbttagcompound1.setInteger("IDX", doorinfo.insideDirectionX);
			nbttagcompound1.setInteger("IDZ", doorinfo.insideDirectionZ);
			nbttagcompound1.setInteger("TS", doorinfo.lastActivityTimestamp);
			nbttaglist.appendTag(nbttagcompound1);
		}

		NBTTagCompound.setTag("Doors", nbttaglist);
		NBTTagList nbttaglist1 = new NBTTagList("Players");
		Iterator iteratorP = this.playerReputation.keySet().iterator();

		while (iteratorP.hasNext()){
			String name = (String)iteratorP.next();
			NBTTagCompound nbttagcompound2 = new NBTTagCompound(name);
			nbttagcompound2.setString("Name", name);
			nbttagcompound2.setInteger("Rep", ((Integer)this.playerReputation.get(name)).intValue());
			nbttaglist1.appendTag(nbttagcompound2);
		}
		NBTTagCompound.setTag("Players", nbttaglist1);
	}

}

