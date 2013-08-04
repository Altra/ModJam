package Altra.ModJam.settlement.building;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class Worker{

	EntityCreature entity;

	public int targetX;
	public int targetY;
	public int targetZ;
	
	public Boolean workingState;

	Worker(EntityCreature e){
		this.entity = e;
	}

	public void setNavigator(int x, int y, int z){
		this.workingState = false;
		if (this.entity.getDistanceSq(x, y, z) > 256.0D){
			Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.entity, 14, 3, this.entity.worldObj.getWorldVec3Pool().getVecFromPool(x, y, z));

			if (vec3 != null){
				this.entity.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, 0.8D);
			}
		} else{
			this.entity.getNavigator().tryMoveToXYZ(x, y, z, 0.7D);
		}
	}

	public int getDistanceSquared(int x, int y, int z){
		return (int) this.entity.getDistanceSq(x, y, z);
	}
	
	public void setWorkingState(Boolean state){
		this.workingState = state;
		if(this.workingState)this.entity.getNavigator().clearPathEntity();
	}

}
