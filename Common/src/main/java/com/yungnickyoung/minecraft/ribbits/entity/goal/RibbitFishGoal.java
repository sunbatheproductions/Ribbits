package com.yungnickyoung.minecraft.ribbits.entity.goal;

import com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.Optional;

public class RibbitFishGoal extends Goal {
    private final RibbitEntity ribbit;
    private final double range;
    private final int minRequiredFishTicks;
    private final int maxRequiredFishTicks;

    private int requiredFishTicks;
    private int ticksFishing;
    private BlockPos waterPos;
    private BlockPos dryPos;

    public RibbitFishGoal(RibbitEntity ribbit, double range, int minRequiredFishTicks, int maxRequiredFishTicks) {
        this.ribbit = ribbit;
        this.range = range;
        this.minRequiredFishTicks = minRequiredFishTicks;
        this.maxRequiredFishTicks = maxRequiredFishTicks;

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public void start() {
        this.requiredFishTicks = this.ribbit.getRandom().nextInt(this.minRequiredFishTicks, this.maxRequiredFishTicks);
        this.ticksFishing = 0;
    }

    @Override
    public void stop() {
        this.waterPos = null;
        this.dryPos = null;
        this.ticksFishing = 0;

        this.ribbit.setFishing(false);
    }

    @Override
    public boolean isInterruptable() {
        return this.ticksFishing >= this.requiredFishTicks;
    }

    @Override
    public boolean canUse() {

        if (this.ribbit.level().isNight()) {
            return false;
        }

        Optional<BlockPos> waterPos = BlockPos.findClosestMatch(this.ribbit.getOnPos(), (int) range, 5, blockpos -> this.ribbit.level().getFluidState(blockpos).is(FluidTags.WATER));
        
        Optional<BlockPos> nearestDryPos;
                
        if (waterPos.isPresent()) {
            this.waterPos = waterPos.get();
            nearestDryPos = BlockPos.findClosestMatch(waterPos.get(), 1, 1, blockPos1 -> !this.ribbit.level().getFluidState(blockPos1).is(FluidTags.WATER) && !this.ribbit.level().getFluidState(blockPos1.above()).is(FluidTags.WATER));
        } else {
            return false;
        }

        nearestDryPos.ifPresent(dryPos -> this.dryPos = dryPos.above());

        return nearestDryPos.isPresent() && this.ribbit.level().getDayTime() <= 18000 && this.ribbit.level().getDayTime() >= 1000;
    }

    @Override
    public boolean canContinueToUse() {
        Iterable<BlockPos> nearbyPositions = BlockPos.betweenClosed(Mth.floor(this.ribbit.getX() - 1.5), Mth.floor(this.ribbit.getY() - 1.5), Mth.floor(this.ribbit.getZ() - 1.5), Mth.floor(this.ribbit.getX() + 1.5), this.ribbit.getBlockY(), Mth.floor(this.ribbit.getZ() + 1.5));

        boolean waterNearby = false;
        for (BlockPos nearbyPos : nearbyPositions) {
            if (this.ribbit.level().getFluidState(nearbyPos).is(FluidTags.WATER)) {
                waterNearby = true;
                break;
            }
        }

        return this.ribbit.distanceToSqr(this.dryPos.getX() + 0.5f, this.dryPos.getY() + 0.5f, this.dryPos.getZ() + 0.5f) >= 0.6f * 0.6f || waterNearby;
    }

    @Override
    public void tick() {
        if (this.ribbit.distanceToSqr(this.dryPos.getX() + 0.5f, this.dryPos.getY() + 0.5f, this.dryPos.getZ() + 0.5f) <= 0.6f * 0.6f) {
            this.ticksFishing++;
            this.ribbit.setFishing(true);
            this.ribbit.getLookControl().setLookAt(this.waterPos.getX(), this.waterPos.getY(), this.waterPos.getZ());
        } else {
            this.ribbit.setFishing(false);
            this.ribbit.getMoveControl().setWantedPosition(this.dryPos.getX() + 0.5f, this.dryPos.getY() + 0.5f, this.dryPos.getZ() + 0.5f, 2.0f);
        }
    }
}
