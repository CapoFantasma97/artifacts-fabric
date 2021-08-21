package artifacts.common.item.curio.hands;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class PocketPistonItem extends CurioItem {

    public PocketPistonItem() {
        addListener(LivingAttackEvent.class, this::onLivingAttack, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        float knockbackBonus = (float) (double) ModConfig.server.pocketPiston.knockbackBonus.get();
        event.getEntityLiving().applyKnockback(knockbackBonus, MathHelper.sin((float) (wearer.rotationYaw * (Math.PI / 180))), -MathHelper.cos((float) (wearer.rotationYaw * (Math.PI / 180))));
        damageEquippedStacks(wearer);
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.BLOCK_PISTON_EXTEND, 1, 1);
    }
}
