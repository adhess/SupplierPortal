package com.adhess.org.p2p.service.p2p_service;

import com.adhess.org.p2p.service.p2p_service.dao.P2p_order_Repository;
import com.adhess.org.p2p.service.p2p_service.dao.P2p_supplier_Repository;
import com.adhess.org.p2p.service.p2p_service.entities.P2p_order;
import com.adhess.org.p2p.service.p2p_service.entities.P2p_supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
@EnableDiscoveryClient
public class P2pServiceApplication implements CommandLineRunner {
	@Autowired
	private P2p_order_Repository p2p_order_repository;

	public static void main(String[] args) {
		SpringApplication.run(P2pServiceApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

//		P2p_order p2p_order = new P2p_order("124598", LocalDate.of(2017,5,25));
//		this.p2p_order_repository.save(p2p_order);

	}
}
