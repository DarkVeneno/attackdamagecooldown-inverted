package quatum.no_attackcooldowndamagedelay.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import quatum.no_attackcooldowndamagedelay.Config;
import quatum.no_attackcooldowndamagedelay.NoAttackCooldown_DamageDelay;

import java.util.Objects;

/*
 * This file was modified by Carlos Almeida.
 *
 * Changes made:
 * - Inverted first condition on line 39 from !Config.damageTypesListValue.contains(damageType) to Config.damageTypesListValue.contains(damageType)
 *
 * Modified: 2026-09-18 (YYYY-MM-DD)
 *
 * This file is licensed under the GNU General Public License
 * version 2, as provided by the original project.
 */

@EventBusSubscriber
public class NoDamageDelay {
    @SubscribeEvent
    public static void onLivingDamage(LivingIncomingDamageEvent event) {
        if (event == null || event.getEntity() == null) {
            return;
        }

        String damageType = Config.damageType_to_CorospoigString(Objects.requireNonNull(event.getSource().typeHolder().unwrapKey().get()));
        var sourceEntity = event.getSource().getEntity();

        if (Config.LogDamageValue) {
            NoAttackCooldown_DamageDelay.LOGGER.info(damageType);
        }

        if (Config.NoDamageDelayValue && !event.getEntity().level().isClientSide()) {
            if (Config.damageTypesListValue.contains(damageType)&&(sourceEntity == null || !Config.blacklistedEntitysValue.contains(Config.entity_to_CotospoigStrig(sourceEntity)))) {
                event.getEntity().invulnerableTime = 0;
            }
        }
    }
}
