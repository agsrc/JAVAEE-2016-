/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.Employer;

/**
 *
 * @author akshay
 */
@WebServlet(name = "RegistrationEmployerControllerServlet", urlPatterns = {"/EmployerRegistration"})
public class RegistrationEmployerControllerServlet extends HttpServlet {
String Cname=null,empname=null,empprofile=null,emplocation=null,empemail=null,
        empphone=null,emppage=null,emppassword=null;
PreparedStatement stmt=null;
Connection con;
int rowstored=0;
Employer emp=null;
RequestDispatcher nextview=null;
        
            
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mainproject","root","password");
            Cname=request.getParameter("Cname");
            empname=request.getParameter("empname");
            empprofile=request.getParameter("empprofile");
            emplocation=request.getParameter("emplocation");
            empemail=request.getParameter("empemail");
            empphone=request.getParameter("empphone");
            emppage=request.getParameter("emppage");
            emppassword=request.getParameter("emppassword");
             rowstored =SaveEmployer( Cname, empname, empprofile, emplocation,empemail, empphone ,emppage,emppassword,request, response);//why request response used
        if(rowstored>0)
        {
            emp=new Employer();
            emp.setCname(Cname);
            emp.setemplocation(emplocation);
            emp.setempmail(empemail);
            emp.setempname(empname);
            emp.setemppage(emppage);
            emp.setemppassword(emppassword);
            emp.setempprofile(empprofile);
            emp.setempphone(empphone);
        request.setAttribute("Employer",emp);
        nextview=request.getRequestDispatcher("EmployerRegistrationSuccessServlet");
        nextview.forward(request, response);
        }
        else
        {
             request.setAttribute("message","Registration Process has generated some error.Please try again");
        nextview=request.getRequestDispatcher("ErrorRegistration");
        nextview.forward(request, response);
        }}
        
       
            catch(ClassNotFoundException ex)
            {
                request.setAttribute("message","Loading of Java Driver Fails"+ex);
        nextview=request.getRequestDispatcher("EmployerRegistrationErrorServlet");
        nextview.forward(request, response);
            }
        catch(SQLException ex)
        {
                   request.setAttribute("message","Unable to connect to server.Please try again"+ex);
        nextview=request.getRequestDispatcher("EmployerRegistrationErrorServlet");
        nextview.forward(request, response);
        }
        finally {            
            out.close();
        }
    }
public int SaveEmployer(String Cname,String empname,String empprofile,String emplocation,String empemail ,
       String empphone ,String emppage,String emppassword, HttpServletRequest request, HttpServletResponse response)throws IOException,SQLException,ServletException
{
    stmt=con.prepareStatement("insert into Employer values(?,?,?,?,?,?,?,?)");
    stmt.setString(1,Cname);
    stmt.setString(2,empname);
stmt.setString(3,emppassword);
stmt.setString(4,empprofile);
stmt.setString(5,emplocation);
stmt.setString(6,empemail);
stmt.setString(7,empphone);
stmt.setString(8,emppage);
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
