package Altra.ModJam.settlement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
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
    	//this.removeDeadAndOldAgressors();

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
     * one (i.e. the one protecting the lowest number of settlementrs) of them is chosen, else the nearest one regardless
     * of restriction.
     */
    public SettlementDoorInfo findNearestDoorUnrestricted(int par1, int par2, int par3)
    {
        SettlementDoorInfo settlementdoorinfo = null;
        int l = Integer.MAX_VALUE;
        Iterator iterator = this.settlementDoorInfoList.iterator();

        while (iterator.hasNext())
        {
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
        if (this.center.getDistanceSquared(par1, par2, par3) > (float)(this.settlementRadius * this.settlementRadius))
        {
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

    public void addSettlementDoorInfo(SettlementDoorInfo par1SettlementDoorInfo)
    {
        this.settlementDoorInfoList.add(par1SettlementDoorInfo);
        this.centerHelper.posX += par1SettlementDoorInfo.posX;
        this.centerHelper.posY += par1SettlementDoorInfo.posY;
        this.centerHelper.posZ += par1SettlementDoorInfo.posZ;
        this.updateSettlementRadiusAndCenter();
        this.lastAddDoorTimestamp = par1SettlementDoorInfo.lastActivityTimestamp;
    }

    /**
     * Returns true, if there is not a single settlement door left. Called by SettlementCollection
     */
    public boolean isAnnihilated()
    {
        return this.settlementDoorInfoList.isEmpty();
    }

    public void addOrRenewAgressor(EntityLivingBase par1EntityLivingBase)
    {
        Iterator iterator = this.settlementAgressors.iterator();
        SettlementAgressor settlementagressor;

        do
        {
            if (!iterator.hasNext())
            {
                this.settlementAgressors.add(new SettlementAgressor(this, par1EntityLivingBase, this.tickCounter));
                return;
            }

            settlementagressor = (SettlementAgressor)iterator.next();
        }
        while (settlementagressor.agressor != par1EntityLivingBase);

        settlementagressor.agressionTime = this.tickCounter;
    }

    public EntityLivingBase findNearestSettlementAggressor(EntityLivingBase par1EntityLivingBase)
    {
        double d0 = Double.MAX_VALUE;
        SettlementAgressor settlementagressor = null;

        for (int i = 0; i < this.settlementAgressors.size(); ++i)
        {
            SettlementAgressor settlementagressor1 = (SettlementAgressor)this.settlementAgressors.get(i);
            double d1 = settlementagressor1.agressor.getDistanceSqToEntity(par1EntityLivingBase);

            if (d1 <= d0)
            {
                settlementagressor = settlementagressor1;
                d0 = d1;
            }
        }

        return settlementagressor != null ? settlementagressor.agressor : null;
    }



}
