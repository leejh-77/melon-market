import { createRouter, createWebHistory } from 'vue-router'

import ProductsView from '../views/ProductsView.vue'
import LoginView from '../views/LoginView.vue'

const routes = [
  {
    path: '/',
    component: ProductsView
  },
  {
    path: '/login',
    component: LoginView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
