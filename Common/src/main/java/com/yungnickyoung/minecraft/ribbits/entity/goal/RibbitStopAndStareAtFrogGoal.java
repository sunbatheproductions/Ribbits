package com.yungnickyoung.minecraft.ribbits.entity.goal;


import com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.frog.Frog;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class RibbitStopAndStareAtFrogGoal extends Goal {
    private final RibbitEntity ribbit;
    private final double speedModifier;
    @Nullable
    private Frog followingFrog;
    private int timeToRecalcPath;
    private final float stopDistance;
    private float oldWaterCost;
    private final float searchRadius;

    public RibbitStopAndStareAtFrogGoal(RibbitEntity ribbit, double speedModifier, float stopDistance, float searchRadius) {
        this.ribbit = ribbit;
        this.speedModifier = speedModifier;
        this.stopDistance = stopDistance;
        this.searchRadius = searchRadius;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
//        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        List<Frog> nearbyFrogs = this.ribbit.level().getEntitiesOfClass(Frog.class, this.ribbit.getBoundingBox().inflate(this.searchRadius), target -> target instanceof Frog);
        if (!nearbyFrogs.isEmpty()) {
            for (Frog frog : nearbyFrogs) {
                if (!frog.isInvisible() && this.ribbit.hasLineOfSight(frog)) {
                    this.followingFrog = frog;
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
//        return this.followingFrog != null
//                && !this.mob.getNavigation().isDone()
//                && this.mob.distanceToSqr(this.followingFrog) > (double) (this.stopDistance * this.stopDistance);
        return this.followingFrog != null
                && !this.followingFrog.isInvisible()
                && this.ribbit.hasLineOfSight(this.followingFrog)
                && this.ribbit.distanceToSqr(this.followingFrog) <= (this.searchRadius * this.searchRadius);
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.followingFrog = null;
//        this.mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (this.followingFrog != null && !this.ribbit.isLeashed()) {
            this.ribbit.getLookControl().setLookAt(this.followingFrog, 10.0F, (float) this.ribbit.getMaxHeadXRot());
//            if (--this.timeToRecalcPath <= 0) {
//                this.timeToRecalcPath = this.adjustedTickDelay(10);
//                double xDist = this.followingFrog.getX() - this.mob.getX();
//                double yDist = this.followingFrog.getY() - this.mob.getY();
//                double zDist = this.followingFrog.getZ() - this.mob.getZ();
//                double sqrDist = xDist * xDist + yDist * yDist + zDist * zDist;
//                if (sqrDist > this.stopDistance * this.stopDistance) {
//                    this.mob.getNavigation().moveTo(this.followingFrog, this.speedModifier);
//                } else {
//                    this.mob.getNavigation().stop();
//                    LookControl lookControl = this.followingFrog.getLookControl();
//                    if (sqrDist <= this.stopDistance
//                            || lookControl.getWantedX() == this.mob.getX() && lookControl.getWantedY() == this.mob.getY() && lookControl.getWantedZ() == this.mob.getZ()) {
//                        this.mob.getNavigation().moveTo(this.mob.getX() - xDist, this.mob.getY(), this.mob.getZ() - zDist, this.speedModifier);
//                    }
//                }
//            }
        }
    }
}
