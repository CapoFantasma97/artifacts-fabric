package artifacts.client.render.curio.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class NecklaceModel extends BipedModel<LivingEntity> {

    protected NecklaceModel() {
        super(RenderType::getEntityTranslucent, 0, 0, 64, 48);
        setVisible(false);

        bipedBody = new ModelRenderer(this);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrixStack.scale(0.5F, 0.5F, 0.5F);
        bipedBody.render(matrixStack, buffer, light, overlay, red, green, blue, alpha);
    }

    private static NecklaceModel necklace() {
        NecklaceModel model = new NecklaceModel();

        // chain
        model.bipedBody.setTextureOffset(0, 0);
        model.bipedBody.addBox(-(2 * 8) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8, 2 * 12 + 1, 2 * 4 + 1);

        return model;
    }

    private static NecklaceModel centeredNecklace() {
        NecklaceModel model = new NecklaceModel();

        // chain
        model.bipedBody.setTextureOffset(0, 0);
        model.bipedBody.addBox(-(2 * 8 + 1) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8 + 1, 2 * 12 + 1, 2 * 4 + 1);

        return model;
    }

    public static NecklaceModel charmOfSinking() {
        NecklaceModel model = necklace();
        // gem
        model.bipedBody.setTextureOffset(50, 0);
        model.bipedBody.addBox(-1, 3.5F, -5, 2, 4, 1);

        return model;
    }

    public static NecklaceModel crossNecklace() {
        NecklaceModel model = centeredNecklace();

        // cross vertical
        model.bipedBody.setTextureOffset(52, 0);
        model.bipedBody.addBox(-0.5F, 4.5F, -5, 1, 4, 1);

        // cross horizontal
        model.bipedBody.setTextureOffset(56, 0);
        model.bipedBody.addBox(-1.5F, 5.5F, -5, 3, 1, 1);

        return model;
    }

    public static NecklaceModel panicNecklace() {
        NecklaceModel model = centeredNecklace();

        // gem top
        model.bipedBody.setTextureOffset(52, 0);
        model.bipedBody.addBox(-2.5F, 5.5F, -5, 2, 2, 1);
        model.bipedBody.setTextureOffset(58, 0);
        model.bipedBody.addBox(0.5F, 5.5F, -5, 2, 2, 1);

        // gem middle
        model.bipedBody.setTextureOffset(52, 3);
        model.bipedBody.addBox(-1.5F, 6.5F, -5, 3, 2, 1);

        // gem bottom
        model.bipedBody.setTextureOffset(60, 4);
        model.bipedBody.addBox(-0.5F, 8.5F, -5, 1, 1, 1);

        return model;
    }

    public static NecklaceModel pendant() {
        NecklaceModel model = necklace();

        // gem
        model.bipedBody.setTextureOffset(50, 0);
        model.bipedBody.addBox(-1, 4.5F, -5, 2, 2, 1);

        return model;
    }
}
