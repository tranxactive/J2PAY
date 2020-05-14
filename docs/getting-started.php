<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>Getting Started</title>
        <!-- CSS  -->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
        <link href="css/style.css" type="text/css" rel="stylesheet" media="screen,projection"/>
        <link href="css/ghpages-materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
        <link href="css/prism.css" type="text/css" rel="stylesheet" media="screen,projection"/>
    </head>
    <body>
        <?php include __DIR__ . '/nav.php' ?>
        <main>
            <div class="section green">
                <div class="container"> 
                    <div class="section">
                        <h1 class="white-text">Getting Started</h1>
                        <h4 class="light white-text text-lighten-4">Getting started will guide you how to start using J2pay quickly in very simple steps.</h4>
                    </div>                    
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <h2 class="green-text">Download</h2>
                        <p class="caption">
                            J2Pay is available on maven.<br/>
                            while writing this documentation j2pay latest stable version is 2.9.10 so we are using it.
                        </p>
                        <pre class="language-markup">
<code class="language-markup">
    &lt;dependency&gt;
        &lt;groupId&gt;com.tranxactive&lt;/groupId&gt;
        &lt;artifactId&gt;j2pay&lt;/artifactId&gt;
        &lt;version&gt;2.9.10&lt;/version&gt;
    &lt;/dependency&gt;
</code>
                        </pre>

                        <p class="caption">You can also download the jar file <a href="https://repo1.maven.org/maven2/com/tranxactive/j2pay/" target="_blank">here</a></p>
                    </div>
                    <div class="col s12">
                        <h4>Example</h4>
                        <p>In this example we will execute <b>Purchase</b> and <b>Rebill</b> transactions. First we will get the desired gateway i.e Authorize</p>
                        <pre class="language-java">
<code class="language-java">
    Gateway gateway = GatewayFactory.getGateway(AvailableGateways.AUTHORIZE);
</code></pre>
                        <p>Since we are working on test environment we will enable the test mode.</p>
                        <pre class="language-java">
<code class="language-java">
    gateway.setTestMode(true);
</code></pre>
                    </div>
                    <div class="col s12">
                        <p>Next we will ask for the library to show us what are the API paramters for this gateway</p>
                        <pre class="language-java">
<code class="language-java">
    JSONObject apiSampleParameters = gateway.getApiSampleParameters();
    System.out.println(apiSampleParameters)
    
    <span class="token comment">//output</span>
    {"name":"also called api user name / api login id","transactionKey":"the transaction key"}
</code></pre>
                        <p>As we can see in output, library is telling us that Authorize gateway requires two API parameters name and transactionKey. Now we will populate these fields by our merchant values.</p>
                        <pre class="language-java">
<code class="language-java">
    apiSampleParameters.put("name", "&lt;your account's user name here&gt;");
    apiSampleParameters.put("transactionKey", "&lt;your account's transaction key here&gt;");
</code></pre>
                    </div>
                    <div class="col s12">
                        <p>Next we will use Customer and CustomerCard classes to pass the information to purchase method</p>
                        <pre class="language-java">
<code class="language-java">
    Customer customer = new Customer();
        
    customer
        .setFirstName("test first name")
        .setLastName("test last name")
        .setCountry(Country.US)
        .setState("TX")
        .setCity("test city")
        .setAddress("test address")
        .setZip("12345")
        .setPhoneNumber("1234567890")
        .setEmail("email@domain.com")
        .setIp("127.0.0.1");
        
    CustomerCard customerCard = new CustomerCard();
    
    customerCard
        .setName("test card name")
        .setNumber("5424000000000015") <span class="token comment">//Authorize test card</span>
        .setCvv(123)
        .setExpiryMonth("01")
        .setExpiryYear("2022");
</code></pre>
                        <h5>Purchase</h5>
                        <p>We are all set to call purchase method</p>
                        <pre class="language-java">
<code class="language-java">
    HTTPResponse purchaseResponse = gateway.purchase(apiSampleParameters, customer, customerCard, Currency.USD, 2.5f);
</code></pre>
                    </div>
                    <div class="col s12">
                        <h5>Handling Purchase Response</h5>
                        <p>
                            Now we can check whether the transaction was success or fail.
                        </p>
                        <pre class="language-java">
<code class="language-java">
    if(purchaseResponse.isSuccessful()){
        <span class="token comment">//some code</span>
    }
</code></pre>
                    </div>
                    <div class="col s12">
                        <p>To print out the full response see below snippet</p>
                        <pre class="language-java">
<code class="language-java">
    System.out.println(purchaseResponse.getJSONResponse());
    
    <span class="token comment">//output</span>
    {
        "lr": {
            "success": true,
            "message": "SUCCESS",
            "transactionId": "3902990127",
            "amount": 45,
            "cardExpiryYear": "2017",
            "cardFirst6": "601160",
            "cardExpiryMonth": "12",
            "maskedCard": "601160******6611",
            "rebillParams": {
                "customerVaultId": "174302554"
            },        
            "voidParams": {
                "transactionId": "3902990127"
            },
            "currencyCode": "USD",
            "cardLast4": "6611",
            "refundParams": {
                "transactionId": "3902990127"
            }
        },
        "gr": { // long gateway response }
    }

</code></pre>
                    </div>
                    <div class="col s12">
                        <h6 class="green-text">Note</h6>
                        <p>Response is defined in great detail in <a href="api_responses.php">API Responses section</a><br/>
                            For this example the only thing you should to know is gateway response is divided into two keys.
                        <ol>
                            <li>lr, library response</li>
                            <li>gr, gateway response</li>
                        </ol>
                        Library response only contains the values that library thinks important for you and could be useful for further actions like refund/void/rebill.
                        Keep in mind library response has already prepared the parameters required for further actions on this transaction. i.e. refund, rebill or void.
                        </p>
                    </div>
                    <div class="col s12">
                        <h5>Rebill</h5>
                        <p>Remember, we saved the purchase respons in purchaseResponse variable. Below is the code showing how to execute rebill transaction in just two lines.</p>
                        <pre class="language-java">
<code class="language-java">
JSONObject rebillParams = purchaseResponse.getJSONObject("lr").getJSONObject("rebillParams");    
HTTPResponse rebillResponse = gateway.rebill(apiSampleParameters, rebillParams, 50);
</code></pre>
                    </div>
                    <div class="col s12">
                        <br/>
                        Congratulations on complete getting started guide.
                        Please feel free to write us on <a href="mailto:info@tranxactive.com">info@tranxactive.com</a>
                        <br/>
                        You can also see the detailed example <a href="example.php">here</a>.
                        <br/>
                    </div>
                </div>
            </div>
        </main>
        <!--  Scripts-->
        <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script src="js/materialize.js"></script>
        <script src="js/init.js"></script>

    </body>
</html>
