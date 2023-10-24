# USERS
## DB:
- USER
	+ ID
	+ User ID
	+ Nick
	+ Email
	+ Creation date
	+ Change date
	+ Profile ID
	+ Security (embedded)
- SECURITY
	+ Change date
	+ Password Hash
	+ Salt
	+ TOTP key
## Objects:
- UserManager
	+ add
	+ get
	+ remove
	+ modify
- Auther:
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
