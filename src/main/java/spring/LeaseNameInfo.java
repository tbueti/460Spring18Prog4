//==========================================================================================================================//
//                                                                               LEASE_NAME_INFO                                                                                   //
//==========================================================================================================================//
//==========================================================================================================================//
// Author: Jon Ingram                                                                                                                                                       //
// NetID: jpingram                                                                                                                                                                 //
// File: LeaseNameInfo.java                                                                                                                                                 //
// Date: 5/01/2018                                                                                                                                                               //
// Description: Holds the resulting tuple values for query 3                                                                                                //
//==========================================================================================================================//

package spring;
import java.util.Date;
import java.text.SimpleDateFormat;

// Lease Name Info Class
public class LeaseNameInfo {

   // Class members
   public String firstName;
   public String lastName;
   public int hallId;
   public int roomNo;
   public int rate;
   public Date dueDate;

   // Constructor
   //first_name, last_name, res_apt_id, room_no, rate, due_date
   public LeaseNameInfo(String fn, String ln, int hid, int rno, int r, int dd) {

      try {
         this.firstName = fn;
         this.lastName = ln;
         this.hallId = hid;
         this.roomNo = rno;
         this.rate = r;
         SimpleDateFormat dd = new SimpleDateFormat("dd/MM/yyyy");
         this.dueDate = dd.parse(sd);
      } catch (Exception e) {

      }
   }
}
