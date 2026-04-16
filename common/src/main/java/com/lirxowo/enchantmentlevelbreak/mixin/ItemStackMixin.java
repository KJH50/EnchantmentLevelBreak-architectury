package com.lirxowo.enchantmentlevelbreak.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import com.lirxowo.enchantmentlevelbreak.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Unique
    private static final ThreadLocal<Boolean> IS_PROCESSING = ThreadLocal.withInitial(() -> false);

    @Inject(method = "enchant", at = @At("HEAD"), cancellable = true)
    private void onEnchant(Holder<Enchantment> enchantment, int level, CallbackInfo ci) {
        if (IS_PROCESSING.get()) {
            return;
        }

        if (!ModConfig.getInstance().isAllowEnchantmentTableRestacking()) {
            return;
        }

        try {
            IS_PROCESSING.set(true);
            ItemStack stack = (ItemStack)(Object)this;
            if (!stack.isEmpty() && level > 0) {
                level = Math.min(level, ModConfig.getInstance().getMaxEnchantmentLevel());
                if (stack.is(Items.ENCHANTED_BOOK)) {
                    ItemEnchantments currentEnchantments = stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
                    ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(currentEnchantments);
                    int currentLevel = mutable.getLevel(enchantment);
                    int newLevel = calculateNewLevel(currentLevel, level);
                    mutable.set(enchantment, newLevel);
                    stack.set(DataComponents.STORED_ENCHANTMENTS, mutable.toImmutable());
                } else {
                    ItemEnchantments currentEnchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
                    ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(currentEnchantments);
                    int currentLevel = mutable.getLevel(enchantment);
                    int newLevel = calculateNewLevel(currentLevel, level);
                    mutable.set(enchantment, newLevel);
                    stack.set(DataComponents.ENCHANTMENTS, mutable.toImmutable());
                }
                ci.cancel();
            }
        } finally {
            IS_PROCESSING.set(false);
        }
    }

    @Unique
    private int calculateNewLevel(int currentLevel, int newLevel) {
        if (ModConfig.getInstance().isAllowLevelStacking()) {
            return currentLevel + newLevel;
        } else if (ModConfig.getInstance().isAllowVanillaLevelStacking() && currentLevel == newLevel) {
            return currentLevel + 1;
        } else if (currentLevel > 0) {
            return currentLevel;
        }
        return newLevel;
    }

    @Inject(method = "isEnchantable", at = @At("HEAD"), cancellable = true)
    private void onIsEnchantable(CallbackInfoReturnable<Boolean> cir) {
        if (!ModConfig.getInstance().isAllowEnchantmentTableRestacking()) {
            return;
        }
        ItemStack stack = (ItemStack)(Object)this;
        cir.setReturnValue(!stack.isEmpty() && stack.getCount() == 1);
    }
}
