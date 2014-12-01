package MiscUtils.GuideBase.Gui.Utils;

import MiscUtils.GuideBase.Utils.GuideTab;
import MiscUtils.MiscUtilsMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuideTabSelectionButton extends GuiButton {
    public GuideTab instance = null;
    boolean Selected;
    public GuideTabSelectionButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, GuideTab gd, boolean Selected) {
        super(p_i1020_1_, p_i1020_2_, p_i1020_3_, 30, 28, "");
        instance = gd;
        this.Selected = Selected;

    }



    public GuideTab GetInstance(){
        return instance;
    }

    public ResourceLocation IconTexutre = new ResourceLocation(MiscUtilsMain.Id.toLowerCase(), "textures/gui/GuideGuiIcons.png");
    protected static RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();

    public void drawButton(Minecraft mc, int x, int y)
    {

        GL11.glPushMatrix();
        GL11.glColor4f(1F, 1F, 1F, 1F);
            mc.getTextureManager().bindTexture(IconTexutre);
            this.hovered = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;


            this.mouseDragged(mc, x, y);
            int mode = 0;

             if (this.hovered)
            {
                mode = 1;
            }

        int xx = xPosition;
        int yy = yPosition;


        if(Selected) {
            xx += 3;
            mode = 1;

            drawTexturedModalRect(xx - 3, yy, 0,58 ,3, 28);
        }

            if(mode == 0){
                drawTexturedModalRect(xx, yy, 0,87 , 30, 28);
            }else{
                drawTexturedModalRect(xx, yy, 0,58 ,30, 28);

            }


        if(instance.stack != null)
                MiscUtils.Render.RenderHelper.drawItemStack(mc.fontRendererObj, instance.stack, xx + 7, yy + 5);

        GL11.glPopMatrix();
    }
}
