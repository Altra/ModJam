package Altra.ModJam.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import Altra.ModJam.client.model.ModelDwarf;
import Altra.ModJam.entity.EntityDwarf;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDwarf extends RenderLiving{
	
	protected ModelDwarf dwarfModel;
	
    private static final ResourceLocation t = new ResourceLocation("altra", "/textures/entity/dwarf.png");

    public RenderDwarf()  {
        super(new ModelDwarf(0.0F), 0.5F);
        this.dwarfModel = (ModelDwarf)this.mainModel;
    }
    
    public void renderDwarf(EntityDwarf entity, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRenderLiving(entity, par2, par4, par6, par8, par9);
        this.renderEquippedItems(entity, par8);
    }


	public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderDwarf((EntityDwarf)entity, par2, par4, par6, par8, par9);
    }

	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		return t;
	}
}
