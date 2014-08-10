package MiscUtils.Utils.Register;

import MiscUtils.Utils.Config.ConfigBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRegister {

    ConfigBase config;

    public OreDictionaryRegister(ConfigBase config){
        this.config = config;
    }


    public void RegisterOreDictionary(String Name, ItemStack stack){
        if(stack.getItem() instanceof ItemBlock){
            Block bl = Block.getBlockFromItem(stack.getItem());

            if(config.IsBlockEnabled(bl)){
                OreDictionary.registerOre(Name, stack);
            }
        }else{

            if(config.IsItemEnabled(stack.getItem())){
                OreDictionary.registerOre(Name, stack);
            }

        }

    }
}
