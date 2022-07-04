const express = require('express');
const auth = require("../middlewares/auth");
const notifService = require("../services/notif");

var router = express.Router();

router.post('/get-5-last-notifs', auth.ensureSignedIn, auth.currentUser, async (req, res) => {
    const { user } = req.body;
    const notifs = await notifService.get5LastNotifs(user);
    res.json(notifs);
})

router.post('/delete', auth.ensureSignedIn, auth.currentUser, async (req, res) => {
    const { id } = req.body;
    const notifDeleted = await notifService.deleteById(id);
    res.json(notifDeleted);
})

module.exports = router;
