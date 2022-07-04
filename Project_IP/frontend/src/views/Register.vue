<script>
export default {
    data() {
    return {
        dataRegister: {
            email: '',
            username: '',
            password: '',
            repeatPassword: ''
            },
        error: '',
        passwordFieldType1: "password",
        passwordFieldType2: "password"
        }
    },
    methods:{
        submitRegister(){
        this.$http.post('http://localhost:3001/auth/register', this.dataRegister, {withCredentials: true, headers: {"Content-type": "application/json",}})
             .then((res) => {
                if (res.data.success) {
                    this.$router.push({name: "login"});
                } else {
                    this.error = res.data.error;
                }
             })
             .catch((error) => {
                console.log(error);
            })
        },
        switchVisibility(which) {
            let elt = document.getElementById("seePass1");
            if (which == 1) {
                this.passwordFieldType1 = this.passwordFieldType1 === "password" ? "text" : "password";
            } else {
                this.passwordFieldType2 = this.passwordFieldType2 === "password" ? "text" : "password";
                elt = document.getElementById("seePass2");
            }
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
            <form method="post" @submit.prevent="submitRegister">
                <div class="border">
                    <div class="welcomeDiv">
                      <a href="/">
                        <img class="houseMenuImg" alt="Menu" src="../assets/Images/houseLogo.png">
                      </a>
                      <p class="welcome">Welcome !</p>
                    </div>
                    <p class="signInUp">Sign up to ARTS</p>
                    <p class="happyTo">Happy to meet you !</p>
                    <div class="form-group">
                        <label>Email</label> 
                        <input class="form-control" type="email" v-model="dataRegister.email" placeholder="Enter your email" /> 
                    </div>
                    <div class="form-group">
                        <label>Username</label> 
                        <input class="form-control" v-model="dataRegister.username" placeholder="Enter yout username" />
                    </div>
                    <div class="form-group">
                        <label>Password</label> 
                        <div class="passwordDiv">
                          <input class="form-control" :type='passwordFieldType1' v-model="dataRegister.password" placeholder="Enter your password" />
                          <img id="seePass1" class="seePass" alt="seePass" @click.prevent="switchVisibility(1)" src="/src/assets/Images/doNotSeePassword.png">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Confirm Password</label> 
                        <div class="passwordDiv">
                          <input class="form-control" :type='passwordFieldType2' v-model="dataRegister.repeatPassword" placeholder="Confirm your password" />
                          <img id="seePass2" class="seePass" alt="seePass" @click.prevent="switchVisibility(2)" src="/src/assets/Images/doNotSeePassword.png">
                        </div>
                    </div>
                    <button class="submitButton" type="submit">Sign Up</button>
                    <div id="textUnderLoginRegisterButton">
                        <p class="greyText">Already have an account ? <a id="switchRegisterLogin" href="login">Login</a></p>
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
