package net.kaupenjoe.mccourse.entity.custom;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class TomahawkProjectileEntity extends PersistentProjectileEntity {
    private float rotation;
    private float prevRotation = 0;
    public Vector2f groundedOffset = new Vector2f(0,0);

    @Override
    public boolean isInGround() { return super.isInGround(); }

    public static final TrackedData<Boolean> ENCHANTED =
            DataTracker.registerData(TomahawkProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public TomahawkProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public TomahawkProjectileEntity(World world, PlayerEntity player) {
        super(ModEntities.TOMAHAWK_ET, player, world, new ItemStack(ModItems.TOMAHAWK), null);
        this.dataTracker.set(ENCHANTED, player.getMainHandStack().hasGlint());
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.TOMAHAWK);
    }

    public float getLerpedRenderingRotation(float tickDelta) {
        prevRotation = rotation;
        rotation += 0.5f;
        if (rotation >= 360) {
            rotation -= 360;
        }

        return MathHelper.lerpAngleDegrees(tickDelta, prevRotation, rotation);
    }

    public float getLerpedPitch(float tickDelta) {
        return tickDelta == 1.0F ? this.getPitch() : MathHelper.lerp(tickDelta, this.lastPitch, this.getPitch());
    }

    public boolean isEnchanted() {
        return this.dataTracker.get(ENCHANTED);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ENCHANTED, false);
    }

    /*public boolean isGrounded() {
        return inGround;
    }*/

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult); //
        Entity entity = entityHitResult.getEntity();

        if (!this.getWorld().isClient()) {
            entity.damage(((ServerWorld) this.getWorld()), this.getDamageSources().thrown(this, this.getOwner()), 4);
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.discard(); // Eliminar proyectil tras chocar con una entidad
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);

        if (blockHitResult.getSide() == Direction.SOUTH) {
            groundedOffset = new Vector2f(215f, /*180f*/getYaw());
        }
        if (blockHitResult.getSide() == Direction.NORTH) {
            groundedOffset = new Vector2f(215f, /*0f*/getYaw());
        }
        if(blockHitResult.getSide() == Direction.EAST) {
            groundedOffset = new Vector2f(215f,/*-90f*/getYaw());
        }
        if(blockHitResult.getSide() == Direction.WEST) {
            groundedOffset = new Vector2f(215f,/*90f*/getYaw());
        }

        if(blockHitResult.getSide() == Direction.DOWN) {
            groundedOffset = new Vector2f(115f,/*180f*/getYaw());
        }
        if(blockHitResult.getSide() == Direction.UP) {
            groundedOffset = new Vector2f(285f,/*180f*/getYaw());
        }
    }
}
