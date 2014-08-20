package MiscUtils.Config;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Configuration;

import java.util.HashMap;

public abstract class ConfigBase {


    public static HashMap<Block, String> BlockConfigNames = new HashMap<Block, String>();
    public static HashMap<Item, String> ItemConfigNames = new HashMap<Item, String>();

    public static final String CATEGORY_CLIENT_SETTINGS = "Client Settings";
    public static final String CATEGORY_SERVER_SETTINGS = "Server Settings";
    public static final String CATEGORY_BLOCKS = "Blocks";
    public static final String CATEGORY_ITEMS = "Items";
    public static final String CATEGORY_WORLDGEN = "WorldGen";

    public abstract Configuration GetConfigFile();
    public abstract void InitConfig();
    public abstract void LoadConfig();

    public abstract boolean IsWorldGeneratorEnabled(String Name);
    public abstract int GetWorldGenerationChance(String Name, int def);

    public abstract boolean IsBlockEnabled(Block bl);
    public abstract boolean IsItemEnabled(Item itm);

    public Block GetCheckedBlock(Block bl){
        if(IsBlockEnabled(bl))
            return bl;
        else
            return Blocks.bedrock;
    }


    public Item GetCheckedItem(Item item){
        if(IsItemEnabled(item)){
            return item;
        }else{
            return ItemBlock.getItemFromBlock(Blocks.bedrock);
        }

    }




}
