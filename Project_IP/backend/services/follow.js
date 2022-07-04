const Follows = require("../models/follow");

const findById = async (id) => {
    try {
        const art = await Follows.findById(id);
        if (art) {
            return {success: true, data: art};
        } else {
            return {success: false, error: "Follow's id does not exist"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const create = async (userThatFollows, userFollowed) => {
    try {
        const newFollow = {
            userThatFollows, 
            userFollowed, 
        }

        const createdFollow = await Follows.create(newFollow);

        return {
            success: true,
            data: createdFollow
        }  
    } catch (err) {
        return {
            success: false,
            error: err
        }
    }
}


const howManyFollower = async (user) => {
    try {
        const follows = await Follows.find({"userFollowed": user});
        if (follows) {
            const nbFollows = follows.length;
            return {success: true, data: nbFollows};
        } else {
            return {success: false, error: "This user has no follower"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const getFollowersOfUser = async (user) => {
    try {
        const follows = await Follows.find({"userFollowed": user});
        return {success: true, data: follows};
    } catch (err) {
        return {success: false, error: err};
    }
}

const howManyFollowing = async (user) => {
    try {
        const follows = await Follows.find({"userThatFollows": user});
        if (follows) {
            const nbFollows = follows.length;
            return {success: true, data: nbFollows};
        } else {
            return {success: false, error: "This user is not following anybody"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const isFollowed = async (userThatFollows, userFollowed) => {
    try {
        const follow = await Follows.find({"userThatFollows": userThatFollows, "userFollowed": userFollowed})
        if (follow) {
            if (follow.length == 0) {
                return {success: true, data: false};
            } else {
                return {success: true, data: true};
            }
        } else {
            return {success: false, error: "This user has no follow"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}

const remove = async (userThatFollows, userFollowed) => {
    try {
        const retDelete = await Follows.deleteOne({"userThatFollows": userThatFollows, "userFollowed": userFollowed});
        if (retDelete) {
            return {success: true, data: retDelete};
        } else {
            return {success: false, error: "Follow does not exist"};
        }
    } catch (err) {
        return {success: false, error: err};
    }
}
module.exports = {
    findById,
    create,
    howManyFollower,
    getFollowersOfUser,
    howManyFollowing,
    isFollowed,
    remove
}