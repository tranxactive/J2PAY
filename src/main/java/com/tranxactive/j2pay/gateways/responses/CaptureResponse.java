package com.tranxactive.j2pay.gateways.responses;

import com.tranxactive.j2pay.gateways.parameters.Currency;
import com.tranxactive.j2pay.gateways.parameters.CustomerCard;
import com.tranxactive.j2pay.gateways.parameters.ParamList;
import org.json.JSONObject;

/**
 *
 * @author dwamara
 */
public class CaptureResponse extends CoreResponse {

    private float amount = 0.0f;
    private JSONObject voidParams = null;

    private Currency currencyCode = null;

    private String cardExpiryYear = null;
    private String cardExpiryMonth = null;
    private String cardFirst6 = null;
    private String cardLast4 = null;
    private String maskedCard = null;

    private JSONObject refundParams = null;

    public CaptureResponse() {
        this.success = true;
    }

    public CaptureResponse(
            String message,
            float amount,
            Currency currencyCode,
            String cardExpiryYear,
            String cardExpiryMonth,
            String cardFirst6,
            String cardLast4,
            String maskedCard,
            String transactionId,
            JSONObject voidParams,
            JSONObject refundParams,
            JSONObject gatewayResponse
    ) {
        this.success = true;
        this.message = message;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.cardExpiryYear = cardExpiryYear;
        this.cardExpiryMonth = cardExpiryMonth;
        this.cardFirst6 = cardFirst6;
        this.cardLast4 = cardLast4;
        this.maskedCard = maskedCard;
        this.voidParams = voidParams;
        this.refundParams = refundParams;
        this.gatewayResponse = gatewayResponse;
    }

    /**
     * @return the currencyCode
     */
    public Currency getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @param currencyCode the currencyCode to set
     */
    public void setCurrencyCode(Currency currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return the cardExpiryYear
     */
    public String getCardExpiryYear() {
        return cardExpiryYear;
    }

    /**
     * @param cardExpiryYear the cardExpiryYear to set
     */
    public void setCardExpiryYear(String cardExpiryYear) {
        this.cardExpiryYear = cardExpiryYear;
    }

    /**
     * @return the cardExpiryMonth
     */
    public String getCardExpiryMonth() {
        return cardExpiryMonth;
    }

    /**
     * @param cardExpiryMonth the cardExpiryMonth to set
     */
    public void setCardExpiryMonth(String cardExpiryMonth) {
        this.cardExpiryMonth = cardExpiryMonth;
    }

    /**
     * @return the cardFirst6
     */
    public String getCardFirst6() {
        return cardFirst6;
    }

    /**
     * @param cardFirst6 the cardFirst6 to set
     */
    public void setCardFirst6(String cardFirst6) {
        this.cardFirst6 = cardFirst6;
    }

    /**
     * @return the cardLast4
     */
    public String getCardLast4() {
        return cardLast4;
    }

    /**
     * @param cardLast4 the cardLast4 to set
     */
    public void setCardLast4(String cardLast4) {
        this.cardLast4 = cardLast4;
    }

    /**
     * @return the maskedCard
     */
    public String getMaskedCard() {
        return maskedCard;
    }

    /**
     * @param maskedCard the maskedCard to set
     */
    public void setMaskedCard(String maskedCard) {
        this.maskedCard = maskedCard;
    }

    /**
     * @return the refundParams
     */
    public JSONObject getRefundParams() {
        return refundParams;
    }

    /**
     * @param refundParams the refundParams to set
     */
    public void setRefundParams(JSONObject refundParams) {
        this.refundParams = refundParams;
    }

    public void setCardValuesFrom(CustomerCard customerCard) {
        this.setCardFirst6(customerCard.getFirst6());
        this.setCardLast4(customerCard.getLast4());
        this.setMaskedCard(customerCard.getMaskedCard());
        this.setCardExpiryYear(customerCard.getExpiryYear());
        this.setCardExpiryMonth(customerCard.getExpiryMonth());
    }

    /**
     * @return the amount
     */
    public float getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(float amount) {
        this.amount = amount;
    }

    /**
     * @return the voidParams
     */
    public JSONObject getVoidParams() {
        return voidParams;
    }

    /**
     * @param voidParams the voidParams to set
     */
    public void setVoidParams(JSONObject voidParams) {
        this.voidParams = voidParams;
    }

    @Override
    public JSONObject getResponse() {

        return new JSONObject()
                .put(ParamList.LIBRARY_RESPONSE.getName(), new JSONObject()
                        .put(ParamList.SUCCESS.getName(), this.success)
                        .put(ParamList.MESSAGE.getName(), this.message)
                        .put(ParamList.AMOUNT.getName(), this.getAmount())
                        .put(ParamList.CURRENCY_CODE.getName(), this.currencyCode)
                        .put(ParamList.CARD_EXPIRY_YEAR.getName(), this.cardExpiryYear)
                        .put(ParamList.CARD_EXPIRY_MONTH.getName(), this.cardExpiryMonth)
                        .put(ParamList.CARD_FIRST_6.getName(), this.cardFirst6)
                        .put(ParamList.CARD_LAST_4.getName(), this.cardLast4)
                        .put(ParamList.MASKED_CARD.getName(), this.maskedCard)
                        .put(ParamList.TRANSACTION_ID.getName(), this.getTransactionId())
                        .put(ParamList.VOID_PARAMS.getName(), this.getVoidParams() != null ? this.getVoidParams() : JSONObject.NULL)
                        .put(ParamList.REFUND_PARAMS.getName(), this.refundParams != null ? this.refundParams : JSONObject.NULL)
                )
                .put(ParamList.GATEWAY_RESPONSE.getName(), this.gatewayResponse != null ? this.gatewayResponse : JSONObject.NULL);
    }
}
