package Altra.ModJam.settlement;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class Settlement {
	private World worldObj;

	/** list of SettlementDoorInfo objects */
	private final List settlementDoorInfoList = new ArrayList();
	/** Settlement Centre */
	private final ChunkCoordinates center = new ChunkCoordinates(0, 0, 0);
	private int settlementRadius;
	private int lastAddDoorTimestamp;
	private int tickCounter;
    private int population;
    
    private int noBreedTicks;
    
    /** List of player reputations with this settlement*/
    private TreeMap playerReputation = new TreeMap();
    private List villageAgressors = new ArrayList();
    
    
    


}
