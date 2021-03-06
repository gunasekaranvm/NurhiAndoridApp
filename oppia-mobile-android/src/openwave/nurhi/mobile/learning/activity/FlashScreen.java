package openwave.nurhi.mobile.learning.activity;

// import com.example.funfintegration.FunfMainActivity;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import openwave.nurhi.mobile.learning.R;
import openwave.nurhi.mobile.learning.utils.MyLocation;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import edu.mit.media.funf.FunfManager;
import edu.mit.media.funf.pipeline.BasicPipeline;
import edu.mit.media.funf.probe.builtin.SimpleLocationProbe;
import edu.mit.media.funf.probe.builtin.WifiProbe;

public class FlashScreen extends Activity {

	public static final String PIPELINE_NAME = "default";

	private File NigerianProjectFileDiectory = new File(
			Environment.getExternalStorageDirectory() + "/NigerianProjectFile");

	private File NigerianProjectFileDocumentDiectory = new File(
			Environment.getExternalStorageDirectory()
					+ "/NigerianProjectFile/documents");

	// commented ver 1.10.c

	// document
	// video
	// media

	MyLocation myLocation;

	private File NigerianProjectFileVideoDiectory = new File(
			Environment.getExternalStorageDirectory()
					+ "/NigerianProjectFile/video");

	File NigerianProjectFilemMdiaDiectory = new File(
			Environment.getExternalStorageDirectory()
					+ "/NigerianProjectFile/media");

	public static ArrayList<InputStream> input;
	public static ArrayList<InputStream> input1;

	private ArrayList<String> urlList;

	ArrayList<String> downloadPathList = new ArrayList<String>();
	Button btnllibrary;
	private ArrayList<File> fileList;

	DataOutputStream fos;
	OutputStream out;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	public static String Runningapps = "";
	public static String installedapps = "";
	public static String Networkprovider = "";
	public static String Deviceid = "";
	public static String simSerialNo = "";
	public static String batteryinfo = "";
	public static String locations = "";

