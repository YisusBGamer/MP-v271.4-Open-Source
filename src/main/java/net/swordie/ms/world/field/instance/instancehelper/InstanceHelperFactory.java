package net.swordie.ms.world.field.instance.instancehelper;

import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.MobConstants;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.boss.*;
import net.swordie.ms.world.field.instance.instancehelper.events.InfernoWolfInstanceHelper;
import net.swordie.ms.world.field.instance.instancehelper.events.PiggyBarInstanceHelper;
import net.swordie.ms.world.field.instance.instancehelper.events.courtshipdancing.CourtshipDancingInstanceHelper;
import net.swordie.ms.world.field.instance.instancehelper.events.monsterdefense.BountyHuntInstanceHelper;
import net.swordie.ms.world.field.instance.instancehelper.events.monsterdefense.GuardTheCastleGatesInstanceHelper;
import net.swordie.ms.world.field.instance.instancehelper.events.monsterdefense.NewPyramidInstanceHelper;
import net.swordie.ms.world.field.instance.instancehelper.pq.*;
import net.swordie.ms.world.field.instance.instancehelper.quests.ClosedLabInstanceHelper;

/**
 * Created on 02/02/2022.
 *
 * @author Asura
 */
public class InstanceHelperFactory {
    private static final int CRIMSON_QUEEN_FIELD = 105200300;
    private static final int CRIMSON_QUEEN_CHAOS_FIELD = 105200700;
    private static final int DEMIAN_FIELD = 350160200;
    private static final int VERUS_HILLA_FIELD = 450010500;

    // So far.. Only used for InfernoWolf
    public static InstanceHelper getHelperByMob(Mob mob) {
        if (MobConstants.isInfernoWolf(mob.getTemplateId())) {
            return new InfernoWolfInstanceHelper();
        }

        return null;
    }

