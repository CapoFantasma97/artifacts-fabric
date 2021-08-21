package artifacts.common.item.curio.necklace;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class PanicNecklaceItem extends CurioItem {

    public PanicNecklaceItem() {
        addListener(LivingHurtEvent.class, this::onLivingHurt);
    }

    private void onLivingHurt(LivingHurtEvent event, LivingEntity wearer) {
        if (!wearer.world.isRemote && event.getAmount() >= 1) {
            int duration = ModConfig.server.panicNecklace.speedDuration.get();
            int level = ModConfig.server.panicNecklace.speedLevel.get() - 1;

            if (duration > 0 && level >= 0) {
                wearer.addPotionEffect(new EffectInstance(Effects.SPEED, duration, level, false, false));
                damageEquippedStacks(wearer);
            }
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
    }
}
