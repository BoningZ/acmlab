import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login'

const routes = [
  {
    path: '/',
    name: 'Home',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '@/views/Home.vue')
  },

  {
    path: '/login',
    name: 'Login',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: Login
  },

  {
    path: '/DashSample',
    name: 'DashSample',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '@/views/general/DashSample.vue')
  },

  {
    path: '/ChangePassword',
    name: 'ChangePassword',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '@/views/general/ChangePassword.vue')
  },



  {
    path:'/Register',
    name:'Register',
    component:() => import('@/views/Register.vue')
  },

  {
    path:'/IntervieweeProfile',
    name:'IntervieweeProfile',
    component:() => import('@/views/interviewee/IntervieweeProfile.vue')
  },
  {
    path:'/StudentProfile',
    name:'StudentProfile',
    component:()=>import('@/views/student/StudentProfile.vue')
  },
  {
    path:'/Interview',
    name:'Interview',
    component:()=>import('@/views/student/Interview.vue')
  },
  {
    path:'/Formalize',
    name:'Formalize',
    component:()=>import('@/views/teacher/Formalize.vue')
  },
  {
    path:'/ContestTable',
    name:'ContestTable',
    component:()=>import('@/views/teacher/ContestTable.vue')
  },
  {
    path:'/ContestEdit',
    name:'ContestEdit',
    component:()=>import('@/views/teacher/ContestEdit.vue')
  },
  {
    path:'/TeamTable',
    name:'TeamTable',
    component:()=>import('@/views/student/TeamTable.vue')
  },
  {
    path:'/TeamEdit',
    name:'TeamEdit',
    component:()=>import('@/views/student/TeamEdit.vue')
  },
  {
    path:'/TeamTableAdmin',
    name:'TeamTableAdmin',
    component:()=>import('@/views/teacher/TeamTableAdmin.vue')
  },
  {
    path:'/RankingTable',
    name:'RankingTable',
    component:()=>import('@/views/general/RankingTable.vue')
  },
  {
    path:'/RankingEdit',
    name:'RankingEdit',
    component:()=>import('@/views/teacher/RankingEdit.vue')
  },
  {
    path:'/ApplicationEdit',
    name:'ApplicationEdit',
    component:()=>import('@/views/student/ApplicationEdit.vue')
  },
  {
    path:'/RankingTableAdmin',
    name:'RankingTableAdmin',
    component:()=>import('@/views/teacher/RankingTableAdmin.vue')
  },
  {
    path:'/ContestApply',
    name:'ContestApply',
    component:()=>import('@/views/teacher/ContestApply.vue')
  },
  {
    path:'/ProcessTrace',
    name:'ProcessTrace',
    component:()=>import('@/views/general/ProcessTrace.vue')
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
