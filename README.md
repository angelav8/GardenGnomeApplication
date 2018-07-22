# Garden Gnome Monitoring System

This project is a gardening monitoring application called “Garden Gnome” where a user can check different statistics on their garden using an android application, connected to a raspberry pi system.

The idea for the project was influenced from my dad, who has a passion for gardening. He owns an allotment plot which is situated at the community centre of our hometown, Lurgan. We live about 10 minutes away from the allotment, but it can take extra time because of the traffic, and it can be expensive driving in and out every day, maybe twice a day. To reduce the time and costs we came up to a conclusion of an application, so he can monitor his garden from our home on his phone using the Wi-Fi, or on the go on his phone using his data.

The application will allow the user to check different statistics such as the moisture and temperature of their garden. It will also allow the user to check the weather predictions for the next 5 days of the week, this will allow the user to make predictions based on when to water it next. The application will let the user know where the nearest gardening centres are according to their location, this is in case they wanted to go shopping for new plants or accessories.

The Raspberry Pi system contains a moisture sensor probe and a DHT11 temperature sensor attached to the I/O pins on the pi. When the moisture sensor, which is plugged into the raspberry pi detects a change in the water i.e. if it detects the plant is dry, this will be logged in a firebase database and it will send a push notification to the user, so they know the plant needs gardened. This will then help save water in the garden, as it prevents overwatering of plants and plants can be left alone until the next notification.

The temperature sensor will log the temperature of the surrounding area every half hour. This will give an indicator to the user what the temperature is in the place where the garden is and will give an accurate reading.

This final project was awarded 78%. I was highly commended on having a good system that had a lot of different features.

The project used a multitude of different skills such as Java, Python, Google Firebase, Android Application Development and a good use of APIs.
