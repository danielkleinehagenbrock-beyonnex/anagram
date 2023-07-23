# anagram

**anagram** is a small solution to a coding task in the beyonnex.io hiring process.  
The solution is build as a small web app providing a `POST` endpoint to verify if two texts are anagrams.  

This application implements two approaches for checking texts being anagrams.
These checks are syntactic only approach and ignore any semantics in the given texts.

## Documentation
There is an open-api spec, which can be found [here](src/main/resources/openapi/api.yaml), describing the endpoint
provided by this application.

## Automated tests
- The functionality of the application is tested by a `SpringBootTest` -> [AnagramRouterConfigurationTest](src/test/kotlin/io/beyonnex/anagram/router/AnagramRouterConfigurationTest.kt)
- The function `isAnagramOf` is tested by a unit test -> [AnagramFunctionsTest](src/test/kotlin/io/beyonnex/anagram/functions/AnagramFunctionsTest.kt)
- The function `isAnagramOfVerifiedBySorting` is tested by a property based test -> [AnagramFunctionsPropertyTest](src/test/kotlin/io/beyonnex/anagram/functions/AnagramFunctionsPropertyTest.kt)

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
