/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import Model.Customer;
import javax.servlet.RequestDispatcher;
/**
 *
 * @author GNP_FAC
 */
@WebServlet(name = "Registerservlet", urlPatterns = {"/Register"})
public class Registerservlet extends HttpServlet {
Connection con=null;
PreparedStatement stmt=null;
Customer cust=null;
RequestDispatcher nextView=null;
String username=null,password=null,customerName=null,customerEmail=null,customerPhoneNo=null;
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb","root","password");
        username=request.getParameter("tuname");
        customerName=request.getParameter("tcustname");
        password=request.getParameter("tpassword");
        customerEmail=request.getParameter("tEmail");
       customerPhoneNo=request.getParameter("tcontact");
       int rowsStored=saveCustomer(customerName,username,password,customerEmail,customerPhoneNo,request,response);
       if(rowsStored>0)
       {
           cust=new Customer();
           cust.setCustomerName(customerName);
           cust.setCustomerUserName(username);
           cust.setCustomerPassword(password);
           cust.setCustomerEmailAddress(customerEmail);
           cust.setCustomerContactNumber(customerPhoneNo);
           request.setAttribute("customer",cust);
        nextView=request.getRequestDispatcher("SuccessRegistration");
        nextView.forward(request, response);
       }
       else
       {
           request.setAttribute("message","Registration Process has generated some error.Please try again");
        nextView=request.getRequestDispatcher("ErrorRegistration");
        nextView.forward(request, response);
       }
        } catch(ClassNotFoundException ex) {            
            request.setAttribute("message","Loading of Java Driver Fails"+ex);
        nextView=request.getRequestDispatcher("ErrorRegistration");
        nextView.forward(request, response);
        }
        catch(SQLException ex)
        {
               request.setAttribute("message","Unable to connect to server.Please try again"+ex);
        nextView=request.getRequestDispatcher("ErrorRegistration");
        nextView.forward(request, response);
        }
    }
public int saveCustomer(String customerName,String Uname,String Pass,String Email,String Phone,HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException,SQLException
{
    
    stmt=con.prepareStatement("insert into customer values(?,?,?,?,?)");
    stmt.setString(1,customerName);
    stmt.setString(2,Uname);
    stmt.setString(3,Pass);
    stmt.setString(4,Email);
    stmt.setString(5,Phone);
    
    return stmt.executeUpdate();
    
    
}
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
