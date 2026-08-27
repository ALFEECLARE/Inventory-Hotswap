package com.loucaskreger.inventoryhotswap.common;

import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderSetup.RenderSetupBuilder;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.util.Mth;
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

        public static void renderSingleLine(PoseStack stack, RenderingChannelBuilder builder, float x1, float y1, float z1,
                        float x2, float y2,
                        float z2, float r, float g, float b, float a, float lineWidth) {
                Vec3 normal = new Vec3(x2 - x1, y2 - y1, z2 - z1).normalize();
                renderSingleLine(stack, builder, x1, y1, z1, x2, y2, z2, r, g, b, a, (float) normal.x, (float) normal.y,
                                (float) normal.z, lineWidth);
        }

        public static void renderSingleLine(PoseStack stack, RenderingChannelBuilder builder, float x1, float y1, float z1,
                        float x2, float y2,
                        float z2, float r, float g, float b, float a, float normalX, float normalY, float normalZ, float lineWidth) {
                Matrix4f pose = stack.last().pose();
                builder.addVertex(pose, x1, y1, z1).setColor(r, g, b, a).setLineWidth(lineWidth)
                                .setNormal(normalX, normalY, normalZ);
                builder.addVertex(pose, x2, y2, z2).setColor(r, g, b, a).setLineWidth(lineWidth)
                                .setNormal(normalX, normalY, normalZ);
        }

        // copied vanilla code for Sodium/Rubidium compatibility, because they overwrite and broke this vanilla code.
        // see net.minecraft.client.renderer.ShapeRenderer#renderLineBox
        public static void renderLineBoxVanillaStyle(PoseStack stack, RenderingChannelBuilder builder, AABB shape, float red, float green, float blue, float alpha, float lineWidth) {
        	renderLineBoxVanillaStyle(stack, builder, shape.minX, shape.minY, shape.minZ, shape.maxX, shape.maxY, shape.maxZ, red, green, blue, alpha, red, green, blue, lineWidth);
         }

        public static void renderLineBoxVanillaStyle(PoseStack stack, RenderingChannelBuilder builder, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha, float lineWidth) {
        	renderLineBoxVanillaStyle(stack, builder, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha, red, green, blue, lineWidth);
        }

        public static void renderLineBoxVanillaStyle(PoseStack stack, RenderingChannelBuilder builder, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red1, float green1, float blue1, float alpha, float shadowRed, float shadowGreen, float shadowBlue, float lineWidth) {
            Matrix4f pose = stack.last().pose();
            float f = (float)minX;
            float f1 = (float)minY;
            float f2 = (float)minZ;
            float f3 = (float)maxX;
            float f4 = (float)maxY;
            float f5 = (float)maxZ;
            builder.addVertex(pose, f, f1, f2).setColor(red1, green1, shadowBlue, alpha).setLineWidth(lineWidth).setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f3, f1, f2).setColor(red1, shadowGreen, shadowBlue, alpha).setLineWidth(lineWidth).setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f, f1, f2).setColor(shadowRed, green1, shadowBlue, alpha).setLineWidth(lineWidth).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pose, f, f4, f2).setColor(shadowRed, green1, shadowBlue, alpha).setLineWidth(lineWidth).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pose, f, f1, f2).setColor(shadowRed, shadowGreen, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, 1.0F);
            builder.addVertex(pose, f, f1, f5).setColor(shadowRed, shadowGreen, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, 1.0F);
            builder.addVertex(pose, f3, f1, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pose, f3, f4, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pose, f3, f4, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(-1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f, f4, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(-1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f, f4, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, 1.0F);
            builder.addVertex(pose, f, f4, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, 1.0F);
            builder.addVertex(pose, f, f4, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, -1.0F, 0.0F);
            builder.addVertex(pose, f, f1, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, -1.0F, 0.0F);
            builder.addVertex(pose, f, f1, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f3, f1, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f3, f1, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, -1.0F);
            builder.addVertex(pose, f3, f1, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, -1.0F);
            builder.addVertex(pose, f, f4, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f3, f4, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pose, f3, f1, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pose, f3, f4, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pose, f3, f4, f2).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, 1.0F);
            builder.addVertex(pose, f3, f4, f5).setColor(red1, green1, blue1, alpha).setLineWidth(lineWidth).setNormal(0.0F, 0.0F, 1.0F);
         }
        
        public static RenderType getLineRenderType(String label) {
        	return buildRenderType(
	        			label, 
	        			buildPipeline("pipeline/lines", RenderPipelines.LINES_SNIPPET, RenderUtils.Shaders.LINES, BlendFunction.TRANSLUCENT, CompareOp.ALWAYS_PASS, DefaultVertexFormat.POSITION_COLOR_LINE_WIDTH, VertexFormat.Mode.LINES, false, null, null),
	        			LayeringTransform.VIEW_OFFSET_Z_LAYERING_FORWARD,
	        			OutputTarget.ITEM_ENTITY_TARGET
	        			);
        }

        public static RenderType buildEntityRenderType(String label) {
        	return buildRenderType(
	        			label, 
	        			buildPipeline("pipeline/entity_solid", RenderPipelines.ENTITY_SNIPPET, RenderUtils.Shaders.POSITION_COLOR, new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO), CompareOp.ALWAYS_PASS, DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, false, null,null),
	        			LayeringTransform.VIEW_OFFSET_Z_LAYERING_FORWARD,
	        			OutputTarget.ITEM_ENTITY_TARGET
	        			);
        }
        
        public static RenderType buildGuiRenderType(String label) {
        	return buildRenderType(
    				label,
    				buildPipeline("pipeline/gui_textured", RenderPipelines.GUI_TEXTURED_SNIPPET, RenderUtils.Shaders.POSITION_TEX, BlendFunction.TRANSLUCENT, CompareOp.ALWAYS_PASS, DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, null, null, null),
    				null,
    				null
    				);
        }

        public static RenderType getColoredGuiRenderType(String label) {
        	return buildRenderType(
        				label,
        				buildPipeline("pipeline/gui_textured", RenderPipelines.GUI_TEXTURED_SNIPPET, RenderUtils.Shaders.POSITION_TEX_COLOR, BlendFunction.TRANSLUCENT, CompareOp.ALWAYS_PASS, DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, false, null, "Sampler1"),
        				null,
        				null
        				);
        }
        
        public static RenderType getTextParticleRenderType(String label) {
        	return buildRenderType(
        				label,
        				buildPipeline("pipeline/opaque_particle", RenderPipelines.PARTICLE_SNIPPET, RenderUtils.Shaders.POSITION_TEX, BlendFunction.OVERLAY, CompareOp.ALWAYS_PASS, DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, false, null, null),
        				LayeringTransform.VIEW_OFFSET_Z_LAYERING_FORWARD,
        				OutputTarget.ITEM_ENTITY_TARGET
        				);
        }

        public static RenderType buildRenderType(String label, RenderPipeline pipeline, LayeringTransform layerTransform, OutputTarget outputTarget) {
        	RenderSetupBuilder builder = RenderSetup.builder(pipeline);
        	if (layerTransform != null) {
        		builder.setLayeringTransform(layerTransform);
        	}
        	if (outputTarget != null) {
    			builder.setOutputTarget(outputTarget);
        	}
        	return RenderType.create(label,builder.createRenderSetup());       
        }
        
        public static RenderPipeline buildPipeline(String location, RenderPipeline.Snippet base, RenderUtils.Shaders shader, BlendFunction blendFunction, CompareOp depthFunction, VertexFormat defaultFormat, VertexFormat.Mode defaultmode, Boolean isCull, Map<String, UniformType> uniformMap, String samplerName) {
        	RenderPipeline.Builder builder = RenderPipeline.builder(base);
        		builder.withLocation(location);
        	if (shader != null) {
    			builder.withFragmentShader(shader.value).withVertexShader(shader.value);
        	}
        	if (blendFunction != null) {
    			builder.withColorTargetState(new ColorTargetState(blendFunction));
        	}
        	if (depthFunction != null) {
        		builder.withDepthStencilState(new DepthStencilState(depthFunction, false));
        	}
        	if (uniformMap != null) {
        		for (String uniformKey : uniformMap.keySet()) {
        			builder.withUniform(uniformKey, uniformMap.get(uniformKey));
        		}
        	}
        	if (isCull != null) {
        		builder.withCull(isCull);
        	}
        	if (samplerName != null) {
        		builder.withSampler(samplerName);
        	}
    		return builder.withVertexFormat(defaultFormat, defaultmode).build();
        }
        
        public static float getScaleValue(int sourceValue, int targetValue) {
        	return ((float)(Math.ceil(targetValue * 10 / sourceValue)) / 10);
        }
        
        public static GuiGraphicsExtractor createGuiGraphicsExtractor() {
        	Minecraft mc = Minecraft.getInstance();
            int xMouse = (int)mc.mouseHandler.getScaledXPos(mc.getWindow());
            int yMouse = (int)mc.mouseHandler.getScaledYPos(mc.getWindow());
            return new GuiGraphicsExtractor(mc, mc.gameRenderer.getGameRenderState().guiRenderState, xMouse, yMouse);
        }
    	
    	public static Vector3f worldSpaceToScreenSpace(float x, float y, float z) {
    		Minecraft mc = Minecraft.getInstance();
    		Camera camera = mc.gameRenderer.getMainCamera();
    		double PI = 3.14159;

    		Vec3 camPos = camera.position();
    		double pX = x - camPos.x();
    		double pY = y - camPos.y();
    		double pZ = z - camPos.z();
    		double opX = pX;
    		double opY = pY;
    		double opZ = pZ;
    		double yawRad = Mth.wrapDegrees(camera.yRot()) * PI / 180;
    		double pitRad = Mth.wrapDegrees(camera.xRot()) * PI / 180;
    		double distance = Math.sqrt(Math.pow(pX,2) + Math.pow(pY,2) + Math.pow(pZ,2));

    		pZ = opZ * Math.cos(yawRad) - opX * Math.sin(yawRad); 
    		pX = opX * Math.cos(yawRad) + opZ * Math.sin(yawRad);
    		
    		opX = pX;
    		opY = pY;
    		opZ = pZ;

    		pZ = opZ * Math.cos(pitRad) - opY * Math.sin(pitRad);
    		pY = opY * Math.cos(pitRad) + opZ * Math.sin(pitRad);

		    int screenWidth = mc.getWindow().getGuiScaledWidth();
		    int screenHeight = mc.getWindow().getGuiScaledHeight();
		    int fov = mc.options.fov().get();
		    double screenDist = screenWidth / 3.5 / Math.tan(fov / 2 * PI / 180);
		    
		    double screenX = -pX / pZ * screenDist + screenWidth / 2;
    		double screenY = -pY / pZ * screenDist + screenHeight / 2;
    		return new Vector3f((float)screenX, (float)screenY, (float)(distance * (pZ / Math.abs(pZ))));
    	}

    	private RenderUtils() {
        }
}
