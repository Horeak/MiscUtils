package MiscUtils.GuideBase.Gui.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuideObjectButton extends GuiButton {
    public ItemStack stack = null;


    //TODO Change ItemStack to Object to allow usage for text-only pages
    public GuideObjectButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, ItemStack gd) {
        super(p_i1020_1_, p_i1020_2_, p_i1020_3_, gd.getDisplayName().length() * 5, 8, "");
        stack = gd;

    }

    public void drawButton(Minecraft mc, int x, int y)
    {

        GL11.glPushMatrix();
        this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;


        this.mouseDragged(mc, x, y);
        int mode = 0;

        if (this.field_146123_n)
        {
            mode = 1;
        }


        if(stack != null) {

            Color c;

            if(mode == 1){
                c = new Color(6,0, 185);
            }else{
                c = new Color(144, 144, 144);
            }

            mc.fontRenderer.drawString(stack.getDisplayName(), xPosition, yPosition, c.getRGB(), false);
        }
        GL11.glPopMatrix();
    }

}
