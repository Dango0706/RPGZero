package me.tuanzi.rpgzero.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Broken extends PotionEffect {

    private int duration;
    private int amplifier;

    /**
     * Creates a potion effect.
     *
     * @param type      effect type
     * @param duration  measured in ticks, see {@link
     *                  PotionEffect#getDuration()}
     * @param amplifier the amplifier, see {@link PotionEffect#getAmplifier()}
     * @param ambient   the ambient status, see {@link PotionEffect#isAmbient()}
     * @param particles the particle status, see {@link PotionEffect#hasParticles()}
     * @param icon      the icon status, see {@link PotionEffect#hasIcon()}
     */
    public Broken(int duration, int amplifier, boolean ambient, boolean particles) {
        super(PotionEffectType.BAD_OMEN, duration, amplifier, ambient, particles, true);
        this.duration = duration;
        this.amplifier = amplifier;
    }

    /**
     * Attempts to add the effect represented by this object to the given
     * {@link LivingEntity}.
     *
     * @param entity The entity to add this effect to
     * @return Whether the effect could be added
     * @see LivingEntity#addPotionEffect(PotionEffect)
     */
    @Override
    public boolean apply(LivingEntity entity) {
        return super.apply(entity);
    }




}
