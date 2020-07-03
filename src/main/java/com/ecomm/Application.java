package com.ecomm;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;


@SpringBootApplication
@EnableKafka
public class Application {

        private static final Logger logger = LoggerFactory.getLogger(Application.class);


        @Value(value = "${kafka.bootstrapAddress}")
        private String bootstrapAddress;

        @Value(value = "${message.topic.name}")
        private String topicName;

        public static void main(String[] args) {
                SpringApplication.run(Application.class, args);
        }
		
		@Bean
        public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory() {
                ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
                factory.setConsumerFactory(consumerFactory());
                factory.setConcurrency(3);
                factory.getContainerProperties().setPollTimeout(3000);
                logger.info("kafkaListenerContainerFactory" + factory);
                return factory;
        }

        @Bean
        public ConsumerFactory<Integer, String> consumerFactory() {
                return new DefaultKafkaConsumerFactory<>(consumerConfigs());
        }

        @Bean
        public Map<String, Object> consumerConfigs() {
                Map<String, Object> props = new HashMap<>();
                props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
                logger.info("bootstrapAddress:" + bootstrapAddress);
                props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
                props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
                return props;
        }


}

