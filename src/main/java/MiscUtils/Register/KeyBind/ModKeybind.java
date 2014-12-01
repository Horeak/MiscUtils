package MiscUtils.Register.KeyBind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ModKeybind extends KeyBinding {
    int Number;

    public ModKeybind(String Name, int KeyNumber, String Category) {
        super(Name, KeyNumber, Category);
        this.Number = KeyNumber;
    }

    public abstract void OnActivated(EntityPlayer player);


    @SubscribeEvent
    public void ButtonClickedEvent(InputEvent.KeyInputEvent event){

        if(GameSettings.isKeyDown(this) && Minecraft.getMinecraft().currentScreen == null){
            if(Minecraft.getMinecraft().thePlayer != null){
                EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
                OnActivated(pl);
            }

        }

    }

}
