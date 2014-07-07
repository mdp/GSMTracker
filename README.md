![Phone image](https://s3.amazonaws.com/img.mdp.im/2014--m-07-2014--m-07-IMG_20140706_2323572-rerrb-ci98y.png)

## GSM Tracker

Use cheap unactivated [burner phones](https://www.youtube.com/watch?v=QRo4-VjgT6A&feature=youtu.be) to as a location tracker.

### Why

I want to know what route my mail takes. I've put one in the mail
to see where it goes.

### How

While an unactivated phone doesn't have access to data or voice,
it still connects with the nearest cellular tower. These towers
have ID's which can then be looked up in a commercial or free 
database.

GSM Tracker runs every hour and logs the current cell tower ID
to a file. When it arrives at it's destination it, the log can
be examined and we can determine what route the phone took.

You could use a GPS logger, but those tend to fail unless they have a clearer shot to the sky. Also without data and GPS, the phone is able to last more than a week logging the Cell ID's.

### Next steps

The phone currently requires someone to dump the logs in order to analyze it. The next step is to have it randomly turn on wifi and try to upload the log files. In order to increase the chance of success, I'll be using a DNS tunneling method to ensure that it works on captive portals - [mdp/dns_leak_client](https://github.com/mdp/dns_leak_client)

### The phone

For this expirement picked up a Huawei "Inspira SIM 5 Android Prepaid Phone" from Net10 for around $28 from Amazon (sadly it's no longer available). This phone is actually operating on the T-Mobile network through Net10's MVNO.

