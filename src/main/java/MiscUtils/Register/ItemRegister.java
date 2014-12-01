package MiscUtils.Register;

import MiscUtils.Config.ConfigBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;

public class ItemRegister {


    ConfigBase config;
    String Id;

    public HashMap<String, Item> map = new HashMap<String, Item>();

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

            map.put((Name.toLowerCase().replace(" ", "_")), Item);
           }
    }



    public void RegisterOutName(Item Item, String Name){
        config.ItemConfigNames.put(Item,Name);

        if(config.IsItemEnabled(Item)){
            Item.setUnlocalizedName(Name);
            GameRegistry.registerItem(Item, (Name.toLowerCase().replace(" ", "_")));

            map.put((Name.toLowerCase().replace(" ", "_")), Item);
        }
    }

    public void RegisterIcons(){
        for(Map.Entry<String, Item> entities : map.entrySet()){
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(entities.getValue(), 0, new ModelResourceLocation(Id.toLowerCase() + ":" + entities.getKey(), "inventory"));
        }

    }
}
