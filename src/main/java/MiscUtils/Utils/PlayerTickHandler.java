package MiscUtils.Utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class PlayerTickHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void RaytracingUpdate(TickEvent.PlayerTickEvent event){
        RayTracing.instance().fire();

    }
}
