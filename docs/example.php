<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>Example</title>
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
                        <h1 class="white-text">Example</h1>
                    </div>                    
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <h2 class="green-text">Introduction</h2>
                        <p class="caption">In this section we will be looking in great detail of how to use a gateway and invoke four methods successfully i.e. purchase, refund, void and rebill.</p>
                        <p>For this example we will be using Authorize gateway. Let’s begin.</p>
                        <p>First of all we will get the Authorize gateway object.</p>
                        <pre class="language-java">
<code class="language-java">
    Gateway gateway = GatewayFactory.getGateway(AvailableGateways.AUTHORIZE);
</code></pre>                        
                        <p>But what if you would like to fetch the Authorize gateway dynamically for example you are getting its name from database.</p>
                        <p>Here is how you could do this.</p>
                        <pre class="language-java">
<code class="language-java">
    Gateway gateway = GatewayFactory.getGateway(AvailableGateways.valueOf("AUTHORIZE"));
</code></pre>    
                        <p>Now you can understand both two approaches of how to get your desired gateway object.</p>
                        <p>Since we are working in test environment second thing we will do is enable the test mode.</p>
                        <pre class="language-java">
<code class="language-java">
    gateway.setTestMode(true);
</code></pre> 
                        <p><span class="green-text">Note: </span>Test mode will only work if it is supported by gateway otherwise it will be ignored by library.</p>
                        <p>Next and most important thing is API parameters, these are the unique values provided my merchant service providers i.e. API user name and password which must be included in all requests and these are always different for all gateways.</p>
                        <p>Since we are using J2pay we do not need to read any documentation for authorize gateway variables.</p>
                        <p>This is where you will be using sample parameter methods <a href="./introduction.php#sampleParameters" target="_blank">(see Sample Parameters section)</a></p>
                        <p>Here is how you will do that.</p>
                        <pre class="language-java">
<code class="language-java">
    JSONObject apiSampleParameters = gateway.getApiSampleParameters();
</code></pre> 
                        <p>Now we will print it to see what the parameters are.</p>
                        <pre class="language-java">
<code class="language-java">
    JSONObject apiSampleParameters = gateway.getApiSampleParameters();
    System.out.println(apiSampleParameters);
    
    <span class="token comment">//output</span>
    { "name" : "also called api user name / api login id", "transactionKey" : "the transaction key" }

</code></pre> 
                        <p>As you can see for Authorize API parameters are name and transactionKey. We will populate these values and pass to purchase method.</p>
                        <pre class="language-java">
<code class="language-java">
apiSampleParameters.put("name", "&lt;your acount's user name here&gt;");
apiSampleParameters.put("transactionKey", "&lt;your account's transaction key here&gt;");
</code></pre> 
                    </div>
                    <div class="col s12">
                        <h4>Purchase</h4>
                        <p>Purchase method requires five parameters.</p>
                        <ol>
                            <li>JSONObject apiParamters, that is the gateway specific paramters always unique for each gateway.</li>
                            <li>Customer customer, this class represents customer personal information.</li>
                            <li>CustomerCard customerCard, this class represents the Customer card details.</li>
                            <li>Currency currency, that is enum contains the list of currency in which amount will be charged.</li>
                            <li>float amount, the amout that will be charged.</li>
                        </ol>
                        <p>We have already set the apiParameters above. </p>
                        <p>Now creating customer and customer card object.</p>
                        <p><span class="green-text">Note: </span>Customer and customercard classes support chaining setter methods and all field used below are required.</p>
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
        .setNumber("5424000000000015")
        .setCvv(123)
        .setExpiryMonth("01")
        .setExpiryYear("2022");
    
</code></pre> 
                        <p><span class="green-text">Note: </span>4th and 5th parameters does not require any explanation.</p>
                        <p>Now all parameters are ready we can pass them to purchase methods</p>
                        <pre class="language-java">
<code class="language-java">
    HTTPResponse response = gateway.purchase(apiSampleParameters, customer, customerCard, Currency.USD, 45);
