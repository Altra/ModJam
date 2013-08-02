package Altra.ModJam;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import Altra.ModJam.blocks.BlockMineDoor;
import Altra.ModJam.entity.EntityDwarf;
import Altra.ModJam.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(modid = "AMJM", name = "MJM(NameNeeded)", version = "0.024")
@NetworkMod(serverSideRequired = false, clientSideRequired = true)

public class MJMod {
	@Instance("AMJM")
	public static MJMod instance;

	@SidedProxy(clientSide = "Altra.ModJam.proxy.ClientProxy", serverSide = "Altra.ModJam.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Block mineDoor;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		
		EntityRegistry.registerModEntity(EntityDwarf.class, "Dwarf", 1, instance, 30, 4, true);	
		LanguageRegistry.instance().addStringLocalization("entity.AMJM.Dwarf.name", "Dwarf");
		
		BiomeGenBase[] forest = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.FOREST);
		
		EntityRegistry.addSpawn(EntityDwarf.class, 70, 8, 12, EnumCreatureType.creature, forest);
		
		proxy.rendering();
		
		
		mineDoor = new BlockMineDoor(3000, Material.iron);
		
		
		
		/**
		int id = 230; // has to be unique
		EntityList.IDtoClassMapping.put(id, EntityDwarf.class);
		EntityList.entityEggs.put(id, new EntityEggInfo(id, 0x00000, 0xFFFFF));
		 **/
	}

	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent event) {

	}
}