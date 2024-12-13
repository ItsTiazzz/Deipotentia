package net.tywrapstudios.deipotentia.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tywrapstudios.deipotentia.Deipotentia;

public class DTags {
    public enum Items {
        SICKLES("sickles"),
        NON_STURDY("non_sturdy"),
        REPULSING("repulsing"),;
        private final TagKey<Item> tagKey;

        Items(String name) {
            this.tagKey = of(name);
        }

        public TagKey<Item> get() { return tagKey; }

        private static TagKey<Item> of(String name) {
            return TagKey.of(Registry.ITEM.getKey(), new Identifier(Deipotentia.MOD_ID, name));
        }
    }

    public enum Blocks {
        NON_STURDY("non_sturdy");
        private final TagKey<Block> tagKey;

        Blocks(String name) {
            this.tagKey = of(name);
        }

        public TagKey<Block> get() { return tagKey; }

        private static TagKey<Block> of(String name) {
            return TagKey.of(Registry.BLOCK.getKey(), new Identifier(Deipotentia.MOD_ID, name));
        }
    }
}
