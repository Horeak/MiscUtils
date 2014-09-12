package MiscUtils.Config;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Configuration;

import java.util.HashMap;

public abstract class ConfigBase {


    public HashMap<Block, String> BlockConfigNames = new HashMap<Block, String>();
    public HashMap<Item, String> ItemConfigNames = new HashMap<Item, String>();

    public static final String CATEGORY_BLOCKS = "Blocks";
    public static final String CATEGORY_ITEMS = "Items";
    public static final String CATEGORY_WORLDGEN = "WorldGen";


    protected void RegisterCategory(String Name, String Desc){
        GetConfigFile().addCustomCategoryComment(Name, Desc);
    }

    private Configuration config;

    public ConfigBase(Configuration config){
        this.config = config;

        RegisterCategory(CATEGORY_BLOCKS, "This allows you to enabled/disable the different blocks from the mod");
        RegisterCategory(CATEGORY_ITEMS, "This allows you to enabled/disable the different items from the mod");
        RegisterCategory(CATEGORY_WORLDGEN, "This allows you to disable and change different world generation types");

        GetConfigFile().setCategoryRequiresMcRestart("Blocks", true);
        GetConfigFile().setCategoryRequiresMcRestart(CATEGORY_ITEMS, true);

        GetConfigFile().setCategoryRequiresMcRestart(CATEGORY_WORLDGEN, true);
    }



    public Configuration GetConfigFile(){
        return config;
    }


    public abstract void InitConfig();
    public abstract void LoadConfig();




    public  boolean IsBlockEnabled(Block block){

        if(GetConfigFile() == null)
            return false;

        if(BlockConfigNames.get(block) == null)
            return false;


        boolean bl = GetConfigFile().get(CATEGORY_BLOCKS, "Enable " + BlockConfigNames.get(block).replace("tile.", "").replace(".name", ""), true).getBoolean(true);

        if(GetConfigFile().hasChanged())
            GetConfigFile().save();

        return bl;
    }



    public  boolean IsItemEnabled(Item item){
        if(GetConfigFile() == null)
            return false;

        if(ItemConfigNames.get(item) == null)
            return false;

        boolean bl = GetConfigFile().get(CATEGORY_ITEMS, "Enable " + ItemConfigNames.get(item).replace("item.", "").replace(".name", ""), true).getBoolean(true);

        if(GetConfigFile().hasChanged())
            GetConfigFile().save();

        return bl;
    }


    public  boolean IsWorldGeneratorEnabled(String WorldGen){

        if(GetConfigFile() == null)
            return false;

        boolean bl = GetConfigFile().get(CATEGORY_WORLDGEN, "Enable Worldgen: " + WorldGen, true).getBoolean(true);

        if(GetConfigFile().hasChanged())
            GetConfigFile().save();

        return bl;
    }

    public  int GetWorldGenerationChance(String WorldGen, int def){
        if(IsWorldGeneratorEnabled(WorldGen)){
            int t = GetConfigFile().get(CATEGORY_WORLDGEN, "The amount of times it will try to spawn in a chunk: " + WorldGen, def).getInt(def);

            if(GetConfigFile().hasChanged())
                GetConfigFile().save();

            return t;


        }
        return 0;
    }







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
