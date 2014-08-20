package MiscUtils.Inventory;




import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Used from BuildCraft source
 */
public class Position {

        public double x, y, z;
        public ForgeDirection orientation;

        public Position(double ci, double cj, double ck) {
                x = ci;
                y = cj;
                z = ck;
                orientation = ForgeDirection.UNKNOWN;
        }

        public Position(double ci, double cj, double ck, ForgeDirection corientation) {
                x = ci;
                y = cj;
                z = ck;
                orientation = corientation;
        }

        public Position(Position p) {
                x = p.x;
                y = p.y;
                z = p.z;
                orientation = p.orientation;
        }

        public Position(NBTTagCompound nbttagcompound) {
                x = nbttagcompound.getDouble("i");
                y = nbttagcompound.getDouble("j");
                z = nbttagcompound.getDouble("k");

                orientation = ForgeDirection.UNKNOWN;
        }

        public Position(TileEntity tile) {
                x = tile.xCoord;
                y = tile.yCoord;
                z = tile.zCoord;
        }




        public void moveForwards(double step) {
                switch (orientation) {
                case UP:
                        y = y + step;
                        break;
                case DOWN:
                        y = y - step;
                        break;
                case SOUTH:
                        z = z + step;
                        break;
                case NORTH:
                        z = z - step;
                        break;
                case EAST:
                        x = x + step;
                        break;
                case WEST:
                        x = x - step;
                        break;
                default:
                }
        }





        public void writeToNBT(NBTTagCompound nbttagcompound) {
                nbttagcompound.setDouble("i", x);
                nbttagcompound.setDouble("j", y);
                nbttagcompound.setDouble("k", z);
        }

        @Override
        public String toString() {
                return "{" + x + ", " + y + ", " + z + "}";
        }

        public Position min(Position p) {
                return new Position(p.x > x ? x : p.x, p.y > y ? y : p.y, p.z > z ? z : p.z);
        }

        public Position max(Position p) {
                return new Position(p.x < x ? x : p.x, p.y < y ? y : p.y, p.z < z ? z : p.z);
        }

}