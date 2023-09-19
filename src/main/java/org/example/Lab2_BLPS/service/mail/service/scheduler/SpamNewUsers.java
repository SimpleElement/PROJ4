package org.example.Lab2_BLPS.service.mail.service.scheduler;

import org.example.Lab2_BLPS.service.mail.service.MailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@Service
public class SpamNewUsers implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        MailService reportService = (MailService) jobExecutionContext.getJobDetail().getJobDataMap().get("mailService");
        reportService.spamNewUsers();
    }
}
