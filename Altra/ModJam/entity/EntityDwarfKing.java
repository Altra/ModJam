package Altra.ModJam.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

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

    private boolean checkLocationSuitable(){
    	int x = (int) this.posX;
    	int y = (int) this.posY;
    	int z = (int) this.posZ;
    	if(this.worldObj.canBlockSeeTheSky(x, y-1, z)){
    		if (!this.worldObj.doesBlockHaveSolidTopSurface(x, y - 1, z)){
    			return false;
    		}else if(!this.worldObj.doesBlockHaveSolidTopSurface(x+1, y - 1, z)){
    			return false;
    		}else if(!this.worldObj.doesBlockHaveSolidTopSurface(x-1, y - 1, z)){
    			return false;
    		}else if(!this.worldObj.doesBlockHaveSolidTopSurface(x, y - 1, z+1)){
    			return false;
    		}else if(!this.worldObj.doesBlockHaveSolidTopSurface(x, y - 1, z-1)){
    			return false;
    		}
    		BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(x, y);
    		if(biome == BiomeGenBase.desert || biome == BiomeGenBase.desertHills || biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleHills){
    			return false;
    		}
    	}
    	return false;
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
