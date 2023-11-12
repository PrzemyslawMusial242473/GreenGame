# USERS
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
	+ Password Hash
	+ Salt
	+ TOTP Secret
## Objects:
- UserRepository
	+ add
	+ get
	+ remove
	+ modify
- AuthService:
	+ bool registerUser(username, email, password)
	+ bool deleteUser(user, password)
	+ changePassword(user, password)
	+ totpKey activateTotp(user)
	+ void deactivateTotp(user)
	+ bool hasTotp(user)
	+ bool authUser(user, password, totp) // gives user cookie, callable by API
	+ bool verifyPassword(user, password)
	+ bool verifyTOTP(user, totp)
	+ bool verifyCookie(user, cookie)
