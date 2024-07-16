package net.plastoid501.rrae.mixin;

import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DimensionType.class)
public interface IDimensionTypeMixin {
    @Invoker("respawnAnchorWorks")
    boolean getRespawnAnchorWorks();
}
