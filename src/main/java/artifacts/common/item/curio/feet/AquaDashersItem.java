package artifacts.common.item.curio.feet;

import artifacts.common.capability.swimhandler.ISwimHandler;
import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import artifacts.common.item.curio.CurioItem;
import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.eventbus.api.Event;

public class AquaDashersItem extends CurioItem {

    public AquaDashersItem() {
        addListener(LivingFluidCollisionEvent.class, this::onFluidCollision);
    }

    private void onFluidCollision(LivingFluidCollisionEvent event, LivingEntity wearer) {
        if (wearer.isSprinting() && wearer.fallDistance < 6 && !wearer.isHandActive() && !wearer.isCrouching()) {
            wearer.getCapability(SwimHandlerCapability.INSTANCE).ifPresent(handler -> {
                if (!handler.isWet() && !handler.isSwimming()) {
                    event.setResult(Event.Result.ALLOW);
                    if (event.getFluidState().isTagged(FluidTags.LAVA)) {
                        if (!wearer.isImmuneToFire() && !EnchantmentHelper.hasFrostWalker(wearer)) {
                            wearer.attackEntityFrom(DamageSource.HOT_FLOOR, 1);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {
        if (entity.ticksExisted % 20 == 0 && isSprintingOnFluid(entity)) {
            damageStack(identifier, index, entity, stack);
        }
    }

    public boolean isSprinting(LivingEntity entity) {
        return isEquippedBy(entity)
                && entity.isSprinting()
                && entity.fallDistance < 6
                && !entity.getCapability(SwimHandlerCapability.INSTANCE).map(ISwimHandler::isWet).orElse(true);
    }

    private boolean isSprintingOnFluid(LivingEntity entity) {
        if (isSprinting(entity)) {
            BlockPos pos = new BlockPos(MathHelper.floor(entity.getPosX()), MathHelper.floor(entity.getPosY() - 0.2), MathHelper.floor(entity.getPosZ()));
            return !entity.world.getBlockState(pos).getFluidState().isEmpty();
        }
        return false;
    }
}
