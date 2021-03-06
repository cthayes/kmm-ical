# kmm-ical
iCalendar parser for Kotlin Multiplatform Mobile 

## Supported Fields
- Summary (mapped to title)
- Location
- Description
- Start
- End

## Dependencies
- Java
- Maven

## Installation
```
git clone https://github.com/cthayes/kmm-ical.git
cd kmm-ical
./gradlew build
```

## Usage

### Android
`./gradlew publishToMavenLocal`

`implementation("net.cthayes:kmm-ical-android-debug:1.0.1")`

### Swift
`build/bin/ios/releaseFramework/ical.framework`

```
Calendar().loadData(url: url) { result, err in
    if let result = result {
        self.data = result
    } else if let err = err {
        print(err)
    }
}
```

## Dates
The start and end dates are in UTC. Sometimes dates were in my local timezone. For consistency, they are converted back to UTC. That timezone is assumed to be EST.

## Unsupported Fields
Fields that aren't listed above are simply ignored. The parser shouldn't be effected by their presense or absense.

## Recurring Events
Recurring Events are currently not supported. The original event will still show in the list but the rest of the events in the series will not be displayed. Support for this will be added in the future.