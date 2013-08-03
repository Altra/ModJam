package Altra.ModJam.settlement;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
    private List villageAgressors = new ArrayList();
    
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


}
