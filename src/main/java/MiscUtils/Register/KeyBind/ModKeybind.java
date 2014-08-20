package MiscUtils.Register.KeyBind;

import MiscUtils.MiscUtils;
import MiscUtils.Network.PacketHandler;
import MiscUtils.Packets.SyncButtonClickEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

public abstract class ModKeybind extends KeyBinding {
    int Number;

    public ModKeybind(String Name, int KeyNumber, String Category) {
        super(Name, KeyNumber, Category);
        this.Number = KeyNumber;
    }

    public abstract void OnActivated(EntityPlayer player);




    @SubscribeEvent
    public void ButtonClickedEvent(InputEvent.KeyInputEvent event){
        if(this.getIsKeyPressed()){
            if(Minecraft.getMinecraft().thePlayer != null){
                EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
                PacketHandler.sendToServer(new SyncButtonClickEvent(pl, this), MiscUtils.channels);
            }

        }

    }

}
