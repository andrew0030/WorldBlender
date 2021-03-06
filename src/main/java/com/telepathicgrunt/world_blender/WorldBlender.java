package com.telepathicgrunt.world_blender;

import com.telepathicgrunt.world_blender.blocks.WBBlocks;
import com.telepathicgrunt.world_blender.blocks.WBPortalSpawning;
import com.telepathicgrunt.world_blender.configs.WBBlendingConfigs;
import com.telepathicgrunt.world_blender.configs.WBDimensionConfigs;
import com.telepathicgrunt.world_blender.configs.WBPortalConfigs;
import com.telepathicgrunt.world_blender.features.WBConfiguredFeatures;
import com.telepathicgrunt.world_blender.features.WBFeatures;
import com.telepathicgrunt.world_blender.generation.WBBiomeProvider;
import com.telepathicgrunt.world_blender.surfacebuilder.WBSurfaceBuilders;
import com.telepathicgrunt.world_blender.the_blender.TheBlender;
import com.telepathicgrunt.world_blender.utils.ConfigHelper;
import com.telepathicgrunt.world_blender.utils.MessageHandler;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(WorldBlender.MODID)
public class WorldBlender{
	public static final String MODID = "world_blender";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public static WBBlendingConfigs.WBConfigValues WBBlendingConfig = null;
	public static WBDimensionConfigs.WBConfigValues WBDimensionConfig = null;
	public static WBPortalConfigs.WBConfigValues WBPortalConfig = null;

	private static boolean chestListGenerated = false;

	public WorldBlender() {

		//Set up config
		WBBlendingConfig = ConfigHelper.register(ModConfig.Type.COMMON, WBBlendingConfigs.WBConfigValues::new, "world_blender-blending.toml");
		WBDimensionConfig = ConfigHelper.register(ModConfig.Type.COMMON, WBDimensionConfigs.WBConfigValues::new, "world_blender-dimension.toml");
		WBPortalConfig = ConfigHelper.register(ModConfig.Type.COMMON, WBPortalConfigs.WBConfigValues::new, "world_blender-portal.toml");

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(this::setup);
		modEventBus.addGenericListener(Biome.class, this::registerBiomes);
		modEventBus.addGenericListener(Feature.class, this::registerFeatures);
		modEventBus.addGenericListener(Block.class, WBBlocks::registerBlocks);
		modEventBus.addGenericListener(TileEntityType.class, WBBlocks::registerBlockEntities);
		modEventBus.addGenericListener(SurfaceBuilder.class, WBSurfaceBuilders::registerSurfaceBuilders);

		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.NORMAL, this::setupChestList);
		forgeBus.addListener(EventPriority.LOWEST, TheBlender::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.NORMAL, WBPortalSpawning::BlockRightClickEvent);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> WorldBlenderClient::subscribeClientEvents);
	}

	public void setup(final FMLCommonSetupEvent event)
	{
		MessageHandler.init();
	}

	public void setupChestList(final WorldEvent.Load event)
	{
		// Do it at any world startup so tile-entities using tags like Vampirism does not crash.
		// We do not need to re-make like when entering other worlds as blocks/tile-entities are
		// not dynamic registries like worldgen registries are.
		if(!chestListGenerated){
			WBPortalSpawning.generateRequiredBlockList(WBPortalConfig.requiredBlocksInChests.get());
			chestListGenerated = true;
		}
	}

	@Deprecated
	public void registerBiomes(final RegistryEvent.Register<Biome> event)
	{
		//Reserve WorldBlender biome IDs for the json version to replace
		Registry.register(WorldGenRegistries.field_243657_i, WBIdentifiers.GENERAL_BLENDED_BIOME_ID, BiomeMaker.func_244234_c(false));
		Registry.register(WorldGenRegistries.field_243657_i, WBIdentifiers.MOUNTAINOUS_BLENDED_BIOME_ID, BiomeMaker.func_244234_c(false));
		Registry.register(WorldGenRegistries.field_243657_i, WBIdentifiers.COLD_HILLS_BLENDED_BIOME_ID, BiomeMaker.func_244234_c(false));
		Registry.register(WorldGenRegistries.field_243657_i, WBIdentifiers.OCEAN_BLENDED_BIOME_ID, BiomeMaker.func_244234_c(false));
		Registry.register(WorldGenRegistries.field_243657_i, WBIdentifiers.FROZEN_OCEAN_BLENDED_BIOME_ID, BiomeMaker.func_244234_c(false));

		WBBiomeProvider.registerBiomeProvider();
	}

	public void registerFeatures(final RegistryEvent.Register<Feature<?>> event)
	{
		//registers all my features
		WBFeatures.registerFeatures(event);
		WBConfiguredFeatures.registerConfiguredFeatures();
	}


	/*
	 * Helper method to quickly register features, blocks, items, structures, biomes, anything that can be registered.
	 */
	public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey)
	{
		entry.setRegistryName(new ResourceLocation(MODID, registryKey));
		registry.register(entry);
		return entry;
	}
}
