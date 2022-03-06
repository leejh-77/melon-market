import { createRouter, createWebHistory } from 'vue-router'

import ProductsView from '../views/ProductsView.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '@/views/SignupView'

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
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
