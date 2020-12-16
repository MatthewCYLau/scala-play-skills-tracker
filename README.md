# Scala Play Skills Tracker

A reference project to build a Scala Play API app

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

- Make a `GET` request to `http://localhost:9000/profiles` to list user profiles

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)