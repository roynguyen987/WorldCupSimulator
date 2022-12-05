# WorldCupSimulator
This multithreaded program simulates a tournament system fit for the World Cup format. Implemented with a client/server program as well.

# PA1
PA1 for CSCI3920 by Roy Nguyen and Daniel/Logan McEldowney

Current state: (Fully Functional - load/save to file not working only)
For the current state of the project, we have working client/server programs and a working process client request function going on in which we can process most
functions and the server and client can communication with no error. We also have a well implemented and working tournament class that can store information and simulate a tournament system very well.
We have the javafx GUIs for the clients working and displaying the right information with the correct functionality. We have a multithreaded server working as well
in which it is able to handle multiple clients. We got the saving to file to work for this project, however, we could not
get the load from a file to work. I am not sure whether it is an issue due to the file stream or something of that nature.
We left comments about how we tried to implement this function and hopefully it is somewhere along the right tracks.


TO RUN:
- Start the ServerApplication  in the PA1 Folder.
- Press A To start the server
- Start the Application in the clientProject1 Folder
- Click admin or public to start a client

Requirements and Implementation:
For this project our requirements primarily focus around having a working tournament simulator for tournaments that follow the format of the World Cup tournament. Below are a list of some of the specific requirements of this project and a description:

R001: -Implementing a tournament class with all of the attributes of a World Cup tournament

Requirement: In this program we want to be able to create a tournament class that can be used as a basis to track and simulate a World Cup tournament. This class should be able to work in creating many different tournaments and should also be able to be updated. This program should also use this class to keep track of and update data.

Implementation: -The implementation for this class was done under the "tournament" directory where there are many different classes that we used to apply to this process
For github under PA1/PA1/src/edu/ucdenver/tournament:

Country.java:
This class implemented a Country object used to identify what Country a team represents. Creating this object only takes in a name as a parameter which is the only variable contained within the class.

LineUp.java:
This class is used for the LineUp of each team and takes in the team as the parameter and contains and ArrayList of players that represents the lineup.

Match.java:
The Match class is used to hold the match score and have a specific DateTime associated with them in which this tournament assumes that no match can occur at the same time. This DateTime is also used to find specific matches in some cases. This class also contains functions that may return the scores and the lineups for either team within the match.

Player.java:
The Player class is used for the information of the player. Takes in the player's info as parameters (name,age,height,weight).

Referee.java:
The Referee class contains the referee information which includes the name and the country represented.

Team.java:
The team class contains team information in the form of the name and the country and the list of players on the team.


Tournament.java:
This tournament class uses all of the other classes above to implement all of the functions needed for a functioning tournament system. The tournament has a name, start date and an end date that it takes in as parameters. This tournament can be saved to a file in which saves all of the tournament information to a file and can also be loaded in from a file which can take information saved within a file and load up the tournament with that information. There are all functions to add the objects created above into the tournament(ex. addTeam(), addMatch()). It also has functions that creates matches like adding referees and players to matches and the addMatch() function itself. The getMatchesFor() and getMatchesOn() function are used to get matches for a certain team and get matches for on a certain date respectively. The upcoming matches can also be retrieved within the tournament with the getUpcomingMatches() function.



R002: -Multithreaded Client and Server Program

Requirement: This program should be able to implement a Multithreaded Client and Server Program. There should be two client programs in which
there is one for the administrator and one for the client. The administrator should be able to make changes and request them by sending messages to
the server program and the server should be able to process those request from the client. The non-administrative user/user client, should be able
to view the upcoming matches and other information about the tournament by using the javafx GUI built. Using a multithreaded server, multiple user clients
can access this system at once implemented with an overridden run() function from the runnable interface.

Server:
For github under PA1/PA1/src/edu/ucdenver/server

ClientWorker.java:
The primary purpose of this java class is to work with the client and process the request recieved from the client. The processClientRequest()
function will take in the message recieved from from the client and use that string to apply changes to the tournament(if the client is an admin).
If the client is just a user the request will mostly consist of viewing information of the tournaments. The proccessing message takes the string
recieved and uses tokenization to break down the message and apply the tournament functions to the tournament created based on what was recieved in the message.
For example, a sample message would something like 'ADDCOUNTRY,NAME' in which will add a country to the tournament with that name. There are many different type of
messages that the client can send through which will apply different functionalities to the tournament.


Server.java:
The server class is used to implement a server that the client can connect to and can be ran using a port and a backlog.


App:
For github under PA1/PA1/src/edu/ucdenver/app

ServerApplication.java:
This class was used to create the application seen on the server side. On the server side, there are four options a,b,c,d
a) will load the previous state
b) starts the server
c) stops the server
d) saves the current state of the program.
This is done with help of the functions mentioned in the tournament class above.




R003: -JavaFX implementation for client GUI:

Requirement: There is a javafx implementation for the client GUI in which the admin can interact to apply all of the functions to update or edit a tournament
and the user can interact to view different information about the tournament.

Implementation: We built the javafx for the client so that the buttons will return a string that is used as a message for the client to
send to the server to process and apply what is neccessary based on the message.


R004: -Edge cases and bugs handled:
Description: We have handled many edge cases and bugs required including edge cases like not being able to add the same player or countries with the same name
and cases of that nature.


