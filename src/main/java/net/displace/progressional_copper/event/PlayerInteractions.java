package net.displace.progressional_copper.event;

import net.displace.progressional_copper.Config;
import net.displace.progressional_copper.ProgressionalCopper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = ProgressionalCopper.MOD_ID)
public class PlayerInteractions {

//    public static final Logger LOGGER = LoggerFactory.getLogger("progressional_copper");

    // map of copper to iron chest modifications
private static final Map<Item, Item> copperToIronChestConversions = Map.ofEntries(
        Map.entry(Items.COPPER_PICKAXE, Items.IRON_PICKAXE),
        Map.entry(Items.COPPER_SHOVEL, Items.IRON_SHOVEL),
        Map.entry(Items.COPPER_SWORD, Items.IRON_SWORD),
        Map.entry(Items.COPPER_HELMET, Items.IRON_HELMET),
        Map.entry(Items.COPPER_CHESTPLATE, Items.IRON_CHESTPLATE),
        Map.entry(Items.COPPER_LEGGINGS, Items.IRON_LEGGINGS),
        Map.entry(Items.COPPER_BOOTS, Items.IRON_BOOTS),
        Map.entry(Items.COPPER_HORSE_ARMOR, Items.IRON_HORSE_ARMOR),
        Map.entry(Items.COPPER_SPEAR, Items.IRON_SPEAR)
);
    // lists of conversions within the villager trades
    public static final List<Item> copperEquipmentList = List.of(Items.COPPER_PICKAXE, Items.COPPER_AXE, Items.COPPER_SHOVEL,
            Items.COPPER_SWORD, Items.COPPER_HOE, Items.COPPER_HELMET, Items.COPPER_CHESTPLATE, Items.COPPER_LEGGINGS,
            Items.COPPER_BOOTS, Items.COPPER_NAUTILUS_ARMOR, Items.COPPER_SPEAR);
    public static final List<Item> ironEquipmentList = List.of(Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_SHOVEL,
            Items.IRON_SWORD, Items.IRON_HOE, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS,
            Items.IRON_NAUTILUS_ARMOR, Items.IRON_SPEAR);
    public static final List<Item> diamondEquipmentList = List.of(Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_SHOVEL,
            Items.DIAMOND_SWORD, Items.DIAMOND_HOE, Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS,
            Items.DIAMOND_BOOTS, Items.DIAMOND_NAUTILUS_ARMOR, Items.DIAMOND_SPEAR);

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos blockPos = event.getPos();

        if (level.isClientSide()) {
            return;
        }

        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();

        // Iron tool chest replacement
        if (Config.REPLACE_IRON_EQUIPMENT_WITH_COPPER.isTrue()) {
            ironToolReplacement(block, level, blockPos);
        }
    }

    @SubscribeEvent
    public static void onEntityInteraction(PlayerInteractEvent.EntityInteract event) {

        if (event.getLevel().isClientSide()) {
            return;
        }

        if (event.getTarget() instanceof Villager villager) {
            var allOffers = villager.getOffers();
            for (MerchantOffer offer : allOffers) {

                for (int i = 0; i < copperEquipmentList.size(); i++) {
                    if (Config.REPLACE_IRON_WITH_COPPER_TRADES.isTrue() && ItemStack.isSameItem(offer.getResult(),
                            new ItemStack(ironEquipmentList.get(i)))) {
                        replaceIronWithCopperTrades(offer, allOffers, i);
                    }
                    if (Config.REPLACE_DIAMOND_WITH_COPPER_TRADES.isTrue() && ItemStack.isSameItem(offer.getResult(),
                            new ItemStack(diamondEquipmentList.get(i)))) {
                        replaceIronWithCopperTrades(offer, allOffers, i);
                    }
                }
            }
        }
    }

    private static void ironToolReplacement(Block block, Level level, BlockPos blockPos) {
        if (block instanceof ChestBlock) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof ChestBlockEntity chestBlockEntity) {
                ResourceKey<@NotNull LootTable> lootTableResourceKey = chestBlockEntity.getLootTable();

                if (lootTableResourceKey == null) {
                    return;
                }
                List<String> paths = List.of("toolsmith", "weaponsmith", "abandoned_mineshaft", "buried_treasure", "shipwreck_map",
                        "shipwreck_supply", "shipwreck_treasure", "underwater_ruin_big", "underwater_ruin_small");

                var path = lootTableResourceKey.identifier().getPath();
                if (paths.contains(path)) {
                    copperToIronChestConversions.forEach((copperItem, ironItem) ->
                            replaceItemFromChest(chestBlockEntity, ironItem, copperItem));
                }
            }
        }
    }

    private static void replaceItemFromChest(ChestBlockEntity chest, Item itemToRemove, Item itemToReplace) {
        for (int slot = 0; slot < chest.getContainerSize(); slot++) {
            ItemStack stack = chest.getItem(slot);

            if (stack.getItem() == itemToRemove) {
                chest.setItem(slot, new ItemStack(itemToReplace));
            }
        }
        chest.setChanged();
    }

    private static void replaceIronWithCopperTrades(MerchantOffer offer, MerchantOffers allOffers, int index) {
        List<EnchantmentInstance> enchantmentInstanceList = new ArrayList<>();

        for (var enchant : offer.getResult().getTagEnchantments().entrySet()) {
            Holder<@NotNull Enchantment> enchantment = enchant.getKey();
            EnchantmentInstance enchantmentInstance = new EnchantmentInstance(Holder.direct(enchantment.value()),
                    enchant.getIntValue());
            enchantmentInstanceList.add(enchantmentInstance);
        }

        ItemStack replacementTool = copperEquipmentList.get(index)
                .applyEnchantments(copperEquipmentList.get(index).getDefaultInstance(), enchantmentInstanceList);

        MerchantOffer newOffer = new MerchantOffer(
                offer.getItemCostA(),
                offer.getItemCostB(),
                replacementTool,
                offer.getMaxUses(),
                offer.getXp(),
                offer.getPriceMultiplier()
        );
        allOffers.set(allOffers.indexOf(offer), newOffer);
    }

}
