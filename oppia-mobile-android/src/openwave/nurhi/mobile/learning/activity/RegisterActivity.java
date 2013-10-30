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

package openwave.nurhi.mobile.learning.activity;

import java.util.ArrayList;

import openwave.nurhi.mobile.learning.R;
import openwave.nurhi.mobile.learning.application.MobileLearning;
import openwave.nurhi.mobile.learning.listener.SubmitListener;
import openwave.nurhi.mobile.learning.model.User;
import openwave.nurhi.mobile.learning.task.Payload;
import openwave.nurhi.mobile.learning.task.RegisterTask;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.uploaddata.BaseUtill;
import com.example.uploaddata.InternetConnections;
import com.example.uploaddata.WebParser;

public class RegisterActivity extends AppActivity implements SubmitListener {

	public static final String TAG = RegisterActivity.class.getSimpleName();

	private static final int ONCLICK_TASK_NULL = 0;
	private static final int ONCLICK_TASK_REGISTERED = 10;
	public static String Usernamemain = "";
	ArrayList<String> arraystate = new ArrayList<String>();
	private SharedPreferences prefs;

	private ProgressDialog pDialog;

	RadioGroup radiogroup1, radiogroup6, radiogroup7;
	CheckBox checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6,
			checkbox7, checkbox8, checkbox9, checkbox10, checkbox51,
			checkbox52, checkbox53, checkbox54, checkbox55, checkbox56,
			checkbox57, checkbox58, checkbox59, checkbox60;

	private CheckBox checkStaffPhysician, checkStaffMidsife, checkStaffNurse,
			checkStaffNurseMidwife, checkStaffFPCoordinator, checkStaffTrainer,
			checkStaffCommHealthOfficer, checkStaffOther;

	EditText edt_type_of_staff_are_you, edt_years_of_education,
			edt_how_many_times_artend_nurhi_training, edt_age, edt_firstname,
			edt_lastname;

	int pos;
	SQLiteDatabase sampleDB;

	private EditText edtRegisterUserName;
	private EditText edtRegisterPhoneNumber;
	private EditText edtRegisterEmailId;
	private EditText edtRegisterPassword;
	private EditText edtRegisterConfirmPassword;

	AutoCompleteTextView edt_facility_where_you_currently_work;

	private ArrayList<String> listEdtTxtValue;
	private ArrayList<String> listAlertValue;
	Context context;
	private boolean validateEmail = true;
	private boolean validatEmailValue = false;
	private boolean validatePassword = false;
	private boolean validateResult = false;
	ArrayList<String> currentlyworkings = new ArrayList<String>();

	String username = "", firstname = "", lastname = "", email = "",
			phoneno = "", password = "", current_working_city = "",
			currently_working_facility = "", staff_type = "",
			nurhi_sponsor_training = "", current_place_employment = "",
			highest_education_level = "", religion = "", sex = "", age = "",
			passwordagain = "", fpm_use_for_yourself = "";

