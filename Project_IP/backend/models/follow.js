var mongoose = require('mongoose');

var followSchema = new mongoose.Schema({
    userThatFollows: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        required: true
    },
    userFollowed: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        required: true
    }
}, {
    timestamps: true,
});

var Follows = mongoose.model('Follows', followSchema);
module.exports = Follows;