<script>
import likesApi from "@/apis/likes";
import authApi from "@/apis/auth";
import followApi from "@/apis/follow";
import artsApi from "@/apis/arts";
export default {
    data() {
        return {
        dataUpdateUser: {
            firstName: '',
            lastName: '',
            aboutMeDesc: '',
            username: '',
            follower: '',
            following: '',
        },
        dataNewArt: {
            image: '',
            name: '',
            desc: '',
        },
        profilPic: null,
        error: '',
        artsOfUser: [],
        }
    },
    async mounted() {
        const me = await authApi.getMe();
        if (me) {
            this.dataUpdateUser.firstName = me.data.data.firstName;
            this.dataUpdateUser.lastName = me.data.data.lastName;
            this.dataUpdateUser.aboutMeDesc = me.data.data.aboutMeDesc;
            this.dataUpdateUser.username = me.data.data.username;
            this.dataUpdateUser.follower = await followApi.howManyFollower(me.data.data._id);
            this.dataUpdateUser.following = await followApi.howManyFollowing(me.data.data._id);
            if (me.data.data.imageProfil == "" || me.data.data.imageProfil == undefined) {
                this.profilPic = "/src/assets/Images/profile/noProfilePic.webp"
            } else {
                this.profilPic= 'http://localhost:3001/profileImage/' + me.data.data.imageProfil;
            }
            await this.showArts();  
        }
    },
    methods:{
        uploadNewArt () {
            document.getElementById('newArt').style.display = "block";
        },
        cancelNewArt () {
            this.error = '';
            document.getElementById('newArt').style.display = "none";
        },
        handleFileUpload( event ) {
            this.dataNewArt.image = event.target.files[0];
        },
        async submitNewArt () {
            const artImported = await artsApi.create(this.dataNewArt.image, this.dataNewArt.name, this.dataNewArt.desc);
            if (artImported.data.success) {
                await this.showArts();  
                this.dataNewArt.image = '';
                this.dataNewArt.name = '';
                this.dataNewArt.desc = '';
                this.error = '';
                document.getElementById('newArt').style.display = "none";
            } else {
                console.log(artImported);
                this.error = 'Failed to import';
            }
        },
        async showArts() {
            const myArts = await artsApi.getMyArts();
            this.artsOfUser = myArts.data.data;
            for (let art of this.artsOfUser) {
                art.image = "http://localhost:3001/artsImage/" + art.image;
                art.likes = await likesApi.likesArt(art._id);
            }
            console.log(this.artsOfUser)
        },
        artClicked(art) {
            this.$router.push({name: "artUpdate", params: {id: art._id}});
        }
    }
}
</script>

<template>
    <section class="profile">
        <link href="https://cdn.jsdelivr.net/npm/remixicon@2.2.0/fonts/remixicon.css" rel="stylesheet">
        <div class="row">
            <div class="p-col1">
                <a href="profile-update">
                    <font-awesome-icon class="faEdit" :icon="['fas', 'edit']"/>
                </a>
                <img class="imgProfil" :src="profilPic">
                <p class="namesProfil">{{this.dataUpdateUser.firstName}} {{this.dataUpdateUser.lastName}}</p>
                
                <p class="usernameProfil">{{this.dataUpdateUser.username}}</p>
                <p class="description">{{this.dataUpdateUser.aboutMeDesc}}</p>
                <div class="follow">
                    <p>Follower&nbsp;&nbsp;{{this.dataUpdateUser.follower}}&nbsp;|&nbsp;Following&nbsp;&nbsp;{{this.dataUpdateUser.following}}</p>
                </div>
            </div>
            <!--<div  class="buttonNewArt"  @click="this.uploadNewArt">
                <font-awesome-icon class="faUpload" :icon="['fas', 'upload']"/>
                <p class="pUpload"> Upload new arts </p>
            </div>--->
            <div id="newArt">
                <form method="post" @submit.prevent="submitNewArt">
                    <h2> New Art</h2>
                    <label>Image: </label>
                    <input class="inputsNewArt" id="uploadNewArt" @change="handleFileUpload( $event )" type="file">
                    <br>
                    <label>Name: </label>
                    <input class="inputsNewArt" id="nameNewArt" v-model="dataNewArt.name" type="text" placeholder="Name of the Art">
                    <br>
                    <label>Description: </label>
                    <textarea class="inputsNewArt" id="descNewArt" v-model="dataNewArt.desc" type="text" placeholder="Description of the Art"></textarea>
                    <br>
                    <div class="buttons">
                        <button class="newArtButton" id="uploadNewArtButton" type="submit">Upload</button>
                        <button class="newArtButton" id="cancelNewArtButton" @click.prevent="this.cancelNewArt">Cancel</button>
                    </div>
                    <p class="errorMsg">{{error}}</p>
                </form>
            </div> 
            <div class="rows">
                 <div class="create">
                    <div class="buttonNewArt" @click="this.uploadNewArt"><i class="ri-add-circle-fill"></i> </div>
                    <div class="word-center">Create Project</div>
              <!--  <button class="buttonNewArt" @click="this.uploadNewArt"> Upload new arts </button>-->

                </div>
                <div v-for="art in this.artsOfUser" class="pics" :key="art._id">

                    <img class="imgArts" :src='art.image' @click="artClicked(art)">
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
.word-center {
    margin-top: 10px;
    color: rgb(129, 129, 129);
}