	private String strEntirePlaces[][] = {
			{ "Karshi General Hospital", "Gwarinpa General Hospital",
					"Asokoro General Hospital", "Elyon Hospital",
					"Ebony Hospital", "Maitama District Hospital",
					"WUSE General Hospital", "Nyanya General Hospital",
					"Family Health Clinic", "Bwari General Hospital",
					"Kubwa General Hospital", "Mpape PHC", "Summit Hospital" },
			{ "Central Hospital", "New Benin PHC", "Oredo PHC",
					"UBTH FP Clinic", "Bamby Hospital",
					"Police Training School Ogida PHC", "Evbuotubu PHC",
					"Ugbekun PHC", "Ogbeson PHC", "Iwogban PHC" },
			{ "University College Hosp", "Adeoyo Teach Mat Hosp", "Sabo PHC",
					"IDI-Ogunugun PHC", "Group Medical Hospital",
					"Jaja Clinic", "Sango PHC", "Bashorun PHC",
					"Ayekale/Oja Igbo PHC", "Oke adu PHC", "Iwo Rd PHC",
					"Bashorun Akeke PHC", "Alafara PHC", "IB Central Hospital",
					"Molly Clinic", "Ayeye PHC", "Oniyanrin PHC",
					"Victory Medical Centre", "Molete Health Centre",
					"Oranyan PHC", "Boluwaji CHC", "Agbongbon Health Centre",
					"Ring Road State Hospital", "The Vine Hospital",
					"Jericho General Hospital", "Oni Memorial Children Hosp",
					"Foko PHC", "MCH Apata", "Model Clinic" },
			{ "Civil Service Hospital GRA Ilorin",
					"Health Centre Tanke - University Road",
					"Kosemani Hospital", "Temitope Hospital",
					"Children's Specialist  Hospital Igboro",
					"Health Cenre Ero-Omo Offa Garage",
					"Comprehensive Health Center(Cottage Hospital)) Ogidi",
					"University of Ilorin Teaching Hospital",
					"Olanrewaju Hospital", "Health Centre Okelele",
					"Cottage Hospital Ajikobi", "Cottage Hospital Adewole",
					"B.H.C Alanamu", "Health Clinic  Magajin Ngeri",
					"SuruLere medical Center", "Basic Health Center Esa",
					"Basic Health Center Esa", "Specialist  Hospital Offa",
					"Wale Clinic & Maternity Offa",
					"Child Welfare Clinic/Olomowewe",
					"Ajisafe Hospital Omo-Aran", "General Hospital Omu-Aran" },
			{ "PHC Badarawa", "PHC Ungwan Shanu",
					"Zakari Isa Memorial Hospital",
					"Barau Dikko Specialist Hospital", "General Hospital Kawo",
					"M.C.H. Nasarawa", "PHC Sabon Tasha",
					"M.C.H. Mararaban Rido", "PHC T/Wada Kujama",
					"Rural Hospital Kujama", "Biba Hospital",
					"Dr Gwamna Awan General Hospital",
					"Family Health Hospital Tudun Wada",
					"44 Military Hospital",
					"Yusuf Dantsoho Memorial Hospital Tudun Wada",
					"Maneks Hospital Makera", "PHC Television",
					"PHC Makera II",
					"Shehu Kangiwa Clinic, Kaduna Polytechnic", "PHC Kagoro",
					"PHC Kabala West", "PHC Barnawa", "PHC Ungwan Muazu" },
			{ "PHC T/Wada", "Zaria Clinic", "General Hospital, G/Sawaba",
					"PHC Baban dodo", "PHC Samaru", "PHC Kwata", "PHC Muchia",
					"PHC Sabon Gari", "General Hospital, Giwa", "PHC Gangara",
					"PHC Hunkuyi", "PHC Kudan", "General  Hospital Hunkuyi" } };

	private ArrayAdapter<String> adapterPlace;
	Button btnRegisterSubmit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		this.drawHeader();

		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		edtRegisterConfirmPassword = (EditText) findViewById(R.id.edt_register_confirm_password);
		edtRegisterEmailId = (EditText) findViewById(R.id.edt_register_email_id);
		edtRegisterPassword = (EditText) findViewById(R.id.edt_register_password);
		edtRegisterPhoneNumber = (EditText) findViewById(R.id.edt_register_phone_number);
		edtRegisterUserName = (EditText) findViewById(R.id.edt_register_user_name);
		edt_firstname = (EditText) findViewById(R.id.edt_firstname);

		edt_lastname = (EditText) findViewById(R.id.edt_lastname);

