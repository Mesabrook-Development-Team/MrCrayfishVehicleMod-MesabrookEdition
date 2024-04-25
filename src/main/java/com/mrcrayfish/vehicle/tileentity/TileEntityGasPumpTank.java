package com.mrcrayfish.vehicle.tileentity;

import com.mrcrayfish.vehicle.init.ModFluids;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

/**
 * Author: MrCrayfish
 */
public class TileEntityGasPumpTank extends TileFluidHandlerSynced
{
    public TileEntityGasPumpTank()
    {
        tank = new FluidTank(1)
        {
            @Override
            protected void onContentsChanged()
            {
                syncToClient();
            }

            @Override
            public boolean canFillFluidType(FluidStack fluid)
            {
                return fluid.getFluid() == FluidRegistry.getFluid("gasoline") || fluid.getFluid() == FluidRegistry.getFluid("diesel") || fluid.getFluid() == FluidRegistry.getFluid("biodiesel");
            }
        };
        tank.setCanFill(true);
        tank.setCanDrain(true);
    }

    public FluidTank getFluidTank()
    {
        return tank;
    }
}