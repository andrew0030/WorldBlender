package net.telepathicgrunt.worldblender.the_blender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;


public class VanillaFeatureGrouping
{
	public static Map<GenerationStage.Decoration, List<ConfiguredFeature<?, ?>>> bamboofeatures = Maps.newHashMap();
	static
	{
		Map<GenerationStage.Decoration, List<ConfiguredFeature<?, ?>>> result = new HashMap<GenerationStage.Decoration, List<ConfiguredFeature<?, ?>>>();
		for (GenerationStage.Decoration generationstage$decoration : GenerationStage.Decoration.values())
		{
			result.put(generationstage$decoration, Lists.newArrayList());
		}
		
		//add all vanilla features here
		result.get(GenerationStage.Decoration.VEGETAL_DECORATION).add(Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.2F)).withPlacement(Placement.TOP_SOLID_HEIGHTMAP_NOISE_BIASED.configure(new TopSolidWithNoiseConfig(160, 80.0D, 0.3D, Heightmap.Type.WORLD_SURFACE_WG))));
		result.get(GenerationStage.Decoration.VEGETAL_DECORATION).add(Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.0F)).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(16))));

		bamboofeatures = result;
	}
	
	public static Map<GenerationStage.Decoration, List<ConfiguredFeature<?, ?>>> lavaAndFirefeatures = Maps.newHashMap();
	static
	{
		Map<GenerationStage.Decoration, List<ConfiguredFeature<?, ?>>> result = new HashMap<GenerationStage.Decoration, List<ConfiguredFeature<?, ?>>>();
		for (GenerationStage.Decoration generationstage$decoration : GenerationStage.Decoration.values())
		{
			result.put(generationstage$decoration, Lists.newArrayList());
		}
		
		//add all vanilla features here
		result.get(GenerationStage.Decoration.VEGETAL_DECORATION).add(Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.LAVA_SPRING_CONFIG).withPlacement(Placement.COUNT_VERY_BIASED_RANGE.configure(new CountRangeConfig(20, 8, 16, 256))));
		result.get(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.NETHER_FIRE_CONFIG).withPlacement(Placement.HELL_FIRE.configure(new FrequencyConfig(10))));
		result.get(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.NETHER_SPRING_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 4, 8, 128))));
		result.get(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.NETHER_FIRE_CONFIG).withPlacement(Placement.HELL_FIRE.configure(new FrequencyConfig(10))));
		result.get(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.ENCLOSED_NETHER_SPRING_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(16, 10, 20, 128))));
		result.get(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.MAGMA_BLOCK.getDefaultState(), 33)).withPlacement(Placement.MAGMA.configure(new FrequencyConfig(4))));
		result.get(GenerationStage.Decoration.UNDERGROUND_DECORATION).add(Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.LAVA.getDefaultState())).withPlacement(Placement.LAVA_LAKE.configure(new ChanceConfig(80))));

		lavaAndFirefeatures = result;
	}
	
	
	
	
	
//	public static Map<GenerationStage.Decoration, List<ConfiguredFeature<?, ?>>> treefeatures = Maps.newHashMap();
//	static
//	{
//		Map<GenerationStage.Decoration, List<ConfiguredFeature<?, ?>>> result = new HashMap<GenerationStage.Decoration, List<ConfiguredFeature<?, ?>>>();
//		for (GenerationStage.Decoration generationstage$decoration : GenerationStage.Decoration.values())
//		{
//			result.put(generationstage$decoration, Lists.newArrayList());
//		}
//		
//		//add all vanilla features here
////		result.get(GenerationStage.Decoration.VEGETAL_DECORATION).add(Feature.BAMBOO.configure(new ProbabilityConfig(0.2F)).withPlacement(Placement.TOP_SOLID_HEIGHTMAP_NOISE_BIASED.configure(new TopSolidWithNoiseConfig(160, 80.0D, 0.3D, Heightmap.Type.WORLD_SURFACE_WG))));
////		result.get(GenerationStage.Decoration.VEGETAL_DECORATION).add(Feature.BAMBOO.configure(new ProbabilityConfig(0.0F)).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(16))));
//
//		treefeatures = result;
//	}
}
