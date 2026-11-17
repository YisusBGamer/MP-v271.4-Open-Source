package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.partyquests.towerofoz.OzTowerModule;
import net.swordie.ms.connection.packet.ReactorPool;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.OzConstants;
import net.swordie.ms.enums.WeatherEffNoticeType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.drop.DropInfo;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.containerclasses.MobSkillInfo;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.instance.Instance;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created on 03/07/2021.
 *
 * @author Asura
 */
public class TowerOfOzInstanceHelper extends PartyQuestInstanceHelper {
    @Override
    public void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {

        var field = mob.getField();
        var fieldId = field.getId();
        var templateId = mob.getTemplateId();
        var instance = attacker.getInstance();

        if (fieldId == OzConstants.F14_FPS_FIELD) {
            onFpsMobDamaged(attacker, field, instance, newHp);
            return;
        }
        switch (OzTowerModule.getFloorOfMainField(fieldId)) {
            // Undersea Tower of Oz : Undersea 1F
            case 1:
                // GMS: 100 Ancient Slimes or the Ancient King Slime
                if (newHp > 0 || OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                    break;
                }
                if (templateId == OzConstants.ANCIENT_KING_SLIME) {
                    instance.broadcast(UserPacket.scriptProgressMessage("Ancient King Slime Eliminated 1/1"));
                    clearFloor(instance, attacker, "Great! Now you can help me with the next floor!");
                    break;
                }
                var slimeKills = (int) instance.getProperty(OzConstants.KILL_COUNT) + 1;
                instance.addProperty(OzConstants.KILL_COUNT, slimeKills);
                instance.broadcast(UserPacket.scriptProgressMessage(
                        String.format("Ancient Slimes Eliminated: %d/%d", slimeKills, OzConstants.F1_KILL_COUNT)));
                if (slimeKills >= OzConstants.F1_KILL_COUNT) {
                    clearFloor(instance, attacker, "Great! Now you can help me with the next floor!");
                }
                break;

            // Undersea Tower of Oz : Undersea 2F
            case 2:
                if (newHp <= 0) {
                    var drop = new Drop(-1, ItemData.getItemDeepCopy(OzConstants.F2_CARD_BREAKER_ITEM_ID));
                    field.drop(drop, mob.getPosition(), mob.getPosition(), true);
                }
                break;

            // Undersea Tower of Oz : Undersea 3F
            case 3:
                if (newHp <= 0) {
                    var dropItem = templateId == OzConstants.ANCIENT_BLUE_TURTLE ? OzConstants.F3_ANCIENT_BLUE_TURTLE_EGG : OzConstants.F3_ANCIENT_ORANGE_TURTLE_EGG;
                    var dropInfos = new HashSet<DropInfo>();
                    for (var i = 0; i < 7; i++) {
                        var di = new DropInfo(dropItem, 4000, 2, 4);
                        dropInfos.add(di);
                    }
                    field.drop(dropInfos, mob.getPosition(), 0);
                }
                break;

            // Undersea Tower of Oz : Undersea 4F
            case 4:
                if (newHp <= 0) {
                    var isRightSide = ((String) instance.getProperty(OzConstants.F4_CUR_SIDE)).equalsIgnoreCase("Right");
                    var curBalancePoints = (int) instance.getProperty(OzConstants.F4_BALANCE_POINTS);
                    var newBalancePoints = 0;
                    if (OzConstants.F4_RIGHT_SIDE_RECT.hasPositionInside(attacker.getPosition()) && !isRightSide // Attacking on Right side & Leaning Left
                        || !OzConstants.F4_RIGHT_SIDE_RECT.hasPositionInside(attacker.getPosition()) && isRightSide) { // Attacking on Left side & Leaning Right
                        // Gain Balance Points
                        newBalancePoints = curBalancePoints + OzConstants.F4_BALANCE_POINTS_PER_MOB;
                    } else {
                        // Deduct Balance Points
                        newBalancePoints = curBalancePoints - OzConstants.F4_BALANCE_POINTS_PER_MOB;
                    }

                    newBalancePoints = Math.max(0, Math.min(newBalancePoints, OzConstants.F4_BALANCE_POINTS_REQ));
                    instance.addProperty(OzConstants.F4_BALANCE_POINTS, newBalancePoints);

                    if (!OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                        var msg = String.format("Balance Points: %d/%d", newBalancePoints, OzConstants.F4_BALANCE_POINTS_REQ);
                        instance.broadcast(UserPacket.scriptProgressMessage(msg));
                    }

                    if (newBalancePoints >= OzConstants.F4_BALANCE_POINTS_REQ) {
                        clearFloor(instance, attacker, "Everything is stable now. You can continue to the next floor.");

                    } else if (Util.succeedProp(5)) {
                        var curSide = isRightSide ? "right" : "left";
                        var newSide = isRightSide ? "Left" : "Right";
                        instance.addProperty(OzConstants.F4_CUR_SIDE, newSide);
                        instance.broadcast(WvsContext.weatherEffectNotice(WeatherEffNoticeType.Oz_Alicia,
                                "The tower is losing its balance. Eliminate monsters on the " + curSide + ".", OzConstants.FLOOR_NOTICE_MS));
                    }
                }
                break;

            // Undersea Tower of Oz : Undersea 6F
            case 6:
                if (newHp <= 0) {
                    var killCount = (int) instance.getProperty(OzConstants.KILL_COUNT);
                    var added = templateId == OzConstants.KING_SLIME ? OzConstants.KING_SLIME_MULTIPLIER : 1;
                    killCount += added;
                    instance.addProperty(OzConstants.KILL_COUNT, killCount);

                    if (!OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                        var msg = String.format(templateId == OzConstants.KING_SLIME ? "King Slimes Eliminated: %d/%d" : "Slimes: %d/%d",
                                killCount, OzConstants.F6_KILL_COUNT);
                        instance.broadcast(UserPacket.scriptProgressMessage(msg));
                    } else {
                        break;
                    }

                    if (killCount >= OzConstants.F6_KILL_COUNT) {
                        OzTowerModule.clearStage(instance, attacker);
                        break;
                    }

                    // Spawn King Slime at small % chance
                    if (templateId == OzConstants.SLIME && Util.succeedProp(OzConstants.KING_SLIME_SPAWN_PROP)) {
                        field.spawnMob(OzConstants.KING_SLIME, mob.getX(), mob.getY(), false, OzConstants.KING_SLIME_HP);
                        instance.broadcast(WvsContext.weatherEffectNotice(WeatherEffNoticeType.Oz_Alicia,
                                "King Slime has appeared somewhere on the map! Defeat it before it disappears!", OzConstants.EVENT_NOTICE_MS));
                    }
                }
                break;

            // Undersea Tower of Oz : Undersea 8F
            case 8:
                if (newHp <= 0) {
                    var killCount = (int) instance.getProperty(OzConstants.KILL_COUNT);
                    killCount++;
                    instance.addProperty(OzConstants.KILL_COUNT, killCount);

                    if (!OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                        var msg = String.format("Kills: %d/%d", killCount, OzConstants.F8_KILL_COUNT);
                        instance.broadcast(UserPacket.scriptProgressMessage(msg));
                    }

                    if (killCount >= OzConstants.F8_KILL_COUNT) {
                        OzTowerModule.clearStage(instance, attacker);
                        break;
                    }
                }
                break;

            // Undersea Tower of Oz : Undersea 10F
            case 10:
                if (newHp <= 0 && templateId == OzConstants.GIANT_SPIDER_TEMPLATE_ID) { // Killed Boss
                    OzTowerModule.clearStage(instance, attacker);
                }
                break;

            // Undersea Tower of Oz : Undersea 11F
            case 11:
                if (newHp <= 0) {
                    var killCount = (int) instance.getProperty(OzConstants.KILL_COUNT);
                    killCount++;
                    instance.addProperty(OzConstants.KILL_COUNT, killCount);

                    if (!OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                        var msg = String.format("Kills: %d/%d", killCount, OzConstants.F11_KILL_COUNT);
                        instance.broadcast(UserPacket.scriptProgressMessage(msg));
                    }

                    if (killCount >= OzConstants.F11_KILL_COUNT) {
                        OzTowerModule.clearStage(instance, attacker);
                        break;
                    }
                }
                break;

            // Undersea Tower of Oz : Undersea 13F
            case 13:
                if (newHp <= 0) {
                    var killCount = (int) instance.getProperty(OzConstants.KILL_COUNT);
                    killCount++;
                    instance.addProperty(OzConstants.KILL_COUNT, killCount);

                    if (!OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                        var msg = String.format("Kills: %d/%d", killCount, OzConstants.F13_KILL_COUNT);
                        instance.broadcast(UserPacket.scriptProgressMessage(msg));
                    }

                    if (killCount >= OzConstants.F13_KILL_COUNT) {
                        OzTowerModule.clearStage(instance, attacker);

                        // Upon clearing this stage with meteors. Stop the meteor rain
                        attacker.getScriptManager().stopFieldEvents();
                        break;
                    }
                }
                break;

            // Undersea Tower of Oz : Undersea 16F
            case 16:
                if (newHp <= 0) {
                    var killCount = (int) instance.getProperty(OzConstants.KILL_COUNT);
                    killCount++;
                    instance.addProperty(OzConstants.KILL_COUNT, killCount);

                    if (!OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                        var msg = String.format("Kills: %d/%d", killCount, OzConstants.F16_KILL_COUNT);
                        instance.broadcast(UserPacket.scriptProgressMessage(msg));
                    }

                    if (killCount >= OzConstants.F16_KILL_COUNT) {
                        OzTowerModule.clearStage(instance, attacker);
                        break;
                    }
                }
                break;

            // Undersea Tower of Oz : Undersea 20F
            case 20:
                if (newHp <= 0 && templateId == OzConstants.TIN_WOODMAN) { // Killed Boss
                    OzTowerModule.clearStage(instance, attacker);
                }
                break;

            // Undersea Tower of Oz : Undersea 21F
            case 21:
                if (newHp <= 0) {
                    if (templateId == OzConstants.ELIZA) {
                        OzTowerModule.clearStage(instance, attacker);
                    } else {
                        // Killed Small Foxes
                        if (!instance.hasProperty("F21_Eliza") && Util.succeedProp(OzConstants.ELIZA_SPAWN_PROP)) {
                            var fh = field.getRandomWalkableFoothold();
                            var pos = fh.getRandomPositionFromEdges(50);

                            field.spawnMob(OzConstants.ELIZA, pos.getX(), pos.getY(), false, OzConstants.ELIZA_HP);
                            instance.chatMessage("Eliza has spawned!");
                            instance.addProperty("F21_Eliza", true);
                        }
                    }
                }
                break;

            // Undersea Tower of Oz : Undersea 26F
            case 26:
                if (newHp <= 0) {
                    var drop = new Drop(-1, ItemData.getItemDeepCopy(OzConstants.ORANGE_SLIME_LIQUID));
                    field.drop(drop, mob.getPosition(), mob.getPosition(), true);

                    var killCount = (int) instance.getProperty(OzConstants.KILL_COUNT);
                    killCount++;
                    instance.addProperty(OzConstants.KILL_COUNT, killCount);

                    if (!OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                        var msg = String.format("Kills: %d/%d", killCount, OzConstants.F26_KILL_COUNT);
                        instance.broadcast(UserPacket.scriptProgressMessage(msg));
                    }

                    if (killCount >= OzConstants.F26_KILL_COUNT) {
                        OzTowerModule.clearStage(instance, attacker);
                        break;
                    }
                }
                break;

            // Undersea Tower of Oz : Undersea 28F
            case 28:
                if (newHp <= 0) {
                    var isBellamoa = templateId == OzConstants.BELLAMOA;
                    var killCountBellamoa = (int) instance.getProperty(OzConstants.F28_KILL_COUNT_BELLAMOA);
                    var killCountPlead = (int) instance.getProperty(OzConstants.F28_KILL_COUNT_PLEAD);
                    if (isBellamoa) {
                        killCountBellamoa++;
                    } else {
                        killCountPlead++;
                    }
                    instance.addProperty(OzConstants.F28_KILL_COUNT_BELLAMOA, killCountBellamoa);
                    instance.addProperty(OzConstants.F28_KILL_COUNT_PLEAD, killCountPlead);

                    if (!OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                        var msg = String.format("%s killed: %d/%d", isBellamoa ? "Bellamoa" : "Plead", isBellamoa ? killCountBellamoa : killCountPlead, OzConstants.F28_KILL_COUNT);
                        instance.broadcast(UserPacket.scriptProgressMessage(msg));
                    }

                    if (killCountBellamoa >= OzConstants.F28_KILL_COUNT && killCountPlead >= OzConstants.F28_KILL_COUNT) {
                        OzTowerModule.clearStage(instance, attacker);
                        break;
                    }
                }
                break;

            // Undersea Tower of Oz : Undersea 29F
            case 29:
                if (newHp <= 0) {
                    var drop = new Drop(-1, ItemData.getItemDeepCopy(OzConstants.RED_SAND_POWDER));
                    field.drop(drop, mob.getPosition(), mob.getPosition(), true);
                }
                break;

            // Undersea Tower of Oz : Undersea 30F
            case 30:
                if (newHp <= 0 && templateId == OzConstants.COWARDLY_LION) { // Killed Boss
                    OzTowerModule.clearStage(instance, attacker);
                }
                break;

            // Undersea Tower of Oz : Undersea 31F
            case 31:
                if (newHp <= 0 && (templateId == OzConstants.BLUE_INSECTIVORE_SLIME || templateId == OzConstants.YELLOW_INSECTIVORE_SLIME)) {
                    var killCount = (int) instance.getProperty(OzConstants.KILL_COUNT);
                    killCount++;
                    instance.addProperty(OzConstants.KILL_COUNT, killCount);

                    if (!OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                        var msg = String.format("Kills: %d/%d", killCount, OzConstants.F31_KILL_COUNT);
                        instance.broadcast(UserPacket.scriptProgressMessage(msg));
                    }

                    if (killCount >= OzConstants.F31_KILL_COUNT) {
                        OzTowerModule.clearStage(instance, attacker);
                        break;
                    }

                    if (Util.succeedProp(OzConstants.FAIRY_SPAWN_PROP)) {
                        for (var pos : OzConstants.FAIRY_SPAWN_POS_LIST) {
                            field.spawnMob(OzConstants.FAIRY, pos.getX(), pos.getY(), false, OzConstants.FAIRY_HP);
                        }
                    }
                }
                break;

            // Undersea Tower of Oz : Undersea 34F
            case 34:
                if (newHp <= 0) {
                    var itemId = templateId == OzConstants.PURPLE_CURLY_COW ? OzConstants.PURPLE_LEATHER : OzConstants.YELLOW_LEATHER;
                    var drop = new Drop(-1, ItemData.getItemDeepCopy(itemId));
                    field.drop(drop, mob.getPosition(), mob.getPosition(), true);
                }
                break;

            // Undersea Tower of Oz : Undersea 37F
            case 37:
                if (newHp <= 0) {

                    if (OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                        break;
                    }

                    HashMap<Integer, Tuple<String, String>> dict = new HashMap<>() {{
                        put(OzConstants.MEERKAT, new Tuple<>("Meerkat", OzConstants.F37_MEERKAT_KILLS));
                        put(OzConstants.KIYO, new Tuple<>("Kiyo", OzConstants.F37_KIYO_KILLS));
                        put(OzConstants.SAND_RAT, new Tuple<>("Sand Rat", OzConstants.F37_SAND_RAT_KILLS));
                        put(OzConstants.SCORPION, new Tuple<>("Scorpion", OzConstants.F37_SCORPION_KILLS));
                    }};

                    for (var entry : dict.entrySet()) {

                        var mobId = entry.getKey();
                        if (templateId == mobId) {
                            var prop = entry.getValue().getRight();
                            var mobName = entry.getValue().getLeft();

                            if (instance.hasProperty(mobName)) { // Signify this mob's kill req has been met
                                continue;
                            }

                            var killCount = (int) instance.getProperty(prop);
                            killCount++;
                            instance.addProperty(prop, killCount);

                            var msg = String.format("%s killed: %d/%d", mobName, killCount, OzConstants.F37_KILL_COUNT);
                            instance.broadcast(UserPacket.scriptProgressMessage(msg));

                            if (killCount >= OzConstants.F37_KILL_COUNT) {
                                instance.addProperty(mobName, true);
                            }
                        }
                    }

                    if (instance.hasProperty("Meerkat") && instance.hasProperty("Kiyo")
                            && instance.hasProperty("Sand Rat") && instance.hasProperty("Scorpion")) {
                        OzTowerModule.clearStage(instance, attacker);
                    }

                    field.spawnMob(mob.getTemplateId(), field.getRandomWalkableFoothold().getRandomPosition());
                }
                break;

            // Undersea Tower of Oz : Undersea 38F
            case 38:
                if (newHp <= 0) {
                    var currentMob = instance.getProperty(OzConstants.F38_CUR_MOB);
                    if (currentMob == null || OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                        break;
                    }

                    if (templateId == (int) currentMob) {
                        var curDarkness = (int) instance.getProperty(OzConstants.F38_CUR_DARKNESS);
                        curDarkness -= OzConstants.DARKNESS_DEC_PER_MOB;
                        instance.addProperty(OzConstants.F38_CUR_DARKNESS, curDarkness);

                        instance.broadcast(WvsContext.weatherEffectNotice(WeatherEffNoticeType.FatWarriorwithBeard, String.format("Dark Energy: %d", curDarkness), 2000));

                        if (curDarkness <= 0) {
                            OzTowerModule.clearStage(instance, attacker);
                            break;
                        }

                    } else {
                        instance.broadcast(WvsContext.weatherEffectNotice(WeatherEffNoticeType.FatWarriorwithBeard, "Eliminating the wrong monsters won't clear out the Dark Energy.", 1000));
                    }

                    if (Util.succeedProp(OzConstants.F38_SWITCH_MOB_PROP)) {
                        instance.addProperty(OzConstants.F38_CUR_MOB, OzTowerModule.selectRandomF38Mob(instance));
                    }
                }
                break;

            // Undersea Tower of Oz : Undersea 40F
            case 40:
                if (newHp <= 0 && templateId == OzConstants.SCARECROW) {
                    OzTowerModule.clearStage(instance, attacker);
                }
                break;

            // Undersea Tower of Oz : Undersea 42F
            case 42:
                if (newHp <= 0) {
                    if (OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                        break;
                    }

                    HashMap<Integer, Tuple<String, String>> dict = new HashMap<>() {{
                        put(OzConstants.BEETLE, new Tuple<>("Beetle", OzConstants.F42_BEETLE_KILLS));
                        put(OzConstants.DUAL_BEETLE, new Tuple<>("Dual Beetle", OzConstants.F42_DUAL_BEETLE_KILLS));
                        put(OzConstants.HARP, new Tuple<>("Harp", OzConstants.F42_HARP_KILLS));
                        put(OzConstants.BLOOD_HARP, new Tuple<>("Blood Harp", OzConstants.F42_BLOOD_HARP_KILLS));
                    }};

                    for (var entry : dict.entrySet()) {

                        var mobId = entry.getKey();
                        if (templateId == mobId) {
                            var prop = entry.getValue().getRight();
                            var mobName = entry.getValue().getLeft();

                            if (instance.hasProperty(mobName)) { // Signify this mob's kill req has been met
                                continue;
                            }

                            var killCount = (int) instance.getProperty(prop);
                            killCount++;
                            instance.addProperty(prop, killCount);

                            var msg = String.format("%s killed: %d/%d", mobName, killCount, OzConstants.F42_KILL_COUNT);
                            instance.broadcast(UserPacket.scriptProgressMessage(msg));

                            if (killCount >= OzConstants.F42_KILL_COUNT) {
                                instance.addProperty(mobName, true);
                            }
                        }
                    }

                    if (mob.getTemplateId() == OzConstants.HARP || mob.getTemplateId() == OzConstants.BLOOD_HARP) {
                        field.spawnMob(mob.getTemplateId(), Util.getRandomFromCollection(field.getNonWallFootholdsWithinRect(new Rect(1081, -628, 2510, -35))).getRandomPosition());
                    } else if (mob.getTemplateId() == OzConstants.DUAL_BEETLE) {
                        field.spawnMob(mob.getTemplateId(), Util.getRandomFromCollection(field.getNonWallFootholdsWithinRect(new Rect(-171, -388, 338, -88))).getRandomPosition());
                    } else {
                        field.spawnMob(mob.getTemplateId(), Util.getRandomFromCollection(field.getNonWallFootholdsWithinRect(new Rect(-768, 450, 2041, 452))).getRandomPosition());

                    }

                    if (instance.hasProperty("Beetle") && instance.hasProperty("Dual Beetle")
                            && instance.hasProperty("Harp") && instance.hasProperty("Blood Harp")) {
                        OzTowerModule.clearStage(instance, attacker);
                    }
                }
                break;

            // Undersea Tower of Oz : Undersea 46F
            case 46:
                if (newHp <= 0) {
                    var killCount = (int) instance.getProperty(OzConstants.KILL_COUNT);
                    killCount++;
                    instance.addProperty(OzConstants.KILL_COUNT, killCount);

                    if (!OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                        var msg = String.format("Kills: %d/%d", killCount, OzConstants.F46_KILL_COUNT);
                        instance.broadcast(UserPacket.scriptProgressMessage(msg));
                    }

                    if (killCount >= OzConstants.F46_KILL_COUNT) {
                        OzTowerModule.clearStage(instance, attacker);
                        break;
                    }
                }
                break;

            // Undersea Tower of Oz : Undersea 50F
            case 50:
                if (newHp <= 0 && templateId == OzConstants.DOROTHY) {
                    
                    // Upon clearing this stage with meteors. Stop the meteor rain
                    attacker.getScriptManager().stopFieldEvents();
                }
        }
    }

