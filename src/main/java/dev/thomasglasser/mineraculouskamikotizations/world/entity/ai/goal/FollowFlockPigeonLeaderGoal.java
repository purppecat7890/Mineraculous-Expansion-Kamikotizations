package dev.thomasglasser.mineraculouskamikotizations.world.entity.ai.goal;

import com.mojang.datafixers.DataFixUtils;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.animal.AbstractFlockingBird;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.ai.goal.Goal;

public class FollowFlockPigeonLeaderGoal extends Goal {
    private static final int INTERVAL_TICKS = 200;
    private final AbstractFlockingBird mob;
    private int timeToRecalcPath;
    private int nextStartTick;

    public FollowFlockPigeonLeaderGoal(AbstractFlockingBird bird) {
        this.mob = bird;
        this.nextStartTick = this.nextStartTick(bird);
    }

    protected int nextStartTick(AbstractFlockingBird taskOwner) {
        return reducedTickDelay(200 + taskOwner.getRandom().nextInt(200) % 20);
    }

    @Override
    public boolean canUse() {
        if (this.mob.hasFollowers()) {
            return false;
        } else if (this.mob.isFollower()) {
            return true;
        } else if (this.nextStartTick > 0) {
            this.nextStartTick--;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(this.mob);
            Predicate<AbstractFlockingBird> predicate = bird -> bird.canBeFollowed() || !bird.isFollower();
            List<? extends AbstractFlockingBird> list = this.mob
                    .level()
                    .getEntitiesOfClass((Class<? extends AbstractFlockingBird>) this.mob.getClass(), this.mob.getBoundingBox().inflate(8.0, 8.0, 8.0), predicate);
            AbstractFlockingBird abstractFlockingBird = DataFixUtils.orElse(list.stream().filter(AbstractFlockingBird::canBeFollowed).findAny(), this.mob);
            abstractFlockingBird.addFollowers(list.stream().filter(bird -> !bird.isFollower()));
            return this.mob.isFollower();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.isFollower() && this.mob.inRangeOfLeader();
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.mob.stopFollowing();
    }

    @Override
    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.mob.pathToLeader();
        }
    }
}
