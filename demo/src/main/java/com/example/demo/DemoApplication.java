package com.example.demo;

import com.example.demo.entity.Venda;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

        Venda venda = new Venda();

        venda.dataHoraTest();
	}
}
