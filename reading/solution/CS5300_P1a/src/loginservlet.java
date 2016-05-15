import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.Calendar;
/**
 * @author hanwenwang
 * @version 1
 * @since 2016/3/19
 */

/**
 * The servlet to Tackle with the post request from login.html and loginSuccessul.jsp
 */
public class loginservlet extends HttpServlet {	 

	private static final long serialVersionUID = 1L;
	//MapHash To store userInfo, and the map is synchronized
	static Map<Integer, UserInfor> map = Collections.synchronizedMap(new HashMap<Integer, UserInfor>());
	
	//start is the sessionId for next coming user.
	static int start = 0;
	
	//ExtensionTime is how long we will expend the cookie life life time when we refresh
	static final int ExtensionTime = 1;
	
	protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//judge if the user has been created a cookie
		Cookie[] cookies = request.getCookies();
	    System.out.println("do post");
	    Cookie userCookie = null;
	    if(cookies != null){
	    	for(int i = 0; i < cookies.length; i++){
	  	    	if(cookies[i].getName().equals("CS5300PROJ1SESSION"))
	  	    	userCookie = cookies[i];	
	  	    }
	    }
	    
	    //if this is request from new user, create a new cookie
	    if(cookies == null || userCookie == null){ 
	    	
	    	//set the cookie name as "CS5300PROJ1SESSION", and cookie value as sessionID
	    	Cookie newCookie = new Cookie("CS5300PROJ1SESSION", Integer.toString(start));		 
	 		newCookie.setValue(Integer.toString(start));
	 		 
	 		//If default message is Hello User
	 		String message = "Hello User";
	 		if(request.getParameter("replaceMessage") != null){
	 			//judge if the length of message is larger then 512
	 			String temMessage = request.getParameter("replaceMessage");
	 			//turn the temMessage to bytes
	 			final byte[] utf8Bytes = temMessage.getBytes("UTF-8");
	 			//if the length of string is less or equal than 512
	 			if(utf8Bytes.length <= 512)
	 				message = request.getParameter("replaceMessage");
	 		} 
	 		//This is to set the expiration date of the cookie
	 		Calendar date = Calendar.getInstance();
	 		date.add(Calendar.MINUTE, ExtensionTime);
	 		UserInfor newUser = new UserInfor(start, message,date);
	 		map.put(start, newUser);
	 		//increase the ID for next incoming user
	 		start++;	 
	 		//create cookie
	 		newCookie.setMaxAge(ExtensionTime * 60);
	 		response.addCookie(newCookie);
	 		 
	 		//attach the attributes to show in JSP file
	 		request.setAttribute("userID",newUser.getUserId());
			request.setAttribute("sessionVersion",newUser.getVersion());
			request.setAttribute("message", newUser.getMessage());
			request.setAttribute("Expiration date", newUser.getExpiration().getTime().toString());
			
	  	    RequestDispatcher dispatcher = request.getSession().getServletContext().getRequestDispatcher("/loginSuccessful.jsp");
	 		dispatcher.forward(request, response); 
	    }
	    else{	//there already exists userInfor for specific user
	    		System.out.print("There is already a cookie");
	    		String IdString = userCookie.getValue();
	    		
	    		//get the UserInfor for the SessionID
	    		System.out.println("The session ID is" + IdString);
	    		int UserIdNow = Integer.parseInt(IdString);
	    		if(map.containsKey(UserIdNow)){
	    			System.out.println("there is no such UserID");
	    		}
	    		else{
	    			System.out.println("find userID is" + UserIdNow);
	    		}
	    		
	    		UserInfor userInfor = map.get(UserIdNow);
	    		//Change the expiration date
	    		Calendar date = Calendar.getInstance();
		 		date.add(Calendar.MINUTE, ExtensionTime);
		 		userInfor.setExpiration(date);
		 		//change the cookie expiration time
		 		userCookie.setMaxAge(ExtensionTime * 60);
		 		//Change the version number for one user cookie
		 		userInfor.setVersion(userInfor.getVersion()+1);
		 		
	    		if (request.getParameter("replace") != null){ 	//replace the new message
	    			System.out.println("This is a replace operation");	
	    			String message = request.getParameter("replaceMessage");
	    			//print out the message for test
	    			System.out.println(message);
	    			
		 			final byte[] utf8Bytes = message.getBytes("UTF-8");
		 			if(utf8Bytes.length <= 512)
		 				userInfor.setMessage(message);	
	    		} 
	    		else if (request.getParameter("refresh") != null) {	//refresh the session
	    			System.out.println("This is a refresh operation");
	    		} 
	           
	    		else if (request.getParameter("logout") != null) {	 //destory the session
	    			System.out.println("This is a logout operation");
	    			//destory the cookie and delete the cookie from the userInfor hashMap
	    			userCookie.setMaxAge(0);
	    			//remove the UserInfro from HashMap
	    			map.remove(UserIdNow);
	    			//If if is logout. We need to direct the page to login message
	    			response.addCookie(userCookie);
	    			RequestDispatcher dispatcher = request.getSession().getServletContext().getRequestDispatcher("/login.html");
	  		   		dispatcher.forward(request, response);
	  		   		return;
	    		} 
	    		else {
	    			System.out.println("there is no operation");
	    		}	
	    		request.setAttribute("userID",userInfor.getUserId());
	    		request.setAttribute("sessionVersion",userInfor.getVersion());
	    		request.setAttribute("message", userInfor.getMessage());
	    		request.setAttribute("Expiration date", userInfor.getExpiration().getTime().toString());
	    		
	    		response.addCookie(userCookie); 
		   	    RequestDispatcher dispatcher = request.getSession().getServletContext().getRequestDispatcher("/loginSuccessful.jsp");
		   		dispatcher.forward(request, response);  
	    }
	}
	
	/**
	 * doget is just for test
	 */
	protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 System.out.println("do get");
		 Cookie newCookie = new Cookie("CS5300PROJ1SESSION", Integer.toString(start));		 
 		 newCookie.setValue(Integer.toString(start));
 		 //This is to define a new userInformation
 		 UserInfor newUser = new UserInfor(start, "Hello User");
 		 map.put(start, newUser);
 		 
 		 //Add the start number of the position
 		 start++;
 		 
 		 //refresh the cookie
 		newCookie.setMaxAge(ExtensionTime * 60);
 		 //This is to return the cookie with the request
 		response.addCookie(newCookie);
 		 
 		request.setAttribute("userID",newUser.getUserId());
		request.setAttribute("sessionVersion",newUser.getVersion());
		request.setAttribute("message", newUser.getMessage());
		request.setAttribute("Expiration date", newUser.getExpiration().getTime().toString());
		
  	    RequestDispatcher dispatcher = request.getSession().getServletContext().getRequestDispatcher("/loginSuccessful.jsp");
 		dispatcher.forward(request, response);  
	}
}