package org.example.Lab2_BLPS.service.report.scheduler;

import org.example.Lab2_BLPS.service.report.service.ReportService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@Service
public class UnbanUser implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        ReportService reportService = (ReportService) jobExecutionContext.getJobDetail().getJobDataMap().get("reportService");
        reportService.unbanUserJob();
    }
}
