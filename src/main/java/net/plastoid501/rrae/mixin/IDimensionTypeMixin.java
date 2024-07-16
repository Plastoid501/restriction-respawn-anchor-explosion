package net.plastoid501.rrae.mixin;

import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DimensionType.class)
public interface IDimensionTypeMixin {
    @Accessor("respawnAnchorWorks")
    boolean getRespawnAnchorWorks();
}
