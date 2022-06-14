package artifacts.common.item.curio.necklace;

import artifacts.common.init.ModItems;
import artifacts.common.trinkets.TrinketsHelper;
import artifacts.mixin.accessors.DamageSourceAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class FlamePendantItem extends PendantItem {

	public FlamePendantItem() {
		super(FlamePendantItem::applyEffects);
	}

	private static void applyEffects(LivingEntity user, Entity attacker, RandomSource random) {
		if (user != null && attacker != null && TrinketsHelper.isEquipped(ModItems.FLAME_PENDANT, user) && random.nextFloat() < 0.4f) {
			attacker.setSecondsOnFire(10);
			//noinspection ConstantConditions
			DamageSource setFireSource = ((DamageSourceAccessor) new EntityDamageSource("onFire", user)).callSetIsFire();
			attacker.hurt(setFireSource, 2);
		}
	}
}
