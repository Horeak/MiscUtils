package MiscUtils;

import MiscUtils.Config.MiscUtilsConfig;
import MiscUtils.GuideBase.Gui.GuiHandler;
import MiscUtils.GuideBase.ItemGuide;
import MiscUtils.GuideBase.MiscUtilsGuideInstance;
import MiscUtils.GuideBase.Registry.GuideModRegistry;
import MiscUtils.Network.ChannelUtils;
import MiscUtils.Proxies.ServerProxy;
import MiscUtils.Register.BlockRegister;
import MiscUtils.Register.ItemRegister;
import MiscUtils.Utils.CraftingUtils;
import MiscUtils.Utils.PlayerTickHandler;
import MiscUtils.Utils.Recipe.RecipeUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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

    public static Item Guide = new ItemGuide().setCreativeTab(CreativeTabs.tabMisc);

    ItemRegister ItemUtils = null;
    BlockRegister BlockUtils = null;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        Utils = new ChannelUtils("MiscUtilsChannel", "MiscUtils");
        RegisterPackets();

        config = new MiscUtilsConfig(event.getSuggestedConfigurationFile() + "");

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        GuideModRegistry.RegisterModToGuide(new MiscUtilsGuideInstance());

        ItemUtils = new ItemRegister(config, Id);
        BlockUtils = new BlockRegister(config, Id);

        ItemUtils.Register(Guide, "GuideItem");


        CraftingUtils CraftingUtils = new CraftingUtils(config);
        CraftingUtils.AddShapelessRecipe(new ItemStack(Guide), new Object[]{Items.book, Items.dye, Items.paper});

        RecipeUtils.RegisterTypes();

    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event){
            ItemUtils.RegisterIcons();
            BlockUtils.RegisterIcons();



        FMLCommonHandler.instance().bus().register(new PlayerTickHandler());
    }



    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event)
    {

    }


    public static void RegisterPackets(){


    }

}
