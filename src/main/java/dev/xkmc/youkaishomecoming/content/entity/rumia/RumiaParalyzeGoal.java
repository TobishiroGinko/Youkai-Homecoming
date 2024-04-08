package dev.xkmc.youkaishomecoming.content.entity.rumia;

import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class RumiaParalyzeGoal extends Goal {

	private final Rumia rumia;

	public RumiaParalyzeGoal(Rumia rumia) {
		this.rumia = rumia;
		setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
	}

	@Override
	public boolean canUse() {
		return rumia.isBlocked();
	}

}
