package artifacts.common.item.curio.necklace;

import artifacts.common.init.ModItems;
import artifacts.common.trinkets.TrinketsHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ShockPendantItem extends PendantItem {

	public ShockPendantItem() {
		super(ShockPendantItem::applyEffect);
	}

	private static void applyEffect(LivingEntity user, Entity attacker, RandomSource random) {
		if (user != null && attacker != null && TrinketsHelper.isEquipped(ModItems.SHOCK_PENDANT, user)
				&& attacker.level.canSeeSky(attacker.blockPosition()) && random.nextFloat() < 0.25f) {
			LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(attacker.level);

			if (lightning != null) {
				lightning.moveTo(Vec3.atBottomCenterOf(attacker.blockPosition()));

				if (attacker instanceof ServerPlayer serverPlayer) {
					lightning.setCause(serverPlayer);
				}

				attacker.level.addFreshEntity(lightning);
			}
		}
	}
}
