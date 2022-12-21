Functional Requirements 

    User should be able to navigate to different parts of website based on customer role 

    As a customer, when I login, I should be able to see my accounts home page, so that I can check my account information  

    As a guest, I should not be able to see any user accounts, so that I cannot see a customer’s sensitive account information 

    As a bank loan manager, when I login, I should be able to see mortgage applications, so that I can approve or deny them 

    User should be able to access and use login page 

    Upon opening the app, user should be prompted to log in 

    As a user, when I open the banking website, I should be prompted to log in, so that I can access account information 

    Any attempt to access anything within the domain while not logged in should redirect to this page 

    When logging in, improper inputs will be denied 

    As a user, when I attempt to login and I make an error, I should be alerted that my username/password attempt was incorrect, so that I can try again 

    Invalid attempts should not succeed in logging into an account 

    Username and password must belong to the same account 

    The maximum input for usernames and passwords should be 100 characters (avoid overloading) 

    Invalid attempts will return an error message 

    As a user, when I attempt to login multiple times, I should be limited to 3 attempts before I’m denied entrance to the account, so that my accounts remain secure 

    After 3 unsuccessful attempts, account is locked and message to contact bank is displayed 

    When succeeding in logging in, should be brought to accounts homepage 

    As a user, when I successfully log in, I should be presented with my accounts home screen, so that I can see my account information. 

    Successful login brings user directly into an accounts home screen 

    After being logged in, it should be possible to log out account 

    As a user, when I enter the home screen, there should be an option to log out of my account so that I can secure my account. 

    Logout feature should lock the features behind the login page 

    User should be able to register online 

    When the website is open, it should show a registration option 

    As a user, when I open the banking website, it should show an option to register on the website so that I create a user account for an existing bank account. 

    Will appear with the login option when website is opened 

    Selecting the registration option should take user to a registration screen 

    As a user, when I select to register as a user, I should be taken to a form to input my information and login credentials, so that I can become a bank customer. 

    My login credentials should be a unique email and password 

    Should be redirected to login if email already exists in database 

    User should be able to access multiple accounts 

    Accounts home screen should display all accounts tied to the user 

    As a customer, when I am logged in I should see all my accounts, so that I can see the account information 

    The correct accounts are displayed 

    Accounts display account type, nickname (if applicable,) account number (masked,) routing number, and balance 

    User should be able to verify balance 

    When in accounts, there should be balance information 

    As a customer, when I am looking at my account(s), I should be able to see my balance(s), so that I can verify the correct amount(s) 

    Most current balance is displayed in the account field 

    User should be able to transfer their funds between their accounts 

    (Potential for expansion: transfer between different banks) 

    As a customer, I should be able to transfer funds from one of my accounts to another one of my accounts, so that I can adjust the balance in them as necessary 

    Debits from origin account should be reflected in destination account 

    User should be able to apply for mortgage 

    User should have loan option presented when website opened 

    As a user, I should be able to see a link or button to open a mortgage application form, so that I can apply for a home loan 

    Will be present on login screen as an alternative option 

    As a user, I should be able to fill out the loan forms through the website, so that I can apply for a home loan. 

    All necessary fields will have to be entered 

    Include a submission button 

    As a user, I should be able to submit my form for review, so that the bank can review and make a decision on the mortgage 

    The inputted data should populate the database with the application information 

    Mortgage id present so that user can check if loan is approved or denied 

    As a user, I should receive a mortgage id after I submit my mortgage application, so that I can I can check the status of my application later 

    Bank should be able to approve or deny mortgage application 

    Mortgage application status should be able to be updated 

    As the banking loan manager, I should be able to update the mortgage loan application, so that the customer can see the bank’s decision 

    Only the banking loan manager should be able to update the status 

    The status should be updated to either approved or denied 

    The status should not go back to default 

 

 

Non-Functional Requirements 

    Security 

    All PII [Personal Identifiable Information] is Masked. 

    As a user I want my personal information to be safely secure so that I do not experience fraud. 

    Verify that the PII is Masked 

    Verify that if there is inactivity on the application, a timeout sequence will initiate and forcibly log the user out automatically. 

    Compatibility  

    Able to access application on different systems I.e., desktop, or phone. 

    As a user I want to access my bank account from my different registered devices so that I can manage my account wherever I am. 

    Verify log in on a computer/laptop. 

    Verify log in on a phone. 

    Verify that the design is responsive to any screen size 

    Performance 

    Fund Transfers should happen instantaneously.  

    As a user I want the bank to approve and transfer funds between accounts within a minute of submitting a transfer request so that I can use my funds in a timely manner. 

    Verify that transfer is done within no more than 5 mins after the transfer request is submitted.  

    Functional Requirements Performance should be quick. 

    As a user I want the banking application to have minimal lag time when using application features so that I can access my money efficiently. 

    Verify that functional features are executed within 1 second.  

    Maintainability 

    Updates to the application should happen quickly. 

    As a user I want the bank application to take a maximum of 1hr time to update so that I do not have to wait long periods in order to access my account. 

    Verify that future updates notify customer beforehand.  

    Verify that the building statistics are completed within 1hr. 

    Usability 

    Visually Clarifying 

    As a user I want the application to be easy on my eyes so that I do not get frustrated or confused. 

    Verify that Features are spread apart and readable. 

    Verify that the Application language is in English  

    Navigation 

    As a user I want the program to be simple and concise so that I do not make unknown mistakes, or get lost. 

    Verify that redirected links are correct. 