package dev.xkmc.youkaishomecoming.content.entity.rumia;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.xkmc.youkaishomecoming.init.YoukaisHomecoming;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RumiaRenderer extends MobRenderer<Rumia, RumiaModel<Rumia>> {

	public static final ResourceLocation TEX = new ResourceLocation(YoukaisHomecoming.MODID, "textures/entities/rumia.png");

	public RumiaRenderer(EntityRendererProvider.Context context) {
		super(context, new RumiaModel<>(context.bakeLayer(RumiaModel.LAYER_LOCATION)), 0.2F);
	}

	public ResourceLocation getTextureLocation(Rumia entity) {
		return TEX;
	}

	protected void setupRotations(Rumia rumia, PoseStack pose, float age, float yaw, float pTick) {
		if (rumia.isBlocked()) {
			pose.mulPose(Axis.XP.rotationDegrees(90));
		}
		super.setupRotations(rumia, pose, age, yaw, pTick);
	}

	@Override
	protected float getAttackAnim(Rumia pLivingBase, float pPartialTickTime) {
		return super.getAttackAnim(pLivingBase, pPartialTickTime);
	}

	@Override
	public void render(Rumia rumia, float yaw, float pTick, PoseStack pose, MultiBufferSource buffer, int light) {
		super.render(rumia, yaw, pTick, pose, buffer, rumia.isCharged() ? 0 : light);
	}
}