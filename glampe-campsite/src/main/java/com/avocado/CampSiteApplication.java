package com.avocado;

import com.avocado.client.email.EmailEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableJpaAuditing
public class CampSiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampSiteApplication.class, args);
    }

//    @Bean
//    CommandLineRunner runner(KafkaTemplate<String, Ob> kafkaTemplate){
//        return args -> {
//            kafkaTemplate.send("notification", EmailEntity
//                    .builder()
//                    .to("huatanthinh1207@gmail.com")
//                            .subject("Fetch Campsite")
//                            .content("Fetch campsite")
//                    .build()
//            );
//        };
//    }
}
