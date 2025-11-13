package pe.edu.upc.managewise.backend.payment.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.managewise.backend.payment.application.internal.services.StripeService;
import pe.edu.upc.managewise.backend.payment.interfaces.rest.resources.CheckoutRequest;
import pe.edu.upc.managewise.backend.payment.interfaces.rest.resources.CheckoutResponse;

/**
 * REST controller for payment operations.
 * Handles Stripe checkout session creation for membership payments.
 */
@RestController
@RequestMapping(value = "/api/v1/payments")
@Tag(name = "Payments", description = "Payment Management Endpoints")
public class PaymentController {

    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    /**
     * Creates a Stripe checkout session for membership payment.
     *
     * @param request The checkout request containing membership details
     * @return ResponseEntity with checkout URL
     */
    @PostMapping("/create-checkout-session")
    @Operation(summary = "Create Stripe checkout session", description = "Creates a Stripe checkout session for membership payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Checkout session created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<CheckoutResponse> createCheckoutSession(@RequestBody CheckoutRequest request) {
        try {
            String checkoutUrl = stripeService.createCheckoutSession(
                    request.membershipType(),
                    request.amount(),
                    request.successUrl(),
                    request.cancelUrl()
            );

            return ResponseEntity.ok(new CheckoutResponse(checkoutUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
