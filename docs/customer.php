<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>Customer</title>
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
                        <h1 class="white-text">Customer Class</h1>
                    </div>                    
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <p class="caption">All gateways require customer details to process transactions. All gateways integrated in J2Pay required Customer class object to execute purchase transaction. Below is the code sample of how to use Customer class.</p>
                    </div>
                    <div class="col s12">
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
