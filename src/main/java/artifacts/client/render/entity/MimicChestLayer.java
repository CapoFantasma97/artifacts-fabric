package artifacts.client.render.entity;

import artifacts.client.render.curio.CurioLayers;
import artifacts.client.render.entity.model.MimicChestLayerModel;
import artifacts.client.render.entity.model.MimicModel;
import artifacts.common.entity.MimicEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MimicChestLayer extends RenderLayer<MimicEntity, MimicModel> {

    public static final ResourceLocation CHEST_ATLAS = new ResourceLocation("textures/atlas/chest.png");

    private final MimicChestLayerModel chestModel;
    public final Material vanillaChestMaterial;
    public final List<Material> chestMaterials;

    @SuppressWarnings("deprecation")
    public MimicChestLayer(RenderLayerParent<MimicEntity, MimicModel> entityRenderer, EntityModelSet modelSet) {
        super(entityRenderer);

        Calendar calendar = Calendar.getInstance();
        boolean isChristmas = calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26
                || calendar.get(Calendar.MONTH) + 1 == 4 && calendar.get(Calendar.DATE) == 1;

        chestModel = new MimicChestLayerModel(modelSet.bakeLayer(CurioLayers.MIMIC_OVERLAY));
        chestMaterials = new ArrayList<>();
        vanillaChestMaterial = isChristmas ? Sheets.CHEST_XMAS_LOCATION : Sheets.CHEST_LOCATION;

        if (!isChristmas && FabricLoader.getInstance().isModLoaded("lootr")) {
            ResourceLocation chestLocation = new ResourceLocation("lootr", "chest");
            chestMaterials.add(new Material(TextureAtlas.LOCATION_BLOCKS, chestLocation));
        } else if (!isChristmas && FabricLoader.getInstance().isModLoaded("myloot")) {
            ResourceLocation chestLocation = new ResourceLocation("myloot", "entity/chest/loot");
            chestMaterials.add(new Material(CHEST_ATLAS, chestLocation));
        } else {
            if (!isChristmas && FabricLoader.getInstance().isModLoaded("quark")) {
                List<String> chestTypes = Arrays.asList(
                        "oak",
                        "spruce",
                        "birch",
                        "jungle",
                        "acacia",
                        "dark_oak",
                        "warped",
                        "crimson"
                );

                for (String chestType : chestTypes) {
                    ResourceLocation chestLocation = new ResourceLocation("quark", String.format("model/chest/%s/normal", chestType));
                    chestMaterials.add(new Material(CHEST_ATLAS, chestLocation));
                }
            }

            chestMaterials.add(vanillaChestMaterial);
        }
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!mimic.isInvisible()) {
            matrixStack.pushPose();

            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180));
            matrixStack.translate(-0.5, -1.5, -0.5);

            getParentModel().copyPropertiesTo(chestModel);
            chestModel.prepareMobModel(mimic, limbSwing, limbSwingAmount, partialTicks);
            chestModel.setupAnim(mimic, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer builder = getChestMaterial(mimic).buffer(buffer, RenderType::entityCutout);
            chestModel.renderToBuffer(matrixStack, builder, packedLight, LivingEntityRenderer.getOverlayCoords(mimic, 0), 1, 1, 1, 1);

            matrixStack.popPose();
        }
    }

    private Material getChestMaterial(MimicEntity mimic) {
        // TODO: missing config
        /*if (!ModConfig.client.useModdedMimicTextures.get()) {
            return vanillaChestMaterial;
        }*/
        if (chestMaterials.size() == 1) {
            return chestMaterials.get(0);
        }
        return chestMaterials.get((int) (Math.abs(mimic.getUUID().getMostSignificantBits()) % chestMaterials.size()));
    }
}
