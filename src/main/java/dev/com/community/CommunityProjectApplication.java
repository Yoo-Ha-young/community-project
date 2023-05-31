package dev.com.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 자동 업데이트
@SpringBootApplication
public class CommunityProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityProjectApplication.class, args);
    }
}