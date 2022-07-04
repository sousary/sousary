var mongoose = require('mongoose');

var likeSchema = new mongoose.Schema({
    art: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Arts",
        required: true
    },
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        required: true
    }
}, {
    timestamps: true,
});

var Like = mongoose.model('Like', likeSchema);
module.exports = Like;