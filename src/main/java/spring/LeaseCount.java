//==========================================================================================================================//
//																											LEASE_COUNT  																												//
//==========================================================================================================================//
//==========================================================================================================================//
// Author: Thomas Bueti																																																			//
// NetID: tbueti																																																						//
// File: LeaseCount.java																																																	  //
// Date: 4/30/2018																																																					//
// Description: Holds the resulting tuple counts for query 2																																//
//==========================================================================================================================//
package spring;

// Lease Count Class
public class LeaseCount {

  // Class members
  public String category;
  public int resHall;
  public int furnApt;

  // Constructor
  public LeaseCount(String cat, int res, int apt) {

    // Initialize
    this.category = cat;
    this.resHall = res;
    this.furnApt = apt;
  }
}
