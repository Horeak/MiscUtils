package MiscUtils.Network;

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

import java.util.EnumMap;
import java.util.Map;

public class ChannelUtils {

    String Channel, ID;
    public EnumMap<Side, FMLEmbeddedChannel> channels;

    public ChannelUtils(String Channel, String ModID){
        this.Channel = Channel;
        this.ID = ModID;
        channels = getNewChannelHandler();
    }

    public ChannelHandler handler = new ChannelHandler(Channel);

    private EnumMap<Side, FMLEmbeddedChannel> getNewChannelHandler()
    {

        EnumMap<Side, FMLEmbeddedChannel> handlers = NetworkRegistry.INSTANCE.newChannel(ID, handler);

        ChannelHandler.PacketExecuter executer = new ChannelHandler.PacketExecuter();

        for(Map.Entry<Side, FMLEmbeddedChannel> e : handlers.entrySet())
        {
            FMLEmbeddedChannel channel = e.getValue();
            String codec = channel.findChannelHandlerNameForType(ChannelHandler.class);
            channel.pipeline().addAfter(codec, "PacketExecuter", executer);
        }

        return handlers;
    }
}
