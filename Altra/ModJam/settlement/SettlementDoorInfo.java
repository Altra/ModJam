package Altra.ModJam.settlement;

public class SettlementDoorInfo{
    public final int posX;
    public final int posY;
    public final int posZ;
    public final int insideDirectionX;
    public final int insideDirectionZ;
    public int lastActivityTimestamp;
    public boolean isDetachedFromVillageFlag;
    private int doorOpeningRestrictionCounter;

    public SettlementDoorInfo(int x, int y, int z, int inDirX, int inDirZ, int TS){
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.insideDirectionX = inDirX;
        this.insideDirectionZ = inDirZ;
        this.lastActivityTimestamp = TS;
    }

    /**
     * Returns the squared distance between this door and the given coordinate.
     */
    public int getDistanceSquared(int par1, int par2, int par3)
    {
        int l = par1 - this.posX;
        int i1 = par2 - this.posY;
        int j1 = par3 - this.posZ;
        return l * l + i1 * i1 + j1 * j1;
    }

    /**
     * Get the square of the distance from a location 2 blocks away from the door considered 'inside' and the given
     * arguments
     */
    public int getInsideDistanceSquare(int par1, int par2, int par3)
    {
        int l = par1 - this.posX - this.insideDirectionX;
        int i1 = par2 - this.posY;
        int j1 = par3 - this.posZ - this.insideDirectionZ;
        return l * l + i1 * i1 + j1 * j1;
    }

    public int getInsidePosX()
    {
        return this.posX + this.insideDirectionX;
    }

    public int getInsidePosY()
    {
        return this.posY;
    }

    public int getInsidePosZ()
    {
        return this.posZ + this.insideDirectionZ;
    }

    public boolean isInside(int par1, int par2)
    {
        int k = par1 - this.posX;
        int l = par2 - this.posZ;
        return k * this.insideDirectionX + l * this.insideDirectionZ >= 0;
    }

    public void resetDoorOpeningRestrictionCounter()
    {
        this.doorOpeningRestrictionCounter = 0;
    }

    public void incrementDoorOpeningRestrictionCounter()
    {
        ++this.doorOpeningRestrictionCounter;
    }

    public int getDoorOpeningRestrictionCounter()
    {
        return this.doorOpeningRestrictionCounter;
    }
}
