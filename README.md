Autodesk Test for android developers
=================================

This is a simple app as Autodesk specified, loading news articles and displaying them from https://newsapi.org/.
The app tries to show correct usage of Andriod architectural components such as LiveData, Lifecycle and Navigation, as well as third party libraries.

* The app architectural guideline is MVVM. 
* The app is written in Kotlin.
* The app observes and reacts to the NewsListFragment lifecycle and refreshes the feed accordingly upon starting the app, resuming from a background state and resuming to the main screen containing the list of articles. .
* LiveData is used.
* Navigation is used.
* Retrofit is used.
* Picasso is used.

### Future work:
* Saving data to persistant storage using room for offline usage.
* Adding search feature i.e.: specific country, source etc.

### Tests
There is a test checking a valid retrofit request to the NewsApi.
There is a test checking normal usage of app, such as waiting for data load, scrolling and entering a news article.

### Screenshots
Can be faound in the [Screenshots](https://github.com/ShaharBmDev/AutodeskTest/tree/master/Screenshots) folder.

### Apk
Can be found in the [Apk](https://github.com/ShaharBmDev/AutodeskTest/tree/master/Apk) folder.
