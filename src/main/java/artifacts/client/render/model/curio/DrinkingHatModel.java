package artifacts.client.render.model.curio;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class DrinkingHatModel extends BipedModel<LivingEntity> {

    protected final ModelRenderer hatShade;

    public DrinkingHatModel() {
        super(0.5F, 0, 64, 64);

        setAllVisible(false);
        head.visible = true;
        hat.visible = true;

        hatShade = new ModelRenderer(this, 0, 52);
        ModelRenderer straw = new ModelRenderer(this, 0, 50);
        ModelRenderer canLeft = new ModelRenderer(this, 0, 41);
        ModelRenderer canRight = new ModelRenderer(this, 12, 41);
        ModelRenderer strawLeft = new ModelRenderer(this, 0, 32);
        ModelRenderer strawRight = new ModelRenderer(this, 17, 32);

        hatShade.addBox(-4, -6, -8, 8, 1, 4);
        straw.addBox(-6, -1, -5, 12, 1, 1);
        canLeft.addBox(4, -11, -1, 3, 6, 3);
        canRight.addBox(-7, -11, -1, 3, 6, 3);
        strawLeft.addBox(5, -4, -3, 1, 1, 8);
        strawRight.addBox(-6, -4, -3, 1, 1, 8);

        head.addChild(hatShade);
        head.addChild(straw);
        head.addChild(canLeft);
        head.addChild(canRight);
        head.addChild(strawLeft);
        head.addChild(strawRight);

        strawLeft.xRot = 0.7853F;
        strawRight.xRot = 0.7853F;
    }
}
