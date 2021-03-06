package cofh.thermal.expansion.plugins.crt.machine;

import cofh.thermal.core.plugins.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.core.plugins.crt.base.CRTRecipe;
import cofh.thermal.expansion.init.TExpRecipeTypes;
import cofh.thermal.expansion.util.recipes.machine.PressRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.thermal.Press")
public class CRTPressManager implements IRecipeManager {

    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack[] outputs, IFluidStack outputFluid, IIngredient[] ingredients, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CRTRecipe crtRecipe = new CRTRecipe(resourceLocation).energy(energy).input(ingredients).output(outputs).output(outputFluid);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, crtRecipe.recipe(PressRecipe::new), ""));
    }

    @Override
    public IRecipeType<PressRecipe> getRecipeType() {

        return TExpRecipeTypes.RECIPE_PRESS;
    }

    @Override
    public void removeRecipe(IItemStack output) {

        removeRecipe(new IItemStack[]{output}, new IFluidStack[0]);
    }

    @ZenCodeType.Method
    public void removeRecipe(IItemStack[] itemOutputs, IFluidStack[] fluidOutputs) {

        CraftTweakerAPI.apply(new ActionRemoveThermalRecipeByOutput(this, itemOutputs, fluidOutputs));
    }

}
