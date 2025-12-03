Feature: RMA Request positive flow 


@browser=chrome
Scenario: User Creates a ticket sucessfully 
Given user is on the login page 
Then user enters valid credentials and logs in 
Then user validates the dashboard url
And navigate to the RMA tool 
Then user validates the rma tool dashboard url
And navigates to customer menu for configurational changes 
Then validates the url 
And search for 'rewe' customer
And now validates the single user
Then configure based on the requirement
Then now navigate to the new Request
And Fill in the required fields
And Find the the new ticket 
Then navigate to Inspection phase 
And fill in the corresponding tags and respective defects
