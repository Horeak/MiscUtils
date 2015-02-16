package MiscUtils;

import MiscUtils.Config.MiscUtilsConfig;
import MiscUtils.GuideBase.Gui.GuiHandler;
import MiscUtils.GuideBase.ItemGuide;
import MiscUtils.GuideBase.MiscUtilsGuideInstance;
import MiscUtils.GuideBase.Recipe.RecipeUtils;
import MiscUtils.GuideBase.Registry.GuideModRegistry;
import MiscUtils.Network.ChannelUtils;
import MiscUtils.Proxies.ServerProxy;
import MiscUtils.Register.ItemRegister;
import MiscUtils.Utils.CraftingUtils;
import MiscUtils.Utils.PlayerTickHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;

@Mod(modid = MiscUtilsMain.Id, name = MiscUtilsMain.Name, version = "@VERSION@")
public class MiscUtilsMain
{

   public static final String Id = "MiscUtils";
   public static final String Name = "MiscUtils";

    public static ChannelUtils Utils;

    @Mod.Instance(Id)
    public static MiscUtilsMain instance = new MiscUtilsMain();


    @SidedProxy(clientSide = "MiscUtils.Proxies.ClientProxy", serverSide = "MiscUtils.Proxies.ServerProxy")
    public static ServerProxy proxy;
    MiscUtilsConfig config;


    public static boolean IsLoadedInDev = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    public static Item Guide = new ItemGuide().setCreativeTab(CreativeTabs.tabMisc).setTextureName(Id + ":ItemGuide");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        Utils = new ChannelUtils("MiscUtilsChannel", "MiscUtils");
        RegisterPackets();

        config = new MiscUtilsConfig(event.getSuggestedConfigurationFile() + "");

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        GuideModRegistry.RegisterModToGuide(new MiscUtilsGuideInstance());

        ItemRegister ItemUtils = new ItemRegister(config, Id);
        CraftingUtils CraftingUtils = new CraftingUtils(config);

        ItemUtils.Register(Guide, "GuideItem");

        CraftingUtils.AddShapelessRecipe(new ItemStack(Guide), new Object[]{Items.book, Items.dye, Items.paper});

        RecipeUtils.RegisterTypes();

        if(event.getSide() == Side.CLIENT){
            FMLCommonHandler.instance().bus().register(new PlayerTickHandler());
        }

    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event){

    }



    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event)
    {
    }


    public static void RegisterPackets(){


    }

}
