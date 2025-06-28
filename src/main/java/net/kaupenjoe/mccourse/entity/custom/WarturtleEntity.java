package net.kaupenjoe.mccourse.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.item.custom.WarturtleArmorItem;
import net.kaupenjoe.mccourse.screen.custom.WarturtleScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.DyedCarpetBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import net.minecraft.item.Item;

import java.util.UUID;

public class WarturtleEntity extends TameableEntity implements InventoryChangedListener, RideableInventory{
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.changing(ModEntities.WARTURTLE_ET.getWidth(),
            ModEntities.WARTURTLE_ET.getHeight() - 0.5F).withEyeHeight(0.845F);
    //
    public final AnimationState sittingTransitionAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState standingTransitionAnimationState = new AnimationState();

    public static final TrackedData<Long> LAST_POSE_TICK =
            DataTracker.registerData(WarturtleEntity.class, TrackedDataHandlerRegistry.LONG);

    public static final TrackedData<Boolean> HAS_TIER_1_CHEST =
            DataTracker.registerData(WarturtleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> HAS_TIER_2_CHEST =
            DataTracker.registerData(WarturtleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> HAS_TIER_3_CHEST =
            DataTracker.registerData(WarturtleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<ItemStack> DYE_STACK =
            DataTracker.registerData(WarturtleEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    protected SimpleInventory inventory;

    private final int TIER_1_CHEST_SLOT = 2;
    private final int TIER_2_CHEST_SLOT = 3;
    private final int TIER_3_CHEST_SLOT = 4;

    public WarturtleEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new WarturtleMoveControl();
        this.createInventory();
    }

    /* GOALS */

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.15D));
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(ModItems.STRAWBERRY), false));

        this.goalSelector.add(4, new FollowOwnerGoal(this, 1.1D, 10f, 3f));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1D));

        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 4.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createWarturtleAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 45)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.15f)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.TEMPT_RANGE, 12);
    }

    /* BREEDABLE */
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ModItems.STRAWBERRY);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.WARTURTLE_ET.create(world, SpawnReason.BREEDING);
    }



    private void setUpAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 20;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.shouldUpdateSittingAnimations()) {
            this.standingTransitionAnimationState.stop();
            if (this.shouldPlaySittingTransitionAnimation()) {
                this.sittingTransitionAnimationState.startIfNotRunning(this.age);
                this.sittingAnimationState.stop();
            } else {
                this.sittingTransitionAnimationState.stop();
                this.sittingAnimationState.startIfNotRunning(this.age);
            }
        } else {
            this.sittingTransitionAnimationState.stop();
            this.sittingAnimationState.stop();
            this.standingTransitionAnimationState.setRunning(this.isChangingPose() && this.getLastPoseTickDelta() >= 0L, this.age);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.getWorld().isClient()) {
            this.setUpAnimationStates();
        }
    }

    private boolean shouldPlaySittingTransitionAnimation() {
        return this.isSitting() && this.getLastPoseTickDelta() < 40L && this.getLastPoseTickDelta() >= 0L;
    }

    public boolean shouldUpdateSittingAnimations() {
        return this.getLastPoseTickDelta() < 0L != this.isSitting();
    }

    public long getLastPoseTickDelta() {
        return this.getWorld().getTime() - Math.abs(this.dataTracker.get(LAST_POSE_TICK));
    }

    public boolean isChangingPose() {
        long l = this.getLastPoseTickDelta();
        return l < (long)(this.isSitting() ? 40 : 52);
    }

    public void setLastPoseTick(long lastPoseTick) {
        this.dataTracker.set(LAST_POSE_TICK, lastPoseTick);
    }

    private void initLastPoseTick(long time) {
        this.setLastPoseTick(Math.max(0L, time - 52L - 1L));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(LAST_POSE_TICK, 0L);

        builder.add(HAS_TIER_1_CHEST, false);
        builder.add(HAS_TIER_2_CHEST, false);
        builder.add(HAS_TIER_3_CHEST, false);

        builder.add(DYE_STACK, ItemStack.EMPTY);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        /* CamelEntity */
        super.writeCustomDataToNbt(nbt);
        nbt.putLong("LastPoseTick", this.dataTracker.get(LAST_POSE_TICK));

        /* AbstractDonkeyEntity */
        NbtList nbtList = new NbtList();

        for(int i = 0; i < this.inventory.size(); i++) {
            ItemStack itemStack = this.inventory.getStack(i);
            if (!itemStack.isEmpty()) {
                //if(i == 0) MCCourseMod.LOGGER.info(itemStack + "-write" + i);
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putByte("Slot", (byte)(i));
                nbtList.add(itemStack.toNbt(this.getRegistryManager(), nbtCompound));
            }
        }

        nbt.put("Items", nbtList);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) { // Sacado de la clase CamelEntity y AbstractDonkeyEntity
        /* CamelEntity */
        super.readCustomDataFromNbt(nbt);
        long l = nbt.getLong("LastPoseTick");
        if(l < 0L) {
            this.setPose(EntityPose.SITTING);
        }
        this.setLastPoseTick(l);

        /* AbstractDonkeyEntity */
        this.createInventory();
        NbtList nbtList = nbt.getList("Items", NbtElement.COMPOUND_TYPE);

        for(int i = 0; i < nbtList.size(); i++) {
            NbtCompound compoundTag = nbtList.getCompound(i);
            int j = compoundTag.getByte("Slot") & 255; // Pasar de byte a int
            //if(i == 0) MCCourseMod.LOGGER.info(j + "-read------------------" + i);
            if (j < this.inventory.size()) {
                this.inventory.setStack(j, ItemStack.fromNbt(this.getRegistryManager(), compoundTag).orElse(ItemStack.EMPTY));
            }
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        this.initLastPoseTick(world.toServerWorld().getTime());
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    public void startSitting() {
        if (this.isSitting()) {
            return;
        }
        this.playSound(SoundEvents.ENTITY_CAMEL_SIT);
        this.setPose(EntityPose.SITTING);
        this.emitGameEvent(GameEvent.ENTITY_ACTION);
        this.setLastPoseTick(-this.getWorld().getTime());

        setInSittingPose(true);
        setSitting(true);
    }

    public void startStanding() {
        if (!this.isSitting()) {
            return;
        }
        this.playSound(SoundEvents.ENTITY_CAMEL_STAND);
        this.setPose(EntityPose.STANDING);
        this.emitGameEvent(GameEvent.ENTITY_ACTION);
        this.setLastPoseTick(this.getWorld().getTime());

        setInSittingPose(false);
        setSitting(false);
    }

    public void setStanding() {
        this.setPose(EntityPose.STANDING);
        this.emitGameEvent(GameEvent.ENTITY_ACTION);
        this.initLastPoseTick(this.getWorld().getTime());
    }

    public void toggleSitting() {
        if (this.isSitting()) {
            startStanding();
        } else {
            startSitting();
        }
    }

    public boolean isSitting() {
        return this.dataTracker.get(LAST_POSE_TICK) < 0L;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        Item itemForTaming = Items.APPLE;
        //
        if(item == itemForTaming && !isTamed()) {
            if (this.getWorld().isClient()) {
                return ActionResult.CONSUME;
            } else {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                super.setOwner(player);
                this.navigation.recalculatePath();
                this.setTarget(null);
                this.getWorld().sendEntityStatus(this, (byte) 7);
                toggleSitting();

                return ActionResult.SUCCESS;
            }
        }

        if(isTamed() && hand == Hand.MAIN_HAND && item != itemForTaming && !isBreedingItem(itemStack) && !player.shouldCancelInteraction()) {
            toggleSitting();
            return ActionResult.SUCCESS;
        } else if (this.isTamed()) {
            this.openInventory(player); // Se abre el inventario solo si se está agachando el jugador
            return this.getWorld().isClient ? ActionResult.SUCCESS : ActionResult.CONSUME;
        }

        return super.interactMob(player, hand);
    }

    /* INVENTORY */ // Principalmente sacado de la clase AbstractHorseEntity

    @Override
    public void onInventoryChanged(Inventory sender) {
        if (sender.getStack(TIER_1_CHEST_SLOT).isOf(Items.CHEST) && !hasTier1Chest()) {
            setChest(TIER_1_CHEST_SLOT, true);
        }
        if (sender.getStack(TIER_2_CHEST_SLOT).isOf(Items.CHEST) && !hasTier2Chest()) {
            setChest(TIER_2_CHEST_SLOT, true);
        }
        if (sender.getStack(TIER_3_CHEST_SLOT).isOf(Items.CHEST) && !hasTier3Chest()) {
            setChest(TIER_3_CHEST_SLOT, true);
        }
        
        if (!sender.getStack(TIER_1_CHEST_SLOT).isOf(Items.CHEST) && hasTier1Chest()) {
            setChest(TIER_1_CHEST_SLOT, false);
            dropChestInventory(TIER_1_CHEST_SLOT);
        }
        if (!sender.getStack(TIER_2_CHEST_SLOT).isOf(Items.CHEST) && hasTier2Chest()) {
            setChest(TIER_2_CHEST_SLOT, false);
            dropChestInventory(TIER_2_CHEST_SLOT);
        }
        if (!sender.getStack(TIER_3_CHEST_SLOT).isOf(Items.CHEST) && hasTier3Chest()) {
            setChest(TIER_3_CHEST_SLOT, false);
            dropChestInventory(TIER_3_CHEST_SLOT);
        }

        if (sender.getStack(0).getItem() instanceof WarturtleArmorItem) {
            equipBodyArmor(sender.getStack(0));
        }
        if (sender.getStack(0).isEmpty() && isWearingBodyArmor()) { // Si está vacío y ya está con la armadura equipada
            equipBodyArmor(ItemStack.EMPTY);
        }

        if (!sender.getStack(1).isEmpty()) {
            this.dataTracker.set(DYE_STACK, sender.getStack(1));
        }
        if (sender.getStack(1).isEmpty()) {
            this.dataTracker.set(DYE_STACK, ItemStack.EMPTY);
        }
    }

    private void dropChestInventory(int slot) {
        if (slot == TIER_1_CHEST_SLOT) {
            ItemScatterer.spawn(getWorld(), this.getX(), this.getY(), this.getZ(), inventory.removeStack(5, 64));
            ItemScatterer.spawn(getWorld(), this.getX(), this.getY(), this.getZ(), inventory.removeStack(6, 64));
            ItemScatterer.spawn(getWorld(), this.getX(), this.getY(), this.getZ(), inventory.removeStack(7, 64));
            ItemScatterer.spawn(getWorld(), this.getX(), this.getY(), this.getZ(), inventory.removeStack(8, 64));
        }
        if (slot == TIER_2_CHEST_SLOT) {
            ItemScatterer.spawn(getWorld(), this.getX(), this.getY(), this.getZ(), inventory.removeStack(9, 64));
            ItemScatterer.spawn(getWorld(), this.getX(), this.getY(), this.getZ(), inventory.removeStack(10, 64));
            ItemScatterer.spawn(getWorld(), this.getX(), this.getY(), this.getZ(), inventory.removeStack(11, 64));
            ItemScatterer.spawn(getWorld(), this.getX(), this.getY(), this.getZ(), inventory.removeStack(12, 64));
        }
        if (slot == TIER_3_CHEST_SLOT) {
            ItemScatterer.spawn(getWorld(), this.getX(), this.getY(), this.getZ(), inventory.removeStack(13, 64));
            ItemScatterer.spawn(getWorld(), this.getX(), this.getY(), this.getZ(), inventory.removeStack(14, 64));
            ItemScatterer.spawn(getWorld(), this.getX(), this.getY(), this.getZ(), inventory.removeStack(15, 64));
            ItemScatterer.spawn(getWorld(), this.getX(), this.getY(), this.getZ(), inventory.removeStack(16, 64));
        }
    }

    private void setChest(int slot, boolean chested) {
        if (slot == TIER_1_CHEST_SLOT) {
            this.dataTracker.set(HAS_TIER_1_CHEST, chested);
        } else if(slot == TIER_2_CHEST_SLOT) {
            this.dataTracker.set(HAS_TIER_2_CHEST, chested);
        } else if (slot == TIER_3_CHEST_SLOT) {
            this.dataTracker.set(HAS_TIER_3_CHEST, chested);
        } else {
            MCCourseMod.LOGGER.error("Can't give chest to a Slot that doesn't exist!");
        }
    }

    public boolean hasTier1Chest() {
        return this.dataTracker.get(HAS_TIER_1_CHEST);
    }
    public boolean hasTier2Chest() {
        return this.dataTracker.get(HAS_TIER_2_CHEST);
    }
    public boolean hasTier3Chest() {
        return this.dataTracker.get(HAS_TIER_3_CHEST);
    }

    protected void createInventory() { // En la clase AbstractDonkeyEntity este método_ se llama onChestedStatusChanged
        SimpleInventory simpleInventory = this.inventory;
        this.inventory = new SimpleInventory(this.getInventorySize());
        if (simpleInventory != null) {
            simpleInventory.removeListener(this);
            int i = Math.min(simpleInventory.size(), this.inventory.size());

            for(int j = 0; j < i; ++j) {
                ItemStack itemStack = simpleInventory.getStack(j);
                if (itemStack.isEmpty()) continue;
                this.inventory.setStack(j, itemStack.copy());
            }
        }
        this.inventory.addListener(this);
    }

    public final int getInventorySize() {
        return getInventorySize(4);
    }

    public static int getInventorySize(int columns) {
        return columns * 3 + 5;
    }

    public boolean areInventoriesDifferent(Inventory inventory) {
        return this.inventory != inventory;
    }

    @Override
    public void openInventory(PlayerEntity player) {
        if(!this.getWorld().isClient() && isTamed()) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            if (serverPlayer.currentScreenHandler != serverPlayer.playerScreenHandler) {
                serverPlayer.closeHandledScreen();
            }

            serverPlayer.openHandledScreen(new ExtendedScreenHandlerFactory<UUID>() {
                @Nullable
                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return new WarturtleScreenHandler(syncId, playerInventory, WarturtleEntity.this.inventory, WarturtleEntity.this, 4);
                }

                @Override
                public Text getDisplayName() {
                    return Text.translatable("entity.mccourse.warturtle");
                }

                @Override
                public UUID getScreenOpeningData(ServerPlayerEntity player) {
                    return WarturtleEntity.this.getUuid();
                }
            });
        }
    }

    public boolean hasArmorOn() {
        return isWearingBodyArmor();
    }

    @Override
    protected void applyDamage(ServerWorld world, DamageSource source, float amount) {
        if (!this.canArmorAbsorb(source)) { // Si es un tipo de daño que la armadura no puede absorber simplemente se aplica
            super.applyDamage(world, source, amount);
        } else { // Si no se aplica una variación del daño en función de la armadura además de quitarle duración
            ItemStack itemStack = this.getBodyArmor();
            itemStack.damage(MathHelper.ceil(amount), this, EquipmentSlot.BODY);

            if (itemStack.getItem() instanceof WarturtleArmorItem warturtleArmorItem) {
                //int damageReduction = warturtleArmorItem.getProtection() / 2; // depends on what armor
                //float damageReduction = (float) this.getAttributeInstance(EntityAttributes.ARMOR).getBaseValue();
                float damageReduction = warturtleArmorItem.getArmorStats()[0] / 2; // depends on what armor
                super.applyDamage(world, source, Math.max(0, amount - damageReduction));
            }
        }
    }

    private boolean canArmorAbsorb(DamageSource damageSource) { // Si el daño puede aplicarse a la armadura
        // Por ejemplo para daño por ahogamiento hace que no sirva
        return this.hasArmorOn() && !damageSource.isIn(DamageTypeTags.BYPASSES_WOLF_ARMOR);
    }

    @Nullable
    private static DyeColor getDyeColor(ItemStack color) {
        Block block = Block.getBlockFromItem(color.getItem());
        return block instanceof DyedCarpetBlock ? ((DyedCarpetBlock)block).getDyeColor() : null;
    }

    @Nullable
    public DyeColor getSwag() {
        return getDyeColor(this.dataTracker.get(DYE_STACK));
    }

    /* CONTROLS */
    class WarturtleMoveControl extends MoveControl {
        public WarturtleMoveControl() {
            super(WarturtleEntity.this);
        }

        public void tick() {
            if (this.state == MoveControl.State.MOVE_TO && !WarturtleEntity.this.isLeashed() &&
                    WarturtleEntity.this.isSitting() && !WarturtleEntity.this.isChangingPose() && WarturtleEntity.this.canChangePose()) {
                WarturtleEntity.this.startStanding();
            }

            super.tick();
        }
    }

    private class WarturtleBodyControl extends BodyControl {
        public WarturtleBodyControl(WarturtleEntity warturtle) {
            super(warturtle);
        }

        public void tick() {
            if (!WarturtleEntity.this.isStationary()) {
                super.tick();
            }
        }
    }

    /* LEASH */
    @Override
    public Vec3d getLeashOffset(float tickDelta) {
        EntityDimensions entityDimensions = this.getDimensions(this.getPose());
        float f = this.getScaleFactor();
        return new Vec3d(0.0, entityDimensions.height() - (double)(0.18F * f), (entityDimensions.width() * 0.36F));
        //return super.getLeashOffset(tickDelta);
    }

    public boolean beforeLeashTick(Entity leashHolder, float distance) { // Hace que se levante cuando alguien se aleja con la soga
        boolean isSitting = this.isSitting();
        if (distance > 6.0F && isSitting && !this.isChangingPose() && this.canChangePose()) {
            this.startStanding();
            return true; // Se puede empezar a mover
        }

        //return false; // No se puede empezar a mover
        return !isSitting; // Se puede empezar a mover solo si no está sentado
    }

    public EntityDimensions getBaseDimensions(EntityPose pose) {
        return pose == EntityPose.SITTING ? SITTING_DIMENSIONS.scaled(this.getScaleFactor()) : super.getBaseDimensions(pose);
    }

    protected BodyControl createBodyControl() { return new WarturtleBodyControl(this); }

    public boolean canChangePose() {
        return this.wouldNotSuffocateInPose(this.isSitting() ? EntityPose.STANDING : EntityPose.SITTING);
    }

    public boolean isStationary() {
        return this.isSitting() || this.isChangingPose();
    }
}
