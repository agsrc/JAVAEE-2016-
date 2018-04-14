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
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author akshay
 */
@WebServlet(name = "LoginEmployerControllerServlet", urlPatterns = {"/LoginEmployerControllerServlet"})
public class LoginEmployerControllerServlet extends HttpServlet{
ResultSet rs=null;
HttpSession session=null;
RequestDispatcher nextview=null;
Connection con=null;
PreparedStatement stmt;
String username,password;
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
con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mainproject","root","password");
        username=request.getParameter("name");
        password=request.getParameter("password");
        rs=checkuser(username,request,response);
        session=request.getSession();
        if(rs.next())
        {
                    if(rs.getString(1).equals(password))//?
                    {
                    if(session!=null)
                    {
                    session.setAttribute("username",username);
                   RequestDispatcher loginsuccess=request.getRequestDispatcher("EmployerLoginSuccess");//?
                   loginsuccess.forward(request, response);
                    }
                    else
                    {
                    request.setAttribute("loginerror"," not able to log in please try again");
                    RequestDispatcher loginerror=request.getRequestDispatcher("EmployerLoginErrorServlet");
                    loginerror.forward(request, response);
                    }
                   
                    }
                        
                        }
        } 
        catch(SQLException ex)
        {
            request.setAttribute("loginerror"," server refused to connect, try again"+ ex);
                    RequestDispatcher loginerror=request.getRequestDispatcher("EmployerLoginErrorServlet");
                    loginerror.forward(request, response);
        }
        catch(ClassNotFoundException ex)
        {
             request.setAttribute("loginerror"," driver failed to load, try again"+ ex);
                    RequestDispatcher loginerror=request.getRequestDispatcher("EmployerLoginErrorServlet");
                    loginerror.forward(request, response);
        }
    }
    public ResultSet checkuser(String username,HttpServletRequest request,HttpServletResponse response) throws SQLException,ServletException,IOException
    {
    stmt=con.prepareStatement("select password from Employer where name=? or email=?");
    stmt.setString(1, "name");
    stmt.setString(2, "name");
    return stmt.executeQuery();
              
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
