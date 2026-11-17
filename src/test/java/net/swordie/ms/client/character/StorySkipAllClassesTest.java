package net.swordie.ms.client.character;

import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.JobConstants.JobEnum;
import net.swordie.ms.constants.JobConstants.LoginJob;
import net.swordie.ms.loaders.DataTable;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * The story skip's decisions for every creatable class, from each job its finish can run at: the class's beginner job
 * and every 1st job its tutorial (or creation) can give.
 */
public class StorySkipAllClassesTest {

    private static final int[] EXPLORER_SUB_JOBS = {0, 1, 2};
    // classes whose Lv. 30 story quests aren't in story-skip.tsv yet (Lara's and Hoyoung's town stories are skipped
    // by story-skip-areas.tsv): the skip still levels and advances them
    private static final Set<Integer> NO_STORY_ROW = Set.of(JobEnum.KHALI.getJobId(), JobEnum.REN.getJobId(),
            JobEnum.MO_XUAN.getJobId(), JobEnum.LYNN.getJobId(), JobEnum.SIA_ASTELLE.getJobId(),
            JobEnum.EREL_LIGHT.getJobId(), JobEnum.LARA.getJobId(), JobEnum.HO_YOUNG.getJobId());

    @Test
    public void everyClassSkipsToItsSecondJob() {
        Set<Integer> kits = new HashSet<>();
        for (DataTable.Row r : DataTable.read("job-starter-kits").rows()) {
            kits.add(r.i("job"));
        }
        List<String> failures = new ArrayList<>();
        for (LoginJob lj : LoginJob.values()) {
            if (lj.getFlag() == LoginJob.JobFlag.DISABLED.getFlag() || !CreatableClasses.isLoginJob(lj.name())) {
                continue; // parked classes (creatable-classes.tsv) have no path to skip to
            }
            int begin = lj.getBeginJob().getBeginnerJobId();
            if (JobConstants.isPinkBean(begin) || JobConstants.isYeti(begin)) {
                if (StorySkipModule.hasSkip(lj.getBeginJob().getJobId())) {
                    failures.add(lj + ": offered a skip it can't finish");
                }
                continue;
            }
            if (JobConstants.isZero(begin)) {
                if (!StorySkipModule.hasSkip(lj.getBeginJob().getJobId())
                        || StorySkipModule.rowOf(JobEnum.ZERO_1.getJobId()) < 0) {
                    failures.add(lj + ": no Zero skip");
                }
                continue;
            }
            for (int job : jobsAtFinish(begin)) {
                for (int subJob : begin == JobEnum.BEGINNER.getJobId() ? EXPLORER_SUB_JOBS : new int[]{0}) {
                    check(lj, job, subJob, kits, failures);
                }
            }
        }
        Assert.assertTrue(String.join("\n", failures), failures.isEmpty());
    }

    /** the beginner job and every 1st job of a class that starts from it */
    private static Set<Integer> jobsAtFinish(int beginner) {
        Set<Integer> jobs = new TreeSet<>(Set.of(beginner));
        for (JobPathModule.ClassChoice c : JobPathModule.getAllChoices()) {
            List<Integer> path = JobPathModule.getJobPath(c.getFinalJob());
            if (!path.isEmpty() && JobEnum.getJobById(path.get(0)).getBeginnerJobId() == beginner) {
                jobs.add(path.get(0));
            }
        }
        return jobs;
    }

    private static void check(LoginJob lj, int job, int subJob, Set<Integer> kits, List<String> failures) {
        String who = lj + " at job " + job + (subJob != 0 ? " sub " + subJob : "");
        if (!StorySkipModule.hasSkip(job)) {
            failures.add(who + ": no skip");
            return;
        }
        int[] cards = StorySkipModule.cardsFor(job, subJob);
        List<Integer> finals = StorySkipModule.finalJobsFor(job, subJob);
        boolean explorerCards = job == JobEnum.BEGINNER.getJobId() && subJob == 0;
        if (finals.isEmpty() || cards == null && finals.size() != 1 && !explorerCards) {
            failures.add(who + ": no single class or class cards (" + finals + ")");
            return;
        }
        for (int finalJob : finals) {
            List<Integer> path = JobPathModule.getJobPath(finalJob);
            if (path.isEmpty() || JobPathModule.getChoice(finalJob) == null) {
                failures.add(who + " -> " + finalJob + ": no job path");
                continue;
            }
            if (JobConstants.isBeginnerJob(job) ? JobEnum.getJobById(path.get(0)).getBeginnerJobId() != job
                    : !path.contains(job)) {
                failures.add(who + " -> " + finalJob + ": the path " + path + " isn't this class's");
            }
            int[] levels = JobPathModule.getAdvanceLevels(path);
            int target = -1;
            for (int i = 0; i < path.size() && i < levels.length; i++) {
                if (levels[i] <= StorySkipModule.TARGET_LEVEL) {
                    target = path.get(i);
                }
            }
            if (target < 0 || JobEnum.getJobById(target) == null || JobConstants.getAdvancementTier(target) < 2) {
                failures.add(who + " -> " + finalJob + ": no 2nd job at Lv. 30 (" + target + ")");
                continue;
            }
            if (StorySkipModule.rowOf(target) < 0
                    && !NO_STORY_ROW.contains((int) JobEnum.getJobById(target).getBeginnerJobId())) {
                failures.add(who + " -> " + finalJob + ": no story quests for job " + target + " (story-skip.tsv)");
            }
            if (JobConstants.isBeginnerJob(job) && !kits.contains(finalJob)) {
                failures.add(who + " -> " + finalJob + ": no starter kit (job-starter-kits.tsv)");
            }
        }
    }
}