</code></pre>
                        <p>You can check the status of purchase request by calling isSuccessful method and you can also get the JSON response by calling getJSONResponse method.</p>
                        <pre class="language-java">
<code class="language-java">
    response.isSuccessful();
    response.getJSONResponse();
</code></pre>
                        <p>Let’s put all code together.</p>
                        <pre class="language-java">
<code class="language-java">
    Gateway gateway = GatewayFactory.getGateway(AvailableGateways.AUTHORIZE);
    JSONObject apiSampleParameters = gateway.getApiSampleParameters();

    apiSampleParameters.put("name", "<your acount's user name here>");
    apiSampleParameters.put("transactionKey", "<your account's transaction key here>");

    Customer customer = new Customer();

    customer
        .setFirstName("test first name")
        .setLastName("test last name")
        .setCountry(Country.US)
        .setState("TX")
        .setCity("test city")
        .setAddress("test address")
        .setZip("12345")
        .setPhoneNumber("1234567890");

    CustomerCard customerCard = new CustomerCard();
    
    customerCard
        .setName("test card name")
        .setNumber("5424000000000015")
        .setCvv(123)
        .setExpiryMonth("01")
        .setExpiryYear("2022");
        
    gateway.setTestMode(true);

    HTTPResponse response = gateway.purchase(apiSampleParameters, customer, customerCard, Currency.USD, 45);

    System.out.println (response.isSuccessful());
    System.out.println (response.getJSONResponse());
</code></pre>
                                        <p>Let’s take a look at response we receive. Consider we are holding response in response variable.</p>
                                        <pre class="language-java">
<code class="language-java">
    JSONObject response = response.getJSONResponse();
</code></pre>
                                        <p>After printing response here is what we got.</p>
                                        <pre class="language-json">
