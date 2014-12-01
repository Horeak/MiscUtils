package MiscUtils.Register.KeyBind;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
@SideOnly(Side.CLIENT)
public class KeybindRegistry {
    public static HashMap<String, KeyBinding> Keybinds = new HashMap<String, KeyBinding>();


    public static KeyBinding GetKeyBind(String KeyCode){
        return Keybinds.get(KeyCode);
    }

    public static void RegisterKeybind(ModKeybind key){
            Keybinds.put(key.getKeyDescription(), key);
            ClientRegistry.registerKeyBinding(key);
            FMLCommonHandler.instance().bus().register(key);



    }

}
