<script>
import userApi from "@/apis/user";
import authApi from "@/apis/auth";
import artApi from "@/apis/arts";
import followApi from "@/apis/follow";
export default {
    data() {
        return {
        dataUpdateUser: {
            firstName: '',
            lastName: '',
            occupation: '',
            aboutMeTitle: '',
            aboutMeDesc: '',
        },
        username: '',
        follower: '',
        following: '',
        profilPic: null,
        error: '',
        successMsgImage: '',
        successMsgUpdate: '',
        }
    },
    async mounted() {
        const me = await authApi.getMe();
        if (me) {
            this.dataUpdateUser.firstName = me.data.data.firstName;
            this.dataUpdateUser.lastName = me.data.data.lastName;
            this.dataUpdateUser.occupation = me.data.data.occupation;
            this.dataUpdateUser.aboutMeTitle = me.data.data.aboutMeTitle;
            this.dataUpdateUser.aboutMeDesc = me.data.data.aboutMeDesc;
            this.username = me.data.data.username;
            this.follower = await followApi.howManyFollower(me.data.data._id);
            this.following = await followApi.howManyFollowing(me.data.data._id);
            if (me.data.data.imageProfil == "" || me.data.data.imageProfil == undefined) {
                this.profilPic = "/src/assets/Images/profile/noProfilePic.webp"
            } else {
                this.profilPic= 'http://localhost:3001/profileImage/' + me.data.data.imageProfil;
            }
        } 
    },
    methods:{
        async submitUpdate(){
            const updatedUser = await userApi.updateUser(this.dataUpdateUser);
            if (!updatedUser.data.success) {
                this.error = updatedUser.data.error;
            } else {
                this.error = '';
                this.successMsgUpdate = 'Informations successfully updated';
                document.getElementById('successMsgUpdate').style.opacity = 1
                setTimeout(this.successMsgDisapear, 5000);
                setTimeout(this.successMsgReset, 6000);
            }
        },
        async submitNewProfilPic(event){
            if (event.target.files.length === 0) {
                e.preventDefault();
                this.error = "No image to upload"
            } else {
                const updatedImage = await userApi.updateImage(event.target.files[0]);
                if (!updatedImage.data.success) {
                    this.error = updatedUser.data.error;
                } else {
                    this.error = '';
                    const me = await authApi.getMe();
                    this.profilPic= 'http://localhost:3001/profileImage/' + me.data.data.imageProfil;
                    this.successMsgImage = 'Profile picture successfully updated';
                    document.getElementById('successMsgImage').style.opacity = 1
                    setTimeout(this.successMsgDisapear, 5000);
                    setTimeout(this.successMsgReset, 6000);
                }
            }
        },
        successMsgDisapear() {
            document.getElementById('successMsgImage').style.opacity = 0;
            document.getElementById('successMsgUpdate').style.opacity = 0;
        },
        successMsgReset () {
            this.successMsgImage = '';
            this.successMsgUpdate= '';
        }
    }
}
</script>

<template>
    <section class="profile">
     
        <div class="row">
            <div class="p-col1">
                <img class="imgProfil" :src="profilPic">
                <font-awesome-icon class="faUpload" :icon="['fas', 'upload']"/>
                <button class="buttonUploadImage" onclick="document.getElementById('getImage').click()">Change profile picture</button>
                <input type="file" id="getImage" accept="image/*" placeholder="Upload" @change="submitNewProfilPic" style="display:none"/>
                <p class="usernameProfil">{{this.username}}</p>
                <div class="follow">
                    <p>Follower&nbsp;&nbsp;{{this.follower}}&nbsp;|&nbsp;Following&nbsp;&nbsp;{{this.following}}</p>
                </div>
                <p id="successMsgImage">{{this.successMsgImage}}</p>
            </div>
            <div class="p-col2">
                <form method="post" @submit.prevent="submitUpdate">
                    <div class="p-table1">
                        <div class="p-header">
                            <h4>BASIC INFORMATION</h4>
                        </div>
                        <div class="row1">
                            <div class="f-name">
                                <label>First name</label>
                                <input type="text" v-model="dataUpdateUser.firstName" :v-bind="dataUpdateUser.firstName">
                            </div>
                            <div class="l-name">
                                <label>Last name</label>
                                <input type="text" v-model="dataUpdateUser.lastName" :v-bind="dataUpdateUser.lastName">
                            </div>
                        </div>
                        <div class="row2">
                            <div class="occupation">
                                <label>Occupation</label>
                                <textarea id="occupation" v-model="dataUpdateUser.occupation" :v-bind="dataUpdateUser.occupation"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="p-table2">
                        <div class="p-header">
                            <h4>ABOUT ME</h4>
                        </div>
                        <div class="row1">
                            <div class="title">
                                <label>Title</label>
                                <input type="text" v-model="dataUpdateUser.aboutMeTitle" :v-bind="dataUpdateUser.aboutMeTitle">
                            </div>
                        </div>

                        <div class="row2">
                            <div class="description">
                                <label>Description</label>
                                <textarea v-model="dataUpdateUser.aboutMeDesc" :v-bind="dataUpdateUser.aboutMeDesc"></textarea>
                            </div>
                        </div>
                    </div>
                    <button class="updateButton" type="submit">Update informations</button>
                    <p class="errorMsg">{{error}}</p>
                    <p id="successMsgUpdate">{{this.successMsgUpdate}}</p>
                </form>
            </div>
        </div>
    </section>
