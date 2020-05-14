<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>Gateway</title>
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
                        <h1 class="white-text">Gateway Class</h1>
                    </div>                    
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <p class="caption">Gateway is the top level abstract class for all gateways which implements GatewaySampleParameters interface, so following is the complete list of methods present in Gateway class.</p>
                    </div>
                    <div class="col s12">
                        <ol>
                            <li>public abstract HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount)</li>
                            <li>public abstract HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount)</li>
                            <li>public abstract HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount)</li>
                            <li>public abstract HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters);</li>
                            <li>public JSONObject getApiSampleParameters()</li>
                            <li>public JSONObject getRefundSampleParameters()</li>
                            <li>public JSONObject getRebillSampleParameters()</li>
                            <li>public JSONObject getVoidSampleParameters()</li>
                            <li>public void setTestMode(boolean testMode)</li>
                        </ol>
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
