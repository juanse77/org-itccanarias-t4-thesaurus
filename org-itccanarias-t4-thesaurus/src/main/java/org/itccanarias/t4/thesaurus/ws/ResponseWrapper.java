package org.itccanarias.t4.thesaurus.ws;

public class ResponseWrapper {

    public final int response_code;
    public final String message;

    public ResponseWrapper(int response_code) {
        this.response_code = response_code;
        this.message = "";
    }

    public ResponseWrapper(int response_code, String message) {
        this.response_code = response_code;
        this.message = message;
    }
}
