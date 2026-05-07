package com.muc.rfid.scheduler;

import com.muc.rfid.repository.TagReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TagCleanupScheduler {

    private final TagReadRepository tagReadRepository;

    @Scheduled(fixedRate = 300000)
    public void cleanupDuplicateTags() {
        int deletedRows = tagReadRepository.deleteDuplicatesOnlyWithinLastFiveMinutes();

        if (deletedRows > 0) {
            log.info("RFID duplicate cleanup deleted {} duplicate records within last 5 minutes.", deletedRows);
        }
    }
}