const Likes = require("../models/like");

const findAll = async () => {
    try {
        const arts = await Likes.find();
        if (arts) {
            return {success: true, data: arts};
        } else {
            return {success: false, error: "No like's found"};
        }
    } catch (err) {
        return {success: false, error: err};
    } 
}

const create = async (art, user) => {
    try {

        const like = await Likes.find({"art": art, "user": user})
        if (like.length != 0) {
            return {
                success: false,
                error: "Art already liked"
            }
        } else {
            const newLike = {
                art, 
                user
            }

            const createdLike = await Likes.create(newLike);

            return {
                success: true,
                data: createdLike
            }  
        }
        } catch (err) {
        return {
            success: false,
            error: err
        }
    }
}

const howManyLikeForArt = async (idArt) => {
    try {
        const likes = await Likes.find({"art": idArt});
        if (likes) {
            const nbLikes = likes.length;
            return {success: true, data: nbLikes};
        } else {
            return {success: false, error: "This art has no like"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const whatIAlreadyLiked = async (idUser) => {
    try {
        const likes = await Likes.find({"user": idUser});
        if (likes) {
            return {success: true, data: likes};
        } else {
            return {success: false, error: "This art has no like"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const isLiked = async (idArt, idUser) => {
    try {
        const like = await Likes.find({"art": idArt, "user": idUser})
        if (like) {
            if (like.length == 0) {
                return {success: true, data: false};
            } else {
                return {success: true, data: true};
            }
        } else {
            return {success: false, error: "This art has no like"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const remove = async (idArt, idUser) => {
    try {
        const retDelete = await Likes.deleteOne({"art": idArt, "user": idUser});
        if (retDelete) {
            return {success: true, data: retDelete};
        } else {
            return {success: false, error: "Like does not exist"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

module.exports = {
    findAll,
    create,
    howManyLikeForArt,
    whatIAlreadyLiked,
    isLiked,
    remove
}