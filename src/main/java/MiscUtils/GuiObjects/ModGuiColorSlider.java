package MiscUtils.GuiObjects;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ModGuiColorSlider extends ModGuiSlider {
    public Color c1, c2;

    public ModGuiColorSlider(int id, int x, int y, int xSize, int ySize,Color c1, Color c2) {
        super(id, x, y, xSize, ySize, "");
        this.c1 = c1;
        this.c2 = c2;

        RenderTab = false;
        RenderLabel = false;
        RenderBackground = false;

    }

    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
    {
          super.drawButton(p_146112_1_, p_146112_2_, p_146112_3_);

        GL11.glPushMatrix();

        drawGradientRect(xPosition, yPosition, xPosition + xSize, yPosition + ySize, c1.getRGB(), c2.getRGB());

        int xPos = xPosition + (int) (this.sliderValue * (float) (this.width - 8));
        int yPos = yPosition;

        Color cc2 = new Color(0,0,0);
        Color cc1 = new Color(57, 57, 57);

        drawRect(xPos, yPos, xPos + 8, yPos + 20, cc2.getRGB());
        drawRect(xPos + 2, yPos + 2, xPos + 6, yPos + 19, cc1.getRGB());

        GL11.glPopMatrix();

    }
}
