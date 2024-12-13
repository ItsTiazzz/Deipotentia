package net.tywrapstudios.deipotentia.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.Deipotentia;

public class HephaestusForgeScreen extends HandledScreen<HephaestusForgeScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(Deipotentia.MOD_ID, "textures/gui/hephaestus_gui.png");

    public HephaestusForgeScreen(HephaestusForgeScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(MatrixStack context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(context, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderProgressDisplayer(context, x, y);
    }

    private void renderProgressDisplayer(MatrixStack context, int x, int y) {
        if (handler.isCrafting()) {
            drawTexture(context, x + 85, y + 30, 176, 0, 8, handler.getScaledProgress());
        }
    }

    @Override
    public void render(MatrixStack context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
