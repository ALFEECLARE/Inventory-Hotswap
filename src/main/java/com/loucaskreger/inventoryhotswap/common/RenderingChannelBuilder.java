package com.loucaskreger.inventoryhotswap.common;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;

public class RenderingChannelBuilder {
	private RenderType renderType;
	private boolean isOpened = false;
	private BufferSource buffer;
	private VertexConsumer builder;
	
	public RenderingChannelBuilder(RenderType renderType) {
		this.renderType = renderType;
		buffer = Minecraft.getInstance().renderBuffers().bufferSource();
		builder = buffer.getBuffer(renderType);
		isOpened = true;
	}

	public VertexConsumer addVertex(float x, float y, float z) {
		return builder.addVertex(x, y, z);
	}
	
	public VertexConsumer addVertex(Matrix4f matrix, float x, float y, float z) {
		return builder.addVertex(matrix, x, y, z);
	}
	
	public VertexConsumer addVertex(PoseStack.Pose pose, float x, float y, float z) {
		return builder.addVertex(pose, x, y, z);
	}
	
	public VertexConsumer getBuilder() {
		if (!isOpened) {
			throw new IllegalStateException("This Builder is still/already closed.");
		}
		return builder;
	}

	public void closeChannel() {
		buffer.endBatch();
		isOpened = false;
	}
}
