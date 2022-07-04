<script>
import artsApi from "@/apis/arts";
import userApi from "@/apis/user";
import likesApi from "@/apis/likes";
import followApi from "@/apis/follow";
import authApi from "@/apis/auth";
export default {
    data() {
        return {
            art: {
                user: '',
                image: ''
            },
            artUpdate: {
                _id: '',
                name: '',
                desc: ''
            },
            user: {
                _id: '',
            },
            nbLikes: 0,
            nbViews: 0,
            me: undefined,
        }
    },
    async mounted() { 
        const artResp = await artsApi.get(this.$route.params.id);
        if (artResp?.data?.success) {
            this.art = artResp.data.data;
            this.art.image = 'http://localhost:3001/artsImage/' + this.art.image;
            this.artUpdate._id = this.art._id;
            this.artUpdate.name = this.art.name;
            this.artUpdate.desc = this.art.desc;
            this.nbViews = this.art.views;
            this.nbLikes = await likesApi.likesArt(this.art._id);
            const userResp = await userApi.getUser(this.art.user);
            this.user = userResp.data.data;
            if (this.user.imageProfil == "" ||this.user.imageProfil == undefined) {
                this.user.imageProfil = "/src/assets/Images/profile/noProfilePic.webp"
            } else {
                this.user.imageProfil = 'http://localhost:3001/profileImage/' + this.user.imageProfil;
            }
        }
        this.me = await authApi.getMe();
        if (this.me) {
            const liked = await likesApi.isLiked(this.art._id, this.me.data.data._id)
            if (liked.data.data) {
                document.getElementById('likeIcon').style.color = "blue";
                this.like = "unlike"
            }
            const followed = await followApi.isFollowed(this.me.data.data._id, this.user._id);
            if (followed) {
                document.getElementById('followIcon').style.color = "rgb(38, 38, 255)";
                this.follow= 'unfollow';
            }
        }

    },
    methods:{
        async submitUpdate() {
            await artsApi.updateArt(this.artUpdate);
            document.getElementById("successUpdate").style.opacity = 1;
            setTimeout(this.successMsgDisapear, 3000);
        },
        async deleteArt() {
            document.getElementsByClassName("areYouSure")[0].style.display = "block";
        },
        async yesDelete() {
            await artsApi.deleteArt(this.artUpdate._id);
            this.$router.push({name: "user", params: {id: this.art.user}});
        },
        async noDelete() {
            document.getElementsByClassName("areYouSure")[0].style.display = "none";
        },
        successMsgDisapear() {
            document.getElementById("successUpdate").style.opacity = 0;
        }
    }
}
</script>

<template>
<body>
    <form method="post" @submit.prevent="submitUpdate">
        <section class="detail-image">
             <link href="https://cdn.jsdelivr.net/npm/remixicon@2.2.0/fonts/remixicon.css" rel="stylesheet">
            <div class="img-detail">
                <!--body-->
                <div class="middle-content">
                    <div class="images">
                        <div class="sub-title"><i class="ri-edit-box-fill"></i><p class="editing">&nbsp;EDITING</p></div>
                        <input type="text" class="titleArt" v-model="artUpdate.name" :v-bind="artUpdate.name"/> 
                        <!-- {{this.art.name}} </p> -->
                        <p class="errorMsg">You must login to follow or like</p>
                        <img class="imageArt" :src="this.art.image">
                    </div>

                    <div class="user-info2">
                        <div class="follower">                      
                            <div class="user-like">
                                <div class="iconLayout">
                                    <font-awesome-icon class="iLikeArt" id="likeIcon" :icon="['fas', 'thumbs-up']"/>
                                    <h5>like</h5>
                                    <p class="nbImpress">{{this.nbLikes}}</p>
                                </div>
                            </div>
                            <div class="user-like">
                                <div class="iconLayout">
                                    <font-awesome-icon class="iLikeArt" :icon="['fas', 'eye']"/>
                                    <h5>views</h5>
                                    <p class="nbImpress">{{this.nbViews}}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <section class="description">
            <div class="description">
                <h2 class="descriptionTitle">Description: </h2>
                <div class="paragraph">
                   
                    <textarea class="descriptionTextArea"  v-model="artUpdate.desc" :v-bind="artUpdate.desc"></textarea>
                </div>
            </div>
            <button class="buttonArtDetail" id="updateButton" type="submit">Update informations</button>
            <p id="successUpdate">Successfully updated</p>
            <button class="buttonArtDetail" id="deleteButton" @click="deleteArt">Delete art</button>
        </section>
    </form>
    <div class="areYouSure">
        <p id="pSure">Are you sure you want to delete this art ?</p>
        <div class="buttonsSure">
            <button id="buttonYes" @click="yesDelete">Yes</button>
            <button id="buttonNo" @click="noDelete">No</button>
        </div>
    </div>
</body>
</template>

<style scoped>

.editing{
    position: absolute;
    margin-top: -80px;
    margin-left: 95px;
}

/*sub title*/
.sub-title{
    height: 60px;
    margin-top:20px;
   color:rgb(56, 56, 240);
    font-size: 40px;

}
.ri-edit-box-fill{
    font-size:60px;
    color:rgb(56, 56, 240);
    margin-top:10px;
    margin-left:40px;
    
}

