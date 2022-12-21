# Exceptions & Bugs Christopher

## User Controller
    - /users/register
        - Input SSN too long
            - 400 : Bad Request
        - Input SSN 0 
            - Created Successfully
        - Input State too long
            - 500 : Internal Server Error

    - /users/get/{id}
        - Get User
            - Sensitive Information Needs to be Null
    