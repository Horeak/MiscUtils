package MiscUtils.Utils;

import com.miscitems.MiscItemsAndBlocks.Utils.References.Colours;
import com.miscitems.MiscItemsAndBlocks.Utils.References.Strings;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Comparator;


public class ItemHelper {

    private static double rand;


    public static boolean compare(ItemStack first, ItemStack second) {

        return (ItemStackComparator.compare(first, second) == 0);
    }


    public static int getColor(ItemStack itemStack) {

        NBTTagCompound nbtTagCompound = itemStack.getTagCompound();

        if (nbtTagCompound == null)
            return Integer.parseInt(Colours.PURE_WHITE, 16);
        else {

            NBTTagCompound displayTagCompound = nbtTagCompound.getCompoundTag(Strings.NBT_ITEM_DISPLAY);
            return displayTagCompound == null ? Integer.parseInt(Colours.PURE_WHITE, 16) : displayTagCompound.hasKey(Strings.NBT_ITEM_COLOR) ? displayTagCompound.getInteger(Strings.NBT_ITEM_COLOR) : Integer.parseInt(Colours.PURE_WHITE, 16);
        }
    }

    public static void setColor(ItemStack itemStack, int color) {

        if (itemStack != null) {

            NBTTagCompound nbtTagCompound = itemStack.getTagCompound();

            if (nbtTagCompound == null) {

                nbtTagCompound = new NBTTagCompound();
                itemStack.setTagCompound(nbtTagCompound);
            }

            NBTTagCompound colourTagCompound = nbtTagCompound.getCompoundTag(Strings.NBT_ITEM_DISPLAY);

            if (!nbtTagCompound.hasKey(Strings.NBT_ITEM_DISPLAY)) {
                nbtTagCompound.setTag(Strings.NBT_ITEM_DISPLAY, colourTagCompound);
            }

            colourTagCompound.setInteger(Strings.NBT_ITEM_COLOR, color);
        }
    }


    public static Comparator<ItemStack> ItemStackComparator = new Comparator<ItemStack>() {

        public int compare(ItemStack itemStack1, ItemStack itemStack2) {

            if (itemStack1 != null && itemStack2 != null) {

                if (itemStack1.getItem() == itemStack2.getItem()) {


                    if (itemStack1.getItemDamage() == itemStack2.getItemDamage()) {


                        if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound()) {


                            if (itemStack1.getTagCompound().equals(itemStack2.getTagCompound())) {
                                return (itemStack1.stackSize - itemStack2.stackSize);
                            }
                            else {
                                return (itemStack1.getTagCompound().hashCode() - itemStack2.getTagCompound().hashCode());
                            }
                        }
                        else if (!(itemStack1.hasTagCompound()) && itemStack2.hasTagCompound()) {
                            return -1;
                        }
                        else if (itemStack1.hasTagCompound() && !(itemStack2.hasTagCompound())) {
                            return 1;
                        }
                        else {
                            return (itemStack1.stackSize - itemStack2.stackSize);
                        }
                    }
                    else {
                        return (itemStack1.getItemDamage() - itemStack2.getItemDamage());
                    }
                }
                else {
                    return (itemStack1.getItem().getIdFromItem(itemStack1.getItem()) - itemStack2.getItem().getIdFromItem(itemStack2.getItem()));
                }
            }
            else if (itemStack1 != null && itemStack2 == null) {
                return -1;
            }
            else if (itemStack1 == null && itemStack2 != null) {
                return 1;
            }
            else {
                return 0;
            }

        }

    };
}