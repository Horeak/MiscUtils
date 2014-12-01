package MiscUtils.Network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public abstract class AbstractPacket
{

    public abstract void toBytes(ByteBuf buffer, Side side);


    public abstract void fromBytes(ByteBuf buffer, Side side);


    public abstract void onMessage(Side side, EntityPlayer player);


}