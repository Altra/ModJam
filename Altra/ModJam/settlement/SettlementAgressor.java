package Altra.ModJam.settlement;

import net.minecraft.entity.EntityLivingBase;

class SettlementAgressor{
    public EntityLivingBase agressor;
    public int agressionTime;

    final Settlement settlementObj;

    SettlementAgressor(Settlement s, EntityLivingBase entity, int t){
        this.settlementObj = s;
        this.agressor = entity;
        this.agressionTime = t;
    }
}
