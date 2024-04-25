package dev.xkmc.youkaishomecoming.content.spell.game.youkari;

import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.youkaishomecoming.content.entity.danmaku.DanmakuHelper;
import dev.xkmc.youkaishomecoming.content.spell.mover.CompositeMover;
import dev.xkmc.youkaishomecoming.content.spell.mover.PolarMover;
import dev.xkmc.youkaishomecoming.content.spell.mover.RectMover;
import dev.xkmc.youkaishomecoming.content.spell.mover.ZeroMover;
import dev.xkmc.youkaishomecoming.content.spell.spellcard.CardHolder;
import dev.xkmc.youkaishomecoming.content.spell.spellcard.SpellCard;
import dev.xkmc.youkaishomecoming.init.registrate.YHDanmaku;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;

@SerialClass
public class DoubleButterfly extends SpellCard {

	@Override
	public void tick(CardHolder holder) {
		super.tick(holder);
		int interval = 200;
		if (tick < interval) return;
		if (tick % interval == 0) {
			int round = tick / interval;
			launch(holder,
					round / 2 % 2 == 0 ? YHDanmaku.Bullet.BALL : YHDanmaku.Bullet.CIRCLE,
					round % 2 == 0 ? DyeColor.CYAN : DyeColor.MAGENTA);
		}
	}

	private void launch(CardHolder holder, YHDanmaku.Bullet type, DyeColor color) {
		var r = holder.random();
		var pos = holder.center();
		DanmakuHelper.Orientation o0 = DanmakuHelper.getOrientation(holder.forward());

		int n = 1000;
		int mrange = 16, vrange = 2;
		int t0 = 100;
		int t1 = 20;
		double tvr = 0.8;
		int t2 = 40;
		int t3 = 60;
		int t4 = 40;
		double avar = Math.PI / 12;

		float wvr = (float) (tvr / (mrange * 2 * Math.PI));
		int total = t0 + t1 + t2 + t3 + t4;
		for (int i = 0; i < n; i++) {
			double a0 = 2 * Math.PI / i;
			double ver = (r.nextDouble() * 2 - 1) * avar;
			Vec3 a1 = o0.rotate(a0, ver);
			float range = mrange + vrange * (float) (r.nextDouble() * 2 - 1);
			float va = range * 2 / (t0 * t0);
			float vr = va * t0;
			var mover = new CompositeMover();
			var a2 = PolarMover.ofPlane(pos, a1)
					.radial(range, 0, 0).angular(0, wvr, 0).dir(0)
					.scale(100).normalize();
			var polar0 = PolarMover.ofPlane(pos, a1)
					.radial(range, 0, 0).angular(0, 0, wvr / 2 / t2);
			var polar1 = polar0.copy().atTime(t2).clearAccel();
			var rect = polar1.copy().atTime(t3).toRect();
			mover.add(t0, new RectMover(a1.scale(-va)));
			mover.add(t1, new ZeroMover(a1, a2, t1));
			mover.add(t2, polar0);
			mover.add(t3, polar1);
			mover.add(t4, rect);
			var danmaku = holder.prepare(total, a1.scale(vr), type, color);
			danmaku.mover = mover;
			holder.shoot(danmaku);
		}
	}

}
