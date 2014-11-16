package MiscUtils.GuideBase.Utils;

import MiscUtils.Utils.StackUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class GuideTab {



    public GuideTab(GuideInstance gd, Object stack, String Name){
        instance = gd;
        this.stack = StackUtils.GetObject(stack);
        this.Name = Name;
    }

    public ItemStack stack = null;
    public String Name;
    public GuideInstance instance = null;
    public ArrayList list = new ArrayList();

    public void Register(Object r){
        ItemStack stack = StackUtils.GetObject(r);
        list.add(stack);

    }

    //TODO Maybe change to language specific version? (blockinfo_en_US.txt)? Get language code then access file.
    //TODO Create file if missing?
    //TODO Add empty properties key if missing?

    public String GetInfoForStack(Object r){
        ItemStack stack = StackUtils.GetObject(r);

        if(stack.getItem() instanceof ItemBlock){
            GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(Block.getBlockFromItem(stack.getItem()));
            if(id != null) {

                InputStream inputstream = StringTranslate.class.getResourceAsStream("/assets/" + instance.BlockDescriptions().getResourceDomain() + "/" + instance.BlockDescriptions().getResourcePath());
                try {
                    if(inputstream != null) {
                        Properties prop = new Properties();
                        prop.load(inputstream);


                        return prop.getProperty("block.info." + stack.getDisplayName().toLowerCase().replace(" ", "_"), "No info found for block! Check blockinfo file! file path: " + "\n" + "/assets/" + instance.BlockDescriptions().getResourceDomain() + "/" + instance.BlockDescriptions().getResourcePath());

                    }

                }catch(Exception e){
                    e.printStackTrace();
                }


            }
        }else{
            GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(stack.getItem());
            if(id != null) {

                InputStream inputstream = StringTranslate.class.getResourceAsStream("/assets/" + instance.ItemDescriptions().getResourceDomain() + "/" + instance.ItemDescriptions().getResourcePath());

                try {
                    if(inputstream != null) {
                        Properties prop = new Properties();
                        prop.load(inputstream);


                        return prop.getProperty("item.info." + stack.getDisplayName().toLowerCase().replace(" ", "_"), "No info found for item! Check iteminfo file! file path: " + "\n" + "/assets/" + instance.ItemDescriptions().getResourceDomain() + "/" + instance.ItemDescriptions().getResourcePath());

                    }

                }catch(Exception e){
                    e.printStackTrace();
                }


            }

        }

        return "EMPTY STRING";
    }
}
