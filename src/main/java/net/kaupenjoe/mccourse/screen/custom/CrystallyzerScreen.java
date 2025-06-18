package net.kaupenjoe.mccourse.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.screen.renderer.EnergyInfoArea;
import net.kaupenjoe.mccourse.screen.renderer.FluidStackRenderer;
import net.kaupenjoe.mccourse.util.MouseUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.item.Item;

import java.util.Optional;

public class CrystallyzerScreen extends HandledScreen<CrystallizerScreenHandler> {

    private static final Identifier GUI_TEXTURE = MCCourseMod.id("textures/gui/crystallizer/crystallizer_gui.png");
    private static final Identifier ARROW_TEXTURE = MCCourseMod.id("textures/gui/crystallizer/arrow_progress.png");
    private static final Identifier CRYSTAL_TEXTURE = Identifier.of("textures/block/amethyst_cluster.png");

    private EnergyInfoArea energyInfoArea;
    private FluidStackRenderer fluidStackRenderer;

    public CrystallyzerScreen(CrystallizerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        // Gets rid of title and inventory title
        titleY = 1000;
        playerInventoryTitleY = 1000;
        titleX = 1000;

        assignEnergyInfoArea();
        assignFluidStackRenderer();
    }

    private void assignFluidStackRenderer() {
        fluidStackRenderer = new FluidStackRenderer((FluidConstants.BUCKET / 81) * 16, true, 16, 50);
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea(((width - backgroundWidth) / 2) + 155,
                ((height - backgroundHeight) / 2) + 9, handler.blockEntity.energyStorage, 10, 48);
        //MCCourseMod.LOGGER.info(width + " " + backgroundWidth);
    }

    private void renderEnergyAreaTooltips (DrawContext context, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 155, 9, 10, 48)) {
            context.drawTooltip(Screens.getTextRenderer(this), energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderFluidTooltip(DrawContext context, int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, offsetX, offsetY, renderer)) {
            context.drawTooltip(Screens.getTextRenderer(this), renderer.getTooltip(handler.blockEntity.fluidStorage, Item.TooltipContext.DEFAULT),
                    Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        //super.drawForeground(context, mouseX, mouseY);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        renderEnergyAreaTooltips(context, mouseX, mouseY, x, y);
        renderFluidTooltip(context, mouseX, mouseY, x, y, 8, 7, fluidStackRenderer);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        energyInfoArea.draw(context);
        fluidStackRenderer.drawFluid(context, handler.blockEntity.fluidStorage, x + 8, y + 7, 16, 50,
                (FluidConstants.BUCKET / 81) * 16);

        renderProgressArrow(context, x, y);
        renderProgressCrystal(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()){
            context.drawTexture(ARROW_TEXTURE, x + 73, y + 35, 0, 0, handler.getScaledArrowProgress(),
                    16, 24, 16);
        }
    }

    private void renderProgressCrystal(DrawContext context, int x, int y) {
        if (handler.isCrafting()){
            context.drawTexture(CRYSTAL_TEXTURE, x + 104, y + 13 + 16 - handler.getScaledCrystalProgress(),
                    0, 16 - handler.getScaledCrystalProgress(), 16, handler.getScaledCrystalProgress(),
                    16, 16);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }
}
