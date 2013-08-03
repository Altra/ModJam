package Altra.ModJam.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityDwarfKing extends EntityCreature{

	public EntityDwarfKing(World par1World) {
		super(par1World);
        this.setSize(0.8F, 2.4F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(3, new EntityAIWander(this, 0.4D));
	}
	
    public boolean isAIEnabled() {
        return true;
    }
    
    protected void updateAITick(){
    	 super.updateAITick();
    }
    
    public boolean interact(EntityPlayer player){
    	return false;
    }
    
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
    }
    
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
    }
    
    protected boolean canDespawn()
    {
        return false;
    }
}
