package com.yungnickyoung.minecraft.ribbits.data;

import net.minecraft.resources.ResourceLocation;

public class RibbitUmbrellaType {
    private final ResourceLocation id;
    private final String modelLocationSuffix;

    public RibbitUmbrellaType(ResourceLocation id, String modelLocationSuffix) {
        this.id = id;
        this.modelLocationSuffix = modelLocationSuffix;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public String getModelLocationSuffix() {
        return this.modelLocationSuffix;
    }

    @Override
    public String toString() {
        return this.id.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof RibbitUmbrellaType other)) {
            return false;
        } else {
            return this.id.equals(other.getId());
        }
    }
}
