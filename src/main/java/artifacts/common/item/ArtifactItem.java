package artifacts.common.item;

import artifacts.Artifacts;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.Collections;
import java.util.List;

public abstract class ArtifactItem extends Item {

	public ArtifactItem(Properties properties) {
		super(properties.stacksTo(1).tab(Artifacts.CREATIVE_TAB).rarity(Rarity.RARE).fireResistant());
	}

	public ArtifactItem() {
		this(new Properties());
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
		if (Artifacts.CONFIG.general.showTooltips) {
			appendTooltipDescription(tooltip, this.getDescriptionId() + ".tooltip");
		}
	}

	public Component getREITooltip() {
		return Component.literal(Language.getInstance().getOrDefault(this.getDescriptionId() + ".tooltip").replace("\n", " "));
	}

	protected void appendTooltipDescription(List<Component> tooltip, String translKey) {
		String[] lines = String.format(Language.getInstance().getOrDefault(translKey), getTooltipDescriptionArguments().toArray()).split("\n");

		for (String line : lines) {
			tooltip.add(Component.literal(line).withStyle(ChatFormatting.GRAY));
		}
	}

	protected List<String> getTooltipDescriptionArguments() {
		return Collections.emptyList();
	}
}