	public static long remainingLocalStoragesdcard = 0;
	ArrayList<String> fileName = new ArrayList<String>();
	ArrayList<String> fileName1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.flashscreen);
		

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(FlashScreen.this);
		String version = prefs.getString("floderdelete", "");

		if (version.equals("")) {

			try {
				File f = NigerianProjectFileDiectory;
				deleteDirectory(f);
				System.out.println("done main nigerian");
			} catch (Exception e) {
				e.printStackTrace();

			}

			editor = prefs.edit();
			editor.putString("floderdelete", "done");
			editor.commit();

		}
		createLibrary();
		new BackgroundTask().execute();

		LatLong();

		//
		// new Handler().postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// //
		// // Intent intent = new Intent(FlashScreen.this,
		// // MobileLearningActivity.class);
		// // startActivity(intent);
		// //
		// // finish();

		// //
		// }
		// }, 6000);
		//
	}

	public void LatLong() {

		myLocation = new MyLocation(FlashScreen.this);

		// check if GPS enabled

		if (myLocation.isLocationAvailable()) {

			Double latitude = myLocation.getLatitude();

			Double longitude = myLocation.getLongitude();

			locations = "latitude:" + latitude + ",longitude:" + longitude;

		} else {

			// can't get location

			// GPS or Network is not enabled

			// Ask user to enable GPS/network in settings

			// enabel gps via program..

		}
	}

	class BackgroundTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				remainingLocalStoragesdcard = remainingLocalStorage();
				System.out.println("My Output**remainingLocalStoragesdcard*"
						+ remainingLocalStoragesdcard);
				Runningapps = runningapplication();
				System.out.println("My Output**Runningapps*" + Runningapps);
				installedapps = installedapp();
				System.out.println("My Output**installedapps*" + installedapps);

				batteryLevelUpdate();
				telephone();

			} catch (Exception e) {

			}
			return null;

		}
	}

	public void createLibrary() {

		urlList = new ArrayList<String>();

		// fileList is Used to check the Folder is Exist or not in the SD Card
		// to perform Download From Assests

		// fileName is Used to Read The File From Sdcard to Show in the APP When
		// User Click the Corresponding Button
		
		
		if (NigerianProjectFileDiectory.exists() == false) {
			NigerianProjectFileDiectory.mkdir();
		}

		if (NigerianProjectFileDocumentDiectory.exists() == false) {
			NigerianProjectFileDocumentDiectory.mkdir();
		}

		if (NigerianProjectFilemMdiaDiectory.exists() == false) {
			NigerianProjectFilemMdiaDiectory.mkdir();
		}

		if (NigerianProjectFileVideoDiectory.exists() == false) {
			NigerianProjectFileVideoDiectory.mkdir();
		}

		
		

		fileList = new ArrayList<File>();

		/* 23 */fileName.add("mec_wheels.pdf");

		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/documents/mec_wheels.pdf"));

		/* 24 */fileName.add("pluss.pdf");

		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/documents/pluss.pdf"));

		// /* 25 */fileName.add("gather_cue_card_clinical.pdf");
		//
		// fileList.add(new File(Environment.getExternalStorageDirectory()
		// + "/NigerianProjectFile/documents/gather_cue_card_clinical.pdf"));

		/* 26 */fileName.add("gathercue_card_non_clinicals.pdf");

		fileList.add(new File(
				Environment.getExternalStorageDirectory()
						+ "/NigerianProjectFile/documents/gathercue_card_non_clinicals.pdf"));

		/* 27 */fileName.add("faqss.pdf");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/documents/faqss.pdf"));

		/* 277 */fileName.add("nationalservices.pdf");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/documents/nationalservices.pdf"));

		/* 2777 */fileName.add("ojtmanuals.pdf");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/documents/ojtmanuals.pdf"));

		/* 2777 */fileName.add("quickreferences.pdf");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/documents/quickreferences.pdf"));

		fileName.add("session_overview.mp4");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/session_overview.mp4"));

		fileName.add("introductory_video.mp4");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/introductory_video.mp4"));

		fileName.add("cs4_scenario.mp4");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/cs4_scenario.mp4"));

		fileName.add("cs4_scenarioA.mp4");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/cs4_scenarioA.mp4"));

		fileName.add("cs3_scenario.mp4");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/cs3_scenario.mp4"));

		fileName.add("cs3_scenarioA.mp4");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/cs3_scenarioA.mp4"));

		fileName.add("cs2_scenarioA.mp4");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/cs2_scenarioA.mp4"));

		fileName.add("cs2_scenario.mp4");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/cs2_scenario.mp4"));

		fileName.add("CS1_Scenario1A.mp4");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/CS1_Scenario1A.mp4"));

		fileName.add("CS1_Scenario1.mp4");
		fileList.add(new File(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/CS1_Scenario1.mp4"));

		/*******************/
		//
		// fileName.add("CS1_Scenario1.mp4");
		// fileList.add(new File(Environment.getExternalStorageDirectory()
		// + "/NigerianProjectFile/media/CS1_Scenario1.mp4"));
		// fileName.add("CS1_Scenario1.mp4");
		// fileList.add(new File(Environment.getExternalStorageDirectory()
		// + "/NigerianProjectFile/media/CS1_Scenario1.mp4"));
		// fileName.add("CS1_Scenario1.mp4");
		// fileList.add(new File(Environment.getExternalStorageDirectory()
		// + "/NigerianProjectFile/media/CS1_Scenario1.mp4"));
		// fileName.add("CS1_Scenario1.mp4");
		// fileList.add(new File(Environment.getExternalStorageDirectory()
		// + "/NigerianProjectFile/media/CS1_Scenario1.mp4"));
		// fileName.add("CS1_Scenario1.mp4");
		// fileList.add(new File(Environment.getExternalStorageDirectory()
		// +
		// "/NigerianProjectFile/media/CS1_Scenario1.mp4"));fileName.add("CS1_Scenario1.mp4");
		// fileList.add(new File(Environment.getExternalStorageDirectory()
		// + "/NigerianProjectFile/media/CS1_Scenario1.mp4"));
		// fileName.add("CS1_Scenario1.mp4");
		// fileList.add(new File(Environment.getExternalStorageDirectory()
		// + "/NigerianProjectFile/media/CS1_Scenario1.mp4"));
		// fileName.add("CS1_Scenario1.mp4");
		// fileList.add(new File(Environment.getExternalStorageDirectory()
		// + "/NigerianProjectFile/media/CS1_Scenario1.mp4"));
		// fileName.add("CS1_Scenario1.mp4");
		// fileList.add(new File(Environment.getExternalStorageDirectory()
		// + "/NigerianProjectFile/media/CS1_Scenario1.mp4"));
		// fileName.add("CS1_Scenario1.mp4");
		// fileList.add(new File(Environment.getExternalStorageDirectory()
		// + "/NigerianProjectFile/media/CS1_Scenario1.mp4"));
		// /************************/
		//

		// delete files if exists

		ArrayList<String> deletefileadd = new ArrayList<String>();
		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/documents/mec_wheel.pdf");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/documents/plus.pdf");

		deletefileadd
				.add(Environment.getExternalStorageDirectory()
						+ "/NigerianProjectFile/documents/gather_cue_card_clinical.pdf");

		deletefileadd
				.add(Environment.getExternalStorageDirectory()
						+ "/NigerianProjectFile/documents/gathercue_card_non_clinical.pdf");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/documents/faqs.pdf");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/documents/nationalservice.pdf");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/documents/ojtmanual.pdf");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/documents/quickreference.pdf");

		deletefileadd
				.add(Environment.getExternalStorageDirectory()
						+ "/NigerianProjectFile/media/Module1Section1option2withpics.mp4");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/distanceed_module_2text.mp4");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/cs4_unsupportive_part1.mp4");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/cs4_supportive.mp4");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/cs3_unsupportive.mp4");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/cs3_supportive.mp4");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/cs2_supportive.mp4");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/cs2_unsupportive.mp4");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/CS1supportive.mp4");

		deletefileadd.add(Environment.getExternalStorageDirectory()
				+ "/NigerianProjectFile/media/cs1_unsupportive.mp4");

		for (int i = 0; i < deletefileadd.size(); i++) {

			try {
				File ffdele = new File(deletefileadd.get(i));
				ffdele.deleteOnExit();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		fileName1 = new ArrayList<String>();

		try {

			input = new ArrayList<InputStream>();

			input.add(getAssets().open("mec_wheels.pdf")); // 23

			input.add(getAssets().open("pluss.pdf")); // 25
			input.add(getAssets().open("gathercue_card_non_clinicals.pdf")); // 26
			input.add(getAssets().open("faqss.pdf")); // 27

			input.add(getAssets().open("nationalservices.pdf")); // 277
			input.add(getAssets().open("ojtmanuals.pdf")); // 2777
			input.add(getAssets().open("quickreferences.pdf")); // 27777 //6

			// session_overview.mp4
			// distanceed_module_ 2text.mp4
			// cs4_unsupportive_part1.mp4
			// cs4_scenarioA.mp4
			// cs3_scenario.mp4
			// cs3_scenarioA.mp4
			// cs2_scenario.mp4
			// cs2_scenarioA.mp4
			// CS1_Scenario1A.mp4
			// CS1_Scenario1.mp4

			input.add(getAssets().open("session_overview.mp4"));
			input.add(getAssets().open("introductory_video.mp4"));

			input.add(getAssets().open("cs4_scenario.mp4"));
			input.add(getAssets().open("cs4_scenarioA.mp4"));
			input.add(getAssets().open("cs3_scenario.mp4"));
			input.add(getAssets().open("cs3_scenarioA.mp4"));

			input.add(getAssets().open("cs2_scenarioA.mp4"));
			input.add(getAssets().open("cs2_scenario.mp4"));
			input.add(getAssets().open("CS1_Scenario1A.mp4"));
			input.add(getAssets().open("CS1_Scenario1.mp4")); // 10

			input1 = new ArrayList<InputStream>();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new download_task().execute();
	}

	class download_task extends AsyncTask<String, String, String> {
		ProgressDialog dialog = null;
		ArrayList<String> department_data;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			dialog = ProgressDialog.show(FlashScreen.this, "",
					"Downloading file...", true);
		}

		@Override
		protected String doInBackground(String... params) {

			if (dialog.isShowing()) {

				dialog.setCancelable(false);
			}

			if (NigerianProjectFileDiectory.exists() == false) {
				NigerianProjectFileDiectory.mkdir();
			}

			if (NigerianProjectFileDocumentDiectory.exists() == false) {
				NigerianProjectFileDocumentDiectory.mkdir();
			}

			if (NigerianProjectFilemMdiaDiectory.exists() == false) {
				NigerianProjectFilemMdiaDiectory.mkdir();
			}

			if (NigerianProjectFileVideoDiectory.exists() == false) {
				NigerianProjectFileVideoDiectory.mkdir();
			}

			Log.d("Error For This", "InputSize ---- >" + input.size());

			for (int i = 0; i < input.size(); i++) {
				Log.v("Error For This", "i ---- >" + i);

				File f2;
				if (fileList.get(i).exists() == false) {

					if (i <= 6) {

						f2 = new File(Environment.getExternalStorageDirectory()
								+ "/NigerianProjectFile/documents");

					} else {

						f2 = new File(Environment.getExternalStorageDirectory()
								+ "/NigerianProjectFile/media");
					}

					try {

						out = new FileOutputStream(f2 + "/" + fileName.get(i));

						byte[] buf = new byte[1024];
						int len;
						while ((len = input.get(i).read(buf)) > 0) {
							out.write(buf, 0, len);
						}

						input.get(i).close();
						out.close();

						Log.v("Download",
								"had been Succeed for   -----------------> "
										+ fileName.get(i));

					} catch (FileNotFoundException ex) {

						Log.d("File", "Error is" + ex);

					} catch (IOException e) {
						System.out.println(e.getMessage());
					} finally {
						try {
							input.get(i).close();
							out.close();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else
					Log.d("Document File", "Exit  ------  "
							+ fileName.get(i).toString());

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					Intent intent = new Intent(FlashScreen.this,
							MobileLearningActivity.class);
					startActivity(intent);
					finish();

				}
			}, 3000);

		}
	}

	public static long remainingLocalStorage() {
		StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
		stat.restat(Environment.getDataDirectory().getPath());
		long bytesAvailable = (long) stat.getBlockSize()
				* (long) stat.getAvailableBlocks();
		return bytesAvailable;
	}

	public String runningapplication() {

		String ss = "";
		try {
			ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

			List l = am.getRunningAppProcesses();

			Iterator i = l.iterator();
			PackageManager pm = this.getPackageManager();

			while (i.hasNext()) {
				ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i
						.next());
				try {

					CharSequence c = pm.getApplicationLabel(pm
							.getApplicationInfo(info.processName,
									PackageManager.GET_META_DATA));

					Log.w("LABEL", c.toString());
					ss = ss + " ," + c.toString();
					int a = c.length();

				} catch (Exception e) {

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return ss;
	}

	public String installedapp() {
		String ss = "";
		try {
			ArrayList<PackageInfo> res = new ArrayList<PackageInfo>();

			PackageManager pm = getApplicationContext().getPackageManager();

			List<PackageInfo> packs = pm.getInstalledPackages(0);

			for (int i = 0; i < packs.size(); i++) {

				PackageInfo p = packs.get(i);

				String description = (String) p.applicationInfo
						.loadDescription(pm);
				String label = p.applicationInfo.loadLabel(pm).toString();
				String packageName = p.packageName;
				String versionName = p.versionName;
				// log output

				ss = ss + label + " " + packageName + ",";
				// Log.d("description", "description------------>" +
				// description);
				// Log.d("label", "label--------------->" + label);
				// Log.d("packageName", "packageName----------->" +
				// packageName);
				// Log.d("VersionName", "VersionName---------->" + versionName);

			}
		} catch (Exception e) {

		}
		return ss;
	}

	public void telephone() {

		try {
			TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

			// TelephonyManager manager =
			// (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

			String carrierName = manager.getNetworkOperatorName();

			String deviceID = manager.getDeviceId();
			String deviceID1 = manager.getSimSerialNumber();
			Networkprovider = carrierName;
			Deviceid = deviceID;
			simSerialNo = deviceID1;

		} catch (Exception e) {

		}
	}

	public void batteryLevelUpdate() {
		try {
			BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
				public void onReceive(Context context, Intent intent) {
					context.unregisterReceiver(this);
					int rawlevel = intent.getIntExtra(
							BatteryManager.EXTRA_LEVEL, -1);
					int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,
							-1);
					int level = -1;
					if (rawlevel >= 0 && scale > 0) {
						level = (rawlevel * 100) / scale;
					}
					int voltage = intent.getIntExtra(
							BatteryManager.EXTRA_VOLTAGE, -1);
					int status = intent.getIntExtra(
							BatteryManager.EXTRA_STATUS, -1);
					int onplug = intent.getIntExtra(
							BatteryManager.EXTRA_PLUGGED, -1);
					boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
							|| status == BatteryManager.BATTERY_STATUS_FULL;
					boolean onUSB = onplug == BatteryManager.BATTERY_PLUGGED_USB;
					boolean onAC = onplug == BatteryManager.BATTERY_PLUGGED_AC;
					String strStatus = "Charging on ";
					if (isCharging && onUSB)
						strStatus += "USB";
					else if (isCharging && onAC)
						strStatus += "AC Power";
					else
						strStatus = "Battery Discharging";

					batteryinfo = "Level: " + Integer.toString(level) + "%";

				}
			};
			IntentFilter batteryLevelFilter = new IntentFilter(
					Intent.ACTION_BATTERY_CHANGED);
			registerReceiver(batteryLevelReceiver, batteryLevelFilter);
		} catch (Exception e) {

		}
	}

	public static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			if (files == null) {
				return true;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

}
