package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.AbstractMythriaFurnaceBlock;
import me.Jonathon594.Mythria.Container.MythriaFurnaceContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMythriaFurnaceTileEntity extends AbstractFuelableFurnaceTileEntity implements IClearable, INamedContainerProvider {
    public static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container.stone_furnace.name");

    private final IIntArray furnaceData = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return (int) temperature;
                case 1:
                    return maxTicks;
                case 2:
                    return ticksLeft;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    temperature = value;
                case 1:
                    maxTicks = value;
                case 2:
                    ticksLeft = value;
            }
        }

        @Override
        public int size() {
            return 3;
        }
    };

    public AbstractMythriaFurnaceTileEntity(TileEntityType<? extends AbstractMythriaFurnaceTileEntity> tileEntityTypeIn, int criticalTemperature) {
        super(tileEntityTypeIn, criticalTemperature, 5);
        maxTemperature = 0;
    }

    @Override
    public boolean canBeLit() {
        return !getFuelStack().isEmpty();
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new MythriaFurnaceContainer(id, playerInventory, this, furnaceData);
    }

    @Override
    public ITextComponent getDisplayName() {
        return CONTAINER_NAME;
    }


    public CompoundNBT getUpdateTag() {
        return this.writeItems(new CompoundNBT());
    }

    @Override
    protected double getSmokeParticleOffset() {
        return 1.0;
    }

    @Override
    protected double getHeatingEfficiency() {
        return 0.023;
    }

    @Override
    protected ItemStack getFuelStack() {
        return inventory.get(4);
    }

    @Override
    protected void setLit(boolean lit) {
        world.setBlockState(pos, getBlockState().with(AbstractMythriaFurnaceBlock.LIT, false));
    }

    @Override
    protected void onFinishBurning() {
    }
}
