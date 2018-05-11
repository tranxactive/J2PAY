<createTransactionRequest xmlns='AnetApi/xml/v1/schema/AnetApiSchema.xsd'>
    <merchantAuthentication>
        <name>${name}</name>
        <transactionKey>${transactionKey}</transactionKey>
    </merchantAuthentication>
    <transactionRequest>
        <transactionType>authCaptureTransaction</transactionType>
        <amount>${amount}</amount>
        <profile>
            <customerProfileId>${customerProfileId}</customerProfileId>
            <paymentProfile>
                <paymentProfileId>${paymentProfileId}</paymentProfileId>
            </paymentProfile>
        </profile>
    </transactionRequest>
</createTransactionRequest>