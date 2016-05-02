/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import forwarding.RequestForwarder;

/**
 *
 * @author Kristian Nielsen
 */
public class Tester {

    public static void main(String[] args) {
        RequestForwarder rf = new RequestForwarder();

        String json = "{\n"
                + "    \"airline\":\"AngularJS Airline\",\n"
                + "    \"flightID\":\"3257-1459830600000\",\n"
                + "    \"numberOfSeats\":2,\n"
                + "    \"reserveeName\":\"Peter Hansen\",\n"
                + "    \"reservePhone\":\"12345678\",\n"
                + "    \"reserveeEmail\":\"peter@peter.dk\",\n"
                + "    \"passengers\":[\n"
                + "        {\"firstName\":\"Peter\",\"lastName\":\"Peterson\"},\n"
                + "        {\"firstName\":\"Jane\",\"lastName\":\"Peterson\"}\n"
                + "    ]\n"
                + "}";
        System.out.println(rf.bookingRequest(json));

    }
}
