package io.yfjz.activemq;

import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

/**
 * @author Woods
 * @email oceans.woods@gmail.com
 * @date 2018-08-22 23:59:55
 */

@Configuration
@EnableJms
public class BrokerConfig {
    /*@Bean(initMethod = "start",destroyMethod = "stop")
    public BrokerService service() throws Exception {
        final BrokerService broker = new BrokerService();
        broker.addConnector( "ws://0.0.0.0:61616");
        broker.setUseJmx(true);
        broker.setBrokerName("YFJZ-Broker");
        broker.getManagementContext().setCreateConnector(false);

        final List<PolicyEntry> policyEntries = new ArrayList<>();
        final PolicyEntry entry = new PolicyEntry();
        entry.setTopic("register");
        entry.setTempTopic(true);
        entry.setMemoryLimit(1024 * 4);
        policyEntries.add(entry);
        PolicyMap policyMap = new PolicyMap();
        policyMap.setPolicyEntries(policyEntries);

        broker.setDestinationPolicy(policyMap);


        PersistenceAdapter adapter = new KahaDBPersistenceAdapter();
        adapter.setDirectory(new File("KahaDB"));
        ((KahaDBPersistenceAdapter) adapter).setJournalMaxFileLength(1024*1024*256);
        broker.setPersistenceAdapter(adapter);
        broker.setPersistent(true);
        broker.setShutdownHooks( Collections.< Runnable >singletonList( new SpringContextHook() ) );

        return broker;
    }*/
}

