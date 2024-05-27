package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.VehicleConfig;
import com.mrcrayfish.vehicle.client.EntityRaytracer.IEntityRaytraceable;
import com.mrcrayfish.vehicle.entity.EngineType;
import com.mrcrayfish.vehicle.entity.EntityPlane;
import com.mrcrayfish.vehicle.entity.EntityPoweredVehicle.AccelerationDirection;
import com.mrcrayfish.vehicle.init.ModSounds;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
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
        this.setMaxSpeed(30F);
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

        if(this.getControllingPassenger() != null && this.getAcceleration() == AccelerationDirection.FORWARD)
        {
            for(int i = 0; i < 4; i++)
            {
                world.spawnParticle(EnumParticleTypes.CLOUD, posX - 0.25 + 0.5 * rand.nextGaussian(), posY + 0.5 * rand.nextGaussian(), posZ - 0.25 + 0.5 * rand.nextGaussian(), 0, 0, 0, 0);
            }	
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
        
        if (this.getControllingPassenger() instanceof EntityPlayer && VehicleConfig.SERVER.experimentalVehicleFeatures)
        {
        	EntityPlayer player = (EntityPlayer) this.getControllingPassenger();
        	EnumHand hand = player.getActiveHand().MAIN_HAND;
        	ItemStack arrows = player.getHeldItem(hand);
        	
        	if(player.isSwingInProgress && arrows.getItem() instanceof ItemArrow && this.isFlying())
        	{
        		shootArrow(player);
        	}
        }
    }
    
    private void shootArrow(EntityPlayer player)
    {
    	if (!this.world.isRemote)
        {
            ItemStack arrowStack = new ItemStack(Items.ARROW);
            EntityArrow entityArrow = new EntityTippedArrow(this.world, player);
            
            // Get the direction the jet is facing
            float yaw = this.rotationYaw;
            float pitch = this.rotationPitch;
            
            // Calculate the direction vector
            double x = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
            double y = -MathHelper.sin(pitch * 0.017453292F);
            double z = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
            
            // Set the arrow's position and motion
            entityArrow.setPosition(this.posX + x, this.posY + this.getMountedYOffset() + y, this.posZ + z);
            
            // Calculate current speed of the jet
            double jetSpeed = Math.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            
            // Set the arrow's motion to be faster than the jet
            double arrowSpeedMultiplier = 6;  // Arrow speed multiplier to ensure it's faster than the jet
            entityArrow.motionX = x * jetSpeed * arrowSpeedMultiplier;
            entityArrow.motionY = y * jetSpeed * arrowSpeedMultiplier;
            entityArrow.motionZ = z * jetSpeed * arrowSpeedMultiplier;
            
            // Spawn the arrow
            this.world.spawnEntity(entityArrow);
        }
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
