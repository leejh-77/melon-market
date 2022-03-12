<template>
  <div class="main">
    <h1>내가 올린 물건</h1>
    <PostsBoard :type="getListType"/>
  </div>
</template>

<script>
import PostsBoard from '@/views/PostsBoard'
import { ListQuery } from '@/constant'

export default {
  name: 'MyPostsView',
  components: { PostsBoard },
  computed: {
    getListType() {
      return ListQuery.Me
    }
  }
}
</script>

<style lang="scss" scoped>

.main {
  padding-top: 50px;
  background: #f8f8f8;
}

</style>
