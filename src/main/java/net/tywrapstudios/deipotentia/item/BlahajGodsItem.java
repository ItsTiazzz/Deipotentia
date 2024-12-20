package net.tywrapstudios.deipotentia.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class BlahajGodsItem extends SwordItem {
    public BlahajGodsItem(Settings settings) {
        super(ToolMaterials.NETHERITE, 25, 40, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        stack.removeCustomName();
        stack.setCustomName(Text.translatable(this.getTranslationKey()).setStyle(Style.EMPTY.withColor(
                Formatting.BLUE).withItalic(false)));
        return stack.getName();
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
