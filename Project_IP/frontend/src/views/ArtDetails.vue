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
                name: '',
                desc: '',
                user: '',
                image: ''
            },
            user: {
                _id: '',
                username: '',
                imageProfil: "/src/assets/Images/profile/noProfilePic.webp"
            },
            follow: "follow",
            like: "like",
            nbFollow: 0,
            nbLikes: 0,
            nbViews: 0,
            me: undefined,
        }
    },
    async mounted() { 
        const artResp = await artsApi.get(this.$route.params.id);
        if (artResp?.data?.success) {
            this.art= artResp.data.data;
            this.art.image = 'http://localhost:3001/artsImage/' + this.art.image;
            this.nbViews = this.art.views;
            this.nbLikes = await likesApi.likesArt(this.art._id);
            const userResp = await userApi.getUser(this.art.user);
            this.user = userResp.data.data;
            if (this.user.imageProfil == "" ||this.user.imageProfil == undefined) {
                this.user.imageProfil = "/src/assets/Images/profile/noProfilePic.webp"
            } else {
                this.user.imageProfil = 'http://localhost:3001/profileImage/' + this.user.imageProfil;
            }
            this.nbFollow = await followApi.howManyFollower(this.user._id);
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
        async iLikeButton () {
            if (this.me) {
                const liked = await likesApi.isLiked(this.art._id, this.me.data.data._id)
                if (!liked.data.data) {
                    await likesApi.create(this.art._id, this.me.data.data._id);
                    document.getElementById('likeIcon').style.color = "blue";
                    this.nbLikes++;
                    this.like = "unlike";
                } else {
                    await likesApi.delete(this.art._id, this.me.data.data._id);
                    document.getElementById('likeIcon').style.color = "white";
                    this.nbLikes--;
                    this.like = "like";
                }
            } else {
                clearTimeout();
                document.getElementsByClassName('errorMsg')[0].style.opacity = "1";
                setTimeout(this.youMustLoginDispear, 3000);
            }
        },
        async followOrUnfollow() {
            if (this.me) {
                if (this.me.data.data._id == this.user._id) {    
                    document.getElementById('errorMsgFollow').style.opacity = "1";
                    setTimeout(this.errorFollowDisapear, 3000);
                } else {
                    const followed = await followApi.isFollowed(this.me.data.data._id, this.user._id);
                    if (followed) {
                        await followApi.delete(this.me.data.data._id, this.user._id);
                        document.getElementById('followIcon').style.color = "white";
                        this.follow= 'follow';
                        this.nbFollow--;
                    } else {
                        await followApi.create(this.me.data.data._id, this.user._id);
                        document.getElementById('followIcon').style.color = "rgb(38, 38, 255)";
                        this.follow= 'Unfollow';
                        this.nbFollow++;
                    }
                }
            } else {
                document.getElementsByClassName('errorMsg')[0].style.opacity = "1";
                setTimeout(this.youMustLoginDispear, 3000);
            }
        },
        youMustLoginDispear() {
            document.getElementsByClassName('errorMsg')[0].style.opacity = "0";
        }, 
        errorFollowDisapear() {
            document.getElementById('errorMsgFollow').style.opacity = "0";
        }
    }
}
</script>

<template>
<body>
    <section class="detail-image">
         <link href="https://cdn.jsdelivr.net/npm/remixicon@2.2.0/fonts/remixicon.css" rel="stylesheet">
        <div class="img-detail">
            <div class="user-info">
                <div class="item">   
                    <a class="linkProfile" :href="'/user/'+this.user._id"><img :src="this.user.imageProfil"> </a>
                    <div>
                        <a class="linkProfile" :href="'/user/'+this.user._id"><p class="usernameArtDetails">{{this.user.username}}</p></a>
                        <p class="titleArt"> {{this.art.name}} </p>
                    </div>
                </div>    
            </div>

            <!--body-->
            <div class="middle-content">
                <div class="images">
                    
                    <p class="errorMsg">You must login to follow or like</p>
                    <img class="imageArt" :src="this.art.image">
                </div>

                <div class="user-info2">
                    <div class="follower">
                        <div class="iconLayout">
                            <font-awesome-icon id="followIcon" class="iLikeArt" @click="followOrUnfollow" :icon="['fas', 'users']"/>
                            <h5>{{this.follow}}</h5>
                            <p class="nbImpress">{{this.nbFollow}}</p>
                        </div>    
                        <p id="errorMsgFollow">You cannot follow yourself !</p>                    
                        <div class="user-like">
                            <div class="iconLayout">
                                <font-awesome-icon class="iLikeArt" id="likeIcon" @click="iLikeButton" :icon="['fas', 'thumbs-up']"/>
                                <h5>{{this.like}}</h5>
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
                <!--add new description -->
                <p class="In-ArtDetailsAccount"><i class="ri-account-circle-fill"></i>&nbsp;ACCOUNT-PROFILE:&nbsp;&nbsp;@{{this.user.username}}</p>
                
                <p class="In-titleArt"><i class="ri-github-fill"></i>&nbsp;NAME Of ART:&nbsp;&nbsp;#  {{this.art.name}}</p>
                <p class="descriptionText"><i class="ri-edit-2-fill"></i>&nbsp;{{this.art.desc}}</p>
            </div>
        </div>
    </section>
</body>
</template>

<style scoped>

#errorMsgFollow {
    z-index: 10;
    color: red;
    position: absolute;
    width: 115px;
    background-color: rgba(0, 0, 0, 0.76);
    border-radius: 10px;
    padding: 5px;
    margin-top: 5px;
    opacity: 0;
    transition: 0.5s;
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
    z-index: 5;
}

.images {
   width: 1063px;
    height:658px;
    margin-top: 20px;
    margin-left: 100px;
}

.imageArt {
   width: 1063px;
    position: relative;
    height: fit-content;
    object-fit: contain;
   height:658px;
   margin-top: 40px;

}

.titleArt {
    /* width: 130%; */
    /* margin-left: 50%; */
    margin-left: 30px;
    font-size: 24px;
    color: rgb(68, 67, 67);
    margin-top: -15px;
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
    height: 180px;
    position: relative;
    display:flex;
    justify-content: space-between;
    padding-top: 20px;
    margin-bottom: -50px;
    align-items:center;
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
.ri-close-circle-line:hover{
    color:blue;
   
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
.ri-folder-open-fill:hover{
   color:blue;
    
}
.user-like{
    margin-top: 70px;
}
.iLikeArt {
    color:white;
    font-size: 30px;
}
.iLikeArt:hover{
    color:blue;
    cursor: pointer;

}
/*---description---*/

.description{
    margin-top: 10%;
    width: 100%;
    height:200px;
    position: relative;
}
.description h2{
   color:rgb(201, 191, 191);
    font-size:30px;
    margin-left: 100px;
    font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
    width:160px;
    margin-left: 100px;
}
.description .paragraph{
    color:gray;
    font-size:16px;
    margin-left: 100px;
    font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
}
/*------3 icons in description----*/
.ri-message-2-fill{
    color:#fff;
    font-size: 17px;
}
.ri-account-circle-fill{
   color:#fff;
    font-size: 20px;
}
.ri-github-fill{
     color:#fff;
    font-size: 20px;
}
.ri-edit-2-fill{
    color:#fff;
    font-size: 20px;
}

</style>