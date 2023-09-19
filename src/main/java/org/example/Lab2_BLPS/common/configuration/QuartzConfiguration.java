package org.example.Lab2_BLPS.common.configuration;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.service.mail.service.MailService;
import org.example.Lab2_BLPS.service.mail.service.scheduler.SpamNewUsers;
import org.example.Lab2_BLPS.service.report.scheduler.UnbanUser;
import org.example.Lab2_BLPS.service.report.service.ReportService;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class QuartzConfiguration {

    private final MailService mailService;
    private final ReportService reportService;

    @Bean
    public JobDetailFactoryBean unbanUserJobDetail() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(UnbanUser.class);
        factory.setDurability(true);
        factory.setJobDataAsMap(Map.of("reportService", reportService));
        return factory;
    }

    @Bean
    public SimpleTriggerFactoryBean unbanUserJobDetailTrigger(JobDetail unbanUserJobDetail) {
        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
        factory.setJobDetail(unbanUserJobDetail);
        factory.setStartDelay(3000);
        factory.setRepeatInterval(3000);
        return factory;
    }

    @Bean
    public JobDetailFactoryBean spamNewUsersJobDetail() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(SpamNewUsers.class);
        factory.setDurability(true);
        factory.setJobDataAsMap(Map.of("mailService", mailService));
        return factory;
    }

    @Bean
    public SimpleTriggerFactoryBean spamNewUsersJobDetailTrigger(JobDetail spamNewUsersJobDetail) {
        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
        factory.setJobDetail(spamNewUsersJobDetail);
        factory.setStartDelay(500);
        factory.setRepeatInterval(5000);
        return factory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(Trigger unbanUserJobDetailTrigger, Trigger spamNewUsersJobDetailTrigger) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setTriggers(unbanUserJobDetailTrigger, spamNewUsersJobDetailTrigger);
        return factory;
    }
}
