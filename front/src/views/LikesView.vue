<template>
  <PostsBoard :type="getType"/>
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

<style scoped>

</style>
