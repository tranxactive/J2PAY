<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>Introduction</title>
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
                        <h1 class="white-text">Introduction</h1>
                    </div>                    
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <p class="caption">J2Pay is an open source multi gateway payment processing library for java (by tranxactive). The main goal of this library is to provide simple and generic request/response for multiple gateways at the same time it also excludes the efforts of reading documentations of gateways. If you are trying to work on a gateway you do not have to read the documentation because this library has a built-in documentation.</p>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <h4>Merits and Demerits</h4>
                        <p>After version 2.7.8, J2pay now support Authorize and Capture transactions.</p>
                        <p>Below is the list of all the transaction types supported now.</p>
                        <ol>
                            <li>Authorize</li>
                            <li>Capture</li>
                            <li>Purchase (Authorize + Capture in one step)</li>
                            <li>Refund</li>
                            <li>Void</li>
                            <li>Recurring/Rebill</li>
                        </ol>
                        <p>If all of your transactions are based on Cards and you are interested in six methods listed above then this library is built for you.</p>
                        <p>At the same time this library provides generic request/response for all gateways. As you know some gateways accept xml while some are JSON or query string. This library always accept and return JSON and do all the casting internally.</p>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <h4>Before You Begin</h4>
                        <p>In this section we will discuss about what you should know before working on this library. Every gateway is this library accept and return JSON. Since JSON is not directly supported by java we will be using org.json package which have number of classes to deal with JSON.</p>
                        <p>As you know any library has collection of classes and interfaces so before working on this library it is highly recommended that you should understand its classes and methods.</p>
                        <ol>
                            <li>Gateway, is the top level abstract class all gateways must be inheriting this class.</li>
                            <li>GatewayFactory, will be responsible for returning the required gateway.</li>
                            <li>HTTPResponse, gateway response will be returning this class's object instead of plain text or JSON.</li>
                            <li>JSONObject, Represent the JSON data also will be using for posting dynamic gateway data.</li>
                            <li>AvailableGateways, enum contains the list of supported gateways. We will be passing this to GatewayFactory to get the desired gateway class object.</li>
                        </ol>
                        <p>In the beginning we read this library has a built-in documentation, now it’s time to understand what was that mean. This Library provided sample parameter methods for all gateways which let know what the required parameters for that gateway are.</p>                        
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <h4 id='sampleParameters'>Sample Parameters</h4>
                        <p>Sample parameter methods are the most important part of this library, these are responsible for providing you the gateway specific parameters with short description that also excludes the reading of gateway documentation.</p>
                        <p>Below is the list of methods. All of these methods returns ready to use JSON with short description. You can just populate the values and pass to another methods.</p>
                        <ol>
                            <li>gateway.getApiSampleParameters()</li>
                            <li>gateway.getRefundSampleParameters()</li>
                            <li>gateway.getVoidSampleParameters()</li>
                            <li>gateway.getRebillSampleParameters()</li>
                            <li>gateway.getCaptureSampleParameters()</li>
                        </ol>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <h4>getApiSampleParameters</h4>
                        <p>This method is the key for all API requests, all gateways required some authentication parameters for example some required username and password while some required transaction key. This method returns the gateway specific parameters.</p>
                        <p><span class="green-text">Note: </span>Rest of the methods work similar as you can identify by their name.</p>
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
