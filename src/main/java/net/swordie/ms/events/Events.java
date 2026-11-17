package net.swordie.ms.events;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.GameConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.life.Reactor;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.loaders.containerclasses.MobSkillInfo;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelperFactory;

/**
 * Created on 08/02/2022.
 *
 * @author Asura
 */
public class Events {

    /**
     * Event triggered when attacking
     *
     * @param chr character that did the attack
     * @param attackInfo attackInfo regarding that attack
     */
    public static void onAttack(Char chr, AttackInfo attackInfo) {
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(chr.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onAttack(chr, attackInfo);
        }
    }

    /**
     * Event triggered when a skill is used
     */
    public static void onSkillUse(Char chr, int skillId, int slv) {
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(chr.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onSkillUse(chr, skillId, slv);
        }
    }

    /**
     * Event triggered when a mob is damaged
     *
     * @param attacker character that dealt the damage
     * @param mob mob that took the damage
     * @param damage total amount of damage done
     * @param oldHp mob's old HP
     * @param newHp mob's new HP
     */
    public static void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        net.swordie.ms.life.mob.boss.papulatus.PapulatusPhases.onDamaged(mob);
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(mob.getField().getInstance());
        if (instanceHelper != null) {
            instanceHelper.onMobDamaged(attacker, mob, damage, oldHp, newHp);
        } else {
            instanceHelper = InstanceHelperFactory.getHelperByMob(mob);
            if (instanceHelper != null) {
                instanceHelper.onMobDamaged(attacker, mob, damage, oldHp, newHp);
            }
        }

        var fieldEvent = mob.getField().getFieldEvent();
        if (fieldEvent != null) {
            fieldEvent.onMobDamaged(attacker, mob, damage, oldHp, newHp);
        }

