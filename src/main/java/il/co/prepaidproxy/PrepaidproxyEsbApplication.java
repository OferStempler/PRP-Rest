package il.co.prepaidproxy;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class PrepaidproxyEsbApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrepaidproxyEsbApplication.class, args);
	}
}
