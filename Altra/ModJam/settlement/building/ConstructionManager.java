package Altra.ModJam.settlement.building;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.nbt.NBTTagCompound;
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
		FMLLog.info("Tick");
		Iterator iterator = this.workers.iterator();

		while (iterator.hasNext()){
			coordUpdate();
			Worker worker = (Worker)iterator.next();
			worker.setNavigator(this.globalCurrentX, this.globalCurrentY, this.globalCurrentZ);
		}

		int i=0;
		int q = 0;
		int workerCount = 0;
		while(i<=this.building.lengthX-1 && workerCount<this.workers.size()-1){
			while(q<=this.building.lengthZ-1 && workerCount<this.workers.size()-1){
				FMLLog.info("W" + workerCount + "| i " + i + ": q" + q);
				int cid = this.world.getBlockId(i, this.currentLevel, q);
				int bid = this.building.getBlockIdFor(i, this.currentLevel, q);
				int X = i+this.centerX;
				int Y = this.currentLevel+this.centerY;
				int z = q+this.centerZ;
				if(cid != bid){
					FMLLog.info("Not the same");
					if(cid==0){
						FMLLog.info("Air block");
						if(isWorkerNearBy(i, this.currentLevel, q)){
							this.world.setBlock(i, this.currentLevel, q, bid);
							FMLLog.info("setingBlock");
							Worker worker = (Worker)this.workers.get(workerCount);
							worker.setWorkingState(true);
							workerCount++;
						}
					}else if(isWorkerNearBy(i, this.currentLevel, q)){
						Worker worker = (Worker)this.workers.get(workerCount);
						this.world.destroyBlockInWorldPartially(worker.entity.entityId, i, this.currentLevel, q, 40);
						FMLLog.info("miningBlock");
						worker.setWorkingState(true);
						workerCount++;
					}
				}
				q++;
			}
			i++;
		}
	}

	private boolean isWorkerNearBy(int x, int y, int z){
		Iterator iterator = this.workers.iterator();
		while (iterator.hasNext()){
			Worker worker = (Worker)iterator.next();
			if(worker.getDistanceSquared(x, y, z)<20){
				FMLLog.info("worker near by!");
				return true;
			}
		}
		return false;
	}


	public void writeEntityToNBT(NBTTagCompound tag){

	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound){

	}

}
