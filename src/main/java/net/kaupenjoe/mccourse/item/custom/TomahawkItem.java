package net.kaupenjoe.mccourse.item.custom;
import net.kaupenjoe.mccourse.entity.custom.TomahawkProjectileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TomahawkItem extends Item {
    public TomahawkItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        // De la clase SnowballItem
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW,
                SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if(!world.isClient()) {
            TomahawkProjectileEntity tomahawk = new TomahawkProjectileEntity(world, user);
            if (user.isInCreativeMode()) {
                tomahawk.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            } else {
                tomahawk.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
            }
            tomahawk.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 0f);
            world.spawnEntity(tomahawk);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return ActionResult.SUCCESS;
    }
    // createEntity()?
}
