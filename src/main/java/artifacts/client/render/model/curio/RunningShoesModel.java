package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class RunningShoesModel extends BipedModel<LivingEntity> {

    public RunningShoesModel() {
        super(1, 0, 32, 32);

        leftLeg = new ModelRenderer(this, 0, 0);
        rightLeg = new ModelRenderer(this, 16, 0);
        ModelRenderer leftShoeTip = new ModelRenderer(this, 0, 16);
        ModelRenderer rightShoeTip = new ModelRenderer(this, 16, 16);

        leftLeg.addBox(-2, 0, -2, 4, 12, 4, 0.5F);
        rightLeg.addBox(-2, 0, -2, 4, 12, 4, 0.5F);
        leftShoeTip.addBox(-2, 9.375F, -3.625F, 4, 3, 1, 0.5F, 0.125F, 0.125F);
        rightShoeTip.addBox(-2, 9.375F, -3.625F, 4, 3, 1, 0.5F, 0.125F, 0.125F);

        leftLeg.addChild(leftShoeTip);
        rightLeg.addChild(rightShoeTip);

        setAllVisible(false);
        leftLeg.visible = rightLeg.visible = true;
    }
}
