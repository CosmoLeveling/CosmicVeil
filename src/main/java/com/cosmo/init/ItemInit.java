package com.cosmo.init;

import com.cosmo.CosmicVeil;
import com.cosmo.items.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ItemInit {
    private static final Identifier EMPTY_ARMOR_SLOT_HELMET_TEXTURE = new Identifier("item/empty_armor_slot_helmet");
    private static final Identifier EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE = new Identifier("item/empty_armor_slot_chestplate");
    private static final Identifier EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE = new Identifier("item/empty_armor_slot_leggings");
    private static final Identifier EMPTY_ARMOR_SLOT_BOOTS_TEXTURE = new Identifier("item/empty_armor_slot_boots");
    private static final Identifier EMPTY_SLOT_HOE_TEXTURE = new Identifier("item/empty_slot_hoe");
    private static final Identifier EMPTY_SLOT_AXE_TEXTURE = new Identifier("item/empty_slot_axe");
    private static final Identifier EMPTY_SLOT_SWORD_TEXTURE = new Identifier("item/empty_slot_sword");
    private static final Identifier EMPTY_SLOT_SHOVEL_TEXTURE = new Identifier("item/empty_slot_shovel");
    private static final Identifier EMPTY_SLOT_PICKAXE_TEXTURE = new Identifier("item/empty_slot_pickaxe");
    private static final Identifier EMPTY_SLOT_INGOT_TEXTURE = new Identifier("item/empty_slot_ingot");
    private static final Identifier EMPTY_SLOT_REDSTONE_DUST_TEXTURE = new Identifier("item/empty_slot_redstone_dust");
    private static final Identifier EMPTY_SLOT_QUARTZ_TEXTURE = new Identifier("item/empty_slot_quartz");
    private static final Identifier EMPTY_SLOT_EMERALD_TEXTURE = new Identifier("item/empty_slot_emerald");
    private static final Identifier EMPTY_SLOT_DIAMOND_TEXTURE = new Identifier("item/empty_slot_diamond");
    private static final Identifier EMPTY_SLOT_LAPIS_LAZULI_TEXTURE = new Identifier("item/empty_slot_lapis_lazuli");
    private static final Identifier EMPTY_SLOT_AMETHYST_SHARD_TEXTURE = new Identifier("item/empty_slot_amethyst_shard");

    public static Item ShadowMirror = registerItem("shadow_mirror",
			new ShadowMirrorItem(new FabricItemSettings().maxCount(1)));
	public static Item ShadowTrap = registerItem("shadow_trap", new ShadowTrap(new FabricItemSettings()));
	public static Item WeeperTotem = registerItem("weeper_totem", new WeeperTotem(new FabricItemSettings()));
    public static Item MonarchsSword = registerItem("monarchs_sword", new MonarchsBlade(ToolMaterials.NETHERITE,3,-2.4f,new FabricItemSettings().maxCount(1)));
	public static Item WeeperSpawnEgg = registerItem("weeper_spawn_egg",
			new SpawnEggItem(EntityInit.WEEPER, 0x2f114d, 0x3e1369, new FabricItemSettings()));
	public static Item ShadowCalibrator = registerItem("shadow_calibrator",
			new ShadowTransporterCalibrator(new FabricItemSettings()));
	public static Item WeeperPearl = registerItem("weeper_pearl", new Item(new FabricItemSettings()));
	public static Item MarksmanVeil = registerItem("marksmen_veil",
			new MarksmenVeil(new FabricItemSettings().maxCount(1)));
	public static Item ShadowShield = registerItem("shadow_shield",
			new ShieldItem(new FabricItemSettings().maxCount(1)));
    public static Item ShadowImbuer = registerItem("shadow_imbuer",
            new ShadowImbueItem(new FabricItemSettings()));
    public static Item ShadowCompass = registerItem("shadow_compass",
            new ShadowCompassItem(new FabricItemSettings().maxCount(1)));
    public static final Item SHADOW_HELMET = registerArmor("shadow_helmet", ArmorItem.Type.HELMET);
    public static final Item SHADOW_CHESTPLATE = registerArmor("shadow_chestplate", ArmorItem.Type.CHESTPLATE);
    public static final Item SHADOW_LEGGINGS = registerArmor("shadow_leggings", ArmorItem.Type.LEGGINGS);
    public static final Item SHADOW_BOOTS = registerArmor("shadow_boots", ArmorItem.Type.BOOTS);
    private static Item registerArmor(String name, ArmorItem.Type type) {
        return Registry.register(Registries.ITEM, new Identifier("cosmic_veil", name),
                new ShadowArmor(ModArmorMaterials.SHADOW_MONARCH, type, new FabricItemSettings().fireproof().maxCount(1)));
    }

    public static Item ShadowTemplate = registerItem("shadow_template",
            new SmithingTemplateItem(
                    Text.literal("Netherite Armor"),
                    Text.literal("Ashenite"),
                    Text.literal("Shadow Template"),
                    Text.literal("Add netherite armor or sword"),
                    Text.literal("Add Ashenite"),
                    List.of(EMPTY_ARMOR_SLOT_HELMET_TEXTURE,
                            EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE,
                            EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE,
                            EMPTY_ARMOR_SLOT_BOOTS_TEXTURE,
                            EMPTY_SLOT_SWORD_TEXTURE),
                    List.of(EMPTY_SLOT_INGOT_TEXTURE)
            )
            );
    public static Item RawShadrock = registerItem("raw_shadrock",new Item(new FabricItemSettings()));
    public static Item RawEclipsium = registerItem("raw_eclipsium",new Item(new FabricItemSettings()));
    public static Item Ashenite = registerItem("ashenite", new Item(new FabricItemSettings()));
    public static Item EclipsiumIngot = registerItem("eclipsium_ingot",new Item(new FabricItemSettings()));
    public static Item ShadrockIngot = registerItem("shadrock_ingot",new Item(new FabricItemSettings()));
    public static Item EclipsiumNugget = registerItem("eclipsium_nugget",new Item(new FabricItemSettings()));
    public static Item ShadrockNugget = registerItem("shadrock_nugget",new Item(new FabricItemSettings()));

	private static Item registerItem(String name, Item item) {
		return Registry.register(Registries.ITEM, new Identifier(CosmicVeil.MOD_ID, name), item);
	}

	public static void RegisterItems() {
	}
}
