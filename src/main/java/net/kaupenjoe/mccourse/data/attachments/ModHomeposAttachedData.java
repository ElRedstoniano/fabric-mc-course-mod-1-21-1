package net.kaupenjoe.mccourse.data.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;


// From: https://gist.github.com/Linguardium/cebcd41c6bbcd74eaa1f8b40ec2bbec8 (see original for saving a List of floats instead of a Optional<Vec3d>)
public record ModHomeposAttachedData(Optional<Vec3d> homePos) {
  // Codecs are used to serialize and deserialize data to different formats.
  // We build a codec here to tell it how to turn our ModCustomAttachedData object into Nbt, json, etc
  // https://docs.fabricmc.net/develop/codecs
  public static Codec<ModHomeposAttachedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Vec3d.CODEC.optionalFieldOf("homepos").forGetter(ModHomeposAttachedData::homePos) // our object just has a list of floats
    ).apply(instance, ModHomeposAttachedData::new)); // all the values we defined above are passed to the factory method we reference here
  // SomeClass::new is a reference to a constructor. so in this case the list of floats is passed to the constructor 
  
  // A packet codec is used to convert the object into a byte buffer for sending over the network or converting back into an object
  // There are better ways to do this, such as using PacketCodec.tuple to build the read/write methods, 
  // but for simplicity of the example, this will do. 
  // https://wiki.fabricmc.net/tutorial:networking#networking_in_1205
  public static PacketCodec<ByteBuf, ModHomeposAttachedData> PACKET_CODEC = PacketCodecs.codec(CODEC);
  
  // A default value we can use as an "empty" or reset data component
  public static ModHomeposAttachedData DEFAULT = new ModHomeposAttachedData(Optional.empty()/*new Vec3d(0, -999, 0)*/);
  // Will use this value to check if there wasn't a prevous home
  
  // helper method for adding values and returning a new attached data object
  // This ensures that when we add things, we are provided the new component to set
  // for persistence and syncing, it uses the new object to determine the need to save
  // modifying the existing object will not trigger a requirement to save or sync the data
  //public ModHomeposAttachedData addFloat(float value) {
  public ModHomeposAttachedData setHomePos(Vec3d homePos) {
    //ArrayList<Float> newFloatList = new ArrayList<>(floatList);
    //newFloatList.add(value);
    //return new ModHomeposAttachedData(List.copyOf(newFloatList)); // makes the list immutable to prevent accidental modification
    return new ModHomeposAttachedData(Optional.of(homePos));
  }
  
  // helper method for removing values and returning a new attached data object
  // same as above
  //public ModHomeposAttachedData removeFloat(float value) {
  public ModHomeposAttachedData removeHomePos() {
    //if (!floatList.contains(value)) return this; // if the value isnt in the list, the list doesnt need modified.

    //ArrayList<Float> newFloatList = new ArrayList<>(floatList);
    //newFloatList.removeIf(v->v==value);
    //return new ModHomeposAttachedData(List.copyOf(newFloatList)); // makes the list immutable to prevent accidental modification
    return new ModHomeposAttachedData(Optional.empty()); // This is the same as clear() method
  }
  
  public ModHomeposAttachedData clear() { // clear method, just returns the default empty component
    return DEFAULT;
  }
}