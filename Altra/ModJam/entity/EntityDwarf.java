package Altra.ModJam.entity;

import Altra.ModJam.entity.ai.EntityAIMine;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityDwarf extends EntityAgeable{

	public EntityDwarf(World par1World) {
		super(par1World);
        this.setSize(0.6F, 1.8F);
        this.getNavigator().setAvoidsWater(true);
       // this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIWander(this, 0.4D));
        this.tasks.addTask(5, new EntityAIMine(this));
	}
	
    public boolean isAIEnabled() {
        return true;
    }
    
    protected void updateAITick()
    {
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
    
	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return null;
	}
}
