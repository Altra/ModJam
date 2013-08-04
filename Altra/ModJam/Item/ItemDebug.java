package Altra.ModJam.Item;

import Altra.ModJam.entity.EntityDwarfKing;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDebug extends Item{
	
	public EntityDwarfKing king;

	public ItemDebug(int id) {
		super(id);
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

}
