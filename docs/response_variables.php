<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>Response Variables</title>
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
                        <h1 class="white-text">Response Variables</h1>
                    </div>                    
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <p class="caption">Below is the list of response variables with short description</p>
                    </div>
                    <div class="col s12">
                        <table class="striped responsive-table">
                            <thead>
                                <tr>
                                    <th>Variable Name</th>
                                    <th>Datatype</th>
                                    <th>Description</th>
                                </tr>
                            </thead>

                            <tbody>
                                <tr>
                                    <td>success</td>
                                    <td>boolean</td>
                                    <td>Transaction status</td>
                                </tr>
                                <tr>
                                    <td>message</td>
                                    <td>string</td>
                                    <td>Contains gateway success/error message</td>
                                </tr>
                                <tr>
                                    <td>amount</td>
                                    <td>float</td>
                                    <td>Charged amount</td>
                                </tr>
                                <tr>
                                    <td>currencyCode</td>
                                    <td>Currency enum</td>
                                    <td>Currency in which amount is charged</td>
                                </tr>
                                <tr>
                                    <td>cardExpiryYear</td>
                                    <td>string</td>
                                    <td>Four digit card expiry year e.g. 2017</td>
                                </tr>
                                <tr>
                                    <td>cardExpiryMonth</td>
                                    <td>string</td>
                                    <td>Two digits card expiry month e.g 04</td>
                                </tr>
                                <tr>
                                    <td>cardFirst6</td>
                                    <td>string</td>
                                    <td>First 6 digits of card</td>
                                </tr>
                                <tr>
                                    <td>cardLast4</td>
                                    <td>string</td>
                                    <td>Last four digits of card</td>
                                </tr>
                                <tr>
                                    <td>maskedCard</td>
                                    <td>string</td>
                                    <td>Masked card number</td>
                                </tr>
                                <tr>
                                    <td>transactionId</td>
                                    <td>string</td>
                                    <td>Gateway provided transaction id</td>
                                </tr>
                                <tr>
                                    <td>rebillParams</td>
                                    <td>json</td>
                                    <td>Pre build rebill parameters required to charge recurring transaction</td>
                                </tr>
                                <tr>
                                    <td>voidParams</td>
                                    <td>json</td>
                                    <td>Pre build void paramters required to void the previously charged transaction</td>
                                </tr>
                                <tr>
                                    <td>refundParams</td>
                                    <td>json</td>
                                    <td>Pre build refund paramters required to redunf the previously charged transaction</td>
                                </tr>
                                <tr>
                                    <td>gatewayResponse</td>
                                    <td>json</td>
                                    <td>Contains the full gateway response</td>
                                </tr>
                            </tbody>
                        </table>
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
