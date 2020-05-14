<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>Sample Responses</title>
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
                        <h1 class="white-text">Sample Responses</h1>
                    </div>                    
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <p class="caption">Below are the sample responses</p>
                    </div>
                    <div class="col s12">
                        <h4>Purchase Response</h4>
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
                    </div>
                    <div class="col s12">
                        <h4>Rebill Response</h4>
                        <pre class="language-json">
<code class="language-json">
    {
        "lr": {
            "amount": 105,
            "rebillParams": {
                "customerProfileId": "1813844918",
                "paymentProfileId": "1808509554"
            },
            "success": true,
            "voidParams": {
                "transactionId": "60036301672"
            },
            "message": "This transaction has been approved.",
            "transactionId": "60036301672",
            "refundParams": {
                "transactionId": "60036301672",
                "cardLast4": "0015"
            }
        },
        "gr": {
        <span class="token comment">//long gateway response</span>
        }
    }
</code></pre>
                    </div>
                    <div class="col s12">
                        <h4>Refund Response</h4>
                        <pre class="language-json">
<code class="language-json">
    {
        "lr": {
            "amount": 1,
            "success": true,
            "voidParams": {
                "transactionId": "60036301739"
            },
            "message": "This transaction has been approved.",
            "transactionId": "60036301739"
        },
        "gr": {
            <span class="token comment">//long gateway response</span>
        }
    }
</code></pre>
                    </div>
                    <div class="col s12">
                        <h4>Void Response</h4>
                        <pre class="language-json">
<code class="language-json">
    {
        "lr": {
            "success": true,
            "message": "This transaction has been approved.",
            "transactionId": "60036301739"
        },
        "gr": {
                <span class="token comment">//long gateway response</span>
        }
    }
</code></pre>
                    </div>
                    <div class="col s12">
                        <h4>Error Response</h4>
                        <pre class="language-json">
<code class="language-json">
    {
        "lr": {
            "success": false,
            "message": "The referenced transaction does not meet the criteria for issuing a credit."
        },
        "gr": {
                <span class="token comment">//long gateway response</span>
            }
    }
</code></pre>
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
