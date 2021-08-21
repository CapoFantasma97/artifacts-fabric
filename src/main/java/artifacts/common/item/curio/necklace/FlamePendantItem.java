package artifacts.common.item.curio.necklace;

import artifacts.common.config.ModConfig;
import net.minecraft.entity.LivingEntity;

public class FlamePendantItem extends PendantItem {

    @Override
    protected void applyEffect(LivingEntity target, LivingEntity attacker) {
        if (!attacker.isImmuneToFire() && attacker.attackable()) {
            attacker.setFire(ModConfig.server.flamePendant.fireDuration.get());
        }
    }
}
