package dev.thomasglasser.mineraculouskamikotizations.world.level.storage;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;

public class KamikotizationData extends SavedData {
    public static final String FILE_ID = "kamikotization";

    private boolean weatherModified = false;

    public static KamikotizationData get(ServerLevel level) {
        return level.getServer().overworld().getDataStorage().computeIfAbsent(KamikotizationData.factory(), KamikotizationData.FILE_ID);
    }

    public static Factory<KamikotizationData> factory() {
        return new Factory<>(KamikotizationData::new, (p_294039_, p_324123_) -> load(p_294039_), DataFixTypes.LEVEL);
    }

    public boolean wasWeatherModified() {
        return weatherModified;
    }

    public void setWeatherModified(boolean weatherModified) {
        this.weatherModified = weatherModified;
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putBoolean("weather_modified", this.weatherModified);
        return tag;
    }

    public static KamikotizationData load(CompoundTag tag) {
        KamikotizationData data = new KamikotizationData();
        data.weatherModified = tag.getBoolean("weather_modified");
        return data;
    }
}
