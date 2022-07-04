<script>
import authApi from "@/apis/auth";
import notifApi from "@/apis/notif";
import userApi from "@/apis/user";
export default {
  data() {
    return {
      isLogged: false,
      signInLogout: "sign-out",
      backgroundBanner: 'linear-gradient(rgba(80, 95, 153, 0.2),rgba(4,9,30,0.2)),url("/src/assets/Images/',
      notifs: [],
    };
  },
  async mounted() {
    if (this.$route.name == "about") {
      document.getElementsByClassName("header")[0].style.height="320px";
      document.getElementsByClassName("header")[0].style.backgroundImage=this.backgroundBanner+'mainpage/background.jpg")';
    } else if (this.$route.name == "contact" || this.$route.name == "profileUpdate" || this.$route.name == "profile" 
             || this.$route.name == "user") {
      document.getElementsByClassName("header")[0].style.height="320px";
      document.getElementsByClassName("header")[0].style.backgroundImage=this.backgroundBanner+'header/R4.jpg")';
    }else if(this.$route.name == "arts"){
      document.getElementsByClassName("header")[0].style.height="100px";
      

    }
     else {
      document.getElementsByClassName("header")[0].style.backgroundImage="";
      document.getElementsByClassName("header")[0].style.height="90px";
    }
    const me = await authApi.getMe();
    if (!me) {
      this.signInLogout= "sign-in";
    } else {
      this.isLogged = true;
      await this.requestNotifs();
    }
  },
  methods: {
    async onLogout() {
      const me = await authApi.getMe();
      if (!me) {
        this.$router.push({name: "login"});
      } else {
        await authApi.logout();
        this.signInLogout= "sign-in";
        this.isLogged = false; 
        this.$router.push({name: "main"});
        document.getElementsByClassName("header")[0].style.backgroundImage="";
        document.getElementsByClassName("header")[0].style.height="90px";
      }
    },
    notifClicked() {
      let displayStyle = document.getElementsByClassName('notifPage')[0].style.display;
      if (displayStyle == "none") {
        document.getElementsByClassName('notifPage')[0].style.display = "block";
        document.getElementById('notifHeaderIcon').style.color = "blue";
      } else {
        this.closeNotifs();
      }
    },
    closeNotifs() {
      document.getElementsByClassName('notifPage')[0].style.display = "none";
      document.getElementById('notifHeaderIcon').style.color = "black";
    },
    async deleteOneNotif(idNotif) {
      await notifApi.delete(idNotif);
      await this.requestNotifs();
    },
    async requestNotifs() {
      const me = await authApi.getMe();
      const notifsResp = await notifApi.get5LastNotifs(me.data.data._id);
      console.log(me.data.data._id)
      if (notifsResp.data.success) {
        this.notifs = notifsResp.data.data;
        for (let notif of this.notifs) {
          let user = await userApi.getUser(notif.otherUser);
          notif.username = user.data.data.username;
        }
      } 
      if (this.notifs.length == 0) {
        this.notifs.push({type: "nothing"});
      }
    }
  }
}
</script>