#successUpdate{
    color: green;
    z-index: 5;
    position: absolute;
    margin-top: -95px;
    margin-left: 50%;
    transform: translateX(-50%);
    font-weight: bold;
    opacity: 0;
    transition: 0.5s;
}
.areYouSure{
    background-color: white;
    width: 350px;
    position: absolute;
    left:50%;
    top:55%;
    transform: translateX(-50%);
    z-index: 10;
    padding: 15px;
    border-radius: 20px;
    border: 2px solid black;
    box-shadow:0 0 10px 100000px rgba(0,0,0,0.7);
    display: none;
}
.buttonsSure {
    margin-left: 50%;
    transform: translateX(-50%);
    text-align: center;
}

#buttonYes {
    background-color: red;
    border-radius: 8px;
    border: 0px;
    font-size: 15px;
    width: 40px;
    height: 25px;
}

#buttonNo {
    margin-left: 40px;
    background-color: green;
    border-radius: 8px;
    border: 0px;
    font-size: 15px;
    width: 40px;
    height: 25px;
}

#buttonYes:hover {
    box-shadow:0 0 50px 5px rgba(255,0,0,0.7);
}

#buttonNo:hover {
    box-shadow:0 0 50px 5px rgba(0,255,0,0.7);
}

#pSure{
    color: black;
    text-align: center;
    margin-bottom: 15px;
}
.buttonArtDetail{
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    height: 35px;
    font-size: 18px;
    border-radius: 20px;
    font-weight: bold;
}
input{
    width: 550px;
}
#updateButton{
    width: 550px;
    background-color: white;
    margin-top: -130px;
}
#updateButton:hover{
    background-color: rgb(36, 199, 36);

}

#deleteButton{
    width: 550px;
    background-color: red;
    color: white;
    margin-top: -70px;

}
#deleteButton:hover{
    background-color:rgb(49, 46, 46);
}


.descriptionTextArea{
    color: white;
    background-color: rgb(141, 136, 136);
    width: 550px;
    height: 80px;
    text-align:center;
    border-radius: 30px;
    margin-top: 40px;
}
.iconLayout{
    background-color: rgba(0, 0, 0, 0.76);
    padding-right: 25px;
    width:100px;
    height:100px;
    border-radius: 50%;
    padding-top: 15px;
    padding-left: 20px;
    padding-bottom: 10px;
}
#followIcon {
    color:white;
}
.nbImpress {
    color:white;
    position: absolute;
    margin-left: 50px;
    margin-top: -45px;
}
.linkProfile {
    text-decoration: none;
}

.images {
    height: 700px;
}

.imageArt {
    width: 100%;
    position: relative;
    height: fit-content;
    object-fit: contain;
    height: 700px;

}

.titleArt {
    margin-top: 15px;
    text-align: center;
    left: 50%;
    transform: translateX(-50%);
    font-size: 35px;
    color: white;
   background-color: rgb(141, 136, 136);
    border-radius: 30px;
}

.errorMsg{
    width: 130%;
    margin-bottom: 10px;
    opacity: 0;
    transition: 0.5s;
}
.usernameArtDetails{
    font-size: 30px;
    margin-left: 30px;
    margin-top: 30px;
    color:rgb(204, 204, 204);
}

body{
    margin:0px;
    padding: 0;
    background-color:#000;
    width: 100%;
    height: 100%;
}
/*header*/
.img-detail{
    width:100%;
}
.user-info{
    width: 100%;
    height: 30px;
    position: relative;
    display:flex;
    justify-content: space-between;
}

.user-info .item{
    display: flex;
    margin-left: 50px;
}
.user-info img{
    width:70px;
    height: 70px;
    border-radius: 100%;
    object-fit: cover;
    margin-top: 20px;
    transition: 0.5s;
}

/*middle*/
.user-info2{
    position: absolute;
    width: 100px;
    display: flex;
    margin-top: 11%;
    right: 50px;
    text-align: center;
    margin-top: -475px;
}

.follower img{
    width: 120px;
    height: 120px;
    border-radius:100%;
    display: flex;
}
.follower h5{
    font-size:16px;
    color:#fff;
    position:relative;
    font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
    margin-top: 10px;
    

}
.user-img{
    display: flex;
    align-items: center;
    margin-top: 70px;
}
.ri-close-circle-line{
    color: #fff;
    font-size:24px;
    margin-right: 220px;
}

.follower-icon{
    margin-top: 50px;
    margin-left: -17px;
}
.ri-add-circle-fill{
    color: blue;
    font-size:30px;
    
    position: relative;

}
input[type="file"]{
    display:none;
  }
.user-save{
    margin-top: 70px;
}
.ri-folder-open-fill{
    color:#fff;
    font-size:40px;
    
}
.user-like{
    margin-top: 70px;
}
.iLikeArt {
    color:white;
    font-size: 30px;
}

/*---description---*/

.description{
    text-align: center;
    margin-top: 180px;
    width: 100%;
    height:330px;
    position: relative;
   
}
.description h2{
    color:gray;
    font-size:30px;
    font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
}
.description .paragraph{
    color:gray;
    font-size:16px;
    font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
}


</style>