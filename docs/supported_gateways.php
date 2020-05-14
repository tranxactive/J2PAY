<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>Supported Gateways</title>
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
                        <h1 class="white-text">Supported Gateways</h1>
                    </div>                    
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <p class="caption">Below is the list of supported gateways</p>
                    </div>
                    <div class="col s12">
                        <table class="striped responsive-table">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Gateway</th>
                                    <th>Recurring Support</th>
                                    <th>Auth + Capture</th>
                                </tr>
                            </thead>

                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>AUTHORIZE</td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td>NMI</td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td>PAYEEZY</td>
                                    <td><i class="tiny material-icons red-text">cancel</i></td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                </tr>
                                <tr>
                                    <td>4</td>
                                    <td>PAYFLOW PRO</td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                </tr>
                                <tr>
                                    <td>5</td>
                                    <td>BillPro</td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                </tr>
                                <tr>
                                    <td>6</td>
                                    <td>EasyPay</td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                    <td><i class="tiny material-icons red-text">cancel</i></td>
                                </tr>
                                <tr>
                                    <td>7</td>
                                    <td>Checkout</td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                    <td><i class="tiny material-icons red-text">cancel</i></td>
                                </tr>
                                <tr>
                                    <td>8</td>
                                    <td>Stripe</td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                </tr>
                                <tr>
                                    <td>9</td>
                                    <td>Braintree</td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                    <td><i class="tiny material-icons green-text">check_box</i></td>
                                </tr>
                            </tbody>
                        </table>
                        <p>
                        <span class="green-text">Note: </span>
                        </p>
                        <p>
                            If your desired gateway is not in list you are free to create an issue on our <a href="https://github.com/tranxactive/J2PAY" target="_blank">github</a> repository and we will integrate that gateway for you. or you can also write us at info@tranxactive.com
                        </p>
                        <p>You can also integrate a gateway your self see <a href="implement_gateway.php">Implementing A Gateway</a> section.</p>
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
