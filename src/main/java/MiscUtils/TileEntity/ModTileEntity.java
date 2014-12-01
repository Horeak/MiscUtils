package MiscUtils.TileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class ModTileEntity extends TileEntity {

    protected EnumFacing orientation;
    protected byte state;
    protected String customName;

    public ModTileEntity() {

        orientation =
        EnumFacing.SOUTH;
        state = 0;
        customName = "";
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new S35PacketUpdateTileEntity(new BlockPos(getPos().getX(), getPos().getY(), getPos().getZ()), 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
    }

    public EnumFacing getOrientation() {

        return orientation;
    }

    public void setOrientation(EnumFacing orientation) {

        this.orientation = orientation;
    }

    public void setOrientation(int orientation) {

        this.orientation = EnumFacing.getFront(orientation);
    }

    public short getState() {

        return state;
    }

    public void setState(byte state) {

        this.state = state;
    }

    public boolean hasCustomName() {

        return customName != null && customName.length() > 0;
    }

    public String getCustomName() {

        return customName;
    }

    public void setCustomName(String customName) {

        this.customName = customName;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {

        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {

        super.readFromNBT(nbtTagCompound);


    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);

    }


    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();


        return stringBuilder.toString();
    }

}