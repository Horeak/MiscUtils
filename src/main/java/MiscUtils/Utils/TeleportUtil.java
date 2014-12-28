package MiscUtils.Utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

public class TeleportUtil extends Teleporter {

    private WorldServer worldserver;

    public TeleportUtil(WorldServer worldserver) {
        super(worldserver);

        this.worldserver = worldserver;

    }
    public void teleport(Entity entity, World world) {

        EntityPlayerMP playerMP = (EntityPlayerMP) entity;

        double dx = 0;
        double dy = 0;
        double dz = 0;

        WorldProvider world_property = worldserver.provider;

        if (world_property != null) {

            dx = world_property.getSpawnPoint().posX;
            dy = world_property.getSpawnPoint().posY;
            dz = world_property.getSpawnPoint().posZ;

        }

        if (dx == 0 && dy == 0 && dz == 0) {

            dy = 250;

            while (world.getBlock((int) dx, (int) dy - 1, (int) dz).equals(Blocks.air) && dy > 0) {
                dy--;
            }

            if (dy == 0) {
                dy = 128;
            }
        }
        dx = dx + 0.5d;
        dy = dy + 1.0d;
        dz = dz + 0.5d;
        entity.setPosition(dx, dy, dz);

        entity.motionX = entity.motionY = entity.motionZ = 0.0D;
        entity.setPosition(dx, dy, dz);

        if (entity.worldObj.provider.dimensionId != world.provider.dimensionId) {
            playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, world.provider.dimensionId, this);
        }

        entity.setPosition(dx, dy, dz); // silly to do this multiple time,s but it kept offseting entity until this was done

    }

    @Override
    public boolean placeInExistingPortal(Entity par1Entity, double par2, double par4, double par6, float par8)
    {
        return false;
    }

    @Override
    public void removeStalePortalLocations(long par1)
    {
    }

    @Override
    public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8)
    {
    }

}
