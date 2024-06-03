package com.yungnickyoung.minecraft.ribbits.util;

import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BufferUtils {
    public static List<Integer> readIntList(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(buf.readInt());
        }
        return list;
    }

    public static void writeIntList(List<Integer> list, FriendlyByteBuf buf) {
        buf.writeInt(list.size());
        for (int n : list) {
            buf.writeInt(n);
        }
    }

    public static List<UUID> readUUIDList(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<UUID> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(buf.readUUID());
        }
        return list;
    }

    public static void writeUUIDList(List<UUID> list, FriendlyByteBuf buf) {
        buf.writeInt(list.size());
        for (UUID uuid : list) {
            buf.writeUUID(uuid);
        }
    }
}