</template>

<style scoped>
#successMsgImage {
    display: block;
    font-weight: 600;
    opacity: 0;
    text-align: center;
    color: green;
    margin-top: 5px;
    margin-bottom: 0%;
    transition: 1s;
}

#successMsgUpdate {
    display: block;
    font-weight: 600;
    opacity: 0;
    text-align: center;
    color: green;
    margin-top: 5px;
    margin-bottom: 0%;
    margin-bottom: 20px;
    transition: 1s;
}

label {
    color: black;
}

.updateButton {
    background-color: black;
    border-color: grey;
    border-radius: 10px;
    border-style: solid;
    color: white;
    width: 100%;
    height: 40px;
    font-size: 20px; 
}

.buttonUploadImage {
    width: 150px;
    border-radius: 8px;
    margin-left: 10px;
    background-color: white;
}

.faUpload {
    color: black;
    margin-left: 110px;
    height: 16px;
}

.imgProfil {
    height: 400px;
    width: 400px;
    margin-top:6px;
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
    margin-right:100px;
}
.p-col1{
   
    background:#fff;
    margin-top:5%;
    margin-bottom:5%;
    padding: 20px 20px;
    box-sizing: border-box;
    height:570px;
    width: 456px;
    margin-right: 50px;
    align-items: center;
   
    transition: 0.5s;
     
}
.p-col1:hover{
    box-shadow:0 0 20px 0px rgba(0,0,0,0.1);

}

.p-col1 .fa{
    
    display:block;
    color:blue;
    margin-left:170px;
    margin-top:10px;
   
    font-size:18px ;   
}
.p-col1 .follow{
    text-align: center;
    color: black;
}
.p-table1{
   
    background:#fff;
    margin-top:2%;
    width: 769px;
    height: 312px;
    margin-bottom:5%;
   
    box-sizing: border-box;
    transition: 0.5s;
}
.p-table1:hover{
    box-shadow:0 0 20px 0px rgba(0,0,0,0.2);

}
.row1{
    display:flex;
    justify-content:flex-start;
     margin-left: 0px;
     margin-right:0px;


}
.p-table1 .p-header{
    background-color:dimgray;
    
}
.p-table1 .p-header h4{
 padding:12px 12px;
    
}
.f-name{
    margin-top:30px;
    margin-left: 66px;
    margin-right:65px;

}
.f-name input{
    margin-top:10px;
    width:250px;
    height:30px;
}
.l-name{
    margin-top:30px;
    margin-left: 65px;
    margin-right:65px;
   
}
.l-name input{
    margin-top:10px;
    width:250px;
    height:30px;
}
.occupation{
    margin-top:30px;
    margin-left:65px;
}
.occupation textarea{
    margin-top:30px;
    width:635px;
    height:60px;
}


.p-table2{
   
    background:#fff;
    margin-top:2%;
    width: 769px;
    height: 312px;
    margin-bottom:5%;
    transition: 0.5s;
    box-sizing: border-box;
   
   
}
.p-table2:hover{
    box-shadow:0 0 20px 0px rgba(0,0,0,0.2);

}
.p-table2 .p-header{
    background-color:dimgray;
    
}
.p-table2 .p-header h4{
 padding:12px 12px;
    
}
.title{
    margin-top:30px;
    margin-left: 65px;
    margin-right:65px;  
}
.title input{
    margin-top:10px;
    width:635px;
    height:30px;
}

.description{
    margin-top:30px;
    margin-left:65px;
}
.description textarea{
    margin-top:30px;
    width:635px;
    height:60px;
}

h3{
    text-align: center;
    font-weight: 600;
    margin: 10px 0;
    font-family: 'Poppins', sans-serif;
}


</style>