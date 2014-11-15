package MiscUtils.GuideBase.Gui.Utils;

import MiscUtils.MiscUtilsMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuideRecipeButton  extends GuiButton {

    boolean Toggle = false;

    public GuideRecipeButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, boolean t) {
        super(p_i1020_1_, p_i1020_2_, p_i1020_3_, 18, 18, "");
        Toggle = t;

    }

    public ResourceLocation IconTexutre = new ResourceLocation(MiscUtilsMain.Id.toLowerCase(), "textures/gui/GuideGuiIcons.png");
    public void drawButton(Minecraft mc, int x, int y)
    {

        GL11.glPushMatrix();
        GL11.glColor4f(1F, 1F, 1F, 1F);

        mc.getTextureManager().bindTexture(IconTexutre);
        this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
        this.mouseDragged(mc, x, y);

        Color c = new Color(119, 119, 119);
        if(Toggle){
            c = new Color(179, 179, 179);
        }


            drawRect(xPosition, yPosition, xPosition + 18, yPosition + 18, c.getRGB());
            drawTexturedModalRect(xPosition + 1, yPosition + 1, 61,0 , 16, 16);

        GL11.glPopMatrix();


    }

    public void func_146113_a(SoundHandler p_146113_1_)
    {
    }

}