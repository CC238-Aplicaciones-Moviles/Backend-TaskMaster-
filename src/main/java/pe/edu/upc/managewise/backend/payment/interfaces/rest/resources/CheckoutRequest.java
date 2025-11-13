package pe.edu.upc.managewise.backend.payment.interfaces.rest.resources;

/**
 * Request resource for creating a Stripe checkout session.
 */
public record CheckoutRequest(
        String membershipType,
        Long amount,
        String successUrl,
        String cancelUrl
) {
}
