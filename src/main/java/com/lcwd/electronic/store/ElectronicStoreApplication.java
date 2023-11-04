package com.lcwd.electronic.store;

import com.lcwd.electronic.store.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ElectronicStoreApplication implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;
	@Value("${normal.role.id}")
	private String normalRoleId;
	@Value("${admin.role.id}")
	private String adminRoleId;
@Autowired
 private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		try{
//			Role normalRole = Role.builder().roleId(normalRoleId).roleName("ROLE_NORMAL").build();
//			Role  adminRole= Role.builder().roleId(adminRoleId).roleName("ROLE_ADMIN").build();
//			roleRepository.save(normalRole);
//			roleRepository.save(adminRole);
//
//		}catch (Exception e){
//			e.printStackTrace();
//		}

	}



}
