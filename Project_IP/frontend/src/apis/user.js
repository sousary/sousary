import axios from "axios";

var user = {
    async updateUser(dataUpdate) {
        const result = await axios.put('http://localhost:3001/user/update', dataUpdate, {withCredentials: true, headers: {"Content-type": "application/json",} });
        return result;
    },
    async updateImage(profilPic) {
        let formData = new FormData();
        formData.append("upload", profilPic);
        const result = await axios.put('http://localhost:3001/user/update-image', formData, {withCredentials: true, headers: {"Content-type": "multipart/form-data",} });
        return result;
    },
    async getUser(idUser) {
        const result = await axios.post('http://localhost:3001/user/get-user', {idUser: idUser});
        return result;
    },
}

export default user
