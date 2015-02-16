package MiscUtils.Render;

import MiscUtils.Utils.RayTracing;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.util.ArrayList;

public class RenderHelper {

    public static RenderItem itemRender = new RenderItem();


    @Deprecated
    public static void lightningFix(){
        int bright = 0xF0;
        int brightX = bright % 65536;
        int brightY = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
    }

    public static void RenderInfoTagOverTileEntity(TileEntity tile, ArrayList<String> InfoStrings, double x, double y, double z){
        MovingObjectPosition mop = RayTracing.instance().getTarget();
        RenderManager manager = RenderManager.instance;


        if(mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            if (mop.blockX == tile.xCoord && mop.blockY == tile.yCoord && mop.blockZ == tile.zCoord) {

                ForgeDirection sideHit = ForgeDirection.getOrientation(mop.sideHit);

                float q = 0.8F;
                float xOffset = (sideHit.offsetX * q), yOffset = (sideHit.offsetY * q), zOffset = (sideHit.offsetZ * q);


                GL11.glPushMatrix();
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                float f = 1.6F;
                float f1 = 0.016666668F * f;


                FontRenderer fontrenderer = manager.getFontRenderer();

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                Block block = tile.getWorldObj().getBlock(tile.xCoord, tile.yCoord, tile.zCoord);
                double g = (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY()) * 1.2;

                GL11.glPushMatrix();
                GL11.glTranslatef((float) x + 0.5F + xOffset, (float) y + (InfoStrings.size() * 0.21F) + yOffset + ((float)g / 2), (float) z + 0.5F + zOffset);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-manager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(manager.playerViewX, 1.0F, 0.0F, 0.0F);
                GL11.glScalef(-f1, -f1, f1);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
                GL11.glDepthMask(false);
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                Tessellator tessellator = Tessellator.instance;
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                tessellator.startDrawingQuads();

                int j = 0;

                for (int h = 0; h < InfoStrings.size(); h++) {
                    int i = fontrenderer.getStringWidth(InfoStrings.get(h)) / 2;

                    if (i > j)
                        j = i;

                }

                double Height = 8.0D * InfoStrings.size();

                tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                tessellator.addVertex((double) (-j - 1), -1.0D, 0.0D);
                tessellator.addVertex((double) (-j - 1), Height, 0.0D);
                tessellator.addVertex((double) (j + 1), Height, 0.0D);
                tessellator.addVertex((double) (j + 1), -1.0D, 0.0D);
                tessellator.draw();


                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glDepthMask(true);

                for (int h = 0; h < InfoStrings.size(); h++) {
                    int i = fontrenderer.getStringWidth(InfoStrings.get(h)) / 2;
                    fontrenderer.drawString(InfoStrings.get(h), -fontrenderer.getStringWidth(InfoStrings.get(h)) / 2, 0 + (h * 8), new Color(0x919191).getRGB());

                }

                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
                GL11.glPopMatrix();

            }
    }


    public static void RenderInventoryBlockWithColor(IItemRenderer.ItemRenderType renderType, ItemStack stack, Color color){

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();


        boolean mustundotranslate = false;
        switch (renderType) {
            case EQUIPPED:
            case EQUIPPED_FIRST_PERSON: {
                break;
            }
            case ENTITY:
            case INVENTORY: {

                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                mustundotranslate = true;
                break;
            }
            default:
                break;
        }


        Block bl = Block.getBlockFromItem(stack.getItem());


        IIcon icon = bl.getIcon(5, stack.getItemDamage());
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
         tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(1.0, 1.0, 1.0, (double)icon.getMinU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(1.0, 0.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV());

        icon = bl.getIcon(4, stack.getItemDamage());
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
         tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
        tessellator.addVertexWithUV(0.0, 0.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV(0.0, 1.0, 1.0, (double)icon.getMaxU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());


        icon = stack.getItem().getIconFromDamage(2);
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
         tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());


        icon = bl.getIcon(3, stack.getItemDamage());
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
         tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
        tessellator.addVertexWithUV(1.0, 0.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV(1.0, 1.0, 1.0, (double)icon.getMaxU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(0.0, 1.0, 1.0, (double)icon.getMinU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(0.0, 0.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV());

        icon = bl.getIcon(1, stack.getItemDamage());
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
         tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
        tessellator.addVertexWithUV(1.0, 1.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(0.0, 1.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV());

        icon = bl.getIcon(0, stack.getItemDamage());
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
        tessellator.addVertexWithUV(0.0, 0.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(1.0, 0.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV());

        tessellator.draw();

        if (mustundotranslate) GL11.glTranslatef(0.5F, 0.5F, 0.5F);

    }


    public static void drawItemStack(FontRenderer fontRendererObj, ItemStack stack, int x, int y)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if(!(stack.getItem() instanceof ItemBlock)){
            GL11.glPushMatrix();


            itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), stack, x, y);
            itemRender.renderItemOverlayIntoGUI(fontRendererObj, mc.getTextureManager(), stack, x, y);

            if(!stack.hasEffect(stack.getItem().getRenderPasses(stack.getItemDamage())))
                GL11.glEnable(GL11.GL_BLEND);



            GL11.glColor4f(1F, 1F, 1F, 1F);
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

            GL11.glPopMatrix();

        }else if(stack.getItem() instanceof ItemBlock){

            //Code based drawItemStack and drawScreen in GuiContainer.java
            GL11.glPushMatrix();
            GL11.glColor4f(1F, 1F, 1F, 1F);

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);

            net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);

            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F / 1.0F, 240F / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), stack, x, y);
            itemRender.renderItemOverlayIntoGUI(fontRendererObj, mc.getTextureManager(), stack, x, y, null);

            GL11.glEnable(GL11.GL_LIGHTING);
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

            GL11.glColor4f(1F, 1F, 1F, 1F);
            GL11.glPopMatrix();
        }

    }


