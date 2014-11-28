package Database;

import java.net.MalformedURLException;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import android.content.Context;

public class DbConnection {
	
	private MobileServiceClient mClient;

	
	public MobileServiceClient connectToAzure(Context context){
		String url = "https://redpandanic.azure-mobile.net/";
		String secret =  "bPMwoAaNznctNZORRuuNaUCioqHEsQ49";
		try {
			mClient = new MobileServiceClient(url,secret,context);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  mClient;
	}
}
