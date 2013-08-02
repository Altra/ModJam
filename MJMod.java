package Altra.ModJam;

import Altra.ModJam.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;


@Mod(modid = "AMJM", name = "MJM(NameNeeded)", version = "0.01")
public class MJMod {
	
	@Instance("AMJM")
	public static MJMod instance;
	
	@SidedProxy(clientSide = "Altra.ModJam.proxy.ClientProxy", serverSide = "Altra.ModJam.proxy.CommonProxy")
	public static CommonProxy proxy;
	

}
