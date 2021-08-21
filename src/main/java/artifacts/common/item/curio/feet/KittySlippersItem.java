package artifacts.common.item.curio.feet;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.HurtSoundModifyingItem;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class KittySlippersItem extends HurtSoundModifyingItem {

    public KittySlippersItem() {
        super(SoundEvents.ENTITY_CAT_HURT);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
        addListener(LivingSetAttackTargetEvent.class, this::onLivingSetAttackTarget, LivingSetAttackTargetEvent::getTarget);
        addListener(LivingEvent.LivingUpdateEvent.class, this::onLivingUpdate, event -> event.getEntityLiving().getRevengeTarget());
        addListener(LivingAttackEvent.class, this::onLivingAttack, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    private void onLivingAttack(LivingAttackEvent event, LivingEntity wearer) {
        if (event.getEntityLiving() instanceof CreeperEntity) {
            damageEquippedStacks(wearer);
        }
    }

    private void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!ModConfig.server.isCosmetic(this) && event.getEntity() instanceof CreeperEntity) {
            ((CreeperEntity) event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>((CreeperEntity) event.getEntity(), PlayerEntity.class, (entity) -> entity != null && isEquippedBy(entity), 6, 1, 1.3, EntityPredicates.CAN_AI_TARGET::test));
        }
    }

    private void onLivingSetAttackTarget(LivingSetAttackTargetEvent event, LivingEntity wearer) {
        if (event.getEntityLiving() instanceof CreeperEntity) {
            ((MobEntity) event.getEntityLiving()).setAttackTarget(null);
        }
    }

    private void onLivingUpdate(LivingEvent.LivingUpdateEvent event, LivingEntity wearer) {
        if (event.getEntityLiving() instanceof CreeperEntity) {
            event.getEntityLiving().setRevengeTarget(null);
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ENTITY_CAT_AMBIENT, 1, 1);
    }
}
