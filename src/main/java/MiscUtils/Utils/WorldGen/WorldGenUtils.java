package MiscUtils.Utils.WorldGen;

import MiscUtils.Utils.Config.ConfigBase;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenUtils {


    public static void RegisterWorldGenerator(WorldGenerator gen, String Name, int Chance, int MaxY,  BiomeGenBase[] Biomes, World world, Random rand, int x, int y, int z, ConfigBase config){
        if(config.IsWorldGeneratorEnabled(Name)){

            int Ch = config.GetWorldGenerationChance(Name, Chance);

            ArrayList<BiomeGenBase> list = new ArrayList<BiomeGenBase>();

            if(Biomes != null)
                for(int i = 0; i < Biomes.length; i++)
                    list.add(Biomes[i]);


            if(list.contains(world.getBiomeGenForCoords(x, y)) || list.size() <= 0)
                for(int j = 0; j < Ch; j++)
                    gen.generate(world, rand, x + rand.nextInt(16), y + rand.nextInt(MaxY), z + rand.nextInt(16));


        }
    }



    //Old Method of registering world gen. Only used in MiscItemsAndBlocks until config handling is rewritten
    @Deprecated
    public static void RegisterWorldGeneratorOld(WorldGenerator gen, String Name, int Chance, int MaxY,  BiomeGenBase[] Biomes, World world, Random rand, int x, int y, int z, Configuration config){
        if(IsWorldGeneratorEnabled(Name, config)){

            int Ch = GetWorldGenerationChance(Name, Chance, config);

            ArrayList<BiomeGenBase> list = new ArrayList<BiomeGenBase>();

            if(Biomes != null)
                for(int i = 0; i < Biomes.length; i++)
                    list.add(Biomes[i]);


            if(list.contains(world.getBiomeGenForCoords(x, y)) || list.size() <= 0)
                for(int j = 0; j < Ch; j++)
                    gen.generate(world, rand, x + rand.nextInt(16), y + rand.nextInt(MaxY), z + rand.nextInt(16));


        }
    }


    public static  boolean IsWorldGeneratorEnabled(String WorldGen, Configuration config){
        boolean bl = config.get("WorldGen", "Enable Worldgen: " + WorldGen, true).getBoolean(true);

        if(config.hasChanged())
            config.save();

        return bl;
    }

    public static  int GetWorldGenerationChance(String WorldGen, int def, Configuration config){
        if(IsWorldGeneratorEnabled(WorldGen, config)){
            int t = config.get("WorldGen", "The amount of times it will try to spawn in a chunk: " + WorldGen, def).getInt(def);

            if(config.hasChanged())
                config.save();

            return t;


        }
        return 0;
    }
}


