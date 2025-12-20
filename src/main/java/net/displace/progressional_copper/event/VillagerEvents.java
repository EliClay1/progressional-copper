package net.displace.progressional_copper.event;

import net.displace.progressional_copper.Config;
import net.displace.progressional_copper.ProgressionalCopper;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = ProgressionalCopper.MOD_ID)
public class VillagerEvents {
    public static final List<Item> copperEquipmentList = List.of(Items.COPPER_PICKAXE, Items.COPPER_AXE, Items.COPPER_SHOVEL,
            Items.COPPER_SWORD, Items.COPPER_HOE, Items.COPPER_HELMET, Items.COPPER_CHESTPLATE, Items.COPPER_LEGGINGS, Items.COPPER_BOOTS);
    public static final List<Item> ironEquipmentList = List.of(Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_SHOVEL,
            Items.IRON_SWORD, Items.IRON_HOE, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS);
    public static final List<Item> diamondEquipmentList = List.of(Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_SHOVEL,
            Items.DIAMOND_SWORD, Items.DIAMOND_HOE, Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS);

    @SubscribeEvent
    public static void onVillagerGuiOpen(PlayerInteractEvent.EntityInteract event) {

        // prevents clientSide crash
        if (event.getLevel().isClientSide()) {
            return;
        }

        if (event.getTarget() instanceof Villager villager) {
            var allOffers = villager.getOffers();
            for (MerchantOffer offer : allOffers) {

                for (int i = 0; i < copperEquipmentList.size(); i++) {
                    if (ItemStack.isSameItem(offer.getResult(), ironEquipmentList.get(i).getDefaultInstance())) {
                        replaceWithCopper(offer, allOffers, i);
                    }
                    if (Config.REMOVE_DIAMOND_TRADES.isFalse() && ItemStack.isSameItem(offer.getResult(), diamondEquipmentList.get(i).getDefaultInstance())) {
                        // TODO - maybe make this have some kind of randomization feature?
                        replaceWithCopper(offer, allOffers, i);
                    }
                }
            }
        }
    }

    private static void replaceWithCopper(MerchantOffer offer, MerchantOffers allOffers, int index) {
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
