package com.yungnickyoung.minecraft.ribbits.world.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.data.RibbitData;
import com.yungnickyoung.minecraft.ribbits.module.RibbitProfessionModule;
import com.yungnickyoung.minecraft.ribbits.module.StructureProcessorTypeModule;
import com.yungnickyoung.minecraft.yungsapi.world.processor.StructureEntityProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class DebugStructureProcessor extends StructureEntityProcessor {
    public static final DebugStructureProcessor INSTANCE = new DebugStructureProcessor();
    public static final Codec<StructureProcessor> CODEC = Codec.unit(() -> INSTANCE);

    private static int ribbitCount = 0;

    @Nullable
    @Override
    public StructureTemplate.StructureEntityInfo processEntity(ServerLevelAccessor serverLevelAccessor, BlockPos structurePiecePos, BlockPos structurePieceBottomCenterPos, StructureTemplate.StructureEntityInfo localEntityInfo, StructureTemplate.StructureEntityInfo globalEntityInfo, StructurePlaceSettings structurePlaceSettings) {
        if (globalEntityInfo.nbt.getString("id").equals("ribbits:ribbit")) {
            DataResult<RibbitData> dataResult = RibbitData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, globalEntityInfo.nbt.get("RibbitData")));
            dataResult.resultOrPartial(RibbitsCommon.LOGGER::error).ifPresent(ribbitData -> {
                if (ribbitData.getProfession().equals(RibbitProfessionModule.NITWIT)) {
                    ribbitCount++;
                    RibbitsCommon.LOGGER.info("Ribbit count: " + ribbitCount);
                }
            });
        }
        return globalEntityInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return StructureProcessorTypeModule.DEBUG_STRUCTURE_PROCESSOR;
    }
}
