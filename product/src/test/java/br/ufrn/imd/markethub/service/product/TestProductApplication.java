package br.ufrn.imd.markethub.service.product;

import br.ufrn.imd.markethub.service.product.base.TestInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class TestProductApplication extends ProductApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ProductApplication.class)
                .initializers(new TestInitializer())
                .profiles("test")
                .run(args);
    }

}
