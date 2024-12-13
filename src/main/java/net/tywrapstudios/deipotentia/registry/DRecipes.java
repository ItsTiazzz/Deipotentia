package net.tywrapstudios.deipotentia.registry;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.recipe.HephaestusForgeRecipe;

public class DRecipes {
    public static RecipeSerializer<HephaestusForgeRecipe> HEPHAESTUS_SERIALIZER;
    public static RecipeType<HephaestusForgeRecipe> HEPHAESTUS_TYPE;

    public static void register() {
        HEPHAESTUS_SERIALIZER = registerSerializer(HephaestusForgeRecipe.Serializer.ID, HephaestusForgeRecipe.Serializer.INSTANCE);
        HEPHAESTUS_TYPE = registerType(HephaestusForgeRecipe.Type.ID, HephaestusForgeRecipe.Type.INSTANCE);
    }

    private static <T extends Recipe<?>> RecipeSerializer<T> registerSerializer(String id, RecipeSerializer<T> serInstance) {
        return Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Deipotentia.MOD_ID, id),
                serInstance);
    }

    private static <T extends Recipe<?>> RecipeType<T> registerType(String id, RecipeType<T> typeInstance) {
        return Registry.register(Registry.RECIPE_TYPE, new Identifier(Deipotentia.MOD_ID, id),
                typeInstance);
    }
}
