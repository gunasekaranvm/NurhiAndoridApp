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

package openwave.nurhi.mobile.learning.model;

public class User {

	private String username;
	private String email;
	private String password;
	private String phone;
	private String passwordAgain;
	private String firstname;
	private String lastname;
	private String city;
	private String api_key;
	private String professional;
	private String current_working_city;
	private String currently_working_facility;
	private String staff_type;
	private String nurhi_sponsor_training;
	private String current_place_employment;
	private String highest_education_level;
	private String religion;
	private String sex;
	private String age;
	private String fpm_use_for_yourself;

	private int points = 0;
	private int badges = 0;

	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	public String getfirstname() {
		return firstname;
	}

	public void setfirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getlastname() {
		return lastname;
	}

	public void setlastname(String lastname) {
		this.lastname = lastname;
	}

	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
	}

	public void setphone(String phone) {
		this.phone = phone;
	}

	public String getphone() {
		return phone;
	}

	public String getpassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	public String getpasswordagain() {
		return passwordAgain;
	}

	public void setpasswordagain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}

	/***/
	public String getcurrent_working_city() {
		return current_working_city;
	}

	public void setcurrent_working_city(String current_working_city) {
		this.current_working_city = current_working_city;
	}

	public String getcurrently_working_facility() {
		return currently_working_facility;
	}

	public void setcurrently_working_facility(String currently_working_facility) {
		this.currently_working_facility = currently_working_facility;
	}

	public String getstaff_type() {
		return staff_type;
	}

	public void setstaff_type(String staff_type) {
		this.staff_type = staff_type;

	}

	public String getnurhi_sponsor_training() {
		return nurhi_sponsor_training;
	}

	public void setnurhi_sponsor_training(String nurhi_sponsor_training) {
		this.nurhi_sponsor_training = nurhi_sponsor_training;

	}

	public String getcurrent_place_employment() {
		return current_place_employment;
	}

	public void setcurrent_place_employment(String current_place_employment) {
		this.current_place_employment = current_place_employment;
	}
	
	
	public String getfpm_use_for_yourself() {
		return fpm_use_for_yourself;
	}

	public void setfpm_use_for_yourself(String fpm_use_for_yourself) {
		this.fpm_use_for_yourself = fpm_use_for_yourself;
	}
	
	
	

	public String gethighest_education_level() {
		return highest_education_level;
	}

	public void sethighest_education_level(String highest_education_level) {
		this.highest_education_level = highest_education_level;
	}

	public String getreligion() {
		return religion;
	}

	public void setreligion(String religion) {
		this.religion = religion;
	}

	public String getSex() {
		return sex;
	}

	public void setsex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getApi_key() {
		return api_key;
	}

	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}

	public String getDisplayName() {
		return firstname + " " + lastname;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getBadges() {
		return badges;
	}

	public void setBadges(int badges) {
		this.badges = badges;
	}

}
