/**
 * 
 */
package sheffield.hexu.app2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import sheffield.mbg.pojo.Restaurant;
import sheffield.mbg.pojo.Restaurant_photo;

import javax.json.Json;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


/**
 * @author xuhe
 *
 */

@Service(value = "app2Service")
public class App2Service {

	public App2Service(){
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */

	// 写一个或多个方法从URL读取字符串转换成JSON或别的数据结构返回
	//通过连接到json文件的url获取json字符串
	public String getJSONstring(String urlPath) throws IOException {

		URL url = new URL(urlPath);

		//use openConnection方法返回URLconnection的实例，此实例代表url连接的文件
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		//Opens a communications link to the resource referenced by this URL
		httpURLConnection.connect();
		//Read the content of this url
		InputStream inputStream = httpURLConnection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String str = null;
		StringBuffer sb = new StringBuffer();
		while ((str = bufferedReader.readLine()) != null) {
			sb.append(str);
		}

		bufferedReader.close();
		httpURLConnection.disconnect();
		return sb.toString();
	}

	//此方法接收json字符串，并且转换成为json对象
	public void jsonToObject(String jsonstr){
		JSONObject jsonObject = new JSONObject(jsonstr);
		String value = jsonObject.getString("results");//通过key获取值
		System.out.println(value);
	}

	public String name;
	public String lastname;


	public static void main(String args[]) throws IOException {

		HashMap<String,Restaurant> allRest = new HashMap();
		//This ArrayList is used for help adding Restaurant Object in to the MAP -- allRest

		try {
			//Use the getJSONString method in APP2service to transfer from URL object to String.
			App2Service app2Service = new App2Service();
			String search = app2Service.getJSONstring("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=53.3810000,-1.4790000&radius=1000&type=restaurant&key=AIzaSyBI87YwMOnQw5W3HgQ7A-x60FBNXpR8lKU");

			//Transfer String to JSONObject
			JSONObject jsonObject = new JSONObject(search);
			JSONArray Results = null;

			//Access the jsonArray "result"
			Results = jsonObject.getJSONArray("results");

			for (int i=0;i<Results.length();){
				Restaurant rest = new Restaurant();

				//Get the first item in the JSONArray, Solve the Results JSONArray
				JSONObject result = (JSONObject) Results.get(i);
				String Name = result.getString("name");
				String Address = result.getString("vicinity");
				String Icon = result.getString("icon");
				String PlaceID = result.getString("place_id");
				//Continue is used to deal the problem -- the short of one rating attribute
				Double rating = null;
				try {
					rating = result.getDouble("rating");
				}
				catch (Exception e){
					continue;
				}
				finally {
					i++;
				}

				//Resolve the lat and lng under the location(which under the geometry)
				JSONObject geo = result.getJSONObject("geometry");
				JSONObject loc = geo.getJSONObject("location");
				Double Lat = loc.getDouble("lat");
				Double Lng = loc.getDouble("lng");

				//Saved result in Restaurant Object
				rest.setName(Name);
				rest.setVicinity(Address);
				rest.setIcon(Icon);
				rest.setPlace_id(PlaceID);
				rest.setRating(rating);
				rest.setLat(Lat);
				rest.setLng(Lng);


				//Solve the photo JSONArray
				JSONArray Photos = result.getJSONArray("photos");
				for (int j=0;j<Photos.length();){
					JSONObject photo = (JSONObject) Photos.get(j);
					String Photo_reference = photo.getString("photo_reference");
					Integer Height = photo.getInt("height");
					Integer Width = photo.getInt("width");

					//saved results in the Restaurant_photo Object
					Restaurant_photo rp = new Restaurant_photo();
					rp.setPhoto_reference(Photo_reference);
					rp.setHeight(Height);
					rp.setWidth(Width);
					//save this Object as a property of Restaurant Object
					rest.setRestaurant_photo(rp);

					j++;
				}

				allRest.put(rest.getName(),rest);


			}
			System.out.println(allRest.get("Orient Express").getRestaurant_photo().getHeight());

		}
		catch (JSONException Json){
			System.out.println("语法错误或有重复key");
			Json.printStackTrace();

		}






	}


}
