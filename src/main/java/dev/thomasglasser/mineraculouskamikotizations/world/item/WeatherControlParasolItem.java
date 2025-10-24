package dev.thomasglasser.mineraculouskamikotizations.world.item;

import com.mojang.serialization.Codec;
import dev.thomasglasser.mineraculous.api.client.gui.screens.RadialMenuOption;
import dev.thomasglasser.mineraculous.api.world.attachment.MineraculousAttachmentTypes;
import dev.thomasglasser.mineraculous.api.world.item.EffectRevertingItem;
import dev.thomasglasser.mineraculous.api.world.item.RadialMenuProvider;
import dev.thomasglasser.mineraculous.api.world.level.storage.AbilityReversionBlockData;
import dev.thomasglasser.mineraculous.api.world.level.storage.AbilityReversionEntityData;
import dev.thomasglasser.mineraculous.api.world.level.storage.AbilityReversionItemData;
import dev.thomasglasser.mineraculous.api.world.miraculous.Miraculouses;
import dev.thomasglasser.mineraculouskamikotizations.core.component.MineraculousKamikotizationsDataComponents;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.MineraculousKamikotizationsEntityTypes;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.grieftracking.GriefTrackingIceCharge;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.grieftracking.GriefTrackingLightningBolt;
import dev.thomasglasser.mineraculouskamikotizations.world.entity.grieftracking.GriefTrackingWindCharge;
import dev.thomasglasser.mineraculouskamikotizations.world.level.storage.KamikotizationData;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class WeatherControlParasolItem extends Item implements EffectRevertingItem, RadialMenuProvider<WeatherControlParasolItem.Mode> {
    public WeatherControlParasolItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        Mode mode = itemStack.get(MineraculousKamikotizationsDataComponents.WEATHER_CONTROL_PARASOL_MODE);
        if (mode == null) return InteractionResultHolder.fail(itemStack);
        ResolvableProfile resolvableProfile = itemStack.get(DataComponents.PROFILE);
        Player owner = resolvableProfile != null ? player.level().getPlayerByUUID(resolvableProfile.id().orElse(resolvableProfile.gameProfile().getId())) : null;
        if (owner == null)
            owner = player;
        if (mode == Mode.ICE) {
            if (!level.isClientSide()) {
                GriefTrackingIceCharge iceCharge = new GriefTrackingIceCharge(player, player.position().x(), player.getEyePosition().y(), player.position().z(), level);
                iceCharge.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(iceCharge);
            }
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.WIND_CHARGE_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        } else if (mode == Mode.LIGHTNING) {
            if (owner instanceof ServerPlayer serverOwner) {
                HitResult hitresult = ProjectileUtil.getHitResultOnViewVector(player, entity -> entity.isAlive() && entity.isPickable(), 100);
                GriefTrackingLightningBolt lightningBolt = MineraculousKamikotizationsEntityTypes.GRIEF_TRACKING_LIGHTNING_BOLT.get().create(level);
                if (lightningBolt != null) {
                    lightningBolt.setCause(serverOwner);
                    if (hitresult instanceof EntityHitResult entityHitResult) {
                        lightningBolt.setPos(entityHitResult.getEntity().position());
                    } else
                        lightningBolt.setPos(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, BlockPos.containing(hitresult.getLocation())).getBottomCenter());
                    level.addFreshEntity(lightningBolt);
                }
            }
        } else if (mode == Mode.WEATHER) {
            if (!level.isClientSide()) {
                ServerLevel overworld = level.getServer().overworld();
                if (overworld.isThundering())
                    overworld.setWeatherParameters(ServerLevel.RAIN_DELAY.sample(overworld.random), 0, false, false);
                else if (overworld.isRaining())
                    overworld.setWeatherParameters(0, ServerLevel.THUNDER_DURATION.sample(overworld.random), true, true);
                else
                    overworld.setWeatherParameters(0, ServerLevel.RAIN_DURATION.sample(overworld.random), true, false);
                overworld.getDataStorage().computeIfAbsent(KamikotizationData.factory(), KamikotizationData.FILE_ID).setWeatherModified(true);
            }
        } else if (mode == Mode.WIND) {
            if (!level.isClientSide()) {
                GriefTrackingWindCharge windCharge = new GriefTrackingWindCharge(player, level, player.position().x(), player.getEyePosition().y(), player.position().z());
                windCharge.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(windCharge);
            }
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.WIND_CHARGE_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        }
        player.getCooldowns().addCooldown(this, 10);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @Override
    public void revert(LivingEntity entity) {
        ServerLevel level = (ServerLevel) entity.level();
        AbilityReversionBlockData.get(level).revert(entity.getUUID(), (ServerLevel) entity.level());
        AbilityReversionItemData.get(level).markReverted(entity.getUUID());
        AbilityReversionEntityData.get(level).revert(entity.getUUID(), (ServerLevel) entity.level(), e -> e.setTicksFrozen(0));
        KamikotizationData overworldData = KamikotizationData.get(level);
        if (overworldData.wasWeatherModified()) {
            entity.getServer().overworld().resetWeatherCycle();
            overworldData.setWeatherModified(false);
        }
    }

    @Override
    public int getColor(ItemStack stack, InteractionHand hand, Player holder) {
        Level level = holder.level();
        int color = level.holderOrThrow(Miraculouses.BUTTERFLY).value().color().getValue();
        ResolvableProfile resolvableProfile = stack.get(DataComponents.PROFILE);
        if (resolvableProfile != null) {
            Player owner = level.getPlayerByUUID(resolvableProfile.id().orElse(resolvableProfile.gameProfile().getId()));
            if (owner != null && owner.getData(MineraculousAttachmentTypes.KAMIKOTIZATION).isPresent())
                color = owner.getData(MineraculousAttachmentTypes.KAMIKOTIZATION).get().kamikoData().nameColor();
        }
        return color;
    }

    @Override
    public List<Mode> getOptions(ItemStack stack, InteractionHand hand, Player holder) {
        return Mode.valuesList();
    }

    @Override
    public Supplier<DataComponentType<Mode>> getComponentType(ItemStack stack, InteractionHand hand, Player holder) {
        return MineraculousKamikotizationsDataComponents.WEATHER_CONTROL_PARASOL_MODE;
    }

    public enum Mode implements RadialMenuOption, StringRepresentable {
        ICE,
        LIGHTNING,
        WEATHER,
        WIND;

        public static final Codec<Mode> CODEC = Codec.STRING.xmap(Mode::of, Mode::getSerializedName);
        public static final StreamCodec<ByteBuf, Mode> STREAM_CODEC = ByteBufCodecs.STRING_UTF8.map(Mode::of, Mode::getSerializedName);

        private static final List<Mode> VALUES_LIST = new ReferenceArrayList<>(values());

        private final Component displayName;

        Mode() {
            this.displayName = Component.translatable(MineraculousKamikotizationsItems.WEATHER_CONTROL_PARASOL.getId().toLanguageKey("ability", getSerializedName()));
        }

        @Override
        public Component displayName() {
            return displayName;
        }

        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }

        public static List<Mode> valuesList() {
            return VALUES_LIST;
        }

        public static Mode of(String name) {
            return valueOf(name.toUpperCase());
        }
    }
}
