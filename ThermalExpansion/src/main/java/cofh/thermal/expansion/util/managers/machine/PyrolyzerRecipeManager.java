package cofh.thermal.expansion.util.managers.machine;

import cofh.core.inventory.FalseIInventory;
import cofh.thermal.core.util.managers.SingleItemRecipeManager;
import cofh.thermal.core.util.recipes.ThermalRecipe;
import cofh.thermal.expansion.init.TExpRecipeTypes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class PyrolyzerRecipeManager extends SingleItemRecipeManager {

    private static final PyrolyzerRecipeManager INSTANCE = new PyrolyzerRecipeManager();
    protected static final int DEFAULT_ENERGY = 2000;

    public static PyrolyzerRecipeManager instance() {

        return INSTANCE;
    }

    private PyrolyzerRecipeManager() {

        super(DEFAULT_ENERGY, 4, 1);
    }

    // region IManager
    @Override
    public void config() {

    }

    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        Map<ResourceLocation, IRecipe<FalseIInventory>> recipes = recipeManager.getRecipes(TExpRecipeTypes.RECIPE_PYROLYZER);
        for (Map.Entry<ResourceLocation, IRecipe<FalseIInventory>> entry : recipes.entrySet()) {
            addRecipe((ThermalRecipe) entry.getValue());
        }
    }
    // endregion
}
