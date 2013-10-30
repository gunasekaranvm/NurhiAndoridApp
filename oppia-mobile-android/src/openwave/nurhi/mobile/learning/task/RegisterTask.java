/* 
 * This file is part of OppiaMobile - http://oppia-mobile.org/
 * 
 * OppiaMobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * OppiaMobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OppiaMobile. If not, see <http://www.gnu.org/licenses/>.
 */

package openwave.nurhi.mobile.learning.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import openwave.nurhi.mobile.learning.activity.RegisterActivity;
import openwave.nurhi.mobile.learning.activity.WriteFile;
import openwave.nurhi.mobile.learning.application.MobileLearning;
import openwave.nurhi.mobile.learning.listener.SubmitListener;
import openwave.nurhi.mobile.learning.model.User;
import openwave.nurhi.mobile.learning.utils.HTTPConnectionUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import openwave.nurhi.mobile.learning.R;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.bugsense.trace.BugSenseHandler;

public class RegisterTask extends AsyncTask<Payload, Object, Payload> {

	public static final String TAG = RegisterTask.class.getSimpleName();

	private Context ctx;
	private SharedPreferences prefs;
	private SubmitListener mStateListener;

	public RegisterTask(Context ctx) {
		this.ctx = ctx;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	@Override
	protected Payload doInBackground(Payload... params) {

		Payload payload = params[0];
		User u = (User) payload.getData().get(0);
		HTTPConnectionUtils client = new HTTPConnectionUtils(ctx);

		String url = prefs.getString("prefServer",
				ctx.getString(R.string.prefServerDefault))
				+ MobileLearning.REGISTER_PATH;
		System.out.println("here getting method name" + url);
		HttpPost httpPost = new HttpPost(url);

		try {

			// 10-09 16:47:08.115: I/System.out(14625): Response string is guna
			// got***{"error_message": "'passwordagain'", "traceback":
			// "Traceback (most recent call last):\n\n  File \"/usr/local/lib/python2.6/dist-packages/tastypie/resources.py\", line 217, in wrapper\n    response = callback(request, *args, **kwargs)\n\n  File \"/usr/local/lib/python2.6/dist-packages/tastypie/resources.py\", line 459, in dispatch_list\n    return self.dispatch('list', request, **kwargs)\n\n  File \"/usr/local/lib/python2.6/dist-packages/tastypie/resources.py\", line 491, in dispatch\n    response = method(request, **kwargs)\n\n  File \"/usr/local/lib/python2.6/dist-packages/tastypie/resources.py\", line 1357, in post_list\n    updated_bundle = self.obj_create(bundle, **self.remove_api_resource_names(kwargs))\n\n  File \"/usr/local/lib/python2.6/dist-packages/oppia/api/resources.py\", line 119, in obj_create\n    'password_again': bundle.data['passwordagain'],\n\nKeyError: 'passwordagain'\n"}
			// 10-09 16:47:08.115: I/System.out(14625): resultResponse found
			// ******Error connecting to server. Please check you have an active
			// internet connection and try again.

			// update progress dialog
			publishProgress(ctx.getString(R.string.register_process));
			Log.d(TAG, "Registering... " + u.getusername());
			// add post params
			JSONObject json = new JSONObject();

			json.put("username", u.getusername());
			json.put("firstname", u.getfirstname());
			json.put("lastname", u.getlastname());
			json.put("email", u.getemail());
			json.put("phoneno", u.getphone());
			json.put("password", u.getpassword());
			json.put("passwordagain", u.getpassword());

			json.put("current_working_city", u.getcurrent_working_city());
			json.put("currently_working_facility",
					u.getcurrently_working_facility());
			json.put("staff_type", u.getstaff_type());

			json.put("fpm_use_for_yourself", u.getfpm_use_for_yourself());
			json.put("nurhi_sponsor_training", u.getnurhi_sponsor_training());
			json.put("current_place_employment",
					u.getcurrent_place_employment());
			json.put("highest_education_level", u.gethighest_education_level());
			json.put("religion", u.getreligion());
			json.put("sex", u.getSex());
			json.put("age", u.getAge());

			StringEntity se = new StringEntity(json.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			httpPost.setEntity(se);

			// make request
			HttpResponse response = client.execute(httpPost);

			InputStream content = response.getEntity().getContent();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					content), 4096);
			String responseStr = "";
			String s = "";
			while ((s = buffer.readLine()) != null) {
				responseStr += s;
			}

			System.out.println("Response string is guna got***" + responseStr);

			switch (response.getStatusLine().getStatusCode()) {
			case 400: // unauthorised
				payload.setResult(false);
				payload.setResultResponse(responseStr);
				break;
			case 201: // logged in
				JSONObject jsonResp = new JSONObject(responseStr);
				u.setApi_key(jsonResp.getString("api_key"));
				try {
					u.setPoints(jsonResp.getInt("points"));
					u.setBadges(jsonResp.getInt("badges"));
				} catch (JSONException e) {
					u.setPoints(0);
					u.setBadges(0);
				}
				u.setfirstname(jsonResp.getString("first_name"));
				u.setlastname(jsonResp.getString("last_name"));
				payload.setResult(true);
				payload.setResultResponse(ctx
						.getString(R.string.register_complete));
				break;
			default:
				payload.setResult(false);
				payload.setResultResponse(ctx
						.getString(R.string.error_connection));
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			payload.setResult(false);
			payload.setResultResponse(ctx.getString(R.string.error_connection));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			payload.setResult(false);
			payload.setResultResponse(ctx.getString(R.string.error_connection));
		} catch (IOException e) {
			e.printStackTrace();
			payload.setResult(false);
			payload.setResultResponse(ctx.getString(R.string.error_connection));
		} catch (JSONException e) {
			BugSenseHandler.sendException(e);
			e.printStackTrace();
			payload.setResult(false);
			payload.setResultResponse(ctx
					.getString(R.string.error_processing_response));
		}
		return payload;
	}

	@Override
	protected void onPostExecute(Payload response) {
		synchronized (this) {
			if (mStateListener != null) {
				mStateListener.submitComplete(response);
			}
		}
	}

	public void setLoginListener(SubmitListener srl) {
		synchronized (this) {
			mStateListener = srl;
		}
	}
}
