package MiscUtils.Register;

import MiscUtils.Config.ConfigBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRegister {

    ConfigBase config;
    String Id;

    public BlockRegister(ConfigBase config, String ModId){
        this.config = config;
        this.Id = ModId;
    }

    public void Register(Block block, String Name){
        config.BlockConfigNames.put(block, Name);

        if(config.IsBlockEnabled(block)){

            block.setUnlocalizedName(Name.toLowerCase().replace(" ", "_"));
            GameRegistry.registerBlock(block, Name.toLowerCase().replace(" ", "_"));
        }
    }


    public void Register(Block block, String Name, Class<? extends TileEntity> tileClass){
        config.BlockConfigNames.put(block, Name);

        if(config.IsBlockEnabled(block)){

            Register(block, Name);
            GameRegistry.registerTileEntity(tileClass, "["+Id+"]" + Name);
        }
    }



    public void Register(Block Block, Class<? extends ItemBlock> itemclass, String Name) {
        config.BlockConfigNames.put(Block, Name);

        if (config.IsBlockEnabled(Block)) {

            Block.setUnlocalizedName(Name.toLowerCase().replace(" ", "_"));
            GameRegistry.registerBlock(Block, itemclass, Name.toLowerCase().replace(" ", "_"));
        }
    }



    public void Register(Block Block, Class<? extends ItemBlock> itemclass, String Name, Class<? extends TileEntity> tileClass){
        config.BlockConfigNames.put(Block, Name);

        if(config.IsBlockEnabled(Block)){
            Block.setUnlocalizedName(Name.toLowerCase().replace(" ", "_"));
            GameRegistry.registerBlock(Block, itemclass, Name.toLowerCase().replace(" ", "_"));
            GameRegistry.registerTileEntity(tileClass, "["+Id+"]" + Name);
        }
    }
}
