package artifacts.client.render.model.curio;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class PanicNecklaceModel extends BipedModel<LivingEntity> {

    public PanicNecklaceModel() {
        super(RenderType::entityTranslucent, 0, 0, 64, 64);

        setAllVisible(false);

        body = new ModelRenderer(this, 0, 0);
        ModelRenderer gem1 = new ModelRenderer(this, 52, 0);
        ModelRenderer gem2 = new ModelRenderer(this, 58, 0);
        ModelRenderer gem3 = new ModelRenderer(this, 52, 3);
        ModelRenderer gem4 = new ModelRenderer(this, 60, 4);

        body.addBox(-(2 * 8 + 1) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8 + 1, 2 * 12 + 1, 2 * 4 + 1);
        gem1.addBox(-2.5F, 5.5F, -5, 2, 2, 1);
        gem2.addBox(0.5F, 5.5F, -5, 2, 2, 1);
        gem3.addBox(-1.5F, 6.5F, -5, 3, 2, 1);
        gem4.addBox(-0.5F, 8.5F, -5, 1, 1, 1);

        body.addChild(gem1);
        body.addChild(gem2);
        body.addChild(gem3);
        body.addChild(gem4);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrixStack.scale(0.5F, 0.5F, 0.5F);
        body.render(matrixStack, buffer, light, overlay, red, green, blue, alpha);
    }
}
