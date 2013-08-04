package Altra.ModJam.settlement.building;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class ConstructionManager {

	private  Building building;
	private final List workerEntitys = new ArrayList();
	
	private int tickCounter = 0;

	public ConstructionManager(Building B){
		this.building = B;
	}

	public void addWorkerEntity(EntityCreature entity){
		this.workerEntitys.add(entity);
	}

	public void tick(){
		++this.tickCounter;
		Iterator iterator = this.workerEntitys.iterator();

		while (iterator.hasNext()){
			EntityCreature worker = (EntityCreature)iterator.next();
			worker
		}

		//this.removeAnnihilatedSettlements();
		this.addNewDoorsToSettlement();

		if(this.tickCounter % 400 == 0){
			this.markDirty();
		}
	}

	private void setNavigator(EntityCreature worker, int x, int y, int z){
		if (worker.getDistanceSq(x, y, z) > 256.0D){
			Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(worker, 14, 3, worker.worldObj.getWorldVec3Pool().getVecFromPool(x, y, z));

			if (vec3 != null){
				worker.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, 1.0D);
			}
		} else{
			worker.getNavigator().tryMoveToXYZ(x, y, z, 1.0D);
		}
	}

}
