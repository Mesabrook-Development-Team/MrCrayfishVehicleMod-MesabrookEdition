package com.mrcrayfish.vehicle.client.render.vehicle;

import com.mrcrayfish.vehicle.client.SpecialModels;
import com.mrcrayfish.vehicle.client.render.AbstractRenderVehicle;
import com.mrcrayfish.vehicle.client.render.Wheel;
import com.mrcrayfish.vehicle.entity.vehicle.EntityBicycle;
import com.mrcrayfish.vehicle.entity.vehicle.EntityDuneBuggy;
import com.mrcrayfish.vehicle.util.RenderUtil;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayer;

public class RenderBicycle extends AbstractRenderVehicle<EntityBicycle>
{
	@Override
    public void render(EntityBicycle entity, float partialTicks)
    {
		this.renderDamagedPart(entity, SpecialModels.BICYCLE.getModel());
		
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0, 0.0, 10.5 * 0.0625);
        GlStateManager.rotate(-22.5F, 1, 0, 0);

        float wheelAngle = entity.prevRenderWheelAngle + (entity.renderWheelAngle - entity.prevRenderWheelAngle) * partialTicks;
        float wheelAngleNormal = wheelAngle / 45F;
        float turnRotation = wheelAngleNormal * 25F;

        GlStateManager.rotate(turnRotation, 0, 1, 0);
        GlStateManager.rotate(22.5F, 1, 0, 0);
        GlStateManager.translate(0.0, 0.0, -10.5 * 0.0625);

        this.renderDamagedPart(entity, SpecialModels.DIRT_BIKE_HANDLES.getModel());

        if(entity.hasWheels())
        {
            Wheel wheel = entity.getProperties().getWheels().stream().filter(wheel1 -> wheel1.getPosition() == Wheel.Position.FRONT).findFirst().orElse(null);
            if(wheel != null)
            {
                GlStateManager.pushMatrix();
                GlStateManager.translate(0, -0.5, 0);
                GlStateManager.translate(wheel.getOffsetX() * 0.0625, wheel.getOffsetY() * 0.0625, wheel.getOffsetZ() * 0.0625);
                float frontWheelSpin = entity.prevFrontWheelRotation + (entity.frontWheelRotation - entity.prevFrontWheelRotation) * partialTicks;
                if(entity.isMoving())
                {
                    GlStateManager.rotate(-frontWheelSpin, 1, 0, 0);
                }
                GlStateManager.scale(wheel.getScaleX(), wheel.getScaleY(), wheel.getScaleZ());
                GlStateManager.rotate(180F, 0, 1, 0);
                this.renderDamagedPart(entity, RenderUtil.getWheelModel(entity));
                GlStateManager.popMatrix();
            }
        }

        GlStateManager.popMatrix();
    }
	
    @Override
    public void applyPlayerModel(EntityBicycle entity, EntityPlayer player, ModelPlayer model, float partialTicks)
    {
        float wheelAngle = entity.prevRenderWheelAngle + (entity.renderWheelAngle - entity.prevRenderWheelAngle) * partialTicks;
        float wheelAngleNormal = wheelAngle / 45F;
        float turnRotation = wheelAngleNormal * 8F;
        model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-50F - turnRotation);
        model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-50F + turnRotation);
        model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-65F);
        model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(30F);
        model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-65F);
        model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-30F);
    }
}
