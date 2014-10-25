package MiscUtils;

import MiscUtils.Network.ChannelUtils;
import MiscUtils.Proxies.ServerProxy;
import MiscUtils.Utils.PlayerTickHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.launchwrapper.Launch;

@Mod(modid = "MiscUtils", name = "MiscUtilities", version = "@VERSION@")
public class MiscUtils
{

    public static ChannelUtils Utils;


    @SidedProxy(clientSide = "MiscUtils.Proxies.ClientProxy", serverSide = "MiscUtils.Proxies.ServerProxy")
    public static ServerProxy proxy;


    public static boolean IsLoadedInDev = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        Utils = new ChannelUtils("MiscUtilsChannel", "MiscUtils");
        RegisterPackets();



    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event){


        FMLCommonHandler.instance().bus().register(new PlayerTickHandler());
    }



    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event)
    {

    }


    public static void RegisterPackets(){


    }

}
