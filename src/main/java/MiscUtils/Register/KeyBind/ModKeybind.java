package MiscUtils.Register.KeyBind;

import MiscUtils.MiscUtils;
import MiscUtils.Network.PacketHandler;
import MiscUtils.Packets.SyncButtonClickEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

public abstract class ModKeybind extends KeyBinding {
    int Number;

    public ModKeybind(String Name, int KeyNumber, String Category) {
        super(Name, KeyNumber, Category);
        this.Number = KeyNumber;
    }

    public abstract void OnActivated(EntityPlayer player, Side side);


    @SubscribeEvent
    public void ButtonClickedEvent(InputEvent.KeyInputEvent event){
        if(GameSettings.isKeyDown(this) && Minecraft.getMinecraft().currentScreen == null){
            if(Minecraft.getMinecraft().thePlayer != null){
                EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
                PacketHandler.sendToServer(new SyncButtonClickEvent(pl, this), MiscUtils.Utils.channels);
                OnActivated(pl, Side.CLIENT);
            }

        }

    }

}
