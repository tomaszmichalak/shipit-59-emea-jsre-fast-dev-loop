# ShipIt 59 contributing guidelines

## How to join the project

1. Be awesome and join the project
2. Have a good humor and be open to learning new things
3. Be ready to share your knowledge with others
4. Cooperate with your team members

## How to contribute

1. Create a new branch from the `main` branch
2. Make changes in your branch
3. Create a pull request to the `main` branch
- make sure to describe your changes in the pull request
- add automated tests if possible
- update CI / CD pipeline if needed
4. Wait for pipeline to pass
5. Ask for a review from your team members
6. Merge your changes to the `main` branch

## Release process

Changes merged to the `main` branch will be automatically deployed to the staging environment. Once the changes are validated in the staging environment, they will be promoted to the pre-prod environment. After the changes are validated in the pre-prod environment, they will be promoted to the prod environment.