package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.client.EntityRaytracer;
import com.mrcrayfish.vehicle.entity.EngineType;
import com.mrcrayfish.vehicle.entity.EntityLandVehicle;
import com.mrcrayfish.vehicle.init.ModSounds;

import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityForklift extends EntityLandVehicle implements EntityRaytracer.IEntityRaytraceable
{

	public EntityForklift(World worldIn) 
	{
		super(worldIn);
        this.setMaxSpeed(5F);
        this.setTurnSensitivity(4);
        this.setFuelCapacity(30000F);
        this.setFuelConsumption(0.375F);
        this.stepHeight = 1F;
	}
	
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return this.getEntityBoundingBox().grow(3.5);
    }

	@Override
	public SoundEvent getMovingSound() 
	{
		return ModSounds.ELECTRIC_ENGINE_MONO;
	}

	@Override
	public SoundEvent getRidingSound() 
	{
		return ModSounds.ELECTRIC_ENGINE_STEREO;
	}

    @Override
    public float getMinEnginePitch()
    {
        return 0.75F;
    }

    @Override
    public float getMaxEnginePitch()
    {
        return 1.25F;
    }

    @Override
    public EngineType getEngineType()
    {
        return EngineType.ELECTRIC_MOTOR;
    }

    @Override
    public boolean canBeColored()
    {
        return false;
    }

    @Override
    public boolean canTowTrailer()
    {
        return false;
    }
    
    @Override
    public boolean canWheelie()
    {
        return false;
    }
	
}
