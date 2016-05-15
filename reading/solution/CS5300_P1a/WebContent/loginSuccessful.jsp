<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Hanwen Wang log inSuccessful - Session management by Cookies</title>
<style type="text/css">
body {
    background-image: url('http://crunchify.com/bg.png');
}
</style>
</head>
 
<body>
    <p> NetID:hw544 </p>
    <div align="center">

        <%
           /*  String message = null; */
        	String message = (String)request.getAttribute("message");
        	int version = (Integer)request.getAttribute("sessionVersion");
        	int userId = (Integer)request.getAttribute("userID");
            String expirationDate = (String)request.getAttribute("Expiration date");
            
            out.println("<p> sessionID:" + Integer.toString(userId) + "</p>");
            out.println("<p> session version:" + Integer.toString(version) + "</p>");
            out.println("<p> Expiration Date:" + expirationDate + "</p>");
            Date date = new Date();
            out.println("<p> Date:" + date.toString() + "</p>");
            out.print("message is" + message);
                           
/*             if (message == null)
                response.sendRedirect("login.html"); */
        %>
        <div align="center">
        <form action="loginservlet" method="post">
            <input type="submit" name="replace" value="replace" />
            <input type="text" name="replaceMessage" id="replaceMessage" />  
          
            <input type="submit" name="refresh" value="refresh" />
            <input type="submit" name="logout" value="logout" />
        </form>
    	</div>
    </div>
</body>
</html>