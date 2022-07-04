<template>
    <section>
        <div class="footer">
            <div class="display">
                <div>
                    <p class="footerTitle">Contact</p>
                        <font-awesome-icon :icon="['fas', 'phone']"/>
                        <a href="tel:1-212-555-5555"> (+855) 012 828 278 </a>
                    <div class="emailFooter">
                        <font-awesome-icon :icon="['fas', 'envelope']"/>
                        <a href="mailto:#"> arts.sharing.itc@gmail.com</a>
                    </div>
                </div>
                <div>
                    <p class="footerTitle">Social media</p>
                    <div class="icon" >
                        <a class="socialMediaIcons" href="https://web.facebook.com/itckh?_rdc=1&_rdr"><font-awesome-icon :icon="['fab', 'facebook']" /></a>
                        <a class="socialMediaIcons" href="https://www.instagram.com/explore/tags/angkorwat/?hl=fr"><font-awesome-icon :icon="['fab', 'instagram']" /></a>
                        <a class="socialMediaIcons" href="https://twitter.com/INSA_Rennes"><font-awesome-icon :icon="['fab', 'twitter']" /></a>
                       </div>
                </div>
               <div>
                <p class="footerTitle">Stay in touch!</p>
                    <div class="display1">
                        <div id="box1">
                            <input type="text" placeholder="E-mail">
                            
                        </div>
                        <div id="box2">
                            <input id="sendButtonFooter" type="button" value="send">
                        </div>
                    </div>
               </div>
                
            </div>
        </div>
    </section>
</template>

<style scoped>
.footer{   
    width: 100%;
    height: 110px;
    background-color: rgb(66, 63, 63);
    color:aliceblue; 
    opacity: 0.8;  
    bottom: 0;
    
}
.display{
    display: flex;
    justify-content: space-between;
    /* padding-top: 20px; */
    padding-left: 30px;
    padding-right: 30px;
}

.display a {
    color: white;
    text-decoration: none;
}

.icon a{
    color: aliceblue;
    text-decoration: none;
    font-size: 25px;
    padding-left: 5px;
   
}
.display1{
   display: flex;
   padding-top: 20px;
   flex-direction: row;
}

.socialMediaIcons{
  margin-right: 10px;
}

.footerTitle{
  text-align: center;
  font-weight: bold;
  font-size: 1.2em;
  padding-top: 10px;
}

#sendButtonFooter{
    margin-left: 3px;
    width: 50px;
  border-radius: 0.7em;
  border-color: grey;
  color: black;
  background-color: white;
  border-width: 0.1em;
  border-style: solid;
  padding: 1px;
}

.icon{
   padding-top: 10px;
}
#box1 input{
    margin-top: 0.15em;
    border-radius: 0.5em;
    background-color: black;
    color: aliceblue;
    height: 35px;
}

#box1 input::placeholder{
    color: white;
}

#box2 input {
   height: 35px;
}

.emailFooter {
    padding-top: 10px;
}
</style>