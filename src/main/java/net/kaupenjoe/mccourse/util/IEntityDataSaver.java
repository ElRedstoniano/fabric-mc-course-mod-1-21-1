package net.kaupenjoe.mccourse.util;

//import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public interface IEntityDataSaver {
    Optional<Vec3d> getPersistentData();
}
