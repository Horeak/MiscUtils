package MiscUtils.GuiObjects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ModGuiSlider extends GuiButton {

    public float sliderValue = 0.0F;
    public String label;
    public int xSize, ySize;
    public boolean RenderTab = true, RenderBackground = true, RenderLabel = true;

    //TODO Improve class inorder to make it easier to implement. For example with custom render instead of the normal render
    public ModGuiSlider(int id, int x, int y, int xSize, int ySize, String label) {
        super(id, x, y, xSize, ySize, label);

        this.label = label;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {


        GL11.glPushMatrix();
        GL11.glColor4f(1F, 1F, 1F, 1F);

            if (Mouse.isButtonDown(0)) {
                if(mouseX >= xPosition && mouseX < xPosition + xSize && mouseY >= yPosition && mouseY < yPosition + ySize) {
                    //TODO Make it work correctly with the correct xSize and prevent it from going outside of its own size
                    sliderValue = MathHelper.clamp_float((float)(mouseX - (xPosition)) / (float)(width - 6), 0F, 1F);
                }
            }

        if(sliderValue > 1F)
            sliderValue = 1F;


        if (RenderBackground) {
            mc.getTextureManager().bindTexture(buttonTextures);

            drawTexturedModalRect(xPosition, yPosition, 0, 46, width / 2, height);
            drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46, width / 2, height);
        }


        if (RenderTab) {
            int xSize = 6;
            int x1 = xPosition + ((int) (sliderValue * (width - 8)));

            drawTexturedModalRect(x1, yPosition, 0, 66, xSize, height);
            drawTexturedModalRect(x1 + xSize, yPosition, 204 - xSize, 66, 8 - xSize, height);

        }


        if(RenderLabel) {
            drawCenteredString(mc.fontRenderer, label, xPosition + (xSize / 2), yPosition + (ySize / 4), 0xffffff);
        }

        GL11.glPopMatrix();

    }

}


