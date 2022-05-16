# Weather Application

A simple spring boot application to retrieve weather information.  

### Setup

### Build and run the App

* Build the application
```bash
./gradlew build
```

* Start the docker containers
```bash
docker-compose up
```

* Run the spring boot weather application. The app will load the database schema with the necessary tables (using flyway migration scripts)
```bash
java -jar build/libs/weather-<version>.jar
```

### References

* [Country Information](https://restcountries.eu/)
* [Currency Exchange Rates](http://fixer.io/)
* [Weather Information](https://openweathermap.org)

