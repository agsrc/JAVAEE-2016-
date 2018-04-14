/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author akshay
 */
public class Register {
    String FName,LName,Password,State;
    public Register(String fname,String lname,String Pass,String state)
    {
        FName=fname;
        LName=lname;
        Password=Pass;
        State=state;
    }
    
    public String getFirstName()
    {
        return FName;
    }
    public String getLastName()
    {
        return LName;
    }
    public String getState()
    {
        return State;
    }
    public String getPassword()
    {
        return Password;
    }
    }
    
    

