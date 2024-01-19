# GreenGame
Project for software engineering course in university

## Docs for all teams:
- Architects: [docs](../docs/architects/architects.md)
- Chat: [docs](../docs/chat/chat.md)
- Daily: [docs](../docs/daily/daily.md)
- Fight: [docs](../docs/fight/fight.md)
- Inventory: [docs](../docs/inventory/inventory.md)
- Scoreboard: [docs](../docs/scoreboard/scoreboard.md)
- Users: [docs](../docs/users/users.md)

## Quick start:
### Getting the source code:
Clone repository:  
`git clone https://github.com/PrzemyslawMusial242473/GreenGame.git`  
Go into cloned directory:  
`cd GreenGame`  
Change to your branch / create a new one:  
`git checkout <branch_name>` / `git branch <branch_name>`  
### Starting docker:
Start docker:  
`docker-compose up --detach`  
Now you can work on the project :D  

### After you are done:
Stop docker (while in GreenGame directory):  
`docker-compose down`  
Add edited files:  
`git add <modified_files>`  
Commit changes:  
`git commit -m <commit_message>`  
Push changes:  
`git push -u origin <branch_name>`  
**WARNING**: pushing to `master` branch is disabled! After your branch is in somehow usable state (it builds and passes tests), you can open a pull request to have it merged into `master`.
