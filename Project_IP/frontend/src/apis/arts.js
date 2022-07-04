import axios from "axios";

var arts = {
    async get(id) {
        const result = await axios.get('http://localhost:3001/arts/'+id);
        return result;
    },
    async getAll() {
        const result = await axios.get('http://localhost:3001/arts/all');
        return result;
    },
    async getMyArts() {
        const result = await axios.get('http://localhost:3001/arts/my-arts', {withCredentials: true, headers: {"Content-type": "application/json",} });
        return result;
    },
    async getArtsOfUser(idUser) {
        const result = await axios.post('http://localhost:3001/arts/arts-user', {idUser: idUser});
        return result;
    },
    async arrayBufferToBase64( buffer ) {
        return window.btoa(
            buffer.reduce( (data, byte) => data + String.fromCharCode(byte), '')
         );
    },
    async create(image, name, desc) {
        let formData = new FormData();
        formData.append("upload", image);
        formData.append("name", name);
        formData.append("desc", desc);
        const result = await axios.post('http://localhost:3001/arts/create', formData, {withCredentials: true, headers: {"Content-type": "multipart/form-data",} });
        return result;
    },
    async oneMoreViewOnArt(id) {
        const result = await axios.post('http://localhost:3001/arts/plus-view', {id: id});
        return result;
    },
    async deleteArt(id) {
        const result = await axios.delete('http://localhost:3001/arts/delete/'+id, {withCredentials: true, headers: {"Content-type": "application/json",} });
        return result;
    },
    async updateArt(dataUpdate) {
        const result = await axios.put('http://localhost:3001/arts/update', dataUpdate, {withCredentials: true, headers: {"Content-type": "application/json",} });
        return result;
    }
}

export default arts
