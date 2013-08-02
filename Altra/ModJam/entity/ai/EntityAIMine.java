package Altra.ModJam.entity.ai;

import Altra.ModJam.entity.EntityDwarf;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;


public class EntityAIMine{
	
    private int breakingTime;
    protected EntityLiving theEntity;
    protected EntityDwarf dwarf;
    protected int entityPosX;
    protected int entityPosY;
    protected int entityPosZ;

    public EntityAIMine(EntityLiving par1EntityLiving)
    {
    	this.theEntity = par1EntityLiving;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return !super.shouldExecute() ? false : (!this.theEntity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") ? false : !this.targetDoor.isDoorOpen(this.theEntity.worldObj, this.entityPosX, this.entityPosY, this.entityPosZ));
        if(theEntity instanceof EntityDwarf){
        	dwarf = (EntityDwarf) theEntity;
        	return dwarf.get
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
        this.breakingTime = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        double d0 = this.theEntity.getDistanceSq((double)this.entityPosX, (double)this.entityPosY, (double)this.entityPosZ);
        return this.breakingTime <= 240 && !this.targetDoor.isDoorOpen(this.theEntity.worldObj, this.entityPosX, this.entityPosY, this.entityPosZ) && d0 < 4.0D;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        super.resetTask();
        this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.entityId, this.entityPosX, this.entityPosY, this.entityPosZ, -1);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        super.updateTask();

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
    }
}
