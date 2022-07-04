var mongoose = require('mongoose');

var forgotPassSchema = new mongoose.Schema({
    code: {
        type: String,
        required: true
    },
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        unique: true
    }
}, {
    timestamps: true,
});

var ForgotPass = mongoose.model('ForgotPass', forgotPassSchema);
module.exports = ForgotPass;