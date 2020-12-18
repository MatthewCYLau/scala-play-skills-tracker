# Scala Play Skills Tracker

A reference project to build a Scala Play API app and deploy to Heroku

An app to track users, and their skillz

## Run/Build Locally

- In project root directory, run `docker-compose up` to start the PostgreSQL database
- Then, run the following commands:

```bash
sbt # start the sbt shell
run # run the app!
```

- When running the app for the first time, you may need to visit `http://localhost:9000` to run a Flyway migration

## Usage

- Make a `GET` request to `http://localhost:9000/users` to list users
- See the full Postman collection [here](https://www.getpostman.com/collections/c32b2d46e762f9df7ad4)

## Deploy to Heroku

- In project root directory, run the following commands. See reference documentations [here](https://www.playframework.com/documentation/2.8.x/ProductionHeroku)

```bash
heroku create # create heroku app
git push heroku master # deploy to Heroku
heroku ps # check app status
heroku logs # view logs
```

- Then, visit the Heroku Postgres database page [here](https://data.heroku.com/), and find the database username, and password
- Create two Heroku environment variables for `HEROKU_DATABASE_USERNAME`, and `HEROKU_DATABASE_PASSWORD` See reference documentations [here](https://devcenter.heroku.com/articles/config-vars)
- Update `play.filters.hosts` in `application.conf` with the Heorku app URL
- Deploy to Heroku again

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)