    /** 14F FPS room: the last kill clears the floor and moves everyone up */
    private void onFpsMobDamaged(Char attacker, Field field, Instance instance, long newHp) {
        if (newHp <= 0 && field.getMobs().size() <= 1) { // the dying mob is still counted
            OzTowerModule.clearStage(instance, attacker);
            int next = OzTowerModule.getFloorByFieldId(OzConstants.F14_FPS_FIELD) + 1;
            addTimer(EventManager.addEvent(() -> {
                instance.warp(OzTowerModule.getFieldByFloor(next));
                OzTowerModule.clearKillAndDamageInfo(instance);
                instance.addProperty(OzConstants.CURRENT_FLOOR, next);
                instance.broadcast(UserLocal.clearGun());
            }, 4, TimeUnit.SECONDS));
        }
    }

    /** clears the floor with Alicia's line for it */
    private static void clearFloor(Instance instance, Char chr, String text) {
        instance.broadcast(WvsContext.weatherEffectNotice(WeatherEffNoticeType.Oz_Alicia, text, OzConstants.FLOOR_NOTICE_MS));
        OzTowerModule.clearStage(instance, chr);
    }

    @Override
    public void onItemLooted(Char chr, Item item, int quantity) {
        var field = chr.getField();
        var itemId = item.getItemId();
        var instance = chr.getInstance();

        switch (itemId) {
            // Undersea Tower of Oz : Undersea 3F
            case OzConstants.F3_ANCIENT_BLUE_TURTLE_EGG:
            case OzConstants.F3_ANCIENT_ORANGE_TURTLE_EGG:
                if (OzTowerModule.hasClearedThisFloor(instance, chr.getFieldID())) {
                    break;
                }

                var curEggs = (int) instance.getProperty(OzConstants.F3_EGG_PROP);
                instance.addProperty(OzConstants.F3_EGG_PROP, curEggs + quantity);

                var msg = String.format("Ancient Turtle Eggs: %d/%d", curEggs + quantity, OzConstants.F3_EGG);
                instance.broadcast(UserPacket.scriptProgressMessage(msg));

                if (curEggs + quantity >= OzConstants.F3_EGG) {
                    clearFloor(instance, chr, String.format("You've collected %d Ancient Turtle Eggs. Time for the next floor.", OzConstants.F3_EGG));
                }
                break;

            // Undersea Tower of Oz : Undersea 29F
            case OzConstants.RED_SAND_POWDER:
                if (OzTowerModule.hasClearedThisFloor(instance, chr.getFieldID())) {
                    break;
                }

                var curPowder = (int) instance.getProperty(OzConstants.ITEMS_LOOTED);
                curPowder += quantity;

                if (curPowder >= 10) {
                    // Drop 1 Red Sand Crystal
                    var drop = new Drop(-1, ItemData.getItemDeepCopy(OzConstants.RED_SAND_CRYSTAL));
                    var pos = new Position(-530, -505);
                    field.drop(drop, pos, pos, true);

                    msg = "You have collected 10 Red Sand Power. A Red Sand Crystal has been created!";
                    instance.broadcast(UserPacket.scriptProgressMessage(msg));

                    curPowder = curPowder % 10;
                }

                instance.addProperty(OzConstants.ITEMS_LOOTED, curPowder);
                break;

            case OzConstants.PURPLE_LEATHER:
                if (OzTowerModule.hasClearedThisFloor(instance, chr.getFieldID())) {
                    return;
                }

                var curLeather = (int) instance.getProperty(OzConstants.ITEMS_LOOTED);
                curLeather += quantity;

                msg = String.format("Collected %d/%d Purple Leather.", curLeather, OzConstants.F34_ITEMS_LOOTED);
                instance.broadcast(UserPacket.scriptProgressMessage(msg));

                instance.addProperty(OzConstants.ITEMS_LOOTED, curLeather);

                if (curLeather >= OzConstants.F34_ITEMS_LOOTED) {
                    OzTowerModule.clearStage(instance, chr);
                }
                break;
        }
    }

