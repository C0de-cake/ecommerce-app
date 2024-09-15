package fr.codecake.ecom.order.infrastructure.secondary.service.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import fr.codecake.ecom.order.domain.order.CartPaymentException;
import fr.codecake.ecom.order.domain.order.aggregate.DetailCartItemRequest;
import fr.codecake.ecom.order.domain.order.vo.StripeSessionId;
import fr.codecake.ecom.order.domain.user.aggregate.User;
import fr.codecake.ecom.product.domain.aggregate.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StripeService {

  @Value("${application.stripe.api-key}")
  private String apiKey;

  @Value("${application.client-base-url}")
  private String clientBaseUrl;

  public StripeService() {
  }

  @PostConstruct
  public void setApiKey() {
    Stripe.apiKey = apiKey;
  }

  public StripeSessionId createPayment(User connectedUser,
                                       List<Product> productsInformation,
                                       List<DetailCartItemRequest> items) {
    SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
      .setMode(SessionCreateParams.Mode.PAYMENT)
      .putMetadata("user_public_id", connectedUser.getUserPublicId().value().toString())
      .setCustomerEmail(connectedUser.getEmail().value())
      .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
      .setSuccessUrl(this.clientBaseUrl + "/cart/success?session_id={CHECKOUT_SESSION_ID}")
      .setCancelUrl(this.clientBaseUrl + "/cart/failure");

    for (DetailCartItemRequest itemRequest : items) {
      Product productDetails = productsInformation.stream()
        .filter(product -> product.getPublicId().value().equals(itemRequest.productId().value()))
        .findFirst().orElseThrow();

      SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
        .putMetadata("product_id", productDetails.getPublicId().value().toString())
        .setName(productDetails.getName().value())
        .build();

      SessionCreateParams.LineItem.PriceData linePriceData = SessionCreateParams.LineItem.PriceData.builder()
        .setUnitAmountDecimal(BigDecimal.valueOf(Double.valueOf(productDetails.getPrice().value()).longValue() * 100))
        .setProductData(productData)
        .setCurrency("EUR")
        .build();

      SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
        .setQuantity(itemRequest.quantity())
        .setPriceData(linePriceData)
        .build();

      sessionBuilder.addLineItem(lineItem);
    }

    return createSession(sessionBuilder.build());
  }

  private StripeSessionId createSession(SessionCreateParams sessionInformation) {
    try {
      Session session = Session.create(sessionInformation);
      return new StripeSessionId(session.getId());
    } catch (StripeException se) {
      throw new CartPaymentException("Error while creating Stripe session");
    }
  }
}
