<script>
export default {
  data() {
    return {
    };
  },
}
</script>

<template>
    <body>
    <!--Home page-->
        <section>
        <div class="banner">
            <div class="content">
                <h1>SHARE YOUR ARTWORKS</h1>
                <div>
                    <button class="mainPageButton" type="button">GET STARTED</button>
                </div>
            </div>
        </div>
        </section>
    <!---About US-->
    <section>
        <div class="containerabout">
            <div class="left"></div>
            <div class="right">
              <div class="content-about">
                <h1>About US</h1>
                <p>Website for artist to share their works</p>
                <a href="/about" class="btn">More</a>
              </div>
            </div>
          </div>
    </section>

    <!--View-->

        <div class="container">
            <h1 class="heading"> View </h1>
        
            <div class="box-container">
        
                <div class="box">
                    <div class="image"> 
                        <img class="imgMainPage" src="../assets/Images/mainpage/plant.jpg" alt="">
                    </div>
                    <div class="content-view">
                        <h3>Plant</h3>
                    </div>
                </div>
        
                <div class="box" >
                    <div class="image">
                        <img class="imgMainPage" src="../assets/Images/mainpage/art.jpg" alt="">
                    </div>
                    <div class="content-view">
                        <h3>Art</h3>
                    </div>
                </div>
        
                <div class="box">
                    <div class="image">
                        <img class="imgMainPage" src="../assets/Images/mainpage/wall.jpg
                        " alt="">
                    </div>
                    <div class="content-view">
                        <h3>Wall art</h3>
                    </div>
                </div>   
        
            </div>
        
        </div>

        <!--Category-->
        <div class="container">
            <h1 class="heading"> Category </h1>
        
            <div class="box-container">
        
                <div class="box">
                    <div class="image"> 
                        <img class="imgMainPage" src="../assets/Images/mainpage/black.png" alt="">
                    </div>
                    <div class="content-view">
                        <h3>Website</h3>
                    </div>
                </div>
        
                <div class="box" >
                    <div class="image">
                        <img  class="imgMainPage" src="../assets/Images/mainpage/logo.png" alt="">
                    </div>
                    <div class="content-view">
                        <h3>Logo Maker</h3>
                    </div>
                </div>
        
                <div class="box">
                    <div class="image">
                        <img class="imgMainPage" src="../assets/Images/mainpage/squid.png" alt="">
                    </div>
                    <div class="content-view">
                        <h3>Squid Game</h3>
                    </div>
                </div>   
        
            </div>
        
        </div>

    <!--Contact-->
    <section>
        <div class="contact">
            <h1> Sharing experience</h1>
            <a href="/contact">
                <button class="mainPageButton" type="button">Contact</button>
            </a>
        </div>

    </section>
    </body>
</template>

<style scoped>

.banner{
    width: 100%;
    height: 100vh;
    background-image: linear-gradient(rgba(0,0,0,0.74),rgba(0,0,0,0.75)),url(../assets/Images/mainpage/background.jpg);
    background-size: cover;
    background-position: center;
}

.content{
    width: 100%;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    text-align: center;
    color: #fff;
}
.content h1{
    font-size: 70px;
    margin-top: 80px;
}
.mainPageButton{
    width: 200px;
    padding: 15px 0;
    text-align: center;
    margin: 20px 10px;
    font-weight: bold;
    border: 2px solid blue;
    background: transparent;
    color: #fff;
    cursor: pointer;
    position:relative;
    overflow: hidden;
}

.mainPageButton:hover{
    border-color: rgba(0, 0, 0, 0);
    background: blue;
    height: 100%;
    transition: 1s;
}

html {
  font-family: "Montserrat";
}

/* section {
  min-height: 100vh;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #1e1f26;
} */

.containerabout {
  width: 90%;
  max-width: 1000px;
  margin: 50px auto;
  display: flex;
  align-items: center;
  justify-content: center;
}
.left {
  width: 50%;
  height: 600px;
  background: url(https://images.unsplash.com/photo-1542662565-7e4b66bae529?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=564&q=80)
    no-repeat center / cover;
  border-radius: 8px;
}
.right {
  width: 50%;
  min-height: 400px;
  background-color: #303137;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 30px;
  border-radius: 8px;
  color: white;
  margin-left: -150px;
}
.right h1 {
  font-size: 40px;
  font-weight: lighter;
}
.right p {
  margin: 20px 0;
  font-weight: 500;
  line-height: 25px;
}
.right a {
  text-decoration: none;
  text-transform: uppercase;
  background-color: white;
  color: black;
  padding: 20px 30px;
  display: inline-block;
  letter-spacing: 2px;
}

@media only screen and (max-width: 768px) {
  .containerabout {
    flex-direction: column;
    width: 100%;
    margin: 0 20px;
  }
  .left {
    width: 100%;
    height: 400px;
  }
  .right {
    width: 90%;
    margin: 0;
    margin-top: -100px;
  }
}
/*-- View --*/


.container .heading{
  text-align: center;
  padding:10px;
  color:#333;
  font-size: 35px;
  border-bottom: 3px solid #333;
}

.container .box-container{
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap:20px;
  padding:20px 9%;
  background: #eee;
}

.container .box-container.active{
  padding-bottom: 150px;
}

.container .box-container .box{
  border-radius: 10px;
  background:#fff;
  box-shadow: 0 5px 10px rgba(0,0,0,.2);
  padding:15px;
  cursor: pointer;
}

.container .box-container .box .image{
  height:400px;
  width: 100%;
  overflow: hidden;
  border-radius: 10px;
  position: relative;
}

.container .box-container .box .image img{
  height:100%;
  width:100%;
  object-fit: cover;
}

.container .box-container .box .image .play{
  position: absolute;
  top:0; left:0;
  height: 100%;
  width:100%;
  background: rgba(0,0,0,.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
  display: none;
}

.container .box-container .box:hover .image .play{
  display: grid;
}

.container .box-container .box .image .play i{
  font-size: 100px;
  color:#fff;
}

.container .box-container .box .content{
  padding-top: 15px;
}

.container .box-container .box .content h3{
  font-size: 20px;
  color:#333;
}



@media (max-width:768px) {
  .container .box-container{
    padding:20px;
  }
}

@media (max-width:450px) {
  .container .box-container{
    grid-template-columns: 1fr;
  }

  .container .box-container .box .image{
    height: 300px;
  }
}

/*-- Contact --*/

.contact{
    margin: 100px auto;
    width: 80%;
    background-image: linear-gradient(rgba(0,0,0,0.7),rgba(0,0,0,0.7)),url(../assets/Images/mainpage/banner.jpg);
    background-size: cover;
    text-align: center;
    padding: 100px 0;
}
.contact h1{
    color: #fff;
    margin-bottom: 40px;
    padding:0;

}

.imgMainPage{
  width: 20px;
  height: 20px;
  color: aliceblue;
  text-decoration: none;
}

</style>