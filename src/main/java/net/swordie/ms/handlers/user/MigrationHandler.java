package net.swordie.ms.handlers.user;

import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.Server;
import net.swordie.ms.client.Account;
import net.swordie.ms.client.CharacterSwitch;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.HyperTPRock;
import net.swordie.ms.client.character.modules.LinkSkillsModule;
import net.swordie.ms.client.character.skills.TownPortal;
import net.swordie.ms.client.friend.result.FriendResult;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.client.party.PartyMember;
import net.swordie.ms.client.surprisemission.SurpriseMissionModule;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.*;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.*;
import net.swordie.ms.enums.*;
import net.swordie.ms.events.Events;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.life.npc.Npc;
import net.swordie.ms.loaders.NpcData;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.world.TransferInfo;
import net.swordie.ms.enums.FieldOption;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.enums.AccountType;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.shop.cashshop.CashShop;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static net.swordie.ms.enums.ChatType.Notice2;

public class MigrationHandler {

    private static final Logger log = LogManager.getLogger();

    private static final Lock transferLock = new ReentrantLock();
    private static final long STATE_WAIT_TIMEOUT = 120000; // 2 minutes

    @Handler(op = InHeader.MIGRATE_IN)
    public static void handleMigrateIn(Client c, InPacket inPacket) {
        int worldId = inPacket.decodeInt();
        inPacket.decodeInt(); // user id
        int charId = inPacket.decodeInt();
        byte[] machineID = inPacket.decodeArr(16);
        // the rest (66 bytes in v265 and v271 alike: 1 1 4 4 4 4, the 32-byte machine vector, 1 1 1, B8) is not read

        boolean stop = net.swordie.ms.ServerSettings.getBoolean("test.stopAtMigrateIn", false);
        if (stop || net.swordie.ms.ServerSettings.getBoolean("test.fieldGate", false)) {
            var info = Server.getInstance().getTransferInfo(charId, worldId);
            Client old = info == null ? null : info.getClient();
            byte[] known = old == null ? null : old.getMachineID();
            log.info(String.format("[Test] MIGRATE_IN at %d: world %d, char %d, %d bytes after the machine id; "
                            + "transfer %s, machine id %s (login knew %s), ip %s.%s",
                    System.currentTimeMillis() % 100000, worldId, charId, inPacket.getUnreadAmount(),
                    info == null ? "missing" : "found",
                    old == null ? "-" : (old.hasCorrectMachineID(machineID) ? "matches" : "differs"),
                    known == null ? "nothing" : known.length + " bytes",
                    old == null ? "-" : (old.getIP().equals(c.getIP()) ? "matches" : "differs"),
                    stop ? " Stopping here (test.stopAtMigrateIn): no SET_FIELD." : ""));
            if (stop) {
                return;
            }
            // v271 field test: from here on only the allowed Out packets go out and no In packet is handled
            c.setFieldTestGate(net.swordie.ms.ServerSettings.getBoolean("test.fieldGate", false));
        }

        int channelId;
        Client oldClient;
        User user;
        synchronized (transferLock) {
            var info = Server.getInstance().getTransferInfo(charId, worldId);
            if (info == null) {
                // someone connecting without us expecting them to
                log.error(String.format("[CharId: %d] Logged in without having a transfer.", charId));
                c.close();
                return;
            }

            synchronized (info.getState()) {
                try {
                    while (info.getState().getState() != TransferInfo.State.Removed) {
                        info.getState().wait(STATE_WAIT_TIMEOUT); // 2 locks is intended
                        if (info.getState().getState() != TransferInfo.State.Removed) {
                            // We timed out
                            log.error(String.format("[CharId: %d] Timed out while waiting for state change.", charId));
                            c.close();
                            return;
                        }
                    }
                } catch (InterruptedException e) {
                    log.error("MigrationHandler.handleMigrateIn", e);
                }
            }

            channelId = info.getNewChannel().getChannelId();
            oldClient = info.getClient();
            if (!oldClient.hasCorrectMachineID(machineID) || !oldClient.getIP().equals(c.getIP())) {
                log.error(String.format("[CharId: %d] Logged in without having the correct ip or hwid.", charId));
                c.write(WvsContext.returnToTitle());
                return;
            }
            user = oldClient.getUser();

            if (user.getBanExpireDate() != null && !user.getBanExpireDate().isExpired())
            {
                log.error(String.format("[CharId: %d] Logged in while being banned.", charId));
                c.write(WvsContext.returnToTitle());
                c.close();
                return;
            }

            // Only remove transfer after we added the user, so there's no 'gap'
            Server.getInstance().addUser(user);
            user.setInGame(true);
            Server.getInstance().getWorldById(worldId).getChannelById(channelId).removeClientFromTransfer(charId);
            info.setState(TransferInfo.State.Complete);
        }

        c.setMachineID(machineID);
        c.setOldChannel(oldClient.getOldChannel());
        c.setUser(user);
        Account acc = oldClient.getAccount();
        c.setAccount(acc);
        c.setChannel(channelId);
        c.setWorldId((byte) worldId);
        c.setChannelInstance(Server.getInstance().getWorldById(worldId).getChannelById(channelId));
        Char chr = oldClient.getChr();
        if (chr == null || chr.getId() != charId) {
            chr = acc.getCharById(charId);
        }
        chr.setChangingChannel(false);
        user.setCurrentChr(chr);
        user.setCurrentAcc(acc);
        chr.setAccount(acc);
        chr.setUser(user);
        chr.setClient(c);
        acc.setCurrentChr(chr);
        chr.initBeforeWarp();
        c.setChr(chr);
        c.getChannelInstance().addChar(chr);
        chr.setInstance(null);

        Field field = chr.getOrCreateFieldByCurrentInstanceType(chr.getFieldID() <= 0 ? FieldConstants.HENESYS_FIELD_ID : chr.getFieldID());

        if (Server.OPCODE_ENCRYPTION) {
            c.sendOpcodeEncryption();
        }


        if (chr.getHP() <= 0) { // automatically revive when relogging
            chr.heal(chr.getMaxHP() / 2, false, true);
        }
        if (chr.getPartyID() != 0) {
            Party party = c.getWorld().getPartybyId(chr.getPartyID());
            if (party == null || !party.hasPartyMember(chr.getId())) {
                chr.setPartyID(0);
            } else {
                chr.setParty(party);
            }
        }

        // blessing has to be split up, as adding skills before SetField is send will crash the client
        chr.initBlessingSkillNames();
        // characters saved with the look of an older class get it matched to their job
        if (chr.getAvatarData().getAvatarLook() != null
                && chr.getAvatarData().getAvatarLook().getJob() != chr.getJob()) {
            chr.getAvatarData().getAvatarLook().setJob(chr.getJob());
        }
        // v265 avatar looks carry the name (UserPool, chairs, mini rooms)
        if (chr.getAvatarData().getAvatarLook() != null) {
            chr.getAvatarData().getAvatarLook().setName(chr.getName());
        }
        if (chr.getAvatarData().getZeroAvatarLook() != null) {
            chr.getAvatarData().getZeroAvatarLook().setName(chr.getName());
        }

        // GMS sends 01 on every channel entry before SET_FIELD; the client answers In 1966
        var enterFlag = new net.swordie.ms.connection.OutPacket(net.swordie.ms.handlers.header.OutHeader.CHANNEL_ENTER_FLAG);
        enterFlag.encodeByte(1);
        c.write(enterFlag);
        chr.warp(field, true);
        chr.write(UserLocal.setInGameDirectionMode(false, false, false)); // release any direction lock

        if (chr.getSurpriseMission() != null) {
            SurpriseMissionModule.showSurpriseMissionToClient(chr, chr.getSurpriseMission());
        }

        chr.setOnline(true); // v195+: respect 'invisible login' setting

        // the server values (v232's UI_PERMISSIONS) now go out with the channel hello
        chr.initBlessingSkills();
        chr.initNobleMind(); // Cygnus Knights: Noble Mind at 0/6 for "The Mind of a Knight"
        // the client keeps 5 event name tags (King of Crowns titles) that start at 0, the first title of each group (the
        // tag array is made with 5 zeroes; the EVENT_NAME_TAG handler reads 5 x (string, signed byte)); this packet's -1s
        // clear them.
        c.write(WvsContext.updateEventNameTag(new int[]{}));
        if (chr.getGuild() != null) {
            chr.setGuild(chr.getClient().getWorld().getGuildByID(chr.getGuild().getId()));
        }

        // v265 carries the quick slots in this packet too
        c.write(FuncKeyMappedMan.funcKeyMappedManInit(chr.getFuncKeyMap(), chr.getQuickslotKeys()));
        if (net.swordie.ms.ServerSettings.getBoolean("debug.loginExtras", false)) {
            sendReferenceLoginPackets(chr);
        }
        chr.initPetPotions();
        if (chr.getFamiliarCodexManager() != null) {
            chr.getFamiliarCodexManager().initQuestsForSlots();
            // the Summon Gauge is always full
            chr.getFamiliarCodexManager().setSummonGauge(GameConstants.MAX_SUMMON_GAUGE);
            // familiars collected before they came at their level cap catch up
            for (var familiar : chr.getFamiliarCodexManager().getFamiliars()) {
                familiar.levelToCap();
            }
            try {
                chr.getFamiliarCodexManager().onLogin();
            } catch (Exception e) {
                log.error("Familiar codex login update failed for " + chr.getName(), e);
            }
        }

        chr.setBulletIDForAttack(chr.calculateBulletIDForAttack());

        if (!chr.isInCashShop()) {
            c.write(WvsContext.friendResult(FriendResult.loadFriends(net.swordie.ms.handlers.social.FriendHandler.v271List(chr))));
            c.write(WvsContext.macroSysDataInit(chr.getMacros()));
            // damage skins travel in SET_FIELD in v265; the reference server sends no save result at login
        }

        acc.getMonsterCollection().init(chr);
        chr.getOffenseManager().setChr(chr);
        chr.getJobHandler().giveHyperAfBuff();
        chr.doHackFixes();
        chr.getUnionRaid().updateTotalDamageDone(chr, true);
        c.requestResolution();

        if (chr.getQuestManager().getQuestById(QuestConstants.POWER_CRYSTAL_QUEST) == null) {
            chr.getScriptManager().startQuestNoCheck(QuestConstants.POWER_CRYSTAL_QUEST);
        }
        chr.getQuestManager().getQuestById(QuestConstants.POWER_CRYSTAL_QUEST).setQrValue("count=" + chr.getAccount().getRemainingEntries(DailyEntry.PowerCrystal) +";max=" + QuestConstants.POWER_CRYSTAL_WEEKLY_COUNT +";type=2");
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(chr.getQuestManager().getQuestById(QuestConstants.POWER_CRYSTAL_QUEST))));


        if (!chr.isInit()) {
            LinkSkillsModule.initLinkSkills(acc, chr);

            chr.login();

            chr.setInit(true);
        }
    }

    /**
     * How far (in pixels) the server's last known position of a character may be from a portal it enters. Generous,
     * since the position is only as recent as the last movement packet.
     */
    private static final int PORTAL_MAX_DISTANCE = 800;

    /** Story fights where dying is a retry inside the story, not an exit (the map's return map isn't in the instance). */
    private static final java.util.Set<Integer> STORY_RETRY_MAPS = java.util.Set.of(927000020);

    /**
     * Where a character who died in a story instance with no exit revives inside it: the map's return map, when the
     * map itself has no forced return, the return map is another map, and it belongs to the instance (or the map is a
     * known story fight). Null: the usual death handling (leave the instance).
     */
    private static Field storyRetryField(Char chr, net.swordie.ms.loaders.containerclasses.FieldInfo fieldInfo) {
        var inst = chr.getInstance();
        int ret = fieldInfo.getReturnMap();
        if (inst == null || inst.getForcedReturn() < GameConstants.NO_MAP_ID || fieldInfo.getForcedReturn() < GameConstants.NO_MAP_ID
                || ret <= 0 || ret >= GameConstants.NO_MAP_ID || ret == chr.getFieldID()
                || !(inst.getFields().containsKey(ret) || STORY_RETRY_MAPS.contains(chr.getFieldID()))) {
            return null;
        }
        return chr.getOrCreateFieldByCurrentInstanceType(ret);
    }


    /**
     * Small packets the reference v265 server sends on every login that this server has no system for yet, in the
     * reference's form (server.properties debug.loginExtras=true), with the reference's values.
     */
    private static void sendReferenceLoginPackets(Char chr) {
        var now = net.swordie.ms.util.ResetClock.now(); // the client's clock zone
        var hour = new net.swordie.ms.connection.OutPacket(net.swordie.ms.handlers.header.OutHeader.HOUR_CHANGED);
        hour.encodeShort(now.getDayOfWeek().getValue());
        hour.encodeShort(now.getHour());
        chr.write(hour);
        chr.write(new net.swordie.ms.connection.OutPacket(net.swordie.ms.handlers.header.OutHeader.SET_BUY_EQUIP_EXT));
        writeBytes(chr, net.swordie.ms.handlers.header.OutHeader.SET_POTION_DISCOUNT_RATE, 0);
        var combo = new net.swordie.ms.connection.OutPacket(net.swordie.ms.handlers.header.OutHeader.MOD_COMBO_RESPONSE);
        combo.encodeInt(0);
        chr.write(combo);
        writeBytes(chr, net.swordie.ms.handlers.header.OutHeader.SET_MONSTER_DEBUFF_MARK, 0);
        writeBytes(chr, net.swordie.ms.handlers.header.OutHeader.CHARACTER_POTENTIAL_CHANGE_PRESET, 1,
                chr.getPotentialMan().getActivePreset());
        chr.write(new net.swordie.ms.connection.OutPacket(net.swordie.ms.handlers.header.OutHeader.SET_QUEST_CLEAR));
        var taming = new net.swordie.ms.connection.OutPacket(net.swordie.ms.handlers.header.OutHeader.SET_TAMING_MOB_INFO);
        taming.encodeInt(chr.getId());
        taming.encodeInt(Math.max(1, chr.getTamingMobLevel()));
        taming.encodeInt(chr.getTamingMobExp());
        taming.encodeInt(chr.getTamingMobFatigue());
        taming.encodeByte(0);
        chr.write(taming);
        writeBytes(chr, net.swordie.ms.handlers.header.OutHeader.IS_UNIVERSE, 0);
        chr.write(new net.swordie.ms.connection.OutPacket(net.swordie.ms.handlers.header.OutHeader.UNK_372));
        writeBytes(chr, net.swordie.ms.handlers.header.OutHeader.CLAIM_SVR_STATUS_CHANGED, 0); // GMS v271 sends 0
        var session = new net.swordie.ms.connection.OutPacket(net.swordie.ms.handlers.header.OutHeader.SESSION_VALUE);
        session.encodeString("kill_count");
        session.encodeString("0");
        chr.write(session);
        writeBytes(chr, net.swordie.ms.handlers.header.OutHeader.ACHIEVEMENT_UPDATE, 0);
        writeBytes(chr, net.swordie.ms.handlers.header.OutHeader.SKILL_SEQUENCE_SKILLS, 0, 0, 0, 0);
        writeBytes(chr, net.swordie.ms.handlers.header.OutHeader.SKILL_SEQUENCE_BUFFS, 0, 0, 0, 0, 0, 0);
    }

    private static void writeBytes(Char chr, net.swordie.ms.handlers.header.OutHeader header, int... bytes) {
        var outPacket = new net.swordie.ms.connection.OutPacket(header);
        for (int b : bytes) {
            outPacket.encodeByte(b);
        }
        chr.write(outPacket);
    }

    @Handler(op = InHeader.USER_TRANSFER_FIELD_REQUEST)
    public static void handleUserTransferFieldRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (inPacket.getUnreadAmount() == 0) {
            // Coming back from the cash shop

            chr.getAvatarData().getCharacterStat().setPosMap(chr.getPreviousFieldID());
            chr.setInCashShop(false);
            chr.setChangingChannel(true);
            c.getChannelInstance().addClientInTransfer(c.getChannelInstance(), c.getChannelInstance(), chr.getId(), c);
            c.write(ClientSocket.migrateCommand(true, (short) c.getChannelInstance().getPort()));
            return;
        }

        inPacket.decodeByte(); // v265: a byte before the field key
        byte fieldKey = inPacket.decodeByte();
        int targetField = inPacket.decodeInt();
        String portalName = inPacket.decodeString();
        int x = inPacket.decodeShort();
        int y = inPacket.decodeShort();

        var field = chr.getField();
        var fieldInfo = field.getInfo();
        if (portalName != null && !"".equals(portalName)) {
            Portal portal = fieldInfo.getPortalByName(portalName);
            if (portal == null) {
                chr.chatMessage("This portal seems to lead to nowhere...");
                log.error(String.format("Could not find portal %s, fieldId %s", portalName, field.getId()));
                chr.dispose(); // the client waits for an answer to its request; without one the player is stuck
                return;
            }
            if (!portal.getEnabled()) {
                tellOnce(chr, "This way is closed right now.");
                chr.dispose();
                return;
            }
            // party quests: a stage's door stays shut until the stage is cleared (decided per instance)
            var portalInstance = chr.getInstance();
            if (portalInstance != null && portalInstance.getInstanceHelper() != null
                    && !portalInstance.getInstanceHelper().canUsePortal(chr, field, portal)) {
                tellOnce(chr, "This way won't open yet.");
                chr.dispose();
                return;
            }
            Position chrPos = chr.getPosition();
            if (chrPos != null && chrPos.distanceTo(new Position(portal.getX(), portal.getY())) > PORTAL_MAX_DISTANCE) {
                // nowhere near the portal (the client sends this when it touches one): a packet-made field change
                chr.getOffenseManager().addOffense(String.format("Character %d used portal %s of field %d from %s (portal at %d, %d).",
                        chr.getId(), portalName, field.getId(), chrPos, portal.getX(), portal.getY()));
                chr.dispose();
                return;
            }
            if (portal.getScript() != null && !portal.getScript().equals("")) {
                chr.getScriptManager().startScript(portal.getId(), portal.getScript(), ScriptType.Portal);
            } else {
                Field toField = chr.getOrCreateFieldByCurrentInstanceType(portal.getTargetMapId());
                if (toField == null) {
                    // some maps keep portals to maps that aren't in the game data at all (Sleepywood 105040300,
                    // Singapore 540000000, Kulan Field V 221040400, ...): nothing happens, but the player can move on
                    tellOnce(chr, "This way doesn't lead anywhere.");
                    chr.dispose();
                    return;
                }
                int lvLimit = toField.getInfo().getLvLimit();
                if (!chr.canWarpTo(toField) && chr.getUser().getAccountType() == AccountType.Player) {
                    // Map.wz info/lvLimit: the map beyond needs this level
                    chr.chatMessage(String.format("You must be at least level %d to enter that area.", lvLimit));
                    chr.dispose();
                    return;
                }
                Portal toPortal = toField.getInfo().getPortalByName(portal.getTargetPortalName());
                if (toPortal == null) {
                    toPortal = toField.getInfo().getPortalByName("sp");
                }
                // walking out of a solo story instance (a quest's or a tutorial's own copy of a map) into an ordinary
                // map: leave the instance, instead of wandering on through private copies of the world
                var soloInstance = chr.getInstance();
                int toForcedReturn = toField.getInfo().getForcedReturn();
                if (soloInstance != null && !soloInstance.isBossEncounter()
                        && net.swordie.ms.world.field.instance.instancehelper.InstanceHelperFactory.getHelperByInstance(soloInstance) == null
                        && soloInstance.getChars().size() <= 1
                        && (toForcedReturn == GameConstants.NO_MAP_ID || toForcedReturn == 0)) {
                    soloInstance.removeChar(chr, portal.getTargetMapId(), toPortal != null ? toPortal.getId() : 0,
                            true, true);
                    return;
                }
                chr.warp(toField, toPortal);
            }
        } else if (chr.getHP() <= 0) {
            // Character is dead, respawn request
            inPacket.decodeByte(); // always 0
            byte tarfield = inPacket.decodeByte(); // ?
            byte reviveType = inPacket.decodeByte();
            int returnMap = fieldInfo.getReturnMap();
            switch (reviveType) {
                // so far only got 0?
            }

            // buff freezer logic
            var buffProtector = chr.getBuffProtectorItem();
            if (buffProtector != null) {
                chr.setBuffProtector(true);
                chr.consumeItem(buffProtector);
                chr.write(UserLocal.setBuffProtector(buffProtector.getItemId(), true));
            } else {
                chr.getTemporaryStatManager().removeAllStats();
                chr.initSoulMP(); // a Soul Weapon's gauge starts over at 0 (removeAllStats took it away)
            }

            int deathcount = chr.getDeathCount();
            if (deathcount > 0) {
                deathcount--;
                chr.setDeathCount(deathcount);
                chr.write(UserLocal.deathCountInfo(deathcount));
                chr.write(FieldPacket.teleport(chr.getPosition(), chr));
                if(FieldConstants.isAlwaysRespawnInReturnMap(chr.getFieldID())) {
                     chr.warp(chr.getOrCreateFieldByCurrentInstanceType(returnMap));
                }
            } else if (chr.reviveByFightGauge()) {
                chr.write(FieldPacket.teleport(chr.getPosition(), chr)); // the gauge took the death: back up in place
            } else if (storyRetryField(chr, fieldInfo) != null) {
                // a story instance with no way out (Demon Slayer's guard fight 927000020): leaving it sent the player
                // to Henesys, out of the story; they come back in its return map instead, as a retry
                chr.warp(storyRetryField(chr, fieldInfo));
            } else {
                var inst = chr.getInstance();
                if (inst != null) {
                    // out of the instance to the death map's forced return / return map, as GMS
                    inst.removeChar(chr, inst.exitFor(fieldInfo), 0, true, true);
                } else if (chr.getTransferField() == targetField && chr.getTransferFieldReq() == chr.getField().getId()) {
                    Field toField = chr.getOrCreateFieldByCurrentInstanceType(chr.getTransferField());
                    if (toField != null && chr.getTransferField() > 0) {
                        chr.warp(toField);
                    }
                    chr.setTransferField(0);
                    return;
                } else {
                    returnMap = fieldInfo.getForcedReturn();
                    if (returnMap >= GameConstants.NO_MAP_ID) {
                        returnMap = fieldInfo.getReturnMap();
                    }
                    Field targetFieldInstance = chr.getOrCreateFieldByCurrentInstanceType(returnMap);
                    chr.warp(targetFieldInstance != null ? targetFieldInstance : chr.getField());
                }
            }
            chr.heal(chr.getMaxHP(), false, true);
            chr.setBuffProtector(false);
            if (chr.getJobHandler() != null) {
                chr.getJobHandler().handleRevive();
            }
        } else if (chr.getTransferField() == targetField && chr.getTransferFieldReq() == chr.getField().getId()) {
            Field toField = chr.getOrCreateFieldByCurrentInstanceType(chr.getTransferField());
            if (toField != null && chr.getTransferField() > 0) {
                chr.warp(toField);
            }
            chr.setTransferField(0);
        }
    }

    @Handler(op = InHeader.USER_PORTAL_SCRIPT_REQUEST)
    public static void handleUserPortalScriptRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        byte portalID = inPacket.decodeByte();
        String portalName = inPacket.decodeString();
        Portal portal = chr.getField().getInfo().getPortalByName(portalName);
        String script = portalName;
        if (portal != null) {
            portalID = (byte) portal.getId();
            script = "".equals(portal.getScript()) ? portalName : portal.getScript();
            chr.getScriptManager().startScript(portalID, script, ScriptType.Portal);
        } else {
            chr.chatMessage("Could not find that portal.");
        }
    }

    @Handler(op = InHeader.USER_PORTAL_TELEPORT_REQUEST)
    public static void handleUserPortalTeleportRequest(Char chr, InPacket inPacket) {
        byte portalID = inPacket.decodeByte();
        String portalName = inPacket.decodeString();
        Portal portal = null;
        Field field = chr.getField();
        if (field != null && field.getInfo() != null) {
            portal = chr.getField().getInfo().getPortalByName(portalName);
        }

        Events.onPortalTeleportRequest(chr, portal);
    }

    @Handler(op = InHeader.USER_TRANSFER_CHANNEL_REQUEST)
    public static void handleUserTransferChannelRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        byte channelId = (byte) (inPacket.decodeByte() + 1);

        //If player not in any channel
        if(!Server.getInstance().isUserLoggedIn(c.getUser())){
            c.close();
        }

        if (c.getWorld().getChannelById(channelId) == null) {
            chr.chatMessage("Could not find that world.");
            chr.write(FieldPacket.transferChannelReqIgnored(FieldPacket.TRANSFER_IGNORED_CHANNEL));
            return;
        }
        Field field = chr.getField();
        if (net.swordie.ms.constants.FieldConstants.teleportWouldBreak(chr, field)
                || channelId < 1 || channelId > c.getWorld().getChannels().size()) {
            chr.write(FieldPacket.transferChannelReqIgnored(FieldPacket.TRANSFER_IGNORED_CHANNEL));
            chr.dispose();
            return;
        }

        chr.changeChannel(channelId);
    }

    @Handler(op = InHeader.USER_MIGRATE_TO_CASH_SHOP_REQUEST)
    public static void handleUserMigrateToCashShopRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        Field field = chr.getField();

        if (!net.swordie.ms.ServerSettings.getBoolean("test.cashShop", true)) {
            // the client's own refusal notice names the wrong reason
            chr.chatMessage("The Cash Shop is turned off on this server.");
            chr.dispose();
            return;
        }
        // the map's MigrateLimit, and places a return to the same map id can't restore (instances)
        if (field == null || field.getInfo() == null || !field.isChannelField() || chr.getInstance() != null
                || (field.getInfo().getFieldLimit() & FieldOption.MigrateLimit.getVal()) != 0) {
            chr.write(FieldPacket.transferChannelReqIgnored(FieldPacket.TRANSFER_IGNORED_CASH_SHOP));
            chr.dispose();
            return;
        }

        chr.getOffenseManager().punishLieDetectorEvasion(false);
        chr.setPreviousFieldID(chr.getFieldID());
        Events.onWarp(chr, field, null);
        chr.getField().removeChar(chr);
        chr.getAvatarData().getCharacterStat().setPosMap(-1);

        if (chr.getParty() != null) {
            Party party = chr.getParty();
            PartyMember pm = party.getPartyMemberByID(chr.getId());
            pm.updateInfoByChar(chr);
            party.broadcastMigration();
        }

        chr.setInCashShop(true);
        // No changeChannel = true, as we're not actually migrating

        CashShop cs = Server.getInstance().getCashShop();
        c.write(Stage.setCashShop(chr, cs));
        c.write(CCashShop.loadLockerDone(chr.getAccount()));
        c.write(CCashShop.wishList(CCashShop.WISH_LOAD_DONE,
                net.swordie.ms.handlers.CashShopHandler.wishList(chr)));
        c.write(CCashShop.queryCashResult(chr));
        c.write(CCashShop.unk3());
        c.write(CCashShop.unk6());

        var map = new HashMap<Integer, Collection<Integer>>();
        map.put(ItemConstants.SURPRISE_STYLE_BOX, SSBConstants.getCurrentSsbItemIds());
        c.write(WvsContext.cashShopPreviewInfo(map));
    }

    @Handler(op = InHeader.USER_MAP_TRANSFER_REQUEST)
    public static void handleUserMapTransferRequest(Char chr, InPacket inPacket) {
        byte mtType = inPacket.decodeByte();
        byte itemType = inPacket.decodeByte();

        MapTransferType mapTransferType = MapTransferType.getByVal(mtType);
        switch (mapTransferType) {
            case DeleteListRecv: //Delete request that's received
                int targetFieldID = inPacket.decodeInt();
                HyperTPRock.removeFieldId(chr, targetFieldID);
                chr.write(WvsContext.mapTransferResult(MapTransferType.DeleteListSend, itemType, chr.getHyperRockFields()));
                break;

            case RegisterListRecv: //Register request that's received
                targetFieldID = chr.getFieldID();
                Field field = chr.getField();
                if (field == null || net.swordie.ms.constants.FieldConstants.teleportWouldBreak(chr, field)) {
                    chr.chatMessage("You may not warp to that map.");
                    chr.dispose();
                    return;
                }
                HyperTPRock.addFieldId(chr, targetFieldID);
                chr.write(WvsContext.mapTransferResult(MapTransferType.RegisterListSend, itemType, chr.getHyperRockFields()));
                break;

        }
    }

    @Handler(op = InHeader.USER_FIELD_TRANSFER_REQUEST)
    public static void handleUserFieldTransferRequest(Char chr, InPacket inPacket) {
        handleFieldTransfer(chr, inPacket);
    }

    @Handler(op = InHeader.USER_FIELD_TRANSFER_REQUEST_2)
    public static void handleUserFieldTransferRequest2(Char chr, InPacket inPacket) {
        handleFieldTransfer(chr, inPacket);
    }

    private static void handleFieldTransfer(Char chr, InPacket inPacket) {
        Field field = chr.getField();
        if (net.swordie.ms.constants.FieldConstants.teleportWouldBreak(chr, field)) {
            chr.chatMessage("You may not warp to that map.");
            chr.dispose();
            return;
        }
        int fieldID = inPacket.decodeInt();
        if (fieldID == 7860) {
            chr.warp(GameConstants.ARDENTMILL);
        } else if (fieldID == net.swordie.ms.client.guild.GuildBaseReturn.TRANSFER_KEY) {
            if (chr.getFieldID() == GameConstants.GUILD_BASE) {
                net.swordie.ms.client.guild.GuildBaseReturn.leave(chr); // the same button from inside: back out
            } else {
                net.swordie.ms.client.guild.GuildBaseReturn.enter(chr);
            }
        } else if (net.swordie.ms.loaders.FieldData.getFieldInfoById(fieldID) != null) {
            chr.warp(fieldID);
        }
    }

    @Handler(op = InHeader.MAKE_ENTER_FIELD_PACKET_FOR_QUICK_MOVE)
    public static void handleMakeEnterFieldPacketForQuickMove(Char chr, InPacket inPacket) {
        int templateID = inPacket.decodeInt();
        if (chr == null) {
            return;
        }
        useQuickMove(chr, templateID);
    }

    /** Runs the quick move entry of this NPC (the map's quick move buttons). */
    public static void useQuickMove(Char chr, int templateID) {
        QuickMoveInfo qmi = GameConstants.getQuickMoveInfos().stream().filter(info -> info.getTemplateID() == templateID).findFirst().orElse(null);
        if (qmi == null) {
            chr.dispose();
            chr.getOffenseManager().addOffense(String.format("Attempted to use non-existing quick move NPC (%d).", templateID));
            return;
        }
        useQuickMove(chr, qmi);
    }

    /** Runs a quick move entry: its level and quest gates as GMS, then its NPC's script. */
    public static void useQuickMove(Char chr, QuickMoveInfo qmi) {
        Field field = chr.getField();
        int templateID = qmi.getTemplateID();
        if (qmi.isNoInstances() && !field.isChannelField()) {
            chr.dispose();
            chr.getOffenseManager().addOffense(String.format("Attempted to use quick move (%s) in illegal map (%d).", qmi.getMsg(), field.getId()));
            return;
        }
        if (chr.getLevel() < qmi.getLevelMin()
                || (qmi.getQuestReq() > 0 && !chr.getQuestManager().hasQuestCompleted(qmi.getQuestReq()))) {
            chr.dispose();
            return;
        }
        if (templateID == GameConstants.MENU_QUICK_MOVE_NPC) {
            net.swordie.ms.client.character.MenuModule.openMenu(chr); // the "Menu" entry opens @menu
            chr.dispose();
            return;
        }
        String script = qmi.getRunScript();
        if (script == null || script.isEmpty()) {
            Npc npc = NpcData.getNpcDeepCopyById(templateID);
            script = npc == null || npc.getScripts().isEmpty() ? String.valueOf(templateID) : npc.getScripts().get(0);
        }
        chr.getScriptManager().startScript(templateID, templateID, script, ScriptType.Npc);
    }

    @Handler(op = InHeader.ENTER_TOWN_PORTAL_REQUEST)
    public static void handleEnterTownPortalRequest(Char chr, InPacket inPacket) {
        int chrId = inPacket.decodeInt(); // Char id
        boolean town = inPacket.decodeByte() != 0;

        Field field = chr.getField();
        TownPortal townPortal = field.getTownPortalByChrId(chrId);
        if (townPortal != null) {       // TODO Using teleports, as grabbing the TownPortalPoint portal id is not working
            if (town) {
                // townField -> fieldField
                Field fieldField = townPortal.getChannel().getField(townPortal.getFieldFieldId());

                chr.warp(fieldField); // Back to the original Door
                chr.write(FieldPacket.teleport(townPortal.getFieldPosition(), chr)); // Teleports player to the position of the TownPortal
            } else {
                // fieldField -> townField
                Field returnField = townPortal.getChannel().getField(townPortal.getTownFieldId()); // Initialise the Town Map,

                chr.warp(returnField); // warp Char
                chr.write(FieldPacket.teleport(townPortal.getTownPosition(), chr));
                if (returnField.getTownPortalByChrId(chrId) == null) { // So that every re-enter into the TownField doesn't spawn another TownPortal
                    returnField.broadcastPacket(WvsContext.townPortal(townPortal)); // create the TownPortal
                    returnField.addTownPortal(townPortal);
                }
            }
        } else {
            chr.dispose();
            log.warn("Character {" + chrId + "} tried entering a Town Portal in field {" + field.getId() + "} which does not exist."); // Potential Hacking Log
        }
    }

    @Handler(op = InHeader.USER_MIGRATE_AUCTION_REQUEST)
    public static void handleUserMigrateAuctionRequest(Char chr, InPacket inPacket) {
        if (chr.getField().getInfo().isTown()) {
            chr.sendToAuctionHouse();
        } else {
            chr.getOffenseManager().addOffense("Tried to go to the auction house without being in a town.");
            chr.dispose();
        }
    }

    @Handler(op = InHeader.DIMENSIONAL_MIRROR_WARP_REQUEST)
    public static void handleDimensionalMirrorWarpRequest(Char chr, InPacket inpacket) {
        int tick = inpacket.decodeInt();
        DimensionalMirrorType dmt = DimensionalMirrorType.getBySlot(inpacket.decodeInt()); // the card slot
        if (dmt == null || chr.getHP() <= 0) {
            chr.dispose();
            return;
        }
        if (dmt.getReqLv() > chr.getLevel()) {
            chr.chatMessage(Notice2, "You haven't reached the required level yet.");
            chr.dispose();
            return;

        }
        if (dmt.getQuestID() != 0 && !chr.getQuestManager().hasQuestCompleted(dmt.getQuestID())) {
            chr.chatMessage(Notice2, "You haven't finished the required quest yet.");
            chr.dispose();
            return;
        }
        int toMap = dmt.getWarpTo();
        if (!chr.getField().getInfo().isTown()) {
            chr.chatMessage("Can only warp from a town.");
            chr.dispose();
            return;
        }
        Field toField = dmt.isAvailable()
                ? chr.getClient().getChannelInstance().getField(toMap) : null;
        if (toField == null) {
            chr.chatMessage(Notice2, "That destination isn't available.");
            chr.dispose();
            return;
        }
        // like on live servers, leaving the destination (e.g. the party quest hub, the Dojo, Monster Park) takes the
        // character back to the town the mirror was used in
        if (chr.getScriptManager() != null) {
            chr.getScriptManager().setReturnField(chr.getFieldID());
        }
        chr.warp(toField);
    }

    @Handler(op = InHeader.AUCTION_LEAVE_REQUEST)
    public static void handleAuctionLeaveRequest(Char chr, InPacket inPacket) {
        if (!chr.isInAuctionHouse()) {
            return; // the exit button sends both 310 type 59 and 311
        }
        var c = chr.getClient();
        chr.getAvatarData().getCharacterStat().setPosMap(chr.getPreviousFieldID());
        chr.setInAuctionHouse(false);
        chr.setChangingChannel(true);
        chr.setLastAuctionCriteria(null);
        c.getChannelInstance().addClientInTransfer(c.getChannelInstance(), c.getChannelInstance(), chr.getId(), c);
        chr.write(ClientSocket.migrateCommand(true, (short) c.getChannelInstance().getPort()));
    }

    @Handler(op = InHeader.USER_TRANSFER_FREE_MARKET_REQUEST)
    public static void handleTransferFreeMarketRequest(Char chr, InPacket inPacket) {
        byte toChannelID = (byte) (inPacket.decodeByte() + 1);
        int fieldID = inPacket.decodeInt();
        if (chr.getWorld().getChannelById(toChannelID) != null && GameConstants.isFreeMarketField(fieldID)
                && GameConstants.isFreeMarketField(chr.getField().getId())) {
            Field toField = chr.getClient().getChannelInstance().getField(fieldID);
            if (toField == null) {
                chr.dispose();
                return;
            }
            int currentChannelID = chr.getClient().getChannel();
            if (currentChannelID != toChannelID) {
                chr.changeChannelAndWarp(toChannelID, fieldID);
            } else {
                chr.warp(toField);
            }
        }

        inPacket.decodeInt(); // tick
    }

    @Handler(op = InHeader.USER_CLIENT_RESOLUTION_RESULT)
    public static void handleUserClientResolutionResult(Client c, InPacket inPacket) {
        int resolutionTypeVal = inPacket.decodeShort();
        ResolutionType resolutionType = ResolutionType.getByVal(resolutionTypeVal);
        if (resolutionType != null) {
            c.setResolution(resolutionType);
        }
    }

    /**
     * v265 menu "Log out" / "Change character": the client asks for a relogin cookie (int 0). Like the reference
     * server, send it back to the title screen; closing the connection then saves and logs the character out.
     * The cookie itself (ISSUE_RELOGIN_COOKIE) leaves this client stuck in the field: see CharacterSwitch.
     */
    @Handler(op = InHeader.REQUEST_RELOGIN_COOKIE)
    public static void handleRequestReloginCookie(Client c, InPacket inPacket) {
        c.write(WvsContext.returnToTitle());
    }

    @Handler(op = InHeader.USER_SWITCH_CHARACTER_REQUEST)
    public static void handleUserSwitchCharacterRequest(Client c, InPacket inPacket) {
        CharacterSwitch.handleRequest(c, inPacket);
    }

    private static final java.util.Map<Integer, Long> lastPortalMessage = new java.util.concurrent.ConcurrentHashMap<>();

    /** A refused portal's reason, at most once every 3 seconds (the client asks again while the player holds up). */
    private static void tellOnce(Char chr, String message) {
        long now = System.currentTimeMillis();
        Long last = lastPortalMessage.get(chr.getId());
        if (last == null || now - last >= 3000) {
            lastPortalMessage.put(chr.getId(), now);
            chr.chatMessage(message);
        }
    }
}
