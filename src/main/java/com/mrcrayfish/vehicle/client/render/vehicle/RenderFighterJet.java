package com.mrcrayfish.vehicle.client.render.vehicle;

import com.mrcrayfish.vehicle.client.SpecialModels;
import com.mrcrayfish.vehicle.client.render.AbstractRenderVehicle;
import com.mrcrayfish.vehicle.common.Seat;
import com.mrcrayfish.vehicle.entity.VehicleProperties;
import com.mrcrayfish.vehicle.entity.vehicle.EntityFighterJet;
import com.mrcrayfish.vehicle.util.RenderUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;

public class RenderFighterJet extends AbstractRenderVehicle<EntityFighterJet>
{
    @Override
    public void render(EntityFighterJet entity, float partialTicks)
    {
        this.renderDamagedPart(entity, SpecialModels.FIGHTER_JET_BODY.getModel());

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(-0.1, -5.6 * 0.0625, 8 * 0.0725);
            GlStateManager.translate(8 * 0.0625, 0, 0);
            GlStateManager.translate(6 * 0.0625, 0, 0);
            GlStateManager.rotate(-5F, 1, -10, 0);
            this.renderDamagedPart(entity, SpecialModels.FIGHTER_JET_WING.getModel());
        }
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(0.1, -5.6 * 0.0625, 8 * 0.0725);
            GlStateManager.rotate(180F, 0, 180, 1);
            GlStateManager.translate(8 * 0.0625, 0.0625, 0);
            GlStateManager.translate(6 * 0.0625, 0, 0);
            GlStateManager.rotate(5F, 1, -10, 0);
            this.renderDamagedPart(entity, SpecialModels.FIGHTER_JET_WING.getModel());
        }
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(0, -0.5, 0);
            GlStateManager.scale(0.85, 0.85, 0.85);
            this.renderWheel(entity, 0F, -3 * 0.0625F, 24 * 0.0625F, 0F, partialTicks);
            this.renderWheel(entity, 7.5F * 0.0625F, -3 * 0.0625F, 2 * 0.0625F, 100F, partialTicks);
            this.renderWheel(entity, -7.5F * 0.0625F, -3 * 0.0625F, 2 * 0.0625F, -100F, partialTicks);
        }
        GlStateManager.popMatrix();
    }

    private void renderWheel(EntityFighterJet vehicle, float offsetX, float offsetY, float offsetZ, float legRotation, float partialTicks)
    {
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(offsetX, offsetY, offsetZ);
            this.renderDamagedPart(vehicle, SpecialModels.FIGHTER_JET_WHEEL_COVER.getModel());

            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(0, -2.25F / 16F, 0);
                GlStateManager.pushMatrix();
                {
                    if(vehicle.isMoving())
                    {
                        float wheelRotation = vehicle.prevWheelRotation + (vehicle.wheelRotation - vehicle.prevWheelRotation) * partialTicks;
                        GlStateManager.rotate(-wheelRotation, 1, 0, 0);
                    }
                    GlStateManager.scale(0.8F, 0.8F, 0.8F);
                    IBakedModel model = RenderUtil.getWheelModel(vehicle);
                    if(model != null)
                    {
                        RenderUtil.renderColoredModel(model, ItemCameraTransforms.TransformType.NONE, vehicle.getWheelColor());
                    }
                }
                GlStateManager.popMatrix();
            }
            GlStateManager.popMatrix();

            GlStateManager.rotate(legRotation, 0, 1, 0);
            this.renderDamagedPart(vehicle, SpecialModels.FIGHTER_JET_LEG.getModel());
        }
        GlStateManager.popMatrix();
    }

    @Override
    public void applyPlayerModel(EntityFighterJet entity, EntityPlayer player, ModelPlayer model, float partialTicks)
    {
        model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-85F);
        model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(10F);
        model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-85F);
        model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-10F);
    }

    @Override
    public void applyPlayerRender(EntityFighterJet entity, EntityPlayer player, float partialTicks)
    {
        int index = entity.getSeatTracker().getSeatIndex(player.getUniqueID());
        if(index != -1)
        {
            VehicleProperties properties = entity.getProperties();
            Seat seat = properties.getSeats().get(index);
            Vec3d seatVec = seat.getPosition().addVector(0, properties.getAxleOffset() + properties.getWheelOffset(), 0).scale(properties.getBodyPosition().getScale());
            seatVec = new Vec3d(-seatVec.x, seatVec.y, seatVec.z);
            seatVec = seatVec.scale(0.0625);
            double scale = 32.0 / 30.0;
            double offsetX = seatVec.x * scale;
            double offsetY = (seatVec.y + player.getYOffset() - 0.5) * scale + 24 * 0.0625; //Player is 2 blocks high tall but renders at 1.8 blocks tall
            double offsetZ = seatVec.z * scale;
            GlStateManager.translate(offsetX, offsetY, offsetZ);
            float bodyPitch = entity.prevBodyRotationX + (entity.bodyRotationX - entity.prevBodyRotationX) * partialTicks;
            float bodyRoll = entity.prevBodyRotationZ + (entity.bodyRotationZ - entity.prevBodyRotationZ) * partialTicks;
            GlStateManager.rotate(bodyRoll, 0, 0, 1);
            GlStateManager.rotate(-bodyPitch, 1, 0, 0);
            GlStateManager.translate(-offsetX, -offsetY, -offsetX);
        }
    }
}
