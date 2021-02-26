package com.resourcefulbees.resourcefulbees.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.resourcefulbees.resourcefulbees.ResourcefulBees;
import com.resourcefulbees.resourcefulbees.client.gui.widget.ArrowButton;
import com.resourcefulbees.resourcefulbees.container.UnvalidatedApiaryContainer;
import com.resourcefulbees.resourcefulbees.network.NetPacketHandler;
import com.resourcefulbees.resourcefulbees.network.packets.BuildApiaryMessage;
import com.resourcefulbees.resourcefulbees.network.packets.ValidateApiaryMessage;
import com.resourcefulbees.resourcefulbees.tileentity.multiblocks.apiary.ApiaryTileEntity;
import com.resourcefulbees.resourcefulbees.utils.MathUtils;
import com.resourcefulbees.resourcefulbees.utils.PreviewHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class UnvalidatedApiaryScreen extends ContainerScreen<UnvalidatedApiaryContainer> {

    private static final ResourceLocation unvalidatedTexture = new ResourceLocation(ResourcefulBees.MOD_ID, "textures/gui/apiary/unvalidated.png");
    private static final ResourceLocation arrowButtonTexture = new ResourceLocation(ResourcefulBees.MOD_ID, "textures/gui/apiary/arrow_button.png");
    private final ApiaryTileEntity apiaryTileEntity;
    private final PlayerEntity player;
    private int verticalOffset;
    private int horizontalOffset;
    private ArrowButton upButton;
    private ArrowButton downButton;
    private ArrowButton leftButton;
    private ArrowButton rightButton;
    private PreviewButton previewButton;


    public UnvalidatedApiaryScreen(UnvalidatedApiaryContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.player = inv.player;
        this.verticalOffset = screenContainer.getApiaryTileEntity().getVerticalOffset();
        this.horizontalOffset = screenContainer.getApiaryTileEntity().getHorizontalOffset();
        this.apiaryTileEntity = this.container.getApiaryTileEntity();
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new Button(getGuiLeft() + 116, getGuiTop() + 10, 50, 20, new StringTextComponent(I18n.format("gui.resourcefulbees.apiary.button.validate")), onPress -> this.validate()));
        BuildButton buildStructureButton = this.addButton(new BuildButton(getGuiLeft() + 116, getGuiTop() + 35, 50, 20, new StringTextComponent(I18n.format("gui.resourcefulbees.apiary.button.build")), onPress -> this.build()));
        if (!this.player.isCreative()) {
            buildStructureButton.active = false;
        }
        this.previewButton = this.addButton(new PreviewButton(getGuiLeft() + 22, getGuiTop() + 25, 12, 12, 0, 24, 12, arrowButtonTexture, this.container.getApiaryTileEntity().isPreviewed(), onPress -> {
            setPreviewToggle();
            previewSetToggle(this.previewButton.isTriggered());
        }));
        previewSetToggle(this.previewButton.isTriggered());
        this.upButton = this.addButton(new ArrowButton(getGuiLeft() + 22, getGuiTop() + 12, 12, 12, 0, 0, 12, arrowButtonTexture, onPress -> this.offsetPosition(Direction.UP)));
        this.downButton = this.addButton(new ArrowButton(getGuiLeft() + 22, getGuiTop() + 38, 12, 12, 12, 0, 12, arrowButtonTexture, onPress -> this.offsetPosition(Direction.DOWN)));
        this.leftButton = this.addButton(new ArrowButton(getGuiLeft() + 9, getGuiTop() + 25, 12, 12, 24, 0, 12, arrowButtonTexture, onPress -> this.offsetPosition(Direction.LEFT)));
        this.rightButton = this.addButton(new ArrowButton(getGuiLeft() + 35, getGuiTop() + 25, 12, 12, 36, 0, 12, arrowButtonTexture, onPress -> this.offsetPosition(Direction.RIGHT)));
    }

    private void previewSetToggle(boolean toggled) {
        if (!toggled)
            this.previewButton.setTrigger(false);

        PreviewHandler.setPreview(getContainer().getPos(), this.container.getApiaryTileEntity().buildStructureBounds(this.horizontalOffset, this.verticalOffset), toggled);
    }

    private void setPreviewToggle() {
        if (this.previewButton.active)
            this.previewButton.setTrigger(!this.previewButton.isTriggered());
    }

    private void offsetPosition(Direction direction) {
        previewSetToggle(false);
        switch (direction) {
            case UP:
                verticalOffset++;
                break;
            case DOWN:
                verticalOffset--;
                break;
            case LEFT:
                horizontalOffset--;
                break;
            default:
                horizontalOffset++;
        }
        verticalOffset = MathUtils.clamp(verticalOffset, -1, 2);
        horizontalOffset = MathUtils.clamp(horizontalOffset, -2, 2);

        apiaryTileEntity.setVerticalOffset(verticalOffset);
        apiaryTileEntity.setHorizontalOffset(horizontalOffset);
    }

    private void build() {
        previewSetToggle(false);
        UnvalidatedApiaryContainer container = getContainer();
        BlockPos pos = container.getPos();
        NetPacketHandler.sendToServer(new BuildApiaryMessage(pos, verticalOffset, horizontalOffset));
    }

    @Override
    public void render(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.upButton.active = verticalOffset != 2;
        this.downButton.active = verticalOffset != -1;
        this.leftButton.active = horizontalOffset != -2;
        this.rightButton.active = horizontalOffset != 2;
        this.renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);
        this.drawMouseoverTooltip(matrix, mouseX, mouseY);
    }

    private void validate() {
        previewSetToggle(false);
        UnvalidatedApiaryContainer container = getContainer();
        BlockPos pos = container.getPos();
        NetPacketHandler.sendToServer(new ValidateApiaryMessage(pos, verticalOffset, horizontalOffset));
    }

    @Override
    protected void drawBackground(@Nonnull MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
        Minecraft client = this.client;
        if (client != null) {
            this.client.getTextureManager().bindTexture(unvalidatedTexture);
            int i = (this.width - this.xSize) / 2;
            int j = (this.height - this.ySize) / 2;
            this.drawTexture(matrix, i, j, 0, 0, this.xSize, this.ySize);
        }
    }

    @Override
    protected void drawForeground(@Nonnull MatrixStack matrix, int mouseX, int mouseY) {
        this.textRenderer.draw(matrix,  "Offset", 65, 13, 0x404040);
        this.textRenderer.draw(matrix, "Vert.", 75, 26, 0x404040);
        this.textRenderer.draw(matrix, "Horiz.", 75, 39, 0x404040);
        this.drawRightAlignedString(matrix, textRenderer, String.valueOf(verticalOffset), 70, 26, 0x404040);
        this.drawRightAlignedString(matrix, textRenderer, String.valueOf(horizontalOffset), 70, 39, 0x404040);

        for (Widget widget : this.buttons) {
            if (widget.isHovered()) {
                widget.renderToolTip(matrix, mouseX - this.guiLeft, mouseY - this.guiTop);
                break;
            }
        }
    }


    public void drawRightAlignedString(@Nonnull MatrixStack matrix, FontRenderer fontRenderer, @Nonnull String s, int posX, int posY, int color) {
        fontRenderer.draw(matrix, s, (float) (posX - fontRenderer.getStringWidth(s)), (float) posY, color);
    }

    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    @OnlyIn(Dist.CLIENT)
    public class BuildButton extends Button {
        public BuildButton(int widthIn, int heightIn, int width, int height, StringTextComponent text, IPressable onPress) {
            super(widthIn, heightIn, width, height, text, onPress);
        }

        @Override
        public void renderToolTip(@Nonnull MatrixStack matrix, int mouseX, int mouseY) {
            if (!this.active) {
                StringTextComponent s = new StringTextComponent(I18n.format("gui.resourcefulbees.apiary.button.build.creative"));
                UnvalidatedApiaryScreen.this.renderTooltip(matrix, s, mouseX, mouseY);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public class PreviewButton extends ImageButton {
        private final ResourceLocation resourceLocation;
        private final int xTexStart;
        private final int yTexStart;
        private final int yDiffText;
        private boolean triggered;

        public PreviewButton(int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn, boolean triggered, IPressable onPressIn) {
            super(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn, onPressIn);
            this.triggered = triggered;
            this.xTexStart = xTexStartIn;
            this.yTexStart = yTexStartIn;
            this.yDiffText = yDiffTextIn;
            this.resourceLocation = resourceLocationIn;
        }

        @Override
        public void renderButton(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getTextureManager().bindTexture(this.resourceLocation);
            RenderSystem.disableDepthTest();
            int i = this.yTexStart;
            int j = this.xTexStart;
            if (!this.active) {
                j += 24;
            } else if (this.isTriggered()) {
                j += 12;
                if (this.isHovered()) {
                    i += this.yDiffText;
                }
            } else {
                if (this.isHovered()) {
                    i += this.yDiffText;
                }
            }
            drawTexture(matrix, this.x, this.y, (float) j, (float) i, this.width, this.height, 64, 64);
            RenderSystem.enableDepthTest();
        }

        @Override
        public void renderToolTip(@Nonnull MatrixStack matrix, int mouseX, int mouseY) {
            StringTextComponent s;
            if (!isTriggered()) {
                s = new StringTextComponent(I18n.format("gui.resourcefulbees.apiary.button.preview.enable"));
            }
            else {
                s = new StringTextComponent(I18n.format("gui.resourcefulbees.apiary.button.preview.disable"));
            }
            UnvalidatedApiaryScreen.this.renderTooltip(matrix, s, mouseX, mouseY);
        }

        public void setTrigger(boolean triggered) {
            container.getApiaryTileEntity().setPreviewed(triggered);
            this.triggered = triggered;
        }

        public boolean isTriggered() {
            return this.triggered;
        }
    }
}
