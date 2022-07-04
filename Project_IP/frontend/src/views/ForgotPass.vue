<script>
import * as bcrypt from 'bcryptjs';

export default {
    data() {
    return {
        email: '',
        code: '',
        hashCode: '',
        newPass: '',
        error: '',
        passwordFieldType: "password"
        }
    },
    methods:{
        submitEmail() {
            this.$http.post('http://localhost:3001/auth/sendMail', {code: this.code, email: this.email, newPass: this.newPass}, {withCredentials: true, headers: {"Content-type": "application/json",}})
             .then((res) => {
                if (res.data.success) {
                    this.error = "";
                    this.hashCode = res.data.hashCode;
                    document.getElementById("formCode").style.display = "block";
                } else {
                    this.error = res.data.error;
                }
             })
             .catch((error) => {
                console.log(error);
            })
        },
        submitCode() {
            if (bcrypt.compareSync(this.code, this.hashCode)) {
                this.error = "";
                document.getElementById("formPass").style.display = "block";
            } else {
                this.error = "Code false"
            }
        },
        submitPass() {
            this.$http.post('http://localhost:3001/auth/newPassword', {code: this.code, email: this.email, newPass: this.newPass}, {withCredentials: true, headers: {"Content-type": "application/json",}})
             .then((res) => {
                 console.log(res);
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
            <div class="border">
                <div class="welcomeDiv">
                    <a href="/">
                    <img class="houseMenuImg" alt="Menu" src="../assets/Images/houseLogo.png">
                    </a>
                    <p class="welcome">Welcome !</p>
                </div>
                <p class="signInUp">Password forgotten</p>
                <p class="happyTo">Don't panic an email will be sent to you to reset your password</p>
                <form method="post" @submit.prevent="submitEmail">
                    <div class="form-group">
                        <label>Email</label> 
                        <input class="form-control" type="email" v-model="email" placeholder="Enter your email" /> 
                    </div>
                    <button class="submitButton" id="sendEmail" type="submit">Send the email</button>
                </form>
                <form id="formCode" method="post" @submit.prevent="submitCode">
                    <div class="form-group">
                        <label class="labelSpace">Code</label> 
                        <input class="form-control" v-model="code" placeholder="Enter the code sended by email" />
                    </div>
                    <button class="submitButton" id="codeButton" type="submit">Verify code</button>
                </form>
                <form id="formPass" method="post" @submit.prevent="submitPass">
                    <div class="form-group">
                        <label class="labelSpace">New Password</label> 
                        <div class="passwordDiv">
                          <input class="form-control" :type='passwordFieldType' v-model="newPass" placeholder="Enter a new password" />
                          <img id="seePass" class="seePass" alt="seePass" @click.prevent="switchVisibility" src="/src/assets/Images/doNotSeePassword.png">
                        </div>
                    </div>
                    <button class="submitButton" type="submit">Valid new password</button>
                </form>
                <p class="errorMsg">{{error}}</p>
            </div>
            
        </div>
        

        <div class="blocRight">
            <p class="arts">ARTS</p>
            <img class="artsLogo" src="../assets/Images/artsLogo.png">
        </div>
    </div>
</template>

<style scoped>
.labelSpace {
    margin-top: 30px;
}

#sendEmail {
    background-color: red;
    border-color: red;
}

#codeButton {
    background-color: cadetblue;
    border-color: cadetblue;
}

#formCode {
    display: none;
}

#formPass {
    display: none;
}

</style>
