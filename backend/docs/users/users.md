**# USERS
## DB:
- USER
	+ ID
	+ Username
	+ Email
	+ Creation date
	+ Change date
	+ Security Data (embedded)
- SECURITY
	+ Security Change date
    + Security Last login date
	+ Password Hash
	+ Salt
## Objects:
- UserRepository
	+ add
	+ get
	+ remove
	+ modify
- AuthService:
	+ bool registerUser(UserRegisterForm)
	+ bool deleteUser(greenGameUser, password)
	+ changePassword(greenGameUser, password)
	+ bool authUser(greenGameUser, password, totp) // gives greenGameUser cookie, callable by API
	+ bool verifyPassword(greenGameUser, password)
	+ User getUserFromSession(greenGameUser, cookie)
