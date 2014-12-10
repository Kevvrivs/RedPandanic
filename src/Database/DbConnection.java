package Database;

import java.net.MalformedURLException;

import com.microsoft.windowsazure.mobileservices.*;

import android.content.Context;

public class DbConnection {
	

	public static MobileServiceClient connectToAzureService(Context context){
		//String url = "https://pandabears.azure-mobile.net/";
		//String key  = "BLHpgClAaVeLTPYTCNkUBJczvEJzVg75";
		
		//Kevin's
		//String url = "https://prevkit.azure-mobile.net/";
		//String key = "rXootnVHePtKaJRpTRSbqABoIdvzkE36";
		String url = "https://prevkit.azure-mobile.net/";
		String key =  "rXootnVHePtKaJRpTRSbqABoIdvzkE36";
		
		MobileServiceClient mClient = null; 
		
		try {
			mClient = new MobileServiceClient(url,key,context);
			return mClient;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mClient;
	}
}
