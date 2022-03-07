import { createRouter, createWebHistory } from 'vue-router'

import ProductsView from '../views/ProductsView.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '@/views/SignupView'
import WritePostView from '@/views/WritePostView'

const routes = [
  {
    path: '/',
    component: ProductsView
  },
  {
    path: '/login',
    component: LoginView
  },
  {
    path: '/signup',
    component: SignupView
  },
  {
    path: '/post',
    component: WritePostView
  },
  {
    path: '/post/:postId',
    component: WritePostView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
