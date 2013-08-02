package Altra.ModJam.entity.ai;

import cpw.mods.fml.common.FMLLog;
import Altra.ModJam.entity.EntityDwarf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIDoorInteract;
import net.minecraft.world.World;


public class EntityAIMine extends EntityAIBase{

	protected EntityLiving theEntity;
	protected EntityDwarf dwarf;
	protected int entityPosX;
	protected int entityPosY;
	protected int entityPosZ;
	private int breakingTime;

	private int targetBlockCoord[] = new int[3];

	public EntityAIMine(EntityLiving par1EntityLiving)
	{
		this.theEntity = par1EntityLiving;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		return targetBlock();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting(){
		FMLLog.info("HI");
		this.entityPosX = (int) this.theEntity.posX;
		this.entityPosY = (int) this.theEntity.posY;
		this.entityPosZ = (int) this.theEntity.posZ;
		targetBlock();
		this.breakingTime = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting(){
		if(this.targetBlockCoord[0]!=0 && this.targetBlockCoord[2]!=0){
			return true;
		}else if(targetBlock()){
			return true;
		} else
			return false;
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		super.resetTask();
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		this.entityPosX = (int) this.theEntity.posX;
		this.entityPosY = (int) this.theEntity.posY;
		this.entityPosZ = (int) this.theEntity.posZ;

		if (this.breakingTime >= 240){
			this.theEntity.worldObj.setBlockToAir(this.targetBlockCoord[0], this.targetBlockCoord[1], this.targetBlockCoord[2]);
		}

		if(this.targetBlockCoord[0]!=0 && this.targetBlockCoord[2]!=0){
			++this.breakingTime;
			this.mineTargetBlock();
		}

		/**

    	if (this.theEntity.getRNG().nextInt(20) == 0)
    	{
    		this.theEntity.worldObj.playAuxSFX(1010, this.entityPosX, this.entityPosY, this.entityPosZ, 0);
    	}

    	++this.breakingTime;
    	int i = (int)((float)this.breakingTime / 240.0F * 10.0F);

    	if (i != this.field_75358_j)
    	{
    		this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.entityId, this.entityPosX, this.entityPosY, this.entityPosZ, i);
    		this.field_75358_j = i;
    	}

    	if (this.breakingTime == 240 && this.theEntity.worldObj.difficultySetting == 3)
    	{
    		this.theEntity.worldObj.setBlockToAir(this.entityPosX, this.entityPosY, this.entityPosZ);
    		this.theEntity.worldObj.playAuxSFX(1012, this.entityPosX, this.entityPosY, this.entityPosZ, 0);
    		this.theEntity.worldObj.playAuxSFX(2001, this.entityPosX, this.entityPosY, this.entityPosZ, this.targetDoor.blockID);
    	}
		 **/
	}


	public boolean checkMinable(Material m){
		if(m==Material.rock)return true;
		if(m==Material.ground)return true;
		if(m==Material.grass)return true;
		if(m==Material.sand)return true;
		return false;
	}

	public boolean targetBlock(){
		World world = this.theEntity.worldObj;
		if(checkMinable(world.getBlockMaterial(entityPosX+1, entityPosY, entityPosZ))){
			this.targetBlockCoord[0] = entityPosX+1;
			this.targetBlockCoord[1] = entityPosY;
			this.targetBlockCoord[2] = entityPosZ;
			return true;
		}
		else if(checkMinable(world.getBlockMaterial(entityPosX-1, entityPosY, entityPosZ))){
			this.targetBlockCoord[0] = entityPosX-1;
			this.targetBlockCoord[1] = entityPosY;
			this.targetBlockCoord[2] = entityPosZ;
			return true;
		}
		else if(checkMinable(world.getBlockMaterial(entityPosX, entityPosY, entityPosZ+1))){
			this.targetBlockCoord[0] = entityPosX;
			this.targetBlockCoord[1] = entityPosY;
			this.targetBlockCoord[2] = entityPosZ+1;
			return true;
		}
		else if(checkMinable(world.getBlockMaterial(entityPosX, entityPosY, entityPosZ-1))){
			this.targetBlockCoord[0] = entityPosX;
			this.targetBlockCoord[1] = entityPosY;
			this.targetBlockCoord[2] = entityPosZ-1;
			return true;
		}
		return false;
	}

	private void mineTargetBlock(){
		//this.theEntity.worldObj.destroyBlockInWorldPartially(this.targetBlockCoord[0], this.targetBlockCoord[1], this.targetBlockCoord[2], id, 2);
		int i = (int)((float)this.breakingTime / 240.0F * 10.0F);
		this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.entityId, this.targetBlockCoord[0], this.targetBlockCoord[1], this.targetBlockCoord[2], i);
		if(this.theEntity.worldObj.getBlockId(this.targetBlockCoord[0], this.targetBlockCoord[1], this.targetBlockCoord[2])==0){
			this.targetBlockCoord[0] = 0;
			this.targetBlockCoord[1] = 0;
    		this.targetBlockCoord[2] = 0;
    	}
    }
}
