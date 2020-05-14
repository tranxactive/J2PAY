<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>HTTP Response</title>
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
                        <h1 class="white-text">HTTPResponse Class</h1>
                    </div>                    
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col s12">
                        <p class="caption">This is the class which actually represents the response as you can see in the Gateway section all four transaction methods i.e. purchase, refund, rebill and voidTransaction returns HTTPResponse.<br/>
                            <br/>Below are the methods found in this class with short description.
                        </p>
                    </div>
                    <div class="col s12">
                        <h4>isSuccessful</h4>
                        <p>
                            Once you have executed the transaction by any of four methods i.e. purchase, refund, void or rebill, First thing you would like to check was that transaction successful or not? This is where isSuccessfull method comes handy. 
                        </p>
                        <p>
                            isSuccessful method returns boolean so you could easily check by sample code. Consider you saved response in response variable.
                        </p>
                        <pre class="language-java">
<code class="language-java">
    if(response.isSuccessful()){
    <span class="token comment">//handle successful transaction</span>
    }else{
    <span class="token comment">//handle failed transaction</span>
    }

</code></pre>
                    </div>
                    <div class="col s12">
                        <h4>getJSONResponse</h4>
                        <p>
                            Second thing you would like to do is get the actual response and see the data. This is where you will be using getJSONResponse method. 
                        </p>
                        <p>
                            This method returns the JSONObject. Below is the sample code.
                        </p>
                        <pre class="language-java">
<code class="language-java">
   JSONObject json = response.getJSONResponse();
</code></pre>
                        <span class="green-text">Note:</span> To understand the API response in great detail see the <a href="api_responses.php">API Responses section</a><br/>.
                    </div>
                    <div class="col s12">
                        <h4>getResponseTime</h4>
                        <p>
                            Some times for debugging purpose developers want to track the actual time an http request took to completely the execution. For this purpose you will be using getResponseTime.
                        </p>
                        <p>
                            This method returns time in milliseconds. Below is the sample code.
                        </p>
                        <pre class="language-java">
<code class="language-java">
   response.getResponseTime();
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
