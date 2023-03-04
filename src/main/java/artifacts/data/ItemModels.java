package artifacts.data;

import artifacts.Artifacts;
import artifacts.common.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemModels extends ItemModelProvider {

    public ItemModels(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Artifacts.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // noinspection ConstantConditions
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(item -> ForgeRegistries.ITEMS.getKey(item).getNamespace().equals(Artifacts.MODID))
                .filter(item -> item != ModItems.MIMIC_SPAWN_EGG.get())
                .filter(item -> item != ModItems.UMBRELLA.get())
                .forEach(this::addGeneratedModel);
    }

    private void addGeneratedModel(Item item) {
        // noinspection ConstantConditions
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();
        withExistingParent("item/" + name, "item/generated").texture("layer0", new ResourceLocation(Artifacts.MODID, "item/" + name));
    }
}
