package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.client.EntityRaytracer;
import com.mrcrayfish.vehicle.entity.EngineType;
import com.mrcrayfish.vehicle.entity.EntityMotorcycle;
import com.mrcrayfish.vehicle.entity.EntityPoweredVehicle.FuelPort;
import com.mrcrayfish.vehicle.init.ModSounds;

import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBicycle extends EntityMotorcycle implements EntityRaytracer.IEntityRaytraceable
{
	public EntityBicycle(World worldIn)
	{
        super(worldIn);
        this.setMaxSpeed(7F);
        this.setTurnSensitivity(10);
        this.setFuelCapacity(0F);
        this.setFuelConsumption(0.0F);
	}
	
    @Override
    public SoundEvent getMovingSound()
    {
        return ModSounds.DIRT_BIKE_ENGINE_MONO;
    }

    @Override
    public SoundEvent getRidingSound()
    {
        return ModSounds.DIRT_BIKE_ENGINE_STEREO;
    }

    @Override
    public EngineType getEngineType()
    {
        return EngineType.SMALL_MOTOR;
    }

    @Override
    public float getMinEnginePitch()
    {
        return 0.85F;
    }

    @Override
    public float getMaxEnginePitch()
    {
        return 1.5F;
    }

    @Override
    public boolean canBeColored()
    {
        return true;
    }

    @Override
    public boolean isLockable()
    {
        return false;
    }
}
