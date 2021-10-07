# PayMyBuddy

### Running App

Post installation of MySQL, Java and Maven, you will have to set up the tables and data in the data base.
For this, please run the sql commands present in the `Data.sql` file under the `resources` folder in the code base.
Then run application as spring boot app,
####Pages:
 * Login page : [localhost:8080](http://localhost:8080/) <br> 
        username and password to test:<br>
        username: **khalil@gmail.com**<br>
        password: **123456**<br>
        after login you will be redirect to transaction page
 * Transaction page : [transfer](http://localhost:8080/transfer)
 * SignUp page : [SignUp](http://localhost:8080/signup)
### Testing

The app has unit tests and integration tests written.
To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.

`mvn test`


## diagramme de classe 

![Class Diagram v3](https://user-images.githubusercontent.com/64974948/136042482-c1f2d34a-b463-4b53-bc9f-0582ad4d4759.png)


## Le modèle physique de données

![MPD_without_Friend](https://user-images.githubusercontent.com/64974948/136365467-798fed94-bd80-44c4-a254-6daeacbee0a7.PNG)

## Business Model

![BusinessModel_01](https://user-images.githubusercontent.com/64974948/136040585-4b5be2c1-9526-42fe-a347-6334f8103c4d.png)
![BusinessModel_02](https://user-images.githubusercontent.com/64974948/136040610-64573b8b-38f8-4ef4-a03c-e4a30ee74fe6.png)

