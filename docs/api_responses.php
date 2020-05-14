<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>API Responses</title>
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
                        <h1 class="white-text">API Responses</h1>
                    </div>                    
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <h2 class="green-text">Introduction</h2>
                        <p class="caption">The magical thing in this library is its unique responses no matter whatever gateway is. Once you understand API responses it will be easy for you to use this response for further transactions i.e. refund, void or rebill.</p>
                        <p>First of all as we read in the beginning all responses are JSON.</p>
                        <p>All responses are divided into two json keys.</p>
                        <ol>
                            <li>lr (Library response)</li>
                            <li>gr (gateway response)</li>
                        </ol>
                        <p>So here is how a simple response will look like.</p>
                        <pre class="language-json">
<code class="language-json">
    {
        lr : { //library response },
        gr: { //gateway response}
    }
</code></pre>                        
                        <p>J2pay response makes it simple for developer to check the gateways response, Original gateway response contains too much data that developers usually don’t need. To make it simple for the developers J2pay divides the gateway response into two keys lr and gr.</p>
                        <p>lr response which means library response that only contains the values that library thinks important for you and could be useful for further actions like refund/void/rebill.</p>
                        <p>However you can also see the gateway full response in gr key.</p>
                    </div>
                    <div class="col s12">
                        <h4>Library Response</h4>
                        <p>In this section we will take a deep look into library response (lr).</p>
                        <p>Library response is further divided into two responses success and error. Both of these are listed below.</p>
                        <p>First take a look at error message which is very simple and contains only two keys. See below.</p>
                        <pre class="language-json">
<code class="language-json">
    {
        "lr": {
            "success": false,
            "message": "gateway error message",
        },
        "gr": { //long gateway response }
    }
</code></pre>   
                        <p>Error response will be same for all four transaction.</p>
                        <p>Success Response matters for four different transaction purchase, refund, void or rebill. However no major difference. Also keep in mind success response for all gateways remain same.</p>
                        <span class="green-text">Note: </span>You can take a look on all sample responses <a href="sample_responses.php">here</a>.
                        <p>Let’s take a look at purchase success response.</p>
                        <pre class="language-json">
<code class="language-json">
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
                        <p>Purchase response is very simple as you can see it contains success key which also let you know if transaction was successful plus the message that gateway returns. These two keys will always present no matter if response was success or failed.</p>
                        <p>As you can see success response also contains transactionId. Rest of the key do not require explanation except these three.</p>
                        <ol>
                            <li>voidParams</li>
                            <li>refundParams</li>
                            <li>rebillParams</li>
                        </ol>
                        <p>After purchase transaction successfully executed some more actions could be performed on this transaction i.e. you could refund or void or rebill (also called recurring).</p>
                        <p>And for all these three actions all gateways required different parameters, some required transactionId while some required there tokenized variables with different variable names. That is the headache for developer if he is working with multiple gateways. But J2pay has already taken care of these problems by these three keys in purchase response described above.</p>
                        <p>So for example if you would like to perform a refund against the previously charged transaction. You can pass the refundParams as it is in the refund transaction request or you could directly save that in database to refund later. Same for void and rebill. The sample responses for all transaction are listed in sample responses section.</p>
                        <span class="green-text">Note: </span>
                        <p>
                            To view all the response variables <a href="response_variables.php">click here</a>.<br/>
                            To view all sample responses <a href="sample_responses.php">click here</a>.
                        </p>
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
