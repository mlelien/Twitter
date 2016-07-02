# Project 3 - *Twitter Clone*

**Twitter Clone** is an android app that allows a user to view home and mentions timelines, view user profiles with user timelines, as well as compose and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **30+** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **sign in to Twitter** using OAuth login process
* [X] User can **switch between Timeline and Mention views using tabs**
  * [X] User is displayed the username, name, and body for each tweet
  * [X] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
* [X] User can **compose and post a new tweet**
  * [X] User can click a "Compose" icon in the Action Bar on the top right
  * [X] User can then enter a new tweet from a second activity and then post this to twitter
* [X] User can navigate to **view their own profile**
  * [X] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [X] User can **click on the profile image** in any tweet to see **another user's** profile.
 * [X] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
 * [X] Profile view includes that user's timeline of recent tweets

The following **optional** features are implemented:

* [X] While composing a tweet, user can see a character counter with characters remaining for tweet out of 140
* [X] User can **pull down to refresh tweets** in either timeline.
* [X] User can **search for tweets matching a particular query** and see results.
* [X] Improve the user interface and theme the app to feel twitter branded with colors and styles
* [X] User can **"reply" to any tweet on their home timeline**
  * [X] The user that wrote the original tweet is automatically "@" replied in compose
* [X] User can click on a tweet to be **taken to a "detail view"** of that tweet
* [X] Used Parcelable instead of Serializable leveraging the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler) when passing data between activities.

## Video Walkthrough

Here's a walkthrough of implemented user stories:
http://imgur.com/a/63npk

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Figuring out how/where to pass the data was confusing.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright 2016 Emily Lien

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
