var mongoose = require('mongoose');

var notifSchema = new mongoose.Schema({
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        required: true
    },
    otherUser: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        required: true
    },
    newArt: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Arts",
    },
    type: {
        type: String, 
        enum: ['newFollower', 'newArt']
    }
}, {
    timestamps: true,
});

var Notifs = mongoose.model('Notifs', notifSchema);
module.exports = Notifs;