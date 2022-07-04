var mongoose = require('mongoose');

var artSchema = new mongoose.Schema({
    image: {
        type: String,
        required: true
    },
    name: {
        type: String,
        required: true
    },
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        required: true
    },
    desc: String,
    views: {
        type: Number, 
        default: 0
    }
}, {
    timestamps: true,
});

var Arts = mongoose.model('Arts', artSchema);
module.exports = Arts;