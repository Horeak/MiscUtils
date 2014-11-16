package MiscUtils.Render;

import MiscUtils.Utils.RayTracing;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.util.ArrayList;

public class RenderHelper {

    public static RenderItem itemRender = new RenderItem();


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

    public static void drawTexturedModalRect(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_, int zLevel)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + p_73729_6_), (double)zLevel, (double)((float)(p_73729_3_ + 0) * f), (double)((float)(p_73729_4_ + p_73729_6_) * f1));
        tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + p_73729_6_), (double)zLevel, (double)((float)(p_73729_3_ + p_73729_5_) * f), (double)((float)(p_73729_4_ + p_73729_6_) * f1));
        tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + 0), (double)zLevel, (double)((float)(p_73729_3_ + p_73729_5_) * f), (double)((float)(p_73729_4_ + 0) * f1));
        tessellator.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + 0), (double)zLevel, (double)((float)(p_73729_3_ + 0) * f), (double)((float)(p_73729_4_ + 0) * f1));
        tessellator.draw();
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

}
