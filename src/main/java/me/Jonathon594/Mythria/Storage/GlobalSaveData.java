package me.Jonathon594.Mythria.Storage;

import me.Jonathon594.Mythria.Managers.TimeManager;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class GlobalSaveData extends WorldSavedData {

    private static final String DATA_NAME = Mythria.MODID + "_global_data";

    public GlobalSaveData(final String name) {
        super(name);
    }

    public GlobalSaveData() {
        super(DATA_NAME);
    }

    public static GlobalSaveData get() {
        ServerWorld world = ServerLifecycleHooks.getCurrentServer().getWorld(World.OVERWORLD);

        return world.getSavedData().getOrCreate(GlobalSaveData::new, DATA_NAME);
    }

    @Override
    public void read(final CompoundNBT nbt) {
        TimeManager.getCurrentDate().setMGD(nbt.getInt("CurrentDate"));
        //Todo ProfileArchive.loadData(nbt);
    }

    @Override
    public CompoundNBT write(final CompoundNBT compound) {
        compound.putInt("CurrentDate", TimeManager.getCurrentDate().getMGD());
        //Todo ProfileArchive.saveData(compound);
        return compound;
    }

    @Override
    public boolean isDirty() {
        return true;
    }

}
