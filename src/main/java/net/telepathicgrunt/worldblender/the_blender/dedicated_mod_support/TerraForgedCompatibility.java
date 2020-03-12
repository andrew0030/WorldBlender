package net.telepathicgrunt.worldblender.the_blender.dedicated_mod_support;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.terraforged.feature.template.decorator.DecoratedFeature;
import com.terraforged.feature.template.feature.MultiTemplateFeature;
import com.terraforged.feature.template.feature.TemplateFeature;
import com.terraforged.feature.template.feature.TemplateFeatureConfig;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import net.telepathicgrunt.worldblender.biome.WBBiomes;
import net.telepathicgrunt.worldblender.configs.WBConfig;
import net.telepathicgrunt.worldblender.the_blender.ConfigBlacklisting;
import net.telepathicgrunt.worldblender.the_blender.ConfigBlacklisting.BlacklistType;

public class TerraForgedCompatibility
{
	private static Set<ResourceLocation> terraFeaturesRL = 
			Stream.of(
				new ResourceLocation("terraforged:willow_small"),
				new ResourceLocation("terraforged:jungle_large"),
				new ResourceLocation("terraforged:oak_large"),
				new ResourceLocation("terraforged:pine"),
				new ResourceLocation("terraforged:jungle_huge"),
				new ResourceLocation("terraforged:acacia_large"),
				new ResourceLocation("terraforged:oak_huge"),
				new ResourceLocation("terraforged:willow_large"),
				new ResourceLocation("terraforged:oak_small"),
				new ResourceLocation("terraforged:birch_large"),
				new ResourceLocation("terraforged:redwood_large"),
				new ResourceLocation("terraforged:acacia_small"),
				new ResourceLocation("terraforged:birch_forest"),
				new ResourceLocation("terraforged:birch_small"),
				new ResourceLocation("terraforged:oak_small"),
				new ResourceLocation("terraforged:dark_oak_small"),
				new ResourceLocation("terraforged:redwood_huge"),
				new ResourceLocation("terraforged:spruce_large"),
				new ResourceLocation("terraforged:jungle_small"),
				new ResourceLocation("terraforged:dark_oak_large")
			).collect(Collectors.toCollection(HashSet::new));
	                                 
	public static void addTerraForgedtrees() 
	{                                 
		if(!WBConfig.allowModdedFeatures || ConfigBlacklisting.isResourceLocationBlacklisted(BlacklistType.BLANKET, new ResourceLocation("terraforged:_")))
		{
			return;
		}
		
		for(Feature<?> feature : ForgeRegistries.FEATURES)
		{                             
			//find terraforge trees
			if(terraFeaturesRL.contains(feature.getRegistryName()) && !ConfigBlacklisting.isResourceLocationBlacklisted(BlacklistType.FEATURE, feature.getRegistryName()))
			{
				//add it to our biomes with vanilla tree placement
				for(Biome blendedBiome : WBBiomes.biomes)
				{
					if(feature instanceof TemplateFeature)
					{
						blendedBiome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, 
								((TemplateFeature)feature).withConfiguration(new TemplateFeatureConfig(false, false, 0)).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(5, 0.1F, 1))));
					}
					else if(feature instanceof MultiTemplateFeature)
					{
						blendedBiome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, 
								((MultiTemplateFeature)feature).withConfiguration(new TemplateFeatureConfig(false, false, 0)).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(5, 0.1F, 1))));
					}
					else if(feature instanceof DecoratedFeature)
					{
						blendedBiome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, 
								((DecoratedFeature<?, ?>)feature).withConfiguration(new TemplateFeatureConfig(false, false, 0)).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(5, 0.1F, 1))));
					}
					else
					{
						blendedBiome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, 
								((TemplateFeature)feature).withConfiguration(new TemplateFeatureConfig(false, false, 0)).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(5, 0.1F, 1))));
					}
				}
			}
		}
	}
}