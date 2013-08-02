package Altra.ModJam.proxy;

import Altra.ModJam.client.renderer.entity.RenderDwarf;
import Altra.ModJam.entity.EntityDwarf;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{

	@Override
	public void rendering(){
		RenderingRegistry.registerEntityRenderingHandler(EntityDwarf.class, new RenderDwarf());
	}

}