<code class="language-json">
    {
        "lr": {
            "amount": 2.5,
            "cardExpiryYear": "2017",
            "message": "This transaction has been approved.",
            "cardFirst6": "542400",
            "cardExpiryMonth": "12",
            "transactionId": "60036012175",
            "maskedCard": "542400******0015",
            "rebillParams": {
                "customerProfileId": "1813844918",
                "paymentProfileId": "1808509554"
            },
            "success": true,
            "voidParams": {
                "transactionId": "60036012175"
            },
            "currencyCode": "USD",
            "cardLast4": "0015",
            "refundParams": {
                "transactionId": "60036012175",
                "cardLast4": "0015"
            }
        },
        "gr": { //long gateway response }
    }

</code></pre>
                                        <p>As you can see for further transaction like refund, void or rebill library itself created the required parameters</p>
                                        <p>For rebill</p>
                                        <pre class="language-json">
<code class="language-json">
    "rebillParams": {
        "customerProfileId": "1813844918",
        "paymentProfileId": "1808509554"
    },
</code></pre>
                                        <p>For void</p>
                                        <pre class="language-json">
<code class="language-json">
    "voidParams": {
        "transactionId": "60036012175"
    },
</code></pre>
                                        <p>For refund</p>
                                        <pre class="language-json">
<code class="language-json">
    "refundParams": {
        "transactionId": "60036012175",
        "cardLast4": "0015"
    }
</code></pre>
                                        <p><span class="green-text">Note: </span>You can save these parmeters in database and pass them to suitable methods.</p>
                    </div>

                    <div class="col s12">
                        <h4>Rebill</h4>
                        <p>For rebill we will call the getRebillSampleParameters method.</p>
                        <pre class="language-java">
<code class="language-java">
    JSONObject rebillSampleParameters = gateway.getRebillSampleParameters();
</code></pre>
                        <p>After printing it you will see.</p>
                        <pre class="language-json">
<code class="language-json">
    {"customerProfileId":"the customer profile id","paymentProfileId":"the customer payment profile id"}
</code></pre>
                        <p>If you match that with above purchase response rebillParams key you will see actually there is no difference. Purchase response already contains these parameters with populated values.</p>
                        <p>So we are not creating them like getApiSampleParameters above but if you have not executed the purchase transaction from this library you have second option to create these parameters and pass them to rebill method. Below we have described both approaches so you could use whatever suits you better.</p>
                        <h4>First Approach</h4>
                        <p>This approach is fast forward. We will be using library generated parameters (rebillParams).</p>
                        <p>Since rebill method required three parameters</p>
                        <ol>
                            <li>JSON apiParameters</li>
                            <li>JSON rebillParameters</li>
                            <li>float amount</li>
                        </ol>
                        <p>We have already discussed the apiParameters and just to remind you we saved gateway object in gateway variable and purchase response in response variable.</p>
                        <p>Here is how we could easily call the rebill method.</p>
                        <pre class="language-java">
<code class="language-java">
    JSONObject rebillParams = response.getJSONObject("lr").getJSONObject("rebillParams")
    HTTPResponse rebillResponse = gateway.rebill(apiSampleParameters, rebillParams, 105);
</code></pre>
                        <p>Wasn’t that simple just two lines?</p>
                        
                        <h4>Second Approach</h4>
                        <p>Second method is similar as we created apiParameters.</p>
                        <pre class="language-java">
<code class="language-java">
    JSONObject rebillParams = gateway.getRebillSampleParameters();
</code></pre>
                        <p>After printing rebillParams we got.</p>
                        <pre class="language-java">
<code class="language-java">
    System.out.println(rebillParams);
    
    <span class="token comment">//output</span>
    {"customerProfileId":"the customer profile id","paymentProfileId":"the customer payment profile id"}
</code></pre>
                        <p>Now we will populate these values.</p>
                        <pre class="language-java">
<code class="language-java">
    rebillParams.put("customerProfileId", "1813844918");
    rebillParams.put("paymentProfileId", "1808509554");
</code></pre>
                        <p>Now we can call rebill method.</p>
                        <pre class="language-java">
<code class="language-java">
    HTTPResponse rebillResponse = gateway.rebill(apiSampleParameters, rebillParams, 105);
</code></pre>
                        <p>As you have seen above you can call the rebillResponse. getJSONResponse() method the get the response. And you could also check for whether the transaction was success or not by calling the rebillResponse.isSuccessful() method.</p>
                        <p>you can also notice both approaches are really simple and you are free to use whatever suits you better but it is recommended to use first approach as it is also very simple and excludes the chances of any bug.</p>
                        <p><span class="green-text">Note: </span>For rest of the example we will be using first approach.</p>
                    </div>
                    <div class="col s12">
                        <h4>Refund</h4>
                        <p>Refund method required three parameters</p>
                        <ol>
                            <li>JSON apiParameters</li>
                            <li>JSON refundParameters</li>
                            <li>float amount</li>
                        </ol>
                        <p>It is very similar to refund. That’s how we will call refund method.</p>
                        <pre class="language-java">
<code class="language-java">
    JSONObject refundParams = response.getJSONObject("lr").getJSONObject("refundParams")
    HTTPResponse refundResponse = gateway.refund(apiSampleParameters, refundParams, 2.5);
</code></pre>
                        <p><span class="green-text">Note: </span>Rest of the work will remain same refundResponse contains the actual response.</p>
                    </div>
                    <div class="col s12">
                        <h4>Void</h4>
                        <p>voidTransaction method requires two parameters.</p>
                        <ol>
                            <li>JSON apiParameters</li>
                            <li>JSON voidParameters</li>
                        </ol>
                        <p>Below is the sample code.</p>
                        <pre class="language-java">
<code class="language-java">
    JSONObject voidParams= response.getJSONObject("lr").getJSONObject("voidParams")
    HTTPResponse voidResponse = gateway.voidTransaction (apiSampleParameters, voidParams);
</code></pre>
                        <p><span class="green-text">Note: </span>Rest of the work will remain same voidResponse contains the actual response.</p>
                        <p>Congratulations on completing example. You have fully understand the library.</p>
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
