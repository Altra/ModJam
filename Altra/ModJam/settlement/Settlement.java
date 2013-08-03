package Altra.ModJam.settlement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.village.VillageAgressor;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.World;
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
        boolean flag = false;
        boolean flag1 = this.worldObj.rand.nextInt(50) == 0;
        Iterator iterator = this.settlementDoorInfoList.iterator();
        while (iterator.hasNext()){
        	SettlementDoorInfo doorinfo = (SettlementDoorInfo)iterator.next();

            if (flag1)
            {
                villagedoorinfo.resetDoorOpeningRestrictionCounter();
            }

            if (!this.isBlockDoor(villagedoorinfo.posX, villagedoorinfo.posY, villagedoorinfo.posZ) || Math.abs(this.tickCounter - villagedoorinfo.lastActivityTimestamp) > 1200)
            {
                this.centerHelper.posX -= villagedoorinfo.posX;
                this.centerHelper.posY -= villagedoorinfo.posY;
                this.centerHelper.posZ -= villagedoorinfo.posZ;
                flag = true;
                villagedoorinfo.isDetachedFromVillageFlag = true;
                iterator.remove();
            }
        }

        if (flag)
        {
            this.updateVillageRadiusAndCenter();
        }
    }

    private boolean isBlockDoor(int par1, int par2, int par3)
    {
        int l = this.worldObj.getBlockId(par1, par2, par3);
        return l <= 0 ? false : l == Block.doorWood.blockID;
    }

    private void updateRadius()
    {
        int i = this.villageDoorInfoList.size();

        if (i == 0)
        {
            this.center.set(0, 0, 0);
            this.villageRadius = 0;
        }
        else
        {
            this.center.set(this.centerHelper.posX / i, this.centerHelper.posY / i, this.centerHelper.posZ / i);
            int j = 0;
            VillageDoorInfo villagedoorinfo;

            for (Iterator iterator = this.villageDoorInfoList.iterator(); iterator.hasNext(); j = Math.max(villagedoorinfo.getDistanceSquared(this.center.posX, this.center.posY, this.center.posZ), j))
            {
                villagedoorinfo = (VillageDoorInfo)iterator.next();
            }

            this.villageRadius = Math.max(32, (int)Math.sqrt((double)j) + 1);
        }
    }

    
}
