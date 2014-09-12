package MiscUtils.Register.KeyBind;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;

import java.util.HashMap;
@SideOnly(Side.CLIENT)
public class KeybindRegistry {
    public static HashMap<String, KeyBinding> Keybinds = new HashMap<String, KeyBinding>();


    public static KeyBinding GetKeyBind(String KeyCode){
        return Keybinds.get(KeyCode);
    }

    public static void RegisterKeybind(KeyBinding key){
            Keybinds.put(key.getKeyDescription(), key);
            ClientRegistry.registerKeyBinding(key);

    }

}
