package net.kaupenjoe.mccourse.entity.client;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.custom.WarturtleEntity;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.item.custom.WarturtleArmorItem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.Map;

public class WarturtleArmorFeatureRenderer extends FeatureRenderer<WarturtleEntity, WarturtleModel<WarturtleEntity>> {
    private final WarturtleModel<WarturtleEntity> model; // Mirar clase clase WolfArmorFeatureRenderer
    private Map<Item, Identifier> ARMOR_MAP = Map.of(
            ModItems.IRON_WARTURTLE_ARMOR, MCCourseMod.id("textures/entity/warturtle/armor/iron_warturtle.png"),
            ModItems.GOLD_WARTURTLE_ARMOR, MCCourseMod.id("textures/entity/warturtle/armor/gold_warturtle.png"),
            ModItems.DIAMOND_WARTURTLE_ARMOR, MCCourseMod.id("textures/entity/warturtle/armor/diamond_warturtle.png"),
            ModItems.NETHERITE_WARTURTLE_ARMOR, MCCourseMod.id("textures/entity/warturtle/armor/netherite_warturtle.png"),
            ModItems.FLUORITE_WARTURTLE_ARMOR, MCCourseMod.id("textures/entity/warturtle/armor/fluorite_warturtle.png")
    );

    public WarturtleArmorFeatureRenderer(FeatureRendererContext<WarturtleEntity, WarturtleModel<WarturtleEntity>> context, EntityModelLoader loader) {
        super(context);
        model = new WarturtleModel<>(loader.getModelPart(ModEntityModelLayers.WARTURTLE_ARMOR));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WarturtleEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.hasArmorOn()) {
            ItemStack itemStack = entity.getBodyArmor();
            if (itemStack.getItem() instanceof WarturtleArmorItem armorItem) {
                this.getContextModel().copyStateTo(this.model);
                this.model.animateModel(entity, limbAngle, limbDistance, tickDelta);
                this.model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(ARMOR_MAP.get(armorItem)));
                this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
                renderDyed(matrices, vertexConsumers, light, entity, armorItem);
            }
        }
    }

    private static final Identifier[] DYE_LOCATION = new Identifier[]{
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/white.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/orange.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/magenta.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/light_blue.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/yellow.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/lime.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/pink.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/gray.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/light_gray.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/cyan.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/purple.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/blue.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/brown.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/green.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/red.png"),
            MCCourseMod.id("textures/entity/warturtle/armor/blankies/black.png")
    };

    public void renderDyed(
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WarturtleEntity warturtle, WarturtleArmorItem armorItem) {
        DyeColor dyeColor = warturtle.getSwag();
        Identifier identifier;
        if (dyeColor != null) {
            identifier = DYE_LOCATION[dyeColor.getId()];
        } else {
            identifier = ARMOR_MAP.get(armorItem); // Default
        }

        this.model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(identifier)), light, OverlayTexture.DEFAULT_UV);
    }
}
