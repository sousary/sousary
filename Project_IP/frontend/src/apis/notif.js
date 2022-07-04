import axios from "axios";

var notif = {
    async get5LastNotifs(user) {
        const result = await axios.post('http://localhost:3001/notif/get-5-last-notifs', {user: user}, {withCredentials: true, headers: {"Content-type": "application/json",} });
        return result;
    },
    async delete(id) {
        const result = await axios.post('http://localhost:3001/notif/delete', {id: id}, {withCredentials: true, headers: {"Content-type": "application/json",} });
        return result;
    }
}

export default notif
