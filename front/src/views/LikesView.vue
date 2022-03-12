<template>
  <div class="main">
    <h1>좋아요한 물건</h1>
    <PostsBoard :type="getType"/>
  </div>
</template>

<script>
import PostsBoard from '@/views/PostsBoard'
import { ListQuery } from '@/constant'

export default {
  name: 'LikesView',
  computed: {
    getType() {
      return ListQuery.Like
    }
  },
  components: { PostsBoard }
}
</script>

<style lang="scss" scoped>

.main {
  padding-top: 50px;
  background: #f8f8f8;
}

</style>
