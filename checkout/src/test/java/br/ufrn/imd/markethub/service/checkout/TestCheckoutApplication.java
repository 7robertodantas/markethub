package br.ufrn.imd.markethub.service.checkout;

import br.ufrn.imd.markethub.service.checkout.base.TestInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class TestCheckoutApplication extends CheckoutApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(CheckoutApplication.class)
                .initializers(new TestInitializer())
                .profiles("test")
                .run(args);
    }

}
