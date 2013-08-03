package Altra.ModJam.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import Altra.ModJam.client.model.ModelDwarf;
import Altra.ModJam.entity.EntityDwarf;
import Altra.ModJam.entity.EntityDwarfKing;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDwarf extends RenderLiving{
	
	protected ModelDwarf dwarfModel;
	
    private static final ResourceLocation dwarf = new ResourceLocation("altra", "/textures/entity/dwarf.png");
    private static final ResourceLocation dwarfKing = new ResourceLocation("altra", "/textures/entity/dwarfking.png");

    public RenderDwarf()  {
        super(new ModelDwarf(0.0F), 0.3F);
        this.dwarfModel = (ModelDwarf)this.mainModel;
    }
    
    public void renderDwarf(EntityLiving entity, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRenderLiving(entity, par2, par4, par6, par8, par9);
    }


    public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9)
    {
    	this.renderDwarf((EntityLiving)entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation func_110775_a(Entity entity) {
    	if(entity instanceof EntityDwarfKing){
    		return dwarfKing;
    	}else return dwarf;
    }
}
