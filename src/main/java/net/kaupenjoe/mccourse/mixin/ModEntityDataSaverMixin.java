package net.kaupenjoe.mccourse.mixin;

import net.kaupenjoe.mccourse.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Entity.class)
public abstract class ModEntityDataSaverMixin implements IEntityDataSaver {

    /*
    * This class is now obsolete as now entities doesnt not use nbt to save data
    *
    * Here are some examples of possible ways to achieve a similar thing:
    * - (Using config files) -> https://github.com/John-Paul-R/Essential-Commands/blob/1.21.x/src/main/java/com/fibermc/essentialcommands/mixin/ServerPlayerEntityMixin.java
    * - (Using a PersistentState (writing data directly into the world))
    *       -> https://github.com/MimStar/home-utilities-1.21.4/blob/1.21.6/src/main/java/com/homeutilities/StateSaverAndLoader.java#L68 (using a Map<UUID, CustomData> persistentState)
    *       -> https://discord.com/channels/507304429255393322/507982478276034570/1381850757845876756 a basic example of a custom PersistentState
    * - (Using Entity Attachments (with AttachmentRegistryBuilder class)) -> https://gist.github.com/Linguardium/cebcd41c6bbcd74eaa1f8b40ec2bbec8
    * */
    //@Unique
   // private NbtCompound persistentData;
    /*@Unique
    private Optional<Vec3d> persistentData;
    @Override
    public Optional<Vec3d> getPersistentData() {
        //if (persistentData.get() == null){
        //    this.persistentData = Optional.of(Vec3d.ZERO);
        //}
        return persistentData;
    }*/

    //@Inject(method = "writeNbt", at = @At("HEAD"))
    /*@Inject(method = "writeData", at = @At("HEAD"))
    //protected void injectWriteMethod(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> info){
    protected void injectWriteMethod(WriteView view, CallbackInfo ci){
        //if (this.persistentData != null){
        if (this.persistentData.get() != null){
            //nbt.put("mccourse.custom_data", persistentData);
            view.put("HomePos", Vec3d.CODEC, persistentData.get());
        }
    }*/

    /*@Inject(method = "readData", at = @At("HEAD"))
    //protected void injectReadMethod(NbtCompound nbt, CallbackInfo info){
    protected void injectReadMethod(ReadView view, CallbackInfo info){
        //if (nbt.contains("mccourse.custom_data"/_*, 10*_/)){
        //    this.persistentData = nbt.getCompoundOrEmpty("mccourse.custom_data");
        //}
        view.read("HomePos", Vec3d.CODEC).ifPresent(vec3d -> {
            persistentData = Optional.of(vec3d);
        });
    }*/
}
