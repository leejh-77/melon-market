<template>
  <PostsBoard :query="2"/>
</template>

<script>
import PostsBoard from '@/views/PostsBoard'

export default {
  name: 'LikesView',
  components: { PostsBoard }
}
</script>

<style scoped>

</style>
