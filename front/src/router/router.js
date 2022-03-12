import { createRouter, createWebHistory } from 'vue-router'

import LoginView from '../views/LoginView.vue'
import SignupView from '@/views/SignupView'
import PostDetailView from '@/views/PostDetailView'
import PostEditView from '@/views/PostEditView'
import LikesView from '@/views/LikesView'
import PopularView from '@/views/PopularView'
import MainBoardView from '@/views/MainBoardView'
import MyPostsView from '@/views/MyPostsView'

const routes = [
  // home
  { path: '/', component: MainBoardView },

  // auth
  { path: '/login', component: LoginView },
  { path: '/signup', component: SignupView },

  // post
  { path: '/post-edit/:postId', component: PostEditView },
  { path: '/post-edit', component: PostEditView },
  { path: '/post-detail/:postId', component: PostDetailView },

  // like
  { path: '/likes', component: LikesView },
  { path: '/popular', component: PopularView },
  { path: '/mine', component: MyPostsView }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
