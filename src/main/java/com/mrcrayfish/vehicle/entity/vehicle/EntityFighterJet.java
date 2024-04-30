package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.client.EntityRaytracer.IEntityRaytraceable;
import com.mrcrayfish.vehicle.entity.EngineType;
import com.mrcrayfish.vehicle.entity.EntityPlane;
import com.mrcrayfish.vehicle.init.ModSounds;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityFighterJet extends EntityPlane implements IEntityRaytraceable
{
    public float wheelSpeed;
    public float wheelRotation;
    public float prevWheelRotation;

    public float propellerSpeed;
    public float propellerRotation;
    public float prevPropellerRotation;
    
    public EntityFighterJet(World worldIn)
    {
        super(worldIn);
        this.setAccelerationSpeed(1.5F);
        this.setMaxSpeed(35F);
        this.setMaxTurnAngle(35);
        this.setTurnSensitivity(3);
        this.setSize(3F, 1.6875F);
        this.setFuelCapacity(125000F);
        this.setFuelConsumption(1.2F);
    }
    
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return this.getEntityBoundingBox().grow(1.0);
    }

    @Override
    public void updateVehicle()
    {
        prevWheelRotation = wheelRotation;
        prevPropellerRotation = propellerRotation;

        if(this.isMoving())
        {
        	makeSmoke();
        }
        
        
        if(this.onGround)
        {
            wheelSpeed = currentSpeed / 30F;
        }
        else
        {
            wheelSpeed *= 0.95F;
        }
        wheelRotation -= (90F * wheelSpeed);

        if(this.canDrive() && this.getControllingPassenger() != null)
        {
            propellerSpeed += 1F;
            if(propellerSpeed > 120F)
            {
                propellerSpeed = 120F;
            }
        }
        else
        {
            propellerSpeed *= 0.95F;
        }
        propellerRotation += propellerSpeed;
    }
    
    public void makeSmoke()
    {
    	if(!this.world.isRemote)
    	{
    		return;
    	}
    	
    	double yaw = Math.toRadians(rotationYaw);
        double pitch = Math.toRadians(rotationPitch);
        
        double xOffset = -Math.sin(yaw) * Math.cos(pitch);
        double yOffset = -Math.sin(pitch);
        double zOffset = Math.cos(yaw) * Math.cos(pitch);
        
        double particleX = posX + xOffset * 0.5; // Adjust the distance as needed
        double particleY = posY + yOffset * 0.5;
        double particleZ = posZ + zOffset * 0.5;
        
//        spawnSmoke(particleX + 1.65, particleY + 1, particleZ - 3.55);
    }
    
    public void spawnSmoke(double x, double y, double z)
    {
    	world.spawnParticle(EnumParticleTypes.CLOUD, x, y, z, 0, 0, 0);
    }

    @Override
    public SoundEvent getMovingSound()
    {
        return ModSounds.JET_ENGINE_MONO;
    }

    @Override
    public SoundEvent getRidingSound()
    {
        return ModSounds.JET_ENGINE_STEREO;
    }

    @Override
    public EngineType getEngineType()
    {
        return EngineType.LARGE_MOTOR;
    }
    
    @Override
    public float getMinEnginePitch()
    {
        return 0.8F;
    }

    @Override
    public float getMaxEnginePitch()
    {
        return 1.6F;
    }

    @Override
    public boolean canBeColored()
    {
        return false;
    }

    @Override
    protected float getModifiedAccelerationSpeed()
    {
        return super.getModifiedAccelerationSpeed() * (propellerSpeed / 120F);
    }

    @Override
    public boolean canMountTrailer()
    {
        return false;
    }
}
