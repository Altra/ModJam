package Altra.ModJam.settlement.building;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class Worker{

	EntityCreature entity;

	public int targetX;
	public int targetY;
	public int targetZ;

	Worker(EntityCreature e){
		this.entity = e;
	}

	public void setNavigator(int x, int y, int z){
		if (this.entity.getDistanceSq(x, y, z) > 256.0D){
			Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.entity, 14, 3, this.entity.worldObj.getWorldVec3Pool().getVecFromPool(x, y, z));

			if (vec3 != null){
				this.entity.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, 1.0D);
			}
		} else{
			this.entity.getNavigator().tryMoveToXYZ(x, y, z, 1.0D);
		}
	}

	public int getDistanceSquared(int x, int y, int z){
		int X = (int) (x - this.entity.posX);
		int Y = (int) (y - this.entity.posY);
		int Z = (int) (z - this.entity.posZ);
		return X* X + Y * Y + Z * Z;
	}

}
