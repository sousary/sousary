const nodemailer = require('nodemailer');
const bcrypt = require('bcryptjs');
const Users = require("../models/user");
const ForgotPass = require("../models/forgotPass");
const userService = require("./user");
const forgotPassService = require("./forgotPass");

const sendMail = async (email) => {
    try {
        //We verify that the email exist
        const user = await Users.findOne({'email': email});

        if (!user) {
            return {success: false, error: 'Email do not exist'};
        } else {
            let transporter = nodemailer.createTransport({
                service: 'gmail',
                auth: {
                    user: "arts.sharing.itc@gmail.com",
                    pass: "arts.1234"
                }
            })

            //Creating the code to send to the email of the user
            const code = Math.floor(Math.random() * 10) + Math.floor(Math.random() * 10) * 10 + 
                         Math.floor(Math.random() * 10) * 100 + (1 + Math.floor(Math.random() * 9)) * 1000;
            
            console.log(code);

            let message = {
                from: "example@email.com",
                to: email,
                subject: "Password forgotten",
                text: "The code requested to change your password is: " + code
            }

            //Hashing the code
            const salt = bcrypt.genSaltSync(10);
            const hashCode = bcrypt.hashSync((code+''), salt);
            
            //send the mail
            transporter.sendMail(message, function(err, info) {
                if (err) {
                    console.log(err);
                } else {
                    console.log(info);
                }
            })

            const idUser = user._id;

            try {
                //Check if a code as already been requested for this user
                const forgotP = await ForgotPass.findOne({'user': idUser});

                //If no code have already been requested we create a new entry in the database
                //otherwise we will update the existing one
                if (!forgotP) {
                    await forgotPassService.create(hashCode, idUser);
                } else {
                    await forgotPassService.update(forgotP._id, hashCode);
                }
            } catch (err) {
                return {success: false, error: err || "err"};
            }

            return {success: true, hashCode: hashCode};
        } 
    } catch (err) {
        return {success: false, error: err || "err"};
    }
}

const resetPassword = async (code, email, newPass) => {
    try {
        //We verify that the email exist
        const user = await Users.findOne({'email': email});
        if (!user) {
            return {success: false, error: 'Email do not exist'};
        } else {
            const idUser = user._id;
            try {
                //Check if a code as already been requested for this user
                const forgotP = await ForgotPass.findOne({'user': idUser});

                if (!forgotP) {
                    return {success: false, error: 'Code never requested for this email'};
                } else {
                    if (!bcrypt.compareSync(code, forgotP.code)) {
                        return {success: false, error: 'Code do not match'};
                    } else {
                        await userService.updatePassword(idUser, newPass);
                        await forgotPassService.remove(forgotP._id);
                        return {success: true, data: 'Password changed'};
                    }
                }
            } catch (err) {
                return {success: false, error: err || "err"};
            }
        }
    } catch (err) {
        return {success: false, error: err || "err"};
    }
}

module.exports = {
    sendMail,
    resetPassword
}