#uploadNewArtButton{
    background-color: black;
    color: white
}

#cancelNewArtButton{
    margin-left: 30px;
    background-color: rgba(255, 0, 0);
    color: white;
}
.newArtButton {
    margin-top: 10px;
    border-radius: 10px;
    font-size: 15px;
    
}

.buttons{
    margin-left: 50%;
    transform: translateX(-50%);
}

#uploadNewArt {
    margin-left: 38px;
    color: black;
}

#nameNewArt {
    margin-left: 40px;
}

.inputsNewArt {
    margin-left: 5px;
}

h2 {
    color: black;
    text-align: center;
}
#newArt {
    display: none;
    position: absolute;
    background-color: white;
    z-index: 4;
    padding: 20px;
    border-radius: 10px;
    box-shadow:0 0 10000px 100000px rgba(0,0,0,0.7);
    margin-top: 50px;
    margin-left: 40%;
}
.buttonNewArt {
    width: 60px;
    height: 60px;
    background-color: rgb(155, 152, 152);
    border-radius:100%;
    margin-left: 95px;
    margin-top:80px;
    align-items: center;
    display:flex; 
    justify-content: center;
}

.pUpload{
    margin-top: 7px;
    margin-left: 6px;
    
}

.faUpload{
    margin-top: 11px;
    margin-left: 10px;
}

h1 {
    color: black;
}

label {
    color: black;
    position: block;
}

.faEdit {
   position: absolute;
    color: blue;
    
    font-size: 35px;
    margin-left: 0px;
    margin-top: 0px;
}
.faEdit:hover{
    color:grey;
}

.description {
    text-align: center;
    color: grey;
    font-size: 20px;
}


.imgProfil {
    height: 220px;
    width: 220px;
    border-radius: 100%;
    margin-top: 50px;
    left: 50%;
    transform: translateX(-50%);
    border:3px solid rgb(114, 106, 106);
    object-fit: cover;
    /* border-radius: 100%; */
}
.namesProfil {
    font-family:Georgia, 'Times New Roman', Times, serif;
    font-size:14px;
    color:black;

}

.profile{
    background-color: #f4f5f0;
   
}
.row{
    display:flex;
    justify-content:flex-start;
    margin-left: 100px;
    margin-right:100px;

}
.p-col1{
    background:#fff;
    margin-top: 20px;
    margin-bottom:5%;
    padding: 20px 20px;
    box-sizing: border-box;
    height:650px;
    width: 350px;
    margin-right: 0px;
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
.row{
    justify-content:flex-start;
    margin-left: 40px;
    margin-right:40px;
    height:100%;
}

/*---item feature---*/
.rows {
    margin-top: 50px;
    width: fit-content;
    /* background-color:#fff; */
   
    /* align-items:flex-start; */
    display:flex;
    /* flex-wrap: wrap; */
    flex-flow: row wrap;
}
.create{
    margin-top:10px;
    margin-bottom:10px;
    margin-left:20px;
    width: 250px;
    height:290px;
    background-color:#fff;
     align-items:center;
    text-align:center;

}
.create:hover{
     box-shadow:0 0 20px 0px rgba(0,0,0,0.2);
    
}
.ri-add-circle-fill{
    color:blue;
    font-size:24px;
}
.ri-add-circle-fill:hover{
    color:black;
    font-size:24px;
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
    /* width: 1100px; */
    height:231px;
    background-color:#fff;
    justify-content: space-between;
    align-items:flex-start;
    display:flex;
}
        /*------icon hover------*/
.ri-thumb-up-fill{
    color:#000;

}
.ri-thumb-up-fill:hover{
    color:blue;
    font-size: 16px;
}
</style>