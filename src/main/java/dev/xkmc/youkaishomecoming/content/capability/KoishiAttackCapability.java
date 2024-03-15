package dev.xkmc.youkaishomecoming.content.capability;

import dev.xkmc.l2library.capability.player.PlayerCapabilityHolder;
import dev.xkmc.l2library.capability.player.PlayerCapabilityNetworkHandler;
import dev.xkmc.l2library.capability.player.PlayerCapabilityTemplate;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.youkaishomecoming.init.YoukaisHomecoming;
import dev.xkmc.youkaishomecoming.init.data.YHDamageTypes;
import dev.xkmc.youkaishomecoming.init.registrate.YHEffects;
import dev.xkmc.youkaishomecoming.init.registrate.YHItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

@SerialClass
public class KoishiAttackCapability extends PlayerCapabilityTemplate<KoishiAttackCapability> {

	public static final Capability<KoishiAttackCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final PlayerCapabilityHolder<KoishiAttackCapability> HOLDER = new PlayerCapabilityHolder<>(
			new ResourceLocation(YoukaisHomecoming.MODID, "koishi_attack"), CAPABILITY,
			KoishiAttackCapability.class, KoishiAttackCapability::new, PlayerCapabilityNetworkHandler::new
	);

	public static boolean ENABLED = false;//TODO

	private static final int COOLDOWN = 6000, DELAY = 20, RANDOM = 1000, COUNT = 3, DAMAGE = 40;

	@SerialClass.SerialField
	private int tickRemain = 0;
	@SerialClass.SerialField
	private int attackCooldown = 0;
	@SerialClass.SerialField
	private int blockCount = 0;
	@SerialClass.SerialField
	private Vec3 source = null;

	@Override
	public void onClone(boolean isWasDeath) {
		blockCount = 0;
		tickRemain = 0;
	}

	@Override
	public void tick() {
		if (!ENABLED) return;
		if (!(player instanceof ServerPlayer sp)) return;
		if (tickRemain > 0) {
			tickRemain--;
			if (tickRemain == 0 && source != null) {
				attackCooldown = COOLDOWN;
				Vec3 vec = player.getEyePosition().add(source);
				source = null;
				if (player.hurt(YHDamageTypes.koishi(player, vec), DAMAGE)) {
					blockCount = 0;
				}
			}
			return;
		}
		if (attackCooldown > 0) {
			attackCooldown--;
			return;
		}
		if (!player.canBeSeenAsEnemy() ||
				player.hasEffect(YHEffects.UNCONSCIOUS.get()) ||
				!player.hasEffect(YHEffects.YOUKAIFIED.get()) &&
						!player.hasEffect(YHEffects.YOUKAIFYING.get())) {
			blockCount = 0;
			return;
		}
		if (player.getRandom().nextDouble() < 1d / RANDOM) {
			tickRemain = DELAY;
			source = player.getViewVector(1).normalize().scale(-2);
			YoukaisHomecoming.HANDLER.toClientPlayer(new KoishiStartPacket(), sp);
		}

	}

	public void onBlock() {
		player.getCooldowns().addCooldown(player.getUseItem().getItem(), 100);
		blockCount++;
		if (blockCount == COUNT) {
			blockCount = 0;
			player.spawnAtLocation(YHItems.KOISHI_HAT.get());
		}
	}

	public static void register() {
	}

}