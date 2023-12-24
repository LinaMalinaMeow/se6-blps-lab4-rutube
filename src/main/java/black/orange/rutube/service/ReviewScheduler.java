package black.orange.rutube.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewScheduler {
    private final NotifyAdminsForReviewTask notifyAdminsForReviewTask;

    @Scheduled(cron = "*/50 * * * * *")
    public void scheduleReview() {
        notifyAdminsForReviewTask.start();
    }
}
