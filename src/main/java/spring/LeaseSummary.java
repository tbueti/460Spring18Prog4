//==========================================================================================================================//
//																											LEASE_SUMMARY																												//
//==========================================================================================================================//
//==========================================================================================================================//
// Author: Thomas Bueti																																																			//
// NetID: tbueti																																																						//
// File: LeaseSummary.java																																																	//
// Date: 4/30/2018																																																					//
// Description: Holds the resulting tuple values for query 2																																//
//==========================================================================================================================//

package spring;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

// Lease Summary Class
public class LeaseSummary {

	// Constants
	public static final int NUM_SEM_DAYS = 105;

	// Class members
	public Date startDate;
	public int duration;

	// Constructor
	public LeaseSummary(String sd, String dur) {

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.startDate = sdf.parse(sd);
			this.duration = Integer.parseInt(dur);
		} catch (Exception e) {

		}
	}

	// Returns true if the lease is currently active
	// and false if the lease is not active. Does a
	// simple date check/comparison between the startDate
	// plus the number of days of a semester times the
	// duration and today's date.
	public boolean isActive() {

		// Get the current date
		Date today = new Date();

		// Get a Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.startDate);

		// Go forward in time by the number of semesters
		cal.add(Calendar.DATE, NUM_SEM_DAYS * this.duration);

		// Now let's get the future date
		Date leaseEnd = cal.getTime();

		// If we are within the lease
		if (today.compareTo(leaseEnd) < 0) {

			return true;
		}

		// The lease is up
		return false;

	}


}
