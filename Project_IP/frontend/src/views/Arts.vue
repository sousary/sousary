<script>
import authApi from "@/apis/auth";
import userApi from "@/apis/user";
import likesApi from "@/apis/likes";
import artsApi from "@/apis/arts";
export default {
    data() {
        return {
        error: '',
        arts: []
        }
    },
    async mounted() {
        await this.showArts();  
    },
    methods:{
        async showArts() {
            const myArts = await artsApi.getAll();
            const me = await authApi.getMe();
            this.arts = myArts.data.data;
            let cpt = 0;
            for (let art of this.arts) {
                art.image = "http://localhost:3001/artsImage/" + art.image;
                art.likes = await likesApi.likesArt(art._id);
                art.cpt = cpt;
                let userImage = await userApi.getUser(art.user);
                if (userImage.data.data.imageProfil == "" || userImage.data.data.imageProfil == undefined) {
                    art.imageProfilUser = "/src/assets/Images/profile/noProfilePic.webp"
                } else {
                    art.imageProfilUser= 'http://localhost:3001/profileImage/' + userImage.data.data.imageProfil
                }
                art.username = userImage.data.data.username;
                
                if (me) {
                    let liked = await likesApi.isLiked(art._id, me.data.data._id)
                    if (liked.data.data) {
                        document.getElementsByClassName('iLike')[cpt].style.color = "grey";
                    }
                }
                cpt++;
            }
        },
        async artClicked (art) {
            await artsApi.oneMoreViewOnArt(art._id);
            art.views++;
            this.$router.push({name: "art", params: {id: art._id}});
        },
        async iLikeButton (art) {
            const me = await authApi.getMe();
            if (me) {
                const liked = await likesApi.isLiked(art._id, me.data.data._id)
                if (!liked.data.data) {
                    await likesApi.create(art._id, me.data.data._id);
                    document.getElementsByClassName('iLike')[art.cpt].style.color = "grey";
                    art.likes++;
                } else {
                    await likesApi.delete(art._id, me.data.data._id);
                    document.getElementsByClassName('iLike')[art.cpt].style.color = "black";
                    art.likes--;
                }
            } else {
                clearTimeout();
                document.getElementsByClassName('errorMsg')[art.cpt].style.opacity = "1";
                setTimeout(this.youMustLoginDispear, 3000, art.cpt);
            }
        },
        youMustLoginDispear(nb) {
            document.getElementsByClassName('errorMsg')[nb].style.opacity = "0";
        },
        async userClicked(user) {
            this.$router.push({name: "user", params: {id: user}});
        }
    }
}
</script>

<template>
    <section class="profile">
        <div class="art-header">  
            <h2>Best OF Art</h2>
        </div>
        <div class="rows">
            <div v-for="art in this.arts" class="pics" :key="art._id">
                <img class="imgArts" @click="artClicked(art)" :src="art.image">
                <div class="position">
                    <img class="profilPicture" :title="art.username" :src="art.imageProfilUser" @click="userClicked(art.user)">
                    <div class="names">{{art.name}}</div>
                    <div class="views">
                        <font-awesome-icon class="iLike" :icon="['fas', 'thumbs-up']" @click="iLikeButton(art)"/> &nbsp;{{art.likes}}&nbsp;&nbsp;&nbsp;
                        <font-awesome-icon :icon="['fas', 'eye']"/>&nbsp;{{art.views}}
                    </div>
                </div>
                <p class="errorMsg">You must login to like</p>
                <br>
            </div>            
        </div>        
    </section>
</template>

<style scoped>
@import '@/assets/Style/jquery.flipster.min.css';

#ux-ui
{
    margin-top: -160px;
    margin-left: 140px;  
}
#nft
{
    margin-top: -160px;
    margin-left: 150px;  
}
#graphic
{
    margin-top: -160px;
    margin-left: 70px;  
}


#contant_art
{
    color: black;
    text-align: center;
    padding-top: 170px;
    font-size: 15px;
    margin-top:0;
}

.name_type
{
    
    color: rgb(255, 255, 255);
    margin-top: -160px;
    margin-left: 110px;
    position: relative;
    font-size: 20px;
    -webkit-text-stroke-width: 2px;
    -webkit-text-stroke-color: black;
}
.errorMsg {
    opacity: 0;
    position: absolute;
    margin-top: -62px;
    margin-left: 40px;
    border-radius: 10px;
    padding: 2px;
    background-color: white;
    transition: 0.5s;
    z-index: 5;
}

