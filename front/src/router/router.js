import { createRouter, createWebHistory } from 'vue-router'

import PostsBoard from '../views/PostsBoard.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '@/views/SignupView'
import PostDetailView from '@/views/PostDetailView'
import PostEditView from '@/views/PostEditView'

const routes = [
  {
    path: '/',
    component: PostsBoard
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
    path: '/post-edit/:postId',
    component: PostEditView
  },
  {
    path: '/post-edit',
    component: PostEditView
  },
  {
    path: '/post-detail/:postId',
    component: PostDetailView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
