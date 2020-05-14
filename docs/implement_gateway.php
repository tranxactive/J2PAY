<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>Implementing a gateway</title>
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
                        <h1 class="white-text">Implementing A Gateway</h1>
                    </div>                    
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <h2 class="green-text">Introduction</h2>
                        <p class="caption">We are very excited to welcome contributors, if you have worked on any gateway you can implement that gateway in our library and support the open source world.</p>
                        <p>You can find our github repository <a href="https://github.com/tranxactive/J2PAY">here</a></p>
                        <p>Before you begin to implement a gateway there are some other classes you should see first.</p>
                        <p>Below are the classes defined briefly.</p>    
                    </div>
                    <div class="col s12">
                        <h4>HTTPClient</h4>
                        <p>The main thing when working with gateways you post some data to gateway and parse the response.</p>
                        <p>To work with http post request this class provides two overloaded static httpPost methods.</p>
                        <ol>
                            <li>public static HTTPResponse httpPost(String url, String postParams, ContentType contentType)</li>
                            <li>public static HTTPResponse httpPost(String url, String postParams, ContentType contentType, Charset charset)</li>
                        </ol>
                        <p>So you don’t have to worry about handling http requests.</p>
                    </div>
                    <div class="col s12">
                        <h4>Helpers</h4>
                        <p>While you are working with multiple gateways the main problem developer usually face is some gateways receive xml while some receive JSON or query string since J2pay always returns JSON Response so you do not have to worry about data conversion from between any of these xml, JSON or query string.</p>
                        <p>Here is the list of helper classes located in com.tranxactive.paymentprocessor.net package.</p>
                        <ol>
                            <li>QueryStringHelper</li>
                            <li>JSONHelper</li>
                            <li>StringHelper</li>
                            <li>XMLHelper</li>
                        </ol>
                        <p><span class="green-text">Note: </span>All of the methods defined in helper classes are static.</p>
                    </div>
                    <div class="col s12">
                        <h4>Responses</h4>
                        <p>To provide generic response j2pay provides five response classes located in com.tranxactive.paymentprocessor.gateways.responses package.</p>
                        <ol>
                            <li>ErrorResponse</li>
                            <li>PurchaseResponse</li>
                            <li>RebillResponse</li>
                            <li>RefundResponse</li>
                            <li>VoidResponse</li>
                        </ol>
                        <p>As you can identify by their names if you are working with purchase method you will be using PurchaseResponse class if working with rebill method you will be using RebillRespons class and so on</p>
                        <p>ErrorResponse class is the only class which will be used in all four methods.</p>
                        <p>One thing you should also know four classes except ErrorResponse considered as success response. So we will be returning them if and only if transaction was successful.</p>
                    </div>
                    <div class="col s12">
                        <h4>ParamList</h4>
                        <p>ParamList is an enum located in com.tranxactive.paymentprocessor.gateways.parameters package contains the list of variables that must be keep generic in all transactions like if you would like to assign transaction id to variable transactionId there are some chances of typo, but if you will be using paramList enum you are very safe.</p>
                        <p>Here is how could you use that while assigning transactionId In JSON.</p>
                        <pre class="language-java">
<code class="language-java">
    JSONObject json = new JSONObject();
    Json.put(ParamList.TRANSACTION_ID.getName(), "1234567890");
</code></pre>
                    </div>
                    <div class="col s12">
                        <h4>Example</h4>
                        <p>Now you have all the knowledge required to integrate a new gateway. In this example we will be integrating NMI gateway. </p>
                        <p>While working on this example we assumed you have read the NMI official documentation.</p>
                    </div>
                    <div class="col s12">
                        <h4>Let’s code.</h4>
                        <p>To integrate NMI gateway we will create a class in com.tranxactive.paymentprocessor.gateways package with the name NMIGateway.</p>
                        <p>Next we will extends the Gateway class which lead us to implementing all the methods that must be present in a gateway.</p>
                        <p>Here is how our class will look like.</p>
                        <pre class="language-java">
