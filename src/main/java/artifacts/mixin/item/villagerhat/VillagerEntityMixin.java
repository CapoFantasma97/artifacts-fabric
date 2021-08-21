package artifacts.mixin.item.villagerhat;

import artifacts.common.config.ModConfig;
import artifacts.common.init.ModItems;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Villager.class)
public abstract class VillagerEntityMixin {

    @ModifyVariable(method = "updateSpecialPrices", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/npc/Villager;getPlayerReputation(Lnet/minecraft/world/entity/player/Player;)I"))
    private int increaseReputation(int i, Player player) {
        if (ModItems.VILLAGER_HAT.get().isEquippedBy(player)) {
            i += ModConfig.server.villagerHat.reputationBonus.get();
        }
        return i;
    }
}
