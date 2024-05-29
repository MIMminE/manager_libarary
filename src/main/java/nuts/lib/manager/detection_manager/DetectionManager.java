package nuts.lib.manager.detection_manager;

import nuts.lib.manager.executor_manager.ScheduleExecutorManager;
import nuts.lib.manager.executor_manager.executor.ExecutorBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DetectionManager<T> {

    private final ScheduleExecutorManager scheduleExecutorManager = new ScheduleExecutorManager(ExecutorBuilder.newSingleThreadScheduledExecutor("detect_manager"));
    private final List<Detector<T>> detectors;
    private final DetectSource<T> detectSource;
    private final long intervalPoll;
    private final String scheduleName;


    public DetectionManager(List<Detector<T>> detectors, DetectSource<T> detectSource, long intervalPoll, String scheduleName) {
        this.detectors = detectors;
        this.detectSource = detectSource;
        this.intervalPoll = intervalPoll;
        this.scheduleName = scheduleName;
    }

    public void run() throws ExecutionException, InterruptedException {
        scheduleExecutorManager.schedule(() -> {
            List<T> postProcessTargets = new ArrayList<>();

            detectSource.poll().forEach(detectElement -> {
                detectors.stream()
                        .filter(detector -> detector.condition(detectElement))
                        .findFirst()
                        .ifPresent(detector -> {
                            detector.apply(detectElement);
                            postProcessTargets.add(detectElement);
                        });
            });
            System.out.println(postProcessTargets);
            detectSource.postProcess(postProcessTargets);
        }, intervalPoll, scheduleName);
    }
}
