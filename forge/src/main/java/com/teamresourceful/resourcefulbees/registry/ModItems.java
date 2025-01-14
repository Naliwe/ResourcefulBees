package com.teamresourceful.resourcefulbees.registry;

import com.teamresourceful.resourcefulbees.ResourcefulBees;
import com.teamresourceful.resourcefulbees.config.Config;
import com.teamresourceful.resourcefulbees.entity.passive.KittenBee;
import com.teamresourceful.resourcefulbees.item.*;
import com.teamresourceful.resourcefulbees.lib.ModConstants;
import com.teamresourceful.resourcefulbees.lib.NBTConstants;
import com.teamresourceful.resourcefulbees.utils.TooltipBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ModItems {

    private ModItems() {
        throw new IllegalStateException(ModConstants.UTILITY_CLASS);
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ResourcefulBees.MOD_ID);


    @SuppressWarnings("deprecation")
    public static final RegistryObject<Item> OREO_COOKIE = ITEMS.register("oreo_cookie", () -> new Item(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).food(new FoodProperties.Builder()
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 600, 1), 1)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1)
            .effect(new MobEffectInstance(MobEffects.SATURATION, 2400, 1), 1)
            .effect(new MobEffectInstance(MobEffects.LUCK, 600, 3), 1)
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0), 1)
            .effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 6000, 0), 1)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1)
            .nutrition(8)
            .saturationMod(2)
            .alwaysEat()
            .build())
            .rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> CATNIP_HONEYCOMB = ITEMS.register("catnip_honeycomb", () -> new Item(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CATNIP_HONEY_BOTTLE = ITEMS.register("catnip_honey_bottle", () -> new CustomHoneyBottleItem(KittenBee.getHoneyBottleData().getProperties().rarity(Rarity.EPIC), KittenBee.getHoneyBottleData()));
    public static final RegistryObject<Item> CATNIP_HONEYCOMB_BLOCK_ITEM = ITEMS.register("catnip_honeycomb_block", () -> new BlockItem(ModBlocks.CATNIP_HONEYCOMB_BLOCK.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CATNIP_HONEY_BLOCK_ITEM = ITEMS.register("catnip_honey_block", () -> new BlockItem(ModBlocks.CATNIP_HONEY_BLOCK.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CATNIP_HONEY_FLUID_BUCKET = ITEMS.register("catnip_honey_fluid_bucket", () -> new CustomHoneyBucketItem(ModFluids.CATNIP_HONEY_STILL, new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.RARE), KittenBee.getHoneyBottleData()));
    public static final RegistryObject<Item> CRAFTING_BEE_BOX = ITEMS.register("crafting_bee_box", () -> new BeeBox(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).stacksTo(1), true));
    public static final RegistryObject<Item> BEE_BOX = ITEMS.register("bee_box", () -> new BeeBox(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).stacksTo(1), false));
    public static final RegistryObject<Item> BEEPEDIA = ITEMS.register("beepedia", () -> new Beepedia(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).stacksTo(1)));
    public static final RegistryObject<Item> HONEY_DIPPER = ITEMS.register("honey_dipper", () -> new HoneyDipper(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).stacksTo(1)));

    public static final RegistryObject<Item> SCRAPER = ITEMS.register("scraper", () -> new Item(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).stacksTo(1)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TextComponent(ChatFormatting.GOLD + I18n.get("item.resourcefulbees.scraper.tooltip")));
            tooltip.add(new TextComponent(ChatFormatting.GOLD + I18n.get("item.resourcefulbees.scraper.tooltip.1")));
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final RegistryObject<Item> SMOKER = ITEMS.register("smoker", () -> new Smoker(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).setNoRepair().durability(Config.SMOKER_DURABILITY.get())));
    public static final RegistryObject<Item> BELLOW = ITEMS.register("bellow", () -> new Item(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> SMOKERCAN = ITEMS.register("smoker_can", () -> new Item(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> WAX = ITEMS.register("wax", () -> new Item(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)) {
        @Override
        public int getBurnTime(ItemStack itemStack) {
            return 400;
        }
    });
    public static final RegistryObject<Item> CENTRIFUGE_ITEM = ITEMS.register("centrifuge", () -> new BlockItem(ModBlocks.CENTRIFUGE.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> MECHANICAL_CENTRIFUGE_ITEM = ITEMS.register("mechanical_centrifuge", () -> new BlockItem(ModBlocks.MECHANICAL_CENTRIFUGE.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> CENTRIFUGE_CONTROLLER_ITEM = ITEMS.register("centrifuge_controller", () -> new BlockItem(ModBlocks.CENTRIFUGE_CONTROLLER.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> CENTRIFUGE_CASING_ITEM = ITEMS.register("centrifuge_casing", () -> new BlockItem(ModBlocks.CENTRIFUGE_CASING.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> ELITE_CENTRIFUGE_CONTROLLER_ITEM = ITEMS.register("elite_centrifuge_controller", () -> new BlockItem(ModBlocks.ELITE_CENTRIFUGE_CONTROLLER.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));

    public static final RegistryObject<Item> ELITE_CENTRIFUGE_CASING_ITEM = ITEMS.register("elite_centrifuge_casing", () -> new BlockItem(ModBlocks.ELITE_CENTRIFUGE_CASING.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> HONEY_GENERATOR_ITEM = ITEMS.register("honey_generator", () -> new BlockItem(ModBlocks.HONEY_GENERATOR.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> WAX_BLOCK_ITEM = ITEMS.register("wax_block", () -> new BlockItem(ModBlocks.WAX_BLOCK.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)) {
        @Override
        public int getBurnTime(ItemStack itemStack) {
            return 4000;
        }
    });
    public static final RegistryObject<Item> GOLD_FLOWER_ITEM = ITEMS.register("gold_flower", () -> new BlockItem(ModBlocks.GOLD_FLOWER.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> BEE_JAR = ITEMS.register("bee_jar", () -> new BeeJar(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).durability(0).stacksTo(16)));
    public static final RegistryObject<Item> OAK_BEE_NEST_ITEM = ITEMS.register("bee_nest", () -> new BlockItem(ModBlocks.OAK_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> ACACIA_BEE_NEST_ITEM = ITEMS.register("acacia_bee_nest", () -> new BlockItem(ModBlocks.ACACIA_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> GRASS_BEE_NEST_ITEM = ITEMS.register("grass_bee_nest", () -> new BlockItem(ModBlocks.GRASS_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> JUNGLE_BEE_NEST_ITEM = ITEMS.register("jungle_bee_nest", () -> new BlockItem(ModBlocks.JUNGLE_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> NETHER_BEE_NEST_ITEM = ITEMS.register("nether_bee_nest", () -> new BlockItem(ModBlocks.NETHER_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> PRISMARINE_BEE_NEST_ITEM = ITEMS.register("prismarine_bee_nest", () -> new BlockItem(ModBlocks.PRISMARINE_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> PURPUR_BEE_NEST_ITEM = ITEMS.register("purpur_bee_nest", () -> new BlockItem(ModBlocks.PURPUR_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> BIRCH_BEE_NEST_ITEM = ITEMS.register("birch_bee_nest", () -> new BlockItem(ModBlocks.BIRCH_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> WITHER_BEE_NEST_ITEM = ITEMS.register("wither_bee_nest", () -> new BlockItem(ModBlocks.WITHER_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> BROWN_MUSHROOM_NEST_ITEM = ITEMS.register("brown_mushroom_bee_nest", () -> new BlockItem(ModBlocks.BROWN_MUSHROOM_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> CRIMSON_BEE_NEST_ITEM = ITEMS.register("crimson_bee_nest", () -> new BlockItem(ModBlocks.CRIMSON_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> CRIMSON_NYLIUM_BEE_NEST_ITEM = ITEMS.register("crimson_nylium_bee_nest", () -> new BlockItem(ModBlocks.CRIMSON_NYLIUM_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> DARK_OAK_NEST_ITEM = ITEMS.register("dark_oak_bee_nest", () -> new BlockItem(ModBlocks.DARK_OAK_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> RED_MUSHROOM_NEST_ITEM = ITEMS.register("red_mushroom_bee_nest", () -> new BlockItem(ModBlocks.RED_MUSHROOM_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> SPRUCE_BEE_NEST_ITEM = ITEMS.register("spruce_bee_nest", () -> new BlockItem(ModBlocks.SPRUCE_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> WARPED_BEE_NEST_ITEM = ITEMS.register("warped_bee_nest", () -> new BlockItem(ModBlocks.WARPED_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> WARPED_NYLIUM_BEE_NEST_ITEM = ITEMS.register("warped_nylium_bee_nest", () -> new BlockItem(ModBlocks.WARPED_NYLIUM_BEE_NEST.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> T1_APIARY_ITEM = ITEMS.register("t1_apiary", () -> new BlockItem(ModBlocks.T1_APIARY_BLOCK.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> T2_APIARY_ITEM = ITEMS.register("t2_apiary", () -> new BlockItem(ModBlocks.T2_APIARY_BLOCK.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> T3_APIARY_ITEM = ITEMS.register("t3_apiary", () -> new BlockItem(ModBlocks.T3_APIARY_BLOCK.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> T4_APIARY_ITEM = ITEMS.register("t4_apiary", () -> new BlockItem(ModBlocks.T4_APIARY_BLOCK.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> APIARY_STORAGE_ITEM = ITEMS.register("apiary_storage", () -> new BlockItem(ModBlocks.APIARY_STORAGE_BLOCK.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> APIARY_BREEDER_ITEM = ITEMS.register("apiary_breeder", () -> new BlockItem(ModBlocks.APIARY_BREEDER_BLOCK.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> ENDER_BEECON_ITEM = ITEMS.register("ender_beecon", () -> new BlockItem(ModBlocks.ENDER_BEECON.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> PURPUR_HONEY_TANK_ITEM = ITEMS.register("purpur_honey_tank", () -> new BlockItem(ModBlocks.PURPUR_HONEY_TANK.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> NETHER_HONEY_TANK_ITEM = ITEMS.register("nether_honey_tank", () -> new BlockItem(ModBlocks.NETHER_HONEY_TANK.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> WOODEN_HONEY_TANK_ITEM = ITEMS.register("wooden_honey_tank", () -> new BlockItem(ModBlocks.WOODEN_HONEY_TANK.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> HONEY_CONGEALER_ITEM = ITEMS.register("honey_congealer", () -> new BlockItem(ModBlocks.HONEY_CONGEALER.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
//    public static final RegistryObject<Item> HONEY_PIPE_ITEM = ITEMS.register("honey_pipe", () -> new BlockItem(ModBlocks.HONEY_PIPE.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));
    public static final RegistryObject<Item> BOTTOMLESS_HONEY_POT = ITEMS.register("bottomless_honey_pot", () -> new BlockItem(ModBlocks.BOTTOMLESS_HONEY_POT.get(), new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES)));

    public static final RegistryObject<Item> IRON_STORAGE_UPGRADE = ITEMS.register("iron_storage_upgrade", () -> new UpgradeItem(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).durability(0).stacksTo(16),
            UpgradeItem.builder()
                    .upgradeType(NBTConstants.NBT_STORAGE_UPGRADE)
                    .upgradeModification(NBTConstants.NBT_SLOT_UPGRADE, 27F)
                    .build()));
    public static final RegistryObject<Item> GOLD_STORAGE_UPGRADE = ITEMS.register("gold_storage_upgrade", () -> new UpgradeItem(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).durability(0).stacksTo(16),
            UpgradeItem.builder()
                    .upgradeType(NBTConstants.NBT_STORAGE_UPGRADE)
                    .upgradeModification(NBTConstants.NBT_SLOT_UPGRADE, 54F)
                    .build()));
    public static final RegistryObject<Item> DIAMOND_STORAGE_UPGRADE = ITEMS.register("diamond_storage_upgrade", () -> new UpgradeItem(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).durability(0).stacksTo(16),
            UpgradeItem.builder()
                    .upgradeType(NBTConstants.NBT_STORAGE_UPGRADE)
                    .upgradeModification(NBTConstants.NBT_SLOT_UPGRADE, 81F)
                    .build()));
    public static final RegistryObject<Item> EMERALD_STORAGE_UPGRADE = ITEMS.register("emerald_storage_upgrade", () -> new UpgradeItem(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).durability(0).stacksTo(16),
            UpgradeItem.builder()
                    .upgradeType(NBTConstants.NBT_STORAGE_UPGRADE)
                    .upgradeModification(NBTConstants.NBT_SLOT_UPGRADE, 108F)
                    .build()));
    public static final RegistryObject<Item> APIARY_BREEDER_UPGRADE = ITEMS.register("apiary_breeder_upgrade", () -> new UpgradeItem(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).durability(0).stacksTo(16),
            UpgradeItem.builder()
                    .upgradeType(NBTConstants.NBT_BREEDER_UPGRADE)
                    .upgradeModification(NBTConstants.NBT_BREEDER_COUNT, 1)
                    .build()) {
        @Override
        public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
            tooltip.addAll(new TooltipBuilder()
                    .addTip(I18n.get("item.resourcefulbees.apiary_breeder_upgrade.tooltip.info"), ChatFormatting.GOLD)
                    .build());
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final RegistryObject<Item> APIARY_BREED_TIME_UPGRADE = ITEMS.register("apiary_breed_time_upgrade", () -> new UpgradeItem(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).durability(0).stacksTo(16),
            UpgradeItem.builder()
                    .upgradeType(NBTConstants.NBT_BREEDER_UPGRADE)
                    .upgradeModification(NBTConstants.NBT_BREED_TIME, 300)
                    .build()) {
        @Override
        public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
            tooltip.addAll(new TooltipBuilder()
                    .addTip(I18n.get("item.resourcefulbees.apiary_breed_time_upgrade.tooltip.info"), ChatFormatting.GOLD)
                    .build());
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final RegistryObject<Item> HONEY_FLUID_BUCKET = ITEMS.register("honey_fluid_bucket", () -> new BucketItem(ModFluids.HONEY_STILL, new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> T1_HIVE_UPGRADE = ITEMS.register("t1_hive_upgrade", () -> new UpgradeItem(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).durability(0).stacksTo(16),
            UpgradeItem.builder()
                    .upgradeType(NBTConstants.NBT_HIVE_UPGRADE)
                    .upgradeModification(NBTConstants.NBT_TIER, 1f)
                    .upgradeModification(NBTConstants.NBT_TIER_MODIFIER, 1f)
                    .build()) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.addAll(new TooltipBuilder()
                    .addTranslatableTip(ModConstants.HIVE_UPGRADE_TOOLTIP, ChatFormatting.GOLD)
                    .build());
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final RegistryObject<Item> T2_HIVE_UPGRADE = ITEMS.register("t2_hive_upgrade", () -> new UpgradeItem(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).durability(0).stacksTo(16),
            UpgradeItem.builder()
                    .upgradeType(NBTConstants.NBT_HIVE_UPGRADE)
                    .upgradeModification(NBTConstants.NBT_TIER, 2f)
                    .upgradeModification(NBTConstants.NBT_TIER_MODIFIER, 1.5f)
                    .build()) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.addAll(new TooltipBuilder()
                    .addTranslatableTip(ModConstants.HIVE_UPGRADE_TOOLTIP, ChatFormatting.GOLD)
                    .build());
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final RegistryObject<Item> T3_HIVE_UPGRADE = ITEMS.register("t3_hive_upgrade", () -> new UpgradeItem(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).durability(0).stacksTo(16),
            UpgradeItem.builder()
                    .upgradeType(NBTConstants.NBT_HIVE_UPGRADE)
                    .upgradeModification(NBTConstants.NBT_TIER, 3f)
                    .upgradeModification(NBTConstants.NBT_TIER_MODIFIER, 2f)
                    .build()) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.addAll(new TooltipBuilder()
                    .addTranslatableTip(ModConstants.HIVE_UPGRADE_TOOLTIP, ChatFormatting.GOLD)
                    .build());
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final RegistryObject<Item> T4_HIVE_UPGRADE = ITEMS.register("t4_hive_upgrade", () -> new UpgradeItem(new Item.Properties().tab(ItemGroupResourcefulBees.RESOURCEFUL_BEES).durability(0).stacksTo(16),
            UpgradeItem.builder()
                    .upgradeType(NBTConstants.NBT_HIVE_UPGRADE)
                    .upgradeModification(NBTConstants.NBT_TIER, 4f)
                    .upgradeModification(NBTConstants.NBT_TIER_MODIFIER, 4f)
                    .build()) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.addAll(new TooltipBuilder()
                    .addTranslatableTip(ModConstants.HIVE_UPGRADE_TOOLTIP, ChatFormatting.GOLD)
                    .build());
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
        }
    });

}
