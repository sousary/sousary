const express = require('express');
const joiValidation = require('../middlewares/joiValidation');
const { registerSchema, loginSchema } = require('../schemas');
const { login } = require("../services/login");
const { register } = require("../services/register");
const mailSenderService = require("../services/mailSender");
const forgotPassService = require("../services/forgotPass");
const auth = require("../middlewares/auth");
const userService = require("../services/user");
const { logout } = require('../services/logout');
var router = express.Router();

router.get('/me', auth.ensureSignedIn, auth.currentUser, async (req, res) => {
  const { currentUser } = req;
  const result = await userService.findById(currentUser?._id);
  res.json(result);
})

router.post('/logout', auth.ensureSignedIn, async (req, res) => {
  const result = logout(req.session);
  return res.json(result);
})

router.post('/sendMail', async (req, res) => {
  const {email} = req.body;
  if (email != "") {
    const result = await mailSenderService.sendMail(email);
    return res.json(result);
  } else {
    return res.json({success: false, error: "Email is empty"});
  }
})

router.get('/forgots', async (req, res) => {
  const forgots = await forgotPassService.findAll();
  res.json(forgots);
})

router.post('/newPassword', async (req, res) => {
  const {code, email, newPass} = req.body;
  const result = await mailSenderService.resetPassword(code, email, newPass);
  return res.json(result);
})

router.post("/login", auth.ensureSignedOut, joiValidation(loginSchema), async (req, res) => {
  const {email, password} = req.body;
  const result = await login(email, password);
  req.session.jwt = result?.data?.token;
  res.json(result);
})

router.post("/register", auth.ensureSignedOut, joiValidation(registerSchema), async (req, res) => {
  const {email, username, password, repeatPassword} = req.body;
  const result = await register(email, username, password, repeatPassword);
  res.json(result);
})

module.exports = router;