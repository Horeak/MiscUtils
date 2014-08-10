package MiscUtils.Utils.Register;

import MiscUtils.Utils.Config.ConfigBase;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemRegister {


    ConfigBase config;
    String Id;

    public ItemRegister(ConfigBase config, String ModId){
        this.config = config;
        this.Id = ModId;
    }

    public static int i = 0;

    public void SilentRegister(Item Item){
        Item.setUnlocalizedName(Id + "_SilentItem_" + i++);
        GameRegistry.registerItem(Item, Id + "_SilentItem_" + i);
    }



    public void Register(Item Item, String Name){
        config.ItemConfigNames.put(Item,Name);

        if(config.IsItemEnabled(Item)){
            Item.setUnlocalizedName((Name.toLowerCase().replace(" ", "_")));
            GameRegistry.registerItem(Item, (Name.toLowerCase().replace(" ", "_")));

        }
    }



    public void RegisterOutName(Item Item, String Name){
        config.ItemConfigNames.put(Item,Name);

        if(config.IsItemEnabled(Item)){
            Item.setUnlocalizedName(Name);
            GameRegistry.registerItem(Item, (Name.toLowerCase().replace(" ", "_")));

        }
    }
}
