package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class BunnyHoppersModel extends BipedModel<LivingEntity> {

    public BunnyHoppersModel() {
        super(0.51F);
        ModelRenderer head1 = new ModelRenderer(this, 0, 0);
        ModelRenderer head2 = new ModelRenderer(this, 32, 0);
        ModelRenderer earLeft1 = new ModelRenderer(this, 20, 0);
        ModelRenderer earLeft2 = new ModelRenderer(this, 52, 0);
        ModelRenderer earRight1 = new ModelRenderer(this, 26, 0);
        ModelRenderer earRight2 = new ModelRenderer(this, 58, 0);
        ModelRenderer bipedLeftLegwear = new ModelRenderer(this, 16, 16);
        ModelRenderer bipedRightLegwear = new ModelRenderer(this, 48, 16);
        ModelRenderer nose1 = new ModelRenderer(this, 0, 9);
        ModelRenderer nose2 = new ModelRenderer(this, 32, 9);
        ModelRenderer tail1 = new ModelRenderer(this, 20, 6);
        ModelRenderer tail2 = new ModelRenderer(this, 52, 6);

        head1.addBox(-2.5F, 8.51F, -7.01F, 5, 4, 5);
        head2.addBox(-2.5F, 8.51F, -7, 5, 4, 5);
        earRight1.addBox(1.15F, 3.51F, -3.01F, 2, 5, 1);
        earRight2.addBox(1.15F, 3.51F, -3, 2, 5, 1);
        earRight1.yRot = 0.2617994F;
        earRight2.yRot = 0.2617994F;
        earLeft1.addBox(-3.15F, 3.51F, -3.01F, 2, 5, 1);
        earLeft2.addBox(-3.15F, 3.51F, -3, 2, 5, 1);
        earLeft1.yRot = -0.2617994F;
        earLeft2.yRot = -0.2617994F;
        bipedLeftLegwear.addBox(-2, 0, -2, 4, 12, 4, 0.75F);
        bipedRightLegwear.addBox(-2, 0, -2, 4, 12, 4, 0.75F);
        nose1.addBox(-0.5F, 10, -7.5F, 1, 1, 1);
        nose2.addBox(-0.5F, 10, -7.5F, 1, 1, 1);
        tail1.addBox(-1, 9, 2, 2, 2, 2);
        tail2.addBox(-1, 9, 2, 2, 2, 2);

        leftLeg.addChild(head1);
        rightLeg.addChild(head2);
        leftLeg.addChild(earRight1);
        rightLeg.addChild(earRight2);
        leftLeg.addChild(earLeft1);
        rightLeg.addChild(earLeft2);
        leftLeg.addChild(bipedLeftLegwear);
        rightLeg.addChild(bipedRightLegwear);
        leftLeg.addChild(nose1);
        rightLeg.addChild(nose2);
        leftLeg.addChild(tail1);
        rightLeg.addChild(tail2);

        setAllVisible(false);
        leftLeg.visible = rightLeg.visible = true;
    }
}
