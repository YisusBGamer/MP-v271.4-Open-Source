package net.swordie.ms.client.character;

import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.JobConstants.JobEnum;
import net.swordie.ms.constants.JobConstants.LoginJob;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/** Every creatable class can be advanced by the story skip / Express from the job it is created with. */
public class StartJobPathTest {

    // these begin jobs lead to several classes: the story skip asks with its class cards
    private static final Set<Integer> CARD_JOBS = Set.of(JobEnum.BEGINNER.getJobId(), JobEnum.NOBLESSE.getJobId(),
            JobEnum.CITIZEN.getJobId(), JobEnum.DEMON.getJobId());

    @Test
    public void beginnerJobsHaveOneClassPath() {
        for (LoginJob lj : LoginJob.values()) {
            int job = lj.getBeginJob().getJobId();
            if (lj.getFlag() == LoginJob.JobFlag.DISABLED.getFlag() || !CreatableClasses.isLoginJob(lj.name()) || CARD_JOBS.contains(job)
                    || JobConstants.isZero(job) || JobConstants.isPinkBean(job) || JobConstants.isYeti(job)
                    || lj.getBeginJob().getBeginnerJobId() != job) {
                continue; // no story skip path, or created with its 1st job already
            }
            Assert.assertTrue(lj + ": begin job " + job + " is not a beginner job", JobConstants.isBeginnerJob(job));
            long paths = JobPathModule.getAllChoices().stream().filter(c -> {
                List<Integer> path = JobPathModule.getJobPath(c.getFinalJob());
                var first = path.isEmpty() ? null : JobEnum.getJobById(path.get(0).shortValue());
                return first != null && first.getBeginnerJobId() == job;
            }).count();
            Assert.assertEquals(lj + ": classes starting from job " + job, 1, paths);
        }
    }

    @Test
    public void erelLightPath() {
        Assert.assertTrue(JobConstants.isBeginnerJob(JobEnum.EREL_LIGHT.getJobId()));
        Assert.assertEquals(List.of(18100, 18110, 18111, 18112),
                JobPathModule.getJobPath(JobEnum.EREL_LIGHT_4.getJobId()));
    }
}
