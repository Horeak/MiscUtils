package MiscUtils.GuideBase.Utils;

import MiscUtils.GuideBase.Gui.Utils.GuideItem;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public abstract class GuideRecipeTypeRender {

    public abstract String GetName();

    public abstract int GetRenderXSize();
    public abstract int GetRenderYSize();

    public abstract int GetRenderPositionX();
    public abstract int GetRenderPositionY();

    public abstract ResourceLocation GetRenderTexture();

    public abstract ItemStack[] GetRequiredItemsFor(ItemStack stack, int At);
    public abstract boolean ContainsRecipeFor(ItemStack stack);

    public abstract int GetRecipesAmountFor(ItemStack stack);

    public void RenderExtras(GuiScreen gui, int posX, int posY){}

    //TODO Somehow make a default way of getting the stack size of the recipe output
    public abstract ArrayList<GuideItem> AddItemsFor(int PosX, int PosY, ArrayList<GuideItem> ListToAddTo, ItemStack stack, int At);

}
