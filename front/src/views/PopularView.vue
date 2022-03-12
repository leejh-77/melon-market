<template>
  <div class="main">
    <h1>인기 매물</h1>
    <PostsBoard :type="getListType"/>
  </div>
</template>

<script>
import PostsBoard from '@/views/PostsBoard'
import { ListQuery } from '@/constant'

export default {
  name: 'PopularView',
  components: { PostsBoard },
  computed: {
    getListType() {
      return ListQuery.Popular
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
