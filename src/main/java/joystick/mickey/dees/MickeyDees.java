package joystick.mickey.dees;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.Feature;

public class MickeyDees implements ModInitializer {
	
	public static final Item MICKEY_DEES = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static final Item FLIMSY_SPATULA = new Item(new Item.Settings().group(ItemGroup.TOOLS));
	public static final Block NUGGIE_ORE = new NuggieOre(FabricBlockSettings.of(Material.STONE).strength(3.0F, 3.0F).sounds(BlockSoundGroup.STONE).breakByTool(FabricToolTags.PICKAXES).requiresTool());
	public static final Item RAW_NUGGIE = new Item(new Item.Settings().group(ItemGroup.FOOD).food(FoodComponents.RAW_NUGGIE));
	public static final Item COOKED_NUGGIE = new Item(new Item.Settings().group(ItemGroup.FOOD).food(FoodComponents.COOKED_NUGGIE));
	private static ConfiguredFeature<?, ?> NUGGIE_ORE_OVERWORLD = Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, NUGGIE_ORE.getDefaultState(),9)).range(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.aboveBottom(0), YOffset.fixed(120)))).spreadHorizontally().repeat(20);


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.


		Registry.register(Registry.ITEM, new Identifier("mickeydees", "mickey_dees"), MICKEY_DEES);
		Registry.register(Registry.ITEM, new Identifier("mickeydees", "flimsy_spatula"), FLIMSY_SPATULA);
		Registry.register(Registry.ITEM, new Identifier("mickeydees", "raw_nuggie"), RAW_NUGGIE);
		Registry.register(Registry.ITEM, new Identifier("mickeydees", "cooked_nuggie"), COOKED_NUGGIE);
		Registry.register(Registry.BLOCK, new Identifier("mickeydees", "nuggie_ore"), NUGGIE_ORE);
		Registry.register(Registry.ITEM, new Identifier("mickeydees", "nuggie_ore"), new BlockItem(NUGGIE_ORE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));


		RegistryKey<ConfiguredFeature<?, ?>> nuggieOreOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier("mickeydees", "nuggie_ore"));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, nuggieOreOverworld.getValue(), NUGGIE_ORE_OVERWORLD);
		// BiomeModifications is not supported right now, so it identifies as deprecated.
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, nuggieOreOverworld);
	}
}