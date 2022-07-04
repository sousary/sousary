const Users = require("../models/user");
const bcrypt = require('bcryptjs');

const register = async (email, username, pass, repeatPassword) =>{
    
    if (pass == repeatPassword) {
        try {
            var salt = bcrypt.genSaltSync(10);
            var password = bcrypt.hashSync(pass, salt);

            const newUser = {
                email,
                username,
                password
            }

            const createdUser = await Users.create(newUser);

            return {
                success: true,
                data: createdUser
            }
        } catch (err) {
            return {
                success: false,
                error: err
            }
        }
    } else {
        return {
            success: false,
            error: "Passwords mismatch"
        }
    }    
}

module.exports = {
    register
}