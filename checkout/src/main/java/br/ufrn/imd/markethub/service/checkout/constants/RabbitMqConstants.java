package br.ufrn.imd.markethub.service.checkout.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RabbitMqConstants {
    public static final String EXCHANGE_NAME = "checkout";
    public static final String CHECKOUT_SUBMITTED = "checkout_submitted";
    public static final String CHECKOUT_DONE = "checkout_done";
    public static final String CHECKOUT_FAILED = "checkout_failed";
}
