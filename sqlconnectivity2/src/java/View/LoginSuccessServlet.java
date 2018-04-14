/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
/**
 *
 * @author Administrator
 */
@WebServlet(name = "LoginSuccessServlet", urlPatterns = {"/LoginSuccess"})
public class LoginSuccessServlet extends HttpServlet {
Connection con=null;
HttpSession session=null;
PreparedStatement stmt=null;
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
        PrintWriter out = response.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb","root","password");
            session=request.getSession();
            if(session!=null)
            {
            String uname=(String)session.getAttribute("username");
            stmt=con.prepareStatement("select custname from customer where username=? or email=?");
            stmt.setString(1, uname);
            stmt.setString(2,uname);
            rs=stmt.executeQuery();
            if(rs.next())
            {
                out.println("<div style='float:right;postion:absolute;font-size:20px;color:blue'>Welcome <b style='color:green'>"+rs.getString(1)+"</b>&nbpsp;&nbsp;<a href='#'>Sign out</a></div>");
            }
            else
            {
               out.println("<div style='font-size:30px;color:red'>Please register first.<div>"); 
            }
        } 
            else
            {
                out.println("<div style='font-size:30px;color:red'>Please login.<div>"); 
            }
        }
        catch(ClassNotFoundException ex)
        {
            out.println("<p style='color:red;font-size:30px;font-weight:bold'>Loading of Java Driver fails."+ex+"</p>");
        }
        catch(SQLException ex) {            
            
        out.println("<p style='color:red;font-size:30px;font-weight:bold'>Server refused the connection.Please try later."+ex+"</p>");
        
        }
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
