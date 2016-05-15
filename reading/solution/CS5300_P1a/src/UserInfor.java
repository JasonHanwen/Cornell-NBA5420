import java.util.Calendar;
/**
 * @author hanwenwang
 * @version 1
 * @since 2016/3/19
 * @description class to store userInformation
 */
public class UserInfor {
	private int userId;				//sessionID
	private int version = 0;		//session version
	private String message = "";	//message with the session
	private Calendar Expiration;	//expiration date for the session
	public UserInfor(int userId, String message){
		this.userId = userId;
		this.message = message;
	}
	public UserInfor(int userId, String message, Calendar Expiration){
		this.userId = userId;
		this.message = message;
		this.Expiration = Expiration;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Calendar getExpiration() {
		return Expiration;
	}
	public void setExpiration(Calendar expiration) {
		Expiration = expiration;
	}
}
