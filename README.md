# MySeries Project

## Description

CentraleSupélec OSY project - POOA. Coded by *Sebastien Lubineau*, *Romain Pelloie* and *Clément Dessoude*

Website where you can watch information about shows and follow some of them.

## Launch the whole project with Docker

- Install Docker on your computer
- Go inside the docker folder : `cd docker`
- Pull the image of the back : `docker pull clement26695/myseries_back`
- Pull the image of the front : `docker pull clement26695/myseries_front`
- Launch the three containers with docker-compose : `docker-compose up -d`

To see the logs of the container containing the back, run : `docker logs docker_myseries-back_1 -f`

- To see the website, go to <http://localhost:5000>
- You can make request to the backend API at <http://localhost:8080> (e.g <http://localhost:8080/api/serie/all>)
- You can access the database on the port 13306 of your computer (`mysql --host=127.0.0.1 --port=13306 -u root myseries`)

## List of possible actions

- You can browse some shows in the `Home` page
- If you click on a show, you will get some detailed information about it, and see the list of episode related to it
- You can mark some episodes as seen, rate them, etc.
- If you follow a show, and an episode of this show is aired in the next 24h, you will get a notification a 00:00. These notifications are listed in the `Notifications` section.
- You can have some basic recommandation about serie in the `Recommandation` page.
- You can see some statistics about what you watched or followed in the `Statistics` section
