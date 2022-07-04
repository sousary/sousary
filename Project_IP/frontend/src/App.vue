<script setup>
import Header from "./views/Header.vue";
import Footer from "./views/Footer.vue";
</script>

<script>
export default {
  data() {
    return {
    };
  },
  computed: {
    displayHeaderFooter() {
      let ret = true;
      if (this.$route.name == undefined || this.$route.name == "" || this.$route.name == "login" || this.$route.name == "register" || this.$route.name == "forgotPass") {
        ret = false;
      }
      return ret;
    }
  }
}
</script>


<template>
  <Header :key="$route.fullPath" v-if="displayHeaderFooter"/>
  <RouterView/>
  <Footer v-if="displayHeaderFooter"/>
</template>

<style>
@import '@/assets/Style/base.css';
@import "@/assets/Style/CSS.css";
</style>
