<template>
  <div class="main">
    <h1 class="description">중고 매물</h1>
    <div class="board">
      <PostCard class="post-card" v-for="post in posts" :key="post.id" :post="post"/>
    </div>
  </div>
</template>
<script>
import PostCard from '../components/PostCard.vue'
import postService from '@/services/postService'

export default {
  name: 'PostsBoard',
  components: {
    PostCard
  },
  data() {
    return {
      posts: []
    }
  },
  mounted() {
    postService.getPosts()
      .then(res => {
        console.log(res.data)
        this.posts = res.data
      })
  }
}

</script>
<style lang="scss" scoped>
.main {
  height: 100%;
  padding: 50px 0px;
  background: #f8f8f8;

  .description {
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
