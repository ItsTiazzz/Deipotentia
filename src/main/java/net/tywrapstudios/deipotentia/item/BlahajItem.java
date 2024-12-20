package net.tywrapstudios.deipotentia.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class BlahajItem extends BlockItem {
    public BlahajItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        stack.removeCustomName();
        stack.setCustomName(Text.translatable(this.getTranslationKey()).setStyle(Style.EMPTY.withColor(
                Formatting.BLUE).withItalic(false)));
        return stack.getName();
    }
}
