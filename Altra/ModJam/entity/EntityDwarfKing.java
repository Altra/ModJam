package Altra.ModJam.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import Altra.ModJam.MJMod;
import Altra.ModJam.Item.ItemDebug;
import Altra.ModJam.settlement.Settlement;
import Altra.ModJam.settlement.building.Building;
import Altra.ModJam.settlement.building.ConstructionManager;

public class EntityDwarfKing extends EntityCreature{

	private final List subjects = new ArrayList();
	public Settlement settlement;
	public ConstructionManager constMan;
	public boolean settled = false;

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
		if(!settled && checkLocationSuitable()){
			this.tasks.removeTask(new EntityAIWander(this, 0.4D));
			this.worldObj.setBlock((int)this.posX, (int)this.posY-1, (int)this.posZ, 5, 1, 3);
			constMan = new ConstructionManager(this.worldObj, Building.dwarfCenterBuilding, (int)this.posX, (int)this.posY-1, (int)this.posZ);
			this.settled = true;
		}
		if(settled && constMan!=null && constMan.completed){
			constMan = null;
		}
		if(this.constMan!=null){
			this.constMan.tick();
		}
		
		if(this.isEntityInsideOpaqueBlock()){
			this.posY+=1;
		}
		
	}

	private boolean checkLocationSuitable(){
		int x = (int) this.posX;
		int y = (int) this.posY;
		int z = (int) this.posZ;
		if(this.worldObj.canBlockSeeTheSky(x, y, z)){
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
			return true;
		}
		return false;
	}

	public boolean interact(EntityPlayer player){
		if(player.getCurrentEquippedItem().itemID==MJMod.debugKingTool.itemID){
			ItemDebug item = (ItemDebug) player.getCurrentEquippedItem().getItem();
			item.king = this;
			return true;
		}
		return false;
	}

	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);
		
	}

	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);
	}

	protected boolean canDespawn()
	{
		return false;
	}

	public boolean setSettlement(Settlement s){
		if(s.getSettlementType() == "dwarven"){
			this.settlement = s;
			return true;
		}
		return false;
	}

	public Settlement getSettlement(){
		return this.settlement;
	}

}
