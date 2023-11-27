package com.example.server.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean(destroyMethod = "shutdown")
    public HazelcastInstance hazelcastInstance(@Qualifier("hazelcastConfig") Config config) {
        return Hazelcast.getOrCreateHazelcastInstance(config);
    }

    @Bean("hazelcastConfig")
    public Config hazelcastConfig() {
        Config config = new Config("reservations");
        return config;
    }
}



