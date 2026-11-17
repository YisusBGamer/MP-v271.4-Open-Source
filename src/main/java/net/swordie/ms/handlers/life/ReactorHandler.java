package net.swordie.ms.handlers.life;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.ContentPacket;
import net.swordie.ms.connection.packet.ReactorPool;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.HarvestResultType;
import net.swordie.ms.events.Events;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.Reactor;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.ReactorData;
import net.swordie.ms.loaders.containerclasses.ReactorInfo;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReactorHandler {

    private static final Logger log = LogManager.getLogger();
    private static final int HARVEST_REACTOR_BASE = 100000;
    private static final int HARVEST_REACTOR_END = 299999;


    @Handler(op = InHeader.REACTOR_HIT)
    public static void handleReactorHit(Char chr, InPacket inPacket) {
        var objId = inPacket.decodeInt();
        var mask = inPacket.decodeInt(); // 3 = chrLeft | 2 = chrRight
        var delay = inPacket.decodeShort();
        if (inPacket.getUnreadAmount() >= 4) {
            inPacket.decodeInt(); // always 0 (the reference server reads only up to the short)
        }

        Life life = chr.getField().getLifeByObjectID(objId);
        if (!(life instanceof Reactor)) {
            return;
        }
        Reactor reactor = (Reactor) life;
        int templateID = reactor.getTemplateId();
        String action = getReactorAction(templateID);
        chr.getScriptManager().startScript(templateID, objId, action, ScriptType.Reactor);
    }

    @Handler(op = InHeader.REACTOR_CLICK)
    public static void handleReactorClick(Char chr, InPacket inPacket) {
        int objID = inPacket.decodeInt();
        Life life = chr.getField().getLifeByObjectID(objID);
        if (!(life instanceof Reactor)) {
            log.debug("Could not find reactor with objID " + objID);
            var reactor = new Reactor(0);
            reactor.setObjectId(objID);
            chr.write(ReactorPool.reactorRemove(reactor));
            return;
        }
        Reactor reactor = (Reactor) life;
        int templateID = reactor.getTemplateId();
        String action = getReactorAction(templateID);
        chr.getScriptManager().startScript(templateID, objID, action, ScriptType.Reactor);
    }

    @Handler(op = InHeader.REACTOR_RECT_IN_MOB)
    public static void handleReactorRectInMob(Char chr, InPacket inPacket) {
        var objId = inPacket.decodeInt();
        var mobObjId = inPacket.decodeInt();

        var mobLife = chr.getField().getLifeByObjectID(mobObjId);
        var reactorLife = chr.getField().getLifeByObjectID(objId);
        if (mobLife instanceof Mob && reactorLife instanceof Reactor) {
            var mob = (Mob) mobLife;
            var reactor = (Reactor) reactorLife;
            Events.onReactorRectInMob(chr, mob, reactor);

            int templateID = reactor.getTemplateId();
            String action = getReactorAction(templateID);
            chr.getScriptManager().startScript(templateID, objId, action, ScriptType.Reactor);
        }
    }

    @Handler(op = InHeader.REACTOR_KEY)
    public static void handleReactorKey(Char chr, InPacket inPacket) {
        int objID = inPacket.decodeInt();
        int lifeID = inPacket.decodeInt();
        var ignored = inPacket.decodeShort();
        Mob mob = (Mob) chr.getField().getLifeByObjectID(lifeID);
        mob.die(false);
        Field field = chr.getField();
        for (Char character : field.getChars()) {
            character.getTemporaryStatManager().increaseGolluxStack();
        }
    }

    /** Reactor object id each character is gathering (set on a successful request, used by the end notice). */
    private static final Map<Integer, Integer> gathering = new ConcurrentHashMap<>();

    // GMS: the key press on a searchable reactor (Midnight Chaser furniture, quest objects): 4 object id, 2;
    // answered with REACTOR_CHANGE_STATE and the content's own packets
    @Handler(op = InHeader.REACTOR_TOUCH)
    public static void handleReactorTouch(Char chr, InPacket inPacket) {
        int objId = inPacket.decodeInt();
        inPacket.decodeShort();
        var life = chr.getField().getLifeByObjectID(objId);
        if (!(life instanceof Reactor reactor)) {
            chr.dispose();
            return;
        }
        var inst = chr.getInstance();
        if (inst != null && inst.getInstanceHelper()
                instanceof net.swordie.ms.world.field.instance.instancehelper.arcane.MidnightChaserInstanceHelper mc) {
            mc.search(chr, reactor);
        } else {
            String action = getReactorAction(reactor.getTemplateId());
            if (action != null && !action.isEmpty()) {
                chr.getScriptManager().startScript(reactor.getTemplateId(), objId, action, ScriptType.Reactor);
            }
        }
        chr.dispose();
    }

    @Handler(ops = {InHeader.REACTOR_GATHER, InHeader.GATHER_REQUEST})
    public static void handleReactorGather(Char chr, InPacket inPacket) {
        int objId = inPacket.decodeInt();
        var field = chr.getField();

        var life = field.getLifeByObjectID(objId);
        if (!(life instanceof Reactor)) {
            chr.chatMessage("Could not find that reactor.");
            chr.dispose();
            return;
        }

        var reactor = (Reactor) life;
        var templateId = reactor.getTemplateId();

        if (templateId < HARVEST_REACTOR_BASE || templateId > HARVEST_REACTOR_END) {
            // the harvest key on a scripted reactor (Spirit Savior's freed spirits): its script
            String action = getReactorAction(templateId);
            var inst = chr.getInstance();
            if (inst != null && inst.getInstanceHelper()
                    instanceof net.swordie.ms.world.field.instance.instancehelper.arcane.MidnightChaserInstanceHelper mc) {
                mc.search(chr, reactor); // Midnight Chaser's furniture
            } else if (action != null && !action.isEmpty()) {
                chr.getScriptManager().startScript(templateId, objId, action, ScriptType.Reactor);
            } else {
                chr.chatMessage("Invalid reactor to harvest from.");
            }
            chr.dispose();
            return;
        }
        boolean herb = templateId < GameConstants.HARVEST_VEIN_START;
        int skill = herb ? SkillConstants.HERBALISM_SKILL : SkillConstants.MINING_SKILL;
        if (chr.getMakingSkillLevel(skill) <= 0) {
            chr.write(UserLocal.gatherRequestResult(objId,
                    herb ? HarvestResultType.HerbalismNoSkill : HarvestResultType.MiningNoSkill));
            return;
        }
        gathering.put(chr.getId(), objId);
        chr.write(UserLocal.gatherRequestResult(objId, HarvestResultType.Success));
    }

    /** The gather animation finished (client 0x144ebfbd0: int reactor object id): drops, mastery, reactor gone. */
    @Handler(op = InHeader.GATHER_END_NOTICE)
    public static void handleGatherEndNotice(Char chr, InPacket inPacket) {
        int objId = inPacket.decodeInt();
        Integer pending = gathering.remove(chr.getId());
        Field field = chr.getField();
        Life life = field == null ? null : field.getLifeByObjectID(objId);
        if (pending == null || pending != objId || !(life instanceof Reactor reactor) || !reactor.isHarvestable()) {
            if (field != null) {
                field.broadcastPacket(ContentPacket.gatherResult(chr.getId(), false));
            }
            return;
        }
        // the reactor script (reactor/gather0.py) drops on its 4th hit; the end notice is the whole gather
        reactor.setHitCount(Math.max(reactor.getHitCount(), 3));
        int templateID = reactor.getTemplateId();
        chr.getScriptManager().startScript(templateID, objId, getReactorAction(templateID), ScriptType.Reactor);
        // the script's mastery step sends the result (success by mastery level)
        net.swordie.ms.client.character.achievement.MapleAchievements.onGather(chr,
                templateID < GameConstants.HARVEST_VEIN_START ? SkillConstants.HERBALISM_SKILL : SkillConstants.MINING_SKILL);
    }

    /**
     * @return the script of a reactor: its own action, else that of the reactor it links to, else "[id]action"
     */
    private static String getReactorAction(int templateID) {
        ReactorInfo ri = ReactorData.getReactorInfoByID(templateID);
        String action = ri == null || ri.getAction() == null ? "" : ri.getAction();
        if (action.isBlank() && ri != null && ri.getLink() > 0) {
            ReactorInfo linked = ReactorData.getReactorInfoByID(ri.getLink());
            if (linked != null && linked.getAction() != null) {
                action = linked.getAction();
            }
        }
        return action.isBlank() ? templateID + "action" : action;
    }
}
