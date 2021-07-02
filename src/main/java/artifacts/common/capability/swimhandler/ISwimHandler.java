package artifacts.common.capability.swimhandler;

import net.minecraft.entity.player.ServerPlayerEntity;

public interface ISwimHandler {

    boolean isSwimming();

    boolean isSinking();

    boolean isWet();

    int getSwimTime();

    void setSwimming(boolean shouldSwim);

    void setSinking(boolean shouldSink);

    void setWet(boolean hasTouchedWater);

    void setSwimTime(int swimTime);

    void syncSwimming();

    void syncSinking(ServerPlayerEntity player);
}
