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
public class PrihvatWSException extends Exception {
    private static final Logger log = Logger.getLogger(PrihvatWSException.class.getName());

    public PrihvatWSException() {
    }

    public PrihvatWSException(String message) {
        super(message);
    }

    public PrihvatWSException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrihvatWSException(Throwable cause) {
        super(cause);
    }
}
