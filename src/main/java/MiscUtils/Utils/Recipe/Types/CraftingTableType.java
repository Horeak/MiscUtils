package MiscUtils.Utils.Recipe.Types;

import MiscUtils.GuideBase.Gui.Utils.GuideItem;
import MiscUtils.GuideBase.Utils.GuideRecipeTypeRender;
import MiscUtils.Utils.StackUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;

public class CraftingTableType extends GuideRecipeTypeRender{


    @Override
    public String GetName() {
        return "container.crafting";
    }

    @Override
    public int GetRenderXSize() {
        return 124;
    }

    @Override
    public int GetRenderYSize() {
        return 62;
    }

    @Override
    public int GetRenderPositionX() {
        return 25;
    }

    @Override
    public int GetRenderPositionY() {
        return 12;
    }

    @Override
    public ResourceLocation GetRenderTexture() {
        return new ResourceLocation("textures/gui/container/crafting_table.png");
    }

    @Override
    public ItemStack[] GetRequiredItemsFor(ItemStack stack, int at) {
        ItemStack[] stacks = new ItemStack[9];

        int j = 0;

        for(Object r : CraftingManager.getInstance().getRecipeList()){
            if(r instanceof IRecipe){
                IRecipe re = (IRecipe)r;

                if(StackUtils.AreStacksEqualIgnoreDamage(re.getRecipeOutput(), stack)){

                    if(j == at) {

                        if (re instanceof ShapelessRecipes) {
                            for (int i = 0; i < ((ShapelessRecipes) re).recipeItems.size(); i++) {
                                Object g = ((ShapelessRecipes) re).recipeItems.get(i);
                                stacks[i] = StackUtils.GetObject(g);
                            }

                        } else if (re instanceof ShapedRecipes) {
                            for (int i = 0; i < ((ShapedRecipes) re).recipeItems.length; i++) {
                                Object g = ((ShapedRecipes) re).recipeItems[i];
                                stacks[i] = StackUtils.GetObject(g);
                            }


                        } else if (re instanceof ShapedOreRecipe) {
                            ShapedOreRecipe res = (ShapedOreRecipe)re;

                            for (int i = 0; i < res.getInput().length; i++) {
                                Object g = res.getInput()[i];
                                stacks[i] = StackUtils.GetObject(g);
                            }

                        } else if (re instanceof ShapelessOreRecipe) {
                            ShapelessOreRecipe res = (ShapelessOreRecipe)re;

                            for (int i = 0; i < res.getInput().size(); i++) {
                                Object g = res.getInput().get(i);
                                stacks[i] = StackUtils.GetObject(g);
                            }

                        }

                    }

                    j += 1;

                }
            }

        }


        return stacks;
    }

    @Override
    public boolean ContainsRecipeFor(ItemStack stack) {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();

        for(ItemStack st : GetRequiredItemsFor(stack, 0)){
            if(st != null)
                list.add(st);

        }

        return list.size() > 0;
    }

    @Override
    public int GetRecipesAmountFor(ItemStack stack) {
        int i = 0;

        for(Object r : CraftingManager.getInstance().getRecipeList()){
            if(r instanceof IRecipe) {
                IRecipe res = (IRecipe) r;

                if(StackUtils.AreStacksEqualIgnoreDamage(res.getRecipeOutput(), stack)){
                    i += 1;
                }

            }
        }


        return i;
    }

    @Override
    public ArrayList<GuideItem> AddItemsFor(int PosX, int PosY, ArrayList<GuideItem> ListToAddTo, ItemStack stack, int At) {
       ItemStack[] stacks = GetRequiredItemsFor(stack, At);
        ItemStack render = stack.copy();

        for(int y = 0; y < 3; y++){
            for(int x = 0; x < 3; x++){
                ListToAddTo.add(new GuideItem(0, PosX + 5 + (x * 18), PosY + 5 + (y * 18), stacks[x+y * 3]));
            }
        }


        int h = 0;
        //Get the stack size of the output...
         for(Object r : CraftingManager.getInstance().getRecipeList()) {
               if(r instanceof IRecipe){
                   IRecipe res = (IRecipe)r;

                   if(StackUtils.AreStacksEqualIgnoreDamage(render, res.getRecipeOutput())){

                      if(h == At){
                         render.stackSize = res.getRecipeOutput().stackSize;
                         render.setItemDamage(res.getRecipeOutput().getItemDamage());

                      }

                       h += 1;
                   }
               }
         }


        ListToAddTo.add(new GuideItem(0, PosX + 99, PosY + 23, render));


        return ListToAddTo;
    }

}
