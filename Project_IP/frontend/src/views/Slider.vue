<script>
export default {
  data() {
    return {
        btns: "",
        slides: "",
        currentImg: 1
    };
  },
  mounted() {
    this.btns = document.querySelectorAll(".nav-btn");
    this.slides = document.querySelectorAll(".video-slide");
    setTimeout(this.autoSlide, 4000);
  },
  methods: {
    sliderNav(manual) {
        this.btns.forEach((btn) => {
            btn.classList.remove("active");
        });

        this.slides.forEach((slide) => {
            slide.classList.remove("active");
        });

        this.btns[manual].classList.add("active");
        this.slides[manual].classList.add("active");
        this.currentImg = manual;
    },
    autoSlide() {
        if (this.currentImg > 4) {
            this.currentImg = 0;
        }
        this.sliderNav(this.currentImg)
        this.currentImg++;
        setTimeout(this.autoSlide, 4000);
    }
  }
}
</script>

<template>
<body>
    <section class="home">
      <img class="video-slide active" src="/src/assets/Images/slider/25571.jpg">
      <img class="video-slide" src="/src/assets/Images/slider/2.jpg">
      <img class="video-slide" src="/src/assets/Images/slider/42271.jpg">
      <img class="video-slide" src="/src/assets/Images/slider/4.jpg">
      <img class="video-slide" src="/src/assets/Images/slider/5.jpg">
      
      <div class="text-content">
        <h2 class="title">We Inspire You <br>By The <span>ARTS</span></h2>
        <p></p>
      </div>
      <div class="button-container">
        <a id="refDiscover" href="arts"><button class="read-btn">Get Started </button></a>
      </div>
      

      <div class="slider-navigation">
        <div @click="sliderNav(0)" class="nav-btn active"></div>
        <div @click="sliderNav(1)" class="nav-btn"></div>
        <div @click="sliderNav(2)" class="nav-btn"></div>
        <div @click="sliderNav(3)" class="nav-btn"></div>
        <div @click="sliderNav(4)" class="nav-btn"></div>
      </div>
    </section>
  </body>
</template>

<style scoped>
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Poppins", sans-serif;
}

#refDiscover{
    text-decoration: none;
}

header{
  z-index: 999;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 200px;
  transition: 0.5s ease;
}

header .brand{
  color: #fff;
  font-size: 1.5em;
  font-weight: 700;
  text-transform: uppercase;
  text-decoration: none;
}

header .navigation{
  position: relative;
}

header .navigation .navigation-items a{
  position: relative;
  color: #fff;
  font-size: 1em;
  font-weight: 500;
  text-decoration: none;
  margin-left: 30px;
  transition: 0.3s ease;
}

header .navigation .navigation-items a:before{
  content: '';
  position: absolute;
  background: #fff;
  width: 0;
  height: 3px;
  bottom: 0;
  left: 0;
  transition: 0.3s ease;
}

header .navigation .navigation-items a:hover:before{
  width: 100%;
}

/* section{
  padding: 100px 200px;
} */

.home{
  position: relative;
  width: 100%;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  flex-direction: column;
  background: #2696E9;
}

.home:before{
  z-index: 777;
  content: '';
  position: absolute;
  background: rgba(3, 96, 251, 0.3);
  /* width: 100%;
  height: 100%; */
  top: 0;
  left: 0;
}

.home .content{
  z-index: 888;
  color: #fff;
  width: 100%;
  margin-top: 50px;
  display: none;
}

.home .content.active{
  display: block;
}