    @Override
    public void onItemDropped(Char chr, Item item, int quantity, Position position, Drop drop) {
        var field = chr.getField();
        var fieldId = field.getId();
        var itemId = item.getItemId();
        var instance = chr.getInstance();

        switch (itemId) {
            case OzConstants.RED_SAND_CRYSTAL:
                if (OzTowerModule.getFloorOfMainField(fieldId) == 29) {
                    if (OzConstants.F29_DROP_RECT.hasPositionInside(position)) {
                        var req = (int) instance.getProperty(OzConstants.F29_CRYSTAL_REQ);
                        if (quantity >= req) {
                            addTimer(EventManager.addEvent(() -> {
                                field.removeLife(drop);

                                OzTowerModule.clearStage(instance, chr);
                            }, 2, TimeUnit.SECONDS));
                        } else {
                            var msg = String.format("You need to drop %d Red Sand Crystals!", req);
                            instance.broadcast(UserPacket.scriptProgressMessage(msg));
                        }
                    }
                }
                break;

            case OzConstants.COUNTERCLOCKWISE_LABYRINTH_KEY:
            case OzConstants.CLOCKWISE_LABYRINTH_KEY:
                if (OzTowerModule.getFloorOfMainField(fieldId) == 33) {
                    addTimer(EventManager.addEvent(() -> {
                        var reactor = field.getReactorsInRect(position.getRectAround(OzConstants.F33_RECT)).get(0);
                        var isClockwise = itemId == OzConstants.CLOCKWISE_LABYRINTH_KEY;
                        var curState = reactor.getState();
                        var newState =
                                isClockwise
                                        ? curState == 3 ? 0 : curState + 1
                                        : curState == 0 ? 3 : curState - 1;
                        reactor.setState((byte) newState);
                        instance.broadcast(ReactorPool.reactorChangeState(reactor, (short) 0, (byte) 0));
                        field.removeLife(drop);
                    }, 2, TimeUnit.SECONDS));

                }
                break;
        }
    }

