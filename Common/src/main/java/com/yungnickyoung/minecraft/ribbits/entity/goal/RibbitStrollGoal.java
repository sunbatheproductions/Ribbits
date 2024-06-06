package com.yungnickyoung.minecraft.ribbits.entity.goal;

import com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class RibbitStrollGoal extends RandomStrollGoal {
    private final int dayHomeRange;
    private final RibbitEntity ribbit;

    public RibbitStrollGoal(RibbitEntity mob, double speedModifier, int dayHomeRange) {
        super(mob, speedModifier);
        this.ribbit = mob;
        this.dayHomeRange = dayHomeRange;

        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public RibbitStrollGoal(RibbitEntity mob, double speedModifier, int interval, int dayHomeRange) {
        super(mob, speedModifier, interval);
        this.ribbit = mob;
        this.dayHomeRange = dayHomeRange;
    }

    public RibbitStrollGoal(RibbitEntity mob, double speedModifier, int interval, boolean checkNoActionTime, int dayHomeRange) {
        super(mob, speedModifier, interval, checkNoActionTime);
        this.ribbit = mob;
        this.dayHomeRange = dayHomeRange;
    }

    @Override
    public boolean canUse() {
        if (this.ribbit.level().isNight()) {
            return false;
        }

        return super.canUse();
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        BlockPos distanceVariance = new BlockPos(this.mob.getRandom().nextInt(this.dayHomeRange * 2) - this.dayHomeRange,
                this.mob.getRandom().nextInt(this.dayHomeRange * 2) - this.dayHomeRange,
                this.mob.getRandom().nextInt(this.dayHomeRange * 2) - this.dayHomeRange);

        return new Vec3(distanceVariance.getX() + this.ribbit.getHomePosition().getX(),
                distanceVariance.getY() + this.ribbit.getHomePosition().getY(),
                distanceVariance.getZ() + this.ribbit.getHomePosition().getZ());
    }
}
