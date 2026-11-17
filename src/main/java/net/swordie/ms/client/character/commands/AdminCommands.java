package net.swordie.ms.client.character.commands;

import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.client.jobs.cygnus.NightWalker;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.Server;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.Account;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.hexa.HexaModule;
import net.swordie.ms.client.character.skills.vmatrix.V271Matrix;
import net.swordie.ms.client.character.FirstEnterReward;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.items.ItemOption;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.runestones.RuneStone;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.StolenSkill;
import net.swordie.ms.client.character.skills.atom.forceatom.ForceAtom;
import net.swordie.ms.client.character.skills.info.ForceAtomInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatBase;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.guild.Guild;
import net.swordie.ms.client.guild.GuildMember;
import net.swordie.ms.client.jobs.nova.Kaiser;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.client.party.PartyMember;
import net.swordie.ms.client.party.PartyResult;
import net.swordie.ms.client.rankings.RankingModule;
import net.swordie.ms.client.surprisemission.SurpriseMissionModule;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.connection.packet.*;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.field.IdkBossFieldPacket;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.*;
import net.swordie.ms.constants.JobConstants.JobEnum;
import net.swordie.ms.enums.*;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.android.Android;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.RandomPortal;
import net.swordie.ms.life.Reactor;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.life.npc.Npc;
import net.swordie.ms.loaders.*;
import net.swordie.ms.loaders.containerclasses.SkillStringInfo;
import net.swordie.ms.scripts.ScriptManagerImpl;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.util.*;
import net.swordie.ms.world.Channel;
import net.swordie.ms.world.World;
import net.swordie.ms.world.field.*;
import net.swordie.ms.world.field.instance.instancehelper.pq.PartyQuestRecords;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import net.swordie.ms.world.field.fieldevents.timedfieldevents.elitechampions.*;
import net.swordie.ms.world.field.obstacleatom.ObstacleAtomInfo;
import net.swordie.ms.life.npc.PlacedNpcTemplate;
import net.swordie.ms.life.npc.RemovedNpcTemplate;
import net.swordie.orm.dao.AccountDao;
import net.swordie.orm.dao.CharDao;
import net.swordie.orm.dao.PlacedNpcTemplateDao;
import net.swordie.orm.dao.RemovedNpcTemplateDao;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;
import static net.swordie.ms.enums.AccountType.*;
import static net.swordie.ms.enums.ChatType.*;
import static net.swordie.ms.enums.InventoryOperation.Add;

/**
 * Created on 12/22/2017.
 */
public class AdminCommands {
    private static final int LUNA_NPC = 1011100;

    static final Logger log = LogManager.getRootLogger();

    private static final CharDao charDao = (CharDao) SworDaoFactory.getByClass(Char.class);
    private static final AccountDao accountDao = (AccountDao) SworDaoFactory.getByClass(Account.class);
    private static final UserDao userDao = (UserDao) SworDaoFactory.getByClass(User.class);
    private static final PlacedNpcTemplateDao placedNpcTemplateDao = (PlacedNpcTemplateDao) SworDaoFactory.getByClass(PlacedNpcTemplate.class);
    private static final RemovedNpcTemplateDao removedNpcTemplateDao = (RemovedNpcTemplateDao) SworDaoFactory.getByClass(RemovedNpcTemplate.class);

    /**
     * Black Mage gauge test (only on the admin using it): !bmgauge <553|554|555> <amount> [seconds] puts
     * CurseOfCreation / CurseOfDestruction / BlackMageDebuff on this character, to see which bar the client draws.
     */
    @Command(names = {"bmgauge"}, requiredType = Admin)
    public static class BlackMageGauge extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 3) {
                chr.chatMessage("Usage: !bmgauge <553|554|555> <amount> [seconds]");
                return;
            }
            CharacterTemporaryStat cts;
            switch (args[1]) {
                case "553" -> cts = CurseOfCreation;
                case "554" -> cts = CurseOfDestruction;
                case "555" -> cts = BlackMageDebuff;
                default -> {
                    chr.chatMessage("Pick 553, 554 or 555.");
                    return;
                }
            }
            int amount = Integer.parseInt(args[2]);
            int seconds = args.length > 3 ? Integer.parseInt(args[3]) : 60;
            Option o = new Option(amount, 100014, seconds);
            o.xOption = amount;
            var tsm = chr.getTemporaryStatManager();
            tsm.putCharacterStatValue(cts, o);
            tsm.sendSetStatPacket();
            chr.chatMessage(cts + " " + amount + " for " + seconds + " s");
        }
    }

    @Command(names = {"help"}, requiredType = Player)
    public static class Help extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            for (Class clazz : AdminCommands.class.getClasses()) {
                Command cmd = (Command) clazz.getAnnotation(Command.class);
                if (chr.getUser().getAccountType().ordinal() >= cmd.requiredType().ordinal()) {
                    StringBuilder str = new StringBuilder(String.format("[%s] ", cmd.requiredType().toString()));
                    for (String cmdName : cmd.names()) {
                        str.append(cmdName);
                        str.append(", ");
                    }
                    chr.chatMessage(Expedition, str.toString());
                }
            }
        }
    }
    @Command(names = {"warphere"}, requiredType = GameMaster)
    public static class WarpHere extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            Char victim = chr.getWorld().getCharByName((args[1]));
            if (victim != null) {
                victim.changeChannelAndWarp((byte) chr.getClient().getChannelInstance().getChannelId(), chr.getFieldID());
            } else {
                chr.chatMessage(Notice2, "Player not found, make sure you typed the correct name (Case Sensitive).");
            }
        }
    }
    @Command(names = {"givenx"}, requiredType = GameMaster)
    public static class giveNx extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 3) {
                chr.chatMessage("Usage: !givenx [name] [amount]");
                return;
            }
            String name = args[1];
            int amount = Integer.valueOf(args[2]);
            Char other = chr.getWorld().getCharByName(name);
            other.addNx(amount);
        }
    }
    @Command(names = {"packet"}, requiredType = Admin)
    public static class TestPacket extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 3) {
                chr.chatMessage("Usage: !packet <op> <data>");
                return;
            }
            short op = Short.parseShort(args[1]);

            OutPacket outPacket = new OutPacket(op);
            StringBuilder data = new StringBuilder();
            for (int j = 2; j < args.length; j++) {
                data.append(" ").append(args[j]);
            }
            outPacket.encodeArr(data.toString());
            outPacket.encodeArr(new byte[300]);

            chr.write(outPacket);
        }
    }


    @Command(names = {"usects", "cts"}, requiredType = Tester)
    public static class CtsCom extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            Option o = new Option();
            o.nOption = Integer.parseInt(args[2]);
            o.rOption = net.swordie.ms.client.jobs.adventurer.thief.Thief.HASTE;
            o.tOption = 5;

            CharacterTemporaryStat cts = CharacterTemporaryStat.getByBitPos(Integer.parseInt(args[1]));