    @Override
    public void onMobFixedMoveDirUpdate(Mob mob) {
        var fieldId = mob.getField().getId();
        var instance = mob.getField().getInstance();

        switch (OzTowerModule.getFloorOfMainField(fieldId)) {
            // Undersea Tower of Oz : Undersea 13F
            case 13:

                if (OzTowerModule.hasClearedThisFloor(instance, fieldId)) {
                    return;
                }

                var curRockHits = (int) instance.getProperty(OzConstants.F13_ROCK_HITS);
                curRockHits++;
                instance.addProperty(OzConstants.F13_ROCK_HITS, curRockHits);

                if (curRockHits >= OzConstants.F13_MAX_ROCK_HITS) {
                    // If Rock hits is 5, clears Oz and warps everyone out.
                    instance.clear();
                }
                break;
        }
    }

    @Override
    public void onMobSkill(Mob mob, MobSkillInfo msi, MobSkillID msId) {
        var templateId = mob.getTemplateId();
        var skillId = msi.getSkillId();
        var slv = msi.getLevel();
        Rect rect = null;
        if (msi.getLt() != null) {
            rect = mob.getRectAround(new Rect(msi.getLt(), msi.getRb()));
            if (mob.isFlip()) {
                rect.horizontalFlipAround(mob.getPosition().getX());
            }
        }

        switch (templateId) {
            case OzConstants.FAIRY:
                if (msId.equals(MobSkillID.Poison) && slv == 17 && rect != null) {
                    rect = mob.getRectAround(new Rect(-400, -200, 400, 300));
                    var mobs = new ArrayList<>(mob.getField().getMobsInRect(rect));
                    Option o = new Option();
                    o.nOption = 1;
                    o.rOption = MobSkillID.Invincible.getVal(); // TODO -> Get Shield that causes 'MISS'
                    o.slv = 4;
                    o.tOption = 10; // 10 seconds

                    for (var affected : mobs) {
                        if (affected.getTemplateId() == OzConstants.FAIRY) {
                            continue;
                        }

                        affected.getTemporaryStat().addMobSkillOptionsAndBroadCast(MobStat.Invincible, o);
                    }

                }
                break;
        }
    }

