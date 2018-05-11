<createTransactionRequest xmlns='AnetApi/xml/v1/schema/AnetApiSchema.xsd'>
    <merchantAuthentication>
        <name>${name}</name>
        <transactionKey>${transactionKey}</transactionKey>
    </merchantAuthentication>
    <transactionRequest>
        <transactionType>refundTransaction</transactionType>
        <amount>${amount}</amount>
        <payment>
            <creditCard>
                <cardNumber>${creditCardNumber}</cardNumber>
                <expirationDate>${creditCardExpirationDate}</expirationDate>
            </creditCard>
        </payment>
        <refTransId>${transactionId}</refTransId>
    </transactionRequest>
</createTransactionRequest>
