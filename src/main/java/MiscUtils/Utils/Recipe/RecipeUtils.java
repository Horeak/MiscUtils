package MiscUtils.Utils.Recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;

public class RecipeUtils {

    //TODO Add a system where modders can register a recipe type to allow MiscUtils to get recipes from it

    //Only crafting table and furnace recipes!
    @Deprecated
    public static ArrayList<IRecipe> GetRecipes(ItemStack stack){
        ArrayList<IRecipe> res = new ArrayList<IRecipe>();


        for(Object r : CraftingManager.getInstance().getRecipeList()){
            if(r instanceof IRecipe) {
                IRecipe rec = (IRecipe) r;

                if(rec.getRecipeOutput() != null)
                if (rec.getRecipeOutput().getItem() == stack.getItem()) {
                    res.add(rec);
                }
            }
        }


        for(Object r : FurnaceRecipes.smelting().getSmeltingList().values()){
            if(r instanceof IRecipe) {
                IRecipe rec = (IRecipe) r;

                if(rec.getRecipeOutput() != null)
                if (rec.getRecipeOutput().getItem() == stack.getItem()) {
                    res.add(rec);
                }
            }
        }



        return res;
    }
}
