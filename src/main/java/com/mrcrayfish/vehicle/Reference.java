package com.mrcrayfish.vehicle;

/**
 * Author: MrCrayfish
 * Forked by RavenholmZombie for use on Mesabrook
 */
public class Reference
{
    public static final String MOD_ID = "vehicle";
    public static final String MOD_NAME = "MrCrayfish's Vehicle Mod - Mesabrook Edition";
    public static final String MOD_VERSION = "0.44.1-0.0.3";
    public static final String MOD_COMPATIBILITY = "[1.12.2]";
    public static final String MOD_DEPENDS = "required-after:immersiveengineering;required-after:immersivepetroleum;required-after:obfuscate@[0.2.5,);after:cfm@[4.1.5,);after:controllable@[0.8.0,);required-after:forge@[14.23.4.2705,)";
    public static final String LOG_PREFIX = "[" + MOD_NAME + "] ";
    
    public static final String PROXY_CLIENT = "com.mrcrayfish.vehicle.proxy.ClientProxy";
    public static final String PROXY_SERVER = "com.mrcrayfish.vehicle.proxy.ServerProxy";
}
