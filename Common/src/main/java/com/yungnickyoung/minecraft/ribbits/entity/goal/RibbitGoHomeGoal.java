package com.yungnickyoung.minecraft.ribbits.entity.goal;

import com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class RibbitGoHomeGoal extends Goal {
    private final RibbitEntity ribbit;
    private final int homePointRange;
    private final float speedModifier;
    private final int interval;

    public RibbitGoHomeGoal(RibbitEntity ribbit, int homePointRange, float speedModifier, int interval) {
        this.ribbit = ribbit;
        this.homePointRange = homePointRange;
        this.speedModifier = speedModifier;
        this.interval = interval;
    }

    @Override
    public boolean canUse() {
        if (this.ribbit.distanceToSqr(this.ribbit.getHomePosition().getX(),
                this.ribbit.getHomePosition().getY(),
                this.ribbit.getHomePosition().getZ()) <= this.homePointRange * this.homePointRange &&
                this.ribbit.getRandom().nextInt(RandomStrollGoal.reducedTickDelay(this.interval)) != 0) {
            return false;
        }

        return this.ribbit.level().isNight();
    }

    @Override
    public boolean canContinueToUse() {
        return !this.ribbit.getNavigation().isDone() && !this.ribbit.isVehicle();
    }

    @Override
    public void start() {
        BlockPos distanceVariance = new BlockPos(this.ribbit.getRandom().nextInt(this.homePointRange * 2) - this.homePointRange,
                this.ribbit.getRandom().nextInt(this.homePointRange * 2) - this.homePointRange,
                this.ribbit.getRandom().nextInt(this.homePointRange * 2) - this.homePointRange);


        this.ribbit.getNavigation().moveTo(distanceVariance.getX() + this.ribbit.getHomePosition().getX(),
                distanceVariance.getY() + this.ribbit.getHomePosition().getY(),
                distanceVariance.getZ() + this.ribbit.getHomePosition().getZ(), this.speedModifier);
    }

    @Override
    public void stop() {
        this.ribbit.getNavigation().stop();
    }
}
