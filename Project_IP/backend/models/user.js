const bcrypt = require('bcryptjs/dist/bcrypt');
var mongoose = require('mongoose');

var userSchema = new mongoose.Schema({
    email: {
        type: String,
        unique: true,
        required: true,
        validate: {
            validator: async val => Users.doesntExist({username: val}),
            message: ({value}) => `Username ${value} already taken.`
        }

    },
    username: {
        type: String,
        unique: true,
        required: true
    },
    firstName: {
        type: String
    },
    lastName: {
        type: String
    },
    occupation: {
        type: String
    },
    aboutMeTitle: {
        type: String
    },
    aboutMeDesc: {
        type: String
    },
    imageProfil: {
        type: String
    },
    password: {
        type: String,
        required: true
    },
}, {
    timestamps: true,
});

// userSchema.pre('save', function (next) {
//     if (this.isModified('password')) {
//         var salt = bcrypt.genSaltSync(10);
//         this.password = bcrypt.hashSync(this.password, salt);
//     }
//     next();
// });

userSchema.methods.matchesPasswords = function (password) {
    return bcrypt.compareSync(password, this.password);
};

userSchema.statics.doesntExist = async function (options) {
    return (await this.where(options).countDocuments()) === 0;
};

var Users = mongoose.model('Users', userSchema);
module.exports = Users;