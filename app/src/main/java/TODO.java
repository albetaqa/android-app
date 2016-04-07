/**
 * Created by Mohammad on 11/30/2014.
 */
public interface TODO {
    /*
    TODO
    make sure the app don't crash in the following situations
    - internet is cut
    - internet is slow
    - internet cut while downloading the daily xml
    - internet cut while downloading an image

    TODO
    - showing daily betaqas at some some specific time in the day
        how to handle it
        while i'll save the week photos in the xml file!! maybe we could save the time too in the xml
        to let every betaqa appear at a specific time

    TODO
    - maintain history of files for few days

    TODO Card files management
    - create folders like the following
        /albetaqa/
            2014
                12
                    31
                        ad3yatelrasoul011.jpg
                        adab0027.jpg
                        akhlaq0245.jpg
                        aqeda0057.jpg
                    30
                        border.9.png
                        ebadat0072.jpg
                        ebadat0167.jpg
                    22
                        manhyat0055.jpg
                11
                10
                09
                ..
                ..
                01
            2013
            2012

    TODO random card names
    http://albetaqa.com/social/google/random.php
    return a text file, \n separated
    each line has a file name :)

    The file name should be prefixed with the full url
    http://albetaqa.com/social/

    if the file name already has http://, the file should be downloaded from that remote url too :)

    TODO CardFileUtility
    Create a class CardFileUtility
    Most of the class functions should have locking for consistent data
        e.x.: don't save a new file while other thread is fetching file names
    functions:
        filename[] fetchCards( int pageNo )
        saveCard( jpg card )

        updateCardsInboxActivity
        updateNotifications
            use same notification and push extra information to it
            i mean if a new card is downloaded, make the notification as 2 new cards
            instead of having 2 separate notifications for each card

    TODO url of daily cards
    - will save xml files, all in one folder
    - http://albetaqa.com/social/daily/2014-12-04.xml
    - http://albetaqa.com/social/daily/2014-12-05.xml
    - http://albetaqa.com/social/daily/2014-12-06.xml
    and so on

    android will fetch that file on a daily or a weekly basis
    when the day starts the application notifies the user with that card
    - android application should know the best time for notification
        - according to smart calculations, there should be a perfect time for notifications


    TODO collect anonymous data
    background thread, collects data every day once, or every week
    compress data and send to a google document, or a php page somewhere

     data to be collected:
    - how many cards does a user read daily
        - which cards exactly does the user read and spent time reading them
    - time spent reading every card
    - ip (optional)
    - usage log
        activities navigation
        cards navigation (enter-leave time) (screen on time)
    - country, gps location
    - exceptions thrown
    - feedback and error logs
     */
}
