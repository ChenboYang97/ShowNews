# ShowNews
An Android application used to show headlines and search related news, built with Java and Android MVVM architecture and utilized NEWS endpoint to provide the news data.  

## Project Demo
### Swipe and Save news
* Swipe left to dislike news (Click 👎 works as well)
* Swipe right to like news, and save it to the local (Click 👍 works as well)
* Tap top corner 👍 of the saved news to delete it from the local  

![swipe&save](https://user-images.githubusercontent.com/74288362/178090875-7edd9c2a-e9b9-436b-bb8e-c2d4162b2494.gif)   
### Back to last news
* Tap Arrow-Back icon to go back to the last news

![back](https://user-images.githubusercontent.com/74288362/178090873-5d136684-4070-41d8-b298-3b5b757f44bc.gif)   
### Search news
* Tap the Search in the middle and search news by keywords

![search](https://user-images.githubusercontent.com/74288362/178090877-80f009d6-9ecd-4318-9eac-c62982a5ff87.gif)   

## Reflection 
This was a side project built during my second year at the University of Southern California. Project goals included using Android technologies learned up until this point and familiarizing myself with the Android development.  
   
### Technologies implemented

* **Android MVVM architecture** for development pattern
* **JetPack navigation component** for navigation
* **Recycler View** for showing news
* **Room** for database to support offline mode
* **Retrofit** for pulling the news from NEWS RESTful endpoint
* Third-party library **CardStackView** for tinder-like swiping cards (CardStackView library can be found here: [Link](https://github.com/yuyakaido/CardStackView))
