package com.halifaxcarpool.customer.business.authentication;

public enum AuthenticationResult {

    SUCCESS("success"),
    FAILURE("failure");

    private String result;

    AuthenticationResult(String result) {
        this.result = result;
    }

    public String getAuthenticationResult() {
        return result;
    }

}
