package Altra.ModJam.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import Altra.ModJam.MJMod;
import Altra.ModJam.Item.ItemDebug;
import Altra.ModJam.entity.ai.EntityAIMine;
import Altra.ModJam.settlement.Settlement;

public class EntityDwarf extends EntityAgeable{

	public EntityDwarfKing king;
	public Settlement settlement;
	public boolean reg;

	public EntityDwarf(World par1World) {
		super(par1World);
		this.setSize(0.5F, 1.3F);
		this.setCurrentItemOrArmor(0, new ItemStack(Item.pickaxeIron));
		this.getNavigator().setAvoidsWater(true);
		// this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIWander(this, 0.4D));
		this.tasks.addTask(2, new EntityAIMine(this));
	}

	public boolean isAIEnabled() {
		return true;
	}

	protected void updateAITick(){
		super.updateAITick();
		if(this.isEntityInsideOpaqueBlock()){
			this.posY+=3;
		}
		if(this.worldObj.getBlockId((int)this.posX, (int)this.posY, (int)this.posZ)!=0){
			this.posY+=3;
		}
		if(this.king!=null){
			if(this.king.constMan!=null && !this.reg){
				this.king.constMan.addWorkerEntity(this);
				this.reg = true;
			}
		}
		if(this.king==null || this.king.constMan==null){
			this.reg = false;
		}
		if(this.king != null && this.king.isDead){
			this.king=null;
		}
		if(this.isCollidedHorizontally){
			this.motionY += 0.05;
		}
	}

	public boolean interact(EntityPlayer player){
		if(player.getCurrentEquippedItem().itemID==MJMod.debugKingTool.itemID){
			ItemDebug item = (ItemDebug) player.getCurrentEquippedItem().getItem();
			this.king = item.king;
			this.reg = false;
			return true;
		}
		return false;
	}

	public void writeEntityToNBT(NBTTagCompound nbtTag){
		super.writeEntityToNBT(nbtTag);
	}

	public void readEntityFromNBT(NBTTagCompound nbtTag){
		super.readEntityFromNBT(nbtTag);
	}

	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return null;
	}

	public boolean setDwarfKing(EntityDwarfKing entity){
		if(getDistanceToEntity(entity)>60){
			return false;
		}else{
			this.king = entity;
			return true;
		}
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
