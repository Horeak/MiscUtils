package MiscUtils.GuiObjects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class ModGuiSlider extends GuiButton {

    public float sliderValue = 1.0F;
    public float sliderMaxValue = 1.0F;
    public boolean dragging = false;
    public String label;
    public int xSize, ySize;
    //150, 20

    public ModGuiSlider(int id, int x, int y, int xSize, int ySize, String label, float startingValue, float maxValue) {
            super(id, x, y, xSize, ySize, label);
            
            this.label = label;
            this.sliderValue = startingValue;
            this.sliderMaxValue = maxValue;
        this.xSize = xSize;
        this.ySize = ySize;
    }




    public int getHoverState(boolean par1) {
            return 0;
    }

    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
            if (this.visible) {
                    if (this.dragging) {
                            this.sliderValue = (float) (par2 - (this.xPosition + 4)) / (float) (this.width - 8);

                            if (this.sliderValue < 0.0F) {
                                    this.sliderValue = 0.0F;
                            }

                            if (this.sliderValue > 1.0F) {
                                    this.sliderValue = 1.0F;
                            }

                    }

                    this.displayString = label + ": " + (int) (sliderValue * sliderMaxValue);
                    
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)), this.yPosition, 0, 66, 4, 20);
                    this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
                    
            }
    }

    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
    	
    	
            if (super.mousePressed(par1Minecraft, par2, par3)) {
                    this.sliderValue = (float) (par2 - (this.xPosition + 4)) / (float) (this.width - 8);

                    if (this.sliderValue < 0.0F) {
                            this.sliderValue = 0.0F;
                    }

                    if (this.sliderValue > 1.0F) {
                            this.sliderValue = 1.0F;
                    }

                    if(!dragging)
                    this.dragging = true;
                    else
                    	dragging = false;
                    return true;
            } else {
                    return false;
            }
    }

    public void mouseReleased(int par1, int par2) {
            this.dragging = false;
    }
}