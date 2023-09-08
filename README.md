# Shorts Free YT

## Background

I have lost SO much of my life to YouTube Shorts. I don't use TikTok, but surely YouTube Shorts triggers the same part of my brain that TikTok does. Oh, the hours wasted. 

The problem is though—I still love the YouTube app. It is a well made app and I have grown quite fond of it over the years. I use it all the time for podcasts and general video watching. But for the sake of my free time (and my work time 😅), I need to put up a barrier between Shorts and I.

There are couple of ways I thought of fixing this problem:
- Writing my own YouTube app from the API (API stinks)
- Leveraging some sort of A11y control or overlay to hide the Shorts stuff (Not possible/Highly intrusive)
- Self discipline (yeah, sure LOL)

None of that worked, unfournately. 

Eventually though, after a long binge and months of muelling, I thought _maybe_ I could leverage the website inside an Android app, manipulate the DOM, and remove Shorts related elements. 

And that's what you see here!

## Architecture

This is a Kotlin Multiplatform app, but I am lazy and not much of an iOS developer so the iOS app is non-existent. **Pull requests welcome!**

Fundamentally, this doesn't have any logi to share between the 2 apps. The details of getting YouTube in a browser to behave like the native app, while removing Shorts elements means there is very little "business logic" to share here.

So feel free to roast me about using Kotlin Multiplatform, but I just wanted to try it out 🤣

## Features

✅ Shorts removed from the home screen and from the feed
✅ Picture in Picture. (kind of)
✅ Background playing.
✅ Has many of the same core features as the native app. The YouTube web app is pretty good actually.
✅ Keeps user logged in.

## Drawbacks

😢 It's a web app. The look and feel isn't as good. It won't keep state as well.
😢 Deleting the DOM elements is tricky and can cause flashing.
😢 No video downloading. 

## How to use

Download Android Studio, clone this app and run the Android app on your device choice. Then make sure to disable YouTube!!

Eventually, I will add an APK for direct download, lest I am sued by YouTube.
