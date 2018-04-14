/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author akshay
 */
public class Employer {
    String Companyname=null,employername=null,employerprofile=null,employerlocation=null,employeremail=null,
        employerphone=null,employerpage=null,employerpassword=null;
   
            
   public void setCname(String Cname){Companyname=Cname;};
   public void setempname(String empname){employername=empname;};
   public void setempprofile(String empprofile){employerprofile=empprofile;};
   public void setemplocation(String emplocation){employerlocation=emplocation;};
   public void setempmail(String empemail){employeremail=empemail;};
   public void setempphone(String empphone){employerphone=empphone;};
   public void setemppage(String emppage){employerpage=emppage;};
   public void setemppassword(String emppassword){employerpassword=emppassword;};
   
   public  String getCname(){ return Companyname;};
   public  String getempname(){return employername;};
   public  String getempprofile(){return employerprofile;};
   public  String getemplocation(){return employerlocation;};
   public  String getempmail(){return employeremail;};
   public  String getempphone(){return employerphone;};
   public  String getemppage(){return employerpage;};
   public  String getemppassword(){return employerpassword;};
    
}
