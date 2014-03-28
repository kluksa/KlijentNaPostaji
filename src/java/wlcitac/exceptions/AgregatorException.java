/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wlcitac.exceptions;

/**
 *
 * @author kraljevic
 */
public class AgregatorException extends Exception{
    public AgregatorException() {
    }

    public AgregatorException(String message) {
        super(message);
    }

    public AgregatorException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public AgregatorException(Throwable throwable) {
        super(throwable);
    }
}
