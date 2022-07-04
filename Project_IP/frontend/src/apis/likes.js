import axios from "axios";

var likes = {
    async getAll() {
        const result = await axios.get('http://localhost:3001/like/all');
        return result;
    },
    async create(idArt, idUser) {
        const result = await axios.post('http://localhost:3001/like/create', {idArt: idArt, idUser: idUser}, {withCredentials: true, headers: {"Content-type": "application/json",} });
        return result;
    },
    async likesArt(idArt) {
        const result = await axios.post('http://localhost:3001/like/likes-art', {idArt: idArt});
        return result.data.data;
    },
    async likesUser(idUser) {
        const result = await axios.post('http://localhost:3001/like/likes-user', {idUser: idUser}, {withCredentials: true, headers: {"Content-type": "application/json",} });
        return result;
    },
    async isLiked(idArt, idUser) {
        const result = await axios.post('http://localhost:3001/like/is-liked', {idArt: idArt, idUser: idUser}, {withCredentials: true, headers: {"Content-type": "application/json",} });
        return result;
    },
    async delete(idArt, idUser) {
        const result = await axios.post('http://localhost:3001/like/delete', {idArt: idArt, idUser: idUser}, {withCredentials: true, headers: {"Content-type": "application/json",} });
        return result;
    }
}

export default likes
