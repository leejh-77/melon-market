import { createRouter, createWebHistory } from 'vue-router'

import PostsBoard from '../views/PostsBoard.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '@/views/SignupView'
import PostDetailView from '@/views/PostDetailView'
import PostEditView from '@/views/PostEditView'
import LikesView from '@/views/LikesView'

const routes = [
  // home
  { path: '/', component: PostsBoard },

  // auth
  { path: '/login', component: LoginView },
  { path: '/signup', component: SignupView },

  // post
  { path: '/post-edit/:postId', component: PostEditView },
  { path: '/post-edit', component: PostEditView },
  { path: '/post-detail/:postId', component: PostDetailView },

  // like
  { path: '/likes', component: LikesView }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