		currentlyworkings = new ArrayList<String>();
		currentlyworkings.add("Karshi General Hospital");
		currentlyworkings.add("Bwari General Hospital");
		currentlyworkings.add("Gwarinpa General Hospital");
		currentlyworkings.add("Asokoro General Hospital");
		currentlyworkings.add("Maitama Distric Hospital");
		currentlyworkings.add("WUSE General Hospital");
		currentlyworkings.add("Kubwa General Hospital");
		currentlyworkings.add("Nyanya General Hospital");
		currentlyworkings.add("Family Health Clinic");
		currentlyworkings.add("Mpape PHC");
		currentlyworkings.add("University College Hospital");
		currentlyworkings.add("Adeoyo Teach Mat Hospital");
		currentlyworkings.add("Sabo PHC");
		currentlyworkings.add("IDI-Ogunugun");
		currentlyworkings.add("Group Medical");
		currentlyworkings.add("Bashorun Phc");
		currentlyworkings.add("Sango Phc");
		currentlyworkings.add("Ayekale /Oja Lgobo");

		arraystate = new ArrayList<String>();
		arraystate.add("Plateau");
		arraystate.add("Delta");
		arraystate.add("Ekiti");
		arraystate.add("Borno");
		arraystate.add("FCT");
		arraystate.add("Osun");
		arraystate.add("Nasarawa");
		arraystate.add("Katsina");
		arraystate.add("Kano");
		arraystate.add("Ebonyi");
		arraystate.add("Adamawa");
		arraystate.add("zamfara");
		arraystate.add("Akwa lbom");
		arraystate.add("Taraba");
		arraystate.add("Rivers");
		arraystate.add("Enugu");
		arraystate.add("Oyo");
		arraystate.add("Lagos");
		arraystate.add("Kogi");

		listEdtTxtValue = new ArrayList<String>();
		listAlertValue = new ArrayList<String>();

		btnRegisterSubmit = (Button) findViewById(R.id.btnsubmit);

		context = RegisterActivity.this;

		checkStaffPhysician = (CheckBox) findViewById(R.id.checkStaffType1);
		checkStaffMidsife = (CheckBox) findViewById(R.id.checkStaffType2);
		checkStaffNurse = (CheckBox) findViewById(R.id.checkStaffType3);
		checkStaffNurseMidwife = (CheckBox) findViewById(R.id.checkStaffType4);
		checkStaffFPCoordinator = (CheckBox) findViewById(R.id.checkStaffType5);
		checkStaffTrainer = (CheckBox) findViewById(R.id.checkStaffType6);
		checkStaffCommHealthOfficer = (CheckBox) findViewById(R.id.checkStaffType7);
		checkStaffOther = (CheckBox) findViewById(R.id.checkStaffType8);
		// checkStaffPhysician = (CheckBox)findViewById(R.id.checkStaffType9);

		checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
		checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
		checkbox3 = (CheckBox) findViewById(R.id.checkbox3);
		checkbox4 = (CheckBox) findViewById(R.id.checkbox4);
		checkbox5 = (CheckBox) findViewById(R.id.checkbox5);
		checkbox6 = (CheckBox) findViewById(R.id.checkbox6);
		checkbox7 = (CheckBox) findViewById(R.id.checkbox7);
		checkbox8 = (CheckBox) findViewById(R.id.checkbox8);
		checkbox9 = (CheckBox) findViewById(R.id.checkbox9);
		checkbox10 = (CheckBox) findViewById(R.id.checkbox10);

		checkbox51 = (CheckBox) findViewById(R.id.checkbox51);
		checkbox52 = (CheckBox) findViewById(R.id.checkbox52);
		checkbox53 = (CheckBox) findViewById(R.id.checkbox53);
		checkbox54 = (CheckBox) findViewById(R.id.checkbox54);
		checkbox55 = (CheckBox) findViewById(R.id.checkbox55);
		checkbox56 = (CheckBox) findViewById(R.id.checkbox56);
		checkbox57 = (CheckBox) findViewById(R.id.checkbox57);
		checkbox58 = (CheckBox) findViewById(R.id.checkbox58);
		checkbox59 = (CheckBox) findViewById(R.id.checkbox59);
		checkbox60 = (CheckBox) findViewById(R.id.checkbox60);

		radiogroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		radiogroup6 = (RadioGroup) findViewById(R.id.radioGroup6);
		radiogroup7 = (RadioGroup) findViewById(R.id.radioGroup7);

		edt_facility_where_you_currently_work = (AutoCompleteTextView) findViewById(R.id.edt_facility_where_you_currently_work);

		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_dropdown_item_1line, currentlyworkings);
		// edt_facility_where_you_currently_work.setAdapter(adapter);

		ArrayAdapter<String> adapters1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, arraystate);

		edt_type_of_staff_are_you = (EditText) findViewById(R.id.edt_type_of_staff_are_you);
		edt_how_many_times_artend_nurhi_training = (EditText) findViewById(R.id.edt_how_many_times_artend_nurhi_training);
		edt_age = (EditText) findViewById(R.id.edt_age);

		edt_years_of_education = (EditText) findViewById(R.id.edt_years_of_education);

		/* Register Activity Start */

		InputFilter alphaNumericFilter = new InputFilter() {
			@Override
			public CharSequence filter(CharSequence arg0, int arg1, int arg2,
					Spanned arg3, int arg4, int arg5) {
				for (int k = arg1; k < arg2; k++) {
					if (!Character.isLetterOrDigit(arg0.charAt(k))
							|| Character.isSpace(arg0.charAt(k))) {
						return "";
					}
				}
				return null;
			}
		};
		InputFilter number = new InputFilter() {
			@Override
			public CharSequence filter(CharSequence arg0, int arg1, int arg2,
					Spanned arg3, int arg4, int arg5) {
				for (int k = arg1; k < arg2; k++) {
					if (!Character.isDigit(arg0.charAt(k))) {
						return "";
					}
				}
				return null;
			}
		};

		InputFilter alphaemail = new InputFilter() {
			@Override
			public CharSequence filter(CharSequence arg0, int arg1, int arg2,
					Spanned arg3, int arg4, int arg5) {
				for (int k = arg1; k < arg2; k++) {
					if (!Character.isLetterOrDigit(arg0.charAt(k))) {
						return "";
					}
				}
				return null;
			}
		};

		listEdtTxtValue = new ArrayList<String>();
		listAlertValue = new ArrayList<String>();

		radiogroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				int id = radiogroup1.getCheckedRadioButtonId();
				RadioButton b = (RadioButton) findViewById(id);
				String arrStrValues[];
				int position = Integer.parseInt(b.getTag().toString());
				arrStrValues = strEntirePlaces[position];

				current_working_city = (String) b.getText();
				System.out.println(" What city do you currently work in ******"
						+ b.getText());
				adapterPlace = new ArrayAdapter<String>(RegisterActivity.this,
						android.R.layout.simple_expandable_list_item_1,
						arrStrValues);
				edt_facility_where_you_currently_work.setAdapter(adapterPlace);
			}
		});

		// edt_facility_where_you_currently_work.setOnItemClickListener(new
		// OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long rowId) {
		//
		//
		//
		// Log.e("", "position "+position);
		// //here position is your selected item id
		//
		// /*String selecteditem =name_product.get(position);
		// Log.d("Test",selecteditem.toString());
		// int pos = Integer.parseInt(selecteditem);
		// Toast.makeText(getApplicationContext(), "cvxcvcv",pos).show();*/
		//
		// // I want to get position when i click element in
		// autocompletetextView
		// }
		// });

		radiogroup6.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = radiogroup6.getCheckedRadioButtonId();
				RadioButton b = (RadioButton) findViewById(id);
				religion = (String) b.getText();
				System.out.println("***" + b.getText());
			}
		});

		radiogroup7.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = radiogroup7.getCheckedRadioButtonId();
				RadioButton b = (RadioButton) findViewById(id);
				sex = (String) b.getText();
				System.out.println("***" + b.getText());
			}
		});
	}

	public void showToast(String str) {
		Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
	}

	public void submitComplete(Payload response) {
		pDialog.dismiss();
		Log.d(TAG, "Login activity reports: " + response.getResultResponse());
		if (response.isResult()) {
			User u = (User) response.getData().get(0);
			// set params
			Editor editor = prefs.edit();
			editor.putString(getString(R.string.prefs_username),
					u.getusername()); // edt_your_professiona_training.getText().toString()
										// chnage
			editor.putString(getString(R.string.prefs_api_key), u.getApi_key());
			editor.putString(getString(R.string.prefs_display_name),
					u.getDisplayName());
			editor.putInt(getString(R.string.prefs_points), u.getPoints());
			editor.putInt(getString(R.string.prefs_points), u.getBadges());
			editor.commit();

			showAlert("Register", "Registration successful",
					ONCLICK_TASK_REGISTERED);
		} else {
			showAlert("Register", response.getResultResponse(),
					ONCLICK_TASK_NULL);
		}

	}

	public void onRegisterClick(View view) {
		// get form fields

		Log.d("Entered ", "into Submit -------------------------");

		username = edtRegisterUserName.getText().toString().trim();
		firstname = edt_firstname.getText().toString().trim();
		lastname = edt_lastname.getText().toString().trim();
		email = edtRegisterEmailId.getText().toString().trim();
		phoneno = edtRegisterPhoneNumber.getText().toString().trim();
		password = edtRegisterPassword.getText().toString().trim();
		passwordagain = edtRegisterConfirmPassword.getText().toString().trim();

		// listEdtTxtValue.clear();

		//
		// listEdtTxtValue.add(firstname);
		// listAlertValue.add("First Name");
		//
		// listEdtTxtValue.add(lastname);
		// listAlertValue.add("Last Name");
		//
		// listEdtTxtValue.add(username);
		// listAlertValue.add("User Name");
		//
		// listEdtTxtValue.add(phoneno);
		// listAlertValue.add("Phone Number");
		//
		// listEdtTxtValue.add(email);
		// listAlertValue.add("Email Id");
		//
		// listEdtTxtValue.add(password);
		// listAlertValue.add("Password");
		//
		// listEdtTxtValue.add(passwordagain);
		// listAlertValue.add("Confirm Password");
		//
		 
//
//			validatEmailValue = android.util.Patterns.EMAIL_ADDRESS.matcher(
//					email).matches();
//			 validatEmailValue = true;
	 
		//
		//
		//
		// if (validatePassword == true) {
		//
		//
		//
		// if (password.equals(passwordagain)) {
		// validateResult = true;
		// } else
		// Toast.makeText(getApplicationContext(),
		// "Password and Confirm Password are mismatch",
		// Toast.LENGTH_LONG).show();
		// }
		validateResult = true;
		current_place_employment = "";

		if (checkbox1.isChecked())
			current_place_employment = current_place_employment + " "
					+ checkbox1.getText().toString();
		if (checkbox2.isChecked())
			current_place_employment = current_place_employment + " "
					+ checkbox2.getText().toString();
		if (checkbox3.isChecked())
			current_place_employment = current_place_employment + " "
					+ checkbox3.getText().toString();
		if (checkbox4.isChecked())
			current_place_employment = current_place_employment + " "
					+ checkbox4.getText().toString();
		if (checkbox5.isChecked())
			current_place_employment = current_place_employment + " "
					+ checkbox5.getText().toString();
		if (checkbox6.isChecked())
			current_place_employment = current_place_employment + " "
					+ checkbox6.getText().toString();
		if (checkbox7.isChecked())
			current_place_employment = current_place_employment + " "
					+ checkbox7.getText().toString();
		if (checkbox8.isChecked())
			current_place_employment = current_place_employment + " "
					+ checkbox8.getText().toString();
		if (checkbox9.isChecked())
			current_place_employment = current_place_employment + " "
					+ checkbox9.getText().toString();
		if (checkbox10.isChecked())

			current_place_employment = current_place_employment + " "
					+ checkbox10.getText().toString();

		fpm_use_for_yourself = "";

		if (checkbox51.isChecked())
			fpm_use_for_yourself = fpm_use_for_yourself + " "
					+ checkbox51.getText().toString();
		if (checkbox52.isChecked())
			fpm_use_for_yourself = fpm_use_for_yourself + " "
					+ checkbox52.getText().toString();
		if (checkbox53.isChecked())
			fpm_use_for_yourself = fpm_use_for_yourself + " "
					+ checkbox53.getText().toString();
		if (checkbox54.isChecked())
			fpm_use_for_yourself = fpm_use_for_yourself + " "
					+ checkbox54.getText().toString();
		if (checkbox55.isChecked())
			fpm_use_for_yourself = fpm_use_for_yourself + " "
					+ checkbox55.getText().toString();
		if (checkbox56.isChecked())
			fpm_use_for_yourself = fpm_use_for_yourself + " "
					+ checkbox56.getText().toString();
		if (checkbox57.isChecked())
			fpm_use_for_yourself = fpm_use_for_yourself + " "
					+ checkbox57.getText().toString();
		if (checkbox58.isChecked())
			fpm_use_for_yourself = fpm_use_for_yourself + " "
					+ checkbox58.getText().toString();
		if (checkbox59.isChecked())
			fpm_use_for_yourself = fpm_use_for_yourself + " "
					+ checkbox59.getText().toString();
		if (checkbox60.isChecked())

			fpm_use_for_yourself = fpm_use_for_yourself + " "
					+ checkbox60.getText().toString();

		staff_type = "";

		if (checkStaffPhysician.isChecked()) {
			staff_type = staff_type + " "
					+ checkStaffPhysician.getText().toString();
		}
		if (checkStaffMidsife.isChecked()) {
			staff_type = staff_type + " "
					+ checkStaffMidsife.getText().toString();
		}
		if (checkStaffNurse.isChecked()) {
			staff_type = staff_type + " "
					+ checkStaffNurse.getText().toString();
		}
		if (checkStaffNurseMidwife.isChecked()) {
			staff_type = staff_type + " "
					+ checkStaffNurseMidwife.getText().toString();
		}
		if (checkStaffFPCoordinator.isChecked()) {
			staff_type = staff_type + " "
					+ checkStaffFPCoordinator.getText().toString();
		}
		if (checkStaffTrainer.isChecked()) {
			staff_type = staff_type + " "
					+ checkStaffTrainer.getText().toString();
		}
		if (checkStaffCommHealthOfficer.isChecked()) {
			staff_type = staff_type + " "
					+ checkStaffCommHealthOfficer.getText().toString();
		}
		if (checkStaffOther.isChecked()) {
			staff_type = staff_type + " "
					+ checkStaffOther.getText().toString() + " "
					+ edt_type_of_staff_are_you.getText().toString();
		}

		Log.e("", "staff_type " + staff_type);

		if (validateResult == true) {

			currently_working_facility = edt_facility_where_you_currently_work
					.getText().toString();

			highest_education_level = edt_years_of_education.getText()
					.toString();

			highest_education_level = " " + highest_education_level;

			nurhi_sponsor_training = edt_how_many_times_artend_nurhi_training
					.getText().toString();

			nurhi_sponsor_training = " " + nurhi_sponsor_training;

			age = edt_age.getText().toString();

			Log.d("Test Log** ", "First Name " + firstname);
			Log.d("Test Log** ", "Last Name " + lastname);
			Log.d("Test Log** ", "username " + username);
			Log.d("Test Log** ", "email " + email);
			Log.d("Test Log** ", "phoneno " + phoneno);
			Log.d("Test Log** ", "password " + password);
			Log.d("Test Log** ", "passwordagain " + passwordagain);
			Log.d("Test Log**", "current_working_city " + current_working_city);
			Log.d("Test Log**", "fpm_use_for_yourself " + fpm_use_for_yourself);
			Log.d("Test Log**", "highest_education_level "
					+ highest_education_level);
			Log.d("Test Log**", "currently_working_facility "
					+ currently_working_facility);

			Log.d("Test Log**", "strTypeOfStaff " + staff_type);

			Log.d("Test Log**", "nurhi_sponsor_training "
					+ nurhi_sponsor_training);
			Log.d("Test Log**", "current_place_employment "
					+ current_place_employment);
			Log.d("Test Log**", "highest_education_level "
					+ highest_education_level);
			Log.d("Test Log**", "religion " + religion);
			Log.d("Test Log**", "sex " + sex);
			Log.d("Test Log**", "age " + age);

//			else if (email.length() == 0) {
//				showToast("Email ID should not be empty");
//			} else if (email.length() < 6) {
//				showToast("Please enter your Email ID");
//			} else if (validatEmailValue == true) {
//				showToast("Please Enter Valid Email ID");
//			} 
			
			
			if (firstname.length() == 0) {
				showToast("First Name should not be empty");
			} else if (firstname.length() < 3) {
				showToast("First Name atleast 3 character");
			} else if (lastname.length() == 0) {
				showToast("Last Name should not be empty");
			} else if (lastname.length() < 3) {
				showToast("Last Name atleast 3 character");
			} else if (username.length() == 0) {
				showToast("User Name should not be empty");
			} else if (username.length() < 6) {
				showToast("User Name atleast 6 character");
			} else if (phoneno.length() == 0) {
				showToast("Phone No should not be empty");
			} else if (phoneno.length() < 6) {
				showToast("Phone No atleast 6 character");
			}else if (email.length() == 0) {
				showToast("Email ID should not be empty");
			} else if (email.length() < 6) {
				showToast("Please enter your Email ID");
			} else if (password.length() == 0) {
				showToast("Password should not be empty");
			} else if (password.length() < 6) {
				showToast("Password must be atleast 6 character");
			} else if (passwordagain.length() == 0) {
				showToast("Confirm Password should not be empty");
			} else if (passwordagain.length() < 6) {
				showToast("Confirm Password atleast 6 character");
			} else if (current_working_city.equalsIgnoreCase("")) {
				showToast("Please select currently working City");
			} else if (currently_working_facility.equalsIgnoreCase("")) {
				showToast("Please select facility currently working");
			}  else if (staff_type.equalsIgnoreCase("")) {
				showToast("Please select what type of staff are you?");
			} 			
			else if (nurhi_sponsor_training.equalsIgnoreCase(" ")) {
				showToast("Please enter training attended in NURHI");
			} else if (current_place_employment.equalsIgnoreCase("")) {
				showToast("Please select family planning method");
			} else if (fpm_use_for_yourself.equalsIgnoreCase("")) {
				showToast("Please select family planning method  used yourself");
			} else if (highest_education_level.equalsIgnoreCase(" ")) {
				showToast("Please enter years of education you received");
			} else if (religion.equalsIgnoreCase("")) {
				showToast("Please select your religion");
			}

			else if (sex.equalsIgnoreCase("")) {
				showToast("Please select your gender");
			} else if (age.equalsIgnoreCase("")) {
				showToast("Please enter your age");
			} else {

				Usernamemain = username;
				pDialog = new ProgressDialog(this);
				pDialog.setTitle("Register");
				pDialog.setMessage("Registering...");
				pDialog.setCancelable(true);
				pDialog.show();

				ArrayList<Object> users = new ArrayList<Object>();
				User u = new User();
				u.setusername(username);
				u.setfirstname(firstname);
				u.setlastname(lastname);
				u.setemail(email);
				u.setphone(phoneno);
				u.setpassword(password);
				u.setpasswordagain(passwordagain);

				u.setcurrent_working_city(current_working_city);
				u.setcurrently_working_facility(currently_working_facility);
				u.setstaff_type(staff_type);
				u.setnurhi_sponsor_training(nurhi_sponsor_training);
				u.setcurrent_place_employment(current_place_employment);

				u.setfpm_use_for_yourself(fpm_use_for_yourself);
				u.sethighest_education_level(highest_education_level);
				u.setreligion(religion);
				u.setsex(sex);
				u.setAge(age);

				try {

					new WriteFile(RegisterActivity.this)
							.CreateFile("User Registraion Process started");
					new WriteFile(RegisterActivity.this)
							.CreateFile("\nUsername:" + username
									+ "\nPassword:" + password + "\nFirstname:"
									+ firstname + "\nLastname:" + lastname
									+ "\nEmail:" + email + "\ntrainning2:"
									+ currently_working_facility + "\nPhone:"
									+ phoneno + "\ncity:"
									+ current_working_city + "\nWorktype"

									+ "\nstafftype" + staff_type
									+ "\nfamilyplaning:"
									+ current_place_employment
									+ "\nnurhitrainning:"
									+ nurhi_sponsor_training + "\neducation:"
									+ highest_education_level + "\nreligion:"
									+ religion + "\nsex:" + sex
									+ "\ntrainning2"

									+ "\nAge" + age);

					new WriteFile(RegisterActivity.this)
							.CreateFile("User Registraion Process end");

				} catch (Exception e) {
					e.printStackTrace();
				}
				users.add(u);
				Payload p = new Payload(users);
				RegisterTask lt = new RegisterTask(this);
				lt.setLoginListener(this);
				lt.execute(p);

			}

		}
	}

	private void showAlert(String title, String msg, int onClickTask) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				RegisterActivity.this);
		builder.setTitle(title);
		// String mess = msg.replace("\n\"}", "");
		// mess = mess.replace("{\"error\":\"", "");

		builder.setMessage(msg);
		switch (onClickTask) {
		case ONCLICK_TASK_NULL:
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// do nothing

						}

					});
			break;
		case ONCLICK_TASK_REGISTERED:
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// return to main activity
							RegisterActivity.this.finish();
						}

					});
			break;
		}
		builder.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent i = new Intent(this, PrefsActivity.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public class addusertask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			WebParser web = new WebParser();

			BaseUtill bs = new BaseUtill();
			String base64 = bs.get_Convert_Base64(MobileLearning.adduserbase64
					+ params[0] + MobileLearning.adduserbase641 + params[1]
					+ MobileLearning.adduserbase642 + params[2]
					+ MobileLearning.adduserbase643 + params[3]
					+ MobileLearning.adduserbase644 + params[4]
					+ MobileLearning.adduserbase645 + params[5]
					+ MobileLearning.adduserbase646 + params[6]
					+ MobileLearning.adduserbase647 + params[7]
					+ MobileLearning.adduserbase648 + params[8]
					+ MobileLearning.adduserbase649);
			String md5 = bs.get_Convert_MD5key(MobileLearning.adduserbase64
					+ params[0] + MobileLearning.adduserbase641 + params[1]
					+ MobileLearning.adduserbase642 + params[2]
					+ MobileLearning.adduserbase643 + params[3]
					+ MobileLearning.adduserbase644 + params[4]
					+ MobileLearning.adduserbase645 + params[5]
					+ MobileLearning.adduserbase646 + params[6]
					+ MobileLearning.adduserbase647 + params[7]
					+ MobileLearning.adduserbase648 + params[8]
					+ MobileLearning.adduserbase649
					+ MobileLearning.md5passcode);

			if ((InternetConnections.getInstance(RegisterActivity.this).isInternetAlive) == true) {

				try {

					web.get_Department_Detail(MobileLearning.appurl, base64,
							md5);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}

	}
}
