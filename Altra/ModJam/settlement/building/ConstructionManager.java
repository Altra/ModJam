package Altra.ModJam.settlement.building;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ConstructionManager {
	private World world;

	private Random rand = new Random();

	private  Building building;
	private final List workers = new ArrayList();

	private int tickCounter = 0;
	public boolean completed;

	public int centerX;
	public int centerY;
	public int centerZ;

	public int globalCurrentX;
	public int globalCurrentY;
	public int globalCurrentZ;
	
	public int currentLevel;

	public ConstructionManager(World worldobj, Building B, int x, int y, int z){
		this.world = worldobj;
		this.building = B;
		this.centerX = x;
		this.centerY = y;
		this.centerZ = z;
	}

	public void addWorkerEntity(EntityCreature entity){
		this.workers.add(new Worker(entity));
	}

	private void coordUpdate(){
		this.globalCurrentX = this.centerX + rand.nextInt(building.lengthX) - rand.nextInt(building.lengthX);
		this.globalCurrentY = this.centerY + rand.nextInt(building.noLevels) - rand.nextInt(building.noLevels);
		this.globalCurrentZ = this.centerZ + rand.nextInt(building.lengthZ) - rand.nextInt(building.lengthZ);
	}

	public void tick(){
		++this.tickCounter;
		Iterator iterator = this.workers.iterator();

		while (iterator.hasNext()){
			coordUpdate();
			Worker worker = (Worker)iterator.next();
			worker.setNavigator(this.globalCurrentX, this.globalCurrentY, this.globalCurrentZ);
		}

		int i=0;
		int q = 0;
		int workerCount;
		while(i<this.building.lengthX){
			while(q<this.building.lengthZ){
				int cid = this.world.getBlockId(i, q, this.currentLevel);
				int bid = this.building.getBlockIdFor(i, q, this.currentLevel);
				if(cid != bid){
					if(cid==0){
						if(isWorkerNearBy(i, q, this.currentLevel)){
							this.world.setBlock(i, q, this.currentLevel, bid);
							workerCount++;
						}
					}else if(isWorkerNearBy(i, q, this.currentLevel)){
						Worker worker = (Worker)this.workers.get(workerCount);
						this.world.destroyBlockInWorldPartially(worker.entity.entityId, i, q, this.currentLevel, 10);
				}
			}
			q++;
		}
		i++;
	}
}
	
	public boolean getBlockId(int x, int y, )

	private boolean isWorkerNearBy(int x, int y, int z){
		Iterator iterator = this.workers.iterator();
		while (iterator.hasNext()){
			Worker worker = (Worker)iterator.next();
			if(worker.getDistanceSquared(x, y, z)<10){
				return true;
			}
		}
		return false;
	}

}
