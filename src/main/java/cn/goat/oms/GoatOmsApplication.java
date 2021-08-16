package cn.goat.oms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.goat.oms.mapper")
public class GoatOmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoatOmsApplication.class, args);
	}

}
