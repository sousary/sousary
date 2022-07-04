import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import ForgotPass from '../views/ForgotPass.vue'
import MainPage from '../views/MainPage.vue'
import About from '../views/About.vue'
import Contact from '../views/Contact.vue'
import Register from '../views/Register.vue'
import User from '../views/User.vue'
import ProfileUpdate from '../views/ProfileUpdate.vue'
import ProfileView from '../views/ProfileView.vue'
import Arts from '../views/Arts.vue'
import ArtDetails from '../views/ArtDetails.vue'
import ArtUpdateVue from '../views/ArtUpdate.vue'
import Slider from '../views/Slider.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/oldMain',
      name: 'oldMain',
      component: MainPage
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/register',
      name: 'register',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      // component: () => import('../views/Register.vue')
      component: Register
    },
    {
      path: '/forgotPass',
      name: 'forgotPass',
      component: ForgotPass
    },
    {
      path: '/about',
      name: 'about',
      component: About
    },
    {
      path: '/contact',
      name: 'contact',
      component: Contact
    },
    {
      path: '/profile-update',
      name: 'profileUpdate',
      component: ProfileUpdate
    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView
    },
    {
      path: '/arts',
      name: 'arts',
      component: Arts
    },
    {
      path: '/user/:id',
      name: 'user',
      component: User
    },
    {
      path: '/art/:id',
      name: 'art',
      component: ArtDetails
    },
    {
      path: '/art-update/:id',
      name: 'artUpdate',
      component: ArtUpdateVue
    },
    {
      path: '/',
      name: 'main',
      component: Slider
    }
  ]
})

export default router