html {
    background-color: white;
}

.profile{
    background-color: white;
   
}

.rows {
    /* margin-top: 50px; */
    width: fit-content;
    /* background-color:#fff; */
    justify-content:center;
    /* align-items:flex-start; */
    display:flex;
    /* flex-wrap: wrap; */
    flex-flow: row wrap;
    margin-right: 50px;
    margin-left: 30px;

}
/*----image---*/
.pics{
    margin-top:10px;
    margin-bottom:10px;
    margin-left:20px;
    /* width: 257px; */
    height:291px;
    background-color:rgb(233, 233, 233);
    border: 3px solid rgb(187, 186, 186);

}
.pics:hover{
    box-shadow:0 0 50px 0px rgba(17, 15, 15, 0.4);  
    
}
 
.imgArts {
    /* width: 250px; */
    height:250px;
    position: relative;
   
}

.profilPicture {
    width: 30px;
    height: 30px;
    margin-left: 2px;
    margin-bottom: 4px;
    border-radius: 8px;
    background-color: black;
    object-fit: cover;
    cursor: pointer;
}

.position{
    color:#000;
    font-size:14px;
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
        /*------icon hover------*/

.iLike:hover{
    color:blue;
    font-size: 16px;
}

/* PIF*/

body
{
   
    font-family: sans-serif;
    position: relative;

}
.art-header{
    height:220px;
    width: 100%;
    text-align: center;
    background-image: linear-gradient(rgba(80, 95, 153, 0.3),rgba(4,9,30,0.3)),url(src/assets/Images/header/R4.jpg);
    background-position: center;
    background-size: cover;
    position: relative;
  
}

.art-header h2{
    color:#fff;
    font-size:60px;
   padding-top: 80px;
    font-family: Cinzel Decorative; 
    font-weight: bold;


   
}

.menu_bar{
    width: 100%;
    height: 80px;
    background-color:white;
    background-size: cover;
    background-position: center;
    box-shadow: 0 0 2px 0 black;
    z-index: 5;
    margin-top: 0;
    
}
.logo{
    margin-left: 20px;
    width: 120px;
    color:black;
    cursor: pointer;
    position: absolute;
}
.list
{
    position: absolute;
    right: 0%;
}
.list ul
{
    list-style: none;
    display: flex;
    
}
.list li
{
    display: inline;
    position: relative;
}
.list ul a
{
    text-decoration: none; 
    font-size: 20px;
    
    color: rgb(1, 1, 1);  
    padding: 10px 20px;
    border-radius: 10px;
   
}
.list ul li:hover a{
    color: white;
    background-color: blue;
}

.display{
	
	display: flex;
	justify-content: center;
	align-items: center;
}
.wrapper{
    min-height: 100vh;
    position: absolute;
    margin: 100px 50px;
    
}

.search_box{
    width: 600px;
    height: 20px;
	background:white;
	position: relative;
	padding: 10px;
    
	border-radius: 50px;
	display: flex;
    box-shadow: 0 0 2px 0 black;
}

.search_box .search_btn{
	width: 25px;
	height: 25px;
	border-radius: 50%;
	background:white;
	color: rgb(18, 16, 16);
	margin-right: 15px;
    margin-top: 2px;
	cursor: pointer;
}


.search_small
{
    width: 5px;
}

.search_box .input_search{
    width: 300px; 
	height: 1px;
	outline: none;
	border: 0;
	background:white;
	border-radius: 50px;
	padding: 10px 5px 10px 1px;
	color: rgb(15, 1, 1);
    box-shadow: 0 0 2px 0 rgba(14, 0, 0, 0.819);
}

::placeholder {
  color: rgb(8, 1, 1);
}

::-webkit-input-placeholder {
  color: rgb(11, 10, 10);
}

:-ms-input-placeholder {
  color: #fff;
}
.types
{
    width: 100px;
    height: 30px;
    margin-top: 100px;
    margin-left: 1700px;
    position: absolute;
}

.type_contant ul
{
    list-style: none;
    margin-left: 300px;
    margin-top: -30px;
}
.type_contant ul li
{
    display: inline;
    position: relative;
}

.type_contant ul li a{
    text-decoration: none;
    color: black;
    padding: 7px;
    font-size: 15px;
    border-radius: 10px;
}
.type_contant ul li:hover a{
    color: white;
    background-color: rgb(35, 35, 225);
}
.sort
{
    width: 200px;
    margin-top: 80px;
    margin-left: 1750px;
    position: absolute;

}
.sort ul
{
    list-style: none;
    
}
.sort ul li
{
    display:block;
    margin-left: 30px;
    margin-top: 10px;
    position: relative;
}
.sort ul li a{
    color: black;
    text-decoration: none;
    
    padding: 2px 20px;
 }
.sort ul li:hover a{
    color: white;
    background-color: rgb(184, 184, 198);
}
.sub_recommended
{
    display: none;
    position: absolute;
    background-color:  rgb(184, 184, 198);
    border-radius: 5px;
}
.sub_recommended ul {
    width: 150px;
    text-align: left;
}
.sub_recommended ul li{
    padding: 2px;
    margin-left: -20%;
}
.sort ul li:hover .sub_recommended{
    display: block;
}
.sub_recommended ul li:hover a{
    color:blue;
}
p
{
    color: #06040f;
}
#contant_art
{
    color: black;
    text-align: center;
    padding-top: 170px;
    font-size: 15px;
    margin-top:0;
}
.type-img
{
    display: flex;
  
} 
.img
{
    margin: 10px;
    border: 3px solid rgb(195, 192, 192);
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
}
.img:hover{
    transform: scale(1.1); /* (150% zoom - Note: if the zoom is too large, it will go outside of the viewport) */
    border: 3px solid rgb(255, 255, 255);
  }
.name_image
{
    position: absolute;
    font-size: 30px;
    color:white;
}

.fa{
    display:block;
    color: #fff;
    margin: 10px ;
    font-size:22px ;
    cursor: pointer;
}  
.combo_box
{
    width: 100px;
    height: 30px;
    margin-top: 5px;
   margin-left: 600px;
}
.web_design h1
{
  text-align: center;
  font-size: 50px;
}
.web_design p
{
  text-align: center;
 font-size: 20px;
}
.wrapper_1
{
	min-height: 100vh;
    position: absolute;
    padding: 30px 750px;
    margin-left: 100px;
}
.search_box_1{
    width: 300px;
    height:20px;
	background:white;
	position: relative;
	padding: 10px;
    margin-left:10px ;
	border-radius: 50px;
	display: flex;
    box-shadow: 0 0 2px 0 black;
    display: flex;
    justify-content: center;
    
}
.search_box_1 .input_search{
    width: 270px; 
	height: 1px;
	outline: none;
	border: 0;
	background:white;
	border-radius: 50px;
	padding: 10px 2px;
	color: rgb(15, 1, 1);
    box-shadow: 0 0 2px 0 rgba(14, 0, 0, 0.819);
    margin-top: -500px;
    
}
.search_box_1 .search_btn{
	width: 25px;
	height: 25px;
	border-radius: 50%;
	background:white;
	color: rgb(18, 16, 16);
	margin-right: 15px;
    margin-top: -3px;
	cursor: pointer;
}
.color-picker{
    padding:10px 5px;
    margin-left: 500px;
   
}
#colorPicker{
    width: 80px;
    border-radius: 10px;
    margin-left: 600px;
    margin-top: 13px;
}


