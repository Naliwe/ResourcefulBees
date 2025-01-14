package com.teamresourceful.resourcefulbees.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamresourceful.resourcefulbees.ResourcefulBees;
import com.teamresourceful.resourcefulbees.api.IBeeRegistry;
import com.teamresourceful.resourcefulbees.api.beedata.outputs.ItemOutput;
import com.teamresourceful.resourcefulbees.api.beedata.CustomBeeData;
import com.teamresourceful.resourcefulbees.api.beedata.MutationData;
import com.teamresourceful.resourcefulbees.compat.jei.ingredients.EntityIngredient;
import com.teamresourceful.resourcefulbees.registry.BeeRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BlockToItem extends BaseCategory<BlockToItem.Recipe> {

    public static final ResourceLocation GUI_BACK = new ResourceLocation(ResourcefulBees.MOD_ID, "textures/gui/jei/beemutation.png");
    public static final ResourceLocation ID = new ResourceLocation(ResourcefulBees.MOD_ID, "block_to_item_mutation");
    private static final IBeeRegistry BEE_REGISTRY = BeeRegistry.getRegistry();

    public BlockToItem(IGuiHelper guiHelper) {
        super(guiHelper, ID,
                I18n.get("gui.resourcefulbees.jei.category.block_to_item_mutation"),
                guiHelper.drawableBuilder(GUI_BACK, -12, 0, 99, 75).addPadding(0, 0, 0, 0).build(),
                guiHelper.createDrawable(ICONS, 0, 0, 16, 16),
                BlockToItem.Recipe.class);
    }

    public static List<Recipe> getMutationRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        BEE_REGISTRY.getBees().values().forEach((beeData -> {
            MutationData mutationData = beeData.getMutationData();
            if (mutationData.hasMutation() && !mutationData.getBlockMutations().isEmpty()) {
                mutationData.getItemMutations()
                        .forEach((block, collection) ->  collection
                            .forEach(itemOutput -> recipes.add(new Recipe(block, itemOutput, itemOutput.getChance(), RecipeUtils.getEffectiveWeight(collection, itemOutput.getWeight()), beeData)))
                        );
            }
        }));
        return recipes;
    }

    @Override
    public void setIngredients(BlockToItem.@NotNull Recipe recipe, @NotNull IIngredients ingredients) {
        RecipeUtils.setBlockInput(ingredients, null, recipe.blockInput);

        ItemStack itemStack = new ItemStack(recipe.itemOutput.getItem());
        ingredients.setOutput(VanillaTypes.ITEM, itemStack);
        ingredients.setInput(JEICompat.ENTITY_INGREDIENT, new EntityIngredient(recipe.beeType, -45.0f));
    }

    @Override
    public @NotNull List<Component> getTooltipStrings(Recipe recipe, double mouseX, double mouseY) {
        List<Component> list = RecipeUtils.getTooltipStrings(mouseX, mouseY, recipe.chance);
        return list.isEmpty() ? super.getTooltipStrings(recipe, mouseX, mouseY) : list;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayout iRecipeLayout, BlockToItem.@NotNull Recipe recipe, @NotNull IIngredients ingredients) {
        RecipeUtils.setGuiItemStacksGroup(iRecipeLayout, ingredients);

        IGuiIngredientGroup<EntityIngredient> ingredientStacks = iRecipeLayout.getIngredientsGroup(JEICompat.ENTITY_INGREDIENT);
        ingredientStacks.init(0, true, 16, 10);
        ingredientStacks.set(0, ingredients.getInputs(JEICompat.ENTITY_INGREDIENT).get(0));
    }

    @Override
    public void draw(Recipe recipe, @NotNull PoseStack stack, double mouseX, double mouseY) {
        RecipeUtils.drawMutationScreen(stack, this.beeHive, this.info, recipe.weight, recipe.chance);
    }

    protected static class Recipe {
        private final @Nullable Block blockInput;
        private final @NotNull ItemOutput itemOutput;
        private final double chance;
        private final double weight;
        private final @NotNull CustomBeeData beeType;

        public Recipe(@Nullable Block blockInput, @NotNull ItemOutput itemOutput, double chance, double weight, @NotNull CustomBeeData beeType) {
            this.blockInput = blockInput;
            this.itemOutput = itemOutput;
            this.chance = chance;
            this.weight = weight;
            this.beeType = beeType;
        }
    }
}
