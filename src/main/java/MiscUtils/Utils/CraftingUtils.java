package MiscUtils.Utils;

import MiscUtils.Config.ConfigBase;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CraftingUtils {

    ConfigBase config;

    public CraftingUtils(ConfigBase config){
        this.config = config;

    }

    public void RegisterSmelting(ItemStack Input, ItemStack Output, float Xp){
        if(StackEnabled(Output) && StackEnabled(Input))
            FurnaceRecipes.smelting().func_151394_a(Input, Output, Xp);

    }

    public void RegisterSmelting(Item Input, ItemStack Output, float Xp){
        if(StackEnabled(Output) && StackEnabled(StackUtils.GetObject(Input)))
            GameRegistry.addSmelting(Input, Output, Xp);

    }

    public void RegisterSmelting(Block Input, ItemStack Output, float Xp){
        if(StackEnabled(Output) && StackEnabled(StackUtils.GetObject(Input)))
            GameRegistry.addSmelting(Input, Output, Xp);

    }


    public void AddRecipe(ItemStack output, Object... Array){

        for(int i = 0; i < Array.length; i++)
            if(StackUtils.GetObject(Array[i]) != null)
                if(!StackEnabled(StackUtils.GetObject(Array[i])))
                    return;


        if (StackEnabled(output))
            GameRegistry.addShapedRecipe(output, Array);
    }


    public void AddShapelessRecipe(ItemStack output, Object... Array) {

        for(int i = 0; i < Array.length; i++)
            if(StackUtils.GetObject(Array[i]) != null)
                if(!StackEnabled(StackUtils.GetObject(Array[i])))
                    return;



        if (StackEnabled(output))
            GameRegistry.addShapelessRecipe(output, Array);
    }


    public void AddRecipe(IRecipe res){

        if (StackEnabled(res.getRecipeOutput()))
            GameRegistry.addRecipe(res);
    }





    public void AddShapedOreRecipe(ShapedOreRecipe res){

        for(int i = 0; i < res.getInput().length; i++)
            if(StackUtils.GetObject(res.getInput()[i]) != null)
                if(!StackEnabled(StackUtils.GetObject(res.getInput()[i])))
                    return;


        if(StackEnabled(res.getRecipeOutput()))
            AddRecipe(res);
    }

    public void AddShapelessOreRecipe(ShapelessOreRecipe res){

        for(int i = 0; i < res.getInput().size(); i++)
            if(StackUtils.GetObject(res.getInput().get(i)) != null)
                if(!StackEnabled(StackUtils.GetObject(res.getInput().get(i))))
                    return;


        if(StackEnabled(res.getRecipeOutput()))
            AddRecipe(res);
    }

    public boolean StackEnabled(ItemStack stack){

        if(stack == null || stack != null && stack.getItem() == null)
            return false;


        if (stack.getItem() instanceof ItemBlock) {
            Block bl = Block.getBlockFromItem((ItemBlock) stack.getItem());
            if (bl != null) {


                if(config.BlockConfigNames.get(bl) == null)
                    return true;

                return config.IsBlockEnabled(bl);
            }

        } else  {
            Item itm = stack.getItem();
            if (itm != null) {


                if(config.ItemConfigNames.get(itm) == null)
                    return true;

                return config.IsItemEnabled(itm);


            }
        }


        return true;
    }


}
