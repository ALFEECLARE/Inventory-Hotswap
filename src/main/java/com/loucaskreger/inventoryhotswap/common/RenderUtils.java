package com.loucaskreger.inventoryhotswap.common;

import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

import com.mojang.blaze3d.buffers.BufferType;
import com.mojang.blaze3d.buffers.BufferUsage;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RenderUtils {
		public static final String SHADER_POSITION_TEX = "core/position_tex";
		public static final String SHADER_POSITION_COLOR = "core/position_color";
		public static final String SHADER_POSITION_TEX_COLOR = "core/position_tex_color";
		public static final String SHADER_LINES = "core/rendertype_lines";

		public static enum Shaders {
			POSITION_TEX(SHADER_POSITION_TEX),
			POSITION_COLOR(SHADER_POSITION_COLOR),
			POSITION_TEX_COLOR(SHADER_POSITION_TEX_COLOR),
			LINES(SHADER_LINES);
			
			private String value;
			private Shaders(String keyString) {
				value = keyString;			}
		}

        public static void renderSingleLine(PoseStack stack, VertexConsumer buffer, float x1, float y1, float z1,
                        float x2, float y2,
                        float z2, float r, float g, float b, float a) {
                Vec3 normal = new Vec3(x2 - x1, y2 - y1, z2 - z1).normalize();
                renderSingleLine(stack, buffer, x1, y1, z1, x2, y2, z2, r, g, b, a, (float) normal.x, (float) normal.y,
                                (float) normal.z);
        }

        public static void renderSingleLine(PoseStack stack, VertexConsumer buffer, float x1, float y1, float z1,
                        float x2, float y2,
                        float z2, float r, float g, float b, float a, float normalX, float normalY, float normalZ) {
                Matrix4f matrix4f = stack.last().pose();
                PoseStack.Pose matrix3f = stack.last();
                buffer.addVertex(matrix4f, x1, y1, z1).setColor(r, g, b, a)
                                .setNormal(matrix3f, normalX, normalY, normalZ);
                buffer.addVertex(matrix4f, x2, y2, z2).setColor(r, g, b, a)
                                .setNormal(matrix3f, normalX, normalY, normalZ);
        }

        // copied vanilla code for Sodium/Rubidium compatibility, because they overwrite and broke this vanilla code.
        // see net.minecraft.client.renderer.ShapeRenderer#renderLineBox
        public static void renderLineBoxVanillaStyle(PoseStack p_109647_, VertexConsumer p_109648_, AABB p_109649_, float p_109650_, float p_109651_, float p_109652_, float p_109653_) {
        	renderLineBoxVanillaStyle(p_109647_, p_109648_, p_109649_.minX, p_109649_.minY, p_109649_.minZ, p_109649_.maxX, p_109649_.maxY, p_109649_.maxZ, p_109650_, p_109651_, p_109652_, p_109653_, p_109650_, p_109651_, p_109652_);
         }

        public static void renderLineBoxVanillaStyle(PoseStack p_109609_, VertexConsumer p_109610_, double p_109611_, double p_109612_, double p_109613_, double p_109614_, double p_109615_, double p_109616_, float p_109617_, float p_109618_, float p_109619_, float p_109620_) {
        	renderLineBoxVanillaStyle(p_109609_, p_109610_, p_109611_, p_109612_, p_109613_, p_109614_, p_109615_, p_109616_, p_109617_, p_109618_, p_109619_, p_109620_, p_109617_, p_109618_, p_109619_);
        }

        public static void renderLineBoxVanillaStyle(PoseStack p_109622_, VertexConsumer p_109623_, double p_109624_, double p_109625_, double p_109626_, double p_109627_, double p_109628_, double p_109629_, float p_109630_, float p_109631_, float p_109632_, float p_109633_, float p_109634_, float p_109635_, float p_109636_) {
            Matrix4f matrix4f = p_109622_.last().pose();
            PoseStack.Pose matrix3f = p_109622_.last();
            float f = (float)p_109624_;
            float f1 = (float)p_109625_;
            float f2 = (float)p_109626_;
            float f3 = (float)p_109627_;
            float f4 = (float)p_109628_;
            float f5 = (float)p_109629_;
            p_109623_.addVertex(matrix4f, f, f1, f2).setColor(p_109630_, p_109635_, p_109636_, p_109633_).setNormal(matrix3f, 1.0F, 0.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f3, f1, f2).setColor(p_109630_, p_109635_, p_109636_, p_109633_).setNormal(matrix3f, 1.0F, 0.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f, f1, f2).setColor(p_109634_, p_109631_, p_109636_, p_109633_).setNormal(matrix3f, 0.0F, 1.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f, f4, f2).setColor(p_109634_, p_109631_, p_109636_, p_109633_).setNormal(matrix3f, 0.0F, 1.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f, f1, f2).setColor(p_109634_, p_109635_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, 0.0F, 1.0F);
            p_109623_.addVertex(matrix4f, f, f1, f5).setColor(p_109634_, p_109635_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, 0.0F, 1.0F);
            p_109623_.addVertex(matrix4f, f3, f1, f2).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, 1.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f3, f4, f2).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, 1.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f3, f4, f2).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, -1.0F, 0.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f, f4, f2).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, -1.0F, 0.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f, f4, f2).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, 0.0F, 1.0F);
            p_109623_.addVertex(matrix4f, f, f4, f5).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, 0.0F, 1.0F);
            p_109623_.addVertex(matrix4f, f, f4, f5).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, -1.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f, f1, f5).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, -1.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f, f1, f5).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 1.0F, 0.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f3, f1, f5).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 1.0F, 0.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f3, f1, f5).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, 0.0F, -1.0F);
            p_109623_.addVertex(matrix4f, f3, f1, f2).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, 0.0F, -1.0F);
            p_109623_.addVertex(matrix4f, f, f4, f5).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 1.0F, 0.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f3, f4, f5).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 1.0F, 0.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f3, f1, f5).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, 1.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f3, f4, f5).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, 1.0F, 0.0F);
            p_109623_.addVertex(matrix4f, f3, f4, f2).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, 0.0F, 1.0F);
            p_109623_.addVertex(matrix4f, f3, f4, f5).setColor(p_109630_, p_109631_, p_109632_, p_109633_).setNormal(matrix3f, 0.0F, 0.0F, 1.0F);
         }
        
        public static RenderPipeline buildLinePipeline(String label) {
        	return buildPipeline(label,RenderPipelines.MATRICES_COLOR_SNIPPET, RenderUtils.Shaders.LINES, BlendFunction.TRANSLUCENT, DepthTestFunction.NO_DEPTH_TEST, DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.LINES, false, Map.ofEntries(
					Map.entry("LineWidth", UniformType.FLOAT),
					Map.entry("ScreenSize", UniformType.VEC2)
				));       	
        }
        
        public static RenderPipeline buildEntityPipeline(String label) {
        	return buildPipeline(label, RenderPipelines.MATRICES_COLOR_SNIPPET, RenderUtils.Shaders.POSITION_COLOR, BlendFunction.PANORAMA, DepthTestFunction.NO_DEPTH_TEST, DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, null, null);
        }
        
        public static RenderPipeline buildGuiPipeline(String label) {
        	return buildPipeline(label,RenderPipelines.GUI_TEXTURED_SNIPPET,RenderUtils.Shaders.POSITION_TEX, BlendFunction.TRANSLUCENT, DepthTestFunction.NO_DEPTH_TEST, DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, null, null);
        }
        
        public static RenderPipeline buildPipeline(String label, RenderPipeline.Snippet base, RenderUtils.Shaders shader, BlendFunction blendFunction, DepthTestFunction depthFunction, VertexFormat defaultFormat, VertexFormat.Mode defaultmode, Boolean isCull, Map<String, UniformType> uniformMap) {
        	RenderPipeline.Builder builder = RenderPipeline.builder(base)
    				.withLocation(MetaUtils.getModId() + "/" + label)
    				.withFragmentShader(shader.value)
    				.withVertexShader(shader.value);
        	if (blendFunction != null) {
    			builder.withBlend(blendFunction);
        	} else {
        		builder.withoutBlend();
        	}
        	if (depthFunction != null) {
        		builder.withDepthTestFunction(depthFunction);
        	}
        	if (uniformMap != null) {
        		for (String uniformKey : uniformMap.keySet()) {
        			builder.withUniform(uniformKey, uniformMap.get(uniformKey));
        		}
        	}
        	if (isCull != null) {
        		builder.withCull(isCull);
        	}
    		return builder.withVertexFormat(defaultFormat, defaultmode).build();
        }
        
        public static RenderSystem.AutoStorageIndexBuffer buildBuffer(VertexFormat.Mode mode) {
        	return RenderSystem.getSequentialBuffer(mode);
        }

        public static GpuTexture getGpuTexture(ResourceLocation location, TextureFormat format) {
        	return RenderSystem.getDevice().createTexture(location.getNamespace(), format, 0, 0, 0);
        }
        public static void renderIfExists(RenderPipeline usingPipeLine, RenderSystem.AutoStorageIndexBuffer autoStorageBuffer) {
        	renderIfExists(usingPipeLine, autoStorageBuffer, 72);
        }
       
        public static void renderIfExists(RenderPipeline usingPipeLine, RenderSystem.AutoStorageIndexBuffer autoStorageBuffer, int bufferSize) {
            GpuBuffer gpuBuffer = RenderSystem.getDevice()
                    .createBuffer(() -> "vertext simple buffer", BufferType.VERTICES, BufferUsage.DYNAMIC_WRITE, bufferSize);
    		RenderTarget renderTarget = Minecraft.getInstance().getMainRenderTarget();
            try (RenderPass renderpass = RenderSystem.getDevice()
                    .createCommandEncoder()
                    .createRenderPass(renderTarget.getColorTexture(), OptionalInt.empty(), renderTarget.getDepthTexture(), OptionalDouble.empty())) {
                renderpass.setPipeline(usingPipeLine);
                renderpass.setIndexBuffer(autoStorageBuffer.getBuffer(36), autoStorageBuffer.type());
                renderpass.setVertexBuffer(0, gpuBuffer);
                renderpass.drawIndexed(0, 0);
            }
        }
        	
        public static void renderIfExists(BufferBuilder buffer, RenderPipeline usingPipeLine, RenderSystem.AutoStorageIndexBuffer autoStorageBuffer) {
    		MeshData mesh = buffer.build();
    		if (mesh != null) {
    			GpuBuffer vertexBuffer = RenderSystem.getDevice()
    					.createBuffer(() -> "vertex buffer", BufferType.VERTICES, BufferUsage.STATIC_WRITE, mesh.vertexBuffer());

    			RenderTarget renderTarget = Minecraft.getInstance().getMainRenderTarget();
    			GpuTexture texture = renderTarget.getColorTexture();
    			int drawStateCount = mesh.drawState().indexCount();
    			if (texture == null || drawStateCount == 0) {
    				return;
    			}

    			GpuBuffer gpuBuffer = autoStorageBuffer.getBuffer(drawStateCount);
    			try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder()
    					.createRenderPass(texture, OptionalInt.empty(), renderTarget.getDepthTexture(), OptionalDouble.empty())) {

    				Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
    				matrix4fStack.pushMatrix();

    				renderPass.setPipeline(usingPipeLine);
    				renderPass.setIndexBuffer(gpuBuffer, autoStorageBuffer.type());
    				renderPass.setVertexBuffer(0, vertexBuffer);
    				renderPass.drawIndexed(0, drawStateCount);

    				matrix4fStack.popMatrix();
    			}
    		}
        }
 
        public static float getScaleValue(int sourceValue, int targetValue) {
        	return ((float)(Math.ceil(targetValue * 10 / sourceValue)) / 10);
        }
        
        private RenderUtils() {
        }
}
