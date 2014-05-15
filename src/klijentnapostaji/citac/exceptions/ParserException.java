/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentnapostaji.citac.exceptions;

import java.util.logging.Logger;

/**
 *
 * @author kraljevic
 */
public class ParserException extends Exception {

    private static final Logger log = Logger.getLogger(ParserException.class.getName());

    public ParserException() {
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(Throwable cause) {
        super(cause);
    }

}
