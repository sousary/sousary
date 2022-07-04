const express = require('express');
var router = express.Router();

router.use('/auth', require('./auth'));
router.use('/user', require('./user'));
router.use('/arts', require('./art'));
router.use('/like', require('./like'));
router.use('/follow', require('./follow'));
router.use('/notif', require('./notif'));

module.exports = router;