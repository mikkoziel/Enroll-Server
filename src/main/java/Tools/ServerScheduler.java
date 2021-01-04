package Tools;

import Model.Enrollment;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class ServerScheduler {
    private Scheduler scheduler;

    public ServerScheduler(){
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            this.scheduler = schedulerFactory.getScheduler();
            this.scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void addEnrollJob(Enrollment en){
        JobDetail job = JobBuilder.newJob(EnrollJob.class)
                .withIdentity(String.valueOf(en.getSchedule_id()), "Enrollment")
                .build();

        // Trigger the job to run on the next round minute
        SimpleTrigger  trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity(String.valueOf(en.getSchedule_id()), "Enrollment")
                .startAt(en.getEndDate())
                .build();

        try {
            this.scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void checkEnrollment(){

    }
}
