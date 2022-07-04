const fs = require('fs');
const Users = require("../models/user");
const bcrypt = require('bcryptjs');

const findById = async (id) => {
    try {
        let user = await Users.findById(id);
        user.password = undefined;
        if (user) {
            return {success: true, data: user};
        } else {
            return {success: false, error: "User's id does not exist"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const update = async (id, email, username, firstName, lastName, occupation, aboutMeTitle, aboutMeDesc, ) => {
    try {
        const updateArt = await Users.updateOne({"_id": id}, {$set: {"email": email, "username": username, "firstName": firstName,
         "lastName": lastName, "occupation": occupation, "aboutMeTitle": aboutMeTitle, "aboutMeDesc": aboutMeDesc}});
        if (updateArt) {
            return {success: true, data: updateArt};
        } else {
            return {success: false, error: "Failed to update"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const updateImage = async (id, imageProfil) => {
    try {
        const user = await Users.findById(id);
        if (user.imageProfil != undefined && user.imageProfil != "") {
            const path = "profileImage/" + user.imageProfil;
            fs.unlink(path, (err) => {
                if (err) {
                    console.error(err)
                    return
                }    
                //file removed
            });
        }
        const updateArt = await Users.updateOne({"_id": id}, {$set: {"imageProfil": imageProfil}});
        if (updateArt) {
            return {success: true, data: updateArt};
        } else {
            return {success: false, error: "Failed to update"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
} 

const deleteById = async (id) => {
    try {
        const user = await Users.findById(id);
        if (user.imageProfil != undefined && user.imageProfil != "") {
            const path = "profileImage/" + user.imageProfil;
            fs.unlink(path, (err) => {
                if (err) {
                    console.error(err)
                    return
                }    
                //file removed
            });
        }
        const retDelete = await Users.deleteOne({"_id": id});
        if (retDelete) {
            return {success: true, data: retDelete};
        } else {
            return {success: false, error: "User's id does not exist"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const updatePassword = async (id, newPassword) => {
    try {
        var salt = bcrypt.genSaltSync(10);
        var hashedPass = bcrypt.hashSync(newPassword, salt);
        const updatePass = await Users.updateOne({"_id": id}, {"password": hashedPass});
        if (updatePass) {
            return {success: true, data: updatePass};
        } else {
            return {success: false, error: "Failed to change password"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const updateUsername = async (id, username) => {
    try {
        const updateUsername = await Users.updateOne({"_id": id}, {"username": username});
        if (updateUsername) {
            return {success: true, data: updateUsername};
        } else {
            return {success: false, error: "Failed to change username"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const getUsers = async () => {
    try {
        const users = await Users.find();
        if (users) {
            return {success: true, data: users};
        } else {
            return {success: false, error: "No users found"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

module.exports = {
    findById,
    getUsers,
    deleteById,
    updatePassword,
    updateUsername,
    update,
    updateImage
}
