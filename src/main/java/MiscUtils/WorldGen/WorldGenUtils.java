package MiscUtils.WorldGen;

import MiscUtils.Config.ConfigBase;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

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


            if(list.contains(world.getBiomeGenForCoords(new BlockPos(x, y, z))) || list.size() <= 0) {
                for (int j = 0; j < Ch; j++) {
                    gen.generate(world, rand, new BlockPos(x + rand.nextInt(16), y + rand.nextInt(MaxY), z + rand.nextInt(16)));
                }
            }


        }
    }
}


