package artifacts.common.item.curio.hands;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class FireGauntletItem extends CurioItem {

    public FireGauntletItem() {
        addListener(LivingAttackEvent.class, this::onLivingAttack, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        if (DamageSourceHelper.isMeleeAttack(event.getSource()) && !event.getEntity().isImmuneToFire()) {
            event.getEntity().setFire(ModConfig.server.fireGauntlet.fireDuration.get());
            damageEquippedStacks(wearer);
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 1);
    }
}