.home video{
  z-index: 000;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.slider-navigation{
  z-index: 888;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  transform: translateY(300px);
  margin-bottom: 12px;
}

.slider-navigation .nav-btn{
  width: 12px;
  height: 12px;
  background: #fff;
  border-radius: 50%;
  cursor: pointer;
  box-shadow: 0 0 2px rgba(255, 255, 255, 0.5);
  transition: 0.3s ease;
}

.slider-navigation .nav-btn.active{
  background: #f7ff05;
}

.slider-navigation .nav-btn:not(:last-child){
  margin-right: 20px;
}

.slider-navigation .nav-btn:hover{
  transform: scale(1.2);
}

.video-slide{
  position: absolute;
  width: 100%;
  height: 100vh;
  clip-path: circle(0% at 0 50%);
  object-fit: cover;
}

.video-slide.active{
  clip-path: circle(150% at 0 50%);
  width: 100%;
  transition: 2s ease;
  transition-property: clip-path;
}


.text-content{
  color: #fff;
  position: absolute;
  top: 25%;
  /* color: var(--white-color); */
  margin: 0 200px;
  transition: 0.3s ease;

  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  text-shadow: var(--text-shadow);
  padding: 20px;
  border-radius: 10px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  border-right: 1px solid rgba(255, 255, 255, 0.1);
  box-shadow: var(--box-shadow);

}
.text-content .title{
  font-size: 4em;
  font-weight: 700;
  text-shadow: var(--text-shadow);
  margin-bottom: 20px;
  /* transform: translateY(-50px);
  opacity: 0; */
}
/* .text-content-active .title{
  transform: translateY(0);
  opacity: 1;
  transition: 1s ease;
  transition-delay: 0.3s;
  transition-property: transform, opacity;
} */
.text-content span {
  font-size: o.5em;
  max-width: 700px;
  font-family: "Cinzel Decorative";
}
.text-content p{
  font-size: 4em;
  max-width: 700px;
  font-family: "Cinzel Decorative";
  /* background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  text-shadow: var(--text-shadow);
  padding: 20px;
  border-radius: 10px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  border-right: 1px solid rgba(255, 255, 255, 0.1);
  box-shadow: var(--box-shadow); */
  /* transform: translateX(-80px);
  opacity: 0; */
}

.button-container .read-btn{

  border: none;
  outline: none; 

  font-size: 1em;
  font-weight: bold;
  padding: 25px 25px;
  height: 2rem;
  color: #222;
  background-color: #fff;
  display: flex;
  align-items: center; 
  margin-top: 40px;
  border-radius: 10px; 
  cursor: pointer;
  
  /* transform: translateX(50px);
  opacity: 0; */

 
}
.button-container{
  position: absolute;
  top: 57%;
  left: 13%;
  z-index: 100;
  
}

@media (max-width: 1040px){
  header{
    padding: 12px 20px;
  }

  section{
    padding: 100px 20px;
  }

  .home .media-icons{
    right: 15px;
  }

  header .navigation{
    display: none;
  }

  header .navigation.active{
    position: fixed;
    width: 100%;
    height: 100vh;
    top: 0;
    left: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    background: rgba(1, 1, 1, 0.5);
  }

  header .navigation .navigation-items a{
    color: #222;
    font-size: 1.2em;
    margin: 20px;
  }

  header .navigation .navigation-items a:before{
    background: #222;
    height: 5px;
  }

  header .navigation.active .navigation-items{
    background: #fff;
    width: 600px;
    max-width: 600px;
    margin: 20px;
    padding: 40px;
    display: flex;
    flex-direction: column;
    align-items: center;
    border-radius: 5px;
    box-shadow: 0 5px 25px rgb(1 1 1 / 20%);
  }

  .menu-btn{
    background: url(menu.png)no-repeat;
    background-size: 30px;
    background-position: center;
    width: 40px;
    height: 40px;
    cursor: pointer;
    transition: 0.3s ease;
  }

  .menu-btn.active{
    z-index: 999;
    background: url(close.png)no-repeat;
    background-size: 25px;
    background-position: center;
    transition: 0.3s ease;
  }
}

@media (max-width: 560px){
  .home .content h1{
    font-size: 3em;
    line-height: 60px;
  }
}




</style>
