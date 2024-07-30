package nuts.lib.manager.data_inject_manager.rdb;

import com.navercorp.fixturemonkey.FixtureMonkey;
import jakarta.persistence.EntityManager;
import nuts.lib.manager.executor_manager.ScheduleExecutorManager;
import nuts.lib.manager.executor_manager.executor.ExecutorBuilder;
import nuts.lib.manager.fixture_manager.FixtureMonkeySupplier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RdbDataInjectManager<T> {

    private final EntityManager entityManager;
    private final ScheduleExecutorManager scheduleExecutorManager = new ScheduleExecutorManager(ExecutorBuilder.newSingleThreadScheduledExecutor("data_inject"));

    private final FixtureMonkey fixtureMonkey = FixtureMonkeySupplier.supplierDefault.get();
    private final Class<T> entity;
    private final PlatformTransactionManager transactionManager;

    public RdbDataInjectManager(EntityManager entityManager, Class<T> entity, PlatformTransactionManager transactionManager) {
        this.entityManager = entityManager;
        this.entity = entity;
        this.transactionManager = transactionManager;
    }

    public void injectSamples(int count) throws InterruptedException {
        List<T> ts = fixtureMonkey.giveMe(entity, count);
        for (T t : ts) {
            entityManager.merge(t);
        }
        entityManager.flush();
        entityManager.clear();

    }

    public void injectInterval(int count, int intervalMillis) throws ExecutionException, InterruptedException {
        scheduleExecutorManager.schedule(() -> {
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.execute(a -> {
                for (T t : fixtureMonkey.giveMe(entity, count)) {

                    entityManager.persist(t);
                }

                entityManager.flush();
                entityManager.clear();
                return null;
            });

        }, intervalMillis, "inject schedule");
    }


}
