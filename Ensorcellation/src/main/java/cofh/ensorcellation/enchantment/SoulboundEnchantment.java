package cofh.ensorcellation.enchantment;

import cofh.core.enchantment.EnchantmentCoFH;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;

public class SoulboundEnchantment extends EnchantmentCoFH {

    public static boolean permanent = true;

    public SoulboundEnchantment() {

        super(Rarity.UNCOMMON, EnchantmentType.VANISHABLE, EquipmentSlotType.values());
        maxLevel = 3;
    }

    @Override
    public int getMinEnchantability(int level) {

        return 1 + (level - 1) * 5;
    }

    @Override
    protected int maxDelegate(int level) {

        return getMinEnchantability(level) + 50;
    }

    @Override
    public int getMaxLevel() {

        return permanent ? 1 : maxLevel;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {

        return super.canApplyTogether(ench) && ench != Enchantments.VANISHING_CURSE;
    }

}
