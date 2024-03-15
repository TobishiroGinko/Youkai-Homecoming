package dev.xkmc.youkaishomecoming.compat.thirst;

import dev.ghen.thirst.api.ThirstHelper;
import dev.xkmc.youkaishomecoming.init.food.YHCoffee;
import dev.xkmc.youkaishomecoming.init.food.YHFood;

public class ThirstCompat {

	public static void init() {
		for (var e : YHCoffee.values()) {
			ThirstHelper.addDrink(e.item.get(), 8, 13);
		}

		ThirstHelper.addDrink(YHFood.BLACK_TEA.item.get(), 8, 13);
		ThirstHelper.addDrink(YHFood.GREEN_TEA.item.get(), 8, 13);
		ThirstHelper.addDrink(YHFood.OOLONG_TEA.item.get(), 8, 13);
		ThirstHelper.addDrink(YHFood.WHITE_TEA.item.get(), 8, 13);
		ThirstHelper.addDrink(YHFood.SCARLET_MIST.item.get(), 8, 13);
		ThirstHelper.addDrink(YHFood.WIND_PRIESTESSES.item.get(), 8, 13);

		ThirstHelper.addDrink(YHFood.MILK_POPSICLE.item.get(), 6, 10);
		ThirstHelper.addDrink(YHFood.BIG_POPSICLE.item.get(), 6, 10);

		ThirstHelper.addDrink(YHFood.AVGOLEMONO.item.get(), 6, 10);
		ThirstHelper.addDrink(YHFood.SHIRAYUKI.item.get(), 6, 10);

		ThirstHelper.addDrink(YHFood.HIGAN_SOUP.item.get(), 8, 13);
		ThirstHelper.addDrink(YHFood.POOR_GOD_SOUP.item.get(), 8, 13);
		ThirstHelper.addDrink(YHFood.POWER_SOUP.item.get(), 8, 13);
		ThirstHelper.addDrink(YHFood.MUSHROOM_SOUP.item.get(), 8, 13);
		ThirstHelper.addDrink(YHFood.MISO_SOUP.item.get(), 8, 13);


	}

}
