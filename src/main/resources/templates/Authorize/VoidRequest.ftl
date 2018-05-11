<createTransactionRequest xmlns='AnetApi/xml/v1/schema/AnetApiSchema.xsd'>
    <merchantAuthentication>
        <name>${name}</name>
        <transactionKey>${transactionKey}</transactionKey>
    </merchantAuthentication>
    <transactionRequest>
        <transactionType>voidTransaction</transactionType>
        <refTransId>${transactionId}</refTransId>
    </transactionRequest>
</createTransactionRequest>