<script>
export default {
  data() {
    return {
      dataLogin: {
        email: '',
        password: ''
      },
      error: '',
      passwordFieldType: "password"
    }
  },
  methods:{
    submitLogin(){
      this.$http.post('http://localhost:3001/auth/login', this.dataLogin, {withCredentials: true, headers: {"Content-type": "application/json",}})
           .then((res) => {
             if (res.data.success) {
               this.$router.push({name: "main"});
             } else {
               this.error = res.data.error;
             }
           })
           .catch((error) => {
             console.log(error);
           })
    },
    switchVisibility() {
      this.passwordFieldType = this.passwordFieldType === "password" ? "text" : "password";
      let elt = document.getElementById("seePass");
      if (elt.getAttribute('src') == '/src/assets/Images/seePassword.png') {
        elt.setAttribute('src','/src/assets/Images/doNotSeePassword.png');
      } else {
        elt.setAttribute('src','/src/assets/Images/seePassword.png');
      }
    }
  }
}
</script>

<template>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href='https://fonts.googleapis.com/css?family=Cinzel Decorative' rel='stylesheet'>

    <div class="rightLeft">
        <div class="blocLeft">
            <form method="post" @submit.prevent="submitLogin">
                <div class="border">
                    <div class="welcomeDiv">
                      <a href="/">
                        <img class="houseMenuImg" alt="Menu" src="../assets/Images/houseLogo.png">
                      </a>
                      <p class="welcome">Welcome !</p>
                    </div>
                    <p class="signInUp">Sign in to ARTS</p>
                    <p class="happyTo">Happy to see you again !</p>
                    <div id="googleBtn" class="form-group">
                        <div id="insideGoogleBtn">
                            <img id="googleIcon" src="../assets/Images/login/googleLogo.png">
                            <span id="buttonText">Continue with Google</span>
                        </div>
                    </div>
                    <p id="or" class="greyText">or</p>
                    <div class="form-group">
                        <label>Email</label> 
                        <input class="form-control" type="email" v-model="dataLogin.email" placeholder="Enter your email" />
                    </div>
                    <div class="form-group">
                        <label>Password</label> 
                        <div class="passwordDiv">
                          <input class="form-control" :type='passwordFieldType' v-model="dataLogin.password" placeholder="Enter your password" />
                          <img id="seePass" class="seePass" alt="seePass" @click.prevent="switchVisibility" src="/src/assets/Images/doNotSeePassword.png">
                        </div>
                        <a id="forgotPass" href="/forgotPass">forgot password ?</a>
                    </div>
                    <button class="submitButton" type="submit">Login</button>
                    <div id="textUnderLoginRegisterButton">
                        <p class="greyText">Don't have an account ? <a id="switchRegisterLogin" href="register">Register</a></p>
                    </div>
                    <p class="errorMsg">{{error}}</p>
                </div>
            </form>
        </div>

        <div class="blocRight">
            <p class="arts">ARTS</p>
            <img class="artsLogo" src="../assets/Images/artsLogo.png">
        </div>
    </div>

    
</template>

<style>
@import "../assets/Style/CSS.css";

#googleIcon {
    display: inline-block;
    vertical-align: middle;
    width: 42px;
    height: 42px;
}

#buttonText {
    vertical-align: middle;
    padding-left: 5px;
    font-size: 14px;
    font-weight: bold;
    font-family: 'Roboto', sans-serif;
}

#googleBtn {
    background: white;
    color: #333333;
    width: 100%;
    border-radius: 5px;
    border: thin solid #000000;
    box-shadow: 1px 1px 1px grey;
    white-space: nowrap;
}

#googleBtn:hover {
    cursor: pointer;
}

#insideGoogleBtn{
    display: flex;
    align-items: center;
    justify-content: center;
}

#or{
    padding-bottom: 1%;
    padding-top: 1%;
    text-align: center;
    font-size: 18px;
}

#forgotPass {
    display: block;
    color: #666666;
    font-weight: bold;
    font-size: 12px;
    text-align: right;
    margin-right: 2px;
    
}

</style>