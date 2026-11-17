package net.swordie.ms.connection.packet;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.TownPortal;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.field.ScenePacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.util.Position;
import org.junit.Assert;
import org.junit.Test;

import static net.swordie.ms.connection.packet.PacketBytes.assertBytes;
import static net.swordie.ms.connection.packet.PacketBytes.hex;

/** Exact bytes of packet writers whose layout was fixed against the client's decoder. */
public class PacketGoldenTest {

    private static final int TOWN = 100000000;
    private static final int FIELD = 104000000;
    private static final int MYSTIC_DOOR = 2311002;

    private static TownPortal portal(int townId, int fieldId) {
        Char chr = new Char();
        chr.setClient(new Client(null, new byte[4], new byte[4]));
        return new TownPortal(chr, new Position(), new Position(-120, 35), townId, fieldId, MYSTIC_DOOR, 1000);
    }

    @Test
    public void townPortalWithRealFieldsSendsSkillAndPosition() {
        assertBytes("E400 00E1F505 00EA3206 5A432300 88FF 2300", WvsContext.townPortal(portal(TOWN, FIELD)));
    }

    @Test
    public void townPortalWithoutFieldSendsOnlyTheTwoIds() {
        assertBytes("E400 00E1F505 FFC99A3B", WvsContext.townPortal(portal(TOWN, GameConstants.NO_MAP_ID)));
        assertBytes("E400 FFC99A3B FFC99A3B",
                WvsContext.townPortal(portal(GameConstants.NO_MAP_ID, GameConstants.NO_MAP_ID)));
        assertBytes("E400 FFC99A3B 00EA3206", WvsContext.townPortal(portal(GameConstants.NO_MAP_ID, FIELD)));
    }

    @Test
    public void transferIgnoredChannel() {
        assertBytes("0D03 01", FieldPacket.transferChannelReqIgnored(FieldPacket.TRANSFER_IGNORED_CHANNEL));
    }

    @Test
    public void transferIgnoredCashShop() {
        assertBytes("0D03 02", FieldPacket.transferChannelReqIgnored(FieldPacket.TRANSFER_IGNORED_CASH_SHOP));
    }

    @Test
    public void inGamePingIsEmpty() {
        assertBytes("2100", new net.swordie.ms.connection.OutPacket(OutHeader.PING_CHECK_RESULT));
    }

    @Test
    public void loginAliveRequestIsEmpty() {
        assertBytes("2000", Login.sendAliveReq());
    }

    @Test
    public void matrixInformationHasTheSubTypeTables() {
        String packet = hex(HexaPacket.matrixInformation()).replace(" ", "");
        // activate costs: 1 subType {1, 4 Sol Erda, 90 fragments}, then its upgrade rows: 29 levels, level 1 = 1 / 25
        String subTables = "01000000 01000000 04000000 5A000000"
                + "01000000 01000000 1D000000 01000000 01000000 19000000 02000000 01000000 1E000000";
        Assert.assertTrue(packet.contains(subTables.replace(" ", "")));
    }

    @Test
    public void sceneIllustrationShowMatchesGms() {
        assertBytes("1503 51 0200 3030 2600 4566666563742F446972656374696F6E474C2E696D672F4572656C2F696C6C7573742F302F30"
                        + " 00 06FFFFFF 32000000 0A000000 E8030000 FFFFFFFF 0200 6363 00 00000000 FFFFFFFF 00000000 FF000000 00 0000",
                ScenePacket.illustrationShow("00", "Effect/DirectionGL.img/Erel/illust/0/0", false, -250, 50, 10, 1000,
                        false, -1, 255));
    }

    @Test
    public void sceneIllustrationRemoveAndScaleMatchGms() {
        assertBytes("1503 52 0200 3030 64000000", ScenePacket.illustrationRemove("00", 100));
        assertBytes("1503 53 0200 3031 78000000 78000000 00000000 00000000 0000 00000000",
                ScenePacket.illustrationScale("01", 120, 120));
    }

    @Test
    public void sceneNarrationAndFaceMatchGms() {
        String text = "#fnArial##fs30#When the strange darkness began to infest the world of light,\r\n #ra black mark#k appeared.";
        assertBytes("0D05 1F 6900 23666E417269616C232366733330235768656E2074686520737472616E6765206461726B6E65737320626567616E"
                        + "20746F20696E666573742074686520776F726C64206F66206C696768742C0D0A2023726120626C61636B206D61726B236B"
                        + "2061707065617265642E 00 00000000 00000000 00000000 C8000000 02000000 0A00 30784646303030303030",
                ScenePacket.narration(text, false, 0, 200, 2, "0xFF000000"));
        assertBytes("0D05 12 0700 626C696E6B2F31 FFFFFFFF", ScenePacket.face("blink/1", -1));
    }

    @Test
    public void sceneMoveSpeedMatchesGms() {
        assertBytes("A500 00000100 50000000", ScenePacket.moveSpeed(80));
    }

    @Test
    public void sceneFloatingAndNavigationMatchGms() {
        assertBytes("6205 3A734E29 03000000 03000000 03000000 01 00000000", ScenePacket.fieldFloating(693007162, 3, 3, 3, true));
        assertBytes("3801 9A724E29 01000000 0700 39323031373234 00000000", ScenePacket.navigationToNpc(693007002, 9201724));
    }

    @Test
    public void sceneActionPacketsMatchGms() {
        assertBytes("0D05 01", ScenePacket.clearForcedAction());
        assertBytes("0D05 13 01000000", ScenePacket.actionHold(true));
    }
}
