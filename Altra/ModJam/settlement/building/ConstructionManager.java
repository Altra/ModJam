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

	public int tickCounter = 0;
	private int navTick = 0;
	public boolean completed = false;

	public int centerX;
	public int centerY;
	public int centerZ;

	public int globalCurrentX;
	public int globalCurrentY;
	public int globalCurrentZ;

	public int level;
	public int workerCount;

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
		this.globalCurrentX = this.centerX + rand.nextInt(building.lengthX);
		this.globalCurrentY = this.centerY + rand.nextInt(building.noLevels);
		this.globalCurrentZ = this.centerZ + rand.nextInt(building.lengthZ);
	}

	public void tick(){
		if(this.world.isRemote)return;
		++this.tickCounter;
		Iterator iterator = this.workers.iterator();

		while (iterator.hasNext()){
			betterNav();
			Worker worker = (Worker)iterator.next();
			worker.setNavigator(this.globalCurrentX, this.globalCurrentY, this.globalCurrentZ);
		}

		workerCount = 0;
		build();
	}

	private void build(){
		for(this.level=1;level<=this.building.noLevels;level++){
			for(int i=0;i<=this.building.lengthX-1 && workerCount<this.workers.size()-1;i++){
				for(int q=0;q<=this.building.lengthZ-1 && workerCount<this.workers.size()-1;q++){
					int bid = this.building.getBlockIdFor(i,level,q);
					int X = i+this.centerX;
					int Y = (level-1)+this.centerY;
					int Z = q+this.centerZ;
					int cid = this.world.getBlockId(X,Y,Z);
					if(cid != bid){
						if(cid==0){
							if(this.tickCounter>1200 || isWorkerNearBy(X,Y,Z)){
								this.world.setBlock(X,Y,Z, bid);
								FMLLog.info("setingBlock");
								Worker worker = (Worker)this.workers.get(workerCount);
								worker.setWorkingState(true);
								workerCount++;
							}
						}else if(this.tickCounter>1200|| isWorkerNearBy(X,Y,Z)){
							Worker worker = (Worker)this.workers.get(workerCount);
							//this.world.destroyBlockInWorldPartially(worker.entity.entityId, X,Y,Z, 2);
							this.world.destroyBlock(X,Y,Z, true);
							worker.setWorkingState(true);
							workerCount++;
						}
					}
				}
			}
		}
	}

	private boolean isWorkerNearBy(int x, int y, int z){
		Iterator iterator = this.workers.iterator();
		while (iterator.hasNext()){
			Worker worker = (Worker)iterator.next();
			if(worker.getDistanceSquared(x, y, z)<5){
				FMLLog.info("worker near by!");
				return true;
			}else if(level>3){
				if(worker.getDistanceSquared(x, y, z)<15){
					FMLLog.info("worker near by!");
					return true;
				}
			}
		}
		return false;
	}

	private void betterNav(){
		int inc = this.building.lengthX + this.building.lengthZ;
		if(this.navTick<50+inc){
			this.globalCurrentX = this.centerX;
			this.globalCurrentY = this.centerY;
			this.globalCurrentZ = this.centerZ;
		}else if(this.navTick<100+inc){
			this.globalCurrentX = this.centerX + this.building.lengthX;
			this.globalCurrentY = this.centerY;
			this.globalCurrentZ = this.centerZ;
		}else if(this.navTick<150+inc){
			this.globalCurrentX = this.centerX + this.building.lengthX;
			this.globalCurrentY = this.centerY;
			this.globalCurrentZ = this.centerZ + this.building.lengthZ;
		}else if(this.navTick<200+inc){
			this.globalCurrentX = this.centerX;
			this.globalCurrentY = this.centerY;
			this.globalCurrentZ = this.centerZ + this.building.lengthZ;
		}else if(this.navTick<250+inc){
			this.globalCurrentX = this.centerX + this.building.lengthX/2;
			this.globalCurrentY = this.centerY + this.building.noLevels;
			this.globalCurrentZ = this.centerZ + this.building.lengthZ/2;
		}
		if(this.navTick>250+inc)this.navTick=0;
		else this.navTick++;
	}

}
