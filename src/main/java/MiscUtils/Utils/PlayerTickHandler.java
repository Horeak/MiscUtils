package MiscUtils.Utils;


import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerTickHandler {

    @SubscribeEvent
    public void RaytracingUpdate(TickEvent.PlayerTickEvent event){
        RayTracing.instance().fire();

    }
}
