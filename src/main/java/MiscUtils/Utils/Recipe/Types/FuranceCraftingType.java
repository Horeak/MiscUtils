package MiscUtils.Utils.Recipe.Types;

import MiscUtils.GuideBase.Gui.Utils.GuideItem;
import MiscUtils.GuideBase.Utils.GuideRecipeTypeRender;
import MiscUtils.Utils.StackUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Map;

public class FuranceCraftingType extends GuideRecipeTypeRender {
    @Override
    public String GetName() {
        return "container.furnace";
    }

    @Override
    public int GetRenderXSize() {
        return 90;
    }

    @Override
    public int GetRenderYSize() {
        return 62;
    }

    @Override
    public int GetRenderPositionX() {
        return 51;
    }

    @Override
    public int GetRenderPositionY() {
        return 12;
    }

    @Override
    public ResourceLocation GetRenderTexture() {
        return new ResourceLocation("textures/gui/container/furnace.png");
    }

    @Override
    public ItemStack[] GetRequiredItemsFor(ItemStack stack, int At) {
        ItemStack[] stacks = new ItemStack[1];

        int j = 0;

        Map<ItemStack, ItemStack> recipes = (Map<ItemStack, ItemStack>) FurnaceRecipes.smelting().getSmeltingList();
        for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
            if(StackUtils.AreStacksEqualIgnoreData(recipe.getValue(), stack)) {
                if(j == At) {
                    stacks[0] = recipe.getKey();
                }

                j += 1;
            }
        }




        return stacks;
    }

    @Override
    public boolean ContainsRecipeFor(ItemStack stack) {
        return GetRecipesAmountFor(stack) > 0;
    }

    @Override
    public int GetRecipesAmountFor(ItemStack stack) {
        int num = 0;

        Map<ItemStack, ItemStack> recipes = (Map<ItemStack, ItemStack>) FurnaceRecipes.smelting().getSmeltingList();
        for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
            if(StackUtils.AreStacksEqualIgnoreData(recipe.getValue(), stack)) {
               num += 1;
            }
        }

        return num;
    }

    @Override
    public ArrayList<GuideItem> AddItemsFor(int PosX, int PosY, ArrayList<GuideItem> ListToAddTo, ItemStack stack, int At) {
        ItemStack render = stack.copy();


        ListToAddTo.add(new GuideItem(0, PosX + 5, PosY + 5, GetRequiredItemsFor(stack, At)[0]));

        int h = 0;
        //Get the stack size of the output...
        for(Object r : FurnaceRecipes.smelting().getSmeltingList().values()) {
            ItemStack sta = StackUtils.GetObject(r);

            if (StackUtils.AreStacksEqualIgnoreData(render, sta)) {

                if (h == At) {
                    render.stackSize = sta.stackSize;
                    render.setItemDamage(sta.getItemDamage());
                }


                h += 1;
            }
        }



        ListToAddTo.add(new GuideItem(0, PosX + 65, PosY + 23, render));

        ListToAddTo.add(new GuideItem(0, PosX + 5, PosY + 41, new ItemStack(Items.coal)));


        return ListToAddTo;
    }

    double burn = 0, BurnMax = 24;

    public void RenderExtras(GuiScreen gui, int posX, int posY, ItemStack stack, int at){

        if(burn >= BurnMax)
            burn = 0;

        else
            burn += 0.02;

        gui.drawTexturedModalRect(posX + 56, posY + 55, 176, 0, 14, 14);
        gui.drawTexturedModalRect(posX + 79, posY + 52, 176, 14, (int)burn, 17);



    }
}