<code class="language-java">
    public class NMIGateway  extends Gateway{

        @Override
        public HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) { }

        @Override
        public HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount) { }

        @Override
        public HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount) { }

        @Override
        public HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters) { }

        @Override
        public JSONObject getApiSampleParameters() { }

        @Override
        public JSONObject getRefundSampleParameters() { }

        @Override
        public JSONObject getRebillSampleParameters() { }

        @Override
        public JSONObject getVoidSampleParameters() { }
    }
</code></pre>
                        <p>Next we will add four below methods at the end of our class. These will be helping us to build the final parameters that need to be posted on the gateway.</p>
                        <pre class="language-java">
<code class="language-java">
    private JSONObject buildPurchaseParameters(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount){}
    private JSONObject buildVoidParameters(JSONObject apiParameters, JSONObject voidParameters) {}
    private JSONObject buildRefundParameters(JSONObject apiParameters, JSONObject refundParameters, float amount){}
    private JSONObject buildRebillParameters(JSONObject apiParameters, JSONObject rebillParameters, float amount){}
</code></pre>
                        <p>Next we will define apiURL variable globally where all request will be posted.</p>
                        <pre class="language-java">
<code class="language-java">
    private final String apiURL = "https://secure.networkmerchants.com/api/transact.php";
</code></pre>
                        <p>Next we will work on four SampleParameters methods.</p>
                        <p>First and most important is getApiSampleParameters method which is required to perform all transactions.</p>
                        <p>If you have read the NMI documentation you will see API parameters are username and password.</p>
                        <p>Here is how getApiSampleParameters method will look like.</p>
                        <pre class="language-java">
<code class="language-java">
    @Override
    public JSONObject getApiSampleParameters() {
        return new JSONObject()
            .put("username", "the api user name use demo as the user name for testing")
            .put("password", "the api password use password  as the password for testing");
    }
</code></pre>
                        <p>Below are the three remaining methods after updating.</p>
                        <pre class="language-java">
<code class="language-java">
    @Override
    public JSONObject getRefundSampleParameters() {
        return new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), "the transaction id which will be refunded");
    }

    @Override
    public JSONObject getRebillSampleParameters() {
        return new JSONObject()
                .put("customerVaultId", "the customer vault id");
    }

    @Override
    public JSONObject getVoidSampleParameters() {
        return new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), "the transaction id which will be void");
    }
</code></pre>
                        <p>Next we will be working on four buildparameters methods. Here is how these look like after inserting our code.</p>
                        <pre class="language-java">
<code class="language-java">
    private JSONObject buildPurchaseParameters(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount) {

        JSONObject object = new JSONObject();
        object
                .put("type", "sale")
                .put("username", apiParameters.getString("username"))
                .put("password", apiParameters.getString("password"))
                .put("ccnumber", customerCard.getNumber())
                .put("ccexp", customerCard.getExpiryMonth() + customerCard.getExpiryYear().substring(2))
                .put("cvv", customerCard.getCvv())
                .put("amount", amount)
                .put("currency", currency)
                .put("first_name", customer.getFirstName())
                .put("last_name", customer.getLastName())
                .put("address1", customer.getAddress())
                .put("city", customer.getCity())
                .put("state", customer.getState())
                .put("zip", customer.getZip())
                .put("country", customer.getCountry().getCodeISO2())
                .put("phone", customer.getPhoneNumber())
                .put("email", customer.getEmail())
                .put("ipaddress", customer.getIp())
                .put("customer_vault", "add_customer");

        return object;

    }

    private JSONObject buildVoidParameters(JSONObject apiParameters, JSONObject voidParameters) {

        JSONObject object = new JSONObject();
        object
                .put("type", "void")
                .put("username", apiParameters.getString("username"))
                .put("password", apiParameters.getString("password"))
                .put("transactionid", voidParameters.getString(ParamList.TRANSACTION_ID.getName()));

        return object;
    }

    private JSONObject buildRefundParameters(JSONObject apiParameters, JSONObject refundParameters, float amount) {

        JSONObject object = new JSONObject();
        object
                .put("type", "refund")
                .put("username", apiParameters.getString("username"))
                .put("password", apiParameters.getString("password"))
                .put("transactionid", refundParameters.getString(ParamList.TRANSACTION_ID.getName()))
                .put("amount", Float.toString(amount));

        return object;
    }

    private JSONObject buildRebillParameters(JSONObject apiParameters, JSONObject rebillParameters, float amount) {

        JSONObject object = new JSONObject();
        object
                .put("username", apiParameters.getString("username"))
                .put("password", apiParameters.getString("password"))
                .put("customer_vault_id", rebillParameters.getString("customerVaultId"))
                .put("amount", Float.toString(amount));

        return object;
    }