    @Override
    public void onPortalTeleportRequest(Char chr, Portal portal) {
        var field = chr.getField();
        var fieldId = field.getId();
        var instance = chr.getInstance();

        switch (OzTowerModule.getFloorOfMainField(fieldId)) {
            case 4: // 4F
                var teleportPos = Util.randBoolean() ? new Position(1418, -595) : new Position(-415, -598);
                chr.write(FieldPacket.teleport(teleportPos, chr));
                chr.write(WvsContext.exclRequest2()); // clears info for if user falls in portal next time. a sort of ack that the user is out of the teleporting portal
                break;
            case 22: // 22F
                chr.write(FieldPacket.teleport(new Position(-1580, 41), chr));
                chr.write(WvsContext.exclRequest2()); // clears info for if user falls in portal next time. a sort of ack that the user is out of the teleporting portal
                break;
        }
    }

    @Override
    public void onInstanceCleared(Instance instance) {
        // If Oz, save important information and put it in QR value on each character
        for (var chrId : new HashSet<>(instance.getCharIds())) {
            OzTowerModule.saveOzInfoOntoQR(instance, chrId);
        }
        OzTowerModule.saveOzRanking(instance);
    }

    @Override
    protected List<Integer> getPartyQuestItems() {
        return new ArrayList<>();
    }
}
