package MiscUtils.Register.KeyBind;

import MiscUtils.MiscUtils;
import cpw.mods.fml.common.FMLCommonHandler;

import java.util.HashMap;

public class KeybindRegistry {
    public static HashMap<String, ModKeybind> Keybinds = new HashMap<String, ModKeybind>();


    public static ModKeybind GetKeyBind(String KeyCode){
        return Keybinds.get(KeyCode);
    }

    public static void RegisterKeybind(ModKeybind key){
        Keybinds.put(key.getKeyDescription(), key);
        FMLCommonHandler.instance().bus().register(key);
        MiscUtils.proxy.RegisterKey(key);
    }

}
