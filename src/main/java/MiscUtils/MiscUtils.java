package MiscUtils;

import MiscUtils.Network.ChannelUtils;
import MiscUtils.Packets.SyncButtonClickEvent;
import MiscUtils.Proxies.ServerProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.relauncher.Side;

import java.util.EnumMap;

@Mod(modid = "MiscUtils", name = "MiscUtilities", version = "@VERSION@")
public class MiscUtils
{

    public static EnumMap<Side, FMLEmbeddedChannel> channels;
    public static ChannelUtils Utils;


    @SidedProxy(clientSide = "MiscUtils.Proxies.ClientProxy", serverSide = "MiscUtils.Proxies.ServerProxy")
    public static ServerProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        Utils = new ChannelUtils("MiscUtilsChannel", "MiscUtils");
        RegisterPackets();
        channels = Utils.getNewChannelHandler();



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
