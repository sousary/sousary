const Users = require("../models/user");
var bcrypt = require('bcryptjs');
const { createASessionToken } = require("./utils");

const login = async (email, password) => {
    try {
        const user = await Users.findOne({'email': email});
        if (!user) {
            return { success: false, error: 'Email not registered'};
        } else {
            if (user.matchesPasswords(password)) {   
                const token = createASessionToken(user?._id, user?.email);                
                return { success: true, data: {user, token}};
            } else {
                return { success: false, error: 'Password do not match'};
            }
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

module.exports = {
    login
}