const express = require('express');
const auth = require("../middlewares/auth");
const { logout } = require('../services/logout');
const userService = require("../services/user");
const multer = require('multer');

var router = express.Router();
const upload = multer({
  dest: 'profileImage',
  limits: {
      fileSize: 10000000, //10 Mo
  },
  fileFilter(req, file, cb) {
      if (!file.originalname.match(/\.(png|jpg|jpeg|PNG|JPG|JPEG)$/)){
          cb(new Error('Please upload an image (png, jpg or jpeg)'))
      }
      cb(undefined, true)
  }
});

router.get('/users', auth.ensureSignedIn, async (req, res) => {
  const users = await userService.getUsers();
  res.json(users);
})

router.post('/update-password', auth.ensureSignedIn, auth.currentUser, async (req, res, next) => {
  const {password} = req.body;
  const { currentUser } = req;
  const updatedPass = await userService.updatePassword(currentUser?._id, password);
  res.json(updatedPass);
})

router.post('/update-user', auth.ensureSignedIn, auth.currentUser, async (req, res, next) => {
  const {username} = req.body;
  const { currentUser } = req;
  const updatedUsername = await userService.updateUsername(currentUser?._id, username);
  res.json(updatedUsername);
})

router.post('/get-user', async (req, res) => {
  const {idUser} = req.body;
  const updatedUsername = await userService.findById(idUser);
  res.json(updatedUsername);
})

router.put('/update', auth.currentUser, auth.ensureSignedIn, async (req, res, next) => {
  const { email, username, firstName, lastName, occupation, aboutMeTitle, aboutMeDesc} = req.body;
  const { currentUser } = req;
  const result = await userService.update(currentUser?._id, email, username, firstName, lastName, occupation, aboutMeTitle, aboutMeDesc);
  res.json(result);
})

router.put('/update-image', auth.currentUser, auth.ensureSignedIn, upload.single('upload'), async (req, res, next) => {
  const imageProfil = req.file.filename;
  const { currentUser } = req;
  const result = await userService.updateImage(currentUser?._id, imageProfil);
  res.json(result);
})

router.post('/delete-user', auth.ensureSignedIn, auth.currentUser, async (req, res, next) => {
  const { currentUser } = req;
  const deletedUser = await userService.deleteById(currentUser?._id);
  logout(req.session);
  res.json(deletedUser);
})

module.exports = router;