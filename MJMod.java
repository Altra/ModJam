package Altra.ModJam;

import Altra.ModJam.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = "AMJM", name = "MJM(NameNeeded)", version = "0.01")
public class MJMod {

	@Instance("AMJM")
	public static MJMod instance;

	@SidedProxy(clientSide = "Altra.ModJam.proxy.ClientProxy", serverSide = "Altra.ModJam.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

	}

	@EventHandler
	public void load(FMLInitializationEvent event) {

	}

	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent event) {

	}
}