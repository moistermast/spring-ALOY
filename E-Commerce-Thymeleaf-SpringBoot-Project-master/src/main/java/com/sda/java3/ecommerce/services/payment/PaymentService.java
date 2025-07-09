package com.sda.java3.ecommerce.services.payment;

import java.math.BigDecimal;

public interface PaymentService {
    
    /**
     * Process payment using available gateways in Pakistan
     */
    PaymentResult processPayment(PaymentRequest request);
    
    /**
     * Get available payment methods for Pakistan
     */
    PaymentMethods getAvailablePaymentMethods();
    
    /**
     * Validate payment request
     */
    boolean validatePaymentRequest(PaymentRequest request);
}

class PaymentRequest {
    private BigDecimal amount;
    private String currency;
    private String paymentMethod; // "paypal", "2checkout", "easypaisa", "jazzcash"
    private String customerEmail;
    private String customerName;
    private String orderId;
    
    // Getters and setters
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
}

class PaymentResult {
    private boolean success;
    private String transactionId;
    private String message;
    private String gatewayResponse;
    
    // Getters and setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getGatewayResponse() { return gatewayResponse; }
    public void setGatewayResponse(String gatewayResponse) { this.gatewayResponse = gatewayResponse; }
}

class PaymentMethods {
    private boolean paypalAvailable = true;
    private boolean twoCheckoutAvailable = true;
    private boolean easyPaisaAvailable = true;
    private boolean jazzCashAvailable = true;
    private boolean bankTransferAvailable = true;
    
    // Getters
    public boolean isPaypalAvailable() { return paypalAvailable; }
    public boolean isTwoCheckoutAvailable() { return twoCheckoutAvailable; }
    public boolean isEasyPaisaAvailable() { return easyPaisaAvailable; }
    public boolean isJazzCashAvailable() { return jazzCashAvailable; }
    public boolean isBankTransferAvailable() { return bankTransferAvailable; }
} 