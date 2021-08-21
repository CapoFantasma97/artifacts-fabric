package artifacts.common.item.curio.necklace;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public abstract class PendantItem extends CurioItem {

    public PendantItem() {
        addListener(LivingAttackEvent.class, this::onLivingAttack);
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        LivingEntity attacker = DamageSourceHelper.getAttacker(event.getSource());
        if (!wearer.world.isRemote()
                && event.getAmount() >= 1
                && attacker != null
                && random.nextDouble() < ModConfig.server.pendants.get(this).strikeChance.get()) {
            applyEffect(wearer, attacker);
            damageEquippedStacks(wearer);
        }
    }

    protected abstract void applyEffect(LivingEntity target, LivingEntity attacker);

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
    }
}
