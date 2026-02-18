package com.mikkelcat.potion_overhaul;

import com.mikkelcat.potion_overhaul.networking.UpdatePotionRecipesPayload;
import com.mikkelcat.potion_overhaul.resources.PotionRecipe;
import com.mikkelcat.potion_overhaul.resources.RegistryManagersGetter;
import com.mikkelcat.potion_overhaul.utils.RegistryManagerHelper;
import com.mojang.serialization.Codec;
import com.portingdeadmods.portingdeadlibs.api.config.PDLConfigHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.*;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.HashMap;
import java.util.List;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PotionOverhaul.MODID)
public class PotionOverhaul {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "potion_overhaul";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DataMapType<Item, Integer> BREWING_FUEL_MAP = DataMapType.builder(rl("brewing_fuel"), Registries.ITEM, Codec.INT)
            .synced(Codec.INT, false)
            .build();

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.examplemod")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public PotionOverhaul(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        //Register potionsize changemthod to modevent bus.
        modEventBus.addListener(this::modifymaxpotionstack);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener(this::onDatapacksSynced);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerDatapackRegistries);
        modEventBus.addListener(this::registerDataMaps);
        modEventBus.addListener(this::registerPayloads);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        PDLConfigHelper.registerConfig(PotionOverhaulConfig.class, ModConfig.Type.COMMON, modContainer);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(EXAMPLE_BLOCK_ITEM);
        }
    }

    private void onDatapacksSynced(OnDatapackSyncEvent event) {
        ServerPlayer player = event.getPlayer();
        MinecraftServer server = event.getPlayerList().getServer();
        List<ServerPlayer> relevantPlayers = event.getPlayer() == null ? event.getPlayerList().getPlayers() : List.of(event.getPlayer());

        reloadPotionRecipes(player, relevantPlayers);

    }

    private static void reloadPotionRecipes(ServerPlayer player, List<ServerPlayer> relevantPlayers) {
        if (player != null) {
            updateReloadableRegistries(player);
        } else {
            for (ServerPlayer relevantPlayer : relevantPlayers) {
                updateReloadableRegistries(relevantPlayer);
            }
        }
    }

    private static void updateReloadableRegistries(ServerPlayer p) {
        PacketDistributor.sendToPlayer(p, new UpdatePotionRecipesPayload(new HashMap<>(RegistryManagerHelper.getPotionRecipesManager(p.level()).getByName())));
    }

    private void modifymaxpotionstack(ModifyDefaultComponentsEvent event)
    {
        event.modify(Items.POTION, builder ->
        {
            builder.set(DataComponents.MAX_STACK_SIZE, 8);
        });

        event.modify(Items.SPLASH_POTION, builder ->
        {
            builder.set(DataComponents.MAX_STACK_SIZE, 8);
        });

        event.modify(Items.LINGERING_POTION, builder ->
        {
            builder.set(DataComponents.MAX_STACK_SIZE, 8);
        });
    }

    private void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(PotionOverhaulRegistries.POTION_RECIPE_KEY, PotionRecipe.CODEC.codec(), PotionRecipe.CODEC.codec());
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID);
        registrar.playToClient(UpdatePotionRecipesPayload.TYPE, UpdatePotionRecipesPayload.STREAM_CODEC, UpdatePotionRecipesPayload::handle);
    }

    private void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(BREWING_FUEL_MAP);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
