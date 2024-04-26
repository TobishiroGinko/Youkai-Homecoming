package dev.xkmc.danmaku.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.xkmc.danmaku.entity.SimplifiedProjectile;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public record DoubleLayerDanmakuType(ResourceLocation colored, ResourceLocation overlay, int color)
		implements RenderableDanmakuType<DoubleLayerDanmakuType, DoubleLayerDanmakuType.Ins> {

	@Override
	public void start(MultiBufferSource buffer, Iterable<Ins> list) {
		VertexConsumer vc;
		vc = buffer.getBuffer(RenderType.entityCutoutNoCull(colored));
		for (var e : list) {
			e.tex(vc, color);
		}
		vc = buffer.getBuffer(RenderType.entityCutoutNoCull(overlay));
		for (var e : list) {
			e.tex(vc, -1);
		}
	}

	@Override
	public void create(DanmakuRenderer r, SimplifiedProjectile e, PoseStack pose, float pTick) {
		pose.mulPose(r.cameraOrientation());
		pose.mulPose(Axis.YP.rotationDegrees(180.0F));
		PoseStack.Pose mat = pose.last();
		Matrix4f m4 = new Matrix4f(mat.pose());
		Matrix3f m3 = new Matrix3f(mat.normal());
		DanmakuRenderHelper.add(this, new Ins(m3, m4));
	}

	public record Ins(Matrix3f m3, Matrix4f m4) {

		public void tex(VertexConsumer vc, int color) {
			vertex(vc, m4, m3, 0, 0, 0, 1, color);
			vertex(vc, m4, m3, 1, 0, 1, 1, color);
			vertex(vc, m4, m3, 1, 1, 1, 0, color);
			vertex(vc, m4, m3, 0, 1, 0, 0, color);
		}

		private static void vertex(VertexConsumer vc, Matrix4f m4, Matrix3f m3, float x, int y, int u, int v, int color) {
			vc.vertex(m4, x - 0.5F, y - 0.5F, 0.0F).color(color)
					.uv((float) u, (float) v).overlayCoords(OverlayTexture.NO_OVERLAY)
					.uv2(LightTexture.FULL_BRIGHT).normal(m3, 0.0F, 1.0F, 0.0F).endVertex();
		}

	}
}
