/**
 * @author hengxin
 * @date May 8, 2014
 * @description receive messages of type {@link AtomicityMessage} from {@link MessagingService}
 * and dispatch them to {@link AtomicityRegisterClient} or {@link AtomicityRegisterServer}.
 * @see MessagingService
 */
package io.github.hengxin.distributed_mobile_memo.sharedmemory.atomicity.message;

import io.github.hengxin.distributed_mobile_memo.sharedmemory.architecture.communication.IPMessage;
import io.github.hengxin.distributed_mobile_memo.sharedmemory.architecture.communication.IReceiver;
import io.github.hengxin.distributed_mobile_memo.sharedmemory.atomicity.AbstractAtomicityRegisterClient;
import io.github.hengxin.distributed_mobile_memo.sharedmemory.atomicity.AtomicityRegisterClientFactory;
import io.github.hengxin.distributed_mobile_memo.sharedmemory.atomicity.AtomicityRegisterServer;

public enum AtomicityMessagingService implements IReceiver {
    INSTANCE;

    /**
     * dispatch messages of type {@link AtomicityMessage}
     * to {@link AbstractAtomicityRegisterClient} or {@link AtomicityRegisterServer}.
     */
    @Override
    public void onReceive(IPMessage msg) {
        // it only receives messages of type type {@link AtomicityMessage} from {@link MessagingService}
//        assert (msg instanceof AtomicityMessage);

        /**
         * Injecting artificial delay to simulate latency variances.
         */
//        try {
//            Thread.sleep(new Random().nextInt(10));
//        } catch (InterruptedException ie) {
//            ie.printStackTrace();
//        }

        /**
         *  {@link AtomicityRegisterServer} is responsible for handling with messages
         *  of types of {@link AtomicityReadPhaseMessage} and {@link AtomicityWritePhaseMessage}
         *  while
         *  {@link AtomicityRegisterClient} is responsible for handling with messages
         *  of types of {@link AtomicityReadPhaseAckMessage} and {@link AtomicityWritePhaseAckMessage}
         */
        if (msg instanceof AtomicityReadPhaseMessage || msg instanceof AtomicityWritePhaseMessage)
            AtomicityRegisterServer.INSTANCE.handleAtomicityMessage((AtomicityMessage) msg);
        else {// (msg instanceof AtomicityReadAckPhaseMessage || msg instanceof AtomicityWriteAckPhaseMessage)
            AbstractAtomicityRegisterClient client = null;
            try {
                client = AtomicityRegisterClientFactory.INSTANCE.getAtomicityRegisterClient();
            } catch (AtomicityRegisterClientFactory.NoSuchAtomicAlgorithmSupportedException nsaas) {
                nsaas.printStackTrace();
                System.exit(1);
            }

            client.handleAtomicityMessage((AtomicityMessage) msg);
        }
    }

}
