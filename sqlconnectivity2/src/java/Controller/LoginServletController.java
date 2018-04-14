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
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import java.sql.ResultSet;
/**
 *
 * @author Administrator
 */
@WebServlet(name = "LoginServletController", urlPatterns = {"/Login"})
public class LoginServletController extends HttpServlet {
Connection con=null;
PreparedStatement stmt=null;
String username,password;
ResultSet rs=null;

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
        
        try {
        Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb","root","password");
        username=request.getParameter("tname");
        password=request.getParameter("tpassword");
        rs=CheckUser(username,request,response);
        HttpSession session=request.getSession();
        if(rs.next())
        {
            if(rs.getString(1).equals(password))
            {
                
                if(session!=null)
                {
                    session.setAttribute("username",username);
                    RequestDispatcher loginsuccess=request.getRequestDispatcher("LoginSuccess");
                    loginsuccess.forward(request, response);
                }
                
            }
        }
        else
        {
            request.setAttribute("Loginerror","No username or email or password found.Please try again.");
        RequestDispatcher loginerror=request.getRequestDispatcher("LoginError");
                    loginerror.forward(request, response);
        }
        
        } catch(ClassNotFoundException ex) {            
            request.setAttribute("Loginerror","Loading of Java Driver fails."+ex);
        RequestDispatcher loginerror=request.getRequestDispatcher("LoginError");
                    loginerror.forward(request, response);
        }
        catch(SQLException ex)
        {
            request.setAttribute("Loginerror","Server refused the connection.Please try later."+ex);
        RequestDispatcher loginerror=request.getRequestDispatcher("LoginError");
                    loginerror.forward(request, response);
        }
    }
public ResultSet CheckUser(String username,HttpServletRequest request,HttpServletResponse response) throws SQLException,ServletException,IOException
{
    stmt=con.prepareStatement("select password from customer where username=? or email=?");
    stmt.setString(1,username);
    stmt.setString(2,username);
            
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
