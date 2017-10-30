package ru.andreymarkelov.atlas.plugins.promconfluenceexporter.manager;

import io.prometheus.client.Collector;
import io.prometheus.client.Counter;

import java.util.ArrayList;
import java.util.List;

public class MetricCollectorImpl extends Collector implements MetricCollector {
    private final Counter clusterPanicCounter = Counter.build()
            .name("confluence_cluster_panic_count")
            .help("Cluster Panic Count")
            .create();

    //--> Labels

    private final Counter labelCreateCounter = Counter.build()
            .name("confluence_label_create_count")
            .help("Label Create Count")
            .labelNames("visibility", "prefix")
            .create();

    private final Counter labelAddCounter = Counter.build()
            .name("confluence_label_add_count")
            .help("Label Add Count")
            .labelNames("visibility", "prefix", "source", "spaceKey")
            .create();

    private final Counter labelRemoveCounter = Counter.build()
            .name("confluence_label_remove_count")
            .help("Label Remove Count")
            .labelNames("visibility", "prefix", "source", "spaceKey")
            .create();

    private final Counter labelDeleteCounter = Counter.build()
            .name("confluence_label_delete_count")
            .help("Label Delete Count")
            .labelNames("visibility", "prefix")
            .create();

    //--> Login/Logout

    private final Counter userLogoutCounter = Counter.build()
            .name("confluence_user_logout_count")
            .help("User Logout Count")
            .labelNames("username", "ip")
            .create();

    private final Counter userLoginCounter = Counter.build()
            .name("confluence_user_login_count")
            .help("User Login Count")
            .labelNames("username", "ip")
            .create();

    //--> Space

    private final Counter spaceCreateCounter = Counter.build()
            .name("confluence_space_create_count")
            .help("Space Create Count")
            .labelNames("username")
            .create();

    private final Counter spaceDeleteCounter = Counter.build()
            .name("confluence_space_delete_count")
            .help("Space Delete Count")
            .labelNames("username")
            .create();

    @Override
    public void clusterPanicCounter() {
        clusterPanicCounter.inc();
    }

    //--> Labels

    @Override
    public void labelCreateCounter(String visibility, String prefix) {
        labelCreateCounter.labels(visibility, prefix).inc();
    }

    @Override
    public void labelRemoveCounter(String visibility, String prefix, String source, String spaceKey) {
        labelRemoveCounter.labels(visibility, prefix, source, spaceKey).inc();
    }

    @Override
    public void labelAddCounter(String visibility, String prefix, String source, String spaceKey) {
        labelAddCounter.labels(visibility, prefix, source, spaceKey).inc();
    }

    @Override
    public void labelDeleteCounter(String visibility, String prefix) {
        labelDeleteCounter.labels(visibility, prefix).inc();
    }

    //--> Login/Logout

    @Override
    public void userLoginCounter(String username, String ip) {
        userLoginCounter.labels(username, ip).inc();
    }

    @Override
    public void userLogoutCounter(String username, String ip) {
        userLogoutCounter.labels(username, ip).inc();
    }

    //--> Space


    @Override
    public void spaceCreateCounter(String username) {
        spaceCreateCounter.labels(username).inc();
    }

    @Override
    public void spaceDeleteCounter(String username) {
        spaceDeleteCounter.labels(username).inc();
    }

    @Override
    public Collector getCollector() {
        return this;
    }

    //--> Collect

    @Override
    public List<MetricFamilySamples> collect() {
        List<MetricFamilySamples> result = new ArrayList<>();
        result.addAll(clusterPanicCounter.collect());
        result.addAll(labelCreateCounter.collect());
        result.addAll(labelRemoveCounter.collect());
        result.addAll(labelAddCounter.collect());
        result.addAll(labelDeleteCounter.collect());
        result.addAll(userLoginCounter.collect());
        result.addAll(userLogoutCounter.collect());
        return result;
    }
}