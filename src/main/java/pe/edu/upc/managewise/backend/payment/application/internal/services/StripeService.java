package pe.edu.upc.managewise.backend.payment.application.internal.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service for handling Stripe payment operations.
 * This service creates checkout sessions for membership payments.
 */
@Service
public class StripeService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    /**
     * Creates a Stripe checkout session for membership payment.
     *
     * @param membershipType The type of membership (Basic, Premium, etc.)
     * @param amount The amount to charge in cents (e.g., 1000 = $10.00)
     * @param successUrl The URL to redirect after successful payment
     * @param cancelUrl The URL to redirect if payment is cancelled
     * @return The checkout session URL
     * @throws StripeException if there's an error creating the session
     */
    public String createCheckoutSession(String membershipType, Long amount, String successUrl, String cancelUrl) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(amount) // Amount in cents
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(membershipType + " Membership")
                                                                .setDescription("TaskMaster " + membershipType + " Plan")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }
}
