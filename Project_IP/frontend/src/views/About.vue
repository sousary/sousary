<template>
<section>
     <!---space-->
     <div class="empty">
        <div class="about">
            <div>
            <h2 class="h2About"> Whether users are a professional artist or a creative soul who likes design or paint, 
                there's a lot to be said about the numerous benefits that come along with joining an online art community.
                WorkArt Sharing website is a website art community dedicated to sharing or selling art forms such as painting,
                protography, images and so on. Moreover, workArt sharing can be an excellent source of inspiration and motivation for any artist,
                affording you the opportunity to upload their art, receive feedback, 
                learn new techniques and get money from selling art on websites as well.</h2>
            <div id="about1">
                <img class="imgAbout" src="../assets/Images/about/about1.jpg">
                <img class="imgAbout" id="secondImage" src="../assets/Images/artsLogo.png">
            </div>
            </div>
            <div>
                <img class="imgAbout" src="../assets/Images/about/about3.jpg">
                <div class="about2">
                    <h2 class="h2About">
                        "IT ALL BEGINS WITH AN IDEA"
                    </h2>
                </div>
            </div> 
        </div>
     </div>
</section>
</template>

<style scoped>

#secondImage {
    margin-left: 100px;
}

section {
    background-color: white;
}

/*space for write code*/
.empty{
    width:100%;
    height:100%;
    
}

#box1 input{
    background-color: black;
    color: aliceblue;
    border: 0px;
    height: 40px;
}
#box2 input {
   height: 42px;
   width: 60px;
   transition: 0.5s;
   
}
#box2 input:hover{
    background-color:blue;
}
.display1{
   display: flex;
  
   padding-top: 60px;
   flex-direction: row;
}
.h2About{
    font-family: 'Iowan Old Style';
    font-style: normal;
    font-weight: 400;
    font-size: 26px;
    line-height: 49px;
    color: #000000;
    text-align: justify;
}
.imgAbout{
    width: 500px;
    height: 445px;
    padding-left:60px;
    margin-top: 20px;  
}
.about{
    display: flex;
    margin-left: 40px;
    margin-right: 30px;
    

}
.about2 >h2{
    font-family: 'Iowan Old Style';
    text-align: center;
    padding-top: 30px;
    font-style: normal;
    font-weight: 400;
    font-size: 36px;
    line-height: 49px;
    width: 360px;
    height: 162px;
    background: rgba(196, 196, 196, 0.56);
    
}

</style>