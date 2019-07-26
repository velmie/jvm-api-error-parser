# API Parser

[![](https://jitpack.io/v/velmie/jvm-api-parser.svg)](https://jitpack.io/#velmie/jvm-api-parser)

A library for parsing responses from api and converting error codes into messages for the user.

- [API response description](#api-response-description)
- [Version](#version)
- [How it works](#how-it-works)
- [Installation](#installation)
- [License](#license)

# API response description
**It is assumed that the response will correspond to the following specifications.**

Each error from server should be in next format:

- ***code***: a unique code of an error. Used to identify error from the dictionary.
- ***target***: some sort of error scope
- ***field*** - the error related to certain field
- ***common*** - the error related to whole request
- ***message (OPTIONAL)***: the error message for developers (use it only for debug purposes)
- ***source (OPTIONAL)***: a container for additional data. Arbitrary structure: ( field: resource object attribute name. Required if target set to field. )

Example:
```json
{
"data": [
     {
       "id": 1,
       "userName": "Tom",
       "age": 21
     },
     {
       "id": 2,
       "userName": "Bob",
       "age": 22
     }
   ],
  "errors": [
    {
      "code": "insufficient_funds",
      "target": "common",
      "message": "Hi Nick, it seems that user has empty balance"
    },
    {
      "code": "invalid_punctuation",
      "target": "field",
      "source": {
        "field": "userPassword"
      },
      "message": "Hi Vova, it seems that the password provided is missing a punctuation character"
    },
    {
      "code": "invalid_password_confirmation",
      "target": "field",
      "source": {
        "field": "userPassword",
        "someAdditionalData": "bla bla bla"
      },
      "message": "Hi Lesha, it seems that the password and password confirmation fields do not match"
    }
  ]
 }
```
# Version
0.0.2

# How it works
The library provides ready-made interfaces for server responses to which the object passed to the parmer must correspond.

To initialize the ErrorParser, you must pass to the constructor:
  errorMessages: 
- `Map<String, String>` - the key is the error code and the value of the displayed message
- `defaultErrorMessage`: String - message of unknown errors


**Api parser description:**
- `parse(response: ResponseInterface<T>)` - returns `ApiParserResponse` in the states: success , empty or error
- `getParserResponse(response: ResponseInterface<T>)` - parses the server response object and returns the processed result
- `getErrors(errors: List<ErrorMessageInterface>)` - returns a list of processed errors
- `getMessage(errorCode: String)` - returns the message associated with this error code
- `getMessage(errorMessage: ErrorMessageInterface)` - returns the processed error
- `getFirstMessage(errors: List<ErrorMessageInterface>)` - returns the first processed error from the list

JVM/Android(Kotlin)
-------------

```kotlin
val apiParser = ErrorParser(resource.getStringArray(R.array.error_code)
                              .zip(resource.getStringArray(R.array.error_message)).toMap(),
                              resource.getString(R.string.something_went_wrong))
               
val parserResponse: ParserResponseEntity<UserEntity> = apiParser.getParserResponse(serverResponse)
                             
val apiParserResponse: ApiParserResponse<UserEntity> = apiParser.parse(serverResponse)  

when (apiParserResponse) {
                is ApiParserEmptyResponse -> {
                    //do something
                }
                is ApiParserErrorResponse -> {
                     //do something
                }
                is ApiParserSuccessResponse -> {
                     //do something
                }
}
                            
```

# Installation

####Gradle
Add it in your **root build.gradle** at the end of repositories:
```groovy
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
Then, add the API Parser library to your **app's build.gradle**:
```groovy
dependencies {
	 implementation 'com.github.velmie:jvm-api-parser:Tag'
}
```
#### Maven

Add the JitPack repository to your build file:
```XML
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```

Add the dependency:
```XML
<dependency>
    <groupId>com.github.velmie</groupId>
    <artifactId>jvm-api-parser</artifactId>
    <version>Tag</version>
</dependency>
```

# License
ApiParser is released under the MIT license. See [LICENSE](https://github.com/velmie/ios-api-error-parser/blob/0.0.2/LICENSE) for details.