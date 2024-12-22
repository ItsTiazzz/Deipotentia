package net.tywrapstudios.deipotentia.registry;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.recipe.HephaestusForgeRecipe;

public class DRecipes {
    public static final RecipeSerializer<HephaestusForgeRecipe> HEPHAESTUS_SERIALIZER;
    public static final RecipeType<HephaestusForgeRecipe> HEPHAESTUS_TYPE;

    static {
        HEPHAESTUS_SERIALIZER = registerSerializer(HephaestusForgeRecipe.Serializer.ID, HephaestusForgeRecipe.Serializer.INSTANCE);
        HEPHAESTUS_TYPE = registerType(HephaestusForgeRecipe.Type.ID, HephaestusForgeRecipe.Type.INSTANCE);
    }

    public static void register() {}

    private static <T extends Recipe<?>> RecipeSerializer<T> registerSerializer(String id, RecipeSerializer<T> serInstance) {
        return Registry.register(Registries.RECIPE_SERIALIZER, Deipotentia.id(id),
                serInstance);
    }

    private static <T extends Recipe<?>> RecipeType<T> registerType(String id, RecipeType<T> typeInstance) {
        return Registry.register(Registries.RECIPE_TYPE, Deipotentia.id(id),
                typeInstance);
    }
}
