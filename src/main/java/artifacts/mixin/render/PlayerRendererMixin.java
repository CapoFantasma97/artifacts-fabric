package artifacts.mixin.render;

import artifacts.client.render.curio.CurioRenderers;
import artifacts.client.render.curio.renderer.GloveCurioRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public PlayerRendererMixin(EntityRenderDispatcher manager, PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(manager, model, shadowRadius);
    }

    @Inject(method = "renderLeftHand", at = @At("TAIL"))
    private void renderLeftGlove(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, CallbackInfo callbackInfo) {
        renderArm(matrixStack, buffer, light, player, HumanoidArm.LEFT);
    }

    @Inject(method = "renderRightHand", at = @At("TAIL"))
    private void renderRightGlove(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, CallbackInfo callbackInfo) {
        renderArm(matrixStack, buffer, light, player, HumanoidArm.RIGHT);
    }

    @Unique
    private static void renderArm(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, HumanoidArm handSide) {
        InteractionHand hand = handSide == player.getMainArm() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;

        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
            ICurioStacksHandler stacksHandler = handler.getCurios().get(SlotTypePreset.HANDS.getIdentifier());
            if (stacksHandler != null) {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                IDynamicStackHandler cosmeticStacks = stacksHandler.getCosmeticStacks();

                for (int slot = hand == InteractionHand.MAIN_HAND ? 0 : 1; slot < stacks.getSlots(); slot += 2) {
                    ItemStack stack = cosmeticStacks.getStackInSlot(slot);
                    if (stack.isEmpty()) {
                        if (stacksHandler.getRenders().get(slot)) {
                            stack = stacks.getStackInSlot(slot);
                        }
                    }

                    GloveCurioRenderer renderer = CurioRenderers.getGloveRenderer(stack);
                    if (renderer != null) {
                        renderer.renderFirstPersonArm(matrixStack, buffer, light, player, handSide, stack.hasFoil());
                    }
                }
            }
        });
    }
}
