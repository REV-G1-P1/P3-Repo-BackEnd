# Exceptions & Bugs Carlos

## User
### Register
On failed User registration the accounts (checking and savings), still get created.



## Account Information
### Update Name
Sending numbers in the accountName value (without " ") still works
### Update Balance
Just like above in Update Name, sending only numbers (without " ") still works, maybe it doesnt have to be strings to then parse



## Mortgage Application


## Log-In/Log-Out
### Log In
Repeated Log-ins return the same message (cookie) even when logging in with different accounts
### Log Out
Log out always returns Ok status code even when the message (cookie) is not correct and nothing is actually logged out


