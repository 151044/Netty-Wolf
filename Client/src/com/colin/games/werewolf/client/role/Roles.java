package com.colin.games.werewolf.client.role;

import com.colin.games.werewolf.common.roles.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class Roles {
    private Roles(){
        throw new AssertionError();
    }
    private static final Map<String, Supplier<? extends Role>> makeMap = new HashMap<>();
    public static void register(String lookupName,Supplier<? extends Role> supplier) {
        makeMap.put(lookupName,supplier);
    }
    public static Role makeNew(String lookup) {
        Objects.requireNonNull(lookup);
        return makeMap.get(lookup).get();
    }
}
