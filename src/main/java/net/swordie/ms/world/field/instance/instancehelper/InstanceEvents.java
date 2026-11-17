package net.swordie.ms.world.field.instance.instancehelper;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.life.Reactor;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.loaders.containerclasses.MobSkillInfo;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.instance.Instance;

/**
 * Created on 03/07/2021.
 *
 * @author Asura
 */
public class InstanceEvents {

    /**
     * Event triggered when attacking
     *
     * @param chr character that did the attack
     * @param attackInfo attackInfo regarding that attack
     */
    public void onAttack(Char chr, AttackInfo attackInfo) {

    }

    /**
     * Event triggered when a skill is used (USER_SKILL_USE_REQUEST that passed the cooldown and MP checks)
     */
    public void onSkillUse(Char chr, int skillId, int slv) {

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
    public void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {

    }

    /**
     * Event triggered when an item is looted
     *
     * @param chr character that looted the item
     * @param item item that is looted by the character
     * @param quantity quantity looted
     */
    public void onItemLooted(Char chr, Item item, int quantity) {

    }

    /**
     * Event triggered when an item is dropped from inventory
     *
     * @param chr character that dropped the item
     * @param item item that is dropped
     * @param quantity quantity dropped
     * @param position drop's position
     */
    public void onItemDropped(Char chr, Item item, int quantity, Position position, Drop drop) {

    }

    /**
     * Event triggered when an item is dropped by a mob
     *
     * @param Field field that the item is dropped in
     * @param quantity quantity dropped
     * @param position drop's position
     */
    public void onItemDroppedByMob(Field field, int quantity, Position position, Drop drop){

    }

    /**
     * Event triggered when a Fixed Move Direction mob hits it's end point
     *
     * @param mob mob that has reached it's end point
     */
    public void onMobFixedMoveDirUpdate(Mob mob) {

    }

    /**
     * Event triggered when a mob has moved
     * @param mob mob that has moved
     */
    public void onMobMove(Mob mob) {

    }

    /**
     * Event triggered when a Mob uses a skill
     *
     * @param mob Mob that uses the skill
     * @param msi MobSkillInfo
     * @param msId MobSkillId
     */
    public void onMobSkill(Mob mob, MobSkillInfo msi, MobSkillID msId) {

    }

    /**
     * Event triggered when player lands in a portal that sends PortalTeleportRequest
     *
     * @param chr player that is affected
     * @param portal portal the player landed in
     */
    public void onPortalTeleportRequest(Char chr, Portal portal) {

    }

    /**
     * Event triggered when character dies
     * @param chr character that has died
     */
    public void onChrDeath(Char chr) {

    }

    /**
     * For fights that use their own gauge instead of a death count: true while it still lets this character revive
     * in the fight. Asked after onChrDeath.
     */
    public boolean canReviveInFight(Char chr) {
        return false;
    }

    /**
     * Event triggered when character gets hit
     * @param chr character that got hit
     * @param dmgAmount amount of damage that the character took
     */
    public void onChrHit(Char chr, int dmgAmount) {

    }

    /**
     * Event triggered when a monster's attack or skill hits a character (the client's hit report names the monster)
     * @param chr character that got hit
     * @param mobTemplateId the monster that hit
     * @param dmgAmount damage the client reported
     */
    public void onChrHitByMob(Char chr, int mobTemplateId, int dmgAmount) {

    }

    /**
     * Same hit as {@link #onChrHitByMob}, with the monster's attack index (Mob.wz info/attack/N; -1 = touch).
     */
    public void onChrHitByMobAttack(Char chr, int mobTemplateId, int attackIdx, int dmgAmount) {

    }

    /**
     * Event triggered when a mob dies
     * @param mob mob that has died
     */
    public void onMobDeath(Field field, Mob mob) {

    }

    /**
     * Event triggered when a Mob is spawned via Field#generateMobs.
     * @param field The Field on which the mob is spawned
     * @param mob The Mob that has been spawned
     */
    public void onMobSpawn(Field field, Mob mob) {

    }

    /**
     * Event triggered when a quick-time event succeeded
     * @param chr chr that succeeded the quick-time event
     */
    public void onQuickTimeEventSuccess(Char chr) {

    }

    /**
     * Event triggered when 'reactor activate by mob' packet is sent
     * @param chr character the packet is sent to
     * @param mob mob that activates the reactor
     * @param reactor reactor that is activated
     */
    public void onReactorRectInMob(Char chr, Mob mob, Reactor reactor) {

    }

    /**
     * Event triggered when the instance is cleared
     * @param instance instance that is cleared
     */
    public void onInstanceCleared(Instance instance) {
        if (instance != null && instance.getInstanceHelper() != null) {
            instance.getInstanceHelper().clearInstance(false);
        }
    }

    /**
     * Event triggered when character leaves instance
     * @param chr character that has left the instance
     */
    public void onChrRemoved(Char chr) {
        var tsm = chr.getTemporaryStatManager();

        // Temporary Skill Bar Remove
        chr.write(FieldPacket.updateTemporarySkillBar(0)); // resets special skill bar

        // Removed stats
        // TODO -> Once Damien InstanceEvents is created, move it to that file
        tsm.removeStat(CharacterTemporaryStat.Stigma);
    }

    /**
     * Event triggered when party is disbanded
     * @param party
     */
    public void onPartyDisband(Party party) {

    }

    /**
     * Event triggered when party is created
     * @param party
     */
    public void onPartyCreated(Party party) {

    }

    public void onWarp(Char chr, Field oldField, Field newField){

    }

    /**
     * Asked before a character uses a portal inside the instance (Field portals are shared by every copy of a map, so
     * a party quest's locked stage doors are decided here, per instance).
     *
     * @return false to keep the portal closed for this character
     */
    public boolean canUsePortal(Char chr, Field field, Portal portal) {
        return true;
    }

    /**
     * Asked before a map of the instance respawns its monsters (MobGen).
     *
     * @return false so the map's monsters only spawn once (stages where every monster must be defeated)
     */
    public boolean canRespawnMobs(Field field) {
        return true;
    }

    /**
     * Asked before a map of the instance spawns its own (Map.wz) monsters at all.
     *
     * @return false when the instance spawns that map's monsters itself (e.g. one kind at a time)
     */
    public boolean canSpawnMapMobs(Field field) {
        return true;
    }
}
