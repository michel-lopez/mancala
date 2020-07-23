# Mancala

## Demo
https://tealorange-mancala-game.herokuapp.com

## Modules
### Back end: Spring boot
* The game logic can be found in backend/src/main/java/com/tealorange/mancala/model
* Run from command line with: `./mvnw --projects backend --also-make spring-boot:run`
* Open in browser: http://localhost:8080

### Front end: Renders the Mancala game with a simple HTML Canvas element.
* Run this command to develop on the front only with live reload: `npm run --prefix frontend serve`
* Open in browser: http://localhost:1234

## Package
* Run this command to create a package `./mvnw --projects backend --also-make package` (or `install`)
* The frontend code will be added to the same package.

## Todo
* The spring-boot code is a quick and dirty copy of https://github.com/michel-lopez/dashboard and needs to be improved.
* The front end code is a quick and dirty copy of https://github.com/michel-lopez/tealorange and needs to be improved.
* Make it possible to play against the computer
* Add a multiplayer mode