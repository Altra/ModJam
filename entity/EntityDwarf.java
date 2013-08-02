package Altra.ModJam.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityDwarf extends EntityAgeable{

	public EntityDwarf(World par1World) {
		super(par1World);
        this.getNavigator().setAvoidsWater(true);
	}
	
    public boolean isAIEnabled()
    {
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
		// TODO Auto-generated method stub
		return null;
	}
	

}
