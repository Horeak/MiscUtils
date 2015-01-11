package MiscUtils.GuideBase.Gui;

import MiscUtils.GuideBase.Gui.Utils.GuideItem;
import MiscUtils.GuideBase.Gui.Utils.GuideModSelectionButton;
import MiscUtils.GuideBase.Gui.Utils.GuideObjectButton;
import MiscUtils.GuideBase.Gui.Utils.GuideRecipeButton;
import MiscUtils.GuideBase.Gui.Utils.GuideTabSelectionButton;
import MiscUtils.GuideBase.Recipe.RecipeUtils;
import MiscUtils.GuideBase.Registry.GuideModRegistry;
import MiscUtils.GuideBase.Utils.GuideInstance;
import MiscUtils.GuideBase.Utils.GuideRecipeTypeRender;
import MiscUtils.GuideBase.Utils.GuideTab;
import MiscUtils.GuideBase.Utils.ModGuideInstance;
import MiscUtils.GuideBase.Utils.TextGuideTab;
import MiscUtils.MiscUtilsMain;
import MiscUtils.Render.RenderHelper;
import MiscUtils.Utils.StackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiGuideBase extends GuiScreen {

    public ResourceLocation MainTexture = new ResourceLocation(MiscUtilsMain.Id.toLowerCase(), "textures/gui/GuideGui.png");
    public ResourceLocation IconTexutre = new ResourceLocation(MiscUtilsMain.Id.toLowerCase(), "textures/gui/GuideGuiIcons.png");

    public ArrayList<ItemStack> InfoList = new ArrayList<ItemStack>();

    public GuideTab currentTab = null;

    public float InfoScroll = 0;
    public boolean InfoScrolling;

    public boolean CanScrollText = false;

    public boolean ShowingObject = false, UnRegisteredItem = false;
    public ItemStack ObjectShowing = null;

    public boolean ShowingRecipe = false;
    public int CurrentRecipe, MaxRecipe;
    public int CurrentRecipeType, MaxRecipeType;
    public GuideRecipeTypeRender res;

    public boolean SearchBar = false;
    public GuiTextField ObjectSearch;

    public int xSizeOfTexture = 255, ySizeOfTexture = 208;

    public String ObjectTextForRender = null;

    public ModGuideInstance Current = null;

    //TODO Make pages and tabs work as instances of an abstract instead of having it how it is now. And make GuideInstance register a page instance where then the contents of the page is registered to that instance



    protected void mouseClicked(int x, int y, int g)
    {
        super.mouseClicked(x, y, g);

        if(ObjectSearch != null && SearchBar)
            ObjectSearch.mouseClicked(x, y, g);

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



        //Get scroll bar value
        if(CanScrollText){
            float t = MathHelper.clamp_float((float)((y-5)-posY) / 190, 0.0F, 1F);
            boolean isOnInfoScroll = y >= posY + 6 && y < posY + 200;
            boolean flag = Mouse.isButtonDown(0);


            if (!flag) {
                InfoScrolling = false;

            } else if (InfoScrolling && isOnInfoScroll) {
                InfoScroll = t;

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
            drawTexturedModalRect(posX + 214, (int)(posY + 7 +(InfoScroll * (178))), 32, 0, 12, 15);
            GL11.glPopMatrix();
        }



        int textX = 34, textY = 16;


        //TODO Make pages pages work as modules and do rendering for each instance
        //Render text pages
        if(!ShowingObject) {
            fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + StatCollector.translateToLocal(Current.Id) + ": " + StatCollector.translateToLocal(currentTab.Name), posX + textX, posY + 4, new Color(91, 91, 91).getRGB(), false);

            if (currentTab instanceof TextGuideTab) {
                String Text = ((TextGuideTab) currentTab).GetText();

                int lines = 0;
                java.util.List list = fontRendererObj.listFormattedStringToWidth(Text, TextLength);
                lines = list.size();

                int PerPage = 18;

                float trantt = ((float)(lines-PerPage) * InfoScroll);
                int trant = (int)trantt;

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


        }


        //TODO Make pages pages work as modules and do rendering for each instance
        if (ShowingObject && ObjectShowing != null){
            fontRendererObj.drawString(ObjectShowing.getDisplayName(), posX + textX, posY + 4, new Color(91, 91, 91).getRGB(), false);
            fontRendererObj.drawString(EnumChatFormatting.BLUE + "" + EnumChatFormatting.ITALIC + "Mod: " + StackUtils.GetIdentifier(ObjectShowing).modId, posX + 34, posY + 12, 0xffffff);

            if(ObjectTextForRender == null){
                ObjectTextForRender = currentTab.GetInfoForStack(ObjectShowing);
            }

            String Text = ObjectTextForRender;

            int xx = posX + 188;
            int yy = posY + 10;

            Color c1 = new Color(162, 162, 162);
            Color c2 = new Color(149, 149, 149);

            //Render item background
            drawRect(xx - 2, yy - 2, xx + 18, yy + 18, c2.getRGB());
            drawRect(xx, yy, xx + 16, yy + 16, c1.getRGB());

            //Render item when showing a item page
            RenderHelper.drawItemStack(fontRendererObj, ObjectShowing, xx, yy);


            int Offset = 0;
            if(ShowingRecipe){
                MaxRecipeType = RecipeUtils.GetTotalDifferentRecipeTypes(ObjectShowing) - 1;

                if(RecipeUtils.GetRecipeAt(ObjectShowing, CurrentRecipeType) != null)
                    res = RecipeUtils.GetRecipeAt(ObjectShowing, CurrentRecipeType);

                if(res != null) {

                    MaxRecipe = res.GetRecipesAmountFor(ObjectShowing) - 1;

                    if (CurrentRecipe > MaxRecipe)
                        CurrentRecipe = MaxRecipe;



                    Minecraft.getMinecraft().renderEngine.bindTexture(res.GetRenderTexture());
                    drawTexturedModalRect(posX + 50, posY + 30, res.GetRenderPositionX(), res.GetRenderPositionY(), res.GetRenderXSize(), res.GetRenderYSize());
                    res.RenderExtras(this, posX, posY, ObjectShowing, CurrentRecipe);


                    super.drawScreen(x, y, f);

                    String nm = (EnumChatFormatting.UNDERLINE) + StatCollector.translateToLocal(res.GetName());

                    fontRendererObj.drawString(nm, posX + 100 - fontRendererObj.getStringWidth(nm) / 2, posY + 20, new Color(108, 108, 108).getRGB(), false);

                    textY += res.GetRenderYSize();
                    Offset = res.GetRenderYSize();

                }

            }else{

                //Handles rendering of buttons when not showing a recipe
                GL11.glColor4f(1F, 1F, 1F, 1F);
                super.drawScreen(x, y, f);

            }

            //TODO Make pages pages work as modules and do rendering for each instance
            if(HasPage(ObjectShowing) || MiscUtilsMain.IsLoadedInDev) {
                if (Text != null && !Text.isEmpty()) {
                    ArrayList list = new ArrayList<String>();

                    if(MiscUtilsMain.IsLoadedInDev && !HasPage(ObjectShowing)){
                        list.add(EnumChatFormatting.BOLD + "" + EnumChatFormatting.UNDERLINE + "Page was loaded in dev!");
                        list.add("");
                    }

                    for(Object r : fontRendererObj.listFormattedStringToWidth(Text, TextLength)){
                        String te = (String)r;
                        list.add(te);
                    }

                    int lines = list.size();

                    int PerPage = 16 - (Offset / 10);

                    float transs = ((float) (lines - PerPage) * InfoScroll);
                    int trant = (int) transs;


                    int lineMax = trant + PerPage;
                    int lineMin = trant;

                    if (lineMax > lines)
                        lineMax = lines;


                    if (lineMax > 0 && lineMin >= 0)
                        for (int line = lineMin; line < lineMax; line++) {

                            if (list.size() > line)
                                fontRendererObj.drawString((String) list.get(line), posX + textX, ((posY + textY + 20 + ((line - lineMin) * 10))), new Color(107, 107, 107).getRGB(), false);

                        }

                    if (lines > PerPage)
                        CanScrollText = true;
                    else
                        CanScrollText = false;


                }
            }
        }else{

            //Handles default rendering of buttons
            GL11.glColor4f(1F, 1F, 1F, 1F);
            super.drawScreen(x, y, f);
        }




        //Render tooltip info/names
        for (int i = 0; i < buttonList.size(); i++) {
            GuiButton btn = (GuiButton) buttonList.get(i);
            if (btn != null && btn.func_146115_a() && btn.enabled) {

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
                }else if(btn instanceof GuideRecipeButton) {
                    GuideRecipeButton el = (GuideRecipeButton) btn;
                    if (el != null && el != null) {
                        String[] desc = {StatCollector.translateToLocal("guide.hover.recipeAviable"), EnumChatFormatting.GRAY + (ShowingRecipe ? StatCollector.translateToLocal("guide.hover.recipeHide") : StatCollector.translateToLocal("guide.hover.recipeShow"))};

                        java.util.List temp = Arrays.asList(desc);
                        drawHoveringText(temp, x, y, fontRendererObj);
                    }

                } else if(btn instanceof GuideItem){
                    GuideItem el = (GuideItem)btn;
                        if (el != null && el.stack != null) {
                            java.util.List temp = new ArrayList();

                            temp.add(el.stack.getDisplayName());
                            el.stack.getItem().addInformation(el.stack, Minecraft.getMinecraft().thePlayer, temp, false);

                            drawHoveringText(temp, x, y, fontRendererObj);
                        }



             }else{
                    if(btn.displayString == "<"){
                        String[] desc = {StatCollector.translateToLocal("guide.hover.prevRecipe")};

                        java.util.List temp = Arrays.asList(desc);
                        drawHoveringText(temp, x, y, fontRendererObj);


                    }else if(btn.displayString == ">"){
                        String[] desc = {StatCollector.translateToLocal("guide.hover.nextRecipe")};

                        java.util.List temp = Arrays.asList(desc);
                        drawHoveringText(temp, x, y, fontRendererObj);

                    }else if(btn.displayString == "<<"){
                        String[] desc = {StatCollector.translateToLocal("guide.hover.prevRecipeType")};

                        java.util.List temp = Arrays.asList(desc);
                        drawHoveringText(temp, x, y, fontRendererObj);


                    }else if(btn.displayString == ">>"){
                        String[] desc = {StatCollector.translateToLocal("guide.hover.nextRecipeType")};

                        java.util.List temp = Arrays.asList(desc);
                        drawHoveringText(temp, x, y, fontRendererObj);
                    }




                }

        }



        }

    }

    public void keyTyped(char key, int keycode){
        super.keyTyped(key, keycode);

        if(ObjectSearch != null && ObjectSearch.isFocused()){
            ObjectSearch.textboxKeyTyped(key, keycode);
        }


        int Pos = InfoList.size() - 1;
        if(ShowingObject) {
            if(keycode == Keyboard.KEY_BACK){
                if(InfoList.size() > 0){
                    SetPageFromStack(InfoList.get(Pos), false);
                    InfoList.remove(InfoList.get(Pos));
                }else{
                    ResetObjectPageInfo();
                    InfoList.clear();
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

            ResetObjectPageInfo();
            InfoList.clear();
        }

        if(button instanceof GuideTabSelectionButton) {
            currentTab = ((GuideTabSelectionButton) button).GetInstance();

            ResetObjectPageInfo();
            InfoList.clear();

        }

        if(button instanceof GuideObjectButton) {
            ResetObjectPageInfo();

            ObjectShowing = ((GuideObjectButton) button).stack;
            ShowingObject = true;

            InfoList.clear();
            InfoList.add(ObjectShowing);

        }

        if(button instanceof GuideRecipeButton){
            ShowingRecipe ^= true;

            if(ShowingRecipe)
            res = RecipeUtils.GetRecipeAt(ObjectShowing, 0);

            CurrentRecipe = 0;
            MaxRecipe = 0;

            CurrentRecipeType = 0;
            MaxRecipeType = 0;
        }

        if(button.displayString == "<"){
            CurrentRecipe -= 1;

        }else if(button.displayString == ">"){
            CurrentRecipe += 1;


        }else  if(button.displayString == "<<"){
            CurrentRecipeType -= 1;

        }else if(button.displayString == ">>"){
            CurrentRecipeType += 1;
        }

        if(button instanceof GuideItem){
            GuideItem gd = (GuideItem)button;

            //Remove for loops to allow items that are not registered to be able to be viewed.
            //TODO Allow accessing pages that are not registered? have them show the recipe and a blank page? only allow items that have a recipe?
            //TODO Make control click default or dev only?

            boolean DebugAllItemsAccess = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
            ItemStack stack = gd.stack;

            if(stack != null) {
                ItemStack ccp = stack.copy();
                ccp.setItemDamage(0);

                SetPageFromStack(ccp, !DebugAllItemsAccess);
                InfoList.add(ccp);


            }

        }


    }


    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();

        int posX = (this.width - xSizeOfTexture) / 2;
        int posY = (this.height - ySizeOfTexture) / 2;

        SearchBar = false;

        int obSX = posX + 60, obSY = posY - 12;
        int sizeX = 130, sizeY = 12;

        if(ObjectSearch == null)
        ObjectSearch = new GuiTextField(fontRendererObj, obSX, obSY, sizeX, sizeY);
        else{
            ObjectSearch.xPosition = obSX;
            ObjectSearch.yPosition = obSY;

            ObjectSearch.height = sizeY;
            ObjectSearch.width = sizeX;
        }

        for(int i = 0; i < GuideModRegistry.ModArray.size(); i++){
            GuideInstance instance = GuideModRegistry.ModArray.get(i).guide;
            buttonList.add(new GuideModSelectionButton(0, posX  - 2, (posY + 3) + (i * 27), instance, Current != null ? Current.guide.ModPageName().equalsIgnoreCase(instance.ModPageName()) : false));
        }


        if(Current != null && Current.guide.GuideTabs.size() > 0)
            for(int i = 0; i < Current.guide.GuideTabs.size(); i++){
                GuideTab instance = Current.guide.GuideTabs.get(i);

                if(instance == null || instance.stack == null){
                        Current.guide.GuideTabs.clear();
                        Current.guide.RegisterInfo();

                    return;
                }

                buttonList.add(new GuideTabSelectionButton(0, posX + 233, (posY + 3) + (i * 27), instance, currentTab != null ? currentTab.Name.equalsIgnoreCase(instance.Name) : false));
            }




        if(!ShowingObject && !(currentTab instanceof TextGuideTab))
        if(ObjectSearch != null) {
            ObjectSearch.drawTextBox();
            SearchBar = true;
        }


        ArrayList copy = new ArrayList();


        //TODO Make tabs work as modules and do rendering for each instance
        //Add ObjectButtons based on scroll
          if (!ShowingObject && currentTab != null && !(currentTab instanceof TextGuideTab)) {

                    if(currentTab != null && currentTab.list != null && currentTab.list.size() > 0)
                    for (int i = 0; i < currentTab.list.size(); i++) {
                            ItemStack stack = StackUtils.GetObject(currentTab.list.get(i));

                            if (ObjectSearch == null || ObjectSearch.getText().isEmpty() || ObjectSearch.getText() == "" || stack.getDisplayName().toLowerCase().contains(ObjectSearch.getText().toLowerCase())) {
                                copy.add(stack);
                            }
                        }



                int PerPage = 19;
                int m = copy.size();




                if (m > PerPage)
                    CanScrollText = true;
                else
                    CanScrollText = false;

                float transs = ((float)(m-PerPage) * (InfoScroll));
                int trant = (int) transs;


                int lineMax = trant + PerPage;
                int lineMin = trant;

                if (lineMax > m)
                    lineMax = m;


                if (lineMax > 0 && lineMin >= 0)
                    for (int line = lineMin; line < lineMax; line++) {
                        ItemStack stack = (ItemStack) copy.get(line);
                        buttonList.add(new GuideObjectButton(buttonList.size() + 1, posX + 36, posY + 16 + ((line - lineMin) * 10), stack));
                    }

        }else{


            if(RecipeUtils.GetTotalRecipeAmountFor(ObjectShowing) > 0){
                buttonList.add(new GuideRecipeButton(0, posX + 165, posY + 9, ShowingRecipe));
            }

            if(ShowingRecipe){
                GuiButton bt = new GuiButton(0, posX + 35, posY + 20 + (res.GetRenderYSize() / 4), 15, 20, "<");
                bt.enabled = CurrentRecipe > 0;
                buttonList.add(bt);

                GuiButton bt2 = new GuiButton(0, posX + 51 + res.GetRenderXSize(), posY + 20 + (res.GetRenderYSize() / 4), 15, 20, ">");
                bt2.enabled = CurrentRecipe < MaxRecipe;
                buttonList.add(bt2);




                GuiButton bt21 = new GuiButton(0, posX + 35, posY + 30 + (res.GetRenderYSize() / 2), 15, 20, "<<");
                bt21.enabled = CurrentRecipeType > 0;
                buttonList.add(bt21);

                GuiButton bt22 = new GuiButton(0, posX + 51 + res.GetRenderXSize(), posY + 30 + (res.GetRenderYSize() / 2), 15, 20, ">>");
                bt22.enabled = CurrentRecipeType < MaxRecipeType;
                buttonList.add(bt22);

                for(GuideItem itm : res.AddItemsFor(posX + 50, posY + 30, new ArrayList<GuideItem>(), ObjectShowing, CurrentRecipe))
                    buttonList.add(itm);


            }

        }


        int PrevId = 0;
        for(Object r : buttonList){
            if(r instanceof GuiButton){
                GuiButton gb = (GuiButton)r;
                gb.id = PrevId++;
            }
        }

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void ResetObjectPageInfo(){
        InfoScroll = 0;
        InfoScrolling = false;

        ObjectShowing = null;
        ShowingObject = false;
        ShowingRecipe = false;

        CurrentRecipe = 0;
        MaxRecipe = 0;

        SearchBar = false;

        ObjectTextForRender = null;

        ObjectSearch.setText("");

    }

    public void SetPageFromStack(ItemStack stack, boolean Restricated) {
        if (Restricated) {
            for (ModGuideInstance inst : GuideModRegistry.ModArray) {
                for (GuideTab tab : inst.guide.GuideTabs) {
                    for (Object r : tab.list) {
                        ItemStack st = StackUtils.GetObject(r);

                        if (StackUtils.AreStacksEqualIgnoreData(st, stack)) {
                            ResetObjectPageInfo();

                            ItemStack ccp = stack.copy();
                            ccp.setItemDamage(0);

                            ObjectShowing = ccp;
                            ShowingObject = true;

                            ObjectTextForRender = null;

                            UnRegisteredItem = !HasPage(ccp);

                            if (!tab.Name.equalsIgnoreCase(currentTab.Name))
                                currentTab = tab;

                            if (!Current.Id.equalsIgnoreCase(inst.Id))
                                Current = inst;
                        }

                    }
                }
            }

        } else {
            ResetObjectPageInfo();
            ItemStack ccp = stack.copy();
            ccp.setItemDamage(0);

            ObjectShowing = ccp;
            ShowingObject = true;

            UnRegisteredItem = !HasPage(ccp);

            ObjectTextForRender = null;
        }

    }

    public boolean HasPage(ItemStack stack){
        for (ModGuideInstance inst : GuideModRegistry.ModArray) {
            for (GuideTab tab : inst.guide.GuideTabs) {
                for (Object r : tab.list) {
                    ItemStack st = StackUtils.GetObject(r);
                    if (StackUtils.AreStacksEqualIgnoreData(st, stack)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
