package MiscUtils.Utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class PlayerTickHandler {

    @SubscribeEvent
    public void RaytracingUpdate(TickEvent.PlayerTickEvent event){
        RayTracing.instance().fire();

    }
}
