const express = require('express');
const auth = require("../middlewares/auth");
const followService = require("../services/follow");
const notifService = require("../services/notif");

var router = express.Router();

router.post('/create', auth.ensureSignedIn, auth.currentUser, async (req,res) => {
    const { userThatFollows, userFollowed } = req.body;
    const result = await followService.create(userThatFollows, userFollowed);
    if (result?.success) {
        await notifService.createNewFollower(userFollowed, userThatFollows);
    }
    res.json(result);
})

router.post('/how-many-follower', async (req, res) => {
    const { user } = req.body;
    const result = await followService.howManyFollower(user);
    res.json(result);
})

router.post('/how-many-following', async (req, res) => {
    const { user } = req.body;
    const result = await followService.howManyFollowing(user);
    res.json(result);
})

router.post('/is-followed', auth.ensureSignedIn, auth.currentUser, async (req, res) => {
    const { userThatFollows, userFollowed } = req.body;
    const isFollowed = await followService.isFollowed(userThatFollows, userFollowed);
    res.json(isFollowed);
})

router.post('/delete', auth.ensureSignedIn, auth.currentUser, async (req, res) => {
    const { userThatFollows, userFollowed } = req.body;
    const deletedFollow = await followService.remove(userThatFollows, userFollowed);
    res.json(deletedFollow);
})

module.exports = router;
