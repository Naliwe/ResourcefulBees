package com.resourcefulbees.resourcefulbees.mixin;

import com.resourcefulbees.resourcefulbees.tileentity.multiblocks.apiary.ApiaryTileEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntity.EnterBeehiveGoal.class)
public abstract class MixinEnterBeehiveGoal {
    @Unique
    private BeeEntity beeEntity;

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<init>(Lnet/minecraft/entity/passive/BeeEntity;)V", at = @At(value = "RETURN"))
    private void init(BeeEntity beeEntity, CallbackInfo ci) {
        this.beeEntity = beeEntity;
    }

    @Inject(at = @At("HEAD"), method = "canBeeUse()Z", cancellable = true)
    public void canBeeStart(CallbackInfoReturnable<Boolean> cir) {
        if (beeEntity.hasHive() && beeEntity.wantsToEnterHive() && beeEntity.hivePos != null && beeEntity.hivePos.closerThan(beeEntity.position(), 2.0D)) {
            TileEntity tileentity = beeEntity.level.getBlockEntity(beeEntity.hivePos);
            if (tileentity instanceof BeehiveTileEntity) {
                BeehiveTileEntity beehivetileentity = (BeehiveTileEntity) tileentity;
                if (!beehivetileentity.isFull()) {
                    cir.setReturnValue(true);
                } else {
                    beeEntity.hivePos = null;
                }
            } else if (tileentity instanceof ApiaryTileEntity) {
                ApiaryTileEntity apiaryTileEntity = (ApiaryTileEntity) tileentity;
                if (!apiaryTileEntity.isFullOfBees()) {
                    cir.setReturnValue(true);
                } else {
                    beeEntity.hivePos = null;
                }
            }
        }
    }

    /**
     * @author epic_oreo
     * @reason crashes when switching to vanilla code due to hivePos being null. retained vanilla checks in overwrite.
     */
    @Overwrite()
    public void start() {
        if (beeEntity.hivePos != null) {
            TileEntity tileentity = beeEntity.level.getBlockEntity(beeEntity.hivePos);
            if (tileentity != null) {
                if (tileentity instanceof BeehiveTileEntity) {
                    BeehiveTileEntity beehivetileentity = (BeehiveTileEntity) tileentity;
                    beehivetileentity.addOccupant(beeEntity, beeEntity.hasNectar());
                } else if (tileentity instanceof ApiaryTileEntity) {
                    ApiaryTileEntity apiaryTileEntity = (ApiaryTileEntity) tileentity;
                    apiaryTileEntity.tryEnterHive(beeEntity, beeEntity.hasNectar(), false);
                }
            }
        }
    }
}