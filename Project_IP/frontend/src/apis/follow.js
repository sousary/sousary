import axios from "axios";

var follow = {
    async create(userThatFollows, userFollowed) {
        const result = await axios.post('http://localhost:3001/follow/create', {userThatFollows: userThatFollows, userFollowed: userFollowed}, {withCredentials: true, headers: {"Content-type": "application/json",} });
        return result;
    },
    async howManyFollower(user) {
        const result = await axios.post('http://localhost:3001/follow/how-many-follower', {user: user});
        return result.data.data;
    },
    async howManyFollowing(user) {
        const result = await axios.post('http://localhost:3001/follow/how-many-following', {user: user});
        return result.data.data;
    },
    async isFollowed(userThatFollows, userFollowed) {
        const result = await axios.post('http://localhost:3001/follow/is-followed', {userThatFollows: userThatFollows, userFollowed: userFollowed}, {withCredentials: true, headers: {"Content-type": "application/json",} });
        return result.data.data;
    },
    async delete(userThatFollows, userFollowed) {
        const result = await axios.post('http://localhost:3001/follow/delete', {userThatFollows: userThatFollows, userFollowed: userFollowed}, {withCredentials: true, headers: {"Content-type": "application/json",} });
        return result;
    }
}

export default follow