    public static InstanceHelper getHelperByInstance(Instance instance) {

        if (instance == null) {
            return null;
        }

        if (instance.getInstanceHelper() != null) {
            return instance.getInstanceHelper();
        }

        // Build
        var fieldId = instance.getEnterFieldId();

        // Moon Bunny
        if (FieldConstants.isMoonBunnyField(fieldId)) {
            instance.setInstanceHelper(new MoonBunnyInstanceHelper());
        }

        // Piggy Bar minigame
        if (PiggyBarInstanceHelper.isField(fieldId)) {
            instance.setInstanceHelper(new PiggyBarInstanceHelper());
        }

        // Mirror World dungeons (Zero)
        if (net.swordie.ms.world.field.instance.instancehelper.quests.MirrorDungeonInstanceHelper.isRoom(fieldId)) {
            instance.setInstanceHelper(new net.swordie.ms.world.field.instance.instancehelper.quests.MirrorDungeonInstanceHelper());
        }

        // High Mountain (Epic Dungeon; a run may resume at a later stage)
        if (net.swordie.ms.world.field.instance.instancehelper.epic.HighMountainInstanceHelper.isField(fieldId)) {
            instance.setInstanceHelper(new net.swordie.ms.world.field.instance.instancehelper.epic.HighMountainInstanceHelper());
        }

        // Angler Company (Epic Dungeon 2; a run may resume at a later stage)
        if (net.swordie.ms.world.field.instance.instancehelper.epic.AnglerCompanyInstanceHelper.isField(fieldId)) {
            instance.setInstanceHelper(new net.swordie.ms.world.field.instance.instancehelper.epic.AnglerCompanyInstanceHelper());
        }

        // Nightmare Paradise (Epic Dungeon 3; a run may resume at a later stage)
        if (net.swordie.ms.world.field.instance.instancehelper.epic.NightmareParadiseInstanceHelper.isField(fieldId)) {
            instance.setInstanceHelper(new net.swordie.ms.world.field.instance.instancehelper.epic.NightmareParadiseInstanceHelper());
        }

        // Spirit Savior (Arcana daily)
        if (net.swordie.ms.world.field.instance.instancehelper.arcane.SpiritSaviorInstanceHelper.isField(fieldId)) {
            instance.setInstanceHelper(new net.swordie.ms.world.field.instance.instancehelper.arcane.SpiritSaviorInstanceHelper());
        }

        // Midnight Chaser (Lachelein daily)
        if (net.swordie.ms.world.field.instance.instancehelper.arcane.MidnightChaserInstanceHelper.isField(fieldId)) {
            instance.setInstanceHelper(new net.swordie.ms.world.field.instance.instancehelper.arcane.MidnightChaserInstanceHelper());
        }

        // Hungry Muto (Chu Chu weekly)
        if (net.swordie.ms.world.field.instance.instancehelper.arcane.HungryMutoInstanceHelper.isField(fieldId)) {
            instance.setInstanceHelper(new net.swordie.ms.world.field.instance.instancehelper.arcane.HungryMutoInstanceHelper());
        }

        // Esfera Guardian (Esfera weekly)
        if (net.swordie.ms.world.field.instance.instancehelper.arcane.EsferaGuardianInstanceHelper.isField(fieldId)) {
            instance.setInstanceHelper(new net.swordie.ms.world.field.instance.instancehelper.arcane.EsferaGuardianInstanceHelper());
        }

        // Ranheim Defense (Morass weekly)
        if (net.swordie.ms.world.field.instance.instancehelper.arcane.RanheimDefenseInstanceHelper.isField(fieldId)) {
            instance.setInstanceHelper(new net.swordie.ms.world.field.instance.instancehelper.arcane.RanheimDefenseInstanceHelper());
        }

        // Erda Spectrum (Vanishing Journey weekly)
        if (net.swordie.ms.world.field.instance.instancehelper.arcane.ErdaSpectrumInstanceHelper.isField(fieldId)) {
            instance.setInstanceHelper(new net.swordie.ms.world.field.instance.instancehelper.arcane.ErdaSpectrumInstanceHelper());
        }

        // Dimensional Crack (Ludibrium PQ)
        if (FieldConstants.isDimensionalCrackField(fieldId)) {
            instance.setInstanceHelper(new DimensionalCrackInstanceHelper());
        }

        // Lord Pirate (Herb Town PQ)
        if (FieldConstants.isLordPirateField(fieldId)) {
            instance.setInstanceHelper(new LordPirateInstanceHelper());
        }

        // Escape (Lion King's Castle PQ)
        if (FieldConstants.isEscapePQField(fieldId)) {
            instance.setInstanceHelper(new EscapeInstanceHelper());
        }

        // Romeo and Juliet (Magatia PQ)
        if (FieldConstants.isRomeoJulietField(fieldId)) {
            instance.setInstanceHelper(new RomeoJulietInstanceHelper());
        }

        // Cooking with Tangyoon
        if (FieldConstants.isTangyoonField(fieldId)) {
            instance.setInstanceHelper(new TangyoonInstanceHelper());
        }

        // Kenta in Danger
        if (FieldConstants.isKentaPQField(fieldId)) {
            instance.setInstanceHelper(new KentaInstanceHelper());
        }

        // Dragon Rider
        if (FieldConstants.isDragonRiderField(fieldId)) {
            instance.setInstanceHelper(new DragonRiderInstanceHelper());
        }

        // Remnant of the Goddess (Orbis PQ)
        if (FieldConstants.isGoddessPQField(fieldId)) {
            instance.setInstanceHelper(new GoddessInstanceHelper());
        }

        // Resurrection of the Hoblin King
        if (FieldConstants.isHoblinKingField(fieldId)) {
            instance.setInstanceHelper(new HoblinKingInstanceHelper());
        }

        // Forest of Poison Haze
        if (FieldConstants.isPoisonHazeField(fieldId)) {
            instance.setInstanceHelper(new PoisonHazeInstanceHelper());
        }

        // The Ice Knight's Curse
        if (FieldConstants.isIceKnightField(fieldId)) {
            instance.setInstanceHelper(new IceKnightInstanceHelper());
        }

        // Xerxes in Chryse
        if (FieldConstants.isChryseField(fieldId)) {
            instance.setInstanceHelper(new ChryseInstanceHelper());
        }

        // First Time Together (Cross World)
        if (FieldConstants.isFirstTimeTogetherField(fieldId)) {
            instance.setInstanceHelper(new FirstTimeTogetherInstanceHelper());
        }

        // Ursus
        if (FieldConstants.isUrsusField(fieldId)) {
            instance.setInstanceHelper(new net.swordie.ms.world.field.instance.instancehelper.events.UrsusInstanceHelper());
        }

        // Princess No
        if (FieldConstants.isPrincessNoField(fieldId)) {
            instance.setInstanceHelper(new PrincessNoInstanceHelper());
        }

        // Ninja Castle inner halls
        if (FieldConstants.isNinjaCastleField(fieldId)) {
            instance.setInstanceHelper(new NinjaCastleInstanceHelper());
        }

        // Courtship Dancing
        if (FieldConstants.isCourtshipDancingField(fieldId)) {
            instance.setInstanceHelper(new CourtshipDancingInstanceHelper());
        }

        // Nett's Pyramid
        if (FieldConstants.isNettPyramidField(fieldId)) {
            instance.setInstanceHelper(new NewPyramidInstanceHelper());
        }

        if (FieldConstants.isGuardTheCastleGatesField(fieldId)) {
            instance.setInstanceHelper(new GuardTheCastleGatesInstanceHelper());
        }

        // Bounty Hunt
        if (FieldConstants.isBountyHuntField(fieldId)) {
            instance.setInstanceHelper(new BountyHuntInstanceHelper());
        }

        // Commerci Voyage
        if (FieldConstants.isVoyageField(fieldId)) {
            instance.setInstanceHelper(new VoyageInstanceHelper());
        }

        // Oz
        if (FieldConstants.isTowerOfOzField(fieldId)) {
            instance.setInstanceHelper(new TowerOfOzInstanceHelper());
        }

        // Magnus
        if (FieldConstants.isBossMagnusField(fieldId)) {
            instance.setInstanceHelper(new MagnusInstanceHelper());
        }

        // Hilla
        if (FieldConstants.isHardHillaField(fieldId)) {
            instance.setInstanceHelper(new HardHillaInstanceHelper());
        }

        if (FieldConstants.isVonLeonField(fieldId)) {
            instance.setInstanceHelper(new VonLeonInstanceHelper());
        }

        if (FieldConstants.isRanmaruField(fieldId)){
            instance.setInstanceHelper(new RanmaruInstanceHelper());
        }


        if (FieldConstants.isVonBonInstanceField(fieldId)){
            instance.setInstanceHelper(new VonBonInstanceHelper());
        }

        if (FieldConstants.isVellumField(fieldId)) {
            instance.setInstanceHelper(new VellumInstanceHelper());
        }

        if(FieldConstants.isPierreField(fieldId)){
            instance.setInstanceHelper(new PierreInstanceHelper());
        }

        // Crimson Queen (Root Abyss, Normal 105200300 / Chaos 105200700): her four expressions
        if (fieldId == CRIMSON_QUEEN_FIELD || fieldId == CRIMSON_QUEEN_CHAOS_FIELD) {
            instance.setInstanceHelper(new net.swordie.ms.world.field.instance.instancehelper.boss.CrimsonQueenInstanceHelper());
        }

        if (FieldConstants.isCygnusField(fieldId)){
            instance.setInstanceHelper(new CygnusInstanceHelper());
        }

        // Zakum
        if (FieldConstants.isBossZakumField(fieldId)) {
            instance.setInstanceHelper(new ZakumInstanceHelper());
        }

        // Will
        if (FieldConstants.isBossWillField(fieldId)) {
            instance.setInstanceHelper(new WillInstanceHelper());
        }

        // Gloom
        if (FieldConstants.isGloomField(fieldId)) {
            instance.setInstanceHelper(new GloomInstanceHelper());
        }

        // Darknell, Verus Hilla, Guardian Angel Slime: official mechanics run by the server
        if (fieldId == net.swordie.ms.constants.BossConstants.DARKNELL_FIELD
                || fieldId == net.swordie.ms.constants.BossConstants.DARKNELL_HARD_FIELD) {
            instance.setInstanceHelper(new DarknellInstanceHelper());
        }
        if (fieldId == VERUS_HILLA_FIELD) {
            instance.setInstanceHelper(new VerusHillaInstanceHelper());
        }
        // Lucid: the entrance scenes Normal 450004100, Hard 450004400, Easy 450003800
        if (fieldId == BossConstants.LUCID_FIELD || fieldId == BossConstants.LUCID_HARD_FIELD
                || fieldId == net.swordie.ms.constants.BossConstants.LUCID_EASY_FIELD) {
            instance.setInstanceHelper(new LucidInstanceHelper());
        }
        // Damien: Hard 350160100, Normal 350160200 (phase 2 + 40)
        if (fieldId == FieldConstants.DEMIAN_HARD_FIELD || fieldId == DEMIAN_FIELD) {
            instance.setInstanceHelper(new DemianInstanceHelper());
        }
        int lotusMode = net.swordie.ms.constants.BossConstants.getLotusV265Mode(fieldId);
        if (lotusMode >= 0 && fieldId == net.swordie.ms.constants.BossConstants.LOTUS_V265_FIELD_BASE[lotusMode] + 100) {
            instance.setInstanceHelper(new LotusV265InstanceHelper());
        }
        int blackMageMode = net.swordie.ms.constants.BossConstants.getBlackMageMode(fieldId);
        if (blackMageMode >= 0 && fieldId == net.swordie.ms.constants.BossConstants.BLACK_MAGE_FIELDS[blackMageMode][0]) {
            instance.setInstanceHelper(new BlackMageInstanceHelper());
        }
        int serenMode = net.swordie.ms.constants.BossConstants.getSerenMode(fieldId);
        if (serenMode >= 0 && fieldId == net.swordie.ms.constants.BossConstants.serenP1Field(serenMode)) {
            instance.setInstanceHelper(new SerenInstanceHelper());
        }
        if (fieldId == net.swordie.ms.constants.BossConstants.AKECHI_FIELD) {
            instance.setInstanceHelper(new AkechiInstanceHelper());
        }
        if (fieldId == GuardianAngelSlimeInstanceHelper.WAITING_ROOM
                || fieldId == net.swordie.ms.constants.BossConstants.GUARDIAN_ANGEL_SLIME_NORMAL_FIELD
                || fieldId == net.swordie.ms.constants.BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS_FIELD) {
            instance.setInstanceHelper(new GuardianAngelSlimeInstanceHelper());
        }

        int kalosMode = net.swordie.ms.constants.BossConstants.getKalosMode(fieldId);
        if (kalosMode >= 0 && net.swordie.ms.constants.BossConstants.KALOS_P1_FIELDS[kalosMode] == fieldId) {
            instance.setInstanceHelper(new KalosInstanceHelper());
        }

        int[] kalingStage = net.swordie.ms.constants.BossConstants.getKalingStage(fieldId);
        if (kalingStage != null && kalingStage[1] == 0) {
            instance.setInstanceHelper(new KalingInstanceHelper());
        }

        for (int m = 0; m < 4; m++) {
            if (fieldId == net.swordie.ms.constants.BossConstants.ADVERSARY_FIELD_BASE[m] + 100) {
                instance.setInstanceHelper(new FirstAdversaryInstanceHelper());
            }
        }
        for (int m = 0; m < 2; m++) {
            if (fieldId == net.swordie.ms.constants.BossConstants.LIMBO_FIELD_BASE[m] + 100) {
                instance.setInstanceHelper(new LimboInstanceHelper());
            }
            if (fieldId == net.swordie.ms.constants.BossConstants.BALDRIX_FIELDS[m][0]) {
                instance.setInstanceHelper(new BaldrixInstanceHelper());
            }
            if (fieldId == net.swordie.ms.constants.BossConstants.MALEFIC_FIELDS[m][0]) {
                instance.setInstanceHelper(new MaleficStarInstanceHelper());
            }
            if (fieldId == net.swordie.ms.constants.BossConstants.JUPITER_FIELDS[m][0]) {
                instance.setInstanceHelper(new JupiterInstanceHelper());
            }
            if (fieldId == net.swordie.ms.constants.BossConstants.KAI_FIELDS[m][0]) {
                instance.setInstanceHelper(new KaiInstanceHelper());
            }
        }

        // Inferno Wolf Den
        if (FieldConstants.isInfernoWolfDenField(fieldId)) {
            instance.setInstanceHelper(new InfernoWolfInstanceHelper());
        }

        //Goo Island Exploration
        if (FieldConstants.isGooExplorationField(fieldId)){
            instance.setInstanceHelper(new GooExplorationInstanceHelper());
        }

        //Closed Lab (Used for Quest: [Magatia's Secret] Zenumist and the Missing Alchemist)
        if (fieldId == FieldConstants.CLOSED_LAB_FIELD) {
            instance.setInstanceHelper(new ClosedLabInstanceHelper());
        }

        // the party is warped in before setup runs: the first map's onWarp/onMobSpawn need the instance already
        if (instance.getInstanceHelper() != null) {
            instance.getInstanceHelper().setInstance(instance);
        }
        return instance.getInstanceHelper();
    }

}
