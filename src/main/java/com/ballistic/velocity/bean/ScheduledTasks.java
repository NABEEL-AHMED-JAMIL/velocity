package com.ballistic.velocity.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/* * * * * * * * * * * * * *
 * Note :- Deprecate Soon   *
 * * * * * * * * * * * * * */
@Component
public class ScheduledTasks {

    private static final Logger logger = LogManager.getLogger(ScheduledTasks.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(fixedRate = 10000)
    public void scheduleTaskWithFixedRate() {
        logger.info("Fixed Rate Task -::- Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }

    @Scheduled(fixedDelay = 10000)
    public void scheduleTaskWithFixedDelay() {
        logger.info("Fixed Delay Task -::- Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            logger.error("Ran into an error {}", ex);
            throw new IllegalStateException(ex);
        }
    }

    @Scheduled(fixedRate = 10000, initialDelay = 16000)
    public void scheduleTaskWithInitialDelay() {
        logger.info("Fixed Rate Task -::- Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }

    @Scheduled(cron = "0 * * * * ?")
    public void scheduleTaskWithCronExpression() {
        logger.info("Fixed Rate Task -::- Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }
}
