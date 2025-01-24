package br.ufrn.imd.markethub.service.wallet;

import br.ufrn.imd.markethub.service.wallet.base.TestInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TestWalletApplication extends WalletApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(WalletApplication.class)
				.initializers(new TestInitializer())
				.profiles("test")
				.run(args);
	}
}
