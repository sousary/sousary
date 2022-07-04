const jwt = require("jsonwebtoken");

const createASessionToken = (userId, email) => {
    return jwt.sign({
        _id: userId,
        email,
    }, 'jwt-secret');
}

module.exports = {
    createASessionToken
}