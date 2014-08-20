package MiscUtils.Packets;

import MiscUtils.Network.AbstractPacket;
import MiscUtils.Register.KeyBind.KeybindRegistry;
import MiscUtils.Register.KeyBind.ModKeybind;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class SyncButtonClickEvent extends AbstractPacket {

    ModKeybind key;
    String player;

    public SyncButtonClickEvent(EntityPlayer player, ModKeybind key){
        this.player = player.getCommandSenderName();
        this.key = key;
    }

    public SyncButtonClickEvent(){}

    @Override
    public void toBytes(ByteBuf buffer, Side side) {
        ByteBufUtils.writeUTF8String(buffer, key.getKeyDescription());
        ByteBufUtils.writeUTF8String(buffer, player);
    }

    @Override
    public void fromBytes(ByteBuf buffer, Side side) {
        key = KeybindRegistry.GetKeyBind(ByteBufUtils.readUTF8String(buffer));
        player = ByteBufUtils.readUTF8String(buffer);
    }

    @Override
    public void onMessage(Side side, EntityPlayer player) {

        EntityPlayer pl = null;

        if(player != null){
            pl = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().func_152612_a(this.player);
        }
            if(key != null && pl != null) {
                key.OnActivated(pl, side);
            }
        }

}
