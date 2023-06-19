package de.materna.date4u.core.util;

import com.fasterxml.classmate.Annotations;

import java.util.Map;

public class NicknameToIdMapper {
    private static Map<String, Long> nicknameIdMap;

    public static boolean mapEmpty(){
        return nicknameIdMap == null;
    }

    public static void setNicknameIdMap(Map<String, Long> nicknameIdMap) {
        NicknameToIdMapper.nicknameIdMap = nicknameIdMap;
    }

    public static Long mapNicknameToId(String nickname) {
        return nicknameIdMap.get(nickname);
    }
}