    /**
     * Code taken from: https://github.com/iChun/iChunUtil/blob/master/src/main/java/ichun/client/render/RendererHelper.java
     * @author Ichun
     */



    public static void setColorFromInt(int color) {
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        GL11.glColor4f(r, g, b, 1.0F);
    }

    public static void drawTextureOnScreen(ResourceLocation resource, double posX, double posY, double width, double height, double zLevel)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(posX , posY + height , zLevel, 0.0D, 1.0D);
        tessellator.addVertexWithUV(posX + width, posY + height , zLevel, 1.0D, 1.0D);
        tessellator.addVertexWithUV(posX + width, posY , zLevel, 1.0D, 0.0D);
        tessellator.addVertexWithUV(posX , posY , zLevel, 0.0D, 0.0D);
        tessellator.draw();
    }

    public static void drawColourOnScreen(int colour, int alpha, double posX, double posY, double width, double height, double zLevel)
    {
        int r = (colour >> 16 & 0xff);
        int g = (colour >> 8 & 0xff);
        int b = (colour & 0xff);
        drawColourOnScreen(r, g, b, alpha, posX, posY, width, height, zLevel);
    }

    public static void drawColourOnScreen(int r, int g, int b, int alpha, double posX, double posY, double width, double height, double zLevel)
    {
        if(width <= 0 || height <= 0)
        {
            return;
        }
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(r, g, b, alpha);
        tessellator.addVertex(posX , posY + height , zLevel);
        tessellator.addVertex(posX + width, posY + height , zLevel);
        tessellator.addVertex(posX + width, posY , zLevel);
        tessellator.addVertex(posX , posY , zLevel);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void startGlScissor(int x, int y, int width, int height)//From top left corner, like how Minecraft guis are. Don't forget to call endGlScissor after rendering
    {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution reso = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        double scaleW = (double)mc.displayWidth / reso.getScaledWidth_double();
        double scaleH = (double)mc.displayHeight / reso.getScaledHeight_double();
        if(width <= 0 || height <= 0)
        {
            return;
        }
        if(x < 0)
        {
            x = 0;
        }
        if(y < 0)
        {
            y = 0;
        }
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int)Math.floor((double)x * scaleW), (int)Math.floor((double)mc.displayHeight - ((double)(y + height) * scaleH)), (int)Math.floor((double)(x + width) * scaleW) - (int)Math.floor((double)x * scaleW), (int)Math.floor((double)mc.displayHeight - ((double)y * scaleH)) - (int)Math.floor((double)mc.displayHeight - ((double)(y + height) * scaleH))); //starts from lower left corner (minecraft starts from upper left)
    }
    public static void endGlScissor()
    {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    //TODO "Disabeld StencilBits by default, to prevent issues with intel cards. You must now opt-in to enabeling stencil bits by suppling the -Dforge.forceDisplayStencil=true flag."
    public static void renderTestStencil()
    {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution reso1 = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glColorMask(false, false, false, false);
        GL11.glDepthMask(false);
        GL11.glStencilFunc(GL11.GL_NEVER, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_KEEP, GL11.GL_KEEP);
        GL11.glStencilMask(0xFF);
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        RenderHelper.drawColourOnScreen(0xffffff, 255, 0, 0, 60, 60, 0);
        GL11.glColorMask(true, true, true, true);
        GL11.glDepthMask(true);
        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 0, 0xFF);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
        RenderHelper.drawColourOnScreen(0xffffff, 255, 0, 0, reso1.getScaledWidth_double(), reso1.getScaledHeight_double(), 0);
        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }

    public static void renderTestSciccor()
    {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution reso1 = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        RenderHelper.startGlScissor(reso1.getScaledWidth() / 2 - 50, reso1.getScaledHeight() / 2 - 50, 100, 100);
        GL11.glPushMatrix();
        GL11.glTranslatef(-15F, 15F, 0F);
        RenderHelper.drawColourOnScreen(0xffffff, 255, 0, 0, reso1.getScaledWidth_double(), reso1.getScaledHeight_double(), 0);
        GL11.glPopMatrix();
        RenderHelper.endGlScissor();
    }



}
