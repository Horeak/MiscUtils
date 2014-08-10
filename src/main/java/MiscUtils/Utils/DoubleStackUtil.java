package MiscUtils.Utils;

import net.minecraft.item.ItemStack;

public class DoubleStackUtil {

    ItemStack Stack_1;
    ItemStack Stack_2;

    public DoubleStackUtil(ItemStack Stack_1, ItemStack Stack_2){
        this.Stack_1 = Stack_1;
        this.Stack_2 = Stack_2;


    }

    public ItemStack GetStack_1(){return Stack_1;}
    public ItemStack GetStack_2(){return Stack_2;}
}
