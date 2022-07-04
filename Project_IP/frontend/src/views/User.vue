<script>
import likesApi from "@/apis/likes";
import userApi from "@/apis/user";
import authApi from "@/apis/auth";
import artsApi from "@/apis/arts";
import followApi from "@/apis/follow";
export default {
    data() {
        return {
        dataUser: {
            id: '',
            firstName: '',
            lastName: '',
            aboutMeDesc: '',
            username: '',
            follower: '',
            following: '',
        },
        profilPic: null,
        error: '',
        artsOfUser: [],
        followButton: 'Follow'
        }
    },
    async mounted() { 
        const user = await userApi.getUser(this.$route.params.id);
        if (user) {
            this.dataUser.id = user.data.data._id;
            this.dataUser.firstName = user.data.data.firstName;
            this.dataUser.lastName = user.data.data.lastName;
            this.dataUser.aboutMeDesc = user.data.data.aboutMeDesc;
            this.dataUser.username = user.data.data.username;
            this.dataUser.follower = await followApi.howManyFollower(user.data.data._id);
            this.dataUser.following = await followApi.howManyFollowing(user.data.data._id);
            if (user.data.data.imageProfil == "" || user.data.data.imageProfil == undefined) {
                this.profilPic = "/src/assets/Images/profile/noProfilePic.webp"
            } else {
                this.profilPic= 'http://localhost:3001/profileImage/' + user.data.data.imageProfil;
            }
            this.showArts(user.data.data._id);
        }
        const me = await authApi.getMe();
        if (me) {
            if (me.data.data._id == user.data.data._id) {
                this.$router.push({name: "profile"});
            }
            const followed = await followApi.isFollowed(me.data.data._id, user.data.data._id);
            if (followed) {
                this.followButton= 'Unfollow';
            }
        }
    },
    methods:{
        async showArts(idUser) {
            const arts = await artsApi.getArtsOfUser(idUser);
            this.artsOfUser = arts.data.data;
            for (let art of this.artsOfUser) {
                art.likes = await likesApi.likesArt(art._id);
            }
        },
        async followOrUnfollow() {
            const me = await authApi.getMe();
            if (me) {
                const followed = await followApi.isFollowed(me.data.data._id, this.dataUser.id);
                if (followed) {
                    await followApi.delete(me.data.data._id, this.dataUser.id);
                    this.followButton= 'Follow';
                    this.dataUser.follower--;
                } else {
                    await followApi.create(me.data.data._id, this.dataUser.id);
                    this.followButton= 'Unfollow';
                    this.dataUser.follower++;
                }
            } else {
                this.error = "You must login to follow"
                document.getElementsByClassName('errorMsgUser')[0].style.opacity = "1";
                setTimeout(this.errorDispear, 3000);
                setTimeout(this.errorReset, 4000);
            }
        },
        errorDispear() {
            document.getElementsByClassName('errorMsgUser')[0].style.opacity = "0";
        },
        errorReset() {
            this.error = '';
        }
    }
}
</script>

<template>
    <section class="profile">
     
        <div class="row">
            <div class="p-col1">
                <img class="imgProfil" :src="profilPic">
                <p class="namesProfil">{{this.dataUser.firstName}} {{this.dataUser.lastName}}</p>
                <p class="description">{{this.dataUser.aboutMeDesc}}</p>
                <p class="usernameProfil">{{this.dataUser.username}}</p>
                <button class="followButton" @click="this.followOrUnfollow">{{this.followButton}}</button>
                <div class="follow">
                    <p>Follower&nbsp;&nbsp;{{this.dataUser.follower}}&nbsp;|&nbsp;Following&nbsp;&nbsp;{{this.dataUser.following}}</p>
                </div>
                <p class="errorMsgUser">{{error}}</p>
            </div>
            <div class="rows">
                <div v-for="art in this.artsOfUser" class="pics" :key="art._id">
                    <img class="imgArts" :src='"http://localhost:3001/artsImage/" + art.image'>
                    <div class="position">
                        <div class="names">{{art.name}}</div>
                        <div class="views">
                            <font-awesome-icon :icon="['fas', 'thumbs-up']"/> &nbsp;{{art.likes}}&nbsp;&nbsp;&nbsp;
                            <font-awesome-icon :icon="['fas', 'eye']"/>&nbsp;{{art.views}}
                        </div>
                    </div>
                    <br>
                </div>
            </div>
            <!-- <h1> No arts uploaded </h1> -->
        </div>

        
    </section>
</template>

<style scoped>
.errorMsgUser {
    text-align: center;
    color: red;
    opacity: 0;
    font-weight: bold;
    transition: 0.5s;
}

.followButton {
    left: 50%;
    -ms-transform: translate(-50%, 0);
    transform: translate(-50%, 0);
    font-size: 25px;
    border-radius: 20px;
    border-width: 4px;
    padding-left: 80px;
    padding-right: 80px;
    padding-top: 5px;
    padding-bottom: 5px;
    background-color: rgb(0, 0, 0);
    color: white;
    border-color: blueviolet;
}

h2 {
    color: black;
    text-align: center;
}

h1 {
    color: black;
}

label {
    color: black;
}

.description {
    text-align: center;
    color: grey;
    font-size: 20px;
}


.imgProfil {
    height: 400px;
    width: 400px;
    margin-top: 6px;
    margin-left: 8px;
    object-fit: cover;
}

.profile{
    background-color: #f4f5f0;
   
}
.row{
    display:flex;
    justify-content:flex-start;
    margin-left: 50px;
    margin-right:50px;
    /* margin-top:100px; */
}
.p-col1{
    background:#fff;
    margin-top:50px;
    margin-bottom:5%;
    padding: 20px 20px;
    box-sizing: border-box;
    height:650px;
    width: 456px;
    margin-right: 50px;
    align-items: center;
   
    transition: 0.5s;
     
}
.p-col1:hover{
    box-shadow:0 0 20px 0px rgba(0,0,0,0.2);

}

.p-col1 .fa{
    display:block;
    color:blue;
    margin-left:170px;
    margin-top:10px;
   
    font-size:18px ;   
}

h3{
    text-align: center;
    font-weight: 600;
    margin: 10px 0;
    font-family: 'Poppins', sans-serif;
}



/*---item feature---*/

.rows {
    margin-top: 50px;
    width: fit-content;
    /* background-color:#fff; */
    justify-content: center;
    /* align-items:flex-start; */
    display:flex;
    /* flex-wrap: wrap; */
    flex-flow: row wrap;
}
/*----image---*/
.pics{
    margin-top:10px;
    margin-bottom:10px;
    margin-left:20px;
    /* width: 250px; */
    height:290px;
    background-color:#fff;
}
.pics:hover{
    box-shadow:0 0 20px 0px rgba(17, 15, 15, 0.2);  
    
}
 
.imgArts {
    /* width: 250px; */
    height:250px;
    position: relative;
   
}

.position{
    color:#000;
    font-size:14px;
    background-color: #fff;
    /* width:250px; */
    height:25px;
    display:flex; 
    justify-content: space-between;
    align-items:center;
}

.position .names{
    font-size:14px;
    margin-left: 10px;
}
.position .views{
    font-size:14px;
    margin-right: 10px;
}
.rows .cols2{
   margin-top:50px;
    width: 1100px;
    height:231px;
    background-color:#fff;
    justify-content: space-between;
    align-items:flex-start;
    display:flex;
}
      

</style>