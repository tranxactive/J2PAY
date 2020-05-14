<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>Why Choose J2Pay</title>
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
                        <h1 class="white-text">Why Should I Choose J2pay</h1>
                    </div>                    
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <p class="caption">The main question usually raise is why should I choose this library instead of working directly on gateways API`s or SDKs. To make it simple to understand read below points.</p>
                    </div>
                    <div class="col s12">
                        <ol>
                            <li>You can learn one API for all gateways.</li>
                            <li>You want multi gateway support in your application.</li>
                            <li>You do not have time for learning individual payment gateways docs that are also (poorly documented)</li>
                            <li>You want to support multiple payment gateways in your application without worrying about the implementation of each gateways.</li>
                            <li>You want to use single API for each gateway.</li>
                            <li>You don’t want to take risk whether your code works perfectly on live environment. </li>
                        </ol>
                    </div>
                    <div class="col s12">
                        <h4>Generic Request/Response</h4>
                        <p>
                            If you would like to work with multiple gateways the main problem developers usually face are API parameters names.
                            Some gateways take first name as fname or first_name or take card number as CardNumber or Card_Number or card.
                            J2Pay excludes this type of efforts and provide classes for customer details and customer cards which will remain same for all gateways.
                            Same problem when parsing gateway response.
                            J2Pay also excluded this type of efforts and provide generic response for all gateways.
                            When a transaction is successfully processed some gateways return transaction id as transaction_id or transId or trans_tag. But if you are using J2pay you will always receive "transactionId". <a href="api_responses.php">(More on this Responses section)</a>
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
