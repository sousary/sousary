const express = require('express');
const auth = require("../middlewares/auth");
const likeService = require("../services/like");

var router = express.Router();

router.post('/create', auth.ensureSignedIn, auth.currentUser, async (req,res) => {
    const { idArt, idUser } = req.body;
    const result = await likeService.create(idArt, idUser);
    res.json(result);
})

router.get('/all', async (req, res) => {
    const arts = await likeService.findAll();
    res.json(arts);
})

router.post('/likes-art', async (req, res) => {
    const { idArt } = req.body;
    const result = await likeService.howManyLikeForArt(idArt);
    res.json(result);
})

router.post('/likes-user', auth.ensureSignedIn, auth.currentUser, async (req, res) => {
    const { idUser } = req.body;
    const result = await likeService.whatIAlreadyLiked(idUser);
    res.json(result);
})

router.post('/is-liked', auth.ensureSignedIn, auth.currentUser, async (req, res) => {
    const { idArt, idUser } = req.body;
    const result = await likeService.isLiked(idArt, idUser);
    res.json(result);
})

router.post('/delete', auth.ensureSignedIn, auth.currentUser, async (req, res) => {
    const { idArt, idUser } = req.body;
    const deletedLike = await likeService.remove(idArt, idUser);
    res.json(deletedLike);
})

module.exports = router;
