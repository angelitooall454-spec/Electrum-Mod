package com.electrum;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.EnumMap;
import java.util.List;

public class ModItems {
    /** Entre hierro (15 dur., 9 encant.) y oro (7 dur., 25 encant.): 11 de durabilidad, 16 de encantabilidad, 13 de defensa. */
    public static final int DURABILITY = 11;

    public static final Holder<ArmorMaterial> MATERIAL = Registry.registerForHolder(
            BuiltInRegistries.ARMOR_MATERIAL, ElectrumMod.id("electrum"),
            new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
                        m.put(ArmorItem.Type.BOOTS, 2);
                        m.put(ArmorItem.Type.LEGGINGS, 4);
                        m.put(ArmorItem.Type.CHESTPLATE, 5);
                        m.put(ArmorItem.Type.HELMET, 2);
                        m.put(ArmorItem.Type.BODY, 5);
                    }),
                    16, SoundEvents.ARMOR_EQUIP_IRON,
                    () -> Ingredient.of(ModItems.ELECTRUM_INGOT),
                    List.of(new ArmorMaterial.Layer(ElectrumMod.id("electrum"))),
                    0.0F, 0.0F));

    public static final Block ELECTRUM_ORE = Registry.register(BuiltInRegistries.BLOCK,
            ElectrumMod.id("electrum_ore"), new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)));
    public static final Item ELECTRUM_ORE_ITEM = item("electrum_ore", new BlockItem(ELECTRUM_ORE, new Item.Properties()));
    public static final Item RAW_ELECTRUM = item("raw_electrum", new Item(new Item.Properties()));
    public static final Item ELECTRUM_INGOT = item("electrum_ingot", new Item(new Item.Properties()));

    public static final Item HELMET = armor("electrum_helmet", ArmorItem.Type.HELMET);
    public static final Item CHESTPLATE = armor("electrum_chestplate", ArmorItem.Type.CHESTPLATE);
    public static final Item LEGGINGS = armor("electrum_leggings", ArmorItem.Type.LEGGINGS);
    public static final Item BOOTS = armor("electrum_boots", ArmorItem.Type.BOOTS);

    private static Item item(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, ElectrumMod.id(name), item);
    }

    private static Item armor(String name, ArmorItem.Type type) {
        return item(name, new ArmorItem(MATERIAL, type,
                new Item.Properties().durability(type.getDurability(DURABILITY))));
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(e -> {
            e.accept(HELMET); e.accept(CHESTPLATE); e.accept(LEGGINGS); e.accept(BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(e -> {
            e.accept(RAW_ELECTRUM); e.accept(ELECTRUM_INGOT);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(e -> e.accept(ELECTRUM_ORE_ITEM));
    }
}