<template>
  <head>
    <link href="https://fonts.googleapis.com/css2?family=Cinzel+Decorative&display=swap" rel="stylesheet">
  </head>
  <div class="notifPage">
    <font-awesome-icon id="closeNotifs" @click="closeNotifs" :icon="['fas', 'xmark']"/>
    <p class="notifTitle">Notifications: </p>
    <div v-for="notif in this.notifs" class="notif" :key="notif._id">
      <div v-if="notif.type == 'newFollower'">
        <font-awesome-icon class="closeOneNotif" v-on:click="deleteOneNotif(notif._id, notif.cpt)" :icon="['fas', 'xmark']"/>
        <p class="textNotif"> <a class="linkOtherUser" :href="'/user/'+notif.otherUser">{{notif.username}}</a> is now following you ! </p>
      </div>
      <div v-if="notif.type == 'newArt'">
        <font-awesome-icon class="closeOneNotif" v-on:click="deleteOneNotif(notif._id, notif.cpt)" :icon="['fas', 'xmark']"/>
        <p class="textNotif"> <a class="linkOtherUser" :href="'/user/'+notif.otherUser">{{notif.username}}</a> uploaded a new <a class="linkOtherUser" :href="'/art/'+notif.newArt">Art</a> ! </p>
      </div>
      <div v-if="notif.type == 'nothing'">
        <p class="textNoNotif"> No notification ! </p>
      </div>
    </div>
  </div>
  <section class="header">
    <div class="navbar">
        <div class="logo">
          <a href="/">
            <img class="logoHeader" src="/src/assets/Images/artsLogo.png">
          </a>
          <a href="/" class="artsHeader">Arts</a>
        </div>
        <ul>
            <li><a class="milieuNavbar" href="/">Home</a></li>
            <li><a class="milieuNavbar" href="/about">About</a></li>
            <li><a class="milieuNavbar" href="/arts">Discover</a></li>
            <li><a class="milieuNavbar" href="/contact">Contact</a></li>
        </ul>
        <ul>
            <li v-if="isLogged"><font-awesome-icon id="notifHeaderIcon" @click="notifClicked" :icon="['fas', 'bell']"/></li>
            <li v-if="isLogged"><a href="/profile"><font-awesome-icon :icon="['fas', 'user']"/></a></li>
            <!-- <li><a href="#"><font-awesome-icon :icon="['fas', 'user']"/></a></li> -->
            <li><font-awesome-icon class="menuLogout" v-on:click="onLogout()" :icon="['fas', this.signInLogout]" /></li>
        </ul>
    </div>
  </section>
  
</template> 

<style scoped>
.milieuNavbar{
  font-weight: bold;
}
.header {
    height: 300px;
    background-image: linear-gradient(rgba(80, 95, 153, 0.2),rgba(4,9,30,0.2)),url(../assets/Images/mainpage/background.jpg);
    background-position: center;
    background-size: cover;
    position: relative;
}

.navbar{
    height: 100px;
    width: 100%;
    padding-top: 10px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: relative;
    z-index: 3;
    padding-bottom: 20px;
    border-bottom: 2px solid #fff;
    background-color: #fff;
    position: fixed;
}

.navbar ul li{
    list-style: none;
    display: inline-block;
    margin: 0 20px;
    position: relative;
    
}
.navbar ul li a{
    text-decoration: solid;
    color: black;
    font-size: 2vh;
    
}
.navbar ul li::after{
    content: '';
    height: 3px;
    width: 0;
    background: #f44336;
    position: absolute;
    left: 0;
    bottom: -3px;
    transition: 0.5s;

}
.navbar ul li:hover::after{
    width: 100%;
}

.menuLogout{
  color: black;
  font-size: 2vh;
  cursor: pointer;
}

#notifHeaderIcon {
  color: black;
  font-size: 2vh;
  cursor: pointer;
}

.artsHeader {
  font-family: Cinzel Decorative; 
  margin-left: 3.5em;
  font-size: 40px;
  color: black;
  font-weight: bold;
  margin-top: 0;
  text-decoration: none;
}

.logoHeader {
  position: absolute;
  height: 100px;
  margin-top: -15px;

}

/* Notifications */

.notif {
    background-color: white;
    height: 40px;
    border-bottom: 1px solid grey;
}

.notifPage {
    background-color: white;
    width: 280px;
    padding: 15px;
    border-radius: 15px;
    position: absolute;
    z-index: 10;
    right: 0;
    margin-right: 2px;
    margin-top: 102px;
    display: none;
}

.linkOtherUser {
    color: black;
    text-decoration: none;
    font-weight: bold;
}

.textNotif {
    color: black;
    padding: 10px;
    margin-right: 15px;
    text-align: center;
}

.textNoNotif {
    color: black;
    padding: 10px;
    text-align: center;
}

#closeNotifs {
    width: 20px;
    height: 20px;
    position: absolute;
    color: black;
    margin-left: 230px;
    z-index: 5;
}

.closeOneNotif {
    position: absolute;
    margin-left: 235px;
    margin-top: 13px;
    color: red;
    z-index: 5;
    cursor: pointer;
}

.notifTitle {
    color: black;
    text-align: center;
    margin-bottom: 5px;
    font-size: 20px;
}

#closeNotifs:hover {
    cursor: pointer;
}
</style>