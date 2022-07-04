const express = require('express');
const auth = require("../middlewares/auth");
const artService = require("../services/art");
const followService = require("../services/follow");
const notifService = require("../services/notif");
const multer = require('multer');
const { processIf } = require('@vue/compiler-core');

var router = express.Router();
const upload = multer({
    dest: 'artsImage',
    limits: {
        fileSize: 10000000, //10 Mo
    },
    fileFilter(req, file, cb) {
        if (!file.originalname.match(/\.(png|jpg|jpeg|PNG|JPG|JPEG)$/)){
            cb(new Error('Please upload an image (png, jpg or jpeg)'))
        }
        cb(undefined, true)
    },
});

router.post('/create', auth.ensureSignedIn, auth.currentUser, upload.single('upload'), async (req,res) => {
    const { name, desc } = req.body;
    const { currentUser } = req;
    const user = currentUser?._id;
    const image = req.file.filename;
    const result = await artService.create(image, name, user, desc);
    if (result?.success) {
        const followers = await followService.getFollowersOfUser(user);
        if (followers?.success) {
            for (let follower of followers.data) {
                await notifService.createNewArt(follower.userThatFollows, user, result.data);
            }
        }
    }
    res.json(result);
}, (error, req, res, next) => {
    res.status(400).send({error: error.message});
})

router.get('/all', async (req, res) => {
    const arts = await artService.findAll();
    res.json(arts);
})

router.post('/plus-view', async (req, res) => {
    const { id } = req.body;
    const result = await artService.plusView(id);
    res.json(result);
})

router.get('/my-arts', auth.ensureSignedIn, auth.currentUser, async (req, res) => {
    const { currentUser } = req;
    const result = await artService.artsOfUser(currentUser?._id);
    res.json(result);
})

router.post('/arts-user', async (req, res) => {
    const { idUser } = req.body;
    const result = await artService.artsOfUser(idUser);
    res.json(result);
})
  
router.put('/update', auth.ensureSignedIn, auth.currentUser, async (req, res, next) => {
    const { _id, name, desc } = req.body;
    const result = await artService.update(_id, name, desc);
    res.json(result);
})

router.delete('/delete/:id', auth.ensureSignedIn, async (req, res, next) => {
    const { id } = req.params;
    const deletedArt = await artService.remove(id);
    res.json(deletedArt);
})

router.get('/:id', async (req, res) => {
    const { id } = req.params;
    const result = await artService.findById(id);
    res.json(result);
})

module.exports = router;
