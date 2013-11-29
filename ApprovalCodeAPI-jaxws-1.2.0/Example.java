/*
 * Example.java
 *
 *  Copyright (c) 2009 USAePay
 *
 * Example usage of the usaepay jaxws library.  See
 * http://help.usaepay.com/developer/soap/howto/javajaxws  for more
 * information on using the java library.
 *
 */

import com.usaepay.api.jaxws.*;

/**
 *
 */
public class Example {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        try {

            // instantiate client connection object,  select secure.approvalcode.com
            // as the endpoint.  Use sandbox.approvalcode.com if connecting to
            // the sandbox server.
            UeSoapServerPortType client = usaepay.getClient("sandbox.approvalcode.com");

            // Instantiate security token object (need by all soap methods)
            UeSecurityToken token = usaepay.getToken(
                    "_n1Gc6bZ46Wy0mmCkBp9j83A7EB5w3Am", // "74HnPVZTUpAR72F2ietZ199c6HuYbxnV", // source key
                    "ACTest_!",  // source pin  (if assigned by merchant)
                    "76.90.58.204" //"127.0.0.1"  // IP address of end client (if applicable)
                    );


            // instantiate TransactionRequestObject
            TransactionRequestObject params = new TransactionRequestObject();

            // set card holder name
            params.setAccountHolder("Test Joe");

            // instantiate and populate transaction details
            TransactionDetail details = new TransactionDetail();
            details.setAmount(23.34);
            details.setDescription("My Test Sale from Java");
            details.setInvoice("119891");
            params.setDetails(details);

            // populate credit card data
            CreditCardData ccdata = new CreditCardData();
            ccdata.setCardNumber("4000100011112224");
            ccdata.setCardExpiration("0914");
            ccdata.setCardCode("123");
            params.setCreditCardData(ccdata);


            // Create request object
            RunSale request = new RunSale();

            // Add security token and params to request
            request.setToken(token);
            request.setParams(params);

            // Create response object
            TransactionResponse response;

            // run sale
            response = client.runSale(token, params);

            // Display response
            System.out.println("Response: " + response.getResult() + " RefNum: " + response.getRefNum());


        } catch (Exception e) {

            System.out.println("Soap Exception: " + e.getMessage());
        }
    }

}
