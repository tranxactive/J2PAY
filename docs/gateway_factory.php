<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>Gateway Factory</title>
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
                        <h1 class="white-text">GatewayFactory Class</h1>
                    </div>                    
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <p class="caption">GatewayFactory is the class with one method getGateway which is responsible for returning the required gateway object. See below sample code.</p>
                    </div>
                    <div class="col s12">
                        <p>To get the AUTHORIZE gateway.</p>
                        <pre class="language-java">
<code class="language-java">
   Gateway gateway = GatewayFactory.getGateway(AvailableGateways.AUTHORIZE);
</code></pre>
                        <p>Since java enum also support string values if you are getting gateway name from database here is how you could get gateway by string name.</p>
                                                <pre class="language-java">
<code class="language-java">
   Gateway gateway = GatewayFactory.getGateway(AvailableGateways.valueOf("AUTHORIZE"));
</code></pre>
                        <span class="green-text">Note: </span>To know all supported gateways by J2pay see <a href="supported_gateways.php">supported gateways section.</a>
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
