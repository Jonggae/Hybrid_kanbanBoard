package com.example.hybrid_kanbanboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HybridKanbanBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(HybridKanbanBoardApplication.class, args);
	}

}
