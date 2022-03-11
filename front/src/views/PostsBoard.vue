<template>
  <div class="main">
    <h1 class="title">{{ getTitle }}</h1>
    <div class="board">
      <PostCard v-on:onClickImage="onClickImage(post.id)" class="post-card" v-for="post in posts" :key="post.id"
                :post="post"/>
    </div>
  </div>
</template>
<script>
import PostCard from '../components/PostCard.vue'
import postService from '@/services/postService'
import { ListQuery } from '@/constant'

export default {
  name: 'PostsBoard',
  props: ['query'],
  components: {
    PostCard
  },
  computed: {
    getTitle() {
      const type = this.query ?? ListQuery.Recent
      switch (type) {
        case ListQuery.Recent:
          return '최근 매물'
        case ListQuery.Like:
          return '좋아요한 매물'
        case ListQuery.Popular:
          return '인기 매물'
        default:
          return ''
      }
    }
  },
  data() {
    return {
      posts: []
    }
  },
  methods: {
    onClickImage(id) {
      this.$router.push('/post-detail/' + id)
    }
  },
  mounted() {
    const type = this.query ?? ListQuery.Recent
    postService.getPostList(type)
      .then(res => {
        console.log('[GetPostList]', res.data)
        this.posts = res.data
      })
  }
}

</script>
<style lang="scss" scoped>
.main {
  height: 100%;
  padding: 50px 0;
  background: #f8f8f8;

  .title {
    margin-bottom: 40px;
  }

  .board {
    display: grid;
    justify-content: center;
    grid-template-columns: repeat(4, 220px);
    column-gap: 60px;
    row-gap: 50px;
  }
}
</style>