</code></pre>
                        <p>Next we will be working on purchase method.</p>
                        <p>First of all we will build our final parameters that need to be posted on gateway with the help of buildPurchaseParameters method.</p>
                        <pre class="language-java">
<code class="language-java">
    JSONObject requestObject = this.buildPurchaseParameters(apiParameters, customer, customerCard, currency, amount);
</code></pre>
                        <p>Next we will define some variables to handle the request don’t worry it’s all depends on how you code.</p>
                        <pre class="language-java">
<code class="language-java">
    JSONObject responseObject;
    String requestString;
    String responseString;
    int responseCode;
    requestObject = JSONHelper.encode(requestObject);
    requestString = QueryStringHelper.toQueryString(requestObject);
    HTTPResponse httpResponse;

    PurchaseResponse successResponse = null;
    ErrorResponse errorResponse = new ErrorResponse();
</code></pre>
                        <p>Since NMI requires queryString data to be posted so we are using two helper class.</p>
                        <b>JSONHelper</b> and <b>QueryStringHelper</b>
                        <p>First we will urlencode the json returned by buildPurchaseParameters with the help of this code.</p>
                        <pre class="language-java">
<code class="language-java">
    requestObject = JSONHelper.encode(requestObject);
</code></pre>
                        <p>Next we converted the encoded json to query string with the help of this code.</p>
                        <pre class="language-java">
<code class="language-java">
    requestString = QueryStringHelper.toQueryString(requestObject);
</code></pre>
                        <p>You must be wondering why we initialized errorResponse but set successResponse as null. That all for some programming login to handle the request easily.</p>
                        <p>Next we will be posting the data to gateway, here is how we will do that.</p>
                        <pre class="language-java">
<code class="language-java">
    httpResponse = HTTPClient.httpPost(this.apiURL, requestString, ContentType.APPLICATION_FORM_URLENCODED);
</code></pre>
                        <p>Here are two scenarios that must keep in mind.</p>
                        <ol>
                            <li>Communication with gateway servers was successful.</li>
                            <li>There was some network issue or gateway server was temporary not available.</li>
                        </ol>
                        <p>Here is how you will handle second scenario.</p>
                        <pre class="language-java">
<code class="language-java">
    if (httpResponse.getStatusCode() == -1) {
        return httpResponse;
    }
</code></pre>
                        <p>If the communication with gateway server was successful then our code will not return from this point and continue.</p>
                        <p>Next we will get the gateway response and parse it to JSON so we can easily work on response.</p>
                        <pre class="language-java">
<code class="language-java">
    responseString = httpResponse.getContent();
    responseObject = JSONHelper.decode(QueryStringHelper.toJson(responseString));
    responseCode = responseObject.getInt("response_code");
</code></pre>
                        <p>As you can see we again used the QueryStringHelper and JSONHelper. Wasn’t that easy with the help of helper class.</p>
                        <p>As we know if gateway response was successful than it must return response code 100. See below code.</p>
                        <pre class="language-java">
