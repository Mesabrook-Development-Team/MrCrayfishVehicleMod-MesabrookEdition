package com.mrcrayfish.vehicle.client.render.vehicle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrcrayfish.vehicle.client.EntityRayTracer;
import com.mrcrayfish.vehicle.client.model.SpecialModels;
import com.mrcrayfish.vehicle.client.render.AbstractLandVehicleRenderer;
import com.mrcrayfish.vehicle.client.render.Axis;
import com.mrcrayfish.vehicle.entity.properties.PoweredProperties;
import com.mrcrayfish.vehicle.entity.properties.VehicleProperties;
import com.mrcrayfish.vehicle.entity.vehicle.TractorEntity;
import com.mrcrayfish.vehicle.init.ModEntities;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class TractorRenderer extends AbstractLandVehicleRenderer<TractorEntity>
{
    public TractorRenderer(VehicleProperties defaultProperties)
    {
        super(defaultProperties);
    }

    @Override
    public void render(@Nullable TractorEntity vehicle, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, float partialTicks, int light)
    {
        this.renderDamagedPart(vehicle, SpecialModels.TRACTOR.getModel(), matrixStack, renderTypeBuffer, light);

        //Render the handles bars
        matrixStack.pushPose();
        matrixStack.translate(0, 0.66, -0.475);
        matrixStack.mulPose(Axis.POSITIVE_X.rotationDegrees(-67.5F));
        matrixStack.translate(0, -0.02, 0);
        matrixStack.scale(0.9F, 0.9F, 0.9F);
        float wheelAngle = this.wheelAngleProperty.get(vehicle, partialTicks);
        float maxSteeringAngle = this.vehiclePropertiesProperty.get(vehicle).getExtended(PoweredProperties.class).getMaxSteeringAngle();
        float steeringWheelRotation = (wheelAngle / maxSteeringAngle) * 25F;
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(steeringWheelRotation));
        this.renderDamagedPart(vehicle, SpecialModels.GO_KART_STEERING_WHEEL.getModel(), matrixStack, renderTypeBuffer, light);
        matrixStack.popPose();
    }

    @Override
    public void applyPlayerModel(TractorEntity entity, PlayerEntity player, PlayerModel model, float partialTicks)
    {
        model.rightLeg.xRot = (float) Math.toRadians(-75F);
        model.rightLeg.yRot = (float) Math.toRadians(20F);
        model.leftLeg.xRot = (float) Math.toRadians(-75F);
        model.leftLeg.yRot = (float) Math.toRadians(-20F);

        float wheelAngle = this.wheelAngleProperty.get(entity, partialTicks);
        float maxSteeringAngle = this.vehiclePropertiesProperty.get(entity).getExtended(PoweredProperties.class).getMaxSteeringAngle();
        float steeringWheelRotation = (wheelAngle / maxSteeringAngle) * 25F / 2F;
        model.rightArm.xRot = (float) Math.toRadians(-55F - steeringWheelRotation);
        model.rightArm.yRot = (float) Math.toRadians(-10F);
        model.leftArm.xRot = (float) Math.toRadians(-55F + steeringWheelRotation);
        model.leftArm.yRot = (float) Math.toRadians(10F);
    }

    @Nullable
    @Override
    public EntityRayTracer.IRayTraceTransforms getRayTraceTransforms()
    {
        return (tracer, transforms, parts) ->
        {
            EntityRayTracer.createTransformListForPart(SpecialModels.TRACTOR, parts, transforms);
            EntityRayTracer.createTransformListForPart(SpecialModels.GO_KART_STEERING_WHEEL, parts, transforms,
                    EntityRayTracer.MatrixTransformation.createTranslation(0.0F, 0.66F, -0.475F),
                    EntityRayTracer.MatrixTransformation.createRotation(Axis.POSITIVE_X, -67.5F),
                    EntityRayTracer.MatrixTransformation.createTranslation(0.0F, -0.02F, 0.0F),
                    EntityRayTracer.MatrixTransformation.createScale(0.9F));
            EntityRayTracer.createFuelPartTransforms(ModEntities.TRACTOR.get(), SpecialModels.FUEL_DOOR_CLOSED, parts, transforms);
            EntityRayTracer.createKeyPortTransforms(ModEntities.TRACTOR.get(), parts, transforms);
        };
    }
}
