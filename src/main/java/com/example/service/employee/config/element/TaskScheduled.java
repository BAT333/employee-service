package com.example.service.employee.config.element;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TaskScheduled {



    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.HOURS)
    @CacheEvict(value = "Employees", allEntries = true)
    public void clearingClientCache() {
        log.info("Cleared 'Employees' cache on: " + LocalDateTime.now());
    }

}