        if (newHp <= 0 && attacker != null && attacker.getField() != null) {
            attacker.getField().getFieldOwnershipManager().updateActiveChar(attacker);
        }
    }

    /**
     * Event triggered when an item is looted
     *
     * @param chr character that looted the item
     * @param item item that is looted by the character
     * @param quantity quantity looted
     */
    public static void onItemLooted(Char chr, Item item, int quantity) {
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(chr.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onItemLooted(chr, item, quantity);
        }
    }

    /**
     * Event triggered when an item is dropped from inventory
     *
     * @param chr character that dropped the item
     * @param item item that is dropped
     * @param quantity quantity dropped
     * @param position drop's position
     */
    public static void onItemDropped(Char chr, Item item, int quantity, Position position, Drop drop) {
        // Items that reactors react to (the Haunted Mansion's Candle Chests)
        net.swordie.ms.world.SeasonalEvents.onItemDropped(chr, item, position, drop);
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(chr.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onItemDropped(chr, item, quantity, position, drop);
        } else {
            // The official item-drop reactors of normal maps (Reactor.wz; instances handle their own drops)
            net.swordie.ms.life.ReactorItemEvents.onItemDropped(chr, item, quantity, position, drop);
        }
    }

    /**
     * Event triggered when an item is dropped from a mob
     *
     * @param quantity quantity dropped
     * @param position drop's position
     */
    public static void onItemDroppedByMob(Field field, int quantity, Position position, Drop drop){
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(field.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onItemDroppedByMob(field, quantity, position, drop);
        }
    }

    /**
     * Event triggered when a Fixed Move Direction mob hits it's end point
     *
     * @param mob mob that has reached it's end point
     */
    public static void onMobFixedMoveDirUpdate(Mob mob) {
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(mob.getField().getInstance());
        if (instanceHelper != null) {
            instanceHelper.onMobFixedMoveDirUpdate(mob);
        }
    }

    /**
     * Event triggered when a mob has moved
     * @param mob mob that has moved
     */
    public static void onMobMove(Mob mob) {
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(mob.getField().getInstance());
        if (instanceHelper != null) {
            instanceHelper.onMobMove(mob);
        }
    }

    /**
     * Event triggered when a Mob uses a skill
     *
     * @param mob Mob that uses the skill
     * @param msi MobSkillInfo
     * @param msId MobSkillId
     */
    public static void onMobSkill(Mob mob, MobSkillInfo msi, MobSkillID msId) {
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(mob.getField().getInstance());
        if (instanceHelper != null) {
            instanceHelper.onMobSkill(mob, msi, msId);
        }
    }

    /**
     * Event triggered when player lands in a portal that sends PortalTeleportRequest
     *
     * @param chr player that is affected
     * @param portal portal the player landed in
     */
    public static void onPortalTeleportRequest(Char chr, Portal portal) {
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(chr.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onPortalTeleportRequest(chr, portal);
        }
    }

    /**
     * Event triggered when character dies
     * @param chr character that has died
     */
    public static void onChrDeath(Char chr) {
        net.swordie.ms.life.mob.skill.MobSkillModule.onChrDeath(chr);
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(chr.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onChrDeath(chr);
        }
    }

    /**
     * Event triggered when character gets hit
     * @param chr character that got hit
     * @param dmgAmount amount of damage that the character took
     */
    public static void onChrHit(Char chr, int dmgAmount) {
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(chr.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onChrHit(chr, dmgAmount);
        }
    }

    /** A monster's attack or skill hit the character (see {@link InstanceEvents#onChrHitByMob}). */
    public static void onChrHitByMob(Char chr, int mobTemplateId, int dmgAmount) {
        onChrHitByMob(chr, mobTemplateId, -1, dmgAmount);
    }

    public static void onChrHitByMob(Char chr, int mobTemplateId, int attackIdx, int dmgAmount) {
        if (chr.getInstance() == null) {
            return;
        }
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(chr.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onChrHitByMob(chr, mobTemplateId, dmgAmount);
            instanceHelper.onChrHitByMobAttack(chr, mobTemplateId, attackIdx, dmgAmount);
        }
    }

    /** Only the attack hook of {@link #onChrHitByMob}: a server-played character's hit (fight gauges). */
    public static void onChrHitByMobAttack(Char chr, int mobTemplateId, int attackIdx, int dmgAmount) {
        if (chr.getInstance() == null) {
            return;
        }
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(chr.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onChrHitByMobAttack(chr, mobTemplateId, attackIdx, dmgAmount);
        }
    }

    /**
     * Event triggered when a mob dies
     * @param mob mob that has died
     */
    public static void onMobDeath(Field field, Mob mob) {
        net.swordie.ms.life.mob.boss.papulatus.PapulatusPhases.onDeath(mob);
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(field.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onMobDeath(field, mob);
        }
    }

    /**
     * Event triggered when a Mob is spawned via Field#generateMobs.
     * @param field The Field on which the mob is spawned
     * @param mob The Mob that has been spawned
     */
    public static void onMobSpawn(Field field, Mob mob) {
        net.swordie.ms.life.mob.boss.papulatus.PapulatusPhases.onSpawn(mob);
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(field.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onMobSpawn(field, mob);
        }
    }

    /**
     * Event triggered when a quick-time event succeeded
     * @param chr chr that succeeded the quick-time event
     */
    public static void onQuickTimeEventSuccess(Char chr) {
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(chr.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onQuickTimeEventSuccess(chr);
        }
    }

    /**
     * Event triggered when 'reactor activate by mob' packet is sent
     * @param chr character the packet is sent to
     * @param mob mob that activates the reactor
     * @param reactor reactor that is activated
     */
    public static void onReactorRectInMob(Char chr, Mob mob, Reactor reactor) {
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(chr.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onReactorRectInMob(chr, mob, reactor);
        }
    }

    /**
     * Event triggered when party is disbanded
     * @param party
     */
    public static void onPartyDisband(Party party) {
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(party.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onPartyDisband(party);
        }
    }

    /**
     * Event triggered when party is created
     * @param party
     */
    public static void onPartyCreated(Party party) {
        var instanceHelper = InstanceHelperFactory.getHelperByInstance(party.getInstance());
        if (instanceHelper != null) {
            instanceHelper.onPartyCreated(party);
        }
    }

    /**
     * Event triggered when chr warps or logs out (newField == null)
     * @param chr
     * @param newField
     */
    public static void onWarp(Char chr, Field currentField, Field newField) {

        if (chr.getJobHandler() != null) {
            chr.getJobHandler().onWarp(currentField, newField);
        }

        // Field Ownership
        if (currentField != null && currentField.getFieldOwnershipManager() != null) {
            currentField.getFieldOwnershipManager().removeActiveChar(chr);
        }

        // QoL: remember visited towns for @go
        // Only real towns count: a town map that is its own return map, not a Free Market room or an event/tutorial map
        if (newField != null && newField.getInstance() == null && newField.getInfo() != null
                && newField.getInfo().isTown() && newField.getInfo().getReturnMap() == newField.getId()
                && newField.getId() < 900000000 && !GameConstants.isFreeMarketField(newField.getId()) // hardcode-ok
                && chr.getQuestManager() != null) {
            String visited = chr.getQuestManager().getHiddenRecord(GameConstants.VISITED_TOWNS_QR);
            String id = String.valueOf(newField.getId());
            if (visited == null || visited.isEmpty()) {
                chr.getQuestManager().setHiddenRecord(GameConstants.VISITED_TOWNS_QR, id);
            } else if (!Arrays.asList(visited.split(",")).contains(id)) {
                // quest records are stored in a varchar(255): forget the oldest towns when the list gets too long
                List<String> towns = new ArrayList<>(Arrays.asList(visited.split(",")));
                towns.add(id);
                while (towns.size() > 1 && String.join(",", towns).length() > 250) {
                    towns.remove(0);
                }
                chr.getQuestManager().setHiddenRecord(GameConstants.VISITED_TOWNS_QR, String.join(",", towns));
            }
            net.swordie.ms.client.character.OnboardingModule.onEnterTown(chr, newField); // first town: welcome box
        }

        net.swordie.ms.client.character.PlayStyleModule.onWarp(chr, newField); // play style question / Express job window
        net.swordie.ms.client.character.NewPlayerModule.onWarp(chr, newField); // onboarding checklist: Free Market

        if(newField != null) {
            var instanceHelper = InstanceHelperFactory.getHelperByInstance(newField.getInstance());
            if (instanceHelper != null) {
                instanceHelper.onWarp(chr, currentField, newField);
            }
        }
    }

    public static void onRemoveCTS(Char chr, CharacterTemporaryStat cts, Option option) {
        if (chr != null && chr.getJobHandler() != null) {
            chr.getJobHandler().handleRemoveCTS(cts, option);
        }
    }

    public static void onPutCTS(Char chr, CharacterTemporaryStat cts, Option option) {
        if (chr != null && chr.getJobHandler() != null) {
            chr.getJobHandler().handlePutCTS(cts, option);
        }
    }
}
