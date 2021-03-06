package cofh.core.event;

import cofh.core.init.CoreConfig;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;

import static cofh.core.util.constants.Constants.ID_COFH_CORE;
import static cofh.core.util.constants.NBTTags.TAG_STORED_ENCHANTMENTS;
import static cofh.core.util.helpers.StringHelper.*;
import static net.minecraft.util.text.TextFormatting.DARK_GRAY;
import static net.minecraft.util.text.TextFormatting.GRAY;
import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ID_COFH_CORE)
public class CoreClientEvents {

    private CoreClientEvents() {

    }

    @SubscribeEvent
    public static void handleItemTooltipEvent(ItemTooltipEvent event) {

        List<ITextComponent> tooltip = event.getToolTip();
        if (tooltip.isEmpty()) {
            return;
        }
        ItemStack stack = event.getItemStack();
        if (CoreConfig.enableItemDescriptions) {
            String infoKey = stack.getItem().getTranslationKey(stack) + ".desc";
            if (canLocalize(infoKey)) {
                event.getToolTip().add(1, getInfoTextComponent(infoKey));
            }
        }
        if (CoreConfig.enableEnchantmentDescriptions) {
            if (stack.getTag() != null) {
                ListNBT list = stack.getTag().getList(TAG_STORED_ENCHANTMENTS, TAG_COMPOUND);
                if (list.size() == 1) {
                    Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryCreate(list.getCompound(0).getString("id")));
                    if (ench != null && ench.getRegistryName() != null) {
                        String enchKey = ench.getName() + ".desc";
                        if (canLocalize(enchKey)) {
                            event.getToolTip().add(getInfoTextComponent(enchKey));
                        }
                    }
                }
            }
        }
        if (CoreConfig.enableItemTags && event.getFlags().isAdvanced()) {
            Item item = event.getItemStack().getItem();

            Set<ResourceLocation> blockTags = Block.getBlockFromItem(item).getTags();
            Set<ResourceLocation> itemTags = item.getTags();

            if (!blockTags.isEmpty() || !itemTags.isEmpty()) {
                List<ITextComponent> lines = event.getToolTip();
                if (Screen.hasControlDown()) {
                    if (!blockTags.isEmpty()) {
                        lines.add(getTextComponent("info.cofh.block_tags").mergeStyle(GRAY));
                        blockTags.stream()
                                .map(Object::toString)
                                .map(s -> "  " + s)
                                .map(t -> getTextComponent(t).mergeStyle(DARK_GRAY))
                                .forEach(lines::add);
                    }

                    if (!itemTags.isEmpty()) {
                        lines.add(getTextComponent("info.cofh.item_tags").mergeStyle(GRAY));
                        itemTags.stream()
                                .map(Object::toString)
                                .map(s -> "  " + s)
                                .map(t -> getTextComponent(t).mergeStyle(DARK_GRAY))
                                .forEach(lines::add);
                    }
                } else {
                    lines.add(getTextComponent("info.cofh.hold_ctrl_for_tags").mergeStyle(GRAY));
                }
            }
        }
    }

}
