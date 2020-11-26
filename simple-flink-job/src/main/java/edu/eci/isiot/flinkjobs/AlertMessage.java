/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.isiot.flinkjobs;

/**
 *
 */
public class AlertMessage {
    
    public String recipientEmail;

    public String message;

    public AlertMessage(String recipientEmail, String message) {
        this.recipientEmail = recipientEmail;
        this.message = message;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AlertMessage{" + "recipientEmail=" + recipientEmail + ", message=" + message + '}';
    }   
           
    
}
