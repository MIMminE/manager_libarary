package nuts.lib.manager.broker_manager;

import java.lang.reflect.InvocationTargetException;

public interface BrokerManager<
        MP extends MessageProducer<?>,
        MPC extends ProducerConfig<MP>,
        MC extends MessageConsumer<?>,
        MCC extends ConsumerConfig<MC>> {

    <MPI extends MP> MPI getProducer(MPC config, Class<MPI> producerClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    <MCI extends MC> MCI getConsumer(MCC config, Class<MCI> consumerClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
