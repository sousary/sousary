const ForgotPass = require("../models/forgotPass");

const findById = async (id) => {
    try {
        const forgotpass = await ForgotPass.findById(id);
        if (forgotpass) {
            return {success: true, data: forgotpass};
        } else {
            return {success: false, error: "ForgotPass' id does not exist"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const findAll = async () => {
    try {
        const forgotpass = await ForgotPass.find();
        if (forgotpass) {
            return {success: true, data: forgotpass};
        } else {
            return {success: false, error: "No forgotpass found"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const remove = async (idEmail) => {
    try {
        const retDelete = await ForgotPass.deleteOne({"email": idEmail});
        if (retDelete) {
            return {success: true, data: retDelete};
        } else {
            return {success: false, error: "ForgotPass' id does not exist"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const create = async (code, user) => {
    try {
        const newForgotPass = {
            code, 
            user
        }

        const createdForgotPass = await ForgotPass.create(newForgotPass);

        return {success: true, data: createdForgotPass}
    } catch (err) {
        return {success: false, error: err}
    }
}

const update = async (id, code, user) => {
    try {
        const updateForgotPass = await ForgotPass.updateOne({"_id": id}, {$set: {"code": code, "user": user}});
        if (updateForgotPass) {
            return {success: true, data: updateForgotPass};
        } else {
            return {success: false, error: "Failed to update"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

module.exports = {
    findById,
    findAll,
    create,
    update,
    remove
}