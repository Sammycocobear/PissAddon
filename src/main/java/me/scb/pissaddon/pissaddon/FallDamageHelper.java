package me.scb.pissaddon.pissaddon;
import org.bukkit.entity.LivingEntity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FallDamageHelper {
        private static ConcurrentMap<LivingEntity, Double> tracker = new ConcurrentHashMap<>();

    public static boolean hasFallDamageCap(LivingEntity entity) {
        return tracker.containsKey(entity);
    }

    public static double getFallDamageCap(LivingEntity entity) {
        return tracker.get(entity);
    }

    public static void addFallDamageCap(LivingEntity entity, double damagecap){
        if (hasFallDamageCap(entity)) {
            tracker.remove(entity);
        }
        tracker.put(entity, damagecap);
    }

    public static void removeFallDamageCap(LivingEntity entity) {
        tracker.remove(entity);
    }
}
