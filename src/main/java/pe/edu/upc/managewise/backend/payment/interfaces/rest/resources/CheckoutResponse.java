package pe.edu.upc.managewise.backend.payment.interfaces.rest.resources;

/**
 * Response resource for checkout session creation.
 */
public record CheckoutResponse(
        String checkoutUrl
) {
}
