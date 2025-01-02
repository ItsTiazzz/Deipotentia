package net.tywrapstudios.deipotentia.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.tywrapstudios.deipotentia.component.DeipotentiaComponents;
import net.tywrapstudios.deipotentia.component.PlayerPostMortemComponent;
import net.tywrapstudios.deipotentia.registry.DRegistry;
import net.tywrapstudios.deipotentia.util.NbtUtilities;

public class SoulBleacherItem extends Item {
    public SoulBleacherItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        PlayerPostMortemComponent component = DeipotentiaComponents.PLAYER_DEATH_COMPONENT.get(player);
        component.setDeathData(false, component.getIdentifier() + 1, player);

        if (world.isClient()) return TypedActionResult.pass(player.getStackInHand(hand));

        ItemStack bleacherStack = player.getStackInHand(hand);
        bleacherStack.decrement(1);
        return TypedActionResult.success(bleacherStack, world.isClient());
    }
}
