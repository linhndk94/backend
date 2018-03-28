package com.framework.backend;

import com.framework.backend.repository.impl.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
//
//    @Bean
//    @Autowired
//    public CommandLineRunner commandLineRunner(DummyRepository dummyRepository, RoleRepository roleRepository) {
//        return args -> {
//            Dummy dummy = dummyRepository.getOne(1L);
//            List<Role> roles = roleRepository.findAll();
//            dummy.setRoles(roles);
//            dummyRepository.save(dummy);
//        };
//    }
}
