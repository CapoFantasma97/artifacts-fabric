package artifacts.common.item.curio.necklace;

import artifacts.common.init.ModItems;
import artifacts.common.trinkets.TrinketsHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class ThornPendantItem extends PendantItem {

	public ThornPendantItem() {
		super(ThornPendantItem::applyEffects);
	}

	private static void applyEffects(LivingEntity user, Entity attacker, RandomSource random) {
		if (user != null && attacker != null && TrinketsHelper.isEquipped(ModItems.THORN_PENDANT, user) && random.nextFloat() < 0.5f) {
			attacker.hurt(DamageSource.thorns(user), 2 + random.nextInt(5));
		}
	}
}
