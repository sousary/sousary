const jwt = require("jsonwebtoken");

const config = process.env;

const currentUser = (req, res, next) => {
  if (!req.session.jwt) {
    return res.send({success: false, error: "You must log in"});
  }

  const user = jwt.verify(req.session.jwt, 'jwt-secret');
  req.currentUser = user;
  next();
};

const ensureSignedOut = (req, res, next) => {
  if (req.session.jwt) {
    return res.send({success: false, error: "You are already logged in"});
  }
  next();
};

const ensureSignedIn = (req, res, next) => {
  if (!req.session.jwt) {
    return res.send({success: false, error: "You must log in"});
  }
  next();
};

module.exports = {
  ensureSignedIn,
  ensureSignedOut, 
  currentUser
}