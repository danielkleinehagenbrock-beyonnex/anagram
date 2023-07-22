# anagram

**anagram** is a small solution to a coding task in the beyonnex.io hiring process.  
The solution is build as a small web app providing a `POST` endpoint to verify if two texts are anagrams.
The check is a syntactic only approach and ignores any semantics in the given texts.

## Documentation
There is an open-api spec, which can be found [here](src/main/resources/openapi/api.yaml), describing the endpoint
provided by this application.

## Build

Prerequisites:
* jdk 17

The build is done by gradle and can be initiated by
```shell
./gradlew build
```

The tests can be run alone by
```shell
./gradlew test
```

## Run
The application can be started by
```shell
./gradlew bootRun
```
