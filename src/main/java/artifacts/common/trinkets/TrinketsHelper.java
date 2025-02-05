package artifacts.common.trinkets;

import artifacts.common.item.curio.CurioItem;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Helper methods for Trinkets API
 */
public final class TrinketsHelper {

	private TrinketsHelper() {
	}

	public static boolean isEquipped(Item item, LivingEntity entity) {
		return isEquipped(item, entity, false);
	}

	public static boolean isEquipped(Predicate<ItemStack> filter, LivingEntity entity) {
		return isEquipped(filter, entity, false);
	}

	public static List<ItemStack> getAllEquipped(LivingEntity entity) {
		return getAllEquipped(entity, false);
	}

	public static boolean isEquipped(Item item, LivingEntity entity, boolean ignoreEffectsDisabled) {
		return isEquipped(stack -> stack.getItem().equals(item), entity, ignoreEffectsDisabled);
	}

	public static boolean isEquipped(Predicate<ItemStack> filter, LivingEntity entity, boolean ignoreEffectsDisabled) {
		return TrinketsApi.getTrinketComponent(entity)
				.map(comp -> comp.isEquipped(stack -> (areEffectsEnabled(stack) || ignoreEffectsDisabled) && filter.test(stack)))
				.orElse(false);
	}

	public static List<ItemStack> getAllEquipped(LivingEntity entity, boolean ignoreEffectsDisabled) {
		return TrinketsApi.getTrinketComponent(entity).stream()
				.flatMap(comp -> comp.getAllEquipped().stream())
				.map(Tuple::getB)
				.filter(stack -> !stack.isEmpty() && stack.getItem() instanceof CurioItem && (areEffectsEnabled(stack) || ignoreEffectsDisabled))
				.collect(Collectors.toList());
	}

	public static boolean areEffectsEnabled(ItemStack stack) {
		return CurioItem.getArtifactStatus(stack)
				.map(CurioItem.ArtifactStatus::hasEffects)
				.orElse(false);
	}

	public static boolean areCosmetisEnabled(ItemStack stack) {
		return CurioItem.getArtifactStatus(stack)
				.map(CurioItem.ArtifactStatus::hasCosmetics)
				.orElse(false);
	}

	public static List<ItemStack> getAllEquippedForSlot(LivingEntity entity, String groupId, String slotId) {
		return getAllEquippedForSlot(entity, groupId, slotId, false);
	}

	public static List<ItemStack> getAllEquippedForSlot(LivingEntity entity, String groupId, String slotId, boolean ignoreEffectsDisabled) {
		return TrinketsApi.getTrinketComponent(entity)
				.map(TrinketComponent::getInventory)
				.flatMap(invByGroup -> Optional.ofNullable(invByGroup.get(groupId)))
				.flatMap(invBySlot -> Optional.ofNullable(invBySlot.get(slotId)))
				.stream()
				.flatMap(inv -> IntStream.range(0, inv.getContainerSize()).mapToObj(inv::getItem))
				.filter(stack -> stack.getItem() instanceof CurioItem && (areEffectsEnabled(stack) || ignoreEffectsDisabled))
				.collect(Collectors.toList());
	}
}
