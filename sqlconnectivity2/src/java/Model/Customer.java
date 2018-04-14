/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Administrator
 */
public class Customer {
    String custName,custUName,custPassword,custEmailAddress,custPhoneNo;
    public Customer()
    {
        
    }
    public void setCustomerName(String name)
    {
        custName=name;
    }
    public void setCustomerUserName(String UName)
    {
        custUName=UName;
    }
    public void setCustomerPassword(String password)
    {
        custPassword=password;
    }
    public void setCustomerEmailAddress(String email)
    {
        custEmailAddress=email;
    }
    public void setCustomerContactNumber(String contact)
    {
        custPhoneNo=contact;
    }
    public String getCustomerName()
    {
        return custName;
    }
    public String getCustomerUName()
    {
        return custUName;
    }
    public String getCustomerPassword()
    {
        return custPassword;
    }
    public String getCustomerEmailAddress()
    {
        return custEmailAddress;
    }
    public String getCustomerContactNumber()
    {
        return custPhoneNo;
    }
}