//            CharacterTemporaryStat cts = CharacterTemporaryStat.MagnetArea;
            if (cts == null) {
                chr.chatMessage("Could not find cts with bitpos " + args[1]);
                return;
            }
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            tsm.putCharacterStatValue(cts, o);
            tsm.sendSetStatPacket();
            log.info(String.format("CTS %s = %s", args[1], cts));
            chr.chatMessage(String.format("CTS %s = %s", args[1], cts));
        }
    }

    @Command(names = {"fifthjob", "V"}, requiredType = Tester)
    public static class V extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            chr.getQuestManager().completeQuest(QuestConstants.FIFTH_JOB_QUEST);
        }
    }

    @Command(names = {"showinvinfo", "invinfo"}, requiredType = Tester)
    public static class ShowInvInfo extends AdminCommand {

        public static void execute(Char chr, String[] args) {

            chr.chatMessage(WorldName, "------------------------------------------------------------");
            for (InvType invType : InvType.values()) {
                var inv = chr.getInventoryByType(invType);
                chr.chatMessage("---");
                chr.chatMessage(WorldName, "%s (size = %d/%d)", invType.toString(), inv.getItems().size(), inv.getSlots());
                for (Item item : chr.getInventoryByType(invType).getItems()) {
                    item.setInvType(invType);
                    String name = StringData.getItemStringById(item.getItemId());
                    chr.chatMessage(WorldName, String.format("%s, %d, %d, %d, %s", name, item.getItemId(), item.getId(),
                            item.getBagIndex(), item.getInvType().toString()));
                }
            }
        }
    }

    @Command(names = {"checkid", "getid", "charid", "id"}, requiredType = Tester)
    public static class CheckID extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            chr.chatMessage(SpeakerChannel, "your charID = " + chr.getId() + " \r\nYour userID = " + chr.getUserId());
        }
    }

    @Command(names = {"np", "nearestportal", "mapinfo"}, requiredType = Tester)
    public static class NP extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            Rect rect = new Rect(
                    new Position(
                            chr.getPosition().deepCopy().getX() - 30,
                            chr.getPosition().deepCopy().getY() - 30),
                    new Position(
                            chr.getPosition().deepCopy().getX() + 30,
                            chr.getPosition().deepCopy().getY() + 30)
            );
            chr.chatMessage(Normal, "~~~~~~~~~~");
            chr.chatMessage(SpeakerChannel, "Current Map: " + NumberFormat.getNumberInstance(Locale.US).format(chr.getFieldID()));
            chr.chatMessage(SpeakerChannel, "Current ReturnMap: " + NumberFormat.getNumberInstance(Locale.US).format(chr.getField().getInfo().getReturnMap()));
            chr.chatMessage(SpeakerChannel, "Fieldscript: " + chr.getField().getInfo().getFieldScript());
            chr.chatMessage(SpeakerChannel, "User enter: " + chr.getField().getInfo().getOnUserEnter());
            chr.chatMessage(SpeakerChannel, "User first enter: " + chr.getField().getInfo().getOnFirstUserEnter());
            var info = chr.getField().getInfo();
            List<String> limits = new ArrayList<>();
            for (FieldOption o : FieldOption.values()) {
                if ((info.getFieldLimit() & o.getVal()) != 0) {
                    limits.add(o.name());
                }
            }
            chr.chatMessage(SpeakerChannel, "Limits: " + (limits.isEmpty() ? "none" : String.join(", ", limits)));
            chr.chatMessage(SpeakerChannel, String.format("Town %s, instance %s, channel field %s, time limit %d s",
                    info.isTown(), chr.getInstance() != null, chr.getField().isChannelField(), info.getTimeLimit()));
            chr.chatMessage(SpeakerChannel, "");
            for (Portal portal : chr.getField().getClosestPortal(rect)) {
                chr.chatMessage(SpeakerChannel, "Portal Name: " + portal.getName());
                chr.chatMessage(SpeakerChannel, "Portal ID: " + NumberFormat.getNumberInstance(Locale.US).format(portal.getId()));
                chr.chatMessage(SpeakerChannel, "Portal target map: " + NumberFormat.getNumberInstance(Locale.US).format(portal.getTargetMapId()));
                chr.chatMessage(SpeakerChannel, "Portal script: " + portal.getScript());
                chr.chatMessage(SpeakerChannel, ".");
            }
            chr.chatMessage(Normal, "~~~~~~~~~~");
        }
    }

    @Command(names = {"stats"}, requiredType = Tester)
    public static class Stats extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            int strength = chr.getStat(Stat.str);
            int dexterity = chr.getStat(Stat.dex);
            int intellect = chr.getStat(Stat.inte);
            int luck = chr.getStat(Stat.luk);
            int hp = chr.getStat(Stat.hp);
            int mhp = chr.getStat(Stat.mhp);
            int mp = chr.getStat(Stat.mp);
            int mmp = chr.getStat(Stat.mmp);
            double hpratio = (((double) hp) / mhp) * 100;
            double mpratio = (((double) mp) / mmp) * 100;
            DecimalFormat formatNumbers = new DecimalFormat("##.00");
            NumberFormat addDeci = NumberFormat.getNumberInstance(Locale.US);
            chr.chatMessage(Notice2, "STR: " + addDeci.format(strength) + "  DEX: " + addDeci.format(dexterity) + "  INT: " + addDeci.format(intellect) + "  LUK: " + addDeci.format(luck));
            chr.chatMessage(Notice2, "HP: " + addDeci.format(hp) + " / " + addDeci.format(mhp) + " (" + formatNumbers.format(hpratio) + "%)   MP: " + addDeci.format(mp) + " / " + addDeci.format(mmp) + " (" + formatNumbers.format(mpratio) + "%)");
        }
    }

    @Command(names = {"spawn"}, requiredType = Tester)
    public static class Spawn extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !spawn <id> (<amount> <hp>).");
                return;
            }
            int id = Integer.parseInt(args[1]);
            int count = 1;
            if (args.length > 2) {
                count = Integer.parseInt(args[2]);
                if (count > 50) {
                    count = 50;
                    chr.chatMessage("You put the amount of mobs to spawn above 50, capping it to 50.");
                }
            }
            long hp = 0;
            if (args.length > 3) {
                hp = Long.parseLong(args[3]);
            }
            for (int i = 0; i < count; i++) {
                Mob mob = MobData.getMobDeepCopyById(id);
                if (mob == null) {
                    chr.chatMessage("Could not find a mob with that ID.");
                    return;
                }
                Field field = chr.getField();
                Position pos = chr.getPosition();
                Foothold fh = field.getInfo().getFootholdById(chr.getFoothold());
                mob.setCurFoodhold(fh);
                mob.setHomeFoothold(fh);
                mob.setPosition(pos.deepCopy());
                mob.setPrevPos(pos.deepCopy());
                mob.setPosition(pos.deepCopy());
                if (hp > 0) {
                    mob.setMaxHp(hp);
                    mob.setHp(hp);
                }
                mob.setNotRespawnable(true);
                if (mob.getField() == null) {
                    mob.setField(field);
                }
                field.spawnLife(mob, null);
            }
        }
    }

    @Command(names = {"npc", "spawnnpc"}, requiredType = GameMaster)
    public static class NPC extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            int id = Integer.parseInt(args[1]);
            Npc npc = NpcData.getNpcDeepCopyById(id);
            if (npc == null) {
                chr.chatMessage("Could not find an npc with that ID.");
                return;
            }
            Field field = chr.getField();
            Position pos = chr.getPosition();
            npc.setPosition(pos.deepCopy());
            npc.setCy(chr.getPosition().getY());
            npc.setRx0(chr.getPosition().getX() + 50);
            npc.setRx1(chr.getPosition().getX() - 50);
            npc.setFh(chr.getFoothold());
            npc.setNotRespawnable(true);
            if (npc.getField() == null) {
                npc.setField(field);
            }
            field.spawnLife(npc, null);
            log.debug("npc has id " + npc.getObjectId());
        }
    }

    // equip stats go to the client as shorts and % stats as bytes: bigger values wrap negative.
    // Contributed by Dapsy (from the Discord).
    public static final int PRO_ITEM_MAX_STAT = Short.MAX_VALUE;
    public static final int PRO_ITEM_MAX_PERCENT = Byte.MAX_VALUE;

    private static void applyProStats(Char gm, Equip equip, int stat, int atk, int percent) {
        int s = Math.max(0, Math.min(stat, PRO_ITEM_MAX_STAT));
        int a = Math.max(0, Math.min(atk, PRO_ITEM_MAX_STAT));
        int p = Math.max(0, Math.min(percent, PRO_ITEM_MAX_PERCENT));
        if (gm != null && (s != stat || a != atk || p != percent)) {
            gm.chatMessage(Notice2, String.format("An item holds at most %,d per stat and attack and %d%% per %% stat - "
                    + "capped to stats %d, attack %d, %d%%.", PRO_ITEM_MAX_STAT, PRO_ITEM_MAX_PERCENT, s, a, p));
        }
        equip.setBaseStat(EquipBaseStat.iStr, s);
        equip.setBaseStat(EquipBaseStat.iDex, s);
        equip.setBaseStat(EquipBaseStat.iInt, s);
        equip.setBaseStat(EquipBaseStat.iLuk, s);
        equip.setBaseStat(EquipBaseStat.iMaxHP, s);
        equip.setBaseStat(EquipBaseStat.iMaxMP, s);
        equip.setBaseStat(EquipBaseStat.iDEF, s);
        equip.setBaseStat(EquipBaseStat.iPAD, a);
        equip.setBaseStat(EquipBaseStat.iMAD, a);
        equip.setBaseStat(EquipBaseStat.bdr, p);
        equip.setBaseStat(EquipBaseStat.imdr, p);
        equip.setBaseStat(EquipBaseStat.damR, p);
        equip.setBaseStat(EquipBaseStat.statR, p);
    }

    @Command(names = {"proitem"}, requiredType = Tester)
    public static class ProItem extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 5) {
                chr.chatMessage(Notice2, "Needs more args! <id> <Stat> <Attack> <Flame stats>");
                return;
            }
            int id = Integer.parseInt(args[1]);
            int stat = Integer.parseInt(args[2]);
            int atk = Integer.parseInt(args[3]);
            int flames = Integer.parseInt(args[4]);
            Equip equip = ItemData.getEquipDeepCopy(id, false, chr.getJob());
            if (equip == null) {
                chr.chatMessage(Notice2, id + " is not an equip.");
                return;
            }
            applyProStats(chr, equip, stat, atk, flames);

            chr.addItemToInventory(equip);
            chr.write(WvsContext.inventoryOperation(true, false,
                    Add, (short) equip.getBagIndex(), (byte) 1,
                    0, equip));

        }
    }

    @Command(names = {"setpotential", "setpot"}, requiredType = Tester)
    public static class SetPotential extends AdminCommand {

        public static void execute(Char chr, String[] args) {

            if (args.length < 5) {
                chr.chatMessage("Usage: !setpot <inv position> <id 1st line> <id 2nd line> <id 3rd line>");
                return;
            }
            int invPosition = Integer.parseInt(args[1]);
            Equip equip = (Equip) chr.getInventoryByType(InvType.EQUIP).getItemBySlot(invPosition);
            if (equip == null) {
                chr.chatMessage("There is no equip on this position.");
                return;
            }
            equip.setOptionBase(0, Integer.parseInt(args[2]));
            equip.setOptionBase(1, Integer.parseInt(args[3]));
            equip.setOptionBase(2, Integer.parseInt(args[4]));
            equip.updateToChar(chr);
        }
    }

    @Command(names = {"setbonuspotential", "setbpotential", "setbpot"}, requiredType = Tester)
    public static class SetBonusPotential extends AdminCommand {

        public static void execute(Char chr, String[] args) {

            if (args.length < 5) {
                chr.chatMessage("Usage: !setbpot <inv position> <id 1st line> <id 2nd line> <id 3rd line>");
                return;
            }
            int invPosition = Integer.parseInt(args[1]);
            Equip equip = (Equip) chr.getInventoryByType(InvType.EQUIP).getItemBySlot(invPosition);
            if (equip == null) {
                chr.chatMessage("There is no equip on this position.");
                return;
            }
            equip.setOptionBonus(0, Integer.parseInt(args[2]));
            equip.setOptionBonus(1, Integer.parseInt(args[3]));
            equip.setOptionBonus(2, Integer.parseInt(args[4]));
            equip.updateToChar(chr);
        }
    }

    @Command(names = {"setflames", "flames"}, requiredType = Tester)
    public static class SetFlames extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 7) {
                chr.chatMessage("Usage: !flames <inv position> <stats> <att> <as%> <td%> <bd%>");
                return;
            }
            int invPosition = Integer.parseInt(args[1]);
            Equip equip = (Equip) chr.getInventoryByType(InvType.EQUIP).getItemBySlot(invPosition);
            if (equip == null) {
                chr.chatMessage("There is no equip on this position.");
                return;
            }
            int stat = Integer.parseInt(args[2]);
            int att = Integer.parseInt(args[3]);
            int as = Integer.parseInt(args[4]);
            int td = Integer.parseInt(args[5]);
            int bd = Integer.parseInt(args[6]);

            equip.setfSTR(stat);
            equip.setfDEX(stat);
            equip.setfINT(stat);
            equip.setfLUK(stat);
            equip.setfHP(stat);
            equip.setfMP(stat);
            equip.setfDEF(stat);
            equip.setfSpeed(stat);
            equip.setfJump(stat);

            equip.setfATT(att);
            equip.setfMATT(att);

            equip.setfAllStat(as);
            equip.setfDamage(td);
            equip.setfBoss(bd);
            equip.updateToChar(chr);
        }
    }

    @Command(names = {"setflame", "flame"}, requiredType = Tester)
    public static class setFlame extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 4) {
                chr.chatMessage("Usage: !flame <inv position> <flame type> <value>");
                chr.chatMessage("Example: !flame 1 str 40");
                return;
            }
            int invPosition = Integer.parseInt(args[1]);
            Equip equip = (Equip) chr.getInventoryByType(InvType.EQUIP).getItemBySlot(invPosition);
            if (equip == null) {
                chr.chatMessage("There is no equip on this position.");
                return;
            }
            String flame = args[2].toLowerCase();
            short value = Short.parseShort(args[3]);
            switch (flame) {
                case "str":
                    equip.setfSTR(value);
                    break;
                case "dex":
                    equip.setfDEX(value);
                    break;
                case "int":
                    equip.setfINT(value);
                    break;
                case "luk":
                    equip.setfLUK(value);
                    break;
                case "att":
                case "atk":
                    equip.setfATT(value);
                    break;
                case "matt":
                case "matk":
                    equip.setfMATT(value);
                    break;
                case "def":
                    equip.setfDEF(value);
                    break;
                case "hp":
                    equip.setfHP(value);
                    break;
                case "mp":
                    equip.setfMP(value);
                    break;
                case "speed":
                    equip.setfSpeed(value);
                    break;
                case "jump":
                    equip.setfJump(value);
                    break;
                case "allstat":
                case "as":
                    equip.setfAllStat(value);
                    break;
                case "boss":
                case "bdmg":
                case "bdr":
                    equip.setfBoss(value);
                    break;
                case "dmg":
                case "damage":
                    equip.setfDamage(value);
                    break;
                case "level":
                case "lvl":
                case "lv":
                    equip.setfLevel(value);
                    break;
                case "reset":
                    equip.resetFlameStats();
                    break;
                default:
                    chr.chatMessage("Unknown Flame Type");
                    return;
            }
            equip.updateToChar(chr);
        }
    }

    @Command(names = {"getitem", "item"}, requiredType = Tester)
    public static class GetItem extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            short quant = 1;
            if (args.length > 2 && Util.isNumber(args[args.length - 1])) {
                quant = Short.parseShort(args[args.length - 1]);
            }

            if (Util.isNumber(args[1])) {
                int id = Integer.parseInt(args[1]);
                Equip equip = ItemData.getEquipDeepCopy(id, true);
                if (equip == null) {
                    Item item = ItemData.getItemDeepCopy(id, true);
                    if (item == null) {
                        chr.chatMessage(WorldName, String.format("Could not find an item with id %d", id));
                        return;
                    }
                    item.setQuantity(quant);
                    chr.addItemToInventory(item);
                } else {
                    if (equip.getItemId() != id) {
                        chr.chatMessage(WorldName, String.format("%d can't be shown by this client (it would disconnect you): giving %d instead", id, equip.getItemId()));
                    }
                    for (int i = 0; i < quant; i++) {
                        equip = ItemData.getEquipDeepCopy(id, true);
                        chr.addItemToInventory(equip);
                    }
                }
            } else {
                StringBuilder query = new StringBuilder();
                int size = args.length;
                if (Util.isNumber(args[size - 1])) {
                    size--;
                    quant = Short.parseShort(args[size]);
                }
                for (int i = 1; i < size; i++) {
                    query.append(args[i].toLowerCase()).append(" ");
                }
                query = new StringBuilder(query.substring(0, query.length() - 1));
                Map<Integer, String> map = StringData.getItemStringByName(query.toString(), false);
                if (map.size() == 0) {
                    chr.chatMessage(WorldName, "No items found for query " + query);
                }
                for (Map.Entry<Integer, String> entry : map.entrySet()) {
                    int id = entry.getKey();
                    Item item = ItemData.getEquipDeepCopy(id, true);
                    if (item != null) {
                        // If item is equip
                        Equip equip = (Equip) item;
                        if (equip.getItemId() < 1000000) { // hardcode-ok
                            continue;
                        }
                        for (int i = 0; i < quant; i++) {
                            equip = ItemData.getEquipDeepCopy(id, true);
                            chr.addItemToInventory(equip);
                        }
                        return;
                    }

                    // if item is not equip
                    item = ItemData.getItemDeepCopy(id);
                    if (item == null) {
                        continue;
                    }
                    item.setQuantity(quant);
                    chr.addItemToInventory(item);
                    return;
                }
            }
        }
    }

    @Command(names = {"giveitem"}, requiredType = GameMaster)
    public static class GiveItem extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 3) {
                chr.chatMessage("Usage: !giveitem <playername> <item query (id/name)> <?quantity?>");
                return;
            }

            var other = chr.getField().getCharByName(args[1]);

            if (Util.isNumber(args[2])) {

                int id = Integer.parseInt(args[2]);
                Equip equip = ItemData.getEquipDeepCopy(id, true);
                if (equip == null) {
                    Item item = ItemData.getItemDeepCopy(id, true);
                    if (item == null) {
                        chr.chatMessage(WorldName, String.format("Could not find an item with id %d", id));
                        return;
                    }
                    short quant = 1;
                    if (args.length > 3) {
                        quant = Short.parseShort(args[3]);
                    }
                    item.setQuantity(quant);
                    other.addItemToInventory(item);
                    chr.chatMessage(String.format("Gave item %s (%s) to %s.", item.getItemId(), item.getQuantity(), other.getName()));
                } else {
                    other.addItemToInventory(equip);
                    chr.chatMessage(String.format("Gave item %s (%s) to %s.", equip.getItemId(), equip.getQuantity(), other.getName()));
                }
            } else {
                StringBuilder query = new StringBuilder();
                int size = args.length;
                short quant = 1;
                if (Util.isNumber(args[size - 1])) {
                    size--;
                    quant = Short.parseShort(args[size]);
                }
                for (int i = 2; i < size; i++) {
                    query.append(args[i].toLowerCase()).append(" ");
                }
                query = new StringBuilder(query.substring(0, query.length() - 1));
                Map<Integer, String> map = StringData.getItemStringByName(query.toString(), false);
                if (map.size() == 0) {
                    chr.chatMessage(WorldName, "No items found for query " + query);
                }
                for (Map.Entry<Integer, String> entry : map.entrySet()) {
                    int id = entry.getKey();
                    Item item = ItemData.getEquipDeepCopy(id, true);
                    if (item != null) {
                        Equip equip = (Equip) item;
                        if (equip.getItemId() < 1000000) { // hardcode-ok
                            continue;
                        }
                        other.addItemToInventory(equip);
                        other.write(WvsContext.inventoryOperation(true, false,
                                Add, (short) equip.getBagIndex(), (byte) -1, 0, equip));
                        chr.chatMessage(String.format("Gave item %s (%s) to %s.", item.getItemId(), item.getQuantity(), other.getName()));
                        return;
                    }
                    item = ItemData.getItemDeepCopy(id);
                    if (item == null) {
                        continue;
                    }
                    item.setQuantity(quant);
                    other.addItemToInventory(item);
                    other.write(WvsContext.inventoryOperation(true, false,
                            Add, (short) item.getBagIndex(), (byte) -1, 0, item));
                    chr.chatMessage(String.format("Gave item %s (%s) to %s.", item.getItemId(), item.getQuantity(), other.getName()));
                    return;
                }
            }
        }
    }

    @Command(names = {"coupon"}, requiredType = GameMaster)
    public static class Coupon extends AdminCommand {
        private static final String USAGE = "!coupon add <code> <item id> [qty] [uses] [days] | !coupon nx <code> <NX> [uses] [days]"
                + " | !coupon mp <code> <Maple Points> [uses] [days] | !coupon mesos <code> <mesos> [uses] [days]"
                + " | !coupon list | !coupon del <code>   (uses 0 = any number of accounts; each account once)";

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage(USAGE);
                return;
            }
            String sub = args[1].toLowerCase();
            try {
                switch (sub) {
                    case "list" -> {
                        var lines = net.swordie.ms.client.character.cashshop.CouponModule.listCoupons();
                        if (lines.isEmpty()) {
                            chr.chatMessage("No coupon codes yet.");
                        }
                        lines.forEach(chr::chatMessage);
                    }
                    case "del", "delete", "remove" -> {
                        if (args.length < 3) {
                            chr.chatMessage(USAGE);
                            return;
                        }
                        boolean had = net.swordie.ms.client.character.cashshop.CouponModule.deleteCoupon(args[2]);
                        chr.chatMessage(had ? "Deleted " + args[2].toUpperCase() + "." : "No such code.");
                    }
                    case "add", "nx", "mp", "mesos" -> {
                        if (args.length < 4) {
                            chr.chatMessage(USAGE);
                            return;
                        }
                        String code = args[2];
                        long value = Long.parseLong(args[3]);
                        int at = 4;
                        int qty = 1;
                        if (sub.equals("add")) {
                            if (ItemData.getItemDeepCopy((int) value) == null) {
                                chr.chatMessage("No item with id " + value + ".");
                                return;
                            }
                            if (args.length > at) {
                                qty = Integer.parseInt(args[at++]);
                            }
                        }
                        int uses = args.length > at ? Integer.parseInt(args[at++]) : 0;
                        int days = args.length > at ? Integer.parseInt(args[at]) : 0;
                        long expires = days > 0 ? System.currentTimeMillis() + days * 86_400_000L : 0;
                        boolean ok = net.swordie.ms.client.character.cashshop.CouponModule.addCoupon(code,
                                sub.equals("add") ? (int) value : 0, qty,
                                sub.equals("nx") ? (int) value : 0, sub.equals("mp") ? (int) value : 0,
                                sub.equals("mesos") ? value : 0, uses, expires, "by " + chr.getName());
                        chr.chatMessage(ok ? String.format("Coupon %s saved (%s %d%s, %s accounts%s).",
                                code.toUpperCase(), sub, value, sub.equals("add") ? " x" + qty : "",
                                uses == 0 ? "any number of" : "up to " + uses, days > 0 ? ", " + days + " days" : "")
                                : "Could not save that code (1-32 characters).");
                    }
                    default -> chr.chatMessage(USAGE);
                }
            } catch (NumberFormatException e) {
                chr.chatMessage(USAGE);
            }
        }
    }

    @Command(names = {"job", "setjob"}, requiredType = Tester)
    public static class Job extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            short id = Short.parseShort(args[1]);
            JobEnum job = JobEnum.getJobById(id);
            if (job != null) {
                chr.setJob(id);
                if (JobConstants.isDualBlade(id)) {
                    chr.getAvatarData().getCharacterStat().setSubJob(1);
                }
                Map<Stat, Object> stats = new HashMap<>();
                stats.put(Stat.job, id);
                chr.write(WvsContext.statChanged(stats, chr.getAvatarData().getCharacterStat().getSubJob()));
            } else {
                chr.chatMessage("Unknown job id " + id);
            }
        }
    }

    @Command(names = {"sp", "setsp"}, requiredType = Tester)
    public static class Sp extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            int num = Integer.parseInt(args[1]);
            if (JobConstants.isExtendSpJob(chr.getJob())) {
                if (num >= 0) {
                    // optional job advancement (1 = 1st job's skills); the current one by default
                    if (args.length > 2) {
                        chr.getAvatarData().getCharacterStat().getExtendSP()
                                .setSpToJobLevel(Integer.parseInt(args[2]), num);
                    } else {
                        chr.setSpToCurrentJob(num);
                    }
                    Map<Stat, Object> stats = new HashMap<>();
                    stats.put(Stat.sp, chr.getAvatarData().getCharacterStat().getExtendSP());
                    chr.write(WvsContext.statChanged(stats));
                }
            } else {
                chr.getAvatarData().getCharacterStat().setSp(num);
                Map<Stat, Object> stats = new HashMap<>();
                stats.put(Stat.sp, chr.getAvatarData().getCharacterStat().getSp());
                chr.write(WvsContext.statChanged(stats));
            }
        }
    }

    @Command(names = {"ap", "setap"}, requiredType = Tester)
    public static class Ap extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            int num = Integer.parseInt(args[1]);
            if (num >= 0) {
                chr.setStatAndSendPacket(Stat.ap, (short) num);
            }
        }
    }

    @Command(names = {"hp", "sethp"}, requiredType = Tester)
    public static class Hp extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            int num = Integer.parseInt(args[1]);
            if (num >= 0) {
                chr.setStatAndSendPacket(Stat.hp, num);
                if (num == 0) {
                    chr.die();
                }
            }
        }
    }

    @Command(names = {"mp", "setmp"}, requiredType = Tester)
    public static class Mp extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            int num = Integer.parseInt(args[1]);
            if (num >= 0) {
                chr.setStatAndSendPacket(Stat.mp, num);
            }
        }
    }

    @Command(names = {"setstat"}, requiredType = Admin)
    public static class SetStat extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            Stat stat = args.length > 2 ? switch (args[1].toLowerCase()) {
                case "str" -> Stat.str;
                case "dex" -> Stat.dex;
                case "int" -> Stat.inte;
                case "luk" -> Stat.luk;
                default -> null;
            } : null;
            if (stat == null || !Util.isNumber(args[2]) || Integer.parseInt(args[2]) < 0) {
                chr.chatMessage("Usage: !setstat <str|dex|int|luk> <amount>");
                return;
            }
            chr.setStatAndSendPacket(stat, (short) Integer.parseInt(args[2]));
        }
    }

    @Command(names = {"level", "setlevel", "lvl", "lv"}, requiredType = Tester)
    public static class Level extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            int num = Integer.parseInt(args[1]);
            if (num >= 0) {
                chr.setStatAndSendPacket(Stat.level, num);
                chr.setStatAndSendPacket(Stat.exp, 0);
                chr.getOrCreateJobHandler().handleLevelUp();
                chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.levelUpEffect()));
            }
        }
    }

    @Command(names = {"leveluntil", "levelupuntil", "leveltill", "leveluptill"}, requiredType = Tester)
    public static class LevelUntil extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            int num = Integer.parseInt(args[1]);
            int level = chr.getLevel();
            while (level < num) {
                level++;
                chr.setStat(Stat.level, level);
                Map<Stat, Object> stats = new HashMap<>();
                stats.put(Stat.level, level);
                stats.put(Stat.exp, (long) 0);
                chr.write(WvsContext.statChanged(stats));
                chr.getOrCreateJobHandler().handleLevelUp();
                chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.levelUpEffect()));
            }
        }
    }

    @Command(names = {"heal"}, requiredType = Tester)
    public static class Heal extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            int hp = chr.getMaxHP();
            if (args.length > 1) {
                hp = Integer.parseInt(args[1]);
            }
            chr.heal(hp, false, true);
            chr.healMP(chr.getMaxMP());
        }
    }

    @Command(names = {"invincible", "god", "godmode"}, requiredType = Tester)
    public static class Invincible extends AdminCommand {
        private static final int GOD_MODE_BUFF_ID = 1;
        private static final int GOD_MODE_SECONDS = 3600;

        public static void execute(Char chr, String[] args) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            // toggle on the stat this command sets: isInvincible() also reads it, so it never turned off
            boolean on = !tsm.hasStat(IndieNotDamaged);
            chr.setInvincible(on);
            chr.chatMessage("Invincibility: " + on);
            if (on) {
                // an indie option with no skill or time reads as already over to the client, which then reports it nonstop
                tsm.putCharacterStatValue(IndieNotDamaged, new Option(1, GOD_MODE_BUFF_ID, GOD_MODE_SECONDS));
            } else {
                tsm.removeStat(IndieNotDamaged);
            }
            tsm.sendSetStatPacket();
        }
    }

    @Command(names = {"morph"}, requiredType = Tester)
    public static class Morph extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage(Notice2, "Needs more args! <id>");
                return;
            }

            int morphID = Integer.parseInt(args[1]);
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            Option o1 = new Option();
            o1.nOption = morphID;
            o1.rOption = Kaiser.FINAL_TRANCE;
            tsm.putCharacterStatValue(CharacterTemporaryStat.Morph, o1);
            tsm.sendSetStatPacket();
        }
    }

    @Command(names = {"mount"}, requiredType = Tester)
    public static class Mount extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage(Notice2, "Needs more args! <id>");
                return;
            }

            int mountID = Integer.parseInt(args[1]);
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            TemporaryStatBase tsb = tsm.getTSBByTSIndex(TSIndex.RideVehicle);
            tsb.setNOption(mountID);
            // the riding skill of that vehicle as the reason, so the client's own dismount names it
            int reason = Kaiser.FINAL_TRANCE;
            for (var si : net.swordie.ms.loaders.SkillData.getSkillInfos().values()) {
                if (si.getVehicleId() == mountID && (reason == Kaiser.FINAL_TRANCE || si.getSkillId() / 10000 == 8000)) {
                    reason = si.getSkillId();
                }
            }
            tsb.setROption(reason);
            tsm.putCharacterStatValue(RideVehicle, tsb.getOption());
            tsm.sendSetStatPacket();
        }
    }

    @Command(names = {"unmount"}, requiredType = Tester)
    public static class Unmount extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            if (tsm.hasStat(RideVehicle)) {
                tsm.removeStat(RideVehicle, false);
            }
            chr.chatMessage(Notice2, "Mount removed.");
        }
    }

    @Command(names = {"setmap", "warp"}, requiredType = Tester)
    public static class SetMap extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length > 1 && Util.isNumber(args[1])) {
                Field toField = chr.getOrCreateFieldForGmWarp(Integer.parseInt(args[1]));
                if (toField != null) {
                    chr.warp(toField);
                } else {
                    chr.chatMessage(Notice2, "Could not find a field with id " + args[1]);
                }
            } else {
                chr.chatMessage("Please input a number as first argument.");
            }
        }
    }

    @Command(names = {"setportal"}, requiredType = Tester)
    public static class SetPortal extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            int portalID = Integer.parseInt(args[1]);
            Portal portal = chr.getField().getInfo().getPortalByID(portalID);
            if (portal == null) {
                chr.chatMessage(Notice2, "Portal does not exist.");
                return;
            }
            Position position = new Position(portal.getX(), portal.getY());
            chr.write(FieldPacket.teleport(position, chr));
        }
    }

    @Command(names = {"getskill"}, requiredType = Tester)
    public static class GetSkill extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 4) {
                chr.chatMessage(Notice2, "Needs more args! <id> <cur> <max>");
                return;
            }
            int id = Integer.parseInt(args[1]);
            int cur = Integer.parseInt(args[2]);
            int max = Integer.parseInt(args[3]);
            chr.addSkill(id, cur, max);
        }
    }

    @Command(names = {"makechar"}, requiredType = Tester)
    public static class MakeChar extends AdminCommand {
        // !makechar <name>: a Beginner on your account (this world); pick it on the character select screen
        public static void execute(Char chr, String[] args) {
            if (args.length < 2 || !net.swordie.ms.constants.GameConstants.isValidName(args[1])) {
                chr.chatMessage("Usage: !makechar <name> (a valid, unused character name)");
                return;
            }
            String name = args[1];
            var charDao = (net.swordie.orm.dao.CharDao) net.swordie.orm.dao.SworDaoFactory.getByClass(Char.class);
            net.swordie.ms.client.Account acc = chr.getAccount();
            if (charDao.getByNameAndWorld(name, acc.getWorldId()) != null) {
                chr.chatMessage(name + " is taken.");
                return;
            }
            try {
                byte gender = 0;
                int face = 20000, hair = 30000;
                int job = JobEnum.BEGINNER.getJobId();
                Char nc = new Char(acc, name, 0, 0, job, (short) 0, gender, (byte) 0, face, hair, new int[]{face, hair});
                nc.setUserId(chr.getUser().getId());
                List<net.swordie.ms.client.character.keys.FuncKeyMap> keyMaps = new ArrayList<>();
                keyMaps.add(net.swordie.ms.client.character.keys.FuncKeyMap.getDefaultMapping(0));
                nc.setFuncKeyMap(keyMaps);
                nc.setQuickslotKeys(Arrays.asList(42, 82, 71, 73, 29, 83, 79, 81, 2, 3, 4, 5, 16, 17, 18, 19, 6, 7, 8,
                        9, 20, 30, 31, 32, 10, 11, 33, 34, 37, 38, 49, 50));
                charDao.saveOrUpdate(acc, nc);
                var cs = nc.getAvatarData().getCharacterStat();
                cs.setCharacterId(nc.getId());
                cs.setCharacterIdForLog(nc.getId());
                cs.setWorldIdForLog(acc.getWorldId());
                net.swordie.ms.client.jobs.JobManager.getJobById(job, nc).setCharCreationStats(nc);
                nc.setLocation(net.swordie.ms.ServerConstants.MAX_CHARACTERS);
                acc.addCharacter(nc);
                acc.applyCompletedAccountQuests(nc);
                charDao.saveOrUpdate(nc);
                log.info("[TestClass] made " + name + " (character " + nc.getId() + ") on " + chr.getUser().getName());
                chr.chatMessage(name + " made. Pick it on the character select screen.");
            } catch (Exception ex) {
                log.error("[TestClass] couldn't make " + name, ex);
                chr.chatMessage("Couldn't make " + name + ": " + ex.getMessage());
            }
        }
    }

    @Command(names = {"quickchar"}, requiredType = Tester)
    public static class QuickChar extends AdminCommand {
        // !quickchar <name>: which of your characters (this world) Quick Login enters
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !quickchar <character name>");
                return;
            }
            Char target = chr.getAccount().getCharacters().stream()
                    .filter(c -> c.getName().equalsIgnoreCase(args[1])).findFirst().orElse(null);
            if (target == null) {
                chr.chatMessage("No character " + args[1] + " on this account in this world.");
                return;
            }
            var e = new net.swordie.ms.client.QuickStart.Entry(chr.getAccount().getWorldId(), 0, target.getId());
            net.swordie.ms.client.QuickStart.saveLast(chr.getUser(), e);
            net.swordie.ms.client.QuickStart.saveFavourite(chr.getUser(), e);
            chr.chatMessage("Quick Login now enters " + target.getName() + ".");
        }
    }

    @Command(names = {"testclass", "testchar"}, requiredType = Tester)
    public static class TestClass extends AdminCommand {
        // !testclass <job id> [level]: a ready test character - job, level (default 260), the 5th/6th job unlocks,
        // every skill maxed and the active ones on keys
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !testclass <job id or name, e.g. 112 or HERO> [level, default 260]");
                return;
            }
            JobEnum job = null;
            for (JobEnum j : JobEnum.values()) {
                if (j.name().equalsIgnoreCase(args[1]) || String.valueOf(j.getJobId()).equals(args[1])) {
                    job = j;
                    break;
                }
            }
            if (job == null) {
                chr.chatMessage("Unknown job " + args[1]);
                return;
            }
            short id = (short) job.getJobId();
            int level = args.length > 2 ? Integer.parseInt(args[2]) : 260;
            Job.execute(chr, new String[]{"job", String.valueOf(id)});
            var ad = chr.getAvatarData();
            if (net.swordie.ms.constants.JobConstants.isZero(id) && ad.getZeroAvatarLook() == null) {
                // a Zero made with this command has no Beta look (creation builds it); same as Zero.setCharCreationStats
                var beta = ad.getAvatarLook().deepCopy();
                beta.setWeaponId(ItemConstants.MASSIVE_SHADOW);
                beta.setGender(1);
                beta.setFace(21290);
                beta.setHair(37623);
                beta.setZeroBetaLook(true);
                ad.setZeroAvatarLook(beta);
            }
            if (chr.getLevel() < level) {
                LevelUntil.execute(chr, new String[]{"leveluntil", String.valueOf(level)});
            }
            net.swordie.ms.client.character.LevelUnlockModule.check(chr);
            if (chr.getLevel() >= 200 && chr.getJobHandler() != null) {
                chr.getJobHandler().GiveVSkills(chr); // the class's starting V skills (a path's 5th job gives them)
            }
            MaxSkills.execute(chr, new String[]{"maxskills"});
            // any class weapon usable (Zero's and others have stat requirements); not a real build
            for (String st : new String[]{"str", "dex", "int", "luk"}) {
                SetStat.execute(chr, new String[]{"setstat", st, "1000"});
            }
            equipKit(chr);
            dropOtherPathSkills(chr);
            BindSkills.execute(chr, new String[]{"bindskills"});
            chr.chatMessage(String.format("Test character ready: job %d, level %d. Relog to see the weapon.",
                    chr.getJob(), chr.getLevel()));
        }

        // a reused test character keeps every earlier class's skills (and the client lists them): drop the 1st-4th
        // job skills of other paths; beginner, link/union/guild (7000-9999) and 5th/6th job skills stay
        private static void dropOtherPathSkills(Char chr) {
            List<Skill> drop = new ArrayList<>();
            for (Skill s : new ArrayList<>(chr.getSkills())) {
                int skillJob = s.getSkillId() / 10000;
                boolean classJob = (skillJob >= 100 && skillJob < 7000) || (skillJob >= 10000 && skillJob < 40000);
                if (classJob && !JobConstants.isBeginnerJob(skillJob) && !BindSkills.isOnPath(chr, skillJob)) {
                    drop.add(s);
                }
            }
            if (!drop.isEmpty()) {
                chr.removeSkills(drop);
                chr.chatMessage(String.format("Removed %d skills of other classes.", drop.size()));
            }
        }

        private static void deleteEquipped(Char chr, net.swordie.ms.client.character.items.Item item, int slot) {
            chr.getEquippedInventory().removeItem(item, true);
            chr.getAvatarData().getAvatarLook().removeItem(item.getItemId(), 0, item.isCash(), 0);
            chr.write(net.swordie.ms.connection.packet.WvsContext.inventoryOperation(true, false,
                    net.swordie.ms.enums.InventoryOperation.Remove, (short) -slot, (short) 0, 0, item));
        }

        // deletes one earlier kit weapon/secondary from the Equip tab
        private static void freeKitSlot(Char chr, net.swordie.ms.client.character.items.Inventory equipTab) {
            for (var item : new ArrayList<>(equipTab.getItems())) {
                if (net.swordie.ms.client.character.JobStarterKit.isKitEquip(item.getItemId())) {
                    equipTab.removeItem(item, true);
                    chr.write(net.swordie.ms.connection.packet.WvsContext.inventoryOperation(true, false,
                            net.swordie.ms.enums.InventoryOperation.Remove, (short) item.getBagIndex(), (short) 0, 0,
                            item));
                    return;
                }
            }
        }

        private static final Supplier<List<Integer>> TEST_OUTFIT = DataTable.cached("test-outfit",
                t -> t.rows().stream().map(r -> r.i("item")).toList());

        // the class's starter weapon and secondary (as @job gives) and the test outfit straight into their slots;
        // ammo to the inventory
        private static void equipKit(Char chr) {
            int gender = chr.getAvatarData().getCharacterStat().getGender();
            var kit = net.swordie.ms.client.character.JobStarterKit.getKit(chr.getJob());
            int late = net.swordie.ms.client.character.JobStarterKit.getSecondaryAt30(chr.getJob());
            if (late != 0 && chr.getLevel() >= 30) { // a 1st job can't wear it yet
                kit.add(net.swordie.ms.loaders.ItemData.getItemDeepCopy(late));
            }
            for (int id : TEST_OUTFIT.get()) {
                kit.add(ItemData.getItemDeepCopy(id));
            }
            // the previous class's secondary blocks every skill of the new one
            int shieldSlot = net.swordie.ms.client.character.items.BodyPart.Shield.getVal();
            boolean kitHasSecondary = kit.stream().anyMatch(i -> i != null
                    && net.swordie.ms.constants.ItemConstants.isEquip(i.getItemId())
                    && net.swordie.ms.constants.ItemConstants.getBodyPartFromItem(i.getItemId(), gender) == shieldSlot);
            if (!kitHasSecondary) {
                var oldSecondary = chr.getEquippedInventory().getItemBySlot(shieldSlot);
                if (oldSecondary != null) {
                    deleteEquipped(chr, oldSecondary, shieldSlot);
                }
            }
            var equipTab = chr.getInventoryByType(net.swordie.ms.enums.InvType.EQUIP);
            for (var item : kit) {
                if (item == null) {
                    continue;
                }
                int id = item.getItemId();
                if (!net.swordie.ms.constants.ItemConstants.isEquip(id)) {
                    try {
                        chr.addItemToInventory(id, item.getQuantity());
                    } catch (IllegalStateException e) {
                        chr.chatMessage(String.format("No room for %d (ammo): skipped.", id));
                    }
                    continue;
                }
                var equip = net.swordie.ms.loaders.ItemData.getEquipDeepCopy(id, false);
                if (equip == null) {
                    continue;
                }
                int slot = net.swordie.ms.constants.ItemConstants.getBodyPartFromItem(id, gender);
                // the piece passes through the Equip tab: make room first (old kit pieces go), else stop here with
                // the old piece still worn
                if (equipTab.isFull()) {
                    freeKitSlot(chr, equipTab);
                }
                if (equipTab.isFull()) {
                    chr.chatMessage(String.format("Equip tab is full: %d not given. Free one slot and run "
                            + "!testclass again.", id));
                    continue;
                }
                var equipped = chr.getEquippedInventory();
                var old = equipped.getItemBySlot(slot);
                if (old != null) {
                    deleteEquipped(chr, old, slot); // a test item: deleted, not put back in the bag
                }
                // the player's own equip path, so the look (what the client draws and checks) has it too
                chr.addItemToInventory(net.swordie.ms.enums.InvType.EQUIP, equip, false);
                short from = (short) equip.getBagIndex();
                chr.equip(equip, -slot);
                // the client moves items itself when a player equips; here it has to be told
                chr.write(net.swordie.ms.connection.packet.WvsContext.inventoryOperation(true, false,
                        net.swordie.ms.enums.InventoryOperation.Move, from, (short) -slot, 0, equip));
            }
        }
    }

    @Command(names = {"fillskills", "fillsp"}, requiredType = Tester)
    public static class FillSkills extends AdminCommand {
        // every skill of the character's job tiers at its max level (same as !maxskills)
        public static void execute(Char chr, String[] args) {
            MaxSkills.execute(chr, args);
            chr.chatMessage("Every skill of your job is at its max level.");
        }
    }

    // keyboard index -> key name: 1-0, Q-P, A-L, Z-M
    private static final int[] BIND_KEYS = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 30,
            31, 32, 33, 34, 35, 36, 37, 38, 44, 45, 46, 47, 48, 49, 50};
    private static final String[] BIND_NAMES = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "Q", "W", "E", "R",
            "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};

    // a skill the player casts: it costs something or has a cooldown
    private static boolean looksActive(SkillInfo si, int slv) {
        for (var stat : new net.swordie.ms.client.character.skills.SkillStat[]{
                net.swordie.ms.client.character.skills.SkillStat.mpCon,
                net.swordie.ms.client.character.skills.SkillStat.hpCon,
                net.swordie.ms.client.character.skills.SkillStat.forceCon,
                net.swordie.ms.client.character.skills.SkillStat.ppCon,
                net.swordie.ms.client.character.skills.SkillStat.epCon,
                net.swordie.ms.client.character.skills.SkillStat.cooltime,
                net.swordie.ms.client.character.skills.SkillStat.cooltimeMS}) {
            if (si.getValue(stat, slv) > 0) {
                return true;
            }
        }
        // not free attacks: Final Attack and the like are passives
        return false;
    }

    @Command(names = {"bindtest"}, requiredType = Tester)
    public static class BindTest extends AdminCommand {
        // !bindtest <skill ids...>: only these on 1-0, Q-P..., sent now (to find a skill the client won't keep)
        public static void execute(Char chr, String[] args) {
            for (var fkm : chr.getFuncKeyMap()) {
                for (int key : BIND_KEYS) {
                    fkm.putKeyBinding(key, (byte) 0, 0);
                }
                for (int i = 1; i < args.length && i - 1 < BIND_KEYS.length; i++) {
                    fkm.putKeyBinding(BIND_KEYS[i - 1], (byte) 1, Integer.parseInt(args[i]));
                }
            }
            chr.write(net.swordie.ms.connection.packet.FuncKeyMappedMan.funcKeyMappedManInit(chr.getFuncKeyMap(),
                    chr.getQuickslotKeys()));
            log.info("[TestClass] bindtest " + String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length)));
        }
    }

    @Command(names = {"bindskills"}, requiredType = Tester)
    public static class BindSkills extends AdminCommand {
        /** Whether skills of this skill job belong to the character's job path (same family is not enough: Arch Mage
         * I/L (222) and Bishop (232) are both 2xx). */
        static boolean isOnPath(Char chr, int skillJob) {
            int job = chr.getJob();
            return skillJob / 100 == job / 100
                    && (skillJob % 100 < 10 || (skillJob / 10 == job / 10 && skillJob <= job));
        }

        // the character's active skills on 1-0, Q-P, A-L, Z-M (newest job tier first), in every key preset
        public static void execute(Char chr, String[] args) {
            List<Skill> active = new ArrayList<>();
            for (Skill s : chr.getSkills()) {
                int skillJob = s.getSkillId() / 10000;
                // this class's skills (a test character keeps an earlier class's skills) plus 5th/6th job ones
                if (!isOnPath(chr, skillJob) && skillJob < 40000) {
                    continue;
                }
                // 5th/6th job skills: only this job's cores (a test character keeps earlier classes' ones)
                if (skillJob >= 40000 && net.swordie.ms.loaders.VCoreData.getPossibilitiesByJob(chr.getJob()).stream()
                        .noneMatch(v -> v.getSkillID() == s.getSkillId())) {
                    continue;
                }
                SkillInfo si = SkillData.getSkillInfoById(s.getSkillId());
                // old-style numbers (x0xxx/x9xxx: passives, addAttack triggers) stay off the keys: one the client
                // refuses makes it reset the whole preset to its defaults (and save those) on the next login
                int numberDigit = (s.getSkillId() / 1000) % 10;
                if (skillJob < 40000 && (numberDigit == 0 || numberDigit == 9)) {
                    continue;
                }
                // not isPsd/isPassiveSkill: v271 marks attacks linked to a passive (Raging Blow) that way
                if (s.getCurrentLevel() <= 0 || si == null || si.isInvisible() || si.getHyper() == 1
                        || !net.swordie.ms.client.character.keys.FuncKeyRules.isAllowed(1, s.getSkillId())
                        || !looksActive(si, s.getCurrentLevel())) {
                    continue;
                }
                active.add(s);
            }
            active.sort(Comparator.comparingInt((Skill s) -> -(s.getSkillId() / 10000)).thenComparingInt(Skill::getSkillId));
            // !bindskills <id> ...: those skills take the first keys (the list is longer than the keys for most classes)
            List<String> skipped = new ArrayList<>();
            for (int i = args.length - 1; i >= 1; i--) {
                int id;
                try {
                    id = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    skipped.add(args[i]);
                    continue;
                }
                Skill want = chr.getSkill(id);
                if (want == null || want.getCurrentLevel() <= 0) {
                    chr.chatMessage(String.format("Skill %d isn't learned, so it can't be bound.", id));
                    skipped.add(args[i]);
                    continue;
                }
                active.removeIf(s -> s.getSkillId() == id);
                active.add(0, want);
            }
            // skill keys left from earlier classes make the client refuse them ("current weapon"): clear every skill
            // key first; other key types (items, emotes, menus) stay
            for (var fkm : chr.getFuncKeyMap()) {
                for (var km : fkm.getKeymap()) {
                    if (km.getType() == 1) {
                        km.setType((byte) 0);
                        km.setVal(0);
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            int n = Math.min(active.size(), BIND_KEYS.length);
            for (int i = 0; i < n; i++) {
                for (var fkm : chr.getFuncKeyMap()) {
                    fkm.putKeyBinding(BIND_KEYS[i], (byte) 1, active.get(i).getSkillId());
                }
                sb.append(BIND_NAMES[i]).append('=').append(active.get(i).getSkillId()).append(' ');
            }
            // live: entries the client refuses are sent empty (FuncKeyRules), so the layout should load mid-game
            chr.write(net.swordie.ms.connection.packet.FuncKeyMappedMan.funcKeyMappedManInit(chr.getFuncKeyMap(),
                    chr.getQuickslotKeys()));
            // the character save doesn't write key maps (the key window saves its own): save them here; the client
            // shows them from the next login
            var fkmDao = (net.swordie.orm.dao.FuncKeyMapDao) net.swordie.orm.dao.SworDaoFactory
                    .getByClass(net.swordie.ms.client.character.keys.FuncKeyMap.class);
            fkmDao.saveOrUpdate(chr, chr.getFuncKeyMap());
            // keys then hold the class's own list, not the ids asked for: say which ids were left out
            log.info("[TestClass] " + chr.getName() + " keys: " + sb.toString().trim()
                    + (skipped.isEmpty() ? "" : " | not bound: " + String.join(" ", skipped)));
            chr.chatMessage(String.format("%d skills on keys (%d active in all).", n, active.size())
                    + (skipped.isEmpty() ? "" : " Not bound: " + String.join(" ", skipped)));
        }
    }

    /** !solerda [amount] [fragments]: Sol Erda straight into the HEXA window (default: full) plus Sol Erda Fragments. */
    @Command(names = {"solerda"}, requiredType = Tester)
    public static class SolErda extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            int max = HexaCoreData.getSolErdaSlotMax();
            int amount = args.length > 1 ? Integer.parseInt(args[1]) : max;
            int fragments = args.length > 2 ? Integer.parseInt(args[2]) : 0;
            HexaModule.addSolErda(chr, amount);
            if (fragments > 0) {
                chr.addItemToInventory(HexaConstants.SOL_ERDA_FRAGMENTS[0], fragments);
            }
            chr.chatMessage(String.format("Sol Erda %d/%d, Sol Erda Fragments %,d.", HexaModule.getSolErda(chr), max,
                    HexaModule.getFragments(chr)));
        }
    }

    /** !questinfo <id>: a quest's state and record, and the first requirement missing to start or hand it in. */
    @Command(names = {"questinfo", "qinfo"}, requiredType = Tester)
    public static class QuestInfoCmd extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !questinfo <quest id>");
                return;
            }
            int id = Integer.parseInt(args[1]);
            var qm = chr.getQuestManager();
            Quest q = qm.getQuestById(id);
            String name = StringData.getQuestStringById(id);
            chr.chatMessage(String.format("%d %s: %s, record \"%s\"", id, name == null ? "" : name,
                    q == null ? "not started" : q.getStatus(), q == null ? "" : q.getQRValue()));
            String start = qm.playerReasonCantStart(id);
            String end = qm.playerReasonCantComplete(id);
            chr.chatMessage("Start: " + (start == null ? "nothing missing" : start));
            chr.chatMessage("Hand in: " + (end == null ? "nothing missing" : end));
        }
    }

    /** !symbol <level> [exp] [item id]: sets the worn symbols' level and EXP (all of them, or the one item). */
    @Command(names = {"symbol", "symbols"}, requiredType = Tester)
    public static class SymbolLevel extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !symbol <level> [exp] [item id]");
                return;
            }
            int level = Integer.parseInt(args[1]);
            int exp = args.length > 2 ? Integer.parseInt(args[2]) : 1;
            int only = args.length > 3 ? Integer.parseInt(args[3]) : 0;
            int n = 0;
            for (Item item : chr.getEquippedInventory().getItems()) {
                boolean match = only == 0 || item.getItemId() == only;
                if (item instanceof Equip e && ItemConstants.isSymbol(e.getItemId()) && match) {
                    e.initSymbolStats(Math.max(1, Math.min(level, ItemConstants.getMaxSymbolLevel(e.getItemId()))), exp,
                            chr.getJob());
                    e.updateToChar(chr);
                    n++;
                }
            }
            chr.chatMessage(n + " worn symbol(s) set.");
        }
    }

    /** !maxv: every V Matrix core of the character's job at its max level (5th job needed). */
    @Command(names = {"maxv", "maxmatrix"}, requiredType = Tester)
    public static class MaxV extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (!chr.getQuestManager().hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST)) {
                chr.chatMessage("No 5th job yet: use !fifthjob first.");
                return;
            }
            chr.chatMessage(String.format("Maxed %d V Matrix cores.", V271Matrix.maxAll(chr)));
        }
    }

    @Command(names = {"hexanode"}, requiredType = Tester)
    public static class HexaNode extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 3) {
                chr.chatMessage("!hexanode <core id> <level>  (0 removes the node; relog to see it in the window)");
                return;
            }
            int coreId, level;
            try {
                coreId = Integer.parseInt(args[1]);
                level = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                chr.chatMessage("Core id and level must be numbers.");
                return;
            }
            int before = HexaModule.getNodeLevel(chr, coreId);
            HexaModule.setNodeLevel(chr, coreId, level);
            chr.chatMessage(String.format("HEXA node %d: %d -> %d. Relog to refresh the window.", coreId, before, HexaModule.getNodeLevel(chr, coreId)));
        }
    }

    @Command(names = {"maxskills"}, requiredType = Tester)
    public static class MaxSkills extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            List<Skill> list = new ArrayList<>();
            Set<Short> jobs = new HashSet<>();
            short job = chr.getJob();
            // giant hack, but it's for a command, so it's k
            if (JobConstants.isEvan(job)) {
                jobs.add((short) 2000);
                jobs.add((short) 2200);
                while (job >= 2210) {
                    jobs.add(job--);
                }
            } else if (JobConstants.isDualBlade(job)) { // Thief 1st job, then Blade Recruit up to this job
                jobs.add((short) JobEnum.THIEF.getJobId());
                for (short j = (short) JobEnum.BLADE_RECRUIT.getJobId(); j <= job; j++) {
                    jobs.add(j);
                }
            } else {
                if (job % 100 == 12 || job % 100 == 22 || job % 100 == 32 || job % 100 == 42) {
                    jobs.add(job);
                    jobs.add((short) (job - 1));
                    jobs.add((short) (job - 2));
                    jobs.add((short) ((job / 100) * 100));
                } else if (job % 100 == 11 || job % 100 == 21 || job % 100 == 31 || job % 100 == 41) {
                    jobs.add(job);
                    jobs.add((short) (job - 1));
                    jobs.add((short) ((job / 100) * 100));
                } else if (job % 100 == 10 || job % 100 == 20 || job % 100 == 30 || job % 100 == 40) {
                    jobs.add(job);
                    jobs.add((short) ((job / 100) * 100));
                } else {
                    jobs.add(job);
                }
                // paths whose 1st job isn't the branch's x00
                short first = (short) (JobConstants.isPathFinder(job) ? JobEnum.PATHFINDER_1.getJobId()
                        : JobConstants.isCannonShooter(job) ? JobEnum.PIRATE_CANNONEER.getJobId()
                        : JobConstants.isJett(job) ? JobEnum.JETT_1.getJobId()
                        : JobConstants.isDemonAvenger(job) ? JobEnum.DEMON_AVENGER_1.getJobId() : 0);
                if (first != 0) {
                    jobs.remove((short) ((job / 100) * 100));
                    jobs.add(first);
                }
            }
            for (short j : jobs) {
                for (Skill skill : SkillData.getSkillsByJob(j)) {
                    byte maxLevel = (byte) skill.getMaxLevel();
                    skill.setCurrentLevel(maxLevel);
                    skill.setMasterLevel(maxLevel);
                    list.add(skill);
                    chr.addSkill(skill);
                }
                if (list.size() > 0) {
                    chr.write(WvsContext.changeSkillRecordResult(list, true, false, false, false));
                }
            }
            int hexa = HexaModule.maxAllNodes(chr); // 6th job nodes too (level 260+ with the 5th job); Dapsy
            if (hexa > 0) {
                chr.chatMessage(String.format("Maxed %d HEXA skill nodes.", hexa));
            }
        }
    }

    @Command(names = {"lookup", "find"}, requiredType = Tester)
    public static class Lookup extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 3) {
                chr.chatMessage(Notice2, "Needs more args! <what to lookup> <id/(part of) name>");
                chr.chatMessage(Notice2, "Possible lookup types are: item, skill, mob, npc, map, quest, option (pot), job");
                return;
            }
            StringBuilder queryBuilder = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                queryBuilder.append(args[i].toLowerCase()).append(" ");
            }
            queryBuilder = new StringBuilder(queryBuilder.substring(0, queryBuilder.length() - 1));
            if (queryBuilder.length() < 3 && !Util.isNumber(queryBuilder.toString())) {
                chr.chatMessage("Please narrow your query by having a query of at least 3 characters");
                return;
            }

            var query = queryBuilder.toString();

            chr.chatMessage("Query: " + query);
            boolean isNumber = Util.isNumber(query);
            if ("job".equalsIgnoreCase(args[1])) {
                if (isNumber) {
                    var job = JobEnum.getJobById(Integer.parseInt(query));
                    if (job == null) {
                        chr.chatMessage("No job with id " + query);
                    } else {
                        chr.chatMessage("Job id %d: %s", query, job.toString());
                    }
                } else {
                    var jobs = JobEnum.getJobsByName(query);
                    if (jobs.isEmpty()) {
                        chr.chatMessage("No job found for query %s", query);
                    } else {
                        chr.chatMessage("Following jobs found: ");
                        jobs.forEach(chr::chatMessage);
                    }
                }
            } else if ("skill".equalsIgnoreCase(args[1])) {
                SkillStringInfo ssi;
                int id;
                if (isNumber) {
                    id = Integer.parseInt(query.toString());
                    ssi = StringData.getSkillStringById(id);
                    if (ssi == null) {
                        chr.chatMessage(WorldName, "Cannot find skill " + id);
                        return;
                    }
                    SkillInfo skillInfo = SkillData.getSkillInfoById(id);
                    chr.chatMessage(WorldName, "Name: " + ssi.getName());
                    chr.chatMessage(WorldName, "Desc: " + ssi.getDesc());
                    chr.chatMessage(WorldName, "h: " + ssi.getH());
                    chr.chatMessage(WorldName, "type: " + skillInfo.getType());
                } else {
                    Map<Integer, SkillStringInfo> map = StringData.getSkillStringByName(query.toString());
                    if (map.size() == 0) {
                        chr.chatMessage(WorldName, "No skills found for query " + query);
                    }
                    for (Map.Entry<Integer, SkillStringInfo> entry : map.entrySet()) {
                        id = entry.getKey();
                        ssi = entry.getValue();
                        SkillInfo si = SkillData.getSkillInfoById(id);
                        if (si != null) {
                            chr.chatMessage(WorldName, "Id: " + id);
                            chr.chatMessage(WorldName, "Name: " + ssi.getName());
                            chr.chatMessage(WorldName, "Desc: " + ssi.getDesc());
                            chr.chatMessage(WorldName, "h: " + ssi.getH());
                            chr.chatMessage(WorldName, "type: " + si.getType());
                        }
                    }
                }
            } else if ("option".equalsIgnoreCase(args[1]) || "itemoption".equalsIgnoreCase(args[2])) {
                List<ItemOption> ioList = ItemData.getItemOptionsByName(query.toString());
                for (ItemOption io : ioList) {
                    int id = io.getId();
                    int tier = id / 10000;  // 1 = Rare,  2 = Epic,  3 = Unique,  4 = Legendary
                    ChatType chatType;
                    String ioString = "";
                    if (tier > 0 && tier <= 4) {
                        boolean bonus = (id % (tier * 10000)) >= 2000;
                        if (bonus) {
                            ioString += "[Bonus] ";
                        }
                    }
                    switch (tier) {
                        case 1:
                            chatType = Notice2; // Rare
                            ioString += "(Rare) ";
                            break;
                        case 2:
                            chatType = GroupParty; // Epic
                            ioString += "(Epic) ";
                            break;
                        case 3:
                            chatType = Notice; // Unique
                            ioString += "(Unique) ";
                            break;
                        case 4:
                            chatType = Whisper; // Legendary
                            ioString += "(Legendary) ";
                            break;
                        default:
                            chatType = WorldName; // Other
                            ioString += "(Other) ";
                            break;
                    }
                    ioString += io.getString();
                    chr.chatMessage(chatType, "Id: " + id);
                    chr.chatMessage(chatType, "Name: " + ioString);
                }
            } else {
                String queryType = args[1].toLowerCase();
                int id;
                String name;
                if (isNumber) {
                    id = Integer.parseInt(query.toString());
                    switch (queryType) {
                        case "item":
                            name = StringData.getItemStringById(id);
                            break;
                        case "quest":
                            name = StringData.getQuestStringById(id);
                            break;
                        case "mob":
                            name = StringData.getMobStringById(id);
                            break;
                        case "npc":
                            name = StringData.getNpcStringById(id);
                            break;
                        case "map":
                        case "field":
                            name = StringData.getMapStringById(id);
                            break;
                        default:
                            chr.chatMessage("Unknown query type " + queryType);
                            return;
                    }
                    if (name == null) {
                        chr.chatMessage(WorldName, "Cannot find " + queryType + " " + id);
                        return;
                    }
                    chr.chatMessage(WorldName, "Name: " + name);
                } else {
                    Map<Integer, String> map;
                    switch (queryType) {
                        case "equip":
                            map = StringData.getItemStringByName(query.toString(), false);
                            Set<Integer> nonEquips = new HashSet<>();
                            for (int itemId : map.keySet()) {
                                if (!ItemConstants.isEquip(itemId)) {
                                    nonEquips.add(itemId);
                                }
                            }
                            for (int itemId : nonEquips) {
                                map.remove(itemId);
                            }
                            break;
                        case "item":
                            map = StringData.getItemStringByName(query.toString(), false);
                            break;
                        case "quest":
                            map = StringData.getQuestStringByName(query.toString());
                            break;
                        case "mob":
                            map = StringData.getMobStringByName(query.toString());
                            break;
                        case "npc":
                            map = StringData.getNpcStringByName(query.toString());
                            break;
                        case "map":
                            map = StringData.getMapStringByName(query.toString());
                            break;
                        default:
                            chr.chatMessage("Unknown query type " + queryType);
                            return;
                    }
                    if (map.size() == 0) {
                        chr.chatMessage(WorldName, "No " + queryType + "s found for query " + query);
                        return;
                    }
                    TreeMap<Integer, String> sortedMap = new TreeMap<>(map);
                    for (Map.Entry<Integer, String> entry : sortedMap.entrySet()) {
                        id = entry.getKey();
                        name = entry.getValue();
                        if (queryType.equalsIgnoreCase("item")) {
                            Item item = ItemData.getEquipDeepCopy(id, false);
                            if (item == null) {
                                item = ItemData.getItemDeepCopy(id);
                            }
                            if (item == null) {
                                continue;
                            }
                        }
                        chr.chatMessage(WorldName, "Id: " + id);
                        chr.chatMessage(WorldName, "Name: " + name);
                    }
                }
            }
        }
    }

    @Command(names = {"getprojectiles", "projectiles"}, requiredType = Tester)
    public static class GetProjectiles extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            int[] projectiles = new int[]{
                    ItemConstants.SUBI_THROWING_STARS,
                    ItemConstants.ARROW_FOR_BOW,
                    ItemConstants.ARROW_FOR_CROSSBOW,
                    ItemConstants.BULLET
            };
            for (int projectile : projectiles) {
                Item item = ItemData.getItemDeepCopy(projectile);
                chr.addItemToInventory(item.getInvType(), item, false);
                item.setQuantity(1000);
                chr.write(WvsContext.inventoryOperation(true, false,
                        Add, (short) item.getBagIndex(), (byte) -1, 0, item));
            }
        }
    }

    @Command(names = {"mesos", "money"}, requiredType = Tester)
    public static class Mesos extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            long mesos = Long.parseLong(args[1]);
            chr.addMoney(mesos);
        }
    }

    @Command(names = {"nx", "setnx"}, requiredType = Tester)
    public static class NxCommand extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            int nx = Integer.parseInt(args[1]);
            chr.addNx(nx);
        }
    }


    @Command(names = {"goto"}, requiredType = Tester)
    public static class GoTo extends AdminCommand {
        /** resources/data/goto-maps.tsv; a HashMap so the location list keeps its old order */
        private static final java.util.function.Supplier<Map<String, Integer>> GOTO_MAPS =
                net.swordie.ms.loaders.DataTable.cached("goto-maps", t -> {
                    HashMap<String, Integer> m = new HashMap<>();
                    for (var r : t.rows()) {
                        m.put(r.str("name").trim(), r.i("map"));
                    }
                    return Collections.unmodifiableMap(m);
                });

        public static void execute(Char chr, String[] args) {

            Map<String, Integer> gotomaps = GOTO_MAPS.get();

            if (args.length == 1) {
                chr.chatMessage(Notice2, "List of locations: " + gotomaps.keySet());
            } else if (gotomaps.containsKey(args[1])) {
                Field toField = chr.getClient().getChannelInstance().getField(gotomaps.get(args[1]));
                Portal portal = chr.getField().getInfo().getDefaultPortal();
                chr.warp(toField, portal);
            } else if (args[1].equals("locations")) {
                chr.chatMessage(Notice2, "Use !goto <location>");
                StringBuilder sb = new StringBuilder();
                for (String s : gotomaps.keySet()) {
                    sb.append(s).append(",  ");
                }
                chr.chatMessage(Notice2, sb.substring(0, sb.length() - 2));
            } else {
                chr.chatMessage(Notice2, "Map does not exist.");
            }
        }
    }

    @Command(names = {"clearcache"}, requiredType = Admin)
    public static class ClearCache extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            chr.getScriptManager().dispose(false);
            Server.getInstance().clearCache();
            chr.chatMessage("Cache has been cleared.");
        }
    }

    @Command(names = {"reloadscript", "reloadscripts", "rs"}, requiredType = Admin)
    public static class ReloadScripts extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            ScriptManagerImpl.clear();
            chr.chatMessage("Scripts have been cleared.");
        }
    }

    @Command(names = {"savemap"}, requiredType = Tester)
    public static class SaveMap extends AdminCommand {
        private static HashMap<String, Integer> quickmaps = new HashMap<>();

        public static void execute(Char chr, String[] args) {
            int mapid = chr.getFieldID();
            if (args.length < 1 && !args[1].equalsIgnoreCase("list")) {
                chr.chatMessage(BlackOnWhite, "Incorrect Syntax: !SaveMap <save/go> <key>");
                chr.chatMessage(BlackOnWhite, "To see the list of saved maps, use: !SaveMap list");
            }
            if (args[1].equalsIgnoreCase("save")) {
                String key = args[2];
                quickmaps.put(key, mapid);
                chr.chatMessage(BlackOnWhite, "[SaveMap] Map: " + mapid + " has been saved as key '" + key + "'.");
            } else if (args[1].equalsIgnoreCase("go")) {
                String key = args[2];
                if (quickmaps.get(key) == null) {
                    chr.chatMessage(BlackOnWhite, "[SaveMap] There is no map saved as key '" + args[2] + "'.");
                    return;
                }
                Field toField = chr.getOrCreateFieldForGmWarp((quickmaps.get(key)));
                Portal portal = chr.getField().getInfo().getDefaultPortal();
                chr.warp(toField, portal);
            } else if (args[1].equalsIgnoreCase("list")) {
                Set keys = quickmaps.keySet();
                chr.chatMessage(BlackOnWhite, "[SaveMap] " + quickmaps.size() + " saved maps.");
                for (Object maps : keys) {
                    chr.chatMessage(BlackOnWhite, "[SaveMap] Stored map: " + quickmaps.get(maps) + " as '" + maps + "'.");
                }
            } else {
                chr.chatMessage(BlackOnWhite, "Incorrect Syntax: !SaveMap <save/go> <key>");
                chr.chatMessage(BlackOnWhite, "To see the list of saved maps, use: !SaveMap list");
            }
        }
    }

    @Command(names = {"clearinv"}, requiredType = Tester)
    public static class ClearInv extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage(Notice2, "Syntax Error: !ClearInv <Inventory Type> <Start Index> <End Index>");
                return;
            }
            InvType invType = InvType.getInvTypeByString(args[1]);
            if (invType == null) {
                chr.chatMessage("Please fill in a correct inventory type:  equip / use / etc / setup / cash");
                return;
            }
            short startIndex = Short.parseShort(args[2]);
            short endIndex = Short.parseShort(args[3]);
            for (int i = startIndex; i < endIndex; i++) {
                Item removeItem = chr.getInventoryByType(invType).getItemBySlot(i);
                if (removeItem != null) {
                    chr.consumeItem(removeItem, removeItem.getQuantity());
                }
            }
            chr.dispose();
        }
    }

    @Command(names = {"mobinfo"}, requiredType = Player)
    public static class MobInfo extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            Rect rect = new Rect(
                    chr.getPosition().deepCopy().getX() - 200,
                    chr.getPosition().deepCopy().getY() - 200,
                    chr.getPosition().deepCopy().getX() + 200,
                    chr.getPosition().deepCopy().getY() + 200
            );
            Mob mob = chr.getField().getMobs().stream().filter(m -> rect.hasPositionInside(m.getPosition())).findFirst().orElse(null);
            if (mob == null) {
                chr.chatMessage(SpeakerChannel, "No mob within 200 px.");
                return;
            }
            Char controller = chr.getField().getLifeToControllers().getOrDefault(mob, null);
            if (mob != null) {
                chr.chatMessage(SpeakerChannel, String.format("Object ID: %s | Template/Mob ID: %s | Level: %d | HP: %s/%s " +
                                        "| MP: %s/%s | Left: %s | PDR: %s | MDR: %s " +
                                        "| Controller: %s | Exp : %s | NX: %s",
                                NumberFormat.getNumberInstance(Locale.US).format(mob.getObjectId()),
                                NumberFormat.getNumberInstance(Locale.US).format(mob.getTemplateId()),
                                mob.getLevel(),
                                NumberFormat.getNumberInstance(Locale.US).format(mob.getHp()),
                                NumberFormat.getNumberInstance(Locale.US).format(mob.getMaxHp()),
                                NumberFormat.getNumberInstance(Locale.US).format(mob.getMp()),
                                NumberFormat.getNumberInstance(Locale.US).format(mob.getMaxMp()),
                                mob.isLeft(),
                                mob.getPdr(),
                                mob.getMdr(),
                                controller == null ? "null" : chr.getName(),
                                mob.getForcedMobStat().getExp(),
                                mob.getNxDropAmount()
                        )
                );
            } else {
                chr.chatMessage(SpeakerChannel, "Could not find mob.");
            }
        }
    }

    @Command(names = {"mobskillinfo", "mobattackinfo"}, requiredType = Tester)
    public static class CheckMobSkillInfo extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !mobskillinfo <mobTemplateId>");
                return;
            }

            var mob = MobData.getMobDeepCopyById(Integer.parseInt(args[1]));
            if (mob == null) {
                chr.chatMessage("Could not find mob " + args[1]);
                return;
            }

            chr.chatMessage("--Skills--");
            for (var mobSkill : mob.getSkills()) {
                chr.chatMessage("[%s] MobSkillID: %s (%s), slv: %s, doable: %s", mobSkill.getSkillIdx(), MobSkillID.getMobSkillIDByVal(mobSkill.getSkillID()), mobSkill.getSkillID(), mobSkill.getLevel(), mob.canUseSkill(mobSkill));
            }

            chr.chatMessage("--Attacks--");
            for (var attack : mob.getMobInfo().getAttacks()) {
                chr.chatMessage("[%s] DamR: %s", attack.getSkillIdx(), attack.getFixDamR());
            }
        }
    }


    @Command(names = {"npcinfo"}, requiredType = Player)
    public static class NpcInfo extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            Rect rect = new Rect(
                    chr.getPosition().deepCopy().getX() - 200,
                    chr.getPosition().deepCopy().getY() - 200,
                    chr.getPosition().deepCopy().getX() + 200,
                    chr.getPosition().deepCopy().getY() + 200
            );
            Npc npc = chr.getField().getNpcs().stream().filter(m -> rect.hasPositionInside(m.getPosition())).findFirst().orElse(null);
            Char controller = chr.getField().getLifeToControllers().getOrDefault(npc, null);
            if (npc != null) {
                chr.chatMessage(SpeakerChannel, String.format("Npc ID: %s | Template ID: %s | Left: %s | Pos: (%d, %d) " +
                                        "| Controller: %s | Script: %s",
                                NumberFormat.getNumberInstance(Locale.US).format(npc.getObjectId()),
                                NumberFormat.getNumberInstance(Locale.US).format(npc.getTemplateId()),
                                npc.isLeft(),
                                npc.getPosition().getX(),
                                npc.getPosition().getY(),
                                controller == null ? "null" : chr.getName(),
                                npc.getScripts().size() > 0 ? npc.getScripts().get(0) : "null"
                        )
                );
            } else {
                chr.chatMessage(SpeakerChannel, "Could not find mob.");
            }
        }
    }

    @Command(names = {"reactorinfo"}, requiredType = Player)
    public static class ReactorInfo extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            Rect rect = new Rect(
                    chr.getPosition().deepCopy().getX() - 200,
                    chr.getPosition().deepCopy().getY() - 200,
                    chr.getPosition().deepCopy().getX() + 200,
                    chr.getPosition().deepCopy().getY() + 200
            );
            Reactor reactor = chr.getField().getReactors().stream().filter(m -> rect.hasPositionInside(m.getPosition())).findFirst().orElse(null);
            if (reactor != null) {
                var reactorInfo = ReactorData.getReactorInfoByID(reactor.getTemplateId());
                chr.chatMessage(SpeakerChannel, String.format("Reactor ID: %s | Template ID: %s | Left: %s | Pos: (%d, %d) " +
                                        "| Script: %s",
                                NumberFormat.getNumberInstance(Locale.US).format(reactor.getObjectId()),
                                NumberFormat.getNumberInstance(Locale.US).format(reactor.getTemplateId()),
                                reactor.isLeft(),
                                reactor.getPosition().getX(),
                                reactor.getPosition().getY(),
                                reactorInfo == null ? "No info" : reactorInfo.getAction()
                        )
                );
            } else {
                chr.chatMessage(SpeakerChannel, "Could not find mob.");
            }
        }
    }

    @Command(names = {"completequest"}, requiredType = Tester)
    public static class CompleteQuest extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length <= 1) {
                chr.chatMessage("Usage: !completequest <startquestid> <endquestid?>");
                return;
            }

            int startId = Integer.parseInt(args[1]);
            int endId = startId;
            if (args.length >= 3) {
                endId = Integer.parseInt(args[2]);
            }

            for (int questId = startId; questId <= endId; questId++) {
                chr.getQuestManager().completeQuest(questId);
                chr.chatMessage("Completed quest " + questId);
            }
        }
    }

    @Command(names = {"removequest"}, requiredType = Tester)
    public static class RemoveQuest extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            chr.getQuestManager().removeQuest(Integer.parseInt(args[1]));
            chr.chatMessage("Removed quest " + args[1]);
        }
    }

    @Command(names = {"sethonor", "honor"}, requiredType = Tester)
    public static class SetHonor extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage(SpeakerChannel, "Format: !sethonor <honor exp>");
                return;
            }
            int honor = Integer.parseInt(args[1]);
            chr.setHonorExp(honor);
            chr.write(WvsContext.characterHonorExp(honor));
        }
    }

    @Command(names = {"questmobs"}, requiredType = Tester)
    public static class QuestMobs extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage(SpeakerChannel, "Format: !questmobs <quest id> [count]: sets the kill counts of a quest "
                        + "in progress (default: what it needs)");
                return;
            }
            var q = chr.getQuestManager().getQuestById(Integer.parseInt(args[1]));
            if (q == null || !chr.getQuestManager().hasQuestInProgress(q.getQRKey())) {
                chr.chatMessage(SpeakerChannel, "Quest " + args[1] + " isn't in progress.");
                return;
            }
            for (var req : q.getMobReqs()) {
                req.setCurrentCount(args.length > 2 ? Integer.parseInt(args[2]) : req.getRequiredCount());
            }
            chr.write(WvsContext.message(net.swordie.ms.connection.packet.model.MessagePacket.questRecordMessage(q)));
            chr.chatMessage(SpeakerChannel, "Kill counts of quest " + args[1] + " set.");
        }
    }

    @Command(names = {"startquest"}, requiredType = Tester)
    public static class StartQuest extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage(SpeakerChannel, "Format: !startquest <quest id>");
                return;
            }
            Quest q = QuestData.createQuestFromId(Integer.parseInt(args[1]));
            if (q != null) {
                chr.getQuestManager().addQuest(q);
                chr.chatMessage("Started quest " + args[1]);
            } else {
                chr.chatMessage("Could not find quest with id " + args[1] + ", but still adding it.");
                chr.getScriptManager().startQuestNoCheck(Integer.parseInt(args[1]));
            }
        }
    }

    @Command(names = {"bypassskillcd", "ignoreskillcd", "bypasskillcd", "cd"}, requiredType = Tester)
    public static class BypassSkillCD extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            chr.setSkillCDBypass(!chr.hasSkillCDBypass());
            if (chr.hasSkillCDBypass()) {
                for (var skillId : chr.getSkillCoolTimes().keySet()) {
                    chr.resetSkillCoolTime(skillId);
                }
            }
            chr.chatMessage(Notice2, "Skill Cooldown bypass: " + chr.hasSkillCDBypass());
            chr.dispose();
        }
    }

    @Command(names = {"shop"}, requiredType = Tester)
    public static class Shop extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            int shopId = LUNA_NPC;
            if (args.length > 1) {
                shopId = Integer.parseInt(args[1]);
            }
            chr.getScriptManager().openShop(shopId);
        }
    }

    @Command(names = {"dc"}, requiredType = GameMaster)
    public static class dc extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage(SpeakerChannel, "Not enough args! Use !dc <name>");
                return;
            }
            String name = args[1];
            Char dcChr = Server.getInstance().getWorldById(chr.getClient().getWorldId()).getCharByName(name);
            if (dcChr == null) {
                chr.chatMessage(SpeakerChannel, "target can't be found in world");
                return;
            }
            dcChr.write(WvsContext.returnToTitle());

            chr.chatMessage(SpeakerChannel, String.format("Character %s has been disconnected.", name));


        }
    }

    @Command(names = {"ban"}, requiredType = GameMaster)
    public static class Ban extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 5) {
                chr.chatMessage(SpeakerChannel, "Not enough args! Use !ban <name> <amount> <min/hour/day/week/month/year> <reason>");
                return;
            }
            String name = args[1];
            int amount = Integer.parseInt(args[2]);
            String amountType = args[3].toLowerCase();
            StringBuilder builder = new StringBuilder();
            for (int i = 4; i < args.length; i++) {
                builder.append(args[i] + " ");
            }
            String reason = builder.toString();
            reason = reason.substring(0, reason.length() - 1); // gets rid of the last space
            if (reason.length() > 255) {
                chr.chatMessage(SpeakerChannel, "That ban reason is too long.");
                return;
            }
            Char banChr = Server.getInstance().getWorldById(chr.getClient().getWorldId()).getCharByName(name);
            boolean online = true;
            if (banChr == null) {
                online = false;
                banChr = charDao.getByNameAndWorld(name, chr.getAccount().getWorldId());
                if (banChr == null) {
                    chr.chatMessage(SpeakerChannel, "Could not find that character.");
                    return;
                }
            }
            //Check if user is online somewhere in the server (could be char select too)
            User banUser = Server.getInstance().getUserById(banChr.getUserId());

            if (banUser == null) {
                online = false;
                banUser = userDao.getById(banChr.getUserId());
                if (banUser == null) {
                    chr.chatMessage("Could not find that user.");
                    return;
                }
            }

            LocalDateTime banDate = LocalDateTime.now();
            switch (amountType) {
                case "min":
                case "mins":
                    banDate = banDate.plusMinutes(amount);
                    break;
                case "h":
                case "hour":
                case "hours":
                    banDate = banDate.plusHours(amount);
                    break;
                case "d":
                case "day":
                case "days":
                    banDate = banDate.plusDays(amount);
                    break;
                case "w":
                case "week":
                case "weeks":
                    banDate = banDate.plusWeeks(amount);
                    break;
                case "month":
                case "months":
                    banDate = banDate.plusMonths(amount);
                    break;
                case "y":
                case "year":
                case "years":
                    banDate = banDate.plusYears(amount);
                    break;
                default:
                    chr.chatMessage(SpeakerChannel, String.format("Unknown date type %s", amountType));
                    break;
            }
            banUser.setBanExpireDate(FileTime.fromDate(banDate));
            banUser.setBanReason(reason);
            banUser.getOffenseManager().addOffense(reason, chr.getId());
            chr.chatMessage(SpeakerChannel, String.format("Character %s has been banned. Expire date: %s", name, banDate));

            if (online) {
                banChr.write(WvsContext.returnToTitle());
            } else {
                userDao.saveOrUpdate(banUser, null);
            }

            if (banUser.getClient() != null) {
                //If user is online on another character
                if (banUser.getClient().getAccount().getCurrentChr() != null) {
                    banUser.getClient().getAccount().getCurrentChr().write(WvsContext.returnToTitle());
                } else {
                    //If user is online on char select
                    banUser.getClient().write(WvsContext.returnToTitle());
                }
            }
        }
    }

    @Command(names = {"killmobs"}, requiredType = Tester)
    public static class KillMobs extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !killmobs <true/false (enable drops for killed mobs)> (<templateId>)");
                return;
            }

            Integer templateId = null;
            if (args.length >= 3) {
                templateId = Integer.parseInt(args[2]);
            }

            List<Mob> mobs = new ArrayList<>(chr.getField().getMobs());
            for (Mob mob : mobs) {
                if (templateId == null || templateId == mob.getTemplateId()) {
                    mob.die(Boolean.parseBoolean(args[1]));
                }
            }
        }
    }

    @Command(names = {"damagemobs"}, requiredType = Tester)
    public static class DamageMobs extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !damagemobs <damage>");
            }
            List<Mob> mobs = new ArrayList<>(chr.getField().getMobs());
            for (Mob mob : mobs) {
                mob.damage(chr, Long.parseLong(args[1]));
            }
        }
    }

    @Command(names = {"mobstat"}, requiredType = Tester)
    public static class MobStatTest extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            List<Mob> mobs = new ArrayList<>(chr.getField().getMobs());
            if (mobs.size() > 0) {
                Mob mob = mobs.get(0);
                MobTemporaryStat mts = mob.getTemporaryStat();
                Option o = new Option();
                o.nOption = 1000;
                o.rOption = 145;
                o.slv = 1;
                o.tOption = 5;

                o.wOption = 1000;

                o.mOption = 1000;
                o.bOption = 1000;
                o.rOption = 1000;
                mts.addMobSkillOptionsAndBroadCast(MobStat.PCounter, o);
            } else {
                chr.chatMessage("Could not find a mob.");
            }
        }
    }

    @Command(names = {"fp", "findportal"}, requiredType = Tester)
    public static class FP extends AdminCommand { // FindPortal
        public static void execute(Char chr, String[] args) {
            if (args.length < 1) {
                chr.chatMessage(SpeakerChannel, "Invalid args. Use !findportal <id/name>");
                return;
            }
            Field field = chr.getField();
            Portal portal;
            String query = args[1];
            if (Util.isNumber(query)) {
                portal = field.getInfo().getPortalByID(Integer.parseInt(query));
            } else {
                portal = field.getInfo().getPortalByName(query);
            }
            if (portal == null) {
                chr.chatMessage(SpeakerChannel, "Was not able to find portal " + query);
                return;
            }
            chr.chatMessage(SpeakerChannel, "Portal Name: " + portal.getName());
            chr.chatMessage(SpeakerChannel, "Portal ID: " + NumberFormat.getNumberInstance(Locale.US).format(portal.getId()));
            chr.chatMessage(SpeakerChannel, "Portal target map: " + NumberFormat.getNumberInstance(Locale.US).format(portal.getTargetMapId()));
            chr.chatMessage(SpeakerChannel, "Portal position: " + portal.getX() + ", " + portal.getY());
            chr.chatMessage(SpeakerChannel, "Portal script: " + portal.getScript());
            chr.chatMessage(SpeakerChannel, ".");
            log.info(portal.getScript());
        }
    }

    @Command(names = {"showbuffs"}, requiredType = Tester)
    public static class showBuffs extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            Set<Integer> buffs = new HashSet<>();
            for (List<Option> options : tsm.getCurrentStats().values()) {
                for (Option o : options) {
                    if (o.rOption != 0) {
                        buffs.add(o.rOption);
                    } else {
                        buffs.add(o.rOption);
                    }
                }
            }
            StringBuilder sb = new StringBuilder("Current buffs: ");
            for (int id : buffs) {
                String skillName = StringData.getSkillStringById(id) != null ? StringData.getSkillStringById(id).getName() : "Unknown Skill ID";
                sb.append(skillName).append(" (").append(id).append("), ");
            }
            chr.chatMessage(sb.toString().substring(0, sb.toString().length() - 2));
            sb = new StringBuilder("CTS: ");
            for (CharacterTemporaryStat cts : tsm.getCurrentStats().keySet()) {
                sb.append(cts.toString()).append(", ");
            }
            chr.chatMessage(sb.toString().substring(0, sb.toString().length() - 2));
        }
    }


    @Command(names = {"showfh", "showfootholds"}, requiredType = Tester)
    public static class ShowFootholds extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                Field field = chr.getField();
                int i = 0;
                for (Foothold fh : field.getInfo().getFootholds().values()) {
                    Drop drop1 = new Drop(0, 10);
                    drop1.setPosition(new Position(fh.getX1(), fh.getY1()));
                    drop1.setFh(fh.getId());
                    Drop drop2 = new Drop(0, 100);
                    drop2.setPosition(new Position(fh.getX2(), fh.getY2()));
                    drop2.setFh(fh.getId());
                    field.drop(drop1, drop1.getPosition());
                    field.drop(drop2, drop2.getPosition());
                }
            } else {
                var name = args[1];
                var position = chr.getPosition();
                if (args.length > 2) {
                    position.setX(Integer.parseInt(args[2]));
                    position.setY(Integer.parseInt(args[3]));
                }
                chr.write(FieldPacket.footholdAppear(name, true, position));
            }

        }
    }


    @Command(names = {"lookupreactor", "reactors"}, requiredType = Tester)
    public static class LookupReactor extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            chr.getField().getReactors().forEach(reactor -> chr.chatMessage(reactor.toString()));
        }
    }

    @Command(names = {"spawnreactor", "reactor"}, requiredType = Tester)
    public static class SpawnReactor extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !reactor <id>");
                return;
            }
            var reactor = ReactorData.getReactorByID(Integer.parseInt(args[1]));
            reactor.setPosition(chr.getPosition().deepCopy());
            reactor.setFh(chr.getFoothold());
            chr.getField().spawnLife(reactor, chr);
        }
    }

    @Command(names = {"chuc", "starforce", "sf"}, requiredType = Tester)
    public static class StarForceEquip extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 3) {
                chr.chatMessage("Not enough args! Usage: !sf <equip position in inventory> <star force>");
                return;
            }
            Item item = chr.getEquipInventory().getItemBySlot(Short.parseShort(args[1]));
            if (item == null) {
                chr.chatMessage("No item found in slot " + args[1]);
                return;
            }
            Equip equip = (Equip) item;
            equip.setChuc(Short.parseShort(args[2]));
            equip.updateToChar(chr);
        }
    }

    @Command(names = {"openui", "ui"}, requiredType = Tester)
    public static class OpenUI extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Needs a UI id.");
                return;
            }
            chr.write(FieldPacket.openUI(Integer.parseInt(args[1])));
        }
    }

    @Command(names = {"af", "setaf"}, requiredType = Tester)
    public static class SetAF extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 3) {
                chr.chatMessage("!af <equip position> <amount>");
                return;
            }
            Equip equip = (Equip) chr.getEquipInventory().getItemBySlot(Short.parseShort(args[1]));
            equip.setArc(Short.parseShort(args[2]));
            equip.updateToChar(chr);
        }
    }

    @Command(names = {"script"}, requiredType = Tester)
    public static class StartScriptTest extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 3) {
                chr.chatMessage("!script <type> <name>");
                return;
            }
            ScriptType st = null;
            for (ScriptType type : ScriptType.values()) {
                if (type.toString().equalsIgnoreCase(args[1])) {
                    st = type;
                    break;
                }
            }
            if (st == null) {
                StringBuilder str = new StringBuilder();
                for (ScriptType t : ScriptType.values()) {
                    str.append(t.toString()).append(", ");
                }
                String res = str.toString().substring(0, str.length() - 2);
                chr.chatMessage(String.format("Unknown script type %s, known types: %s", args[1], res));
                return;
            }
            chr.getScriptManager().startScript(0, args[2], st);
        }
    }

    @Command(names = {"inspect"}, requiredType = Admin)
    public static class getEquippedInv extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            // best accidental shutdown prevention system ever invented
            if (args.length < 2) {
                chr.chatMessage("!inspect <TargetcharName>");
                return;
            }
            String name = args[1];
            Char targetChr = Server.getInstance().getWorldById(chr.getClient().getWorldId()).getCharByName(name);
            boolean online = true;
            if (targetChr == null) {
                targetChr = charDao.getByNameAndWorld(name, chr.getAccount().getWorldId());
                if (targetChr == null) {
                    chr.chatMessage(SpeakerChannel, "Could not find that character.");
                    return;
                }
            }
            User targetUser = targetChr.getUser();
            if (targetUser == null) {
                targetUser = userDao.getById(chr.getUserId());
            }
            Account targetAccount = targetChr.getAccount();
            if (targetAccount == null) {
                targetAccount = accountDao.getByCharId(chr.getId());
            }
            Map<String, Object> customBindings = new HashMap<String, Object>();
            customBindings.put("chr", targetChr);
            customBindings.put("acc", targetAccount);
            chr.getScriptManager().startScript(2007, "adminNpc", ScriptType.Npc, customBindings);
        }
    }

    @Command(names = {"shutdown"}, requiredType = Admin)
    public static class ShutdownServer extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            // best accidental shutdown prevention system ever invented
            if (args.length < 3 || !args[2].equalsIgnoreCase("YESSHUTDOWN")) {
                chr.chatMessage("no");
                return;
            }
            Server server = Server.getInstance();
            int mins = Integer.parseInt(args[1]);
            // no instant stop while other players are on
            int others = net.swordie.ms.world.RestartManager.humansOnline()
                    - (net.swordie.ms.ext.ServerHooks.isAutomated(chr) ? 0 : 1);
            if (mins <= 0 && others > 0) {
                chr.chatMessage(others + " other player(s) online: give at least 1 minute of warning.");
                return;
            }
            server.shutdown(mins);
        }
    }

    @Command(names = {"cancelshutdown"}, requiredType = Admin)
    public static class CancelShutdown extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            Server server = Server.getInstance();
            server.cancelShutdown();
        }
    }

    @Command(names = {"broadcast", "br"}, requiredType = GameMaster)
    public static class BroadcastMsg extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !broadcast <msg>");
                return;
            }
            StringBuilder sb = new StringBuilder(String.format("[GM %s]:", chr.getName()));
            for (int i = 1; i < args.length; i++) {
                sb.append(" ").append(args[i]);
            }
            chr.getWorld().broadcastPacket(UserLocal.chatMsg(Notice, sb.toString()));
        }
    }

    @Command(names = {"skillinfo", "skillinfomode", "si"}, requiredType = Tester)
    public static class EnterSkillInfoMode extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            chr.setSkillInfoMode(!chr.isSkillInfoMode());
            chr.chatMessage(AdminChat, "skill info: " + chr.isSkillInfoMode());
        }
    }

    @Command(names = {"debug", "debugmode"}, requiredType = Tester)
    public static class DebugMode extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            chr.setDebugMode(!chr.isDebugMode());
            chr.chatMessage(AdminChat, "Debug mode: " + chr.isDebugMode());
        }
    }

    @Command(names = {"tp", "teleport"}, requiredType = Tester)
    public static class Teleport extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 3) {
                chr.chatMessage("Usage: !tp <x> <y>");
                return;
            }
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            chr.write(FieldPacket.teleport(new Position(x, y), chr));
        }
    }

    @Command(names = {"cleardrops", "cleardrop", "removedrops", "drops"}, requiredType = Tester)
    public static class ClearDrops extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            Field field = chr.getField();
            Set<Drop> removeDrops = new HashSet<>(field.getDrops());
            for (Drop drop : removeDrops) {
                field.removeLife(drop);
            }
        }
    }

    @Command(names = {"setdamageskin", "damageskin", "setds", "ds"}, requiredType = Tester)
    public static class SetDamageSkin extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 1) {
                chr.chatMessage("Usage: <damage skin id>");
                chr.chatMessage("[INFO] damage skin ids range from 1 to 219");
                return;
            }
            String dmgSkinIdxStr = args[1];
            if (!Util.isNumber(dmgSkinIdxStr)) {
                chr.chatMessage("Usage: <damage skin id>");
                chr.chatMessage("[INFO] damage skin ids range from 1 to 219");
                return;
            }
            int dmgSkinIdx = Integer.parseInt(dmgSkinIdxStr);
            QuestManager qm = chr.getQuestManager();
            Quest q = qm.getQuestById(QuestConstants.DAMAGE_SKIN);
            if (q == null) {
                q = QuestData.createQuestFromId(QuestConstants.DAMAGE_SKIN);
                qm.addQuest(q);
            }
            q.setQrValue(dmgSkinIdxStr);
            chr.setDamageSkin(dmgSkinIdx);
            chr.write(UserPacket.setDamageSkin(chr));
            chr.write(WvsContext.message(MessagePacket.questRecordMessage(q)));
        }
    }

    @Command(names = {"sendQRvalue", "qr"}, requiredType = Admin)
    public static class SendQRValue extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 3 || !Util.isNumber(args[1])) {
                chr.chatMessage("Usage: !qr <questid> <qrValue>");
                return;
            }
            int questId = Integer.parseInt(args[1]);


            QuestManager qm = chr.getQuestManager();
            Quest q = qm.getQuestById(questId);
            if (q == null) {
                q = QuestData.createQuestFromId(questId);
                qm.addQuest(q);
            }
            q.setQrValue(args[2]);
            chr.write(WvsContext.message(MessagePacket.questRecordMessage(q)));
            chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
            chr.chatMessage(String.format("Sent QRValue with  QuestId %d, QrValue %s", questId, q.getQRValue()));
        }
    }

    @Command(names = {"amountonline", "playercount", "count", "online", "onlineamount"}, requiredType = Tester)
    public static class AmountOnline extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            int total = 0;
            StringBuilder sb = new StringBuilder();
            for (World w : Server.getInstance().getWorlds()) {
                chr.chatMessage(Notice, "----------------------");
                for (Channel c : w.getChannels()) {
                    total += c.getChars().size();
                    chr.chatMessage(Notice, "Channel %s: %d players.", c.getName(), c.getChars().size());
                }
            }
            chr.chatMessage(Notice, "Total: " + total);
        }
    }

    @Command(names = {"players", "onlineplayers"}, requiredType = Tester)
    public static class Players extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            StringBuilder sb = new StringBuilder();
            for (World w : Server.getInstance().getWorlds()) {
                chr.chatMessage(Notice, "----------------------");
                for (Channel c : w.getChannels()) {
                    StringBuilder chars = new StringBuilder();
                    for (Char aChar : c.getChars().values()) {
                        chars.append(aChar.getName().replace("l", "L"))
                                .append("(")
                                .append(aChar.getOffenseManager().getTrust());
                        if (aChar.getInstance() != null) {
                            chars.append(", I)");
                        }
                        chars.append("), ");
                    }
                    chr.chatMessage(Notice, "Channel %s: %s.", c.getName(),
                            chars.substring(0, chars.length() != 0 ? chars.length() - 2 : 0));
                }
            }
        }
    }

    @Command(names = {"mapplayers"}, requiredType = Tester)
    public static class MapPlayers extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            StringBuilder sb = new StringBuilder();
            for (World w : Server.getInstance().getWorlds()) {
                chr.chatMessage(Notice, "----------------------");
                for (Channel c : w.getChannels()) {
                    StringBuilder chars = new StringBuilder();
                    for (Char aChar : c.getChars().values()) {
                        if (aChar.getFieldID() == chr.getFieldID()) {
                            chars.append(aChar.getName().replace("l", "L"))
                                    .append("(")
                                    .append(aChar.getOffenseManager().getTrust());
                            if (aChar.getInstance() != null) {
                                chars.append(", I)");
                            }
                            chars.append("), ");
                        }
                    }
                    chr.chatMessage(Notice, "Channel %s: %s.", c.getName(),
                            chars.substring(0, chars.length() != 0 ? chars.length() - 2 : 0));
                }
            }
        }
    }

    @Command(names = {"ptinfo", "partyinfo"}, requiredType = Tester)
    public static class PartyInfo extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            Party party = chr.getParty();
            if (party != null) {
                int i = 0;
                for (PartyMember pm : party.getPartyMembers()) {
                    if (pm == null) {
                        chr.chatMessage("Player %d: null", i);
                    } else {
                        chr.chatMessage("Player %d: name = %s, field = %d, channel = %d, online = %s",
                                i, pm.getCharName(), pm.getFieldID(), pm.getChannel(), pm.isOnline());
                    }
                    i++;
                }
            }
        }
    }

    @Command(names = {"warptoplayer", "warpto"}, requiredType = Tester)
    public static class WarpToPlayer extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !warpto <playername>");
                return;
            }
            String targetName = args[1];
            int worldID = chr.getClient().getChannelInstance().getWorldId().getVal();
            World world = Server.getInstance().getWorldById(worldID);
            Char targetChr = world.getCharByName(targetName);

            // Target doesn't exist
            if (targetChr == null) {
                chr.chatMessage(String.format("%s is not online.", targetName));
                chr.dispose();
                return;
            }

            Field targetField = targetChr.getField();
            if (targetChr.getChannel() != chr.getChannel()) {
                // change channel & warp
                int fieldId = targetChr.getFieldID();
                chr.setInstance(null);
                chr.changeChannelAndWarp(targetChr.getChannel(), fieldId);

            } else if (targetField != chr.getField()) {
                // warp & teleport
                chr.setInstance(null);
                chr.warp(targetField);
                teleportTo(chr, targetChr);
            } else {
                // same map: move inside it, no field reload
                teleportTo(chr, targetChr);
            }
        }

        private static void teleportTo(Char chr, Char targetChr) {
            Position to = targetChr.getPosition().deepCopy();
            chr.setPosition(to);
            chr.setFoothold(targetChr.getFoothold());
            chr.write(FieldPacket.teleport(to, chr));
        }
    }

    @Command(names = {"warptouser", "warpuser"}, requiredType = Tester)
    public static class WarpToUser extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !warpto <username>");
                return;
            }
            String targetName = args[1];
            int worldID = chr.getClient().getChannelInstance().getWorldId().getVal();
            World world = Server.getInstance().getWorldById(worldID);

            var userDb = userDao.getByName(targetName);
            var user = world.getUserById(userDb.getId());

            // Target doesn't exist
            if (user.getCurrentChr() == null) {
                chr.chatMessage(String.format("%s is not online.", targetName));
                chr.dispose();
                return;
            }

            Char targetChr = world.getCharByName(user.getCurrentChr().getName());


            Field targetField = targetChr.getField();
            if (targetChr.getChannel() != chr.getChannel()) {
                // change channel & warp
                int fieldId = targetChr.getFieldID();
                chr.setInstance(null);
                chr.changeChannelAndWarp(targetChr.getChannel(), fieldId);

            } else if (targetField != chr.getField()) {
                // warp & teleport
                chr.setInstance(null);
                chr.warp(targetField);
                teleportTo(chr, targetChr);
            } else {
                // same map: move inside it, no field reload
                teleportTo(chr, targetChr);
            }
        }

        private static void teleportTo(Char chr, Char targetChr) {
            Position to = targetChr.getPosition().deepCopy();
            chr.setPosition(to);
            chr.setFoothold(targetChr.getFoothold());
            chr.write(FieldPacket.teleport(to, chr));
        }
    }

    @Command(names = {"warpmap", "warpfield"}, requiredType = GameMaster)
    public static class WarpMap extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Incorrect usage! Usage: !warpmap <fieldId>");
                return;
            }

            var fieldId = Integer.parseInt(args[1]);

            var fieldInfo = FieldData.getFieldInfoById(fieldId);
            if (fieldInfo == null) {
                chr.chatMessage("Could not find field " + fieldId);
                return;
            }

            var chrs = new HashSet<>(chr.getField().getChars());
            for (var c : chrs) {
                c.warpAsGm(fieldId);
            }
        }
    }

    @Command(names = {"guildmaster", "setguildmaster"}, requiredType = GameMaster)
    public static class SetGuildMaster extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !guildmaster <character name> - makes that member (online or not) the master of their guild.");
                return;
            }
            String name = args[1];
            for (World w : Server.getInstance().getWorlds()) {
                for (Guild guild : new ArrayList<>(w.getGuilds().values())) {
                    for (GuildMember gm : new ArrayList<>(guild.getMembers())) {
                        if (gm.getName() != null && gm.getName().equalsIgnoreCase(name)) {
                            if (guild.getLeaderID() == gm.getCharID()) {
                                chr.chatMessage(String.format("%s already leads %s.", gm.getName(), guild.getName()));
                                return;
                            }
                            net.swordie.ms.handlers.social.module.GuildModule.adminSetMaster(guild, gm);
                            chr.chatMessage(String.format("%s is now the master of %s.", gm.getName(), guild.getName()));
                            return;
                        }
                    }
                }
            }
            chr.chatMessage(String.format("No guild member named %s.", name));
        }
    }

    @Command(names = {"guildstat", "setguildstat"}, requiredType = Tester)
    public static class SetGuildStat extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            Guild guild = chr.getGuild();
            if (guild == null) {
                chr.chatMessage("Be in a guild first.");
                return;
            }
            GuildMember gm = guild.getMemberByChar(chr);
            if (gm == null) {
                chr.chatMessage("You are not a member of your guild.");
                return;
            }
            if (args.length < 4) {
                chr.chatMessage("Usage: !guildstat <level> <ggp> <igp>");
                return;
            }
            guild.setLevel(Integer.parseInt(args[1]));
            guild.setGgp(Integer.parseInt(args[2]));
            gm.setIgp(Integer.parseInt(args[3]));
            guild.updateToMembers();
        }

    }

    @Command(names = {"calcdamage", "calc"}, requiredType = Tester)
    public static class ShowDamageCalc extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            chr.setShowDamageCalc(!chr.isShowDamageCalc());
            chr.chatMessage(Notice2, "Showing damage calculation is now %s.", chr.isShowDamageCalc() ? "on" : "off");
        }
    }

    @Command(names = {"pnpc"}, requiredType = GameMaster)
    public static class PNPC extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !pnpc <id> | !pnpc delete <id>");
                return;
            }
            
            if (args[1].equalsIgnoreCase("delete")) {
                if (args.length < 3) {
                    chr.chatMessage("Usage: !pnpc delete <id>");
                    return;
                }
                int npcId = Integer.parseInt(args[2]);
                deleteNpcById(chr, npcId);
                return;
            }
            
            int id = Integer.parseInt(args[1]);
            Npc npc = NpcData.getNpcDeepCopyById(id);
            if (npc == null) {
                chr.chatMessage("Could not find an npc with that ID.");
                return;
            }
            Field field = chr.getField();
            Position pos = chr.getPosition();
            npc.setPosition(pos.deepCopy());
            npc.setCy(pos.getY());
            npc.setRx0(pos.getX() + 50);
            npc.setRx1(pos.getX() - 50);
            npc.setFh(chr.getFoothold());
            npc.setNotRespawnable(true);
            npc.setField(field);
            field.spawnLife(npc, null);

            PlacedNpcTemplate template = new PlacedNpcTemplate(id, field.getId(), pos.getX(), pos.getY(), 
                    npc.getCy(), npc.getRx0(), npc.getRx1(), npc.getFh());
            placedNpcTemplateDao.saveOrUpdate(template);
            chr.chatMessage("Spawned NPC " + id + " at your position.");
            chr.chatMessage("Remember to regenerate .dat files to persist this change!");
        }
        
        private static void deleteNpcById(Char chr, int npcId) {
            Field field = chr.getField();
            
            Rect rect = chr.getPosition().getRectAround(new Rect(-200, -200, 200, 200));
            Npc targetNpc = field.getNpcByTemplateIdAndInRect(npcId, rect);
            
            if (targetNpc == null) {
                chr.chatMessage("No NPC with ID " + npcId + " found near you.");
                return;
            }
            
            List<PlacedNpcTemplate> templates = placedNpcTemplateDao.getByMapId(field.getId());
            Position npcPos = targetNpc.getPosition();
            Rect npcRect = npcPos.getRectAround(new Rect(-5, -5, 5, 5));
            
            PlacedNpcTemplate toDelete = templates.stream()
                    .filter(template -> template.getNpcid() == npcId && 
                            npcRect.hasPositionInside(new Position(template.getX(), template.getY())))
                    .findFirst()
                    .orElse(null);
            
            field.removeLife(targetNpc);
            
            if (toDelete != null) {
                placedNpcTemplateDao.delete(toDelete);
                chr.chatMessage("Deleted placed NPC " + npcId + " (DB ID: " + toDelete.getId() + ")");
                chr.chatMessage("Remember to regenerate .dat files to persist this change!");
            } else {
                chr.chatMessage("Removed NPC " + npcId + " from field (not found in database)");
            }
        }
    }

    @Command(names = {"rnpc"}, requiredType = GameMaster)
    public static class RNPC extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !rnpc <id> | !rnpc delete <id>");
                return;
            }
            
            Field field = chr.getField();
            
            if (args[1].equalsIgnoreCase("delete")) {
                if (args.length < 3) {
                    chr.chatMessage("Usage: !rnpc delete <id>");
                    return;
                }
                int npcId = Integer.parseInt(args[2]);
                deleteRemovedNpc(chr, npcId, field);
                return;
            }
            
            int npcId = Integer.parseInt(args[1]);
            
            List<RemovedNpcTemplate> existing = removedNpcTemplateDao.getByMapId(field.getId());
            boolean alreadyRemoved = existing.stream()
                    .anyMatch(template -> template.getNpcid() == npcId);
            
            if (alreadyRemoved) {
                chr.chatMessage("NPC " + npcId + " is already in the removed list for this map.");
                return;
            }
            
            RemovedNpcTemplate template = new RemovedNpcTemplate(npcId, field.getId());
            removedNpcTemplateDao.saveOrUpdate(template);
            
            chr.chatMessage("Added NPC " + npcId + " to removed list for map " + field.getId());
            chr.chatMessage("Remember to regenerate .dat files to apply this change!");
        }
        
        private static void deleteRemovedNpc(Char chr, int npcId, Field field) {
            List<RemovedNpcTemplate> templates = removedNpcTemplateDao.getByMapId(field.getId());
            
            RemovedNpcTemplate toDelete = templates.stream()
                    .filter(template -> template.getNpcid() == npcId)
                    .findFirst()
                    .orElse(null);
            
            if (toDelete != null) {
                removedNpcTemplateDao.delete(toDelete);
                chr.chatMessage("Removed NPC " + npcId + " from removed list (DB ID: " + toDelete.getId() + ")");
                chr.chatMessage("Remember to regenerate .dat files to apply this change!");
            } else {
                chr.chatMessage("NPC " + npcId + " is not in the removed list for this map.");
            }
        }
    }

    @Command(names = {"charinfo", "cinfo"}, requiredType = Admin)
    public static class CharInfo extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length <= 1) {
                chr.chatMessage("Usage: !cinfo <character name>");
                return;
            }
            var targetname = args[1];
            World w = chr.getWorld();
            Char target = w.getCharByName(targetname);
            if (target == null) {
                chr.chatMessage("Could not find character.");
                return;
            }

            Map<BaseStat, Integer> tbs = target.getTotalBasicStats();
            chr.chatMessage(Notice2, String.format("%s(%d)", target.getName(), target.getId()));
            chr.chatMessage(Notice2, "Ping: %d, Str: %d (%d%%), Int: %d (%d%%), Dex: %d (%d%%), Luk: %d (%d%%), " +
                            "Att: %d (%d%%), Matt: %d (%d%%), HP: %d (%d%%), MP: %d (%d%%), AttSpeed: %d, EXP: %d%%, Mesos: %d%%, Drop: %d%%.",
                    target.getClient().getPing(),
                    tbs.get(BaseStat.str),
                    tbs.get(BaseStat.strR),
                    tbs.get(BaseStat.inte),
                    tbs.get(BaseStat.intR),
                    tbs.get(BaseStat.dex),
                    tbs.get(BaseStat.dexR),
                    tbs.get(BaseStat.luk),
                    tbs.get(BaseStat.lukR),
                    tbs.get(BaseStat.pad),
                    tbs.get(BaseStat.padR),
                    tbs.get(BaseStat.mad),
                    tbs.get(BaseStat.madR),
                    tbs.get(BaseStat.mhp),
                    tbs.get(BaseStat.mhpR),
                    tbs.get(BaseStat.mmp),
                    tbs.get(BaseStat.mmpR),
                    tbs.get(BaseStat.booster),
                    tbs.get(BaseStat.expR),
                    tbs.get(BaseStat.mesoR),
                    tbs.get(BaseStat.dropR)
            );
        }
    }

    @Command(names = {"bosscd"}, requiredType = Tester)
    public static class BossCd extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            Char target = chr;
            if (args.length > 1) {
                int targetId = Integer.parseInt(args[1]);
                Char o = chr.getWorld().getCharByID(targetId);
                if (o == null) {
                    chr.chatMessage("Could not find character.");
                    return;
                }
                target = o;
            }

            target.getAccount().getBossCooldowns().clear();
            PartyQuestRecords.resetLimits(target);
            target.chatMessage("Reset all boss cooldowns.");
        }
    }

    @Command(names = {"resetallbosscd", "allbosscd"}, requiredType = Admin)
    public static class ResetBossCd extends AdminCommand {
        public static void execute(Char chr, String[] args) {

            for (World w : Server.getInstance().getWorlds()) {
                for (Channel c : w.getChannels()) {
                    for (Char zChr : c.getChars().values()) {
                        zChr.getAccount().getBossCooldowns().clear();
                        PartyQuestRecords.resetLimits(zChr);
                        zChr.chatMessage("Reset all boss cooldowns.");
                    }
                }
            }
        }
    }

    @Command(names = {"rp", "randomportal"}, requiredType = Tester)
    public static class RandomPortalTest extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            // 50% chance for inferno/yellow portal
            Foothold foothold = chr.getField().getInfo().getFootholdById(chr.getFoothold());
            Position position = foothold.getRandomPositionFromEdges(50);
            RandomPortal.Type portalType = Util.succeedProp(50)
                    ? RandomPortal.Type.PolloFritto
                    : RandomPortal.Type.Inferno;
            RandomPortal randomPortal = new RandomPortal(portalType, position, chr.getId());
            chr.getField().addLife(randomPortal);
            chr.write(RandomPortalPool.created(randomPortal));
        }
    }

    @Command(names = {"warpother"}, requiredType = GameMaster)
    public static class WarpOther extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 3) {
                chr.chatMessage("Incorrect usage: !warpother <name> <field id to warp to>");
                return;
            }
            var name = args[1];
            var world = Server.getInstance().getWorldById(chr.getWorld().getWorldId().getVal());
            var other = world.getCharByName(name);
            if (other != null) {
                other.warp(Integer.parseInt(args[2]));
                chr.chatMessage("Warped other to %s", args[2]);
            } else {
                chr.chatMessage("%s is not online", name);
            }
        }
    }

    @Command(names = {"unstuck"}, requiredType = GameMaster)
    public static class Unstuck extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Incorrect usage: !unstuck <username>");
                return;
            }

            var user = userDao.getByName(args[1]);
            if (user == null) {
                chr.chatMessage("Could not find that user.");
                return;
            }

            Server.getInstance().removeUser(user);
        }
    }

    @Command(names = {"hide"}, requiredType = GameMaster)
    public static class Hide extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            chr.setHidden(!chr.isHidden());
            chr.chatMessage("You are now %s.", chr.isHidden() ? "hidden" : "unhidden");
            Field field = chr.getField();
            if (chr.isHidden()) {
                chr.write(FieldPacket.adminResult(true));
                field.broadcastPacket(UserPool.userLeaveField(chr), chr);
            } else {
                chr.write(FieldPacket.adminResult(false));
                field.broadcastPacket(UserPool.userEnterField(chr), chr);
            }
        }
    }

    @Command(names = {"buffmap", "buffme"}, requiredType = Admin)
    public static class BuffMap extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            String path = "Effect/Direction23.img/effect2/";
            String effect = "5"; // Red Lightning Strike

            if (args.length < 3) {
                chr.chatMessage("Incorrect usage: !buffmap <base stat> <nValue>");
                return;
            }
            String stat = args[1];
            String val = args[2];

            if (!Util.isNumber(val)) {
                chr.chatMessage("nValue must be a number");
                return;
            }
            int value = Integer.parseInt(val);

            CharacterTemporaryStat cts = null;
            CharacterTemporaryStat cts2 = null;
            switch (stat) {
                case "pad":
                case "mad":
                    cts = IndiePAD;
                    cts2 = IndieMAD;
                    break;
                case "padr":
                case "madr":
                    cts = IndiePADR;
                    cts2 = IndieMADR;
                    break;
                case "fd":
                    cts = IndiePMdR;
                    break;
                case "ied":
                    cts = IndieIgnoreMobpdpR;
                    break;
                case "bdr":
                    cts = IndieBDR;
                    break;
                case "cr":
                    cts = IndieCr;
                    break;
                case "crDmg":
                    cts = IndieCrDmg;
                    break;
                case "exp":
                case "expr":
                    cts = IndieEXP;
                    effect = "10"; // Blue Lightning Strike
                    break;
                case "booster":
                    cts = IndieBooster;
                    effect = "10";
                    break;
            }

            if (cts == null) {
                chr.chatMessage(String.format("%s is not a valid base stat", stat));
                return;
            }

            path += effect;

            Field field = chr.getField();
            List<Char> chrs = Collections.singletonList(chr);
            if (args[0].equals("!buffmap")) {
                chrs = field.getChars();
            }

            for (Char c : chrs) {
                TemporaryStatManager tsm = c.getTemporaryStatManager();
                Option o = new Option();

                o.nOption = value;
                o.rOption = SkillConstants.ALLIED_ENCOURAGEMENT;
                o.tOption = 15 * 60;
                tsm.putCharacterStatValue(cts, o, true);
                if (cts2 != null) {
                    tsm.putCharacterStatValue(cts2, o, true);
                }
                tsm.sendSetStatPacket();

                c.write(UserPacket.effect(Effect.avatarOriented(path)));
                field.broadcastPacket(UserRemote.effect(c.getId(), Effect.avatarOriented(path)), c);
            }
        }
    }

    @Command(names = {"runestone"}, requiredType = Tester)
    public static class TestRunestone extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            RuneStone runeStone = RuneStone.getRandomRuneStone(chr.getField());
            if (args.length > 1) {
                int val = Integer.parseInt(args[1]);
                RuneType runeType = RuneType.getByVal((byte) val);
                if (runeType == null) {
                    chr.chatMessage("Invalid RuneType");
                    chr.chatMessage("Spawning Random Rune instead");
                } else {
                    runeStone.setRuneType(runeType);
                }
            }
            chr.getField().setRuneStone(runeStone);
            chr.getField().broadcastPacket(FieldPacket.runeStoneAppear(runeStone));
        }
    }

    // (!pull removed: it ran bat/Swordie_Tespia_Scripts_Pull.bat, a git pull of the previous developers' scripts
    // repository, so whoever controlled that repository could run code on this server)

    @Command(names = {"checkban", "baninfo"}, requiredType = Admin)
    public static class CheckBan extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !checkban/baninfo <charname>");
                return;
            }

            var name = args[1];
            var other = charDao.getByNameAndWorld(name, chr.getAccount().getWorldId());

            if (other == null) {
                chr.chatMessage("Could not find char %s", name);
                return;
            }

            var user = userDao.getById(other.getUserId());
            if (user == null) {
                chr.chatMessage("Could not find user of char %s", name);
                return;
            }

            chr.chatMessage("[Char %s, User %s] Ban reason: %s, Ban expire date: %s", other.getName(), user.getName(), user.getBanReason(), user.getBanExpireDate());
        }
    }

    /**
     * v271 remote/effect test: shows an effect on you (EFFECT) or, with a name, on that player or bot in your map
     * (REMOTE_EFFECT to the others, EFFECT to the player); "buff"/"unbuff" put Shadow Partner and Dark Sight on a
     * player or bot and take them off again (REMOTE_SET/RESET_TEMPORARY_STAT).
     */
    @Command(names = {"v271fx"}, requiredType = Admin)
    public static class V271Fx extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage("Usage: !v271fx levelup|quest|job|skill <id>|item|exp|text|buff|unbuff [name]");
                return;
            }
            String kind = args[1].toLowerCase();
            int skillId = 0;
            int nameAt = 2;
            if (kind.equals("skill") && args.length > 2) {
                skillId = Integer.parseInt(args[2]);
                nameAt = 3;
            }
            Char target = chr;
            if (args.length > nameAt) {
                target = chr.getField().getCharByName(args[nameAt]);
                if (target == null) {
                    chr.chatMessage("No one named " + args[nameAt] + " in this map.");
                    return;
                }
            }
            if (kind.equals("buff") || kind.equals("unbuff")) {
                TemporaryStatManager tsm = target.getTemporaryStatManager();
                if (kind.equals("buff")) {
                    tsm.putCharacterStatValue(CharacterTemporaryStat.ShadowPartner, new Option(1, NightWalker.SHADOW_PARTNER, 60));
                    tsm.putCharacterStatValue(CharacterTemporaryStat.DarkSight, new Option(1, NightWalker.DARK_SIGHT_2, 60));
                    tsm.sendSetStatPacket();
                } else {
                    tsm.removeStat(CharacterTemporaryStat.ShadowPartner, false);
                    tsm.removeStat(CharacterTemporaryStat.DarkSight, false);
                    tsm.sendResetStatPacket();
                }
                chr.chatMessage(kind + " sent for " + target.getName());
                return;
            }
            Effect effect = switch (kind) {
                case "levelup" -> Effect.levelUpEffect();
                case "quest" -> Effect.questCompleteEffect();
                case "job" -> Effect.changeJobEffect(target);
                case "skill" -> Effect.skillUse(skillId, target.getLevel(), 1, 0);
                case "item" -> Effect.gainQuestItem(4000000, 3); // hardcode-ok
                case "exp" -> Effect.fieldItemConsumed(1234);
                case "text" -> Effect.createFieldTextEffect("v271 text effect", 50, 2000, 4, new Position(0, -100), 0, 0,
                        TextEffectType.None, 0, 0);
                default -> null;
            };
            if (effect == null) {
                chr.chatMessage("Unknown effect " + kind);
                return;
            }
            target.write(UserPacket.effect(effect));
            target.getField().broadcastPacket(UserRemote.effect(target.getId(), effect), target);
            chr.chatMessage(kind + " (v271 type " + Effect.v271Type(effect.getUserEffectType()) + ") on "
                    + target.getName());
        }
    }

    @Command(names = {"gift"}, requiredType = Admin)
    public static class GiftBox extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 4) {
                chr.chatMessage("Usage: !gift <player> <itemID> <quantity> <message>");
                return;
            }
            String targetName = args[1];
            int itemid = Integer.parseInt(args[2]);
            int quantity = Integer.parseInt(args[3]);
            String message = (args.length >= 5)
                    ? String.join(" ", java.util.Arrays.copyOfRange(args, 4, args.length))
                    : "Default msg";

            Char targetChr = Server.getInstance().getWorldById(chr.getClient().getWorldId()).getCharByName(targetName);
            boolean online = true;
            if (targetChr == null) {
                online = false;
                targetChr = charDao.getByNameAndWorld(targetName, chr.getAccount().getWorldId());
                if (targetChr == null) {
                    chr.chatMessage(SpeakerChannel, "Could not find that character.");
                    return;
                }
                Account account = accountDao.getByCharId(targetChr.getId());
                targetChr.setAccount(account);
            }

            int targetId = targetChr.getId();

            FirstEnterReward reward = new FirstEnterReward(targetId, itemid, quantity, FirstEnterRewardType.GameItem, message);

            targetChr.addFirstEnterReward(reward);
            if (online) {
                targetChr.checkFirstEnterReward();
            }
            chr.chatMessage("Gift Sent!, target is online: " + online);
        }
    }
    /**
     * v271 party test with bots (they don't answer invites): "join <name>" puts that player or bot in your party
     * (creating one if needed), "hp <name> <hp>" sends its HP to the party (party HP bar), "leave <name>" takes it out.
     */
    @Command(names = {"v271party"}, requiredType = Admin)
    public static class V271Party extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 3) {
                chr.chatMessage("Usage: !v271party join|hp|leave <name> [hp]");
                return;
            }
            String kind = args[1].toLowerCase();
            Char target = chr.getClient().getWorld().getCharByName(args[2]);
            if (target == null && kind.equals("join")) {
                target = charDao.getByName(args[2]); // offline: listed as an offline member (no HP bar)
            }
            if (target == null || target == chr) {
                chr.chatMessage("No other character named " + args[2] + ".");
                return;
            }
            Party party = chr.getParty();
            switch (kind) {
                case "join" -> {
                    if (target.getParty() != null) {
                        chr.chatMessage(target.getName() + " is already in a party.");
                        return;
                    }
                    if (party == null) {
                        party = Party.createNewParty(true, false, "Party with me!", chr.getClient().getWorld());
                        party.addPartyMember(chr);
                        chr.write(WvsContext.partyResult(PartyResult.createNewParty(party)));
                    }
                    if (party.isFull()) {
                        chr.chatMessage("Your party is full.");
                        return;
                    }
                    party.addPartyMember(target); // sends the join result to the members
                    for (PartyMember pm : party.getOnlineMembers()) {
                        target.write(UserRemote.receiveHP(pm.getChr()));
                    }
                    party.broadcast(UserRemote.receiveHP(target));
                    chr.chatMessage(target.getName() + " joined (PARTY_RESULT join, REMOTE_RECEIVE_HP).");
                }
                case "hp" -> {
                    if (party == null || party.getPartyMemberByID(target.getId()) == null || args.length < 4) {
                        chr.chatMessage("Usage: !v271party hp <member> <hp> (the member must be in your party)");
                        return;
                    }
                    int hp = Integer.parseInt(args[3]);
                    int max = Math.max(hp, target.getTotalStat(BaseStat.mhp));
                    party.broadcast(UserRemote.receiveHP(target.getId(), hp, max), target);
                    chr.chatMessage(String.format("%s HP %d / %d sent to the party.", target.getName(), hp, max));
                }
                case "leave" -> {
                    PartyMember pm = party == null ? null : party.getPartyMemberByID(target.getId());
                    if (pm == null) {
                        chr.chatMessage(target.getName() + " is not in your party.");
                        return;
                    }
                    if (party.getMembers().size() <= 2) {
                        party.disband();
                    } else {
                        party.broadcastLeave(pm, false);
                        party.removePartyMember(pm);
                        party.updateFull();
                    }
                    chr.chatMessage(target.getName() + " left.");
                }
                default -> chr.chatMessage("Usage: !v271party join|hp|leave <name> [hp]");
            }
        }
    }

    /**
     * v271 social test without bots: "add <name>" makes that character's account a friend (both sides), "request
     * <name>" leaves a friend request from it, "status <name> <channel index>" shows the friend online on that channel
     * (-1 offline) without it logging in.
     */
    @Command(names = {"v271friend"}, requiredType = Admin)
    public static class V271Friend extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            if (args.length < 3) {
                chr.chatMessage("Usage: !v271friend add|request|status <name> [channel index]");
                return;
            }
            String msg = switch (args[1].toLowerCase()) {
                case "add" -> net.swordie.ms.handlers.social.FriendHandler.testLink(chr, args[2], false);
                case "request" -> net.swordie.ms.handlers.social.FriendHandler.testLink(chr, args[2], true);
                case "status" -> net.swordie.ms.handlers.social.FriendHandler.testStatus(chr, args[2],
                        args.length > 3 ? Integer.parseInt(args[3]) : 0);
                default -> "Usage: !v271friend add|request|status <name> [channel index]";
            };
            chr.chatMessage(msg);
        }
    }

    /**
     * v271 guild test without bots: "join <name>" puts that (offline) character in your guild, "login <name>" /
     * "logout <name>" sends its online notice to the guild window, "leave <name>" takes it out again.
     */
    @Command(names = {"v271guild"}, requiredType = Admin)
    public static class V271Guild extends AdminCommand {

        public static void execute(Char chr, String[] args) {
            Guild guild = chr.getGuild();
            if (args.length < 3 || guild == null) {
                chr.chatMessage("Usage: !v271guild join|login|logout|leave <name> (you need a guild)");
                return;
            }
            String kind = args[1].toLowerCase();
            Char target = chr.getClient().getWorld().getCharByName(args[2]);
            boolean online = target != null;
            if (target == null) {
                target = charDao.getByNameAndWorld(args[2], chr.getAccount().getWorldId());
            }
            if (target == null || target == chr) {
                chr.chatMessage("No other character named " + args[2] + ".");
                return;
            }
            GuildMember gm = guild.getMemberByCharID(target.getId());
            switch (kind) {
                case "join" -> {
                    if (gm != null || (target.getGuild() != null && target.getGuild() != guild)) {
                        chr.chatMessage(target.getName() + " is already in a guild.");
                        return;
                    }
                    if (online) {
                        net.swordie.ms.handlers.social.module.GuildModule.joinGuild(guild, target);
                    } else {
                        guild.addMember(target, false); // sends the join to the members
                        target.setGuild(guild);
                        charDao.saveOrUpdate(target);
                    }
                    chr.chatMessage(target.getName() + " joined (GUILD_RESULT join).");
                }
                case "login", "logout" -> {
                    if (gm == null) {
                        chr.chatMessage(target.getName() + " is not in your guild.");
                        return;
                    }
                    boolean in = kind.equals("login");
                    gm.setOnline(in);
                    guild.broadcast(WvsContext.guildResult(
                            net.swordie.ms.client.guild.result.GuildResult.notifyLoginOrLogout(guild, gm, in, true)));
                    chr.chatMessage(target.getName() + (in ? " shown online." : " shown offline."));
                }
                case "leave" -> {
                    if (gm == null) {
                        chr.chatMessage(target.getName() + " is not in your guild.");
                        return;
                    }
                    guild.broadcast(WvsContext.guildResult(net.swordie.ms.client.guild.result.GuildResult
                            .leaveGuild(guild, gm.getCharID(), gm.getName(), true)));
                    guild.removeMember(gm);
                    target.setGuild(null);
                    if (!online) {
                        charDao.saveOrUpdate(target);
                    }
                    chr.chatMessage(target.getName() + " left.");
                }
                default -> chr.chatMessage("Usage: !v271guild join|login|logout|leave <name>");
            }
        }
    }


    /**
     * v271 mini room test with one client: the other side is shown only (packets to you; nothing is traded with it).
     * "invite <name>" a trade invite from that character; "open <name>" opens a trade window with it as the visitor;
     * "visitor <name>" shows it entering your current room; "item <id> [qty] [slot]", "meso <n>", "chat <text>" its
     * offer and lines; "state <n>" / "check <n>" the v271 trade confirm packets; "ready" its game ready; "close".
     */
    @Command(names = {"v271room"}, requiredType = Admin)
    public static class V271Room extends AdminCommand {

        private static final java.util.Map<Integer, Char> SHOWN = new java.util.concurrent.ConcurrentHashMap<>();

        private static void send(Char chr, net.swordie.ms.connection.packet.model.MiniRoomPacket p) {
            chr.write(net.swordie.ms.connection.packet.MiniRoomPool.miniRoomBaseDlg(p));
        }

        public static void execute(Char chr, String[] args) {
            String usage = "Usage: !v271room invite|open|visitor <name> | item <id> [qty] [slot] | meso <n> | chat <text>"
                    + " | state <1-4> | check <n> | ready | close";
            if (args.length < 2) {
                chr.chatMessage(usage);
                return;
            }
            var room = chr.getMiniRoom();
            String kind = args[1].toLowerCase();
            Char other = null;
            if (kind.equals("invite") || kind.equals("open") || kind.equals("visitor")) {
                if (args.length < 3) {
                    chr.chatMessage(usage);
                    return;
                }
                other = chr.getClient().getWorld().getCharByName(args[2]);
                if (other == null) {
                    other = charDao.getByName(args[2]);
                }
                if (other == null || other == chr) {
                    chr.chatMessage("No other character named " + args[2] + ".");
                    return;
                }
            }
            switch (kind) {
                case "invite" -> {
                    var trade = new net.swordie.ms.client.social.miniroom.tradingroom.TradingRoom();
                    send(chr, net.swordie.ms.connection.packet.model.MiniRoomPacket.inviteRequest(other, trade));
                    chr.chatMessage("Invite from " + other.getName() + " sent (accepting answers 'room closed').");
                }
                case "open" -> {
                    if (room != null) {
                        chr.chatMessage("Close your room first (!v271room close).");
                        return;
                    }
                    var trade = new net.swordie.ms.client.social.miniroom.tradingroom.TradingRoom();
                    trade.createMiniRoom(chr);
                    send(chr, net.swordie.ms.connection.packet.model.MiniRoomPacket.accept(other, trade));
                    SHOWN.put(chr.getId(), other);
                    chr.chatMessage("Trade window open with " + other.getName() + " shown as the visitor.");
                }
                case "visitor" -> {
                    if (room == null) {
                        chr.chatMessage("Open a room first.");
                        return;
                    }
                    send(chr, net.swordie.ms.connection.packet.model.MiniRoomPacket.accept(other, room));
                    SHOWN.put(chr.getId(), other);
                    chr.chatMessage(other.getName() + " shown entering your room.");
                }
                case "item" -> {
                    if (room == null || args.length < 3) {
                        chr.chatMessage(usage);
                        return;
                    }
                    var item = net.swordie.ms.loaders.ItemData.getItemDeepCopy(Integer.parseInt(args[2]));
                    if (item == null) {
                        chr.chatMessage("No item " + args[2] + ".");
                        return;
                    }
                    if (args.length > 3) {
                        item.setQuantity(Integer.parseInt(args[3]));
                    }
                    int slot = args.length > 4 ? Integer.parseInt(args[4]) : 0;
                    send(chr, net.swordie.ms.connection.packet.model.MiniRoomPacket.placeItem(1, slot, item));
                }
                case "meso" -> {
                    if (room == null || args.length < 3) {
                        chr.chatMessage(usage);
                        return;
                    }
                    send(chr, net.swordie.ms.connection.packet.model.MiniRoomPacket.setMoney(1, Long.parseLong(args[2])));
                }
                case "chat" -> {
                    if (room == null || args.length < 3) {
                        chr.chatMessage(usage);
                        return;
                    }
                    String text = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));
                    send(chr, net.swordie.ms.connection.packet.model.MiniRoomPacket.chat(
                            net.swordie.ms.enums.MiniRoomAction.ChatUser.getVal(), SHOWN.getOrDefault(chr.getId(), chr),
                            0, 0, text));
                }
                case "state" -> send(chr, net.swordie.ms.connection.packet.model.MiniRoomPacket.tradeState(
                        args.length > 2 ? Integer.parseInt(args[2]) : 2));
                case "check" -> send(chr, net.swordie.ms.connection.packet.model.MiniRoomPacket.tradeStateCheck(
                        args.length > 2 ? Integer.parseInt(args[2]) : 0));
                case "ready" -> send(chr, net.swordie.ms.connection.packet.model.MiniRoomPacket.gameAction(
                        net.swordie.ms.enums.MiniRoomAction.GameUserReady));
                case "close" -> {
                    if (room != null) {
                        room.charLeave(chr, net.swordie.ms.enums.RoomLeaveType.LeftRoom);
                    }
                    send(chr, net.swordie.ms.connection.packet.model.MiniRoomPacket.exit(0,
                            net.swordie.ms.enums.RoomLeaveType.TRLeave_TradeFail));
                    chr.setMiniRoom(null);
                    SHOWN.remove(chr.getId());
                }
                default -> chr.chatMessage(usage);
            }
        }
    }

    @Command(names = {"openui"}, requiredType = Admin)
    public static class OpenUICommand extends AdminCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length < 2) {
                chr.chatMessage(Notice2, "Usage: !openui <id|name>");
                return;
            }
            String target = args[1];
            UIType uiType = null;
            try {
                int id = Integer.parseInt(target);
                uiType = UIType.getByVal(id);
            } catch (NumberFormatException ignored) {
                uiType = Arrays.stream(UIType.values())
                        .filter(u -> u.name().equalsIgnoreCase(target))
                        .findFirst()
                        .orElse(null);
            }
            if (uiType == null || uiType == UIType.UI_NOT_DEFINED) {
                chr.chatMessage(Notice2, "UI not found: " + target);
                return;
            }
            chr.write(net.swordie.ms.connection.packet.field.FieldPacket.openUI(uiType));
            chr.chatMessage(Notice2, "Opening UI: " + uiType.name() + " (" + uiType.getVal() + ")");
        }
    }
}
