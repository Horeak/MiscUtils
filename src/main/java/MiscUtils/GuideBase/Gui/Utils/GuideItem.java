package MiscUtils.GuideBase.Gui.Utils;

import MiscUtils.Render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuideItem extends GuiButton {

    public ItemStack stack;

    public GuideItem(int id, int x, int y, ItemStack stack) {
        super(id, x, y, 16, 16, "");
        this.stack = stack;
    }

    public void drawButton(Minecraft mc, int x, int y)
    {

        this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
        this.mouseDragged(mc, x, y);

        GL11.glPushMatrix();

        if(stack != null)
            RenderHelper.drawItemStack(mc.fontRenderer, stack, xPosition, yPosition);


        GL11.glPopMatrix();


    }

    public void func_146113_a(SoundHandler p_146113_1_)
    {
    }
}
