# OrthodonticClinicBackend
Web application for orthodontic clinic management system.
<br/><br/>

## Table of Contents
- [Technologies](#technologies)
- [Installing](#installing)
- [API documentation](#api_documentation)
- [Frontend](#frontend)
- [Screenshots](#screenshots)
<br/><br/>

## Technologies <a name = "technologies"></a>
Project was created with:
* SpringBoot, Spring Web, Spring Security, Spring Data JPA
* Project Lombok
* java-jwt library
* Angular
* Docker
* Database: MySQL
<br/><br/>

## Installing <a name = "installing"></a>
These instructions will help you to run application on you local machine for development and testing purposes. 
#### Prerequisites

`docker with docker-compose` https://docs.docker.com/get-docker/
<br/><br/>

Execute the following commands:
```
$ cd [directory-you-want-to-store-application]
$ git clone https://github.com/mekiroo/OrthodonticClinicFrontend.git
$ git clone https://github.com/mekiroo/OrthodonticClinicBackend.git
$ cd OrthodonticClinicBackend
$ docker-compose up -d
```
Application is available under the address:
```
http://localhost:4200
```

If you want to close the running application, use:
```
$ docker-compose down
```
<br/>

## API documentation <a name = "api_documentation"></a>
API documentation is under the address:
```
http://localhost:8080/swagger-ui.html
```
<br/>

## Frontend <a name = "frontend"></a>
Frontend repository is under the address: <br/>
https://github.com/mekiroo/OrthodonticClinicFrontend.git
<br/><br/>

## Screenshots <a name = "screenshots"></a>
![HomePage](https://user-images.githubusercontent.com/95251966/143947953-9fc328a9-0e65-4fe6-9bc2-b1d1f7a15c38.png)

![Login](https://user-images.githubusercontent.com/95251966/143947989-0d367854-10b4-44c3-8b6d-10627f0fcd12.png)

![CreateVisit](https://user-images.githubusercontent.com/95251966/143947999-e581810c-44ea-44bf-bb87-c23bb67ec935.png)

![VisitsHistory](https://user-images.githubusercontent.com/95251966/143948027-38c57097-dba3-4295-8382-e07734eb4b35.png)

![TodaysVisits1](https://user-images.githubusercontent.com/95251966/143948340-e26f79be-26a3-4c2e-b30b-e3ec68f4b80a.png)

![TodaysVisits2](https://user-images.githubusercontent.com/95251966/143948349-006fe436-f9c0-4b4d-8dcf-e684e5d6c9df.png)
