# J2Pay

**version 1.0**

J2Pay is a multi-gateway payment processing library for Java. Which provides simple and generic api for all gateways.
It reduces developers efforts of writing individual code for each gateway. It provides flexibility to write one code for all gateways. It excludes the efforts of reading docs for individual gateways.

## Why use J2Pay

1. You want multi gateway support in your application.
2. You do not have time for learning individual payment gateways docs that are also (poorly documented).
3. You want to support multiple payment gateways in your application without worrying about the implemention of each gateways.
4. You want to use single api for each gateway.
5. You dont want to write seperate logic for each gateway.

## Generic Request/Response.

If you would like to work with multiple gateways the main problem developers usually face are api prameters names.

for example.

some gateways 
take first name as fname or first_name or
take card number as CardNumber or Card_Number or card.

J2Pay excludes this type of efforts and provide classes for customer details and customer cards which will remain same for all geteways. (for more details see 5 minut example below).

same problem when parsing gateway response.

J2Pay also excluded this type of efforts and provide generic response for all gateways.

for example.

when a transaction is successfully processed some gateways return transaction id as transaction_id or transId or trans_tag blah blah.
but if you are using J2pay you will always receive "transactionId".

here is the sample response.

```json
{  
   "lr":{  
      "maskedCard":"542400******0015",
      "customerProfileId":"1813701920",
      "success":true,
      "paymentProfileId":"1808392124",
      "cardExpiryYear":"2017",
      "message":"This transaction has been approved.",
      "cardFirst6":"542400",
      "cardExpiryMonth":"12",
      "transactionId":"60035117709",
      "cardLast4":"0015"
   },
   "gr":{  
      "createTransactionResponse":{  
         "xmlns:xsd":"http://www.w3.org/2001/XMLSchema",
         "xmlns":"AnetApi/xml/v1/schema/AnetApiSchema.xsd",
         "transactionResponse":{  
            "cvvResultCode":"P",
            "transHashSha2":"",
            "authCode":"A1IG4K",
            "cavvResultCode":2,
            "transId":60035117709 . . .
..........................................................................................
            this part is removed to make this doc simple.
..........................................................................................
}
```

As you can see J2pay api response is simple json and divided into two keys.

1) lr --> short for library response.
2) gr --> short for gateway response.

J2pay response makes it simple for developer to check the gateways response, as you know gateways return a very large response.
to make it simple for the developers J2pay divides the gateway response into two keys lr and gr.

lr respose which means library response that only contains the values that library thinks important for you and could be usefull for further actions like refund/void/rebill.

however you can also see the gateway full response in gr key for some debugging purpose.

## Supported Gateways

J2Pay is extending day by day developers are trying there best to implement big number of gateways only for you.

If your desired gateway is not in the list dont hesitate you can directly write us at info@tranxactive.com or you can create an issue in this repository and we will try our best to integrate that gatway as soon as possible.

1. Authorize.net
2. NMI

`newly intergrated gateways will be added in this list.`

**Goals of this library**

* Developer should be able to integrate any gateway without the need of reading documention of specific gateway.
* Provide generic methods for all gateways.
* Provide generic response for all gateways.

This library will be focusing on four major methods of gateways.

* Purchase
* Refund
* Void
* Rebill

# Understanding the library

This library has three packages.

* com.tranxactive.paymentprocessor.gateways (Contains all the available gateways).
* com.tranxactive.paymentprocessor.gateways.parameters (Contains classes for gateway parameters like Customers, cards).
* com.tranxactive.paymentprocessor.net (Contains classes for http requests and response.).

**List of some major classes**

Here is the list of some major classes you must know before starting working on this library.

* Gateway, is the top level abstract class all gateways must be inheriting this class.
* GatewayFactory, will be responsible for returning the required gateway.
* HTTPResponse, gateway reponse will be returning this class's object instead of plain text or json.
* JSONObject, Represent the json data. also will be using for posting dynamic gateway data.
* AvailableGateways, enum contains the list of supported gateways. we will be passing this to GatewayFactory to get the desired gateway class object.

# Example (Understanding library in 5 minutes)

If you are using maven you can add dependency.

```xml
<dependency>
    <groupId>com.tranxactive</groupId>
    <artifactId>j2pay</artifactId>
    <version>1.0.0</version>
</dependency>
```

In this example we will be looking at purchase method.

Before moving forword we must understand that some paramters are always different for each gateways.
To handle this situation gateway class provides four sample parameters methods (will be looking at them shortly).

* gateway.getApiSampleParameters()
* gateway.getRefundSampleParameters()
* gateway.getVoidSampleParameters()
* gateway.getRebillSampleParameters()

**lets code.**

First we will get the desired gateway in this case Authorize.net.
We can get any supported gateway by the help of getGateway method of GatewayFactory class.

```java
Gateway gateway = GatewayFactory.getGateway(AvailableGateways.AUTHORIZE);
```
Now we can call purchase, refund, void and rebill methods.

Purchase method required 5 parameters.

