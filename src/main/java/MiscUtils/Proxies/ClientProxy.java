package MiscUtils.Proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class ClientProxy extends ServerProxy {

    public void RegisterKey(KeyBinding key){
        ClientRegistry.registerKeyBinding(key);
    }
}
