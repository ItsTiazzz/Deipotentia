package net.tywrapstudios.deipotentia.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.Deipotentia;

public class DTags {
    public enum Items {
        SICKLES("sickles"),
        NON_STURDY("non_sturdy"),
        REPULSING("repulsing"),
        DISPLAYABLE(new Identifier("arsenal", "displayable")),
        BIG_WEAPONS(new Identifier("arsenal", "big_weapons")),
        RANGED_WEAPONS(new Identifier("arsenal", "ranged_weapons")),
        PLUSH("plush"),
        RETAIN_DURABILITY(new Identifier("enchancement", "retains_durability")),;

        private final TagKey<Item> tagKey;

        Items(String name) {
            this.tagKey = of(Deipotentia.id(name));
        }

        Items(Identifier id) {
            this.tagKey = of(id);
        }

        public TagKey<Item> get() { return tagKey; }

        private static TagKey<Item> of(Identifier id) {
            return TagKey.of(Registries.ITEM.getKey(), id);
        }
    }

    public enum Blocks {
        NON_STURDY("non_sturdy");

        private final TagKey<Block> tagKey;

        Blocks(String name) {
            this.tagKey = of(Deipotentia.id(name));
        }

        public TagKey<Block> get() { return tagKey; }

        private static TagKey<Block> of(Identifier id) {
            return TagKey.of(Registries.BLOCK.getKey(), id);
        }
    }
}
