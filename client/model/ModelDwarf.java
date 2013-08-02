package Altra.ModJam.client.model;

import net.minecraft.client.model.ModelVillager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDwarf extends ModelVillager{ //ModelBase{

	//This is temp, until I make the real Model
	public ModelDwarf(float par1) {
		super(par1);
	}
	
	public ModelDwarf(float par1, float par2, int par3, int par4){
		super(par1, par2, par3, par4);
    }

}
