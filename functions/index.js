const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.pushOrders = functions.database.ref('/messages/{pushId}').onWrite( event => {

    console.log('Push notification event triggered');

    //  Grab the current value of what was written to the Realtime Database.
    var valueObject = event.data.val();

    const payload = {
        notification: {
            title: 'AquaJMTShop',
            body: "Order Details",
            sound: "default"
        },
        data: {
            title: valueObject.title,
            message: valueObject.message,
            id: valueObject.id
        }
    };

    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24 //24 hours
    };

    return admin.messaging().sendToTopic("notifications", payload, options);
});