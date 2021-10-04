package artifacts.item.curio.feet;

import artifacts.init.Components;
import artifacts.init.Items;
import artifacts.init.Slot;
import artifacts.item.curio.TrinketArtifactItem;
import artifacts.trinkets.TrinketsHelper;
import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.FluidState;

public class AquaDashersItem extends TrinketArtifactItem {

	public AquaDashersItem() {
		super(Slot.SHOES);
		//noinspection UnstableApiUsage
        LivingFluidCollisionCallback.EVENT.register(AquaDashersItem::onFluidCollision);
	}

	@Override
	public void tick(Player player, ItemStack stack) {
		Components.SWIM_ABILITIES.maybeGet(player).ifPresent(swimAbilities -> {
			if (player.isInWater()) {
				swimAbilities.setWet(true);
			} else if (player.isOnGround() || player.abilities.flying) {
				swimAbilities.setWet(false);
			}
		});

		super.tick(player, stack);
	}

	private static boolean onFluidCollision(LivingEntity entity, FluidState fluidState) {
		if (TrinketsHelper.isEquipped(Items.AQUA_DASHERS, entity) && canSprintOnWater(entity)) {
			if (fluidState.is(FluidTags.LAVA) && !entity.fireImmune() && !EnchantmentHelper.hasFrostWalker(entity)) {
				entity.hurt(DamageSource.HOT_FLOOR, 1);
			}
			return true;
		}

		return false;
	}

	public static boolean canSprintOnWater(LivingEntity entity) {
		return Components.SWIM_ABILITIES.maybeGet(entity)
				.map(swimAbilities -> entity.isSprinting()
						&& entity.fallDistance < 6
						&& !entity.isUsingItem()
						&& !entity.isCrouching()
						&& !swimAbilities.isWet()
						&& !swimAbilities.isSwimming())
				.orElse(false);
	}
}
