package dev.thomasglasser.mineraculouskamikotizations.client;

import dev.thomasglasser.mineraculous.api.client.renderer.item.curio.ContextDependentCurioRenderer;
import dev.thomasglasser.mineraculous.api.world.item.MineraculousItems;
import dev.thomasglasser.mineraculouskamikotizations.client.particle.FloatingBubbleParticle;
import dev.thomasglasser.mineraculouskamikotizations.client.renderer.entity.BubblePrisonRenderer;
import dev.thomasglasser.mineraculouskamikotizations.client.renderer.item.MineraculousKamikotizationsItemProperties;
import dev.thomasglasser.mineraculouskamikotizations.core.particles.MineraculousKamikotizationsParticleTypes;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityTypes;
import dev.thomasglasser.mineraculouskamikotizations.world.item.MineraculousKamikotizationsItems;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.WindChargeRenderer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class MineraculousKamikotizationsClientEvents {
    // Init
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(MineraculousKamikotizationsItems.BUBBLE_SWORD.get(), ContextDependentCurioRenderer::new);

        MineraculousKamikotizationsItemProperties.init();
    }

    // Items
    public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {

        } else if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {

        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {

        } else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {

        } else if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {

        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            // Parasols
            event.insertAfter(Items.NETHERITE_HOE.getDefaultInstance(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.WHITE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.WHITE).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.LIGHT_GRAY).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.LIGHT_GRAY).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.GRAY).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.GRAY).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.BLACK).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.BLACK).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.BROWN).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.BROWN).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.RED).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.RED).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.ORANGE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.ORANGE).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.YELLOW).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.YELLOW).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.LIME).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.LIME).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.GREEN).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.GREEN).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.CYAN).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.CYAN).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.LIGHT_BLUE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.LIGHT_BLUE).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.BLUE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.BLUE).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.PURPLE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.PURPLE).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.MAGENTA).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.MAGENTA).toStack(), MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.PINK).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            // Bubble Wands
            event.insertAfter(MineraculousKamikotizationsItems.PARASOLS.get(DyeColor.PINK).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.WHITE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.WHITE).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.LIGHT_GRAY).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.LIGHT_GRAY).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.GRAY).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.GRAY).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.BLACK).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.BLACK).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.BROWN).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.BROWN).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.RED).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.RED).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.ORANGE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.ORANGE).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.YELLOW).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.YELLOW).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.LIME).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.LIME).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.GREEN).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.GREEN).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.CYAN).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.CYAN).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.LIGHT_BLUE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.LIGHT_BLUE).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.BLUE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.BLUE).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.PURPLE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.PURPLE).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.MAGENTA).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.MAGENTA).toStack(), MineraculousKamikotizationsItems.BUBBLE_WANDS.get(DyeColor.PINK).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.insertAfter(MineraculousItems.BUTTERFLY_CANE.toStack(), MineraculousKamikotizationsItems.WEATHER_CONTROL_PARASOL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {

        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {

        } else if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            // Must be in alphabetical order
        } else if (event.getTabKey() == CreativeModeTabs.OP_BLOCKS) {

        }
    }

    // Entities
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MineraculousKamikotizationsEntityTypes.ICE_CHARGE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(MineraculousKamikotizationsEntityTypes.GRIEF_TRACKING_ICE_CHARGE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(MineraculousKamikotizationsEntityTypes.GRIEF_TRACKING_LIGHTNING_BOLT.get(), LightningBoltRenderer::new);
        event.registerEntityRenderer(MineraculousKamikotizationsEntityTypes.GRIEF_TRACKING_WIND_CHARGE.get(), WindChargeRenderer::new);
        event.registerEntityRenderer(MineraculousKamikotizationsEntityTypes.BUBBLE_PRISON.get(), BubblePrisonRenderer::new);
    }

    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(MineraculousKamikotizationsParticleTypes.FLOATING_BUBBLE.get(), FloatingBubbleParticle.Provider::new);
    }
}