<code class="language-java">
    if (responseCode == 100) {
        httpResponse.setSuccessful(true);
        successResponse = new PurchaseResponse();
        successResponse.setMessage(responseObject.getString("responsetext"));
        successResponse.setTransactionId(responseObject.get("transactionid").toString());
        successResponse.setCardValuesFrom(customerCard);
        successResponse.setAmount(amount);
        successResponse.setCurrencyCode(currency);

        successResponse.setRebillParams(new JSONObject()
                .put("customerVaultId", responseObject.get("customer_vault_id").toString())
        );

        successResponse.setRefundParams(new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("transactionid").toString())
        );

        successResponse.setVoidParams(new JSONObject()
                .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("transactionid").toString())
        );

    } else {
        errorResponse.setMessage(responseObject.getString("responsetext"));
    }

</code></pre>
                        <p>Let’s understand the above code line by line.</p>
                        <pre class="language-java">
<code class="language-java">
    httpResponse.setSuccessful(true);
</code></pre>
                        <p>httpResponse by default set the success to false, so we are only setting it to true in success case as we did above.</p>
                        <pre class="language-java">
<code class="language-java">
    successResponse = new PurchaseResponse();
</code></pre>
                        <p>We initialized successResponse variable defined in the beginning of method.</p>
                        <p>When you take a look at the code of PurchaseResponse class you will see all the parameters that must be set before returning the response.</p>
                        <pre class="language-java">
<code class="language-java">
    <span class="token comment">//this sets the gateway success message.</span>
    successResponse.setMessage(responseObject.getString("responsetext"));
</code></pre>
                        <pre class="language-java">
<code class="language-java">
    <span class="token comment">//this sets the gateway returned transaction id.</span>
    successResponse.setTransactionId(responseObject.get("transactionid").toString());
</code></pre>
                        <pre class="language-java">
<code class="language-java">
    <span class="token comment">//this is our standard we provide some card detail in purchase response. You will see in final response.</span>
    successResponse.setCardValuesFrom(customerCard);
</code></pre>
                        <pre class="language-java">
<code class="language-java">
    successResponse.setAmount(amount);
    successResponse.setCurrencyCode(currency);
</code></pre>
                        <p>Next we set the amount and currency that was charged.</p>
                        <p>Since it’s our responsibility to provide the ready to use parameters required for rebill, refund or void.</p>
                        <p>Here is how we did this.</p>
                        <pre class="language-java">
<code class="language-java">
    successResponse.setRebillParams(new JSONObject()
        .put("customerVaultId", responseObject.get("customer_vault_id").toString())
    );

    successResponse.setRefundParams(new JSONObject()
        .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("transactionid").toString())
    );

    successResponse.setVoidParams(new JSONObject()
        .put(ParamList.TRANSACTION_ID.getName(), responseObject.get("transactionid").toString())
    );
</code></pre>
                        <p>But what if response what not success and we got some error like insufficient fund or avs error.</p>
                        <p>Here is how we did this in else block.</p>
                        <pre class="language-java">
<code class="language-java">
    errorResponse.setMessage(responseObject.getString("responsetext"));
</code></pre>
                        <p>Next we will return the final response that will be HTTPResponse.</p>
                        <pre class="language-java">
<code class="language-java">
    if (successResponse != null) {
        successResponse.setGatewayResponse(responseObject);
        httpResponse.setContent(successResponse.getResponse().toString());
    } else {
        errorResponse.setGatewayResponse(responseObject);
        httpResponse.setContent(errorResponse.getResponse().toString());
    }

    return httpResponse;
</code></pre>
                        <p>That’s all we have successfully integrated the NMI purchase method, next three methods will be same except you will be using different Response classes for each of them i.e. you will be using</p>
                        <p>
                            RebillResponse in rebill method.<br/>
                            RefundResponse in refund method.<br/>
                            VoidResponse in voidTransaction method.<br/>
                            Instead of PurchaseResponse. 
                        </p>
                        <p>It is highly recommended to see source of all the these response class and also sample responses (given here)</p>
                        <p>To see the complete code of NMI gateway you can see on our <a href="https://github.com/tranxactive/J2PAY ">github repository</a>.</p>
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
