import Vue from 'vue'
import VueRouter from 'vue-router'
//import HomeView from '../views/HomeView.vue'
import Login from '../views/Login.vue'
import Dashboard from '../views/Dashboard.vue'
import User from '../views/User.vue'
import Admin from '../views/Admin.vue'
import Test from '../views/Test.vue'
import NotFound from '../views/NotFound.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: Login
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: Dashboard,
    props: {}
  },
  {
    path: '/user',
    name: 'user',
    component: User,
    props: {}
  },
  {
    path: '/admin',
    name: 'admin',
    component: Admin,
    props: {}
  },
  {
    path: '/test',
    name: 'test',
    component: Test,
    props: {}
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    // catch all 404 - define at the very end
    name: 'notfound',
    path: "*",
    component: NotFound,
    props: {}
  }
]

const router = new VueRouter({
  mode: 'history',
  history: true,
  root:  '/',
  base: process.env.BASE_URL,
  routes
})

export default router
