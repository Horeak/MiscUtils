package MiscUtils;

import MiscUtils.Network.ChannelUtils;
import MiscUtils.Packets.SyncButtonClickEvent;
import MiscUtils.Proxies.ServerProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "MiscUtils", name = "MiscUtilities", version = "@VERSION@")
public class MiscUtils
{

    public static ChannelUtils Utils;


    @SidedProxy(clientSide = "MiscUtils.Proxies.ClientProxy", serverSide = "MiscUtils.Proxies.ServerProxy")
    public static ServerProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        Utils = new ChannelUtils("MiscUtilsChannel", "MiscUtils");
        RegisterPackets();



    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event){

    }



    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event)
    {

    }


    public static void RegisterPackets(){

        Utils.handler.RegisterPacket(SyncButtonClickEvent.class);

    }

}
