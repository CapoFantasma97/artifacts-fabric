package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.model.ModelRenderer;

public class ClawsModel extends GloveModel {

    public ClawsModel(boolean smallArms) {
        super(smallArms);

        int smallArmsOffset = smallArms ? 1 : 0;

        ModelRenderer clawLeftUpper1 = new ModelRenderer(this, 0, 6);
        ModelRenderer clawRightUpper1 = new ModelRenderer(this, 0, 38);
        ModelRenderer clawLeftUpper2 = new ModelRenderer(this, 8, 6);
        ModelRenderer clawRightUpper2 = new ModelRenderer(this, 8, 38);
        ModelRenderer clawLeftLower1 = new ModelRenderer(this, 0, 0);
        ModelRenderer clawRightLower1 = new ModelRenderer(this, 0, 32);
        ModelRenderer clawLeftLower2 = new ModelRenderer(this, 8, 0);
        ModelRenderer clawRightLower2 = new ModelRenderer(this, 8, 32);
        clawLeftUpper1.addBox(3 - smallArmsOffset, 10, -1.5F, 1, 4, 1);
        clawRightUpper1.addBox(-4 + smallArmsOffset, 10, -1.5F, 1, 4, 1);
        clawLeftUpper2.addBox(3 - smallArmsOffset, 10, 0.5F, 1, 4, 1);
        clawRightUpper2.addBox(-4 + smallArmsOffset, 10, 0.5F, 1, 4, 1);
        clawLeftLower1.addBox(-smallArmsOffset, 10, -1.5F, 3, 5, 1);
        clawRightLower1.addBox(-3 + smallArmsOffset, 10, -1.5F, 3, 5, 1);
        clawLeftLower2.addBox(-smallArmsOffset, 10, 0.5F, 3, 5, 1);
        clawRightLower2.addBox(-3 + smallArmsOffset, 10, 0.5F, 3, 5, 1);
        leftArm.addChild(clawLeftUpper1);
        rightArm.addChild(clawRightUpper1);
        leftArm.addChild(clawLeftUpper2);
        rightArm.addChild(clawRightUpper2);
        leftArm.addChild(clawLeftLower1);
        rightArm.addChild(clawRightLower1);
        leftArm.addChild(clawLeftLower2);
        rightArm.addChild(clawRightLower2);
    }
}
