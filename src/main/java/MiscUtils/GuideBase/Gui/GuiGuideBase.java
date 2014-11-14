package MiscUtils.GuideBase.Gui;

import MiscUtils.GuideBase.Gui.Utils.GuideModSelectionButton;
import MiscUtils.GuideBase.Gui.Utils.GuideObjectButton;
import MiscUtils.GuideBase.Gui.Utils.GuideRecipeButton;
import MiscUtils.GuideBase.Gui.Utils.GuideTabSelectionButton;
import MiscUtils.GuideBase.Registry.GuideModRegistry;
import MiscUtils.GuideBase.Utils.GuideInstance;
import MiscUtils.GuideBase.Utils.GuideTab;
import MiscUtils.GuideBase.Utils.ModGuideInstance;
import MiscUtils.GuideBase.Utils.TextGuideTab;
import MiscUtils.MiscUtilsMain;
import MiscUtils.Utils.Recipe.RecipeUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class GuiGuideBase extends GuiScreen {

    public ResourceLocation MainTexture = new ResourceLocation(MiscUtilsMain.Id.toLowerCase(), "textures/gui/GuideGui.png");
    public ResourceLocation IconTexutre = new ResourceLocation(MiscUtilsMain.Id.toLowerCase(), "textures/gui/GuideGuiIcons.png");

    //TODO Fix "Bottom" of text contents/object buttons being at the top of the page

    public GuideTab currentTab = null;

    public float InfoScroll = 0;
    public boolean InfoScrolling;

    public boolean Update = true;
    public boolean CanScrollText = false;

    public boolean ShowingObject = false;
    public ItemStack ObjectShowing = null;

    int xSizeOfTexture = 255, ySizeOfTexture = 208;
    double ScrollMax = 4.825;
    float ScrollOffset = 5.175F;


    ModGuideInstance Current = null;


    protected void mouseClicked(int x, int y, int g)
    {
        super.mouseClicked(x, y, g);
        int posX = (this.width - xSizeOfTexture) / 2;
        int posY = (this.height - ySizeOfTexture) / 2;

        //Activate scrolling if on scrollbar
        if(CanScrollText){
            boolean isOnInfoScroll = x >= posX + 214 && x < posX + 229 && y >= posY + 6 && y < posY + 200;
            if (isOnInfoScroll) {
                InfoScrolling = true;
            }
        }


    }

    int TextLength = 170;

    @Override
    public void drawScreen(int x, int y, float f) {

        //No GuideInstance use default
        if(Current == null && GuideModRegistry.ModArray.size() > 0)
            Current = GuideModRegistry.ModArray.get(0);

        //No GuideTab use default
        if(Current != null && currentTab == null && Current.guide.GuideTabs.size() > 0)
            currentTab = Current.guide.GuideTabs.get(0);


        //Should tab contents be updated each time it renders?
        if(Update) {
            Current.guide.GuideTabs.clear();
            Current.guide.RegisterInfo();
        }

        //Resets scroll when off
        if(!CanScrollText){
            InfoScroll = 0.0F;
            InfoScrolling = false;
        }

        drawDefaultBackground();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(MainTexture);

        int posX = (this.width - xSizeOfTexture) / 2;
        int posY = (this.height - ySizeOfTexture) / 2;
        drawTexturedModalRect(posX, posY, 0, 0, xSizeOfTexture, ySizeOfTexture);

        initGui();

        GL11.glPushMatrix();
        GL11.glColor4f(1F, 1F, 1F, 1F);
        super.drawScreen(x, y, f);
        GL11.glPopMatrix();


        //Get scroll bar value
        if(CanScrollText){
            float t = MathHelper.clamp_float(((float) y - (float) posY + (float) 201) / 40, 0.0F, 10F);
            boolean isOnInfoScroll = y >= posY + 6 && y < posY + 200;
            boolean flag = Mouse.isButtonDown(0);
            if (!flag) {
                InfoScrolling = false;

            } else if (InfoScrolling && isOnInfoScroll) {
                InfoScroll = t - ScrollOffset;

            }

        }


        //Render scroll bar
        if(!CanScrollText){
            GL11.glPushMatrix();
            GL11.glColor4f(1F, 1F, 1F, 1F);
            Minecraft.getMinecraft().renderEngine.bindTexture(IconTexutre);
            drawTexturedModalRect(posX + 214, posY + 7, 44, 0, 12, 15);
            GL11.glPopMatrix();

        }else if(CanScrollText){
            GL11.glPushMatrix();
            GL11.glColor4f(1F, 1F, 1F, 1F);
            Minecraft.getMinecraft().renderEngine.bindTexture(IconTexutre);
            GL11.glTranslatef(0.0F, InfoScroll * 36.9F, 0.0F);
            drawTexturedModalRect(posX + 214, posY + 7, 32, 0, 12, 15);
            GL11.glPopMatrix();
        }

        int textX = 34, textY = 16;



        GL11.glPushMatrix();

        //Render text pages
        if(!ShowingObject) {
            fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + StatCollector.translateToLocal(Current.Id) + ": " + StatCollector.translateToLocal(currentTab.Name), posX + textX, posY + 4, new Color(91, 91, 91).getRGB(), false);


            if (currentTab instanceof TextGuideTab) {
                String Text = ((TextGuideTab) currentTab).GetText();

                int lines = 0;
                java.util.List list = fontRendererObj.listFormattedStringToWidth(Text, TextLength);
                lines = list.size();

                int PerPage = 18;
                double trans = (lines) / (ScrollMax);

                double transs = (trans * InfoScroll);
                int trant = (int) transs;


                int lineMax = trant + PerPage;
                int lineMin = trant;

                if (lineMax > lines)
                    lineMax = lines;


                if (lineMax > 0 && lineMin >= 0)
                    for (int line = lineMin; line < lineMax; line++) {

                        if (list.size() > line)
                            fontRendererObj.drawString((String) list.get(line), posX + textX, ((posY + textY + ((line - lineMin) * 10))), new Color(107, 107, 107).getRGB(), false);

                    }

                if (lines > PerPage)
                    CanScrollText = true;
                else
                    CanScrollText = false;

            }


        }else if (ShowingObject && ObjectShowing != null){
            fontRendererObj.drawString(ObjectShowing.getDisplayName(), posX + textX, posY + 4, new Color(91, 91, 91).getRGB(), false);
            String Text = currentTab.GetInfoForStack(ObjectShowing);

            int xx = posX + 188;
            int yy = posY + 10;

            Color c1 = new Color(162, 162, 162);
            Color c2 = new Color(149, 149, 149);

            //Render item background
            drawRect(xx - 2, yy - 2, xx + 18, yy + 18, c2.getRGB());
            drawRect(xx, yy, xx + 16, yy + 16, c1.getRGB());


            //Render item when showing a item page
            GL11.glPushMatrix();
            GL11.glColor4f(1F, 1F, 1F, 1F);

            RenderHelper.disableStandardItemLighting();
            itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), ObjectShowing, xx, yy);
            itemRender.renderItemOverlayIntoGUI(fontRendererObj, mc.getTextureManager(), ObjectShowing, xx, yy);
            RenderHelper.enableStandardItemLighting();

            GL11.glPopMatrix();

            if(Text != null && !Text.isEmpty()) {
                int lines = 0;
                java.util.List list = fontRendererObj.listFormattedStringToWidth(Text, TextLength);
                lines = list.size();

                int PerPage = 16;
                double trans = (lines) / (ScrollMax);

                double transs = (trans * InfoScroll);
                int trant = (int) transs;


                int lineMax = trant + PerPage;
                int lineMin = trant;

                if (lineMax > lines)
                    lineMax = lines;


                if (lineMax > 0 && lineMin >= 0)
                    for (int line = lineMin; line < lineMax; line++) {

                        if (list.size() > line)
                            fontRendererObj.drawString((String) list.get(line), posX + textX, ((posY + textY + 30 + ((line - lineMin) * 10))), new Color(107, 107, 107).getRGB(), false);

                    }

                if (lines > PerPage)
                    CanScrollText = true;
                else
                    CanScrollText = false;


            }
        }

        GL11.glPopMatrix();





        //Render tooltip info/names
        for (int i = 0; i < buttonList.size(); i++) {
            GuiButton btn = (GuiButton) buttonList.get(i);
            if (btn != null && btn.func_146115_a()) {

                if (btn instanceof GuideModSelectionButton) {
                    GuideModSelectionButton el = (GuideModSelectionButton) btn;
                        if (el != null && el.GetInstance() != null) {
                            String[] desc = {StatCollector.translateToLocal(el.GetInstance().ModPageName())};

                            List temp = Arrays.asList(desc);
                            drawHoveringText(temp, x, y, fontRendererObj);
                        }

            }else if(btn instanceof GuideTabSelectionButton){
                    GuideTabSelectionButton el = (GuideTabSelectionButton)btn;
                    if (el != null && el.GetInstance() != null) {
                        String[] desc = {StatCollector.translateToLocal(el.GetInstance().Name)};

                        java.util.List temp = Arrays.asList(desc);
                        drawHoveringText(temp, x, y, fontRendererObj);
                    }
                }else if(btn instanceof GuideRecipeButton){
                    GuideRecipeButton el = (GuideRecipeButton)btn;
                    if (el != null && el != null) {
                        String[] desc = {StatCollector.translateToLocal("guide.hover.recipeAviable")};

                        java.util.List temp = Arrays.asList(desc);
                        drawHoveringText(temp, x, y, fontRendererObj);
                    }

             }

        }



        }

    }


    @Override
    protected void actionPerformed(GuiButton button){
        int id = button.id;

        if(button instanceof GuideModSelectionButton){
            Current = ((GuideModSelectionButton) button).GetInstance().GetModInstance();
            currentTab = null;

            ObjectShowing = null;
            ShowingObject = false;
        }

        if(button instanceof GuideTabSelectionButton) {
            currentTab = ((GuideTabSelectionButton) button).GetInstance();

            ObjectShowing = null;
            ShowingObject = false;
        }

        if(button instanceof GuideObjectButton) {
            ObjectShowing = ((GuideObjectButton) button).stack;
            ShowingObject = true;
        }


    }


    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();

        int posX = (this.width - xSizeOfTexture) / 2;
        int posY = (this.height - ySizeOfTexture) / 2;

        for(int i = 0; i < GuideModRegistry.ModArray.size(); i++){
            GuideInstance instance = GuideModRegistry.ModArray.get(i).guide;
            buttonList.add(new GuideModSelectionButton(buttonList.size() + 1, posX  - 2, (posY + 3) + (i * 27), instance, Current != null ? Current.guide.ModPageName().equalsIgnoreCase(instance.ModPageName()) : false));
        }


        if(Current != null && Current.guide.GuideTabs.size() > 0)
            for(int i = 0; i < Current.guide.GuideTabs.size(); i++){

                GuideTab instance = Current.guide.GuideTabs.get(i);
                buttonList.add(new GuideTabSelectionButton(buttonList.size() + 1, posX + 233, (posY + 3) + (i * 27), instance, currentTab != null ? currentTab.Name.equalsIgnoreCase(instance.Name) : false));
            }





        //Add ObjectButtons based on scroll
        if(!ShowingObject) {
            if (currentTab != null && currentTab.list != null && currentTab.list.size() > 0 && !(currentTab instanceof TextGuideTab)) {
                int PerPage = 19;
                int m = currentTab.list.size();


                if (m > PerPage)
                    CanScrollText = true;
                else
                    CanScrollText = false;

                double transs = (m * (InfoScroll / ScrollMax));
                int trant = (int) transs;


                int lineMax = trant + PerPage;
                int lineMin = trant;

                if (lineMax > m)
                    lineMax = m;


                if (lineMax > 0 && lineMin >= 0)
                    for (int line = lineMin; line < lineMax; line++) {
                        ItemStack stack = (ItemStack) currentTab.list.get(line);
                        buttonList.add(new GuideObjectButton(buttonList.size() + 1, posX + 36, posY + 16 + ((line - lineMin) * 10), stack));
                    }


            }
        }else{
            //Add recipe button if item has more then 0 recipes. (Need to improve GetRecipes method. Read TO-DO RecipeUtils
            if(RecipeUtils.GetRecipes(ObjectShowing).size() > 0){
                buttonList.add(new GuideRecipeButton(buttonList.size() + 1, posX + 165, posY + 9));

            }

        }


    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }



}
