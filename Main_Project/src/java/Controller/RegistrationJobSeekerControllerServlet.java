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
import javax.servlet.RequestDispatcher;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import Model.JobSeeker;

/**
 *
 * @author akshay
 */
@WebServlet(name = "RegistrationJobSeekerControllerServlet", urlPatterns = {"/JobSeekerRegistration"})
public class RegistrationJobSeekerControllerServlet extends HttpServlet {
String name, password, email, location,gender,phone;
    Connection con;
    PreparedStatement stmt=null;
    int rowstored;
    JobSeeker cust;
    RequestDispatcher nextview;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mainproject","root","password");
                    name=request.getParameter("name");  //why inverted comma
                    password=request.getParameter("password");
                    email= request.getParameter("email");       
                    location=request.getParameter("location");
                            gender=request.getParameter("gender");
                            phone=request.getParameter("phone");
            rowstored = savejobseeker(name, password, location, gender,phone,email,request,response); // what if i change argument location?
        if(rowstored>0)
        {
            cust=new JobSeeker();
            cust.setName(name);
            cust.setPassword(password);
            cust.setGender(gender);
            cust.setLocation(location);
            cust.setPhone(phone);
            cust.setEmail(email);
            request.setAttribute("jobseeker", cust);
            nextview=request.getRequestDispatcher("JobSeekerRegistrationSuccesst");// why not nextview.getRequestDispatcher?
            nextview.forward(request,response);
                    
        }
        else
        {
            request.setAttribute("message","registration process has generated some errors");
            nextview=request.getRequestDispatcher("JobSeekerRegistrationErrorServlet");
            nextview.forward(request,response);
        }
        
        }
        catch(SQLException ex)
        {
        request.setAttribute("message", "could not be connected to server right now"+ex);
        nextview=request.getRequestDispatcher("JobSeekerRegistrationErrorServlet");
        nextview.forward(request, response);
        }
        catch(ClassNotFoundException ex)
        {
        request.setAttribute("message","Loading of java driver failed");
        nextview=request.getRequestDispatcher("JobSeekerRegistrationErrorServlet");
        nextview.forward(request, response);
        }
        finally {            
            out.close();
        }
    
    }

    public int savejobseeker(String name,String password,String location,String gender,String phone,String email, HttpServletRequest request,HttpServletResponse response)throws SQLException
    {   
        stmt=con.prepareStatement("insert into jobseeker values(?,?,?,,?,?,?)");
        stmt.setString(1, name);
        stmt.setString(2,password);
        stmt.setString(3, location);
        stmt.setString(4,gender);
        stmt.setString(5,phone);
        stmt.setString(6,email);
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
