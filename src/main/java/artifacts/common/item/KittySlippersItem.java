package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.KittySlippersModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class KittySlippersItem extends HurtSoundModifyingItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/kitty_slippers.png");

    public KittySlippersItem() {
        super(SoundEvents.CAT_HURT);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
        addListener(LivingSetAttackTargetEvent.class, this::onLivingSetAttackTarget, LivingSetAttackTargetEvent::getTarget);
        addListener(LivingEvent.LivingUpdateEvent.class, this::onLivingUpdate, event -> event.getEntityLiving().getLastHurtByMob());
    }

    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof CreeperEntity) {
            ((CreeperEntity) event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>((CreeperEntity) event.getEntity(), PlayerEntity.class, (entity) -> entity != null && isEquippedBy(entity), 6, 1, 1.3, EntityPredicates.NO_CREATIVE_OR_SPECTATOR::test));
        }
    }

    public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        if (event.getEntityLiving() instanceof CreeperEntity) {
            ((MobEntity) event.getEntityLiving()).setTarget(null);
        }
    }

    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof CreeperEntity) {
            event.getEntityLiving().setLastHurtByMob(null);
        }
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.CAT_AMBIENT, 1, 1);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new KittySlippersModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
