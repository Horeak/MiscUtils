package MiscUtils.Utils.Recipe;

import MiscUtils.GuideBase.Utils.GuideRecipeTypeRender;
import MiscUtils.Utils.Recipe.Types.CraftingTableType;
import MiscUtils.Utils.Recipe.Types.FuranceCraftingType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;

public class RecipeUtils {

    public static ArrayList<GuideRecipeTypeRender> RecipeTypeRenders = new ArrayList<GuideRecipeTypeRender>();

    public static void RegisterTypes(){

        RecipeTypeRenders.add(new CraftingTableType());
        RecipeTypeRenders.add(new FuranceCraftingType());

    }


    public static GuideRecipeTypeRender GetRecipeAt(ItemStack stack, int num){
        int g = 0;
        if(GetTotalRecipeAmountFor(stack) >= num){
            for(GuideRecipeTypeRender res : GetRecipesFor(stack)){

                if(g == num){
                    return res;
                }

                g += 1;

            }

        }

        return null;
    }


    public static ArrayList<GuideRecipeTypeRender> GetRecipesFor(ItemStack stack){
        ArrayList<GuideRecipeTypeRender> res = new ArrayList<GuideRecipeTypeRender>();

        for(GuideRecipeTypeRender red : RecipeTypeRenders){
            if(red.ContainsRecipeFor(stack)){
                res.add(red);
            }
        }


        return res;
    }

    public static int GetTotalRecipeAmountFor(ItemStack stack){
        int num = 0;

        for(GuideRecipeTypeRender res : RecipeTypeRenders){
            if(res.ContainsRecipeFor(stack))
                num += res.GetRecipesAmountFor(stack);
        }


        return num;
    }

    public static int GetTotalDifferentRecipeTypes(ItemStack stack){
        int num = 0;

        for(GuideRecipeTypeRender res : RecipeTypeRenders){
            if(res.ContainsRecipeFor(stack))
                num += 1;
        }


        return num;

    }



    @Deprecated
    public static ArrayList<IRecipe> GetCraftingRecipes(ItemStack stack){
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



        return res;
    }
}
