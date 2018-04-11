package com.framework.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.backend.entities.Dummy;
import com.framework.backend.repository.core.DummyRepository;
import com.framework.backend.repository.impl.BaseRepositoryImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
@Log4j2
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    @Autowired
    public CommandLineRunner commandLineRunner(ObjectMapper objectMapper, DummyRepository dummyRepository) {
        return args -> {
            dummyRepository.deleteAllInBatch();
            dummyRepository.save(Dummy.builder().name("Dummy name 1").phoneNumber("Phone number 1").build());
            dummyRepository.save(Dummy.builder().name("Dummy name 2").phoneNumber("Phone number 2").build());
        };
    }
}