* JSONObject apiParamters, that is the gateway specific paramters always unique for each gateway. 
* Customer customer, this class represents customer personal information.
* CustomerCard customerCard, this class represents the Customer card details.
* Currency currency, that is enum contains the list of currency in which amount will be charged.
* float amount, the amout that will be charged.

**1st Parameter**

lets get the apiSampleParameters by calling getApiSampleParameters() method.

```java
JSONObject apiSampleParameters = gateway.getApiSampleParameters();
```
we will print apiSampleParameters to see what is the requirement.

```java
System.out.println(apiSampleParameters);
//output
{ "name" : "also called api user name / api login id", "transactionKey" : "the transaction key" }
```

As you can see for Authorize api parameters are name and transactionKey.
We will populate these values and pass to purchase method.

```java
apiSampleParameters.put("name", "<your acount's user name here>");
apiSampleParameters.put("transactionKey", "<your account's transaction key here>");
```

**2nd Parameter**

Customer class comes with chaining setter methods.

```java
Customer customer = new Customer();
        
customer
        .setFirstName("test first name")
        .setLastName("test last name")
        .setCountry(Country.US)
        .setState("TX")
        .setCity("test city")
        .setAddress("test address")
        .setZip("12345")
        .setPhoneNumber("1234567890");
```

**3rd Parameter**

CustomerCard class comes with chaining setter methods.

```java
CustomerCard customerCard = new CustomerCard();
customerCard
        .setName("test card name")
        .setNumber("5424000000000015")
        .setCvv(123)
        .setExpiryMonth("01")
        .setExpiryYear("2022");
```

4th and 5th parameters does not required any explanation.

now all parameters are ready we can pass them to purchase mehtod.
before purchase you can enable the test mode by calling setTestMode of gateway, By default test mode is disabled.

```java
HTTPResponse response = gateway.purchase(apiSampleParameters, customer, customerCard, Currency.USD, 45);
```

you can check the status of purchase request by calling isSuccessful method.
you can also get the response content by calling getContent method.

```java
response.isSuccessful();
response.getContent();
```

# Putting all code together.

```java
Gateway gateway = GatewayFactory.getGateway(AvailableGateways.AUTHORIZE);
JSONObject apiSampleParameters = gateway.getApiSampleParameters();

apiSampleParameters.put("name", "<your acount's user name here>");
apiSampleParameters.put("transactionKey", "<your account's transaction key here>");

Customer customer = new Customer();
        
customer
        .setFirstName("test first name")
        .setLastName("test last name")
        .setCountry(Country.US)
        .setState("TX")
        .setCity("test city")
        .setAddress("test address")
        .setZip("12345")
        .setPhoneNumber("1234567890");

CustomerCard customerCard = new CustomerCard();
customerCard
        .setName("test card name")
        .setNumber("5424000000000015")
        .setCvv(123)
        .setExpiryMonth("01")
        .setExpiryYear("2022");
        
gateway.setTestMode(true);

HTTPResponse response = gateway.purchase(apiSampleParameters, customer, customerCard, Currency.USD, 45);

System.out.println(response.isSuccessful());
System.out.println(response.getContent());

```

# Contributors

We are very excited to welcome contributers, If you have worked on any gateway you can implement that gateway in our library and support the opensource world.

**Integrating new Gateway**

You can create new gateway by inheriting gateway class.

Here is the list of methods you must override.

* public abstract HTTPResponse purchase(JSONObject apiParameters, Customer customer, CustomerCard customerCard, Currency currency, float amount);
* public abstract HTTPResponse refund(JSONObject apiParameters, JSONObject refundParameters, float amount);
* public abstract HTTPResponse rebill(JSONObject apiParameters, JSONObject rebillParameters, float amount);
* public abstract HTTPResponse voidTransaction(JSONObject apiParameters, JSONObject voidParameters);

Since gateway class in implementing GatewaySampleParameters you must override its methods too.

* public abstract JSONObject getApiSampleParameters();
* public abstract JSONObject getRefundSampleParameters();
* public abstract JSONObject getRebillSampleParameters();
* public abstract JSONObject getVoidSampleParameters();

**Making http requests**

This library comes with built-in http client which itself is a wrapper of apache http client.

HTTPClient class has two static methods.

* httpGet(String url)
* httpPost(String url, String postParams, ContentType contentType, Charset charset)

you can use them directly without worrying about http requests.

**Handling http response**

This library has a built-in class to handle http response which is HTTPResponse.
As you can see above all gateway methods are returning this class object instead of plain text or json.

After making http request do not forget to set response status by using setSuccessful(boolean successful) method of HTTPResponse class.
By default success is set to true but if you found some errors in response you can change that by calling setSuccessful method.

```java
httpResponse.setSuccessful(false);
```

**Finalizing gateway**

After you have integrated your gateway successfully, dont forget to make it available in,

* gatewayFactory class and
* AvailableGateways enum

**`It is highly recommended to check the source code of already integrated gateway before implementing your gateway`**

# Contact us.

Feel free to contact or suggest us at info@tranxactive.com.

**`GOOD LUCK`**


 