#category
{
    position: absolute;

}
#color
{
    position: absolute;
    margin-top:25px;
    margin-left: 1050px;
    font-size: 18px;
}

.display-all
{
   
    margin-top: 100px;
    margin-left: -10px;
    
}
.hero{
    margin-top:-80px;
    width: 100%;
    height: 100vh;
    overflow: hidden;

}

.hero .carousel{
    width: 300px;
    margin: 100px auto 0;
    overflow:visible;
    display: inline-flex
}
 .carousel ul
 {
    list-style: none;

 }
 
 .carousel li
 {
    display: inline;
 }
 .carousel ul a {
    text-decoration: none;
 }
.carousel img{
    width:350px;
    height:220px;
    border: 4px solid rgb(255, 255, 255);
    display: inline-flex;
    border: 2px solid rgb(85, 136, 8);
    margin-left: 3px;
   
}
.cate_col
{
    margin-top: -690px;
    margin-left: 120px;
}

.name_type
{
    
    color: rgb(255, 255, 255);
    margin-top: -160px;
    margin-left: 110px;
    position: relative;
    font-size: 20px;
    -webkit-text-stroke-width: 2px;
    -webkit-text-stroke-color: black;
}

#ux-ui
{
    margin-top: -160px;
    margin-left: 140px;  
}
#nft
{
    margin-top: -160px;
    margin-left: 150px;  
}
#graphic
{
    margin-top: -160px;
    margin-left: 70px;  
}





</style>